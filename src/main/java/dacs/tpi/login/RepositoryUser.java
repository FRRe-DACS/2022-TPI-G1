package dacs.tpi.login;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import dacs.tpi.login.EntityUser;

@Repository
public interface UserRepository extends CrudRepository<EntityUser, Long>  {
    public Optional<EntityUser> findByUsername(String username);
}