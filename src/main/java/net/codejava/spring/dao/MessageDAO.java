package net.codejava.spring.dao;

import java.sql.SQLException;
import java.util.List;

import net.codejava.spring.model.Message;
import net.codejava.spring.model.User;


public interface MessageDAO {
	public List<Message> listMessage(User user, String sort, String order);
	
	public void addMessage(Message message) throws SQLException;

	public void deleteMessage(Integer id);
	
	public void deleteMessageByUser(User user);
	
	public Message getMessage(Integer id) throws SQLException;
}
