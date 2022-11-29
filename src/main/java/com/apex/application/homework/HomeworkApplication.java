package com.apex.application.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import com.apex.application.homework.model.Customer;
import com.apex.application.homework.model.Transaction;

@SpringBootApplication
public class HomeworkApplication {

	private static final String CREATE_CUSTOMER_ENDPOINT = "http://localhost:8080/api/v1/customers";

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void postStartupDataLoad() {
		System.out.println("App started, creating sample data");
		HomeworkApplication app = new HomeworkApplication();
		app.createData();
	}

	private void createData() {
		List<Customer> customerList = new ArrayList<Customer>();

		// Customers
		Customer admin = new Customer("Admin", "Admin", "admin@gmail.com");
		Customer kurtis = new Customer("Kurtis", "Kuntz", "kurtisrkuntz@eaton.com");
		Customer mickey = new Customer("Mickey", "Mouse", "mickeymmouse@gmail.com");
		customerList.addAll(Arrays.asList(admin, kurtis, mickey));

		// Transactions - Generate random int between $60 - $500 USD
		int min = 1;
		int max = 500;
		for (Customer c : customerList) {
			Set<Transaction> transactionList = new HashSet<Transaction>();
			for (int i = 0; i < 5; i++) {
				Random r = new Random();
				int val = r.nextInt(max - min) + min;
				Transaction t = new Transaction(val);
				transactionList.add(t);
			}
			c.setTransactions(transactionList);
		}

		// Persist the random transaction data to the H2 DB for reward points
		// calculation
		RestTemplate restTemplate = new RestTemplate();
		for (Customer c : customerList) {
			restTemplate.postForObject(CREATE_CUSTOMER_ENDPOINT, c, Customer.class);
		}
		System.out.println("Sample data created successfully");
	}

}
