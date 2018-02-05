package com.bilecki.model;

import com.bilecki.repository.entity.LancamentoItemEntity;

public class LancamentoItemModel { 
	  private Long codigo; 	
	  private LancamentoModel lancamento; 
	  private ItemModel item; 
	 
	  //Construtor padrão da classe
	  public LancamentoItemModel() {}
	  
	  //Construtor default
	  public LancamentoItemModel(LancamentoItemEntity lancitem) {
		  this.codigo = lancitem.getOid();
		  this.lancamento = new LancamentoModel(lancitem.getLancamento());
		  this.item = new ItemModel(lancitem.getItem());
	  }
	  
	  public LancamentoModel getLancamento() { 
	    return lancamento; 
	  } 
	  
	  public void setLancamento(LancamentoModel lancamento) { 
	    this.lancamento = lancamento; 
	  } 
	  
	  public ItemModel getItem() { 
	    return item; 
	  } 
	  
	  public void setItem(ItemModel item) { 
	    this.item = item; 
	  }

	  public Long getCodigo() {
		return codigo;
	  }
	
	  public void setCodigo(Long codigo) {
		this.codigo = codigo;
	  }  
	   
} 