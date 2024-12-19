package com.laptrinhoop.dao.impl;

import com.laptrinhoop.dao.ITransactionDAO;
import com.laptrinhoop.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDAO extends GeneraDAO<Transaction, Integer> implements ITransactionDAO {
    @Override
    public Optional<Transaction> findByTransactionId(String transactionId) {
        String hql = "FROM Transaction as t  WHERE t.transactionId =?0";
        List<Transaction> result = this.getResultList(hql,transactionId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));

    }

    @Override
    public Optional<Transaction> findByTransactionIdAndUsername(String transactionId, String username) {
        String hql = "FROM Transaction as t  WHERE t.transactionId =?0 and t.username = ?1";
        List<Transaction> result = this.getResultList(hql,transactionId,username);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<Transaction> findByUsername(String username) {
        String hql = "FROM Transaction as t  WHERE  t.username = ?0";
        List<Transaction> result = this.getResultList(hql,username);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
