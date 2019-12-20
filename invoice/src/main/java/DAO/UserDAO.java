package DAO;

import javax.persistence.EntityManager;

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
}
