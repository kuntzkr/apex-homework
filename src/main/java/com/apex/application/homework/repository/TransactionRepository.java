package com.apex.application.homework.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apex.application.homework.model.Transaction;

@Qualifier("transactions")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}