package DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Buyer;
import entity.Invoice;
import entity.Position;

public class InvoiceDAO {

	private EntityManager em;
	private static InvoiceDAO instance = new InvoiceDAO();
	
	public static InvoiceDAO getInstance()
	{
		return instance;
	}
	
	private InvoiceDAO()
	{
		this.em = JpaInitializer.getInstance().getEm();
	}
	
	public List<Invoice> getInvoices()
	{
		return (List<Invoice>) em.createQuery("FROM entity.Invoice").getResultList();
	}
	
	public List<Invoice> getInvoicesByBuyerId(String id)
	{
		return (List<Invoice>) em.createQuery("SELECT i FROM entity.Invoice i WHERE i.buyer.id LIKE :buyer").setParameter("buyer",id+"%").getResultList();
	}
	
	public List<Invoice> getInvoicesByBuyerName(String name)
	{
		return (List<Invoice>) 
				em.createQuery("SELECT i FROM entity.Invoice i INNER JOIN entity.Buyer b ON b.id=i.buyer WHERE b.name LIKE :buyer")
				.setParameter("buyer",name+"%").getResultList();
	}
	
	public Invoice getInvoiceById(String id)
	{
		List<Invoice> invoices = (List<Invoice>) em.createQuery("FROM entity.Invoice i WHERE id=:id").setParameter("id",new BigDecimal(id)).getResultList();
		if (!invoices.isEmpty())
		{
			return invoices.get(0);
		}
		else return null;
	}
	
	public List<Invoice> getInvoicesById(String id)
	{
		return (List<Invoice>) em.createQuery("FROM entity.Invoice i WHERE cast(id as string) LIKE :id").setParameter("id","%"+id+"%").getResultList();
	}
	
	public List<Invoice> getInvoicesByDate(String date)
	{
		return (List<Invoice>) em.createQuery("SELECT i FROM entity.Invoice i WHERE cast(i.dateOfInvoice as string) LIKE :date").setParameter("date",date+"%").getResultList();
	}
	
	public void addInvoice(Invoice i)
	{
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			em.persist(i);
		}
		catch(javax.persistence.EntityExistsException e)
		{
			return;
		}
		et.commit();
	}
	
	public BigDecimal getMaxNumber()
	{
		List list = (em.createNativeQuery("SELECT MAX(Number) FROM Invoice WHERE "
				+ "YEAR(dateofinvoice)=YEAR(CURDATE())").getResultList());
		System.out.println(list);
		if (list.isEmpty() || list.get(0)==null) return new BigDecimal(LocalDate.now().getYear());
		else {
			System.out.println(list.get(0));
			return new BigDecimal(list.get(0).toString());
		}
	}
	
	public void deleteInvoice(Invoice i)
	{
		EntityTransaction et = em.getTransaction();
		et.begin();
		Object managed = em.merge(i);
		em.remove(managed);
		et.commit();
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
