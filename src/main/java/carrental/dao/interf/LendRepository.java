package carrental.dao.interf;

import carrental.domain.Lend;
import carrental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LendRepository extends JpaRepository<Lend,Long>{
    @Query("SELECT l.user from Lend l where l.user = ?#{[0]}")
    Lend findLendOfUser(User user);
}
