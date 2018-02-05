package com.bilecki.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class Mensagem {
	
	//Exibir mensagem na tela
	public static void mostrarMensagem(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage("Alerta", mensagem));
	}
	
	//Exibir mensagem de atenção
	public static void mostrarMensagemAtencao(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
	}
	
	//Exibir mensagem de informação
	public static void mostrarMensagemInfo(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", mensagem));
	}
	
	//Exibir mensagem de erro
	public static void mostrarMensagemErro(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}
	
	//Mensagem de erro no dialog
	public static void exibirDialogErro(String mensagem) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Erro", mensagem);
		
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	
	//Mensagem de info no dialog
	public static void exibirDialogInfo(String mensagem) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"Info", mensagem);
		
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	
}
