package com.engineering.shop.security.authentication;

import com.engineering.shop.security.UserService;
import com.engineering.shop.security.jwt.JwtResponse;
import com.engineering.shop.security.jwt.JwtUtil;
import com.engineering.shop.users.User;
import com.engineering.shop.users.UserSignInPOJO;
import com.engineering.shop.users.UserSignUpPOJO;
import com.engineering.shop.users.UserTokenInformation;
import com.engineering.shop.users.exceptions.BadUserCredentialsException;
import com.engineering.shop.users.exceptions.UserDisabledException;
import com.engineering.shop.users.exceptions.UserException;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;
    private PasswordEncoder encoder; 

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
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
    
    
}
