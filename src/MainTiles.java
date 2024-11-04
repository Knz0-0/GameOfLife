import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

public class MainTiles extends JFrame {

    private Grid grid;


    public MainTiles(){
        setLayout(new BorderLayout());

        grid = new Grid();


        add(grid, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(5, 1, 0, 5));

        controlPanel.setBackground(Color.DARK_GRAY);
        add(controlPanel, BorderLayout.EAST);

        JButton buttonStartStop = new JButton("Start / Stop");
        buttonStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleGOLThread();
            }
        });
        controlPanel.add(buttonStartStop);

        JButton buttonNext = new JButton("Next");
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.updateGrid();
            }
        });
        controlPanel.add(buttonNext);

        JButton buttonRandomize = new JButton("Randomize");
        buttonRandomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.randomize();
            }
        });
        controlPanel.add(buttonRandomize);

        JButton buttonClear = new JButton("Clear");
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.clear();
            }
        });
        controlPanel.add(buttonClear);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(true);
    }





    private void toggleGOLThread(){
        if (grid.GOLThread != null){
            grid.GOLThread = null;
        }else {
            grid.GOLThread = new Thread(grid);
            grid.GOLThread.start();


        }
    }




    public static void main(String[] args) {
        MainTiles golApp= new MainTiles();
        golApp.setVisible(true);


    }
}
