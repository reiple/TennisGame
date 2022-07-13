import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TennisResultTest {

    private String serverScore;
    private String receiverScore;
    private String outputValue;

    public TennisResultTest(String serverScore, String receiverScore, String outputValue) {
        this.serverScore = serverScore;
        this.receiverScore = receiverScore;
        this.outputValue = outputValue;
    }

    @Parameterized.Parameters(name = "{0}:{1} = {2}")
    public static Collection<Object[]> getAllScores() {
        return Arrays.asList(new Object[][]{
                        {"", "Love", "Love-All"},
                        {"", "Fifteen", "Fifteen-All"},
                        {"", "Thirty", "Thirty-All"},
                        {"Deuce", "", "Deuce"},
                        {"Fifteen","Love", "Fifteen-Love"},
                        {"Love","Fifteen", "Love-Fifteen"},
                        {"Thirty","Love", "Thirty-Love"},
                        {"Love","Thirty", "Love-Thirty"},
                        {"Forty","Love", "Forty-Love"},
                        {"Love","Forty", "Love-Forty"},
                        {"Thirty","Fifteen", "Thirty-Fifteen"},
                        {"Fifteen","Thirty", "Fifteen-Thirty"},
                        {"Forty","Fifteen", "Forty-Fifteen"},
                        {"Fifteen","Forty", "Fifteen-Forty"},
                        {"Forty","Thirty", "Forty-Thirty"},
                        {"Thirty","Forty", "Thirty-Forty"}
                }
        );
    }

    @Test
    public void checkAllScores() {

        String serverScore = this.serverScore;
        String receiverScore = this.receiverScore;

        TennisResult tennisResult = new TennisResult(serverScore, receiverScore);
        assertEquals(this.outputValue, tennisResult.toString());


    }
}
