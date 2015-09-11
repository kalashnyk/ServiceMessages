package net.codejava.spring.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.codejava.spring.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SessionFactory sessionFactory;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserDAOImpl() {
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> listUsers(String sort, String order) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
		if (order.toUpperCase().equals("ASC")) {
			crit.addOrder(Order.asc(sort));
		} else if (order.toUpperCase().equals("DESC")) {
			crit.addOrder(Order.desc(sort));
		}
		List<User> listUser = (List<User>) crit.list();
		return listUser;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean isValidUser(String login, String password) throws SQLException {
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession().createQuery("from User where login=? and password=?")
				.setParameter(0, login).setParameter(1, password).list();
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addUser(User user) throws SQLException {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean isExistUser(String login, String email) throws SQLException {
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession().createQuery("from User where login=?").setParameter(0, login).list();
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
		if (null != user) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	@Transactional
	public void changeRole(User user) throws SQLException {
		User userOld = (User) sessionFactory.getCurrentSession().load(User.class, user.getId());
		if (null != userOld) {
			userOld.setRole(user.getRole());
			sessionFactory.getCurrentSession().update(userOld);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public User findByUserName(String login) {
		List<User> users = new ArrayList<User>();
		try {
			users = sessionFactory.getCurrentSession().createQuery("from User where login=?").setParameter(0, login)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public User findByContact(String contact) {
		List<User> users = new ArrayList<User>();
		try {
			users = sessionFactory.getCurrentSession().createQuery("from User where login like ? or fio like ? or email like ?")
					.setParameter(0, "%"+contact+"%")
					.setParameter(1, "%"+contact+"%")
					.setParameter(2, "%"+contact+"%")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void changeUserPassword(User user) throws SQLException {
		User userOld = (User) sessionFactory.getCurrentSession().load(User.class, user.getId());
		if (null != userOld) {
			userOld.setPassword(user.getPassword());
			sessionFactory.getCurrentSession().update(userOld);
		}
	}
	
	@Override
	public User getUser(Integer userId){
		User user = (User) sessionFactory.getCurrentSession().load(User.class, userId);
		return user;
	}
}
