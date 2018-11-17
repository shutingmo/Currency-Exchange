import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.lang.reflect.Field;
import static org.junit.Assert.*;


public class CurrencyExchangeDepositTest {
    static CurrencyExchange cex = new CurrencyExchange();
    static Field balance_privateField;

    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout


    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException {
        balance_privateField = CurrencyExchange.class.getDeclaredField("balance");
        balance_privateField.setAccessible(true);
    }


    /******************
     * deposit tests
     ******************/

    @Test
    public void givenNegativeNZeroAmountWhenDepositThenFailureNBalanceNotChanged() throws Exception {
        balance_privateField.set(cex, 123.0);
        // negative amount will cause failure
        boolean success = CurrencyExchange.deposit(-200, 1);
        // zero amount will cause failure
        success = success || CurrencyExchange.deposit(0, 1);
        assertFalse(success);
        // balance should not be changed
        assertEquals(123.0, CurrencyExchange.getBalance(), 0.1);
    }

    @Test
    public void givenInvalidCurrencyTypeWhenDepositThenFailureAndBalanceNotChanged() throws Exception {
        balance_privateField.set(cex, 123.0);

        // >11 currency type will cause failure
        boolean success;
        success = CurrencyExchange.deposit(200, 12);
        // <1 currency type will cause failure
        success = success || CurrencyExchange.deposit(200, 0);
        assertFalse(success);

        // balance should not be changed, keep 0.0
        assertEquals(123.0, CurrencyExchange.getBalance(), 0.1);
    }

    @Test
    public void givenUSDWhenDepositThenNoConversionNDeposit() throws Exception {
        balance_privateField.set(cex, 123.0);
        boolean success;
        success = CurrencyExchange.deposit(1234.0, 1);
        assertTrue(success);
        assertEquals(1357.0, CurrencyExchange.getBalance(), 0.1);
    }

    @Test
    public void givenEuroWhenDepositThenConversionNDeposit() throws Exception {
        balance_privateField.set(cex, 123.0);
        boolean success;
        success = CurrencyExchange.deposit(2345, 2);
        assertTrue(success);
        assertEquals(2757.83, CurrencyExchange.getBalance(), 0.5);
    }

    @Test
    public void givenAUDWhenDepositThenConversionNDeposit() throws Exception {
        balance_privateField.set(cex, 123.0);
        boolean success;
        success = CurrencyExchange.deposit(3456, 6);
        assertTrue(success);
        
        assertEquals(2761.17, CurrencyExchange.getBalance(), 0.5);
    }

    @Test
    public void givenJPYWhenDepositThenConversionNDeposit() throws Exception {
        balance_privateField.set(cex, 123.0);
        boolean success;
        success = CurrencyExchange.deposit(6789, 10);
        assertTrue(success);
        assertEquals(189.79, CurrencyExchange.getBalance(), 0.5);
    }
}
