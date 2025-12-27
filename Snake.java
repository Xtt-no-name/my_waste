import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static class Node{
        public int x, y;
        char direction;

        public Node(int x, int y, char direction){
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    List<Node> body;
    int body_length = 0;
    char snack_direction;
    int Dot_size;
    HashSet<String> body_set;
    boolean isFood = false;

    public Snake(int startX, int startY, int Dot_size, char startDirection) {
        // TODO: 初始化蛇的起始长度和位置
        this.body = new LinkedList<>();
        this.body_length = 1;
        this.snack_direction = startDirection;
        this.Dot_size = Dot_size;
        this.body_set = new HashSet<>();
        body.add(new Node(startX, startY, startDirection));
        body.add(new Node(startX - Dot_size, startY, startDirection));
        body.add(new Node(startX - 2 * Dot_size, startY, startDirection));
        body_set.add((startX - Dot_size) + "," + startY);
        body_set.add((startX - 2 * Dot_size) + "," + startY);
    }

    public void move() {
        // 获取当前头部的坐标
        int headX = body.getFirst().x;
        int headY = body.getFirst().y;

        // 计算新头的坐标
        int newX = headX;
        int newY = headY;

        switch (snack_direction) {
            case 'L': newX -= Dot_size; break;
            case 'R': newX += Dot_size; break;
            case 'U': newY -= Dot_size; break;
            case 'D': newY += Dot_size; break;
        }

        if(!isFood) {
            body_set.remove(body.getLast().x + "," + body.getLast().y);
            body.removeLast();
        }
        isFood = false;
        body_set.add(headX + "," + headY);
        body.addFirst(new Node(newX, newY, snack_direction));
    }

    public void grow() {
        // TODO: 吃到食物后长度增加的逻辑
        isFood = true;
    }
}