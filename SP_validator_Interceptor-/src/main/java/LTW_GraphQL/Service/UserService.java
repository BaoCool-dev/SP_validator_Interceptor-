package LTW_GraphQL.Service;

import java.util.List;
import java.util.Optional;

import LTW_GraphQL.Entity.User;
import LTW_GraphQL.Model.UserInput;

public interface UserService {

	boolean deleteUser(Long id);

	User updateUser(Long id, UserInput userInput);

	User createUser(UserInput userInput);

	Optional<User> getUserById(Long id);

	List<User> getAllUsers();

}
