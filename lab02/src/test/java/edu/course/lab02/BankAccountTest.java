package edu.course.lab02;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BankAccountTest {

    @Test
    void withdraw() {
        BankAccount account = new BankAccount(100);
        account.withdraw(10);
        assertTrue(account.getBalance() == 90);
    }
    @Test
    void withdrawMoreThanBalanceThrowsException() {
        BankAccount account = new BankAccount(100);

            assertThrows(IllegalStateException.class, () -> account.withdraw(101));
    }
}