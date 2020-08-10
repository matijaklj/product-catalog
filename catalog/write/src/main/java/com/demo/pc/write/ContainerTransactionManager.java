package com.demo.pc.write;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.axonframework.common.transaction.Transaction;
import org.axonframework.common.transaction.TransactionManager;

public class ContainerTransactionManager implements TransactionManager {

    private final EntityManager entityManager;
    private final String userTransactionJndiName;

    public ContainerTransactionManager(final EntityManager entityManager, final String userTransactionJndiName) {
        this.entityManager = entityManager;
        this.userTransactionJndiName = userTransactionJndiName;
    }

    @Override
    public Transaction startTransaction() {

        Transaction startedTransaction = null;
        try {
            final UserTransaction transaction = (UserTransaction) new InitialContext().lookup(this.userTransactionJndiName);

            if (transaction == null) {
                return startedTransaction;
            }

            if (transaction.getStatus() != Status.STATUS_ACTIVE) {
                transaction.begin();
            }

            if (!this.entityManager.isJoinedToTransaction()) {
                this.entityManager.joinTransaction();
            }


            startedTransaction = new Transaction() {

                @Override
                public void commit() {

                    try {
                        switch (transaction.getStatus()) {
                            case Status.STATUS_ACTIVE:
                                transaction.commit();
                                break;
                            case Status.STATUS_MARKED_ROLLBACK:
                                rollback();
                                break;
                            default:
                                break;
                        }
                    } catch (final IllegalStateException | SystemException | SecurityException | RollbackException | HeuristicMixedException
                            | HeuristicRollbackException e) {
                        System.out.println("XXX Error commiting TX" + e);
                    }
                }

                @Override
                public void rollback() {

                    try {
                        switch (transaction.getStatus()) {
                            case Status.STATUS_ACTIVE:
                            case Status.STATUS_MARKED_ROLLBACK:
                                transaction.rollback();
                                break;
                            default:
                                break;
                        }
                    } catch (final IllegalStateException | SystemException | SecurityException e) {
                        System.out.println("Error rollbacking TX" + e);
                    }
                }
            };

        } catch (final NotSupportedException | SystemException | NamingException e) {
            System.out.println("Error getting a TX" + e);
        }

        return startedTransaction;
    }
}