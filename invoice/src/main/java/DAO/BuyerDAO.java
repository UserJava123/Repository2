package DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Buyer;
import entity.Invoice;

public class BuyerDAO {

	private EntityManager em;
	private static BuyerDAO instance = new BuyerDAO();
	
	public static BuyerDAO getInstance()
	{
		return instance;
	}
	
	private BuyerDAO()
	{
		this.em = JpaInitializer.getInstance().getEm();
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
	
	public List<Buyer> getBuyersById(String id)
	{
		return (List<Buyer>) em.createQuery("SELECT b FROM entity.Buyer b WHERE name LIKE :name").setParameter("name",id).getResultList();
	}
	
	
	public List<Buyer> getBuyers()
	{
		ArrayList<Buyer> list = (ArrayList<Buyer>) em.createQuery("FROM entity.Buyer" ).getResultList();
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
	
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public List<Buyer> getBuyersByName(String name) {
		// TODO Auto-generated method stub
		return (List<Buyer>) em.createQuery("SELECT b FROM entity.Buyer b WHERE name LIKE :name").setParameter("name",name+"%").getResultList();
	}

	public void deleteBuyer(Buyer b) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		et.begin();
		Object managed = em.merge(b);
		em.remove(managed);
		et.commit();
	}
}
