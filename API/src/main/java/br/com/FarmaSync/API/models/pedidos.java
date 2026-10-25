package br.com.FarmaSync.API.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="pedidos")

public class pedidos {
    @Id
    @Column(name="pedidos_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pedidosId ;





    @Column(name = "produto_json",columnDefinition = "TEXT")
    private String produtoJson;


    @Column(name="pagamento",nullable = false)
    private String pagamento;

    @Column(name="valor_total")
    private Double valorTotal;

    @Column(name="quantidade_total",nullable = false)
    private Integer quantidadeTotal;

    @Column(name="data_pedido")
    private LocalDateTime dataPedido;

    @Column(name="pedido_atualizacao")
    private LocalDateTime pedidoAtualizacao;

    public Long getPedidosId() {
        return pedidosId;
    }

    public void setPedidosId(Long pedidosId) {
        this.pedidosId = pedidosId;
    }







    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }


    public String getProdutoJson() {
        return produtoJson;
    }

    public void setProdutoJson(String produtoJson) {
        this.produtoJson = produtoJson;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getPedidoAtualizacao() {
        return pedidoAtualizacao;
    }

    public void setPedidoAtualizacao(LocalDateTime pedidoAtualizacao) {
        this.pedidoAtualizacao = pedidoAtualizacao;
    }
}
