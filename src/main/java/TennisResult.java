public class TennisResult {
    private String serverScore;
    private String receiverScore;

    public TennisResult(String serverScore, String receiverScore) {
        this.serverScore = serverScore;
        this.receiverScore = receiverScore;
    }

    @Override
    public String toString() {
        if("".equals(receiverScore))
            return serverScore;
        if("".equals(serverScore))
            return receiverScore + "-All";

        return "" + this.serverScore + "-" + this.receiverScore;
    }
}
