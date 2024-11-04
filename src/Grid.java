import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Grid extends JPanel implements Runnable{
    public final int GRID_HEIGHT = 200;
    public final int GRID_WIDTH = 200;
    public final int CELL_SIZE = 2;

    public Thread GOLThread;

    final static int FPS = 15;

    private Boolean cellules[][];

    Grid(){
        //parametres de fenetre
        setPreferredSize(new Dimension(600, 600));

        //initialiser la grille
        cellules = new Boolean[GRID_WIDTH][GRID_HEIGHT];
        //mettre toutes les cellules à [false]
        clear();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //modifier l'état de la cellule cliquée
                toggleCellule(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                activateCellule(e);
            }
        });

    }

    private void toggleCellule(MouseEvent e){
        int col = e.getX() / (CELL_SIZE + 1);
        int row = e.getY() / (CELL_SIZE + 1);
        if (col < GRID_WIDTH && row < GRID_HEIGHT && col >= 0 && row >= 0){
            setCellule(!getCellule(col, row), col, row);
            repaint();
        }
    }

    private void activateCellule(MouseEvent e){
        int col = e.getX() / (CELL_SIZE + 1);
        int row = e.getY() / (CELL_SIZE + 1);
        if (col < GRID_WIDTH && row < GRID_HEIGHT && col >= 0 && row >= 0){
            setCellule(true, col, row);
            repaint();
        }
    }

    public Boolean[][] getCellules() {
        return cellules;
    }

    public void setCellules(Boolean[][] cellules){
        this.cellules = cellules;
    }

    public Boolean getCellule(int x, int y){
        return cellules[x][y];
    }

    public void setCellule(boolean value, int x, int y){
        cellules[x][y] = value;
    }

    @Override
    public String toString(){
        String res = "";
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (cellules[i][j]){
                    res += "@ ";
                }else{
                    res += ". ";
                }
            }
            res += "\n";
        }
        return res;
    }

    public int getCellScore(int x, int y){
        int score = 0;

        if(x > 0){//tous les "x-1"
            if (y > 0){//tous les "y-1"
                if (cellules[x-1][y-1]){
                    score++;
                }
            }
            if (y < GRID_WIDTH -1){//tous les "y+1"
                if (cellules[x-1][y+1]){
                    score++;
                }
            }

            if (cellules[x-1][y]){
                score++;
            }
        }
        if(x < GRID_HEIGHT -1){// tous les "x+1"
            if (y > 0){//tous les "y-1"
                if (cellules[x+1][y-1]){
                    score++;
                }
            }
            if (y < GRID_WIDTH -1){//tous les "y+1"
                if (cellules[x+1][y+1]){
                    score++;
                }
            }

            if (cellules[x+1][y]){
                score++;
            }
        }

        if (y < GRID_WIDTH -1 && cellules[x][y+1]){
            score++;
        }
        if (0 < y && cellules[x][y-1]){
            score++;
        }

        return score;
    }

    public Boolean nextCellState(int x, int y){
        if (getCellScore(x, y) == 3){
            return true;
        } else if (getCellScore(x, y) < 2 || getCellScore(x, y) > 3) {
            return false;
        }else {
            return getCellule(x, y);
        }
    }

    public void updateGrid(){
        Grid nextGrid = new Grid();
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                nextGrid.setCellule(nextCellState(i, j), i, j);
            }
        }

        setCellules(nextGrid.getCellules());
        repaint();
    }

    public void randomize(){
        Random rd = new Random();
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                setCellule(rd.nextBoolean(), i, j);
            }
        }
        repaint();
    }

    public void clear(){

        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                setCellule(false, i, j);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //dessiner la grille
        for (int x = 0; x < getWidth() && x / (CELL_SIZE + 1) < cellules.length; x+= CELL_SIZE +1) {
            for (int y = 0; y < getHeight() && y / (CELL_SIZE + 1) < cellules[0].length; y+= CELL_SIZE +1) {
                int col = x / (CELL_SIZE + 1);
                int row = y / (CELL_SIZE + 1);
                //assurer que les indices sont dans la limite de cellules
                if (col < cellules.length && row < cellules[0].length){
                    if (cellules[col][row]){
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, CELL_SIZE+1, CELL_SIZE+1);
                    }else {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, CELL_SIZE+1, CELL_SIZE+1);
                    }
                }
            }
        }
    }




    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        long timer = 0;
        long currentTime;
        int drawCount = 0;


        while (GOLThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                updateGrid();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {

                System.out.println("FPS : " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}

