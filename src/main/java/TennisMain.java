import java.util.Random;

public class TennisMain {

    public static void main(String[] args) {

        String player1 = "leek";
        String player2 = "lily";

        TennisGame game = new TennisGame1(player1, player2);
        playGame(game);

    }

    public static void playGame(TennisGame tennisGame) {

        Random random = new Random();

        while (!tennisGame.isEnd()) {
            if (random.nextBoolean()) {
                tennisGame.wonPointFirstPlayer();
            } else {
                tennisGame.wonPointSecondPlayer();
            }
            System.out.println(tennisGame.getLiteralScore());
        }
    }
}
