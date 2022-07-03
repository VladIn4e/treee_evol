public class Offshoot {
    int x, y;
    int[][] gen = new int[15][9];
    int utk;

    public Offshoot(int x, int y, int[][] gen) {
        this.x = x;
        this.y = y;
        this.gen = gen.clone();
        this.utk = 0;
    }

}

