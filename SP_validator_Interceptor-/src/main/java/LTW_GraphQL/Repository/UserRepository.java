package LTW_GraphQL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import LTW_GraphQL.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}