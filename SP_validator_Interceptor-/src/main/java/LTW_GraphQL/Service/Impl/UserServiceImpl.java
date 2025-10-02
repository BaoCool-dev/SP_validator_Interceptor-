package LTW_GraphQL.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import LTW_GraphQL.Entity.User;
import LTW_GraphQL.Model.UserInput;
import LTW_GraphQL.Repository.UserRepository;
import LTW_GraphQL.Service.UserService;
@Service 
public class UserServiceImpl implements UserService {

   
    private UserRepository userRepository;

   
    @Override
	public List<User> getAllUsers() {
        return userRepository.findAll();
    }

  
    @Override
	public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

   
    @Override
	public User createUser(UserInput userInput) {
        User user = new User();
        user.setFullname(userInput.getFullname());
        user.setEmail(userInput.getEmail());
        user.setPassword(userInput.getPassword()); // Chú ý: Trong thực tế cần mã hóa mật khẩu
        user.setPhone(userInput.getPhone());
        return userRepository.save(user);
    }

   
    @Override
	public User updateUser(Long id, UserInput userInput) {
        User existingUser = userRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setFullname(userInput.getFullname());
        existingUser.setEmail(userInput.getEmail());
        existingUser.setPassword(userInput.getPassword());
        existingUser.setPhone(userInput.getPhone());
        return userRepository.save(existingUser);
    }

    
    @Override
	public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}