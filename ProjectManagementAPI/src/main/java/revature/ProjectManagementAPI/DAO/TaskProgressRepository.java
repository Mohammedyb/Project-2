package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.TaskProgress;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TaskProgressRepository extends JpaRepository<TaskProgress, Integer> {
    Optional<TaskProgress> findByAssignTaskId(Integer assignTaskId);
    List<TaskProgress> getAllByProjectsId(Integer projectId);
    List<TaskProgress> getAllByAssignTaskId(Integer assignTaskId);
}
