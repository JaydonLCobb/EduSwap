package EduSwapApplication.Transactions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import EduSwapApplication.Users.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findById(int id);

    List<Transaction> findBySeller(User seller);

    List<Transaction> findByBuyer(User buyer);
}
