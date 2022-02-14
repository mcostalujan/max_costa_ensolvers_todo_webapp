package todo.webapp.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.webapp.api.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	
	User findUserByUsername(String username);

	User findUserByEmail(String email);
}
