import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        add(new GamePanel());
        setResizable(false);
        pack();
        setTitle("贪吃蛇");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new SnakeGame().setVisible(true);
    }
}