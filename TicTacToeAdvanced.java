package TicTac;

/**
 * Created by saketp on 11/28/2015.
 */
public class TicTacToeAdvanced {
        public static void main(String[] args) {
            Player p1 = new Player("p1", 1, 0);
            Player p2 = new Player("p2", 2, 1);
            int numIter = 100000;
            int p1Wins = 0;
            int p2Wins = 0;
            for(int j = 0; j < numIter; j++) {
                TicTacToe t = new TicTacToe(p1, p2);

                for (int i = 0; i < 10; i++) {
                    //System.out.println("**************************");
                    //System.out.println("**************************");

                   // t.printBoard();
                    if (!t.move()) {
                        //System.out.println("tie");
                        break;
                    }
                    if (t.determineWin()) {
                        //System.out.println(t.getCurrent().getName() + " has won!");
                        if(t.getCurrent().getName().equals("p1")) {
                            p1Wins++;
                        } else {
                            p2Wins++;
                        }

                         //t.printBoard();
                        break;
                    }
                    t.switchPlayer();
                }
            }
            int ties = numIter - p1Wins - p2Wins;
            double winRate1 = (float)p1Wins/numIter;
            double winRate2 = (float)p2Wins/numIter;
            double tieRate = (float)ties/numIter;



            System.out.println("P1 wins: " + p1Wins + " P2 Wins: " + p2Wins + " Ties: " + ties);
            System.out.println("P1 win rate: " + winRate1 + " P2 win rate: " + winRate2 + " Tie rate: " + tieRate);
        }

}
