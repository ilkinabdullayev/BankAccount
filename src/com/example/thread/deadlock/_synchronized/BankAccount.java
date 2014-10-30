package com.example.thread.deadlock._synchronized;

/**
 *
 * @author Ilkin Abdullayev
 */
public class BankAccount {

    double balance;
    int id;

    public BankAccount(int id, double balance) {
        this.balance = balance;
        this.id = id;
    }

    void withdraw(double amount) {
        // Wait to simulate io like database access ...
        try {
            Thread.sleep(10l);
        } catch (InterruptedException e) {
        }
        balance -= amount;
        System.out.println(Thread.currentThread().getName() + " withdraw "+amount+"$ from account “"+id+"”");
    }

    void deposit(double amount) {
        // Wait to simulate io like database access ...
        try {
            Thread.sleep(10l);
        } catch (InterruptedException e) {
        }
        balance += amount;
        
        System.out.println(Thread.currentThread().getName() + " deposit "+amount+"$ to account “"+id+"”");
        
    }

    static void transfer(BankAccount from, BankAccount to, double amount) {
        synchronized (from) {
            from.withdraw(amount);
            synchronized (to) {
                to.deposit(amount);
            }
        }

    }

    
    public static void main(String[] args) {

        final BankAccount fooAccount = new BankAccount(1, 100d);
        final BankAccount barAccount = new BankAccount(2, 100d);


        new Thread() {
            public void run() {
                BankAccount.transfer(fooAccount, barAccount, 10d);
            }
        }.start();


        new Thread() {
            public void run() {
                BankAccount.transfer(barAccount, fooAccount, 10d);
            }
        }.start();

    }
}
