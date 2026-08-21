package Day14.service;

import Day14.model.Account;
import Day14.model.Transaction;
import Day14.model.TransactionType;
import Day14.repository.BankRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnalyticsService {

    private BankRepository repository;

    public AnalyticsService(BankRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.getAllAccounts()
                .stream()
                .flatMap(account -> account.getTransactions().stream())
                .toList();
    }

    public double getTotalDeposits() {
        return getAllTransactions()
                .stream()
                .filter(t -> t.getType() == TransactionType.DEPOSIT)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getAllWithdrawals(){
        return getAllTransactions()
                .stream()
                .filter(t->t.getType()==TransactionType.WITHDRAW)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalTransfers() {
        return getAllTransactions()
                .stream()
                .filter(t -> t.getType() == TransactionType.TRANSFER)
                .mapToDouble(Transaction::getAmount)
                .sum() / 2;
    }

    public Optional<Transaction> getLargestTransaction() {

        return getAllTransactions()
                .stream()
                .max(Comparator.comparingDouble(Transaction::getAmount));
    }

    public List<Transaction> getTransactionsAbove(double amount) {

        return getAllTransactions()
                .stream()
                .filter(t -> t.getAmount() > amount)
                .toList();
    }

    public Map<TransactionType, Long> getTransactionCountByType() {

        return getAllTransactions()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.counting()
                ));
    }

    public double getAverageTransaction() {

        return getAllTransactions()
                .stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0);
    }

    public Map<String, Double> getTotalTransactionAmountByAccount() {

        return getAllTransactions()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getAccountNumber,
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }


}