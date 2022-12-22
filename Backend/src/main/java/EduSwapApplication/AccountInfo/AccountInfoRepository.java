package EduSwapApplication.AccountInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

    AccountInfo findById(int id);

    @Transactional
    void deleteById(int id);

    AccountInfo findByuserId(int id);
}
