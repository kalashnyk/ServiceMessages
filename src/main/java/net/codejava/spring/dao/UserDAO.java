package net.codejava.spring.dao;

import java.sql.SQLException;
import java.util.List;

import net.codejava.spring.model.User;

public interface UserDAO {
	public List<User> listUsers(String sort, String order);
	
	public void addUser(User user) throws SQLException;

	public boolean isValidUser(String login, String password) throws SQLException;
	
	public boolean isExistUser(String login, String email) throws SQLException;
	
	public void deleteUser(Integer id);
	
	public void changeRole(User user) throws SQLException;
	
	public User findByUserName(String login);
	
	public void changeUserPassword(User user) throws SQLException;
	
	public User findByContact(String contact);
	
	public User getUser(Integer userId);
}
