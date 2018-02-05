package com.bilecki.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.primefaces.context.RequestContext;

import com.bilecki.model.ItemModel;
import com.bilecki.model.LancamentoModel;
import com.bilecki.repository.ItemRepository;
import com.bilecki.repository.LancamentoItemRepository;
import com.bilecki.repository.LancamentoRepository;
import com.bilecki.repository.entity.ItemEntity;
import com.bilecki.repository.entity.LancamentoEntity;
import com.bilecki.repository.entity.LancamentoItemEntity;
import com.bilecki.util.Mensagem;

@Named
@ViewScoped
public class LancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private LancamentoModel lancamento;
	private List<ItemModel> itensLancamento;
	private List<LancamentoItemEntity> itensSalvo;
	private List<ItemModel> itensCadastro;
	private ItemModel itemSelecionado;
		
	@Inject
	private ItemRepository itemRepository;
	
	@Inject
	private LancamentoRepository lctoRepository;
	
	@Inject
	private LancamentoItemRepository lcitRepository;

	//Getters and Setters
	public LancamentoModel getLancamento() {
		return lancamento;
	}

	public void setLancamento(LancamentoModel lancamento) {
		this.lancamento = lancamento;
	}

	public List<ItemModel> getItensLancamento() {
		return itensLancamento;
	}

	public void setItensLancamento(List<ItemModel> itensLancamento) {
		this.itensLancamento = itensLancamento;
	}

	public List<ItemModel> getItensCadastro() {
		return itensCadastro;
	}

	public void setItensCadastro(List<ItemModel> itensCadastro) {
		this.itensCadastro = itensCadastro;
	}
	
	public ItemModel getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(ItemModel itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}
	
	public List<LancamentoItemEntity> getItensSalvo() {
		return itensSalvo;
	}

	public void setItensSalvo(List<LancamentoItemEntity> itensSalvo) {
		this.itensSalvo = itensSalvo;
	}

	//Construtor padrão
	public LancamentoBean() {
		lancamento = new LancamentoModel();
		itensLancamento = new ArrayList<ItemModel>();
	}
	

	public void novoItem() {
	}
	
	public void adicionarItem() {
		if (itemSelecionado != null) {
			itensLancamento.add(itemSelecionado);
			setItensLancamento(this.itensLancamento);
			
			//Refaz a soma
			lancamento.setValorTotal(new BigDecimal(itensLancamento.stream().mapToDouble(e->e.getValor().doubleValue()).sum()));
		}
	}
    
	//Deletar	
	public void removeItem(ItemModel item) {		
		//Datatable é atualizado sozinho
		this.itensLancamento.remove(item);
		
		//Refaz a soma
		lancamento.setValorTotal(new BigDecimal(itensLancamento.stream().mapToDouble(e->e.getValor().doubleValue()).sum()));
	}
	
	//Salvar no banco
	public void save() {
		//Comparar as duas datas
		Date dataInicial = Date.from(lancamento.getDataInicial().toInstant(ZoneOffset.UTC));
		Date dataFinal = Date.from(lancamento.getDataFinal().toInstant(ZoneOffset.UTC));
		
		if (dataInicial.after(dataFinal)) {
			Mensagem.mostrarMensagemErro("A data inicial é maior que a final!");
			return;
		}
		
		//Salvar lançamento
		LancamentoEntity lcSalvo = lctoRepository.save(lancamento);
		if (lcSalvo == null) {
			Mensagem.mostrarMensagemErro("Erro ao salvar o lançamento!");
			return;					
		}
		
		//Salvar itens
		boolean salvouItens = lcitRepository.save(itensLancamento, lcSalvo);
		if (!salvouItens) {
			Mensagem.mostrarMensagemErro("Erro ao adicionar os itens!");
			return;			
		}
		
		//Limpar
		init();
		
		//Exibir mensagem
		Mensagem.mostrarMensagem("Lançamento cadastrado com sucesso");	
	}
	
	//Métodos para alteraçao de lançamento
	
	//Atualizar
	public void atualizar(LancamentoModel lancamento) {
		this.lancamento = lancamento;
		itensLancamento = new ArrayList<ItemModel>();
		
		//Carregar itens
		itensSalvo = lcitRepository.getItemByLancamentoE(new LancamentoEntity(lancamento));	
	}
	
	//Remove quando já existe
	public void removeItemQdoLctoExiste(LancamentoItemEntity item) {
		if(item.getOid() == null) {
			itensLancamento.removeIf(p -> p.getCodigo() == item.getItem().getOid().intValue());
		}else {	
			//Remover do banco
			if (!lcitRepository.delete(item.getOid())) {
				Mensagem.mostrarMensagemErro("Ocorreu um erro ao remover esse item");
				return;
			}
		}
		
		if(itensSalvo.size() > 0) {
			//Datatable é atualizado sozinho
			this.itensSalvo.remove(item);	
		}
		
		//Somar
		somarNaAlteracao();

	}
	
	public void adicionarItemQdoLctoExiste() {
		if (itemSelecionado != null) {
			LancamentoItemEntity le = new LancamentoItemEntity();
			le.setItem(new ItemEntity(itemSelecionado));
			le.setLancamento(new LancamentoEntity(lancamento));

			itensSalvo.add(le);
			itensLancamento.add(itemSelecionado);
			
			//Somar valores
			somarNaAlteracao();
		}
	}
	
	//Atualizar
	public void update() {
		//Atualizar as informações do lançamento
		boolean atualizou = lctoRepository.update(lancamento);
		
		if(!atualizou) {			
			Mensagem.mostrarMensagemErro("Ocorreu um erro ao atualizar o lançamento");
			return;
		}

		//Salvar os itens novos no banco
		if(itensLancamento.size() > 0) {
			LancamentoEntity lctoSalvo = lctoRepository.getLcto(lancamento.getOid());
			
			boolean salvouItens = lcitRepository.save(itensLancamento, lctoSalvo);
			if (!salvouItens) {
				Mensagem.mostrarMensagemErro("Erro ao adicionar os itens novos no lançamento!");
				return;			
			}
		}
	}
	
	//faz a soma na alteração
	private void somarNaAlteracao() {
		Double vlrSoma = itensSalvo.stream().mapToDouble(e->e.getItem().getValor().doubleValue()).sum();
		lancamento.setValorTotal(new BigDecimal(vlrSoma));
	}
	
	//Obtem os itens para preenchimento
	@PostConstruct
	public void init(){
		itensCadastro = itemRepository.getAll();
		itemSelecionado = new ItemModel();
		itensLancamento = new ArrayList<ItemModel>();
		itensSalvo = new ArrayList<LancamentoItemEntity>();
		lancamento = new LancamentoModel();
		lancamento.setValorTotal(BigDecimal.ZERO);
	}
}
