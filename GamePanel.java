import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class GamePanel extends JPanel implements ActionListener {
    private Snake snake;
    private Food food;
    private Timer timer;
    private boolean inGame = true;
    private Image headImage;
    private Image foodImage;
    private boolean canChangeDirection = true;

    // 常量定义
    private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 1000;
    private final int DOT_SIZE = 50;

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!canChangeDirection) return;
            int key = e.getKeyCode();

            // 获取当前蛇的方向，防止它直接 180 度回头（比如往右走时不能直接按左）
            char currentDir = snake.snack_direction;

            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && (currentDir != 'R')) {
                snake.snack_direction = 'L';
                canChangeDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && (currentDir != 'L')) {
                snake.snack_direction = 'R';
                canChangeDirection = false;
            }

            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && (currentDir != 'D')) {
                snake.snack_direction = 'U';
                canChangeDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && (currentDir != 'U')) {
                snake.snack_direction = 'D';
                canChangeDirection = false;
            }
        }
    }

    private void loadImages(){
        URL head_url = getClass().getResource("/resources/head.png");
        ImageIcon headImg = new ImageIcon(head_url);
        headImage = headImg.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);

        URL food_url = getClass().getResource("/resources/food.png");
        ImageIcon foodImg = new ImageIcon(food_url);
        foodImage = foodImg.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);

        // 检查图片是否加载
        if (headImage == null || headImg.getImageLoadStatus() != MediaTracker.COMPLETE) {
            JOptionPane.showMessageDialog(this,
                    "图片加载失败！\n请检查 resources/head.png 是否存在。",
                    "资源错误",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    public GamePanel() {
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        setBackground(Color.BLACK);

        setFocusable(true);

        loadImages();
        // 界面初始化（背景、监听器等）
        snake = new Snake(B_WIDTH / 2, B_HEIGHT / 2, DOT_SIZE, 'R');
        food = new Food(B_WIDTH / DOT_SIZE, B_HEIGHT / DOT_SIZE, DOT_SIZE);
        timer = new Timer(140, this);
        addKeyListener(new TAdapter());
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkFood();      // 调用判断逻辑
            canChangeDirection = true;
            checkCollision(); // 调用碰撞逻辑
        }
        if (inGame) {
            int oldTailX = snake.body.getLast().x;
            int oldTailY = snake.body.getLast().y;
            snake.move();     // 调用移动逻辑
            int newHeadX = snake.body.getFirst().x;
            int newHeadY = snake.body.getFirst().y;

            repaint();
        }
    }

    private void checkFood() {
        // TODO: 判断蛇头坐标是否与食物坐标重合
        Snake.Node head = snake.body.getFirst();
        if(head.x == food.x && head.y == food.y){
            snake.grow();
            food.generate(B_WIDTH / DOT_SIZE, B_HEIGHT / DOT_SIZE, DOT_SIZE);
        }
    }

    private void checkCollision() {
        // TODO: 逻辑判断
        int nowHeadX = snake.body.getFirst().x;
        int nowHeadY = snake.body.getFirst().y;

        if(snake.body_set.contains(nowHeadX + "," + nowHeadY)) inGame = false;
        if(nowHeadX >= B_WIDTH || nowHeadX < 0 || nowHeadY >= B_HEIGHT || nowHeadY < 0) inGame = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: 循环调用 g.fillRect 绘制 snake.x 和 snake.y
        // TODO: 绘制 food.x 和 food.y
        Graphics2D g2d = (Graphics2D) g;
        for(Snake.Node link : snake.body){
            drawSnack(g2d, headImage, link.x, link.y, link.direction);
        }
        drawFood(g2d, foodImage, food);
    }
    private void drawSnack(Graphics2D g2d, Image img, int x, int y, char dir) {
        // 1. 保存当前的绘图状态
        AffineTransform oldForm = g2d.getTransform();

        // 2. 计算中心点
        int centerX = x + DOT_SIZE / 2;
        int centerY = y + DOT_SIZE / 2;

        // 3. 开始变换
        g2d.translate(centerX, centerY); // 先移到中心

        double angle = 0;
        switch (dir) {
            case 'U': angle = 0; break;
            case 'D': angle = Math.PI; break;           // 180度
            case 'L': angle = -Math.PI / 2; break;      // 逆时针90度
            case 'R': angle = Math.PI / 2; break;       // 顺时针90度
        }
        g2d.rotate(angle);

        // 4. 画图
        g2d.drawImage(img, -DOT_SIZE / 2, -DOT_SIZE / 2, DOT_SIZE, DOT_SIZE, null);

        // 5. 恢复原来的绘图状态
        g2d.setTransform(oldForm);
    }
    private void drawFood(Graphics2D g2d, Image img, Food food){
        AffineTransform oldForm = g2d.getTransform();

        int centerX = food.x + DOT_SIZE / 2;
        int centerY = food.y + DOT_SIZE / 2;

        g2d.translate(centerX, centerY);
        g2d.drawImage(img, -DOT_SIZE / 2, -DOT_SIZE / 2, DOT_SIZE, DOT_SIZE, null);
        g2d.setTransform(oldForm);
    }
}