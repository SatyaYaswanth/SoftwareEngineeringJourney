package Day14.service;

import Day14.exception.AccountNotFoundException;
import Day14.exception.CustomerNotFoundException;
import Day14.model.*;
import Day14.repository.BankRepository;

import java.util.Objects;
import java.util.Optional;

import static Day14.model.TransactionType.*;

public class BankService {

    private BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    public void registerCustomer(
            String customerId,
            String name,
            String email,
            long phone
    ){
        Customer customer = new Customer(customerId, name, email, phone);
        repository.addCustomer(customer);
    }


    public void createSavingsAccount(
            String accountNumber,
            String customerId,
            double initialBalance,
            double interestRate
    ){
        Optional<Customer> customer = repository.findCustomerById(customerId);
        if (customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found: " + customerId);
        }
        Customer existingCustomer = customer.get();
        Account account = new SavingsAccount(accountNumber, customerId, initialBalance, interestRate);
        repository.addAccount(account);

        existingCustomer.addAccount(account);
    }

    public void createCurrentAccount(
            String accountNumber,
            String customerId,
            double initialBalance,
            double minimumBalance
    ){
        Optional<Customer> customer = repository.findCustomerById(customerId);
        if (customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found: " + customerId);
        }
        Customer existingCustomer = customer.get();
        Account account = new CurrentAccount(accountNumber, customerId, initialBalance, minimumBalance);
        repository.addAccount(account);

        existingCustomer.addAccount(account);
    }

    public void deposit(String accountNumber, double amount){
        Optional<Account> accountOptional =
                repository.findAccountByNumber(accountNumber);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            account.deposit(amount);
            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(transactionId, accountNumber, DEPOSIT, amount, "deposit sucessful");
            account.getTransactions().add(transaction);
        } else {
            throw new AccountNotFoundException(
                    "Account not found: " + accountNumber
            );
        }
    }

    public void withdraw(String accountNumber, double amount){
        Optional<Account> accountOptional =
                repository.findAccountByNumber(accountNumber);
        if(accountOptional.isEmpty()){
            throw new AccountNotFoundException(
                    "Account not found: " + accountNumber
            );
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        Account account = accountOptional.get();
        account.withdraw(amount);
        String transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, WITHDRAW, amount, "Withdraw successful");
        account.getTransactions().add(transaction);

    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount){
        if(Objects.equals(fromAccountNumber, toAccountNumber)){
            throw new IllegalArgumentException("Cannot transfer to same account.");
        }
        Optional<Account> accountOptional =
                repository.findAccountByNumber(fromAccountNumber);
        if(accountOptional.isEmpty()){
            throw new AccountNotFoundException(
                    "Account not found: " + fromAccountNumber
            );
        }
        Account sourceAccount = accountOptional.get();
        accountOptional =
                repository.findAccountByNumber(toAccountNumber);
        if(accountOptional.isEmpty()){
            throw new AccountNotFoundException(
                    "Account not found: " + toAccountNumber
            );
        }
        Account destinationAccount = accountOptional.get();

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        sourceAccount.withdraw(amount);
        String transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, sourceAccount.getAccountNumber(), TRANSFER, amount, "Transfer to " + toAccountNumber);
        sourceAccount.getTransactions().add(transaction);
        destinationAccount.deposit(amount);
        transactionId = generateTransactionId();
        transaction = new Transaction(transactionId, destinationAccount.getAccountNumber(), TRANSFER, amount, "Transfer from " + fromAccountNumber);
        destinationAccount.getTransactions().add(transaction);
    }

    private long transactionCounter = 1;

    private String generateTransactionId() {
        return "TXN" + transactionCounter++;
    }
}
