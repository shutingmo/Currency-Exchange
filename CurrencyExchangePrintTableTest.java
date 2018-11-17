import org.junit.*;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class CurrencyExchangePrintTableTest {

    static CurrencyExchange cex = new CurrencyExchange();
    static Method crrMenu_privateMethod;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout


    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException, NoSuchMethodException {
        crrMenu_privateMethod = CurrencyExchange.class.getDeclaredMethod("printConversionTable");
        crrMenu_privateMethod.setAccessible(true);
    }

    @Before
    public void setUp() throws IllegalAccessException {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        // reset the System.out to a new FileDexcriptor, to print at console
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    /********************
     * Menu option tests
     ********************/

    //currencyMenuOptionSelector
    @Test
    public void givenInputsThenReturnValidMenuOptions() throws Exception {
        crrMenu_privateMethod.invoke(cex);
        String content = outContent.toString();

        assertTrue(content.contains("1.00"));
        assertTrue(content.contains("0.89"));
        assertTrue(content.contains("0.78"));
        assertTrue(content.contains("66.53"));
        assertTrue(content.contains("1.31"));
        assertTrue(content.contains("1.37"));
        assertTrue(content.contains("0.97"));
        assertTrue(content.contains("4.12"));
        assertTrue(content.contains("101.64"));
        assertTrue(content.contains("6.67"));
        assertTrue(content.toLowerCase().contains("Dollar".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Euro".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Pound".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Rupee".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Ringgit".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Yen".toLowerCase()));
        assertTrue(content.toLowerCase().contains("Chinese".toLowerCase()));
    }

}