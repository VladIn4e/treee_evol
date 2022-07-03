import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Tree evol");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocation(0, 0);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow main_win = new MainWindow();
    }
}
