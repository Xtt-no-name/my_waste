import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    private Snake snake;
    private Food food;
    private Timer timer;
    private boolean inGame = true;

    // 常量定义
    private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 1000;
    private final int DOT_SIZE = 10;

    public GamePanel() {
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        setBackground(Color.BLACK);

        setFocusable(true);
        // 界面初始化（背景、监听器等）
        snake = new Snake(B_WIDTH / 2, B_HEIGHT / 2, DOT_SIZE, 'R');
        food = new Food(B_WIDTH, B_HEIGHT, DOT_SIZE);
        timer = new Timer(140, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFood();      // 调用判断逻辑
            checkCollision(); // 调用碰撞逻辑
            snake.move();     // 调用移动逻辑
        }
        repaint(); // 刷新画面
    }

    private void checkFood() {
        // TODO: 判断蛇头坐标是否与食物坐标重合
        // 如果重合：snake.grow() 并且 food.generate()
    }

    private void checkCollision() {
        // TODO: 逻辑判断
        // 1. 撞墙判断 (x[0] < 0 等)
        // 2. 撞到自己判断 (循环比较头部与身体坐标)
        // 若撞到，设置 inGame = false
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: 循环调用 g.fillRect 绘制 snake.x 和 snake.y
        // TODO: 绘制 food.x 和 food.y
    }
}