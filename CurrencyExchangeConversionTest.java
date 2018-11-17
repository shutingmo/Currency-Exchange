import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;


public class CurrencyExchangeConversionTest {

    static CurrencyExchange cex = new CurrencyExchange();
    static Field balance_privateField;

    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout


    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException {
        balance_privateField = CurrencyExchange.class.getDeclaredField("balance");
        balance_privateField.setAccessible(true);
    }

    @Before
    public void setUp() throws IllegalAccessException {
        balance_privateField.set(cex, 0);
    }

    /***********************
     * conversion tests
     ***********************/

    @Test
    public void givenUsdThenNoConversion() throws Exception {
        double amount = 1234.32;
        assertEquals(amount, CurrencyExchange.convertCurrency(amount, 1, true), 0.1);
        assertEquals(amount, CurrencyExchange.convertCurrency(amount, 1, false), 0.1);
    }

    @Test
    public void givenEuroThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 0.89;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 2, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 2, false), 0.1);
    }

    @Test
    public void givenBRPThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 0.78;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 3, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 3, false), 0.1);
    }

    @Test
    public void givenINRThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 66.53;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 4, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 4, false), 0.1);
    }

    @Test
    public void givenAUDThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 1.31;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 5, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 5, false), 0.1);
    }

    @Test
    public void givenCNDThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 1.31;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 6, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 6, false), 0.1);
    }

    @Test
    public void givenSGDThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 1.37;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 7, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 7, false), 0.1);
    }

    @Test
    public void givenSWFThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 0.97;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 8, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 8, false), 0.1);
    }

    @Test
    public void givenMLRThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 4.12;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 9, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 9, false), 0.1);
    }

    @Test
    public void givenJPYThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 101.64;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 10, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 10, false), 0.1);
    }

    @Test
    public void givenCHRThenConversion() throws Exception {
        double amount = 1234.56;
        double rate = 6.67;
        // to USD
        assertEquals(amount / rate, CurrencyExchange.convertCurrency(amount, 11, true), 0.1);
        //from USD
        assertEquals(amount * rate, CurrencyExchange.convertCurrency(amount, 11, false), 0.1);
    }
}
