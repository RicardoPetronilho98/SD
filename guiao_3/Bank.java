public interface Bank {

    int createAccount(float initialBalance);
    float closeAccount(int id) throws InvalidAccountException;
    void transfer(int from, int to, float amount) throws InvalidAccountException, NotEnoughFundsException;
    float totalBalance(int accounts[]);
}