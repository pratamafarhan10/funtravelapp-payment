package com.funtravelapp.payment.account.repository;

import com.funtravelapp.payment.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
//    Optional<Account> findByUserId(Integer userId);
    List<Account> findByUserId(Integer userId);

    Optional<Account> findByNumber(String accountNumber);

//    @Query(
//            value = "",
//            nativeQuery = true
//    )
//    int updateBalanceByAccountNumber(String accountNumber, );
}
