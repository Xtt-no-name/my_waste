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

    public Snake(int startX, int startY, int Dot_size, char startDirection) {
        // TODO: 初始化蛇的起始长度和位置
        this.body = new LinkedList<>();
        this.body_length = 1;
        body.add(new Node(startX, startY, startDirection));
        body.add(new Node(startX + Dot_size, startY, startDirection));
        body.add(new Node(startX + 2 * Dot_size, startY, startDirection));
    }

    public void move() {
        // TODO: 1. 身体跟随（后一个节点坐标移到前一个节点位置）
        // TODO: 2. 根据 direction 更新头部坐标 (x[0], y[0])
    }

    public void grow() {
        // TODO: 吃到食物后长度增加的逻辑
    }
}