import java.util.Scanner;

public class MainConsole {
    public static void main(String[] args) {
        //test affichage
        Grid grid = new Grid();
        System.out.println(grid);

        //initialiser avec des valeurs

        /*
        //glider
        grid.setCellule(true, 5, 5);
        grid.setCellule(true, 5, 6);
        grid.setCellule(true, 5, 7);
        grid.setCellule(true, 4, 7);
        grid.setCellule(true, 3, 6);
        */

        grid.randomize();
        System.out.println(grid);

        Scanner sc = new Scanner(System.in);
        int nbTurns = 1;

        while(true){

            sc.nextLine();
            nbTurns++;

            grid.updateGrid();
            System.out.println("nombre de tours : " + nbTurns + "\n" + grid);
        }



    }
}
