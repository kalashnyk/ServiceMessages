package ru.home.srvmsg.service;

import java.sql.SQLException;
import java.util.List;

import ru.home.srvmsg.model.AddressBook;
import ru.home.srvmsg.model.User;

public interface AddressBookService {
    public List<AddressBook> listAddressBook(User user);

    public Integer addAddressBook(AddressBook addressBook) throws SQLException;

    public void deleteAddressBook(Integer id);

    public User findByContact(String contact);

    public void deleteContact(User user);
}
