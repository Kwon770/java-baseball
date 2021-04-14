package baseballGame;

import java.util.*;

class BallAvailbilty {
    private boolean isBoB;
    private int strikeCnt;
    private int ballCnt;

    public BallAvailbilty(boolean isBob) {
        this.isBoB = isBob;
        this.strikeCnt = 0;
        this.ballCnt = 0;
    }

    public BallAvailbilty(int strikeCnt, int ballCnt) {
        this.isBoB = false;
        this.strikeCnt = strikeCnt;
        this.ballCnt = ballCnt;
    }

    public boolean getIsBob() {
        return isBoB;
    }

    public int getStrikeCnt() {
        return strikeCnt;
    }

    public int getBallCnt() {
        return ballCnt;
    }
}

public class Main {

    public static boolean askWillRetry() {
        System.out.print("게암을 새로 시작하려면 1, 종료하려면 2를 입력하세요. ");
        Scanner console = new Scanner(System.in);
        int input = console.nextInt();

        if (input == 1) return true;
        else return false;
    }

    public static boolean returnIsPlayerWin(BallAvailbilty ballAvailbilty) {
        if (ballAvailbilty.getStrikeCnt() == 3) return true;
        else return false;
    }

    public static void printResult(BallAvailbilty ballAvailbilty) {
        if (ballAvailbilty.getStrikeCnt() == 3) {
            System.out.println("3 스트라이크 아웃");
        } else if (ballAvailbilty.getIsBob()) {
            System.out.println("볼넷");
        } else {
            if (ballAvailbilty.getStrikeCnt() != 0) System.out.print(ballAvailbilty.getStrikeCnt() + " 스트라이크 ");
            if (ballAvailbilty.getBallCnt() != 0) System.out.print(ballAvailbilty.getBallCnt() + " 볼");
            System.out.println();
        }
    }

    public static int returnStrikeCntAndMakeBallCache(int[] computerBall, int[] playerBall, int[] ballCache) {
        int cnt = 0;
        for (int i = 0; i < computerBall.length; i++) {
            if (computerBall[i] == playerBall[i]) cnt++;
            else ballCache[computerBall[i]]++;
        }

        return cnt;
    }

    public static int returnBallCnt(int[] playerBall, int[] ballCache) {
        int cnt = 0;
        for (int i = 0; i < playerBall.length; i++) {
            if (ballCache[playerBall[i]] <= 0) continue;

            cnt++;
            ballCache[playerBall[i]]--;
        }

        return cnt;
    }

    public static BallAvailbilty returnBallAvailability(int[] computerBall, int[] playerBall) {
        int[] ballCache = new int[10];
        int strikeCnt = returnStrikeCntAndMakeBallCache(computerBall, playerBall, ballCache);
        int ballCnt = returnBallCnt(playerBall, ballCache);

        if (strikeCnt == 0 && ballCnt == 0) return new BallAvailbilty(true);
        else return new BallAvailbilty(strikeCnt, ballCnt);
    }

    public static int[] returnRandomBall() {
        int[] ball = new int[3];
        for (int i = 0; i < 3; i++) {
            ball[i] = (int) (Math.random() * 10);
        }

        return ball;
    }

    public static int[] returnInputBall() {
        Scanner console = new Scanner(System.in);

        int input = console.nextInt();
        int[] inputBall = new int[3];
        for (int i = 2; i >= 0; i--) {
            inputBall[i] = input % 10;
            input /= 10;
        }

        return inputBall;
    }

    public static void playGame() {
        int[] computerBall = returnRandomBall();

        for (int cnt = 0; cnt < 9; cnt++) {
            System.out.print("숫자를 입력해주세요 : ");
            int[] playerBall = returnInputBall();

            BallAvailbilty ballAvailbilty = returnBallAvailability(computerBall, playerBall);

            printResult(ballAvailbilty);

            if (returnIsPlayerWin(ballAvailbilty)) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 당신의 승리입니다.");
                return;
            }
        }

        System.out.println("9번의 시도 동안 맞히지 못했습니다! 당신의 패배입니다.");
        return;
    }

    public static void main(String[] args) {
        boolean willRetry;
        do {
            playGame();

            willRetry = askWillRetry();
        } while (willRetry);
    }
}
