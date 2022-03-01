package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.Project;

import java.util.List;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Project getProjectById(Integer id);
    public Project getProjectByName(String name);
    public List<Project> getAllProjectsByManager(Integer projectManagerId);
    public List<Project> getAll();
}
