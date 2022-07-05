public class Offshoot {
    int x, y;
    int[][] gen = new int[15][4];
    int utk, index;

    public Offshoot(int x, int y, int[][] gen, int utk, int index) {
        this.x = x;
        this.y = y;
        this.gen = gen.clone();
        this.utk = utk;
        this.index = index;
    }

}

