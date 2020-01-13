package com.engineering.shop.security.authentication;

import com.engineering.shop.security.UserService;
import com.engineering.shop.security.jwt.JwtResponse;
import com.engineering.shop.security.jwt.JwtUtil;
import com.engineering.shop.users.UserSignInPOJO;
import com.engineering.shop.users.UserSignUpPOJO;
import com.engineering.shop.users.UserTokenInformation;
import com.engineering.shop.users.exceptions.BadUserCredentialsException;
import com.engineering.shop.users.exceptions.UserDisabledException;
import com.engineering.shop.users.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "signUp", consumes = "application/json")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody UserSignUpPOJO accountDetails) {
        return createUserAccount(accountDetails) ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity(HttpStatus.CONFLICT);
    }

    @PostMapping(path = "logIn", consumes = "application/json")
    @ResponseBody
    public ResponseEntity logIn(@RequestBody UserSignInPOJO authenticationDetails) throws Exception {
        try {
            authenticate(authenticationDetails.getEmail(), authenticationDetails.getPassword());
        } catch (UserException e) {
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }

        UserTokenInformation userTokenInformation = userService.getUserDetailsForToken(authenticationDetails.getEmail());
        String token = jwtUtil.generateToken(userTokenInformation);

        return ResponseEntity.ok(new JwtResponse(token,jwtUtil.getStringClaimFromToken(token,"name"),jwtUtil.getStringClaimFromToken(token,"lastName"), jwtUtil.getStringClaimFromToken(token,"role")));
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
