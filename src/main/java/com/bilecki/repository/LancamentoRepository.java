package com.bilecki.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bilecki.model.ItemModel;
import com.bilecki.model.LancamentoModel;
import com.bilecki.repository.entity.ItemEntity;
import com.bilecki.repository.entity.LancamentoEntity;
import com.bilecki.util.EntityManagerProd;

public class LancamentoRepository  implements Serializable{	
	@Inject
	private LancamentoEntity lancamentoEntity;
		
	private EntityManager entityManager;
	
	/**
	 * Mostrar todos
	 * @return
	 */
	public List<LancamentoModel> getAll(){
		List<LancamentoModel> lista = new ArrayList<LancamentoModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("LancamentoEntity.findAll");
		
		for  (LancamentoEntity lctoE: (List<LancamentoEntity>)query.getResultList() ) {
			LancamentoModel lctoM = new LancamentoModel(lctoE);
			
			lista.add(lctoM);
		}
		
		return lista;		
	}
	
	/**
	 * Salvar um item no BD
	 * @param itemModel
	 */
	public LancamentoEntity save(LancamentoModel lc) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		//Salvar
		LancamentoEntity lancamentoEntity = new LancamentoEntity(lc);
		
		entityManager.persist(lancamentoEntity);
		entityManager.flush();
		
		return lancamentoEntity;
	}
	
	/**
	 * Consultar um lcto pelo código
	 * @param codigo
	 * @return
	 */
	public LancamentoEntity getLcto(Long codigo) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		return entityManager.find(LancamentoEntity.class,  codigo);		
	}
	
	/**
	 * Excluir o registro
	 * @param item
	 */
	public boolean delete(int codigo) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		LancamentoEntity itemE = this.getLcto(new Long(codigo));
		try {
			entityManager.remove(itemE);	
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Alterar o registro
	 */
	public boolean update(LancamentoModel lcto) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		LancamentoEntity lctoE = this.getLcto(lcto.getOid());	
		
		lctoE.setDataFinal(lcto.getDataFinal());
		lctoE.setDataInicial(lcto.getDataInicial());
		lctoE.setObservacao(lcto.getObservacao());
		lctoE.setValorTotal(lcto.getValorTotal());
		
		try {
			entityManager.merge(lctoE);	
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
