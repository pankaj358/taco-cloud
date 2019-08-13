package devloafer.app.taco.cloud.services.impl;

import devloafer.app.taco.cloud.domains.User;
import devloafer.app.taco.cloud.repositories.UserRepository;
import devloafer.app.taco.cloud.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) return user;
        throw new UsernameNotFoundException(String.format("User %s not found", username));
    }
}
