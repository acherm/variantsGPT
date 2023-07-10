package com.example;

import java.util.List;

public class Variant {

    class Account {
        private String id;
        private double amount;
        
        
        
        

        public void deposit(double amount) {}

        public double getAmount() {
            return 0.0;
        } // corrected!
        
        
        public void withdrawWithoutLimit(double amount) {}
        

        

        
    }

    class Bank {
        
        private Account accounts;
        
        
        
        
        
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