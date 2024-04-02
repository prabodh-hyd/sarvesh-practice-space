class BankAccount {
int Amount;
    int a=0;
    int b=1;
    void open() throws BankAccountActionInvalidException {
        if(a==0){
        Amount=0;
        a+=1;
            b=1;}
        else{
            throw new BankAccountActionInvalidException("Account already open");
        }
    }

    void close() throws BankAccountActionInvalidException {
        if(a==0){
            throw new BankAccountActionInvalidException("Account not open");
        }
        if(b==1){
            b--;
            a=0;
            Amount=0;
        }
        else if(b==0){
            throw new BankAccountActionInvalidException("Account not open");
        }
    }

    synchronized int getBalance() throws BankAccountActionInvalidException {
        if(b==1){
        return Amount;}
        else{
            throw new BankAccountActionInvalidException("Account closed");
        }
    }

    synchronized void deposit(int amount) throws BankAccountActionInvalidException {
        if(a==0){
            throw new BankAccountActionInvalidException("Account closed");
        }
        if(amount<0){
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }

        
         if(b==0){
            throw new BankAccountActionInvalidException("Account closed");
        }
        else{
            Amount +=amount;
        }
    }

    synchronized void withdraw(int amount) throws BankAccountActionInvalidException {

if(b==0){
    throw new BankAccountActionInvalidException("Account closed");
}
        
        if(Amount<amount){
            throw new BankAccountActionInvalidException("Cannot withdraw more money than is currently in the account");
        }
        else if(amount<0){
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }
        Amount-=amount;
    }

}