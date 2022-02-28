package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.AssignProject;

@Repository
@Transactional
public interface AssignRepository extends JpaRepository<AssignProject, Integer> {
    void deleteAssignProjectByAssignUserId(Integer assignUserId);

}
