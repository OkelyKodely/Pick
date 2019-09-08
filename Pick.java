
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Pick extends JFrame implements KeyListener {
    
    int score = 0;
    
    Image imageBomb = null;

    JPanel p = new JPanel();
    
    int x = 450, y = 500;
    
    class Bomb {
        int x, y;
    }
    
    ArrayList<Bomb> bombs = new ArrayList<Bomb>();
   
    public Pick() {
        setTitle("Pick");
        p.setBounds(0, 0, 1000, 600);
        this.setLayout(null);
        this.setBounds(p.getBounds());
        this.getRootPane().add(p);
        
        this.show();
        this.addKeyListener(this);
        this.play();
        
        this.initBombs();
    }
    
    private void initBombs() {
        for(int i=0; i<50; i++) {
            Random random = new Random();
            int v = random.nextInt(1000);
            int w = 0 + random.nextInt(600);
            Bomb bomb = new Bomb();
            bomb.x = v;
            bomb.y = w;
            bombs.add(bomb);
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            x-=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x+=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            y+=3;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            y-=3;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyExited(KeyEvent e) {
        
    }
    
    public void play() {
        try {
            imageBomb = ImageIO.read(ClassLoader.getSystemResource("bomb.gif"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        Graphics g = p.getGraphics();
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        for (int i = 0; i < bombs.size(); i++) {
                            g.drawImage(imageBomb, bombs.get(i).x, bombs.get(i).y, 30, 30, null);
                            if ((bombs.get(i).x <= x && bombs.get(i).x + 30 >= x) && (bombs.get(i).y <= y && bombs.get(i).y + 30 >= y)) {
                                score += 100;
                            }
                            
                            if ((bombs.get(i).x <= x && bombs.get(i).x + 30 >= x) && (bombs.get(i).y <= y && bombs.get(i).y + 30 >= y)) {
                                bombs.remove(bombs.get(i));
                            }
                        }
                        if (bombs.size() == 0) {
                            initBombs();
                        }
                        g.setColor(Color.BLACK);
                        g.drawString("SCORE " + score, 800, 20);
                        Image image = ImageIO.read(ClassLoader.getSystemResource("mario.png"));
                        g.drawImage(image, x, y, 30, 30, null);
                    } catch (IOException iOException) {
                    }
                    try {
                        Thread.sleep(25);
                        g.setColor(Color.GREEN);
                        g.fillRect(0, 0, 1000, 600);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    
    public static void main(String arg[]) {
        Pick p = new Pick();
    }
}