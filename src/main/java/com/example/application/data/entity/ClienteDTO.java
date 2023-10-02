package com.example.application.data.entity;

import com.example.application.views.main.component.customField.Phone;

import java.time.LocalDate;
import java.util.Objects;

public class ClienteDTO {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private Phone telefone;
    private String email;
    private String cep;
    private String bairro;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String cidade;
    private String estado;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Phone getTelefone() {
        return telefone;
    }

    public void setTelefone(Phone telefone) {
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO that = (ClienteDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) &&
                Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(cpf, that.cpf) &&
                Objects.equals(telefone, that.telefone) && Objects.equals(email, that.email) && Objects.equals(cep, that.cep)
                && Objects.equals(bairro, that.bairro) && Objects.equals(logradouro, that.logradouro) &&
                Objects.equals(numero, that.numero) && Objects.equals(complemento, that.complemento) &&
                Objects.equals(cidade, that.cidade) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataNascimento, cpf, telefone, email, cep, bairro, logradouro, numero,
                complemento, cidade, estado);
    }
}
