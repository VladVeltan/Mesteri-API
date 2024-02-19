package meseriasiapi.repository;


import meseriasiapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
}
