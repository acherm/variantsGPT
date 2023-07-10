package com.example;

import java.util.List;

public class Variant {

    class Account {
        private String id;
        private double amount;
        
        
        
        
        private int currency;
        

        public void deposit(double amount) {}

        public double getAmount() {
            return 0.0;
        } // corrected!
        
        
        public void withdrawWithoutLimit(double amount) {}
        

        

        
        public int getCurrency() {
            return 0;
        }

        public void setCurrency(int currency) {}
        
    }

    class Bank {
        
        private List<Account> accounts;
        
        
        
        private Converter converter;
        
        
        
        private Consortium cons;
        

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
    

    
    class Consortium {
        // Empty class as per the description.
    }
    
}