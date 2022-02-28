package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.Meeting;

import java.util.List;

@Repository
@Transactional
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    List<Meeting> getAllByProjectId(Integer projectId);
}
