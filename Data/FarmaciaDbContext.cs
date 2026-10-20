using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Pedidos.Api.Models;

namespace Pedidos.Api.Data;

public sealed class FarmaciaDbContext(DbContextOptions<FarmaciaDbContext> options) : DbContext(options)
{
    public DbSet<Produto> Produtos => Set<Produto>();
    public DbSet<Pedido> Pedidos => Set<Pedido>();
    public DbSet<PedidoItem> PedidoItens => Set<PedidoItem>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Produto>(entity =>
        {
            entity.ToTable("produtos");
            entity.HasKey(p => p.IdProduto);
            entity.Property(p => p.IdProduto).HasColumnName("id_produto");
            entity.Property(p => p.Nome).HasColumnName("nome").HasMaxLength(100).IsRequired();
            entity.Property(p => p.Descricao).HasColumnName("descricao");
            entity.Property(p => p.Preco).HasColumnName("preco").HasPrecision(10, 2);
            entity.Property(p => p.Estoque).HasColumnName("estoque");
            entity.Property(p => p.Fabricante).HasColumnName("fabricante").HasMaxLength(100);
            entity.Property(p => p.DataValidade).HasColumnName("data_validade");
            entity.Property(p => p.PrecisaReceita).HasColumnName("precisa_receita");
            entity.Property(p => p.CategoriaId).HasColumnName("categoria_id");
            entity.Property(p => p.Imagem).HasColumnName("imagem").HasMaxLength(255);
        });

        modelBuilder.Entity<Pedido>(entity =>
        {
            entity.ToTable("pedidos");
            entity.HasKey(p => p.PedidoId);
            entity.Property(p => p.PedidoId).HasColumnName("pedido_id");
            entity.Property(p => p.UsuarioId).HasColumnName("usuario_id");
            entity.Property(p => p.Pagamento).HasColumnName("pagamento")
                .HasConversion(new ValueConverter<FormaPagamento, string>(
                    value => ConverterPagamento(value), value => ConverterPagamento(value)));
            entity.Property(p => p.ValorTotal).HasColumnName("valor_total").HasPrecision(10, 2);
            entity.Property(p => p.Status).HasColumnName("status")
                .HasConversion(new ValueConverter<StatusPedido, string>(
                    value => ConverterStatus(value), value => ConverterStatus(value)));
            entity.Property(p => p.DataPedido).HasColumnName("data_pedido");
            entity.Property(p => p.PedidoAtualizacao).HasColumnName("pedido_atualizacao");
            entity.HasMany(p => p.Itens).WithOne().HasForeignKey(i => i.PedidoId);
        });

        modelBuilder.Entity<PedidoItem>(entity =>
        {
            entity.ToTable("pedido_itens");
            entity.HasKey(i => i.PedidoItemId);
            entity.Property(i => i.PedidoItemId).HasColumnName("pedido_item_id");
            entity.Property(i => i.PedidoId).HasColumnName("pedido_id");
            entity.Property(i => i.IdProduto).HasColumnName("id_produto");
            entity.Property(i => i.NomeProduto).HasColumnName("nome_produto").HasMaxLength(100).IsRequired();
            entity.Property(i => i.PrecoUnitario).HasColumnName("preco_unitario").HasPrecision(10, 2);
            entity.Property(i => i.Quantidade).HasColumnName("quantidade");
            entity.Property(i => i.Subtotal).HasColumnName("subtotal").HasPrecision(10, 2);
        });
    }

    private static string ConverterPagamento(FormaPagamento value) => value switch
    {
        FormaPagamento.CartaoDeCredito => "Cartão de Crédito",
        FormaPagamento.BoletoBancario => "Boleto Bancário",
        FormaPagamento.Pix => "PIX",
        _ => value.ToString()
    };

    private static FormaPagamento ConverterPagamento(string value) => value switch
    {
        "Cartão de Crédito" => FormaPagamento.CartaoDeCredito,
        "Boleto Bancário" => FormaPagamento.BoletoBancario,
        "PIX" => FormaPagamento.Pix,
        _ => throw new ArgumentOutOfRangeException(nameof(value), value, "Forma de pagamento inválida")
    };

    private static string ConverterStatus(StatusPedido value) => value == StatusPedido.EmPreparacao
        ? "Em preparação" : value.ToString();

    private static StatusPedido ConverterStatus(string value) => value == "Em preparação"
        ? StatusPedido.EmPreparacao : Enum.Parse<StatusPedido>(value.Replace(" ", ""), true);
}
