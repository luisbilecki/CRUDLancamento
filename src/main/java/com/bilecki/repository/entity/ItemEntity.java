package com.bilecki.repository.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bilecki.model.ItemModel;

@Entity
@Table(name="item")
@NamedQueries({
	@NamedQuery(name="ItemEntity.findAll", query = "SELECT i FROM ItemEntity i")
})

public class ItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "oid")
	private Long oid;
	
	@Column(name = "descricao", length = 255)
	private String descricao;

	@Column(name = "valor", precision = 8, scale = 2)
	private BigDecimal valor;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	private List<LancamentoItemEntity> itensLcto;
	
	//Construtor default
	public ItemEntity() {}
	
	//Construtor personalizado
	public ItemEntity(ItemModel im) {
		if (im.getCodigo() != null)
			this.oid = new Long(im.getCodigo());
		this.descricao = im.getDescricao();
		this.valor = im.getValor();
	}
	
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<LancamentoItemEntity> getItensLcto() {
		return itensLcto;
	}

	public void setItensLcto(List<LancamentoItemEntity> itensLcto) {
		this.itensLcto = itensLcto;
	}
	
}
