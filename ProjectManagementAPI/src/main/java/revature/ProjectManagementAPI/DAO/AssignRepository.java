package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.AssignProject;
import revature.ProjectManagementAPI.models.Project;

import java.util.List;

@Repository
@Transactional
public interface AssignRepository extends JpaRepository<AssignProject, Integer> {
    List<AssignProject> getAllByAssignUserId(Integer userId);
    void deleteAssignProjectByAssignUserId(Integer assignUserId);
}
