package com.apex.application.homework.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.apex.application.homework.model.Customer;
import com.apex.application.homework.model.Transaction;
import com.apex.application.homework.repository.CustomerRepository;
import com.apex.application.homework.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class HomeworkControllerTests {

    @InjectMocks
    HomeworkController homeworkController;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    void getCustomerRewards_test() {
        Set<Transaction> transactions = new HashSet<Transaction>();
        Customer c1 = new Customer("Kurtis", "Kuntz", "kurtis@gmail.com");
        c1.setId(1);
        Transaction t1 = new Transaction(100);
        Transaction t2 = new Transaction(200);
        transactions.add(t1);
        transactions.add(t2);
        c1.setTransactions(transactions);
        Mockito.when(customerRepository.findById(any(long.class))).thenReturn(Optional.of(c1));

        Integer result = homeworkController.getCustomerRewards(1);
        assertEquals(result, 300);
    }

    @Test
    void getAllCustomers_test() {
        List<Customer> customers = new ArrayList<Customer>();
        Customer c1 = new Customer("Kurtis", "Kuntz", "kurtis@gmail.com");
        customers.add(c1);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = homeworkController.getAllCustomers();

        assertEquals(result.size(), 1);
    }

    @Test
    void getAllTransactions_test() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction t1 = new Transaction(100);
        transactions.add(t1);
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = homeworkController.getAllTransactions();
        assertEquals(result.get(0).getAmount(), 100);
    }

    @Test
    void createCustomer_test() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Customer c1 = new Customer("Kurtis", "Kuntz", "kurtis@gmail.com");
        c1.setId(1);
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(c1);

        Customer result = homeworkController.createCustomer(c1);

        assertEquals(result.getId(), 1);
    }

    @Test
    void createTransaction_test() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Transaction t1 = new Transaction(100);
        t1.setId(1);
        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(t1);

        Transaction result = homeworkController.createTransaction(t1);

        assertEquals(result.getId(), 1);
    }
}
