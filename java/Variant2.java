package com.example;

import java.util.List;

public class Variant {

    class Account {
        private String id;
        private double amount;
        
        
        private double limit;
        
        
        
        private int currency;
        

        public void deposit(double amount) {}

        public double getAmount() {
            return 0.0;
        }

        
        public void withdrawWithLimit(double amount) {}
        

        
        public double getLimit() {
            return 0.0;
        }

        public void setLimit(double limit) {}
        

        
        public int getCurrency() {
            return 0;
        }

        public void setCurrency(int currency) {}
        
    }

    class Bank {
        
        private Account accounts;
        
        
        
        private Converter converter;
        
        
        

        public void depositOnAccount(String id, double amount) {}

        public void withdrawFromAccount(String id, double amount) {}

        
        public double convert(int curSource, int curTarget, double amount) {
            return 0.0;
        }
        
    }

    class Client {
        private String id;
        private String name;
    }

    
    class Converter {
        public double conv(int curSource, int curTarget, double amount) {
            return 0.0;
        }
    }
    

    
}