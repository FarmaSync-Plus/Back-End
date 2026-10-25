package br.com.FarmaSync.API.models;

import jakarta.persistence.*;

import java.sql.Date;


@Entity
@Table(name = "produtos")
public class produtos {
@Id
@Column(name = "id_produto")
@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long produtosId;

@Column(name = "nome", nullable = true)
    private String nome;
@Column(name = "descricao")
    private String descricao;

@Column(name = "preco", nullable = true)
    private Double preco;
@Column(name = "estoque",nullable = true)
    private Integer estoque;

@Column(name = "fabricante")
    private String fabricante;

@Column(name="data_validade")
    private Date dataValidade;
@Column(name = "precisa_receita")
    private Boolean precisaReceita;


    public Long getProdutosId() {
        return produtosId;
    }

    public void setProdutosId(Long produtosId) {
        this.produtosId = produtosId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Boolean getPrecisaReceita() {
        return precisaReceita;
    }

    public void setPrecisaReceita(Boolean precisaReceita) {
        this.precisaReceita = precisaReceita;
    }
}
