package com.funtravelapp.payment.model.account;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "bank")
    private String bank;
    @Column(name = "number")
    private String number;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "balance")
    private BigDecimal balance;
}
