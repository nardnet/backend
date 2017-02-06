package com.global.vo;

public class ContratosPublicacoes {

	  private  String codigoContrato;
	  private String dataEntrada;
	  private String dataInclusaoSistema;
	  
	  
	public ContratosPublicacoes(String codigoContrato, String dataEntrada, String dataInclusaoSistema) {
		super();
		this.codigoContrato = codigoContrato;
		this.dataEntrada = dataEntrada;
		this.dataInclusaoSistema = dataInclusaoSistema;
	}
	


	public ContratosPublicacoes() {
		// TODO Auto-generated constructor stub
	}



	public String getCodigoContrato() {
		return codigoContrato;
	}


	public void setCodigoContrato(String codigoContrato) {
		this.codigoContrato = codigoContrato;
	}


	public String getDataEntrada() {
		return dataEntrada;
	}


	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}


	public String getDataInclusaoSistema() {
		return dataInclusaoSistema;
	}


	public void setDataInclusaoSistema(String dataInclusaoSistema) {
		this.dataInclusaoSistema = dataInclusaoSistema;
	}
	  
	  
	  
	
	
}
