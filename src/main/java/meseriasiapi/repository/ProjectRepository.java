package meseriasiapi.repository;

import meseriasiapi.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    long count();
    List<Project> findByUserEmail(String userEmail);
}
