package revature.ProjectManagementAPI.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revature.ProjectManagementAPI.DAO.UserRepository;
import revature.ProjectManagementAPI.Models.User;

import java.util.Optional;


@Service
public class UserService {
    // get user by email
    // get user by id

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
