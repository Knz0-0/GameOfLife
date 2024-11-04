import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
        grid.setBorder(new LineBorder(Color.WHITE));

        // Utilisation d'un JPanel avec GridBagLayout pour centrer la grille
        JPanel gridContainer = new JPanel(new GridBagLayout());
        // Ajout de la grille dans le conteneur centré
        gridContainer.add(grid);
        // Ajout du conteneur centré à la fenêtre principale
        add(gridContainer, BorderLayout.CENTER);
        gridContainer.setBackground(Color.BLACK);


        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.black);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 5), // Bordure extérieure
                new EmptyBorder(10, 0, 10, 15) // Espace intérieur : top, left, bottom, right
        ));
        controlPanel.setLayout(new GridLayout(6, 1, 10, 10));
        add(controlPanel, BorderLayout.EAST);




        // icônes
        ImageIcon startStopIcon = loadImageIcon("icons/start_stop_icon.png", 50, 50);
        ImageIcon nextIcon = loadImageIcon("icons/next_icon.png", 50, 50);
        ImageIcon randomizeIcon = loadImageIcon("icons/randomize_icon.png", 50, 50);
        ImageIcon clearIcon = loadImageIcon("icons/clear_icon.png", 50, 50);

        // boutons
        JButton buttonStartStop = new JButton(startStopIcon);
        JButton buttonNext = new JButton(nextIcon);
        JButton buttonRandomize = new JButton(randomizeIcon);
        JButton buttonClear = new JButton(clearIcon);





        //actionlisteners

        buttonStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleGOLThread();
            }
        });
        controlPanel.add(buttonStartStop);

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.updateGrid();
            }
        });
        controlPanel.add(buttonNext);


        buttonRandomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.randomize();
            }
        });
        controlPanel.add(buttonRandomize);

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.clear();
            }
        });
        controlPanel.add(buttonClear);


        //style des boutons
        styleButton(buttonStartStop);
        styleButton(buttonNext);
        styleButton(buttonRandomize);
        styleButton(buttonClear);






        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(false);
    }




    private void styleButton(JButton button) {

        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 5), // Bordure extérieure
                new EmptyBorder(10, 10, 10, 10) // Espace intérieur : top, left, bottom, right
        ));

    }

    private ImageIcon loadImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
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
