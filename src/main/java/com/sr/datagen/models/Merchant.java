package com.sr.datagen.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("merchant")
@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @Column(name = "id")
    private long merchantId;

    @Column(name = "merchant_name")
    private String merchantName;


    @Column(name = "merchant_city")
    private String merchantCity;

    @Column(name = "merchant_state")
    private String merchantState;

    @Column(name = "merchant_zip")
    private String zip;

    @Column(name = "mcc")
    private int mcc;


}
