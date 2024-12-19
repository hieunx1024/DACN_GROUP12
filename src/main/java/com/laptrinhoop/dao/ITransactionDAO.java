package com.laptrinhoop.dao;


import com.laptrinhoop.entity.Transaction;

import java.util.Optional;

public interface ITransactionDAO extends IGeneralDAO<Transaction, Integer>{
    Optional<Transaction> findByTransactionId(String transactionId);

    Optional<Transaction> findByTransactionIdAndUsername(String transactionId, String username);

    Optional<Transaction> findByUsername(String username);
}
