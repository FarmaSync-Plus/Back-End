using Microsoft.EntityFrameworkCore;
using Pedidos.Api.Data;
using Pedidos.Api.DTOs;
using Pedidos.Api.Models;

namespace Pedidos.Api.Services;

public sealed class PedidoService(FarmaciaDbContext db)
{
    public async Task<Pedido> CriarAsync(CriarPedidoRequest request, CancellationToken cancellationToken)
    {
        var itensAgrupados = request.Itens
            .GroupBy(i => i.ProdutoId)
            .Select(g => new { ProdutoId = g.Key, Quantidade = g.Sum(i => i.Quantidade) })
            .ToList();

        await using var transaction = await db.Database.BeginTransactionAsync(cancellationToken);
        var produtos = await db.Produtos
            .Where(p => itensAgrupados.Select(i => i.ProdutoId).Contains(p.IdProduto))
            .ToDictionaryAsync(p => p.IdProduto, cancellationToken);

        if (produtos.Count != itensAgrupados.Count)
            throw new ArgumentException("Um ou mais produtos não foram encontrados.");

        var pedido = new Pedido
        {
            UsuarioId = request.UsuarioId,
            Pagamento = request.Pagamento,
            Status = StatusPedido.Pendente,
            DataPedido = DateTime.UtcNow,
            PedidoAtualizacao = DateTime.UtcNow
        };

        foreach (var item in itensAgrupados)
        {
            var produto = produtos[item.ProdutoId];
            if (produto.Estoque < item.Quantidade)
                throw new InvalidOperationException($"Estoque insuficiente para '{produto.Nome}'. Disponível: {produto.Estoque}.");

            produto.Estoque -= item.Quantidade;
            var subtotal = produto.Preco * item.Quantidade;
            pedido.Itens.Add(new PedidoItem
            {
                IdProduto = produto.IdProduto,
                NomeProduto = produto.Nome,
                PrecoUnitario = produto.Preco,
                Quantidade = item.Quantidade,
                Subtotal = subtotal
            });
            pedido.ValorTotal += subtotal;
        }

        db.Pedidos.Add(pedido);
        await db.SaveChangesAsync(cancellationToken);
        await transaction.CommitAsync(cancellationToken);
        return pedido;
    }
}
