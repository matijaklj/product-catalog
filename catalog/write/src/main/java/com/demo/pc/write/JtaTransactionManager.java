package com.demo.pc.write;

import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.common.transaction.Transaction;
import org.axonframework.common.transaction.TransactionManager;
import org.jooq.lambda.Unchecked;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

public class JtaTransactionManager implements TransactionManager {

    @Inject
    private UserTransaction transaction;

    @Override
    public Transaction startTransaction() {
        Unchecked.consumer(UserTransaction::begin).accept(transaction);
        return new JtaTransaction(transaction);
    }

    private static class JtaTransaction implements Transaction {

        private UserTransaction transaction;

        public JtaTransaction(UserTransaction transaction) {
            this.transaction = transaction;
        }

        @Override
        public void commit() {
            Unchecked.consumer(UserTransaction::commit).accept(transaction);
        }

        @Override
        public void rollback() {
            Unchecked.consumer(UserTransaction::rollback).accept(transaction);
        }
    }
}
