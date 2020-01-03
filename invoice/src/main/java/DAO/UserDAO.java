package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Invoice;
import entity.User;

public class UserDAO {

	private EntityManager em;
	private static UserDAO instance = new UserDAO();
	
	public static UserDAO getInstance()
	{
		return instance;
	}
	
	private UserDAO()
	{
		this.em = JpaInitializer.getInstance().getEm();
	}
	
	public User getUserByLogin(String login, String password)
	{
		return (User) em.createQuery("FROM entity.User WHERE login=:login AND password=SHA(:password)")
				.setParameter("login", login)
				.setParameter("password", password)
				.getResultList().get(0);
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void addUser(User u)
	{
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			em.createNativeQuery("INSERT INTO user VALUES(:login,SHA1(:password),:type)")
			.setParameter("login", u.getLogin())
			.setParameter("password", u.getPassword())
			.setParameter("type", u.getType()).executeUpdate();
		}
		catch(javax.persistence.EntityExistsException e)
		{
			return;
		}
		et.commit();
	}

	public User getUserByLogin(String login) {
		// TODO Auto-generated method stub

		return (User) em.createQuery("FROM entity.User WHERE login=:login")
				.setParameter("login", login)
				.getResultList().get(0);
	}

	public void deleteUser(User u) {
		// TODO Auto-generated method stub
		EntityTransaction et = em.getTransaction();
		et.begin();
		Object managed = em.merge(u);
		em.remove(managed);
		et.commit();
	}

	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return (List<User>) em.createQuery("FROM entity.User")
				.getResultList();
	}
}
