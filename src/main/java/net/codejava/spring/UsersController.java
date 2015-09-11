package net.codejava.spring;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.codejava.spring.model.User;
import net.codejava.spring.model.state.UserState;
import net.codejava.spring.service.AddressBookService;
import net.codejava.spring.service.MessageService;
import net.codejava.spring.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UsersController {

	private static final Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AddressBookService addressBookService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public ModelAndView listUsers(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Get list users");

		String sort = request.getParameter("sort");
		String order = request.getParameter("order");

		if (sort == null) {
			sort = "fio";
		}
		if (order == null) {
			order = "asc";
		}

		logger.info(String.format("Sort by %s %s", sort, order));

		List<User> listUsers = userService.listUsers(sort, order);
		ModelAndView model = new ModelAndView("users");
		model.addObject("userList", listUsers);

		logger.info(String.format("Number of users %d", listUsers.size()));

		if (order.equals("desc")) {
			order = "asc";
		} else {
			order = "desc";
		}
		model.addObject("order", order);

		return model;
	}

	@RequestMapping(value = "/admin/add", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public UserState addUser(@RequestBody User user) {
		logger.info("Add the user");
		UserState userState = new UserState();

		try {
			logger.info("Check of the user");
			if (userService.isExistUser(user.getLogin(), user.getEmail())) {
				userState.setError((byte) 1);
				userState.setMessage("The user with such login or email exists");
				logger.info("The user with such login or email exists");
			} else {
				userService.addUser(user);
				userState.setId(user.getId());
				userState.setError((byte) 0);
				userState.setMessage("The user is successfully created");
				logger.info("The user is successfully created");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			userState.setError((byte) 1);
			userState.setMessage("Error of creation of the user");
			logger.error("Error of creation of the user");
		}

		return userState;
	}

	@RequestMapping(value = "/admin/delete/{userId}")
	public String deleteUser(@PathVariable("userId") Integer userId) {
		logger.info("Delete the user");
		
		User user = userService.getUser(userId);
		
		addressBookService.deleteContact(user);
		messageService.deleteMessageByUser(user);
		
		userService.deleteUser(userId);
		
		return "redirect:/admin/users";
	}

	@RequestMapping(value = "/admin/changerole", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public UserState changeRole(@RequestBody User user) {
		logger.info(String.format("Change a role to the user ID: %d", user.getId()));
		UserState userState = new UserState();

		try {
			userService.changeRole(user);
			userState.setId(user.getId());
			userState.setError((byte) 0);
			userState.setMessage("The role of the user is changed");
			logger.info("The role of the user is changed");
		} catch (SQLException e) {
			e.printStackTrace();
			userState.setError((byte) 1);
			userState.setMessage("The role of the user is not changed");
			logger.info("The role of the user is not changed");
		}

		return userState;
	}
}
