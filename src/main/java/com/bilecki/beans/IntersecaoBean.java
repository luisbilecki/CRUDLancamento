package com.bilecki.beans;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class IntersecaoBean {
	private String mensagem;
	private int f1;
	private int f2;
	private int f3;
	private int f4;

	public int getF1() {
		return f1;
	}

	public void setF1(int f1) {
		this.f1 = f1;
	}

	public int getF2() {
		return f2;
	}

	public void setF2(int f2) {
		this.f2 = f2;
	}

	public int getF3() {
		return f3;
	}

	public void setF3(int f3) {
		this.f3 = f3;
	}

	public int getF4() {
		return f4;
	}

	public void setF4(int f4) {
		this.f4 = f4;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public void calcula() {
		if ((f1>f3 && f2<f4) || (f1<f3 && f2>f4))
		{
			setMensagem("Existe interseção entre as faixas 1 e 2");
		}else
		{
			setMensagem("Não existe interseção entre as faixas 1 e 2");			
		}
	}
}
