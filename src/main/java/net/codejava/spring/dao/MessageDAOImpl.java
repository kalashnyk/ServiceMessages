package net.codejava.spring.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.codejava.spring.model.Message;
import net.codejava.spring.model.User;

@Repository
public class MessageDAOImpl implements MessageDAO {

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

	public MessageDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public MessageDAOImpl() {

	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Message> listMessage(User user, String sort, String order) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Message.class);

		if (!user.isAdmin()) {
			crit.add(Restrictions.eq("to_user", user));
		}

		if (order.toUpperCase().equals("ASC")) {
			crit.addOrder(Order.asc(sort));
		} else if (order.toUpperCase().equals("DESC")) {
			crit.addOrder(Order.desc(sort));
		}

		List<Message> listMessage = (List<Message>) crit.list();
		return listMessage;
	}

	@Override
	@Transactional
	public void addMessage(Message message) throws SQLException {
		sessionFactory.getCurrentSession().save(message);
	}

	@Override
	@Transactional
	public void deleteMessage(Integer id) {
		Message message = (Message) sessionFactory.getCurrentSession().load(Message.class, id);
		if (null != message) {
			sessionFactory.getCurrentSession().delete(message);
		}
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void deleteMessageByUser(User user) {
		List<Message> messages = new ArrayList<Message>();

		messages = (List<Message>) sessionFactory.getCurrentSession().createQuery("from Message where from_user=? or to_user=?")
				.setParameter(0, user).setParameter(1, user).list();

		for (Message message : messages) {
			sessionFactory.getCurrentSession().delete(message);
		}
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Message getMessage(Integer id) throws SQLException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Message.class);
		crit.add(Restrictions.eq("id", id));

		List<Message> listMessage = (List<Message>) crit.list();

		if (listMessage.size() > 0) {
			return listMessage.get(0);
		} else {
			return null;
		}
	}
}
