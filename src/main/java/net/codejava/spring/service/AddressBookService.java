package net.codejava.spring.service;

import java.sql.SQLException;
import java.util.List;

import net.codejava.spring.model.AddressBook;
import net.codejava.spring.model.User;

public interface AddressBookService {
	public List<AddressBook> listAddressBook(User user);

	public Integer addAddressBook(AddressBook addressBook) throws SQLException;

	public void deleteAddressBook(Integer id);
	
	public User findByContact(String contact);
	
	public void deleteContact(User user);
}
