package net.codejava.spring;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.codejava.spring.model.AddressBook;
import net.codejava.spring.model.User;
import net.codejava.spring.model.state.AddressBookState;
import net.codejava.spring.service.AddressBookService;

@Controller
public class AddressBookController {

	private static final Logger logger = Logger.getLogger(AddressBookController.class);

	@Autowired
	private AddressBookService addressBookService;

	@RequestMapping(value = "/addcontact", method = RequestMethod.POST)
	@ResponseBody
	public AddressBookState addContact(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Add contact");

		AddressBookState addressBookState = new AddressBookState();

		String contact = request.getParameter("contact");
		User user_contact = addressBookService.findByContact(contact);

		if (user_contact == null) {
			addressBookState.setError((byte) 1);
			addressBookState.setMessage("Contact is not found");
			logger.error("Contact is not found");
			return addressBookState;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = addressBookService.findByContact(auth.getName());

		AddressBook addressBook = new AddressBook();
		addressBook.setContact(user_contact);
		addressBook.setUser(user);

		try {
			Integer id = addressBookService.addAddressBook(addressBook);
			addressBookState.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			addressBookState.setError((byte) 1);
			addressBookState.setMessage(e.getMessage());
			logger.error(e.getMessage());
			return addressBookState;
		}

		addressBookState.setFio(user_contact.getFio());
		addressBookState.setLogin(user_contact.getLogin());
		addressBookState.setUser_id(user_contact.getId());
		addressBookState.setError((byte) 0);
		addressBookState.setMessage("Contact is added");
		logger.info("Contact is added");

		return addressBookState;
	}
	
	@RequestMapping(value = "/deletecontact", method = RequestMethod.POST)
	@ResponseBody
	public AddressBookState deleteContact(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Delete contact");

		AddressBookState addressBookState = new AddressBookState();

		String id = request.getParameter("id");
		addressBookService.deleteAddressBook(Integer.valueOf(id));

		addressBookState.setError((byte) 0);
		addressBookState.setMessage("Contact is deleted");
		logger.info("Contact is deleted");

		return addressBookState;
	}
}
