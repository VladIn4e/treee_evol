import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.lang.Math;

public class GameField extends JPanel implements ActionListener {
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private final int offshoot_koll = 1;
    private Timer timer;
    public ArrayList<Offshoot> offshoots = new ArrayList<>(1);
    public ArrayList<Integer> woods = new ArrayList<>(1);

    public GameField() {
        setBackground(Color.BLACK);
        initGame();
    }

    public void create_offshoot(int x, int y, int[][] gen) {
        offshoots.add(new Offshoot(x, y, gen));
    }

    public void initGame() {
        for (int p = 0; p < offshoot_koll; p++) {
            int x = 0;
            int y = 0;
            int[][] gen = new int[15][9];
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 9; j++) {
                    gen[i][j] = 1 + (int) (Math.random() * 30);
                }
            }
            create_offshoot(x, y, gen);
        }
        timer = new Timer(1000, this);
        timer.start();
    }

    public void make()  {

    }

    public void drawWood() {

    }

    public void drawOffshoot() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        make();
        drawWood();
        drawOffshoot();
        repaint();
    }
}
