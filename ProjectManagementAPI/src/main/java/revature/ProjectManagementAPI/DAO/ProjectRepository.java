package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.AssignProject;
import revature.ProjectManagementAPI.models.Project;

import java.util.List;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> getAllById(Integer userId);
    Project getProjectById(Integer id);
    Project getProjectByName(String name);
    Project getById(Integer assignProjId);
}
