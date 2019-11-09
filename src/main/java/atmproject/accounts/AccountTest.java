package atmproject.accounts;


import atmproject.User;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test
    public void getCurrentUserTest() {
        User currentUser = new User("Sabin", "Figaro", 456456);
        Account account = new Account(currentUser);
        User expected = new User("Sabin", "Figaro", 456456);
        User actual = account.getCurrentUser();

        Assert.assertEquals(actual.getFirstName(),expected.getFirstName());
        }

    @Test
    public void setCurrentUser() {
        User currentUser = new User("Sabin", "Figaro", 456456);
        Account account = new Account(null);
        account.setCurrentUser(currentUser);

        Assert.assertEquals(account.getCurrentUser(), currentUser);
    }

    @Test
    public void getBalance() {
        Account account = new Account(null);
        Double expected = 0.0;
        Double actual = account.getBalance();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void setBalance() {
        Account account = new Account(null);
        Double expected = 100.0;
        account.setBalance(expected);

        Assert.assertEquals(expected,account.getBalance());
    }

    @Test
    public void addToBalance() {
        Account account = new Account(null);
        account.setBalance(100.0);
        Double input = 80.0;
        account.addToBalance(input);


        Double expected = 180.0;

        Assert.assertEquals(expected,account.getBalance());


    }

    @Test
    public void subtractFromBalance() {
        Account account = new Account(null);
        account.setBalance(100.0);
        Double input = 80.0;
        account.subtractFromBalance(input);


        Double expected = 20.0;

        Assert.assertEquals(expected,account.getBalance());
    }
}