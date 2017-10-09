package ru.home.srvmsg.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ru.home.srvmsg.dao.UserDAO;
import ru.home.srvmsg.dao.exception.UserException;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.home.srvmsg.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDAOImpl() {
    }

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
        return (List<User>) crit.list();
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public boolean isValidUser(String login, String password) throws Exception {
        List<User> users = sessionFactory.getCurrentSession().createQuery("from User where login=? and password=?")
                .setParameter(0, login).setParameter(1, password).list();
        if (users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addUser(User user) throws Exception {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public boolean isExistUser(String login, String email) throws Exception {
        List<User> users = sessionFactory.getCurrentSession().createQuery("from User where login=?").setParameter(0, login).list();
        if (users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void deleteUser(Integer id) throws Exception {
        User userOld = getUser(id);
        if (null == userOld)
            throw new UserException(String.format("User [ID: %d] is not found", id));
        sessionFactory.getCurrentSession().delete(userOld);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void changeRole(User user) throws Exception {
        User userOld = getUser(user.getId());
        if (null == userOld)
            throw new UserException(String.format("User [ID: %d] is not found", user.getId()));
        userOld.setRole(user.getRole());
        sessionFactory.getCurrentSession().update(userOld);
    }

    @Transactional
    @SuppressWarnings("unchecked")
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

    @Transactional
    @SuppressWarnings("unchecked")
    public User findByContact(String contact) {
        List<User> users = new ArrayList<User>();
        try {
            users = sessionFactory.getCurrentSession().createQuery("from User where login like ? or fio like ? or email like ?")
                    .setParameter(0, "%" + contact + "%")
                    .setParameter(1, "%" + contact + "%")
                    .setParameter(2, "%" + contact + "%")
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
    @Transactional
    @SuppressWarnings("unchecked")
    public void changeUserPassword(User user) throws Exception {
        User userOld = getUser(user.getId());
        if (null == userOld)
            throw new UserException(String.format("User [ID: %d] is not found", user.getId()));
        userOld.setPassword(user.getPassword());
        sessionFactory.getCurrentSession().update(userOld);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUser(Integer userId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);
        crit.add(Restrictions.eq("id", userId));

        List<User> listUsers = (List<User>) crit.list();
        if (listUsers.size() == 0)
            return null;

        return listUsers.get(0);
    }
}
