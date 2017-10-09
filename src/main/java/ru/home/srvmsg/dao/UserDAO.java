package ru.home.srvmsg.dao;

import java.util.List;

import ru.home.srvmsg.model.User;

public interface UserDAO {
    public List<User> listUsers(String sort, String order);

    public void addUser(User user) throws Exception;

    public boolean isValidUser(String login, String password) throws Exception;

    public boolean isExistUser(String login, String email) throws Exception;

    public void deleteUser(Integer id) throws Exception;

    public void changeRole(User user) throws Exception;

    public User findByUserName(String login);

    public void changeUserPassword(User user) throws Exception;

    public User findByContact(String contact);

    public User getUser(Integer userId);
}
