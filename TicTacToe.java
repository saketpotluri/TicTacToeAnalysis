package TicTac;

import java.util.ArrayList;


public class TicTacToe {
   private int[][] board;
   private Player p1, p2, current;

    public TicTacToe(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        board = new int[3][3];
        determineFirst(p1, p2);
    }

    private ArrayList<Point> getPossibleMoves() {
        ArrayList<Point> list = new ArrayList<>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length;j++) {
                if(board[i][j] == 0) {
                    list.add(new Point(i,j));
                }
            }
        }
       // System.out.println(list);
        return list;
    }

    public Player getCurrent() {
        return current;
    }

    public void setCurrent(Player current) {
        this.current = current;
    }

    private Point randomlyChosenMove() {
        ArrayList<Point> list = getPossibleMoves();
        if(list.size() == 0) {
            return null;
        }
        int index = (int)(Math.random() * list.size()); //between 0 and size - 1
        return list.get(index);
    }

    public boolean determineWin() {
       return determineWinHor(current) || determineWinDiag(current) || determineWinVert(current);
    }

    private boolean determineWinOther() {
        Player ot = getOther();
        return determineWinHor(ot) || determineWinDiag(ot) || determineWinVert(ot);

    }

    private boolean determineWinHor(Player p) {
        //make a slight efficiency by only checking full rows/cols/diags that
        //involve the last picked spot.
        int symbol = p.getSymbol();
        for(int i = 0; i < board.length; i++) {
            boolean flag = true;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != symbol) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                // .println("Horizontal win");
                return true;
            }
        }
        return false;
    }


    private boolean determineWinVert(Player p) {
        //make a slight efficiency by only checking full rows/cols/diags that
        //involve the last picked spot.
        int symbol = p.getSymbol();
        for(int i = 0; i < board.length; i++) {
            boolean flag = true;
            for (int j = 0; j < board[i].length; j++) {
                if (board[j][i] != symbol) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
               // System.out.println("vert win");

                return true;
            }
        }
        return false;
    }

    private boolean determineWinDiag(Player p) {
        //make a slight efficiency by only checking full rows/cols/diags that
        //involve the last picked spot.
        int symbol = p.getSymbol();
        boolean won = false;
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) // \ diagonal
                | (board[2][0] == symbol && board[1][1] == symbol && board[0][2] == symbol)) {
            won = true;
            //System.out.println("Diag win");
        }
        return won;
    }

    private Player determineFirst(Player p1, Player p2) {
        //random algorithm
        int val = (int) (Math.random()  * 2);
        current = val == 0 ? p1 : p2;
        current = p2; //comment this out for random algorithm
        return current;
    }

    public Player switchPlayer() {
        if(current == p1) {
            current = p2;
        } else {
            current = p1;
        }
        return current;
    }
    private boolean place() {
        Point p = randomlyChosenMove();
        if(p == null) {
          return false;
        }
        int symbol = current.getSymbol();
        board[p.getX()][p.getY()] = symbol;
        return true;
    }

    private Player getOther() {
       return current == p1 ? p2 : p1;
    }

    public boolean move() {
        if(getCurrent().getStrategy() == 0) {
            return place();
        } else {
            if(p1.getStrategy() == 1 && p1.getStrategy() == 1) {
                return placeSmart(true); //include some less optimal ways in order to prevent constant ties
            } else {
               return placeSmart(false);
            }
        }
    }

    private boolean placeSmart(boolean dumb) {
        ArrayList<Point> list = getPossibleMoves();
        if(list.size() == 0) {
            return false;
        }
        Player play = getOther();
        int sym = play.getSymbol();
        int maxZ = 0;
        Point best = null;
        for(Point p: list) {
            //System.out.print(p);
            //check if current player can win
            board[p.getX()][p.getY()] = current.getSymbol();
            if(determineWin()) {
                //System.out.println("Winning");
                return true;
            } else {
                board[p.getX()][p.getY()] = 0; //reset it back to 0
            }
            //make sure that the other player cannot win
            board[p.getX()][p.getY()] = sym;
           if(determineWinOther()) { //other player can win
               board[p.getX()][p.getY()] = current.getSymbol();
              // System.out.println("Blocked other player");
               return true;
           } else {
               //System.out.println("Move did not block other player's victory. Other players symbol: " + sym);
               board[p.getX()][p.getY()] = 0;
           }

            //pick the position with the highest potential
            //compute Z value for p
            //check if z(p) > maxZ, update maxZ and best
            int Z = getZ(p);
            if(Z >= maxZ) {
                maxZ = Z;
                best = p;
            }


        }
        ArrayList<Point> goodPoints = new ArrayList<>();
        if(dumb) {
            for(Point p: list) {
                int Z = getZ(p);
                if(maxZ - Z <= 1) {
                    goodPoints.add(p);
                }
            }
            int randIndex = (int) (Math.random() * goodPoints.size());
            best = goodPoints.get(randIndex);
        }


        board[best.getX()][best.getY()] = current.getSymbol();
        return true;

    }

    private int getZ(Point p) {
        int num = 0;
        int x = p.getX();
        int y = p.getY();
        //check rows
        for(int i = 0; i < board.length; i++) {
            boolean valid = true;
            boolean encountered = false;
            for (int j = 0; j < board[i].length; j++) {
                if (i == x && j == y) {
                    encountered = true;
                }
                if (!(board[i][j] == current.getSymbol() || board[i][j] == 0)) {
                    valid = false;
                }
            }
            if (encountered && valid) {
                num++;
            }
        }
        //check cols
        for(int i = 0; i < board.length; i++) {
            boolean valid = true;
            boolean encountered = false;
            for (int j = 0; j < board[i].length; j++) {
                if (i == x && j == y) {
                    encountered = true;
                }
                if (!(board[j][i] == current.getSymbol() || board[j][i] == 0)) {
                    valid = false;
                }
            }
            if (encountered && valid) {
                num++;
            }
        }

        //check diags
        int otherSymb = getOther().getSymbol();
        if(board[0][0] != otherSymb && board[1][1] != otherSymb && board[2][0] != otherSymb) {
            if((x == 0 && y == 0) || (x == 1 && y == 1) ||  (x == 2 && y == 0)) {
                num++;
            }
        }

        if(board[2][0] != otherSymb && board[1][1] != otherSymb && board[0][2] != otherSymb) {
            if((x == 2 && y == 0) || (x == 1 && y == 1) ||  (x == 0 && y == 2)) {
                num++;
            }
        }
        return num;
    }

    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }



}
