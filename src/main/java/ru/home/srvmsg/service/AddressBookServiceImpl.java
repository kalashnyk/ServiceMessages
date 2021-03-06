package ru.home.srvmsg.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.home.srvmsg.dao.AddressBookDAO;
import ru.home.srvmsg.dao.UserDAO;
import ru.home.srvmsg.model.AddressBook;
import ru.home.srvmsg.model.User;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private AddressBookDAO addressBookDao;

    public UserDAO getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public AddressBookDAO getAddressBookDao() {
        return this.addressBookDao;
    }

    public void setAddressBookDao(AddressBookDAO addressBookDao) {
        this.addressBookDao = addressBookDao;
    }

    @Override
    @Transactional
    public List<AddressBook> listAddressBook(User user) {
        return this.addressBookDao.listAddressBook(user);
    }

    @Override
    @Transactional
    public Integer addAddressBook(AddressBook addressBook) throws SQLException {
        return this.addressBookDao.addAddressBook(addressBook);
    }

    @Override
    @Transactional
    public void deleteAddressBook(Integer id) {
        this.addressBookDao.deleteAddressBook(id);
    }

    @Override
    public User findByContact(String contact) {
        return userDao.findByContact(contact);
    }

    @Override
    @Transactional
    public void deleteContact(User user) {
        this.addressBookDao.deleteContact(user);
    }
}