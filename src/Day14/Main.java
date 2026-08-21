package Day14;

import Day14.exception.AccountNotFoundException;
import Day14.exception.CustomerNotFoundException;
import Day14.exception.InsufficientBalanceException;
import Day14.repository.BankRepository;
import Day14.service.AnalyticsService;
import Day14.service.BankService;
import Day14.util.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // ==========================================
        // INITIALIZATION
        // ==========================================

        BankRepository repository = new BankRepository();

        BankService service =
                new BankService(repository);

        AnalyticsService analyticsService =
                new AnalyticsService(repository);


        // ==========================================
        // CUSTOMER REGISTRATION
        // ==========================================

        service.registerCustomer(
                "C001",
                "Rahul",
                "rahul@email.com",
                889977665
        );

        service.registerCustomer(
                "C002",
                "Arjun",
                "arjun@email.com",
                885974625
        );

        System.out.println("Customers registered successfully.");


        // ==========================================
        // ACCOUNT CREATION
        // ==========================================

        service.createSavingsAccount(
                "ACC1001",
                "C001",
                10000,
                6.5
        );

        service.createCurrentAccount(
                "ACC1002",
                "C002",
                5000,
                1000
        );

        System.out.println("Accounts created successfully.");


        // ==========================================
        // BANKING OPERATIONS
        // ==========================================

        service.deposit(
                "ACC1001",
                2000
        );

        service.withdraw(
                "ACC1001",
                3000
        );

        service.deposit(
                "ACC1002",
                5000
        );

        service.transfer(
                "ACC1001",
                "ACC1002",
                2000
        );

        System.out.println("\nBanking operations completed.");


        // ==========================================
        // FINAL BALANCES
        // ==========================================

        System.out.println("\n========== ACCOUNT BALANCES ==========");

        repository.findAccountByNumber("ACC1001")
                .ifPresent(account ->
                        System.out.println(
                                "ACC1001 : ₹" +
                                        account.getBalance()
                        )
                );

        repository.findAccountByNumber("ACC1002")
                .ifPresent(account ->
                        System.out.println(
                                "ACC1002 : ₹" +
                                        account.getBalance()
                        )
                );


        // ==========================================
        // ANALYTICS
        // ==========================================

        System.out.println(
                "\n========== FINANCIAL ANALYTICS =========="
        );

        System.out.println(
                "All Transactions:"
        );

        analyticsService
                .getAllTransactions()
                .forEach(System.out::println);

        System.out.println(
                "\nTotal Deposits      : ₹" +
                        analyticsService.getTotalDeposits()
        );

        System.out.println(
                "Total Withdrawals   : ₹" +
                        analyticsService.getAllWithdrawals()
        );

        System.out.println(
                "Total Transfers     : ₹" +
                        analyticsService.getTotalTransfers()
        );

        System.out.println(
                "Average Transaction : ₹" +
                        analyticsService.getAverageTransaction()
        );

        System.out.println(
                "Largest Transaction : " +
                        analyticsService.getLargestTransaction()
        );

        System.out.println(
                "Transactions > ₹5000: " +
                        analyticsService.getTransactionsAbove(5000)
        );

        System.out.println(
                "Transaction Count   : " +
                        analyticsService.getTransactionCountByType()
        );

        System.out.println(
                "Amount By Account   : " +
                        analyticsService.getTotalTransactionAmountByAccount()
        );


        // ==========================================
        // FAILURE TESTS
        // ==========================================

        System.out.println(
                "\n========== FAILURE TESTS =========="
        );


        // Test 1: Insufficient balance

        try {

            service.withdraw(
                    "ACC1001",
                    50000
            );

        } catch (InsufficientBalanceException e) {

            System.out.println(
                    "PASS - Insufficient balance detected."
            );
        }


        // Test 2: Account not found

        try {

            service.deposit(
                    "ACC9999",
                    2000
            );

        } catch (AccountNotFoundException e) {

            System.out.println(
                    "PASS - Account not found detected."
            );
        }


        // Test 3: Negative deposit

        try {

            service.deposit(
                    "ACC1001",
                    -1000
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "PASS - Negative amount rejected."
            );
        }


        // Test 4: Same account transfer

        try {

            service.transfer(
                    "ACC1001",
                    "ACC1001",
                    1000
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "PASS - Same-account transfer rejected."
            );
        }


        // Test 5: Customer not found

        try {

            service.createSavingsAccount(
                    "ACC9999",
                    "C999",
                    5000,
                    6.5
            );

        } catch (CustomerNotFoundException e) {

            System.out.println(
                    "PASS - Customer not found detected."
            );
        }


        // ==========================================
        // FINAL RESULT
        // ==========================================

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "       DAY 14 CAPSTONE TEST COMPLETE"
        );

        System.out.println(
                "=========================================="
        );

        FileManager fileManager = new FileManager();
//        fileManager.save(repository);

        File file = new File("Day14/data/bank.dat");

        System.out.println("File exists: " + file.exists());
        System.out.println("File size: " + file.length() + " bytes");
        System.out.println("File path: " + file.getAbsolutePath());

        BankRepository loadedRepository =
                fileManager.load();

        System.out.println(
                "\n========== LOADED DATA =========="
        );

        System.out.println(
                "Customers loaded: " +
                        loadedRepository.getAllCustomers().size()
        );

        System.out.println(
                "Accounts loaded: " +
                        loadedRepository.getAllAccounts().size()
        );

        loadedRepository.findAccountByNumber("ACC1001")
                .ifPresent(account ->
                        System.out.println(
                                "ACC1001 balance after load: ₹" +
                                        account.getBalance()
                        )
                );

        loadedRepository.findAccountByNumber("ACC1002")
                .ifPresent(account ->
                        System.out.println(
                                "ACC1002 balance after load: ₹" +
                                        account.getBalance()
                        )
                );
    }

}