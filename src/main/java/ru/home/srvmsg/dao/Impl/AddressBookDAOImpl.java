package ru.home.srvmsg.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import ru.home.srvmsg.dao.AddressBookDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.home.srvmsg.model.AddressBook;
import ru.home.srvmsg.model.User;

@Repository
public class AddressBookDAOImpl implements AddressBookDAO {

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

    public AddressBookDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AddressBookDAOImpl() {
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<AddressBook> listAddressBook(User user) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(AddressBook.class);
        crit.add(Restrictions.eq("user", user));
        List<AddressBook> listAddressBook = (List<AddressBook>) crit.list();
        return listAddressBook;
    }

    @Override
    @Transactional
    public Integer addAddressBook(AddressBook addressBook) throws SQLException {
        Integer id = (Integer) sessionFactory.getCurrentSession().save(addressBook);
        return id;
    }

    @Override
    @Transactional
    public void deleteAddressBook(Integer id) {
        AddressBook addressBook = (AddressBook) sessionFactory.getCurrentSession().load(AddressBook.class, id);
        if (null != addressBook) {
            sessionFactory.getCurrentSession().delete(addressBook);
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public void deleteContact(User user) {
        List<AddressBook> addressBooks = (List<AddressBook>)sessionFactory.getCurrentSession().createQuery("from AddressBook where contact=?")
                .setParameter(0, user).list();
        for (AddressBook addressBook : addressBooks) {
            sessionFactory.getCurrentSession().delete(addressBook);
        }
    }
}
