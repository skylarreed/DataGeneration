package com.sr.datagen.models;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@XStreamAlias("Card")
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    @XStreamAlias("UserId")
    private long userId;

    @Column(name = "card_id")
    @XStreamAlias("CardId")
    private long cardId;

    @Column(name = "card_number")
    @XStreamAlias("CardNumber")
    private String cardNumber;

    @Column(name = "expiration_date")
    @XStreamAlias("ExpiryDate")
    private String expiryDate;

    @Column(name = "cvv")
    @XStreamAlias("CVV")
    private String cvv;

    @Column(name = "card_type")
    @XStreamAlias("CardType")
    private String cardType;
}
