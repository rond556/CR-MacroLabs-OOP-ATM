package atmproject;


import atmproject.accounts.Account;
import atmproject.accounts.InvestmentsAccount;
import atmproject.accounts.SavingsAccount;


public class ATM {
    private Console console = new Console(System.in, System.out);
    private UserLogin userLogin = new UserLogin();
    private User currentUser;
    private boolean running = true;
    private AccountMenu accountMenu = new AccountMenu();
    private Language language = new Language();

    public void turnOnATM(){
        userLogin.runLoginMenu();
    }


    public void runMainMenu(User currentUser) {
        setCurrentUser(currentUser);
        while (running) {
            console.println(language.getLang(Language.LangKey.MAINMENU));
            Integer userInput = console.getIntegerInput(":");
            mainMenuLogic(userInput);
        }
    }

    public void mainMenuLogic(Integer userInput){
        switch (userInput) {
            case 1:
                String transaction = callDeposit();
                console.println(transaction);
                currentUser.addToHistory(transaction + "\n");
                break;
            case 2:
                transaction = callWithdraw();
                console.println(transaction);
                currentUser.addToHistory(transaction + "\n");
                break;
            case 3:
                if (currentUser.getAccountList().size() >= 2) {
                    transaction = callTransfer();
                    console.println(transaction);
                    currentUser.addToHistory(transaction + "\n");
                } else {
                    console.println("ERROR: Not enough accounts to transfer money. Please create a new one.");
                }
                break;
            case 4:
                console.println(checkBalance());
                break;
            case 5:
                console.println(displayHistory(currentUser));
                break;
            case 6:
                callCreateAccount();
                break;
            case 7:
                callRemoveAccount();
                break;
            case 8:
                returnToLoginMenu();
                break;
        }
        giveInterest();
    }

    public String callDeposit() {
        Account chosenAccount = accountMenu.selectAccount(currentUser);
        console.println("How much would you like to deposit?");
        Double userInput = console.getDoubleInput("$");
        chosenAccount.deposit(userInput);
        return String.format("You deposited $%.2f to %s.", userInput, chosenAccount.getAccountName());
    }

    public String callWithdraw() {
        Account chosenAccount = accountMenu.selectAccount(currentUser);
        console.println("How much would you like to withdraw?");
        Double userInput = console.getDoubleInput("$");
        if(userInput <= chosenAccount.getBalance()){
            chosenAccount.withdraw(userInput);
            return String.format("You withdrew $%.2f from %s.\n", userInput, chosenAccount.getAccountName());
        }
        return "ERROR: insufficient funds for withdraw.\n";
    }

    public String callTransfer(){
        Account chosenAccount = accountMenu.selectAccount(currentUser);
        Account destinationAccount = accountMenu.selectAccount(currentUser);
        if(chosenAccount.equals(destinationAccount)){
            return "ERROR: Incorrect account entry\n";
        } else {
            console.println("How much would you like to transfer?");
            Double userInput = console.getDoubleInput("$");
            if(userInput <= chosenAccount.getBalance()){
                chosenAccount.transfer(destinationAccount, userInput);
                return String.format("You transferred $%.2f from %s to %s.\n",userInput, chosenAccount.getAccountName(), destinationAccount.getAccountName());
            }
            return "ERROR: Insufficient funds for transfer.\n";
        }
    }

    public void callCreateAccount(){
        console.println("What kind of account would you like to create?\n(1) - Checkings\n(2) - Savings\n(3) - Investments");
        Integer typeOfAccount = console.getIntegerInput(":");
        switch(typeOfAccount) {
            case 1:
                Account account = new Account(currentUser, 0.0, "Checkings Account#" + (currentUser.getAccountList().size() + 1));
                currentUser.addAccount(account);
                break;
            case 2:
                SavingsAccount savingsAccount = new SavingsAccount(currentUser, 0.0, "Savings Account#" + (currentUser.getAccountList().size() + 1));
                currentUser.addAccount(savingsAccount);
                break;
            case 3:
                createInvestmentAccount();
                break;

        }
    }

    public void callRemoveAccount() {
        Account chosenAccount = accountMenu.selectAccount(currentUser);
        if(currentUser.getAccountList().containsValue(chosenAccount)){
            currentUser.removeAccount(chosenAccount);
        } else {
            console.println("ERROR: Chosen account not found.");
        }
    }

    private void createInvestmentAccount() {
        console.println("At this bank, you'll need an initial deposit of $15,000 to open an investment account.\nHow " +
                "much would you like to deposit?");
        Integer input = console.getIntegerInput(":");
        if(input > 14999) {
            InvestmentsAccount investmentsAccount = new InvestmentsAccount(currentUser, (double)input, "Investment Account#" + (currentUser.getAccountList().size() + 1));
            currentUser.addAccount(investmentsAccount);
        } else {
            console.println("That is not enough of an initial deposit");
        }
    }

    public String checkBalance() {
        Account chosenAccount = accountMenu.selectAccount(currentUser);
        return String.format("The balance in %s is $%.2f\n", chosenAccount.getAccountName(), chosenAccount.getBalance());
    }

    public String displayHistory(User currentUser) {
        if(currentUser.getTransactionHistory().isEmpty()){
            return "You currently have no history.";
        }
        return currentUser.displayHistory();
    }

    public void returnToLoginMenu() {
        running = false;
    }

    public void giveInterest(){
        for(String name : currentUser.getAccountList().keySet()){
            if(currentUser.getAccountList().get(name) instanceof SavingsAccount){
                ((SavingsAccount) currentUser.getAccountList().get(name)).addInterest();
            }
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isRunning() {
        return running;
    }
}