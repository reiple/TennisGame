public class TennisGame1 implements TennisGame {

    // 목적: TennisMain.java에 있는 플레이어의 이름이 변경되었을 때 다른 게임들에서도 플레이어의 이름이 변경되도록 한다.
    // 적용 Refactoring 기법: Change Value to Reference
    private Player firstPlayer;
    private Player secondPlayer;

    private boolean isEnd = false;

    public TennisGame1(String playerName1, String playerName2) {
        firstPlayer = new Player(playerName1);
        secondPlayer = new Player(playerName2);
    }


    public void wonPointFirstPlayer() {
        firstPlayer.wonPoint();
    }
    public void wonPointSecondPlayer() {
        secondPlayer.wonPoint();
    }

    private ScoreType covertToScoreType(int score) {
        return new ScoreType(score);
    }

    private boolean isDeuce(Player firstPlayer, Player secondPlayer) {
        return firstPlayer.getScore() == secondPlayer.getScore() ? true : false;
    }

    private boolean isLoveScore(ScoreType scoreType) {
        return scoreType.toString().equals(ScoreType.DeuceScoreGroup.toString());
    }

    public String getLiteralScore() {
        String score = "";

        int player1Score = firstPlayer.getScore();
        int player2Score = secondPlayer.getScore();
        ScoreType firstPlayerScoreType = covertToScoreType(firstPlayer.getScore());
        ScoreType secondPlayerScoreType = covertToScoreType(secondPlayer.getScore());

        if (isDeuce(firstPlayer, secondPlayer)) {
            // Extract method
            score = processDeuce(firstPlayerScoreType);
        } else if (isLoveScore(firstPlayerScoreType) || isLoveScore(secondPlayerScoreType)) {
            // Extract method
            score = processLoveScore(player1Score, player2Score, score);
        } else {
            // Refactoring: Switch Statements문 제거. score에 따른 switching => Replace Type Code with SubClasses
            score += firstPlayerScoreType.toString() + "-" + secondPlayerScoreType.toString();
        }

        return score;
    }

    public String processDeuce(ScoreType scoreType) {

        // score를 ScoreType으로 변경
        // Refactoring: Switch Statements문 제거. score에 따른 switching => Replace Type Code with SubClasses
        if (scoreType.getScore() < ScoreType.FortyScoreGroup.getScore()) {
            return scoreType + "-All";
        } else {
            return new String("Deuce");
        }
    }

    public String processLoveScore(int p1, int p2, String score) {

        // Refactoring: 임시 변수(tmpScore) 제거
        int minusResult = p1 - p2;

        if (minusResult == 1) return "Advantage player1";
        else if (minusResult == -1) return "Advantage player2";
        else if (minusResult >= 2) {
            isEnd = true;
            return "Win for player1";

        } else {
            isEnd = true;
            return "Win for player2";
        }
    }

    public boolean isEnd() {
        return isEnd;
    }
}

// 목적: TennisGame 클래스 내에 서로 다른 종류의 변수(player의 score)가 있으므로,
//      별도 클래스를 생성하고 변수 일부를 옮겨서 역할을 나눈다
// Refactoring 기법: Extract Class
class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        resetScore();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void wonPoint() {
        this.score += 1;
    }

}

//// Refactoring: Replace Type Code with SubClasses
//class ScoreType {
//    public static final ScoreType LoveScoreType = new ScoreType(0);
//    public static final ScoreType FitTeenScoreType = new ScoreType(1);
//    public static final ScoreType ThirtyScoreType = new ScoreType(2);
//    public static final ScoreType ForScoreType = new ScoreType(3);
//    public static final ScoreType DeuceScoreType = new ScoreType(4);
//
//    int score;
//
//    public ScoreType(int score) {
//        this.score = score;
//    }
//}

class ScoreType {
    public static final String[] scoreGroups = {"Love", "Fifteen", "Thirty", "Forty", "Deuce"};
    public static final ScoreType LoveScoreGroup = new ScoreType(0);
    public static final ScoreType FitTeenScoreGroup = new ScoreType(1);
    public static final ScoreType ThirtyScoreGroup = new ScoreType(2);
    public static final ScoreType FortyScoreGroup = new ScoreType(3);
    public static final ScoreType DeuceScoreGroup = new ScoreType(4);

    int score;

    public ScoreType(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        if(score >= 4) {
            return scoreGroups[4];
        }
        return scoreGroups[score];
    }

    public int getScore() {
        return score;
    }
}


//enum ScoreNum { // 상수를 enum으로 변경
//    Love(0), Fifteen(1), Thirty(2), Forty(3), Game(4);
//
//    private final int value;
//
//    private ScoreNum(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//}
