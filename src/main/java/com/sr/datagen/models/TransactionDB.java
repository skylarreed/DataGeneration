package com.sr.datagen.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionDB {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @SequenceGenerator(name = "transaction_id_seq", sequenceName = "seq", initialValue = 1)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "card_id")
    private long cardId;


    @Column(name = "year")
    private int year;


    @Column(name = "month")
    private int month;


    @Column(name = "day")
    private int day;


    @Column(name = "time")
    private String time;


    @Column(name = "amount")
    private double amount;


    @Column(name = "type")
    private String type;


    @Column(name = "merchant_id")
    private long merchantId;

    @Column(name = "errors")
    private String errors;

    @Column(name = "is_fraud")
    private boolean isFraud;

}