import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.lang.Math;
import java.util.HashMap;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private final int seed_koll = 20;
    private Timer timer;
    public ArrayList<Offshoot> offshoots = new ArrayList<>(1);
    public ArrayList<Seed> seeds = new ArrayList<>(1);
    public ArrayList<Wood> woods = new ArrayList<>(1);

    public GameField() {
        setBackground(Color.BLACK);
        initGame();
    }

    public void create_offshoot(int x, int y, int[][] gen, int utk, int index) {
        offshoots.add(new Offshoot(x, y, gen, utk, index));
    }

    public void del_offshoot(int i) {
        offshoots.remove(i);
    }

    public void create_wood(int index) {
        woods.add(new Wood(index));
    }

    public void create_1wood(int index, int x, int y) {
        for (int i = 0; i < woods.size(); i++) {
            if (woods.get(i).index == index) {
                woods.get(i).x.add(x);
                woods.get(i).y.add(y);
            }
        }
    }

    public void del_wood(int index) {
        for (int i = 0; i < woods.size(); i++) {
            if (woods.get(i).index == index) {
                woods.remove(i);
            }
        }
    }

    public void create_seed(int x, int y, int utk, int[][] gen) {
        seeds.add(new Seed(x, y, utk, gen));
    }

    public void del_seed(int i) {
        seeds.remove(i);
    }

    public void initGame() {
        for (int p = 0; p < seed_koll; p++) {
            int x = 1 + (int) (Math.random() * 110) * 10;
            int y = 400;
            int[][] gen = new int[15][4];
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 4; j++) {
                    gen[i][j] = 1 + (int) (Math.random() * 30);
                }
            }
            create_seed(x, y, 0, gen);
        }
        timer = new Timer(500, this);
        timer.start();
    }

    public boolean watch(String p, int i) {
        int x = offshoots.get(i).x;
        int y = offshoots.get(i).y;
        if (p == "left") {
            for (int j = 0; j < woods.size(); j++) {
                for (int k = 0; k < woods.get(j).x.size(); k++) {
                    if (woods.get(j).x.get(k) == x-10 && woods.get(j).y.get(k) == y || x-10 < 10) {
                        return false;
                    }
                }
            }
            return true;
        } else if (p == "right") {
            for (int j = 0; j < woods.size(); j++) {
                for (int k = 0; k < woods.get(j).x.size(); k++) {
                    if (woods.get(j).x.get(k) == x+10 && woods.get(j).y.get(k) == y || x+10 > 1180) {
                        return false;
                    }
                }
            }
            return true;
        } else if (p == "up") {
            for (int j = 0; j < woods.size(); j++) {
                for (int k = 0; k < woods.get(j).x.size(); k++) {
                    if (woods.get(j).x.get(k) == x && woods.get(j).y.get(k) == y-10 || y-10 < 10) {
                        return false;
                    }
                }
            }
            return true;
        } else if (p == "down") {
            for (int j = 0; j < woods.size(); j++) {
                for (int k = 0; k < woods.get(j).x.size(); k++) {
                    if (woods.get(j).x.get(k) == x && woods.get(j).y.get(k) == y+10 || y+10 > 680) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void make()  {
        for (int i = 0; i < offshoots.size(); i++) {
            int utk = offshoots.get(i).utk;
            int x = offshoots.get(i).x;
            int y = offshoots.get(i).y;
            int index = offshoots.get(i).index;
            int[] gen = offshoots.get(i).gen[utk];
            boolean left = watch("left", i);
            boolean up = watch("up", i);
            boolean right = watch("right", i);
            boolean down = watch("down", i);
            boolean del_indef = false;
            for (int j=0; j<3; j++) {
                if (gen[j] < 15) {
                    if (j == 0 && left) {
                        create_offshoot(x-10, y, offshoots.get(i).gen, gen[j], index);
                        del_indef = true;
                    } else if (j == 1 && up) {
                        create_offshoot(x, y-10, offshoots.get(i).gen, gen[j], index);
                        del_indef = true;
                    } else if (j == 2 && right) {
                        create_offshoot(x+10, y, offshoots.get(i).gen, gen[j], index);
                        del_indef = true;
                    } else if (j == 3 && down) {
                        create_offshoot(x, y+10, offshoots.get(i).gen, gen[j], index);
                        del_indef = true;
                    }
                    if (del_indef) {
                        del_offshoot(i);
                        create_1wood(index, x, y);
                    }
                }
            }
        }
    }
    
    public void checkDead() {
        int index;
        for (int i = 0; i < woods.size(); i++) {
            if (woods.get(i).age > 29) {
                index = woods.get(i).index;
                del_wood(i);

                for (int j = 0; j < offshoots.size(); j++) {
                    if (offshoots.get(j).index == index) {
                        create_seed(offshoots.get(j).x, offshoots.get(j).y, offshoots.get(j).utk, offshoots.get(j).gen);
                        del_offshoot(j);
                    }
                }
            } else {
                woods.get(i).age += 1;
            }
        }
    }

    public void seedLogick() {
        for (int i = 0; i < seeds.size(); i++) {
            if (seeds.get(i).y < 690) {
                seeds.get(i).y += 10;
            } else if (seeds.get(i).y == 690) {
                int index = woods.size();
                create_offshoot(seeds.get(i).x, seeds.get(i).y, seeds.get(i).gen, seeds.get(i).utk, index);
                create_wood(index);
                del_seed(i);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < offshoots.size(); i++) {
            g.setColor(Color.WHITE);
            g.drawRect(offshoots.get(i).x, offshoots.get(i).y, 10, 10);
        }
        for (int i = 0; i < woods.size(); i++) {
            g.setColor(Color.GREEN);
            for (int j = 0; j < woods.get(i).x.size(); j++) {
                g.drawRect(woods.get(i).x.get(j), woods.get(i).y.get(j), 10, 10);
            }
        }
        for (int i = 0; i < seeds.size(); i++) {
            g.setColor(Color.GRAY);
            g.drawRect(seeds.get(i).x, seeds.get(i).y, 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        make();
        checkDead();
        seedLogick();
        repaint();
    }
}
