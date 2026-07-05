package SpaceGame;
import javax.swing.*;
import java.awt.*;
public class GameFrame extends JFrame{
    GamePanel panel;
    double panelH = 850;
    double panelW = 1300;
    GameFrame(){
        CreateGame();
        AddGame();
        CreateWindow();
        setVisible(true);
    }

    public void CreateWindow(){
        pack();
        setTitle("Space Game");
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void CreateGame(){
        panel = new GamePanel(panelH,panelW);
    }


    public void AddGame(){
        add(panel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new GameFrame());
    }
}
