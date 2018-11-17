import org.junit.*;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CurrencyExchangeWithdrawTest {

    static CurrencyExchange cex = new CurrencyExchange();
    static Field balance_privateField;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout

    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException {
        balance_privateField = CurrencyExchange.class.getDeclaredField("balance");
        balance_privateField.setAccessible(true);
    }

    @Before
    public void setUp() throws IllegalAccessException {
        // redirect the output stream to suppress unnecessary console output
        System.setOut(new PrintStream(outContent));
        balance_privateField.set(cex, 0);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
//        System.setErr(null);
    }

    /*****************************
     * withdraw tests
     *****************************/
    @Test
    public void givenNegativeNZeroAmountWhenWithdrawThenFailureNBalanceNotChanged() throws Exception {
        balance_privateField.set(cex, 123.0);
        // negative amount will cause failure
        boolean success = CurrencyExchange.withdraw(-200, 1);
        // zero amount will cause failure
        success = success || CurrencyExchange.withdraw(0, 1);
        assertFalse(success);
        // balance should not be changed
        assertEquals(123.0, CurrencyExchange.getBalance(), 0.1);
    }

    @Test
    public void givenInvalidCurrencyTypeWhenWithdrawThenFailureAndBalanceNotChanged() throws Exception {
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
    public void givenUSDWhenOverdraftThenFailure() throws Exception {
        double balance = 1234.0;
        balance_privateField.set(cex, balance);
        boolean success;
        success = CurrencyExchange.withdraw(1234.1, 1);
        assertFalse(success);
        assertEquals(balance, CurrencyExchange.getBalance(), 0.5);
    }

    @Test
    public void givenSGDWhenOverdraftThenFailure() throws Exception {
        int balance = 4970;
        balance_privateField.set(cex, balance);
        boolean success;
        success = CurrencyExchange.withdraw(6789, 7);
        assertFalse(success);
        assertEquals(balance, CurrencyExchange.getBalance(), 0.5);
    }

    @Test
    public void givenSWFWhenOverdraftThenFailure() throws Exception {
        int balance = 7020;
        balance_privateField.set(cex, balance);
        boolean success;
        success = CurrencyExchange.withdraw(6789, 8);
        assertFalse(success);
        assertEquals(balance, CurrencyExchange.getBalance(), 0.5);
    }

    @Test
    public void givenUSDWhenWithdrawThenNoConversionNWithdraw() throws Exception {
        balance_privateField.set(cex, 1234.0);
        boolean success;
        success = CurrencyExchange.withdraw(123.0, 1);
        assertTrue(success);
        assertEquals(1111.0, CurrencyExchange.getBalance(), 0.1);
    }

    @Test
    public void givenBPDWhenWithdrawThenConversionNWithdraw() throws Exception {
        balance_privateField.set(cex, 12345.0);
        boolean success;
        success = CurrencyExchange.withdraw(5432.0, 3);
        assertTrue(success);
        // if no convenience fee, it will be 5380.90
        assertEquals(5346.08, CurrencyExchange.getBalance(), 1);
    }

    @Test
    public void givenCHRWhenWithdrawThenConversionNWithdraw() throws Exception {
        balance_privateField.set(cex, 12345.0);
        boolean success;
        success = CurrencyExchange.withdraw(5432.0, 11);
        assertTrue(success);
        // if no convenience fee, it will be 11530.61
        assertEquals(11526.56, CurrencyExchange.getBalance(), 1);
    }

    @Test
    public void givenMLRWhenWithdrawThenConversionNWithdraw() throws Exception {
        balance_privateField.set(cex, 12345.0);
        boolean success;
        success = CurrencyExchange.withdraw(5432.0, 9);
        assertTrue(success);
        // if no convenience fee, it will be 11026.55
        assertEquals(11019.97, CurrencyExchange.getBalance(), 1);
    }

    @Test
    public void givenAUDNBRPWhenWithdrawAfterDepositThenSuccessAndBalanceChange() throws Exception {
        balance_privateField.set(cex, 1000.0);
        boolean success;
        success = CurrencyExchange.deposit(2345, 5);
        success = success && CurrencyExchange.withdraw(1357, 3);
        assertTrue(success);
        // the remaining balance should be round(round(1000+2345/1.31) - 1357/0.78*1.005) ~ 1041.64
        assertEquals(1041.64, CurrencyExchange.getBalance(), 5); // generous tolerance threshold
    }
}
