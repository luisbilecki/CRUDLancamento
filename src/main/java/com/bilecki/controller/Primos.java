package com.bilecki.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@ManagedBean
public class Primos {
	private int i1;
	private int i2;
	private String mensagem;
	
	public int getI1() {
		return i1;
	}

	public void setI1(int i1) {
		this.i1 = i1;
	}

	public int getI2() {
		return i2;
	}

	public void setI2(int i2) {
		this.i2 = i2;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/** Imprime os números primos entre
	 *  dois números
	 *  Por exemplo, 41 e 5002
	 */
	public void main() {
		if (i1 > i2) {
			setMensagem("O primeiro número não pode ser maior que o segundo!");
			return;
		}
		
		String aux = "";
		
		for (int i = i1; i < i2; i++) {
			boolean ehPrimo = true;
			
			for (int j = i1; j <= i; j++) {
				
                if (((i % j) == 0) && (j != i)) {
                    ehPrimo = false;
                    break;
                }
                
            }

            if (ehPrimo) {
                aux+= i + "\t";
            }
        }
		
		//Exibe os números primos na tela
		setMensagem(aux);		
	}

}
