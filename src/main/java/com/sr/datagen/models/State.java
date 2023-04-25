package com.sr.datagen.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "states")
public class State {

    @Id
    private long stateId;
    @Column(name = "state")
    private String state;

    @Column(name = "state_abbr")
    private String stateAbbr;

    @Column(name = "state_capital")
    private String capital;

    @Column(name = "state_nickname")
    private String nickname;


}
