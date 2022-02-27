package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revature.ProjectManagementAPI.Models.Task;
import revature.ProjectManagementAPI.Models.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByManager(User manager);
    List<Task> findAllByEmployee(User employee);
}
