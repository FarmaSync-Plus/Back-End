package br.com.FarmaSync.API.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="pedidos")

public class pedidos {
    @Id
    @Column(name="pedidos_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pedidosId ;

    @Column(name="nome")
    private String nome;

    @Column(name="peso")
    private Float peso;

    @Column(name="pagamento",nullable = false)
    private String pagamento;

    @Column(name="valor_total")
    private Float valorTotal;

    @Column(name="quantidade_pedido",nullable = false)
    private String quantidadePedido;

    @Column(name="data_pedido")
    private Date dataPedido;

    @Column(name="pedido_atualizacao")
    private String pedidoAtualizacao;

    public Long getPedidosId() {
        return pedidosId;
    }

    public void setPedidosId(Long pedidosId) {
        this.pedidosId = pedidosId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getQuantidadePedido() {
        return quantidadePedido;
    }

    public void setQuantidadePedido(String quantidadePedido) {
        this.quantidadePedido = quantidadePedido;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getPedidoAtualizacao() {
        return pedidoAtualizacao;
    }

    public void setPedidoAtualizacao(String pedidoAtualizacao) {
        this.pedidoAtualizacao = pedidoAtualizacao;
    }
}
