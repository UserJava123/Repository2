package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Buyer;
import entity.Invoice;

public class BuyerDAO {

	private EntityManager em;
	
	public BuyerDAO()
	{
		this.em = JpaInitializer.getEm();
	}
	
	public Buyer getBuyerByName(String name)
	{
		List<Buyer> buyers = (List<Buyer>) em.createQuery("SELECT b FROM entity.Buyer b WHERE name LIKE :name").setParameter("name",name+"%").getResultList();
		if (!buyers.isEmpty())
		{
			return buyers.get(0);
		}
		else return null;

	}
	
	public Buyer getBuyerById(String id)
	{
		List<Buyer> buyers = (List<Buyer>) em.createQuery("SELECT b FROM entity.Buyer b WHERE name LIKE :name").setParameter("name",id).getResultList();
		if (!buyers.isEmpty())
		{
			return buyers.get(0);
		}
		else return null;
	}
	
	public List<Buyer> getBuyers()
	{
		ArrayList<Buyer> list = (ArrayList<Buyer>) em.createQuery("SELECT b FROM entity.Buyer b" ).getResultList();
		return list;
	}
	
	public void addBuyer(Buyer b)
	{
		EntityTransaction et = em.getTransaction();
		et.begin();
		try 
		{
			em.persist(b);
		}
		catch (javax.persistence.EntityExistsException e)
		{
			return;
		}
		
		et.commit();
	}
}
