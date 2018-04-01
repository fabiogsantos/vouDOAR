package br.com.tcc.tecdam.voudoar.model;

import android.graphics.Color;

import java.util.Date;

/**
 * Created by fabio.goncalves on 24/03/2018.
 */

public class Campanha {
    private Long    id;                 // Identificador gerado automaticamente pelo banco de dados
    private String  titulo;             // Nome da campanha
    private String  slogan;             // Frase de impácto ou Bordão
    private Integer tipo;               // Causa de interesse da campanha: 0 - Outros, 1 - Capacitação Profissional, 2 - Cultura e Arte, 3 - Defesa dos Direitos Humanos, 4 - Educação, 5 - Esportes, 6 - Meio Ambiente e Proteção dos Animais, 7 - Saúde, 8 - Serviços Sociais
    private String  imagem;             // Logotipo
    private Color   corFundo;           // Cor de Fundo
    private Date    dataInicio;         // Data de Início da Campanha
    private Date    dataFinal;          // Data Final da Campanha
    private String  objetivo;           // Sobre nós (Motivação e Objetivos)
    private String  atividades;         // ações realizadas na campanha
    private String  sobreProblema;      // Sobre o problema que a campanha pretende atacar
    private String  sobreSolucao;       // Sobre a solução proposta para atacar o problema
    private String  publicoAlvo;        // Quais pessoas deseja atender
    private String  ufAtuacao;          // Estado de atuação da campanha;
    private String  cidadeAtuacao;      // Cidade de atuação da campanha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Color getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(Color corFundo) {
        this.corFundo = corFundo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAtividades() {
        return atividades;
    }

    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }

    public String getSobreProblema() {
        return sobreProblema;
    }

    public void setSobreProblema(String sobreProblema) {
        this.sobreProblema = sobreProblema;
    }

    public String getSobreSolucao() {
        return sobreSolucao;
    }

    public void setSobreSolucao(String sobreSolucao) {
        this.sobreSolucao = sobreSolucao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getUfAtuacao() {
        return ufAtuacao;
    }

    public void setUfAtuacao(String ufAtuacao) {
        this.ufAtuacao = ufAtuacao;
    }

    public String getCidadeAtuacao() {
        return cidadeAtuacao;
    }

    public void setCidadeAtuacao(String cidadeAtuacao) {
        this.cidadeAtuacao = cidadeAtuacao;
    }
}
