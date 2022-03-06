package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.Task;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> getAllByUserId(Integer userId);
}
