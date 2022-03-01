package revature.ProjectManagementAPI.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import revature.ProjectManagementAPI.models.User;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByID(Integer id);
    public User getUserByEmail(String email);
}
