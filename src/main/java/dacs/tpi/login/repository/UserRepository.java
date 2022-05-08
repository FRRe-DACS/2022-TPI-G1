package dacs.tpi.login.repository;

import org.springframework.stereotype.Repository;
import dacs.tpi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
interface UserRepository extends JpaRepository<User,Long>{
    
}
