package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.ProjectManagementAPI.Models.Project;
import revature.ProjectManagementAPI.Models.User;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByManager(User manager);
    List<Project> findAllByEmployee(User employee);
}
