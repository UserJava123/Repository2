package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Seller;

public class SellerDAO {

	private EntityManager em;
	
	public SellerDAO()
	{
		this.em = JpaInitializer.getEm();
	}
	
	public Seller getSeller()
	{
		List<Seller> sellers = (List<Seller>) em.createQuery("SELECT s FROM entity.Seller s").getResultList();
		if (!sellers.isEmpty())
		{
			return sellers.get(0);
		}
		else return null;
	}
	
	public void setSeller(Seller s)
	{
		em.createNativeQuery("DELETE FROM seller");
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(s);
		et.commit();
	}
}
