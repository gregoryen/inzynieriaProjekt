package com.engineering.shop.security;

import com.engineering.shop.users.*;
import com.engineering.shop.users.exceptions.EmailAlreadyUsedException;
import com.engineering.shop.users.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public UserService(PasswordEncoder encoder, UserRepository userRepository, UserSignUpPOJOToUserTransformer transformer) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.transformer = transformer;
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
    
    
}
