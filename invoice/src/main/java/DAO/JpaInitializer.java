package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaInitializer {

	public static EntityManager getEm()
	{
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("Lab1");
		EntityManager em = emf.createEntityManager();
		return em;	
	}
}