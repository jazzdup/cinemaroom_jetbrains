class Account {

    private long balance = 0;

    public synchronized boolean withdraw(long amount) {
        if (balance - amount > 0){
            balance = balance - amount;
            return true;
        }else{
            return false;
        }
    }

    public synchronized void deposit(long amount) {
        // do something useful
        balance = balance + amount;
    }

    public long getBalance() {
        return balance;
    }
}