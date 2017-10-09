package ru.home.srvmsg;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ru.home.srvmsg.model.AddressBook;
import ru.home.srvmsg.model.Message;
import ru.home.srvmsg.model.User;
import ru.home.srvmsg.model.request.NewMessage;
import ru.home.srvmsg.model.request.UserChangePassword;
import ru.home.srvmsg.model.state.MessageState;
import ru.home.srvmsg.model.state.UserState;
import ru.home.srvmsg.service.AddressBookService;
import ru.home.srvmsg.service.MessageService;
import ru.home.srvmsg.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MessagesController {

    private static final Logger logger = Logger.getLogger(MessagesController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @RequestMapping(value = "/messages")
    public ModelAndView listMessages(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Get list messages");

        ModelAndView model = new ModelAndView("messages");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(auth.getName());

        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        if (sort == null) {
            sort = "from_user";
        }
        if (order == null) {
            order = "asc";
        }

        logger.info(String.format("Sort by %s %s", sort, order));

        List<Message> listMessages = messageService.listMessage(user, sort, order);
        List<AddressBook> listAddressBook = addressBookService.listAddressBook(user);

        model.addObject("messageList", listMessages);
        model.addObject("addressBookList", listAddressBook);

        logger.info(String.format("Number of messages %d", listMessages.size()));

        if (order.equals("desc")) {
            order = "asc";
        } else {
            order = "desc";
        }
        model.addObject("order", order);
        model.addObject("isAdmin", user.isAdmin());

        return model;
    }

    @RequestMapping(value = "/deletemessage/{messageId}")
    public String deleteUser(@PathVariable("messageId") Integer userId) {
        logger.info("Delete the message");
        messageService.deleteMessage(userId);

        return "redirect:/messages";
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public UserState changePassword(@RequestBody UserChangePassword userCP) {
        logger.info(String.format("Change password"));
        UserState userState = new UserState();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(auth.getName());

        if (!user.getPassword().equals(userCP.getCur_password())) {
            userState.setError((byte) 1);
            userState.setMessage("The current password is correct not");
            logger.info("The current password is correct not");
        } else {
            user.setPassword(userCP.getNew_password());
            try {
                userService.changeUserPassword(user);
                userState.setId(user.getId());
                userState.setError((byte) 0);
                userState.setMessage("The password of the user is changed");
                logger.info("The password of the user is changed");
            } catch (Exception e) {
                e.printStackTrace();
                userState.setError((byte) 1);
                userState.setMessage("The password of the user is not changed");
                logger.info("The password of the user is not changed");
            }
        }

        return userState;
    }

    @RequestMapping(value = "/getmsgcontents", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public MessageState getMsg(@RequestBody Message message) {
        logger.info(String.format("To receive message contents"));
        MessageState messageState = new MessageState();

        try {
            message = messageService.getMessage(message.getId());
            messageState.setId(message.getId());
            messageState.setError((byte) 0);
            messageState.setContent(message.getMessage());
            messageState.setMessage("It was succeeded to receive message contents");
            logger.info("It was succeeded to receive message contents");
        } catch (Exception e) {
            e.printStackTrace();
            messageState.setError((byte) 1);
            messageState.setMessage("It wasn't succeeded to receive message contents");
            logger.info("It wasn't succeeded to receive message contents");
        }

        return messageState;
    }

    @RequestMapping(value = "/addmessage", method = RequestMethod.POST, headers = "Content-Type=application/json")
    @ResponseBody
    public MessageState addMessage(@RequestBody NewMessage newMessage) {
        logger.info(String.format("Add new message"));
        MessageState messageState = new MessageState();

        Message message = new Message();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User from_user = userService.findByUserName(auth.getName());
        User to_user = userService.findByUserName(newMessage.getTo_user());

        message.setFrom_user(from_user);
        message.setTo_user(to_user);
        message.setDate_time(new Date());
        message.setSubject(newMessage.getSubject());
        message.setMessage(newMessage.getMessage());

        try {
            messageService.addMessage(message);
            messageState.setError((byte) 0);
            messageState.setMessage("The message is added");
            logger.info("The message is added");
        } catch (Exception e) {
            e.printStackTrace();
            messageState.setError((byte) 1);
            messageState.setMessage("The message is not added");
            logger.info("The message is not added");
        }

        return messageState;
    }

}
