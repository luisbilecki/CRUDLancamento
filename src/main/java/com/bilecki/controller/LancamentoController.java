package com.bilecki.controller;
 
import java.util.ArrayList;
import java.util.List;
 
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
 
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import com.bilecki.model.ItemModel;
import com.bilecki.model.LancamentoItemModel;
import com.bilecki.model.LancamentoModel;
import com.bilecki.repository.ItemRepository;
import com.bilecki.repository.LancamentoItemRepository;
import com.bilecki.repository.LancamentoRepository;
import com.bilecki.repository.entity.LancamentoEntity;
import com.bilecki.repository.entity.LancamentoItemEntity;
import com.bilecki.util.Mensagem;
 
@Named(value="lancamentoController")
@RequestScoped
public class LancamentoController {  

	@Inject  
	private LancamentoRepository lancamentoRepository;
	
	@Inject
	private ItemRepository itemRepository;
	
	@Inject
	private LancamentoModel lancamento;  
	
	@Inject
	private LancamentoItemRepository lcitRepository;
	
	//Listar
	private List<LancamentoModel> lista = null;
	private List<LancamentoItemModel> listaItensSalvos;
	
	public List<LancamentoModel> getLista() {
		return lista;
	}
	
	public LancamentoModel getLancamento() {
		return lancamento;
	}
	
	public void setLancamento(LancamentoModel lancamento) {
		this.lancamento = lancamento;
	}
	
	public List<LancamentoItemModel> getListaItensSalvos() {
		return listaItensSalvos;
	}
	
	public void setListaItensSalvos(List<LancamentoItemModel> listaItensSalvos) {
		this.listaItensSalvos = listaItensSalvos;
	}
	
	
	/**
	* Remover o lançamento e também os seus itens
	*/
	public void removerLancamento(LancamentoModel lancamento) {
		//Carregar os itens também
		List<LancamentoItemEntity> listaRemover = lcitRepository.getItemByLancamentoE(new LancamentoEntity(lancamento));
	
		//Remover os itens	
		boolean removeu = true;
		for(LancamentoItemEntity le:listaRemover) {
			removeu = lcitRepository.delete(le);
			if (!removeu) {
				Mensagem.exibirDialogErro("Erro ao remover o lançamento.");
				return;
			}
		}
		
		//Remover o lançamento
		if (!lancamentoRepository.delete(lancamento.getOid().intValue())) {
			Mensagem.exibirDialogErro("Ocorreu um erro ao remover o lançamento!");
			return;
		}
		
		//Remove da lista
		this.lista.remove(lancamento);
		
		Mensagem.exibirDialogInfo("O lançamento foi removido!");		
	}
	
	/**
	* Permite visualizar
	* @param lancamento
	*/
	public void mostrarLancamento(LancamentoModel lancamento) {
		this.lancamento = lancamento;
	
		//Carregar itens
		listaItensSalvos = lcitRepository.getItemByLancamento(new LancamentoEntity(lancamento)); 
	}
	
	//Carrega a lista
	@PostConstruct
	public void init(){
		this.lista = lancamentoRepository.getAll();
		this.listaItensSalvos = new ArrayList<LancamentoItemModel>();
	}  
	
	public void handleLancamento(LancamentoModel lc) {
		this.lancamento = lc;
	}
}