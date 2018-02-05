package com.bilecki.repository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bilecki.model.ItemModel;
import com.bilecki.repository.entity.ItemEntity;
import com.bilecki.util.EntityManagerProd;

public class ItemRepository implements Serializable{	
	@Inject
	private ItemEntity itemEntity;
		
	private EntityManager entityManager;
	
	/**
	 * Salvar um item no BD
	 * @param itemModel
	 */
	public boolean save(ItemModel item) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		itemEntity = new ItemEntity(item);
		
		try {
			entityManager.persist(itemEntity);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Consultar um item pelo código
	 * @param codigo
	 * @return
	 */
	public ItemEntity getItem(Long codigo) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		return entityManager.find(ItemEntity.class,  codigo);		
	}
	
	/**
	 * Alterar o registro
	 */
	public boolean update(ItemModel item) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		ItemEntity itemE = this.getItem(new Long(item.getCodigo()));	
		
		itemE.setDescricao(item.getDescricao());
		itemE.setValor(item.getValor());
		try {
			entityManager.merge(itemE);		
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Excluir o registro
	 * @param item
	 */
	public boolean delete(int codigo) {
		entityManager = EntityManagerProd.JpaEntityManager();
		
		ItemEntity itemE = this.getItem(new Long(codigo));
		try {
			entityManager.remove(itemE);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * Mostrar todos
	 * @return
	 */
	public List<ItemModel> getAll(){
		List<ItemModel> lista = new ArrayList<ItemModel>();
		
		entityManager = EntityManagerProd.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("ItemEntity.findAll");
		
		for  (ItemEntity itemE: (List<ItemEntity>)query.getResultList() ) {
			ItemModel itemM = new ItemModel(itemE);
			lista.add(itemM);
		}
		
		return lista;		
	}

}
