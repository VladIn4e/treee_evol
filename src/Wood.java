import java.awt.*;
import java.util.Random;
public class Wood {
    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    public Color randomColor;

    int x, y;
    public Wood(int x, int y) {
            this.x = x;
            this.y = y;
            Color randomColor = new Color(r, g, b);
    }
}