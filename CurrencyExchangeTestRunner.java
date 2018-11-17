import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CurrencyExchangeTestRunner {

    public static void main(String[] args) {
        final boolean VERBOSE;
        if (args.length == 1 && args[0].toLowerCase().equals("verbose")) {
            VERBOSE = true;
        } else {
            VERBOSE = false;
        }


        double dptScore = 30, wdtScore = 35, cvtScore = 25, mmtScore = 4, cmtScore = 4, prtScore = 2;
        ArrayList<String> reportContents = new ArrayList<>();

        RunTest dptResult = new RunTest(CurrencyExchangeDepositTest.class, dptScore, VERBOSE);
        RunTest wdtResult = new RunTest(CurrencyExchangeWithdrawTest.class, wdtScore, VERBOSE);
        RunTest cvtResult = new RunTest(CurrencyExchangeConversionTest.class, cvtScore, VERBOSE);
        RunTest mmtResult = new RunTest(CurrencyExchangeMainMenuTest.class, mmtScore, VERBOSE);
        RunTest cmtResult = new RunTest(CurrencyExchangeCurrencyMenuTest.class, cmtScore, VERBOSE);
        RunTest prtResult = new RunTest(CurrencyExchangePrintTableTest.class, prtScore, VERBOSE);

        reportContents.add("Score:"
                + (dptResult.getActualScore()
                + wdtResult.getActualScore()
                + cvtResult.getActualScore()
                + mmtResult.getActualScore()
                + cmtResult.getActualScore()
                + prtResult.getActualScore())
        );
        reportContents.add(dptResult.toString());
        reportContents.add(wdtResult.toString());
        reportContents.add(cvtResult.toString());
        reportContents.add(mmtResult.toString());
        reportContents.add(cmtResult.toString());
        reportContents.add(prtResult.toString());

        Path file = Paths.get("TestResult.txt");
        try {
            Files.write(file, reportContents, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(reportContents);
    }

}

class RunTest {
    private int runCount, failureCount;
    private double fullScore;
    private Result testResult;
    private String testName;
    private boolean isVerbose;

    public RunTest(Class TestClass, double fullScore, boolean isVerbose) {
        this.testResult = JUnitCore.runClasses(TestClass);
        this.testName = TestClass.getName();
        this.runCount = testResult.getRunCount();
        this.failureCount = testResult.getFailureCount();
        this.fullScore = fullScore;
        this.isVerbose = isVerbose;
    }

    public int getRunCount() {
        return runCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public long getActualScore() {
        if (runCount == 0)
            return 0;
        else
            return Math.round((runCount - failureCount) / ((double) runCount) * this.fullScore);
    }

    public Result getTestResult() {
        return testResult;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n" + this.testName + ":\n");
        sb.append("\tScore:" + this.getActualScore() + " out of " + this.fullScore + "\n");
        if (runCount == 0) {
            // this means the test failed at setup stage. Most possible reason is signature error
            sb.append("\tThe method signature in source code maybe incorrect.\n");
        } else {
            sb.append("\tTest Cases Total:" + this.runCount + " Failure:" + this.failureCount + "\n");
            if (!testResult.wasSuccessful()) {
                // if failure happens, give more details(tell which cases failed)
                sb.append("\tFailed Test Cases:\n");
                for (Failure failure : testResult.getFailures()) {
                    if (isVerbose)
                        sb.append("\t\t" + failure.getDescription().getMethodName() + " " + failure.getMessage() + "\n");
                    else
                        sb.append("\t\t" + failure.getDescription().getMethodName() + "\n");

                }
            }
        }
        return sb.toString();
    }
}