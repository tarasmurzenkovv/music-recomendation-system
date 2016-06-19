package configuration.security;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import repository.UserRepository;

@Component
public class AuthenticatedUser {
    @Autowired
    private UserRepository userRepository;

    public User getAuthenticatedCustomer(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(login);
    }

}
