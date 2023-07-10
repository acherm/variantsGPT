package com.example;

import java.util.List;

public class Variant1 {

    class Account {
        private String id;
        private double amount;
        private double limit;

        public void deposit(double amount) {}

        public double getAmount() {
            return 0.0;
        }

        public void withdrawWithLimit(double amount) {}

        public double getLimit() {
            return 0.0;
        }

        public void setLimit(double limit) {}
    }

    class Bank {
        private List<Account> accounts;
        private Consortium cons;

        public void depositOnAccount(String id, double amount) {}

        public void withdrawFromAccount(String id, double amount) {}
    }

    class Client {
        private String id;
        private String name;
    }

    class Consortium {
        // Empty class as per the description.
    }
}

