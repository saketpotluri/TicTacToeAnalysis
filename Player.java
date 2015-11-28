package TicTac;

/**
 * Created by saketp on 11/27/2015.
 */
public class Player {
    private String name;
    private int symbol;
    private int strategy;

    public Player(String name, int symbol, int strategy) {
        this.name = name;
        this.symbol = symbol;
        this.strategy = strategy;
    }

    public Player(String name, int symbol) {
         this(name, symbol, 0);
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
