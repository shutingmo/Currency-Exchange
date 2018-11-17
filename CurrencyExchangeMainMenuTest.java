import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Permission;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class CurrencyExchangeMainMenuTest {

    static CurrencyExchange cex = new CurrencyExchange();
    static Method mainMenu_privateMethod;
    private Scanner scanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public Timeout globalTimeout = Timeout.millis(1500); // 1500 milliseconds for timeout


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0 5 1", 1}, {"20 -1 2", 2}, {"3", 3}, {"4", 4}
        });
    }

    @Parameter // value 0 by default
    public String inputContent;

    @Parameter(value = 1)
    public int expected;


    @BeforeClass
    public static void oneTimeSetup() throws NoSuchFieldException, NoSuchMethodException {
        mainMenu_privateMethod = CurrencyExchange.class.getDeclaredMethod("mainMenuOptionSelector", Scanner.class);
        mainMenu_privateMethod.setAccessible(true);
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

    //mainMenuOptionSelector
    @Test
    public void givenInputsThenReturnValidMenuOptions() throws InvocationTargetException, IllegalAccessException {
        System.setIn(new ByteArrayInputStream(inputContent.getBytes()));
        scanner = new Scanner(System.in);

        SecurityManager sm = System.getSecurityManager();
        MySecurityManager secManager = new MySecurityManager();
        System.setSecurityManager(secManager);

        try {
            assertEquals(expected, mainMenu_privateMethod.invoke(cex, scanner));
        } catch (SecurityException e){
            // When system exit hits, test fails
            fail();
        }finally {
            System.setSecurityManager(sm);
        }
    }

}

/**
 * Override Security manager to suppress System exit in some code
 */
class MySecurityManager extends SecurityManager {
    @Override public void checkExit(int status) {
        throw new SecurityException();
    }

    @Override
    public void checkPermission(Permission perm) {
        return;
    }
}