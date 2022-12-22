package EduSwapApplication.Transactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EduSwapApplication.Users.User;
import EduSwapApplication.Users.UserRepository;

@RestController
public class TransactionController {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/transactions")
    List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping(path = "/transactions/{id}")
    Transaction getTransactionById(@PathVariable int id) {
        return transactionRepository.findById(id);
    }

    @PostMapping(path = "/transactions/{sellerId}/{buyerId}")
    String createTransaction(@RequestBody Transaction transaction, @PathVariable int sellerId, @PathVariable int buyerId) {
        if (transaction == null)
            return failure;
        User seller = userRepository.findById(sellerId);
        User buyer = userRepository.findById(buyerId);
        if (seller == null || buyer == null)
            return failure;
        transaction.setSeller(seller);
        transaction.setBuyer(buyer);
        transactionRepository.save(transaction);
        return success;
    }

    @PutMapping("/transactions/{id}")
    Transaction updateTransaction(@PathVariable int id, @RequestBody Transaction request) {
        Transaction transaction = transactionRepository.findById(id);
        if (transaction == null)
            return null;
        transactionRepository.save(request);
        return transactionRepository.findById(id);
    }

    @GetMapping(path = "/transactions/seller/{username}")
    List<Transaction> getTransactionBySeller(@PathVariable String username) {
        User seller = userRepository.findByUsername(username);
        return transactionRepository.findBySeller(seller);
    }

    @GetMapping(path = "/transactions/buyer/{username}")
    List<Transaction> getTransactionByBuyer(@PathVariable String username) {
        User buyer = userRepository.findByUsername(username);
        return transactionRepository.findByBuyer(buyer);
    }

}
