package com.engineering.shop.security;

import com.engineering.shop.users.*;
import com.engineering.shop.users.exceptions.EmailAlreadyUsedException;
import com.engineering.shop.users.exceptions.UserException;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Service("userService")
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserSignUpPOJOToUserTransformer transformer;
    private final PasswordEncoder encoder;
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    public UserService(PasswordEncoder encoder, UserRepository userRepository, UserSignUpPOJOToUserTransformer transformer, PasswordResetTokenRepository passwordTokenRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.transformer = transformer;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    public User registerNewUserAccount(UserSignUpPOJO account) throws UserException {
        if (emailAlreadyExists(account)) {
            throw new EmailAlreadyUsedException("There is an account that email address: " + account.getEmail());
        }

        account.setPassword(encoder.encode(account.getPassword()));
        User user = transformer.transform(account);

        System.out.println("account:" + account);
        return userRepository.save(user);
    }

    public UserTokenInformation getUserDetailsForToken(String email) {
        User user = userRepository.getByEmail(email);
        UserToUserTokenInformationTransformer transformer = new UserToUserTokenInformationTransformer();
        System.out.println(user);
        return transformer.transform(user);
    }


    private boolean emailAlreadyExists(UserSignUpPOJO account) {
        User alreadyRegistered = userRepository.getByEmail(account.getEmail());

        return alreadyRegistered != null;
    }
    
    //@PutMapping(path = "modify", consumes = "application/json")
    //@ResponseBody
    public ResponseEntity<Object> updateUser(@RequestBody User userDetails) {
    	//Optional<UserSignUpPOJO> userOptional = user
    	//long id = 19;
    	//getUserDetailsForToken(userDetails.getEmail());
    	//User user = transformer.transform(userDetails);
    	User trueUser = userRepository.getById(userDetails.getId());
    	//userDetails.setId(trueUser.getId());
    	userDetails.setPassword(trueUser.getPassword());
    	userDetails.setRoles(trueUser.getRoles());
    	System.out.println("UserService userDetails: " + userDetails);
    	//user.setId(id);
    	userRepository.save(userDetails);
    	
    	return ResponseEntity.noContent().build();
    	
    }
    
    //@Override
    public User changeUserData(UserSignUpPOJO account) throws UserException {
    	User user = transformer.transform(account);
    	
		return null;
    	
    }

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		
		System.out.println("User = " + user);
		System.out.println("token = "+token);
		System.out.println("myToken = " + myToken);
		passwordTokenRepository.save(myToken);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String validatePasswordResetToken(long id, String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        final User user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
	
	public void changeUserPassword(final User user, final String password) {
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
