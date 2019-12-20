package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaInitializer {
//TODO
//zamiast klasy i tableli seller zdefiniowac singleton ze stałymi
//
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("worker");
	EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("accountant");
	EntityManagerFactory emf3 = Persistence.createEntityManagerFactory("boss");
	
	private static final JpaInitializer INSTANCE = new JpaInitializer();
	
	private String compatibilityLevel = "boss";

	private JpaInitializer()
	{
		
	}
	
	public static JpaInitializer getInstance()
	{
		return INSTANCE;
	}
	
	//TODO stworzyc metode zwracajaca entity managera z odpowiednimi uprawnieniami
	// w zaleznosci od compatibilityLevel
	public EntityManager getEm()
	{
		if(compatibilityLevel.equals("worker")) {
		EntityManager em = emf.createEntityManager();
		return em;	
		}
		else if(compatibilityLevel.equals("accountant"))
		{
			EntityManager em = emf2.createEntityManager();
			return em;
		}
		else if(compatibilityLevel.equals("boss"))
		{
			EntityManager em = emf3.createEntityManager();
			return em;
		}
		else return null;
	}
	
	public String getCompatibilityLevel() {
		return compatibilityLevel;
	}

	public void setCompatibilityLevel(String compatibilityLevel) {
		this.compatibilityLevel = compatibilityLevel;
	}
	
	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
}