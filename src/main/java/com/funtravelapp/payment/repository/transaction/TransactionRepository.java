package com.funtravelapp.payment.repository.transaction;

import com.funtravelapp.payment.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Modifying
    @Query(
            value = "UPDATE transaction set status = :status WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    Transaction updateStatus(@Param("chainingId") String chainingId, @Param("status") String status);

    Optional<Transaction> findByChainingId(String chainingId);

    List<Transaction> findByCustomerId(Integer customerId);
    List<Transaction> findBySellerId(Integer sellerId);

    @Modifying
    @Query(
            value = "UPDATE transaction set is_invoice_sent = :status WHERE chaining_id = :chainingId",
            nativeQuery = true
    )
    int updateInvoiceStatus(@Param("chainingId") String chainingId, @Param("status") String status);
}
