package com.engineering.shop.security.authentication;

import com.engineering.shop.security.GenericResponse;
import com.engineering.shop.security.PasswordDto;
import com.engineering.shop.security.UserService;
import com.engineering.shop.security.jwt.JwtResponse;
import com.engineering.shop.security.jwt.JwtUtil;
import com.engineering.shop.users.User;
import com.engineering.shop.users.UserRepository;
import com.engineering.shop.users.UserSignInPOJO;
import com.engineering.shop.users.UserSignUpPOJO;
import com.engineering.shop.users.UserTokenInformation;
import com.engineering.shop.users.exceptions.BadUserCredentialsException;
import com.engineering.shop.users.exceptions.UserDisabledException;
import com.engineering.shop.users.exceptions.UserException;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;
    private PasswordEncoder encoder; 
    private UserRepository userRepository;
    private Environment env;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, PasswordEncoder encoder, UserRepository userRepository, Environment env) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.env = env;
    }
    
    public AuthenticationController() {
    	super();
    }

    @PostMapping(path = "signUp", consumes = "application/json")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody UserSignUpPOJO accountDetails) {
        return createUserAccount(accountDetails) ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity(HttpStatus.CONFLICT);
    }
    
    @PutMapping(path = "modify", consumes = "application/json")
    @ResponseBody
    public ResponseEntity modify(@RequestBody User modifyDetails) {
    	System.out.println("AuthenticationController modyfikacja: "+modifyDetails);
    	userService.updateUser(modifyDetails);
    	return null;
    }

    @PostMapping(path = "logIn", consumes = "application/json")
    @ResponseBody
    public ResponseEntity logIn(@RequestBody UserSignInPOJO authenticationDetails) throws Exception {
        try {
        	//System.out.println(authenticationDetails.getPassword());
        	String pass = authenticationDetails.getPassword();
        	//System.out.println("pass=" +authenticationDetails.getPassword());
        	String passwd = encoder.encode(pass);
        	System.out.println("pass encoded =" +passwd);
        	//authenticationDetails.setPassword(encoder.encode(authenticationDetails.getPassword()));
            authenticate(authenticationDetails.getEmail(), authenticationDetails.getPassword());
        	//authenticate(authenticationDetails.getEmail(), passwd);
        } catch (UserException e) {
        	System.out.println("nieautoryzowany 403");
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }
        
        
        

        UserTokenInformation userTokenInformation = userService.getUserDetailsForToken(authenticationDetails.getEmail());
        String token = jwtUtil.generateToken(userTokenInformation);
        
        return ResponseEntity.ok(new JwtResponse(token,
        										 userTokenInformation.getId(),
        										 userTokenInformation.getEmail(),
        										 userTokenInformation.getFirstName(),
        										 userTokenInformation.getLastName(),
        										 userTokenInformation.getPrivileges()
        										 ));
    }

    @GetMapping(path = "verifyToken")
    public ResponseEntity verifyToken(@RequestHeader("Device-info") String device) throws Exception {
        return new ResponseEntity(HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new Exception("Empty credentials");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException disabled) {
            throw new UserDisabledException("User disabled");
        } catch (BadCredentialsException badCredentials) {
        	System.out.println("haslo: "+password);
            throw new BadUserCredentialsException("Invalid credentials");
        }
    }

    private boolean createUserAccount(UserSignUpPOJO accountDetails) {
        try {
            userService.registerNewUserAccount(accountDetails);
            return true;
        } catch (UserException ue) {
            return false;
        }
    }
    
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(final HttpServletRequest request, @RequestBody UserSignInPOJO userEmail) {
        String thatEmail = userEmail.getEmail();
        System.out.println(thatEmail);
    	final User user = userRepository.getByEmail(thatEmail);
        if (user != null) {
        	System.out.println("Uzytkownik znaleziony");
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            System.out.println("getAppUrl = "+getAppUrl(request));
            //mailSender.setPort(8081);
            //mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
            constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user);
        } else {
        	System.out.println("Nie znalezniono uzytkownika");
        	return null;
        }
        //return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        return new GenericResponse("Reset hasla").getMessage();
    }
    
    
    
    
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    
    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = "Reset hasla";
        System.out.println("tresc maila = " + constructEmail("Reset Password", message + " \r\n" + url, user));
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }
    
    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    
    
    
    
    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final long id, @RequestParam("token") final String token) {
        final String result = userService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message");
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }
    
    @RequestMapping(value = "savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse  savePassword(final Locale locale, @Valid @RequestBody PasswordDto passwordDto) {
    	System.out.println("new password = " + passwordDto.getNewPassword());
    	System.out.println("old password = " + passwordDto.getOldPassword());
    	String temp = passwordDto.getOldPassword();
    	Long tempL = Long.parseLong(temp);
        final User user = userRepository.getById(tempL);
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse("lol");
    }
    
    
    
    
}
