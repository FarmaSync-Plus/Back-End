using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Pedidos.Api.Data;
using Pedidos.Api.DTOs;
using Pedidos.Api.Models;
using Pedidos.Api.Services;

namespace Pedidos.Api.Controllers;

[ApiController]
[Route("api/pedidos")]
public sealed class PedidosController(FarmaciaDbContext db, PedidoService service) : ControllerBase
{
    [HttpPost]
    public async Task<ActionResult<PedidoResponse>> Criar(CriarPedidoRequest request, CancellationToken cancellationToken)
    {
        try
        {
            var pedido = await service.CriarAsync(request, cancellationToken);
            return CreatedAtAction(nameof(Obter), new { id = pedido.PedidoId }, Mapear(pedido));
        }
        catch (ArgumentException ex) { return BadRequest(new { mensagem = ex.Message }); }
        catch (InvalidOperationException ex) { return Conflict(new { mensagem = ex.Message }); }
        catch (DbUpdateException) { return BadRequest(new { mensagem = "Usuário inválido ou erro ao gravar o pedido." }); }
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<PedidoResponse>>> Listar([FromQuery] int usuarioId, CancellationToken cancellationToken)
    {
        if (usuarioId <= 0) return BadRequest(new { mensagem = "Informe um usuarioId válido." });
        var pedidos = await db.Pedidos.AsNoTracking().Include(p => p.Itens)
            .Where(p => p.UsuarioId == usuarioId).OrderByDescending(p => p.DataPedido).ToListAsync(cancellationToken);
        return Ok(pedidos.Select(Mapear));
    }

    [HttpGet("{id:int}")]
    public async Task<ActionResult<PedidoResponse>> Obter(int id, CancellationToken cancellationToken)
    {
        var pedido = await db.Pedidos.AsNoTracking().Include(p => p.Itens)
            .FirstOrDefaultAsync(p => p.PedidoId == id, cancellationToken);
        return pedido is null ? NotFound(new { mensagem = "Pedido não encontrado." }) : Ok(Mapear(pedido));
    }

    [HttpPatch("{id:int}/status")]
    public async Task<ActionResult<PedidoResponse>> AtualizarStatus(int id, AtualizarStatusRequest request, CancellationToken cancellationToken)
    {
        var pedido = await db.Pedidos.Include(p => p.Itens).FirstOrDefaultAsync(p => p.PedidoId == id, cancellationToken);
        if (pedido is null) return NotFound(new { mensagem = "Pedido não encontrado." });
        if (pedido.Status == StatusPedido.Cancelado || pedido.Status == StatusPedido.Entregue)
            return Conflict(new { mensagem = "Pedidos entregues ou cancelados não podem ser alterados." });
        pedido.Status = request.Status;
        pedido.PedidoAtualizacao = DateTime.UtcNow;
        await db.SaveChangesAsync(cancellationToken);
        return Ok(Mapear(pedido));
    }

    [HttpPost("{id:int}/cancelar")]
    public async Task<ActionResult<PedidoResponse>> Cancelar(int id, CancellationToken cancellationToken)
    {
        var pedido = await db.Pedidos.Include(p => p.Itens).FirstOrDefaultAsync(p => p.PedidoId == id, cancellationToken);
        if (pedido is null) return NotFound(new { mensagem = "Pedido não encontrado." });
        if (pedido.Status is StatusPedido.Enviado or StatusPedido.Entregue or StatusPedido.Cancelado)
            return Conflict(new { mensagem = "Este pedido não pode mais ser cancelado." });

        var ids = pedido.Itens.Select(i => i.IdProduto).ToList();
        var produtos = await db.Produtos.Where(p => ids.Contains(p.IdProduto)).ToDictionaryAsync(p => p.IdProduto, cancellationToken);
        foreach (var item in pedido.Itens) produtos[item.IdProduto].Estoque += item.Quantidade;
        pedido.Status = StatusPedido.Cancelado;
        pedido.PedidoAtualizacao = DateTime.UtcNow;
        await db.SaveChangesAsync(cancellationToken);
        return Ok(Mapear(pedido));
    }

    private static PedidoResponse Mapear(Pedido p) => new(p.PedidoId, p.UsuarioId, NomePagamento(p.Pagamento), p.ValorTotal,
        NomeStatus(p.Status), p.DataPedido, p.PedidoAtualizacao,
        p.Itens.Select(i => new PedidoItemResponse(i.IdProduto, i.NomeProduto, i.PrecoUnitario, i.Quantidade, i.Subtotal)).ToList());

    private static string NomePagamento(FormaPagamento value) => value switch
    {
        FormaPagamento.CartaoDeCredito => "Cartão de Crédito",
        FormaPagamento.BoletoBancario => "Boleto Bancário",
        FormaPagamento.Pix => "PIX",
        _ => value.ToString()
    };

    private static string NomeStatus(StatusPedido value) => value switch
    {
        StatusPedido.EmPreparacao => "Em preparação",
        _ => value.ToString()
    };
}
