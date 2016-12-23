package com.global.entity;

import java.io.Serializable;

public class Teste implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	  private String nome;
	  private String duracao;
	  
	  public Teste(Integer id, String nome, String duracao) {
	    this.setId(id);
	    this.setNome(nome);
	    this.setDuracao(duracao);
	  }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
