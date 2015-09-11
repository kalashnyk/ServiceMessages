package net.codejava.spring.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.codejava.spring.dao.MessageDAO;
import net.codejava.spring.model.Message;
import net.codejava.spring.model.User;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDao;

	public MessageDAO getMessageDao() {
		return this.messageDao;
	}

	public void setMessageDao(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	@Transactional
	public List<Message> listMessage(User user, String sort, String order) {
		return this.messageDao.listMessage(user, sort, order);
	}

	@Override
	@Transactional
	public void addMessage(Message message) throws SQLException {
		this.messageDao.addMessage(message);
	}
	
	@Override
	@Transactional
	public void deleteMessage(Integer id) {
		this.messageDao.deleteMessage(id);
	}

	@Override
	@Transactional
	public void deleteMessageByUser(User user){
		this.messageDao.deleteMessageByUser(user);
	}
	
	@Override
	@Transactional
	public Message getMessage(Integer id) throws SQLException {
		return this.messageDao.getMessage(id);
	}
}