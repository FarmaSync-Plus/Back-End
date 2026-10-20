using System.ComponentModel.DataAnnotations;
using Pedidos.Api.Models;

namespace Pedidos.Api.DTOs;

public sealed class CriarPedidoRequest
{
    [Range(1, int.MaxValue)] public int UsuarioId { get; init; }
    [Required] public FormaPagamento Pagamento { get; init; }
    [Required, MinLength(1)] public List<CriarPedidoItemRequest> Itens { get; init; } = [];
}

public sealed class CriarPedidoItemRequest
{
    [Range(1, int.MaxValue)] public int ProdutoId { get; init; }
    [Range(1, 999)] public int Quantidade { get; init; }
}

public sealed class AtualizarStatusRequest
{
    [Required] public StatusPedido Status { get; init; }
}

public sealed record ProdutoResponse(
    int IdProduto, string Nome, string? Descricao, decimal Preco, int Estoque,
    string? Fabricante, DateTime? DataValidade, bool PrecisaReceita, int? CategoriaId, string? Imagem);

public sealed record PedidoItemResponse(
    int IdProduto, string NomeProduto, decimal PrecoUnitario, int Quantidade, decimal Subtotal);

public sealed record PedidoResponse(
    int PedidoId, int UsuarioId, string Pagamento, decimal ValorTotal, string Status,
    DateTime DataPedido, DateTime PedidoAtualizacao, IReadOnlyList<PedidoItemResponse> Itens);
