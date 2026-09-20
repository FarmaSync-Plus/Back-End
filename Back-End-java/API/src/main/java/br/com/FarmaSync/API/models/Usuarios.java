package br.com.FarmaSync.API.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name="usuarios")

public class Usuarios {

@Id
@Column(name="usuario_id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long usuarioId ;

@Column(name = "nome_usuario", nullable=false, unique=false )
private String nomeUsuario;

@Column(name="CPF",nullable = false,unique = true)
private String cpf;


 @Column(name = "genero",nullable = true)
 private String genero;

@Column(name = "logradouro", nullable = false,unique=false)
private String logradouro;



@Column(name = "bairro",nullable = false,unique = false)
private String bairro;

@Column(name= "Cidade", nullable = false,unique = false)
private String cidade;

@Column(name = "estado", nullable=false,unique=false)
private String estado;

@Column(name = "cep",nullable = true,unique = true)
    private String cep;

@Column(name = "email_usuario" ,nullable=false,unique = true)
private String emailUsuario;

@Column(name = "senha_usuario" ,nullable = false,unique=false )
private String senhaUsuario;

@Column(name = "data_de_nasc")
private Date dataDeNascimento;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
}
