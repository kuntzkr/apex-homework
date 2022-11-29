package com.apex.application.homework.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apex.application.homework.model.Customer;
import com.apex.application.homework.model.Transaction;
import com.apex.application.homework.repository.CustomerRepository;
import com.apex.application.homework.repository.TransactionRepository;

@RestController
@RequestMapping("/api/v1")
public class HomeworkController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@Validated @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/customers/{id}/rewards")
    public Integer getCustomerRewards(@PathVariable("id") long id) {
        Customer customer = customerRepository.findById(id).get();
        Set<Transaction> transactions = customer.getTransactions();
        Integer points = 0;
        for (Transaction t : transactions) {
            System.out.println("Amount: " + t.getAmount());
            // Case of transaction < 100
            if (t.getAmount() <= 100) {
                points += t.getAmount() - 50;
                System.out.println("Points: " + points);
            }
            // Case of transaction > 100
            else {
                int doubleAmt = t.getAmount() - 100;
                points += (doubleAmt * 2) + 50;
                System.out.println("Points: " + points);
            }
        }
        return points;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@Validated @RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
