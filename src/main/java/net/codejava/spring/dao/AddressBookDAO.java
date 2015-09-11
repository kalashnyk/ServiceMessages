package net.codejava.spring.dao;

import java.sql.SQLException;
import java.util.List;

import net.codejava.spring.model.AddressBook;
import net.codejava.spring.model.User;

public interface AddressBookDAO {
	public List<AddressBook> listAddressBook(User user);
	
	public Integer addAddressBook(AddressBook addressBook) throws SQLException;
	
	public void deleteAddressBook(Integer id);
	
	public void deleteContact(User user);
}
