package com.sr.datagen.repositories;

import com.sr.datagen.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
