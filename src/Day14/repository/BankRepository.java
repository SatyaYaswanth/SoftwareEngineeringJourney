package Day14.repository;

import Day14.model.Account;
import Day14.model.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Customer> customers;
    private List<Account> accounts;

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public Optional<Customer> findCustomerById(String customerId){
        return customers.stream()
                .filter(c -> c.getCustomerId().equalsIgnoreCase(customerId))
                .findFirst();
    }

    public Optional<Account> findAccountByNumber(String accountNumber){
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
    }

    public List<Customer> getAllCustomers(){
        return customers;
    }

    public List<Account> getAllAccounts(){
        return accounts;
    }

    public BankRepository() {
        customers = new ArrayList<>();
        accounts = new ArrayList<>();
    }
}
