package ru.home.srvmsg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.home.srvmsg.dao.MessageDAO;
import ru.home.srvmsg.model.Message;
import ru.home.srvmsg.model.User;

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
    public void addMessage(Message message) throws Exception {
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
    public Message getMessage(Integer id) throws Exception {
        return this.messageDao.getMessage(id);
    }
}