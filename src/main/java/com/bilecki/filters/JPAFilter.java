package com.bilecki.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/*
 * Filter é chamado toda vez que é realizada uma requisição ao faces servlet
 */

@WebFilter(servletNames ={ "Faces Servlet" })
public class JPAFilter implements Filter {
	private EntityManagerFactory entityManagerFactory;
	 
	private String persistence_unit_name = "transpobrasil";
	
	public JPAFilter() {
		 
    }
 
	public void destroy() {
 
		this.entityManagerFactory.close();
	}
 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager entityManager =  this.entityManagerFactory.createEntityManager(); 
		request.setAttribute("entityManager", entityManager); 
		entityManager.getTransaction().begin();
		chain.doFilter(request, response);
 
		try {
			//Faz commit na transação
			entityManager.getTransaction().commit(); 
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
		finally{
			entityManager.close();
		}
	}
 
	public void init(FilterConfig fConfig) throws ServletException {
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name); 
	}	
}
