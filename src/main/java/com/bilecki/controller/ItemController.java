package com.bilecki.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.primefaces.context.RequestContext;

import com.bilecki.model.ItemModel;
import com.bilecki.repository.ItemRepository;
import com.bilecki.repository.entity.ItemEntity;
import com.bilecki.util.Mensagem;

@Named(value="itemController")
@RequestScoped
public class ItemController {	
	@Inject
	private ItemRepository itemRepository;
	
	@Inject
	private ItemModel item;
	
	//Listar
	private List<ItemModel> lista = null;
	
	public List<ItemModel> getLista() {
		return lista;
	}

	public ItemModel getItem() {
		return item;
	}
	
	public void setItem(ItemModel item) {
		this.item = item;
	}
	
	//Salvar
	public void save() {
		boolean salvou = itemRepository.save(this.item);
		
		if(salvou) {
			this.item = null;
			Mensagem.mostrarMensagem("Registro salvo com sucesso!");
		}else {
			Mensagem.mostrarMensagemErro("Ocorreu um erro ao salvar o item!");
		}
	}
	
	//Carrega info de um item
	public void carregarItem(ItemModel item) {
		this.item = item;
	}
	
	//Editar
	public void update() {		
		boolean atualizou = itemRepository.update(this.item);
		
		//Atualizar lista
		if (atualizou) {
			this.init();
		}else {
			Mensagem.mostrarMensagemErro("Ocorreu um erro ao atualizar o item!");
		}
	}
		
	//Deletar	
	public void delete(ItemModel item) {
		//Buscar o item
		ItemEntity i = itemRepository.getItem(new Long(item.getCodigo()));
		Hibernate.initialize(i.getItensLcto());
		
		//Ver se ele está em algum lançamento
		if (i.getItensLcto().size() > 0) {
			//Exibir mensagem
			Mensagem.exibirDialogErro("Não foi possível excluir o item.\n"
											+ "Existem lançamentos que dependem deste item.");
			
			//Retornar para a listagem			
			return;
		}
		
		boolean removeu = itemRepository.delete(item.getCodigo().intValue());
		
		if (!removeu) {
			Mensagem.exibirDialogErro("Ocorreu um erro ao remover o item!");
			return;
		}
		
		//Datatable é atualizado sozinho
		this.lista.remove(item);
		

		Mensagem.exibirDialogInfo("O item foi removido!");
	}
	
	//Carrega a lista de itens
	@PostConstruct
	public void init(){
		this.lista = itemRepository.getAll();
	}
}
