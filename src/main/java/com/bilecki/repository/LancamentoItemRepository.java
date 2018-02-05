package com.bilecki.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bilecki.model.ItemModel;
import com.bilecki.model.LancamentoItemModel;
import com.bilecki.model.LancamentoModel;
import com.bilecki.repository.entity.ItemEntity;
import com.bilecki.repository.entity.LancamentoEntity;
import com.bilecki.repository.entity.LancamentoItemEntity;
import com.bilecki.util.EntityManagerProd;

public class LancamentoItemRepository  implements Serializable{	
	@Inject
	private LancamentoItemEntity lancamentoItemEntity;
		
	private EntityManager entityManager;
	
	/**
	 * Salvar no BD
	 * @param itemModel
	 */
	public boolean save(List<ItemModel> lista, LancamentoEntity lancamento) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		//Salvar
		for(ItemModel i:lista) {
			LancamentoItemEntity li = new LancamentoItemEntity();
			li.setLancamento(lancamento);
			
			ItemEntity item = new ItemEntity(i);			
			li.setItem(item);
			
			try {
				entityManager.persist(li);
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Mostrar todos os itens de um lancamento
	 * @return
	 */
	public List<LancamentoItemModel> getItemByLancamento(LancamentoEntity lancamento){
		List<LancamentoItemModel> lista = new ArrayList<LancamentoItemModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("LancamentoItemEntity.findByLanc");
		query.setParameter("lancamento", lancamento);
		
		for  (LancamentoItemEntity lancItemEnt: (List<LancamentoItemEntity>)query.getResultList() ) {
			LancamentoItemModel lancItemMod = new LancamentoItemModel(lancItemEnt);
			
			lista.add(lancItemMod);
		}
		
		return lista;		
	}
	
	public List<LancamentoItemEntity> getItemByLancamentoE(LancamentoEntity lancamento){		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("LancamentoItemEntity.findByLanc");
		query.setParameter("lancamento", lancamento);
		
		return (List<LancamentoItemEntity>) query.getResultList();
	}

	/**
	 * Excluir o registro
	 * @param item
	 */
	public boolean delete(LancamentoItemEntity le) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		try {
			entityManager.remove(le);	
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(Long id) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		try {
			//Buscar item
			LancamentoItemEntity liRemover = entityManager.find(LancamentoItemEntity.class, id);
			
			entityManager.remove(liRemover);	
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
