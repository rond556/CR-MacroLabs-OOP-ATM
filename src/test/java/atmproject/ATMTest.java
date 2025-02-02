package atmproject;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ATMTest {

    @Test
    public void runMainMenu() {
        String input = "8";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ATM atm = new ATM();
        User currentUser = new User("Jow","Byeden",0000);

        atm.runMainMenu(currentUser);

        assertFalse(atm.isRunning());

    }

    @Test
    public void checkBalance() {

        ATM atm = new ATM();
        UserLogin userLogin = new UserLogin();
        User currentUser = new User("Jow","Byeden",0000);

        userLogin.initialAccount(currentUser);
        atm.setCurrentUser(currentUser);



        Double actual = currentUser.getAccountList().get("Checkings Account#1").getBalance();
        Double expected = 0.0;

        Assert.assertEquals(actual,expected);

    }

    @Test
    public void displayHistoryTest1() {
        ATM atm = new ATM();
        User currentUser = new User(null,null,null);
        String expected = "You currently have no history.";
        Assert.assertEquals(expected,atm.displayHistory(currentUser));
    }

    @Test
    public void displayHistoryTest2() {
        ATM atm = new ATM();
        User currentUser = new User(null,null,null);
        String expected = "This is only a test.";
        currentUser.addToHistory("This is only a test.");

        Assert.assertEquals("**" + expected,atm.displayHistory(currentUser));
    }

    @Test
    public void returnTOLoginMenu() {
      ATM ATM = new ATM();

      ATM.returnToLoginMenu();

      assertFalse(ATM.isRunning());


    }






}