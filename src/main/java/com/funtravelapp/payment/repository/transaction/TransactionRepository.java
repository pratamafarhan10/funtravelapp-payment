package com.funtravelapp.payment.repository.transaction;

import com.funtravelapp.payment.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Modifying
    @Query(
            value = "UPDATE transaction set status = :status WHERE id = :id",
            nativeQuery = true
    )
    Transaction updateStatus(@Param("id") int id, @Param("status") String status);

    @Modifying
    @Query(
            value = "UPDATE transaction set is_invoice_sent = :status WHERE order_id = :orderId",
            nativeQuery = true
    )
    int updateInvoiceStatus(@Param("orderId") int orderId, @Param("status") String status);
}
