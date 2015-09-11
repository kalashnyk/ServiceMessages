package net.codejava.spring.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.codejava.spring.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDAO userDao;

	public UserDAO getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public boolean isValidUser(String login, String password) throws SQLException {
		return userDao.isValidUser(login, password);
	}

	@Override
	@Transactional
	public void addUser(net.codejava.spring.model.User user) throws SQLException {
		this.userDao.addUser(user);
	}

	@Override
	@Transactional
	public List<net.codejava.spring.model.User> listUsers(String sort, String order) {
		return this.userDao.listUsers(sort, order);
	}

	@Override
	@Transactional
	public boolean isExistUser(String login, String email) throws SQLException {
		return userDao.isExistUser(login, email);
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		this.userDao.deleteUser(id);
	}

	@Override
	@Transactional
	public void changeRole(net.codejava.spring.model.User user) throws SQLException {
		this.userDao.changeRole(user);
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		net.codejava.spring.model.User user;
		List<GrantedAuthority> authorities;
		
		user = userDao.findByUserName(username);
		authorities = buildUserAuthority(user.getRole());
		return buildUserForAuthentication(user, authorities);
	}

	private User buildUserForAuthentication(net.codejava.spring.model.User user, List<GrantedAuthority> authorities) {
		return new User(user.getLogin(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String role) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(role));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

	@Override
	public net.codejava.spring.model.User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public net.codejava.spring.model.User findByContact(String contact) {
		return userDao.findByContact(contact);
	}
	
	@Override
	@Transactional
	public void changeUserPassword(net.codejava.spring.model.User user) throws SQLException{
		userDao.changeUserPassword(user);
	}
	
	@Override
	public net.codejava.spring.model.User getUser(Integer userId){
		return userDao.getUser(userId);
	}
}