import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CurrencyExchangeCurrencyMenuTest {

    static CurrencyExchange cex = new CurrencyExchange();
    static Method crrMenu_privateMethod;
    private Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0 12 1", 1}, {"15 -1 2", 2}, {"3", 3}, {"4", 4}, {"5", 5},
                {"6", 6}, {"7", 7}, {"8", 8}, {"9", 9}, {"10", 10}, {"11", 11}
        });
    }

    @Parameter // value 0 by default
    public String inputContent;

    @Parameter(value = 1)
    public int expected;

    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException, NoSuchMethodException {
        crrMenu_privateMethod = CurrencyExchange.class.getDeclaredMethod("currencyMenuOptionSelector", Scanner.class);
        crrMenu_privateMethod.setAccessible(true);
    }

    @Before
    public void setUp() throws IllegalAccessException {
        // redirect the output stream to suppress unnecessary console output
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setIn(System.in);
    }

    /********************
     * Menu option tests
     ********************/

    //currencyMenuOptionSelector
    @Test
    public void givenInputsThenReturnValidMenuOptions() throws Exception {
        System.setIn(new ByteArrayInputStream(inputContent.getBytes()));
        scanner = new Scanner(System.in);

        assertEquals(expected, crrMenu_privateMethod.invoke(cex, scanner));
    }

}
