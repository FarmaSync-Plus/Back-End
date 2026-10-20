namespace Pedidos.Api.Models;

public enum FormaPagamento
{
    CartaoDeCredito,
    BoletoBancario,
    Pix
}

public enum StatusPedido
{
    Pendente,
    Confirmado,
    EmPreparacao,
    Enviado,
    Entregue,
    Cancelado
}

public sealed class Produto
{
    public int IdProduto { get; set; }
    public string Nome { get; set; } = string.Empty;
    public string? Descricao { get; set; }
    public decimal Preco { get; set; }
    public int Estoque { get; set; }
    public string? Fabricante { get; set; }
    public DateTime? DataValidade { get; set; }
    public bool PrecisaReceita { get; set; }
    public int? CategoriaId { get; set; }
    public string? Imagem { get; set; }
}

public sealed class Pedido
{
    public int PedidoId { get; set; }
    public int UsuarioId { get; set; }
    public FormaPagamento Pagamento { get; set; }
    public decimal ValorTotal { get; set; }
    public StatusPedido Status { get; set; } = StatusPedido.Pendente;
    public DateTime DataPedido { get; set; }
    public DateTime PedidoAtualizacao { get; set; }
    public List<PedidoItem> Itens { get; set; } = [];
}

public sealed class PedidoItem
{
    public int PedidoItemId { get; set; }
    public int PedidoId { get; set; }
    public int IdProduto { get; set; }
    public string NomeProduto { get; set; } = string.Empty;
    public decimal PrecoUnitario { get; set; }
    public int Quantidade { get; set; }
    public decimal Subtotal { get; set; }
}
