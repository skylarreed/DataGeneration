package com.sr.datagen.repositories;

import com.sr.datagen.models.Transaction;
import com.sr.datagen.models.TransactionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDB, Long> {
}
