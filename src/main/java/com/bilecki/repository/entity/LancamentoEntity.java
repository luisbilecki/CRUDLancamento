package com.bilecki.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.bilecki.model.LancamentoModel;

@Table(name="lancamento") 
@NamedQueries({
	@NamedQuery(name="LancamentoEntity.findAll", query = "SELECT l FROM LancamentoEntity l")
})
@Entity 
public class LancamentoEntity { 
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  @Column(name = "oid") 
  private Long oid; 
   
  @Column(name = "dt_inicial") 
  private LocalDateTime  dataInicial; 
   
  @Column(name = "dt_final") 
  private LocalDateTime  dataFinal; 
   
  @Column(name = "observacao", length = 1000) 
  private String observacao; 
   
  @Column(name = "vl_total", precision = 8, scale = 2) 
  private BigDecimal valorTotal; 
  
  //Construtor default
  public LancamentoEntity() {}
  
  //Construtor personalizado
  public LancamentoEntity(LancamentoModel l) {
	  this.dataInicial = l.getDataInicial();
	  this.dataFinal = l.getDataFinal();
	  this.observacao = l.getObservacao();
	  this.oid = l.getOid();
	  this.valorTotal = l.getValorTotal();
  }
 
  public Long getOid() { 
    return oid; 
  } 
 
  public void setOid(Long oid) { 
    this.oid = oid; 
  } 
 
  public LocalDateTime getDataInicial() { 
    return dataInicial; 
  } 
 
  public void setDataInicial(LocalDateTime dataInicial) { 
    this.dataInicial = dataInicial; 
  } 
 
  public LocalDateTime getDataFinal() { 
    return dataFinal; 
  } 
 
  public void setDataFinal(LocalDateTime dataFinal) { 
    this.dataFinal = dataFinal; 
  } 
 
  public String getObservacao() { 
    return observacao; 
  } 
 
  public void setObservacao(String observacao) { 
    this.observacao = observacao; 
  } 
 
  public BigDecimal getValorTotal() { 
    return valorTotal; 
  } 
 
  public void setValorTotal(BigDecimal valorTotal) { 
    this.valorTotal = valorTotal; 
  } 
   
   
} 