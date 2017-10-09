package ru.home.srvmsg;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.home.srvmsg.model.User;
import ru.home.srvmsg.service.UserService;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }

        return error;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView displayRegistration(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Display the page registration");
        ModelAndView model = new ModelAndView("registration");
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView executeRegistration(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Execute the page registration");

        ModelAndView model = new ModelAndView("registration");

        String fio = null;
        String login = null;
        String email = null;
        String password = null;

        try {
            fio = new String(request.getParameter("fio").getBytes("iso-8859-1"), "UTF-8");
            login = new String(request.getParameter("login").getBytes("iso-8859-1"), "UTF-8");
            email = new String(request.getParameter("email").getBytes("iso-8859-1"), "UTF-8");
            password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        if (fio == null || fio.isEmpty() || login == null || login.isEmpty() || email == null || email.isEmpty()
                || password == null || password.isEmpty()) {

            model.addObject("error", "Fill all fields of a form");
            logger.info("Not all fields are filled");
        } else {
            try {
                if (userService.isExistUser(login, email)) {
                    model.addObject("error", "The user with such login or email exists");
                    logger.info("The user with such login or email exists");
                } else {
                    User user = new User();
                    user.setFio(fio);
                    user.setEmail(email);
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setEnabled(true);
                    user.setRole("ROLE_USER");
                    userService.addUser(user);

                    model.addObject("msg", "The user is successfully created");
                    logger.info("The user is successfully created");
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addObject("error", "Registration error.");
                logger.info("Registration error");
            }
        }

        return model;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }
}
