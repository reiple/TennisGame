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

        ScoreType firstPlayerScoreType = covertToScoreType(firstPlayer.getScore());
        ScoreType secondPlayerScoreType = covertToScoreType(secondPlayer.getScore());

        if (isDeuce(firstPlayer, secondPlayer)) {
            // Extract method
            return processDeuce(firstPlayerScoreType);
        } else if (isLoveScore(firstPlayerScoreType) || isLoveScore(secondPlayerScoreType)) {
            // Extract method
            return processLoveScore(firstPlayer.getScore(), secondPlayer.getScore());
        }
        // Refactoring: Switch Statements문 제거. score에 따른 switching => Replace Type Code with SubClasses

        return firstPlayerScoreType.toString() + "-" + secondPlayerScoreType.toString();

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

    public String processLoveScore(int firstPlayerScore, int secondPlayerScore) {

        // Refactoring: 임시 변수(tmpScore) 제거
        int minusResult = firstPlayerScore - secondPlayerScore;

        if (minusResult == 1) return "Advantage player1";
        else if (minusResult == -1) return "Advantage player2";
        else if (minusResult >= 2) {
            isEnd = true;
            return "Win for player1";

        }

        isEnd = true;
        return "Win for player2";
    }

    public boolean isEnd() {
        return isEnd;
    }
}

/**
 * 목적:
 * TennisGame 클래스 내에 서로 다른 종류의 변수(player의 score)가 있으므로, 별도 클래스를 생성하고 변수를 옮겨 역할을 나눔.
 * Refactoring 기법: Extract Class
 */
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

/**
 * 목적:
 * scoretype이 다른 값이 함부로 쓰이지 않도록 막는다.
 * scoretype을 단순히 숫자가 아닌 이름을 부여함으로 인해 가독성을 높인다.
 * scoretype에 String배열을 사용해 기존에 사용하던 swich문을 없앤다.
 * 적용 Refactoring 기법: Replace Type Code with SubClasses
 * @see TennisGame1.getLiteralScore(), TennisGame1.processDeuce()
 */
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
