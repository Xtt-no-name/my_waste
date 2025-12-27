import java.util.Random;

public class Food {
    public int x;
    public int y;
    private Random random;

    public Food(int rangeX, int rangeY, int dotSize) {
        random = new Random();
        generate(rangeX, rangeY, dotSize);
    }

    public void generate(int rangeX, int rangeY, int dotSize) {
        // TODO: 随机生成坐标，必须是 dotSize 的倍数才能对齐
        x = random.nextInt(rangeX) * dotSize;
        y = random.nextInt(rangeY) * dotSize;
    }
}