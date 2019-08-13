package devloafer.app.taco.cloud.repositories;

import devloafer.app.taco.cloud.domains.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
}
