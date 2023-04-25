import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Map extends JPanel {
    //1==loot
    int[][] map = new int[10][10];
    private static int LootDrawCounter=0;
    Random random = new Random();
    JLabel border = new JLabel(new ImageIcon("images\\border.png"));
    JLabel[][] crossedPlace=new JLabel[10][10];
    JLabel[] markets=new JLabel[5];
    JLabel[][] loots=new JLabel[13][2];
    JLabel[] treasureLoc = new JLabel[8];

    private class Trap {
        int x;
        int y;
        boolean[] showTrap = new boolean[2];
        JLabel icon = new JLabel(new ImageIcon("images\\trap.png"));

        Trap(int x, int y) {
            this.x = x;
            this. y = y;
            icon.setBounds(27 + x*(65), 27 + y*(65) , 65, 65);
            icon.setVisible(false);
            add(icon);
        }
    }
    Trap[] traps;
    private boolean checkAround(int x, int y, int in) {
        if (x > 0) {
            if (y > 0 && map[x-1][y-1] == in) return false;
            if (y < 9 && map[x-1][y+1] == in) return false;
            if (map[x-1][y] == in) return false;
        }
        if (x < 9) {
            if (y > 0 && map[x+1][y-1] == in) return false;
            if (y < 9 && map[x+1][y+1] == in) return false;
            if (map[x+1][y] == in) return false;
        }
        if (y > 0 && map[x][y-1] == in) return false;
        if (y < 9 && map[x][y+1] == in) return false;
        return true;
    }
    private int whatQuadron(int x, int y) {
        if (x < 5 && y < 5) return 0;
        else if (x < 5) return 1;
        else if (y < 5) return 2;
        return 3;
    }
    private boolean checkAroundTreasure(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (map[j][i] != 0) return true;
            }
        }
        return false;
    }
    private void setupTreasures() {
        int[] quadron = new int[4];
        int x, y, q;
        Random rng = new Random();
        for(int i =0; i < 8; i++) {
            do {
                x = rng.nextInt(8) + 1;
                y = rng.nextInt(8) + 1;
                q = whatQuadron(x, y);
            } while(quadron[q] == 2 || checkAroundTreasure(x, y));
            map[y][x] = i + 11;
            quadron[q]++;
            setupTreasureLabel(x, y, i);
        }
    }
    public void toggleQuestLoc(Player p, boolean nextTurn) {
        for (int i = 0; i < p.knowQuestsLoc.length; i++) {
            if (treasureLoc[i].isVisible() && nextTurn) {
                treasureLoc[i].setVisible(false);
            }
            if (p.knowQuestsLoc[i] && !treasureLoc[i].isVisible()) {
                treasureLoc[i].setVisible(true);
            }
        }
    }
    private void setupTreasureLabel(int x, int y, int count) {
        treasureLoc[count] = new JLabel(new ImageIcon("images\\quest.png"));
        treasureLoc[count].setBounds(27 + x*(65), 27 + y*(65) , 65, 65);
        treasureLoc[count].setVisible(true);
        add(treasureLoc[count]);
    }
    public void markTrap(int x, int y, int turn) {
        for (int i = 0; i < traps.length; i++) {
            if (traps[i].x == y && traps[i].y == x) {
                traps[i].showTrap[turn] = true;
                return;
            }
        }
    }
    public void showTraps(int turn) {
        hideTraps();
        for (int i = 0; i < traps.length; i++) {
            if (traps[i].showTrap[turn]) {
                traps[i].icon.setVisible(true);
            }
        }
    }
    public void hideTraps() {
        for (int i = 0; i < traps.length; i++) {
            if (traps[i].icon.isVisible()) {
                traps[i].icon.setVisible(false);
            }
        }
    }
    private void setCastle() {
        int x, y;
        do {
            x = random.nextInt(2);
            y = random.nextInt(2);
        } while (!isEmpty(x+4, y+4));
        map[x+4][y+4] = 4;
        JLabel castle = new JLabel(new ImageIcon("images\\castle.png"));
        castle.setBounds(287 + y*65, 287 + x*65, 65, 65);
        add(castle);
    }
    private boolean checkNotStart(int x, int y) {
        if (x == 0 && y == 9) return false;
        if (x == 9 && y == 0) return false;
        return true;
    }
    private boolean isEmpty(int x, int y) {
        if (map[x][y] == 0 && checkNotStart(x,y)) {
            return true;
        }
        return false;
    }
    private void setTraps() {
        int count = random.nextInt(4) + 5;
        System.out.println("Traps count: " + count);
        traps = new Trap[count];
        int x, y;
        for (int i = 0; i < count; i++) {
            do {
                x = random.nextInt(10);
                y = random.nextInt(10);
            } while(!checkAround(x, y, 3) || !isEmpty(x, y));
            map[x][y] = 3;
            traps[i] = new Trap(y, x);
        }
    }
    private void setupCrossPlaces() {
        for (int i = 0 ; i<crossedPlace.length ; i++){
            for (int j = 0 ; j<crossedPlace[i].length ; j++){
                crossedPlace[i][j]=new JLabel(new ImageIcon("images\\cross.png"));
                crossedPlace[i][j].setBounds(27 + j*(65), 27 + i*(65) , 65, 65);
                crossedPlace[i][j].setOpaque(false);
                crossedPlace[i][j].setVisible(false);
                add(crossedPlace[i][j]);
            }
        }
    }
    private void MarketDivide(){
        int x, y;
        for (int j=x=y=0 ; j< markets.length ; j++) {
            do {
                if (j==0) {
                    x = random.nextInt(4);
                    y = random.nextInt(4);
                } else if (j==1) {
                    x = random.nextInt(4);
                    y = random.nextInt(4)+6;
                } else if (j==2) {
                    x = random.nextInt(4)+6;
                    y = random.nextInt(4);
                } else if (j==3) {
                    x = random.nextInt(4)+6;
                    y = random.nextInt(4)+6;
                } else if (j==4) {
                    do {
                        x = random.nextInt(10);
                        y = random.nextInt(10);
                    } while (!checkAround(x, y, 2));
                }
            } while(!isEmpty(x, y));
            map[x][y] = 2;
            markets[j]=new JLabel(new ImageIcon("images\\market.png"));
            markets[j].setBounds(29+y*65,29+x*65,61,61);
            markets[j].setVisible(true);
            add(markets[j]);
        }
    }
    private void LootDivide(){
        int x, y;
        for (int j=0 ; j<13 ; j++) {
            do {
                x = random.nextInt(10);
                y = random.nextInt(10);
            } while (!isEmpty(x, y));
            map[x][y] = 1;
        }
    }
    private void setupLoots() {
        for (int j = 0; j < loots.length; j++) {
            for (int k = 0; k < loots[j].length; k++) {
                loots[j][k]=new JLabel();
            }
        }
    }
    public void LootDraw(int in, Player[] players){
        loots[LootDrawCounter][in].setBounds(29+players[in].y*65,29+players[in].x*65,61,61);
        loots[LootDrawCounter][in].setBackground(Color.cyan);
        loots[LootDrawCounter][in].setVisible(true);
        loots[LootDrawCounter][in].setOpaque(true);
        add(loots[LootDrawCounter][in]);
        LootDrawCounter++;
    }
    public void LootShower(int in){
        for (int j = 0; j < loots.length; j++) {
            loots[j][in].setVisible(true);
        }
    }
    public void LootHider(int in){
        for (int j = 0; j < loots.length; j++) {
            loots[j][in].setVisible(false);
        }
    }
    private void printMap() {
        for (int i =0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
                if (map[i][j] < 10) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("\n");
    }
    Map() {
        setLayout(null);
        border.setBounds(0, 0, 704, 704);
        border.setVisible(true);
        setupCrossPlaces();
        setupTreasures();
        setCastle();
        setTraps();
        MarketDivide();
        setupLoots();
        LootDivide();
        add(border);
        setOpaque(false);
        printMap();
    }
}