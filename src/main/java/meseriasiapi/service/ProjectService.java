package meseriasiapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import meseriasiapi.domain.Bid;
import meseriasiapi.domain.Project;
import meseriasiapi.repository.BidRepository;
import meseriasiapi.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static meseriasiapi.exceptions.messages.Messages.*;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ListingService listingService;
    private final BidRepository bidRepository;

    public Project findById(UUID projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException(NO_PROJECT_WITH_THIS_ID);
        }
        return project.get();
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(Project project) {

        if (listingService.checkIfCategoryIsInEnum(project.getCategory())) {
            throw new EntityNotFoundException(PROJECT_CATEGORY_NOT_FOUND);
        }


        return projectRepository.save(project);
    }

    public Project updateProject(Project newProject) {
        if (listingService.checkIfCategoryIsInEnum(newProject.getCategory())) {
            throw new EntityNotFoundException(PROJECT_CATEGORY_NOT_FOUND);
        }
        Optional<Project> existingProjectById = projectRepository.findById(newProject.getId());
        if (existingProjectById.isEmpty()) {
            throw new EntityNotFoundException(NO_PROJECT_WITH_THIS_ID_FOUND);
        }
        Project existingProject = existingProjectById.get();

        Project updatedProject = Project.builder()
                .id(existingProject.getId())
                .title(newProject.getTitle())
                .description(newProject.getDescription())
                .category(newProject.getCategory())
                .status(newProject.getStatus())
                .creationDate(existingProject.getCreationDate())
                .county(newProject.getCounty())
                .city(newProject.getCity())
                .user(newProject.getUser())
                .expectedDueDate(newProject.getExpectedDueDate())
                .actionDuration(newProject.getActionDuration())
                .build();
        return projectRepository.save(updatedProject);

    }

    public String deleteProject(UUID id) {

        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            List<Bid> bids = bidRepository.findByProjectId(id);
            if (!bids.isEmpty()) {
                bidRepository.deleteAll(bids);
            }
            projectRepository.delete(project.get());

            return PROJECT_WAS_DELETED_SUCCESSFULLY;
        } else {
            throw new EntityNotFoundException(NO_PROJECT_WITH_THIS_ID_FOUND);
        }
    }
    public List<Project> findAllProjectsWithSorting(String fieldToSortBy) {
        return projectRepository.findAll(Sort.by(Sort.Direction.DESC, fieldToSortBy));
    }

    public Page<Project> findProjectsWithPagination(int offset, int pageSize) {
        return projectRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<Project> findProjectsWithPaginationAndSorting(int offset, int pageSize, String field) {
        return projectRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(Sort.Direction.DESC, field)));
    }

    public List<Project> findProjectsByUserEmail(String userEmail) {
        return projectRepository.findByUserEmail(userEmail);
    }
    public long countAllProjects() {
        return projectRepository.count();
    }
}
