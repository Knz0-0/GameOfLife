import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;

public class MainTiles extends JFrame {

    private Grid grid;


    public MainTiles(){
        setLayout(new BorderLayout());

        grid = new Grid();

        // Utilisation d'un JPanel avec GridBagLayout pour centrer la grille
        JPanel gridContainer = new JPanel(new GridBagLayout());
        // Ajout de la grille dans le conteneur centré
        gridContainer.add(grid);
        // Ajout du conteneur centré à la fenêtre principale
        add(gridContainer, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel(new GridLayout(4, 1, 0, 0));

        controlPanel.setBorder(new LineBorder(Color.BLACK));
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
        setResizable(false);
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
