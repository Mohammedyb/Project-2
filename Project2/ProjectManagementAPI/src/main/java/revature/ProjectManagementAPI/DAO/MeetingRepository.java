package revature.ProjectManagementAPI.DAO;

import org.springframework.stereotype.Repository;
import revature.ProjectManagementAPI.Models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import revature.ProjectManagementAPI.Models.MeetingType;
import revature.ProjectManagementAPI.Models.User;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByType(MeetingType type);
    List<Meeting> findAllByManager(User manager);
    List<Meeting> findAllByEmployee(User employee);
}