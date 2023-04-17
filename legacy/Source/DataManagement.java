import java.text.NumberFormat;
import java.util.Locale;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImageOp;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

// 
// Decompiled by Procyon v0.5.36
// 

public class DataManagement
{
    private HashMap<File, BufferedImage> hmImageData;
    private File file;
    private File fileAddress;
    private Vector<Color> vColor;
    private GObject unit;
    private SetNew setNew;
    boolean specific;
    private int tick;
    
    public DataManagement() {
        this.specific = false;
        this.tick = 45;
        this.hmImageData = new HashMap<File, BufferedImage>();
        (this.vColor = new Vector<Color>()).add(new Color(157, 0, 0, 255));
        this.vColor.add(new Color(0, 0, 157, 255));
        this.vColor.add(new Color(0, 157, 0, 255));
        this.vColor.add(new Color(157, 157, 0, 255));
        this.vColor.add(new Color(157, 0, 157, 255));
        this.vColor.add(new Color(0, 157, 157, 255));
        this.vColor.add(new Color(157, 157, 157, 255));
        this.vColor.add(new Color(255, 0, 0, 255));
        this.vColor.add(new Color(0, 255, 0, 255));
        this.vColor.add(new Color(0, 0, 255, 255));
        this.vColor.add(new Color(255, 255, 0, 255));
        this.vColor.add(new Color(255, 0, 255, 255));
        this.vColor.add(new Color(0, 255, 255, 255));
        this.vColor.add(new Color(255, 255, 255, 255));
        for (int i = 0; i < this.vColor.size(); ++i) {
            this.createDummy(this.vColor.get(i));
        }
    }
    
    public ImageIcon loadImageSafety(final String path, final Boolean exception) {
        try {
            return this.loadImage(path);
        }
        catch (Exception e) {
            if (exception) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public ImageIcon loadImage(final String path) throws Exception {
        this.file = new File("ImageData/" + path);
        if (!this.hmImageData.containsKey(this.file)) {
            this.hmImageData.put(this.file, this.getImage(this.file));
        }
        return new ImageIcon(this.hmImageData.get(this.file));
    }
    
    private BufferedImage getImage(final File file) {
        try {
            return ImageIO.read(file);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public ImageIcon loadImage(final String path, final int w, final int h) {
        this.file = new File("ImageData/" + path);
        return new ImageIcon(this.getImage(this.file).getSubimage(0, 0, w, h));
    }
    
    public ImageIcon rotate(final String path, final int degree) {
        try {
            this.file = new File("ImageData/" + path);
            this.fileAddress = new File(String.valueOf(degree) + "ImageData/" + path);
            if (this.hmImageData.containsKey(this.fileAddress)) {
                return new ImageIcon(this.hmImageData.get(this.fileAddress));
            }
            BufferedImage img;
            if (this.hmImageData.containsKey(this.file)) {
                img = this.hmImageData.get(this.file);
            }
            else {
                img = this.getImage(this.file);
            }
            final int w = img.getWidth() * 2;
            final int h = img.getHeight() * 2;
            final BufferedImage dimg = new BufferedImage(w, h, img.getType());
            final Graphics2D g = dimg.createGraphics();
            g.rotate(Math.toRadians(degree), w / 2, h / 2);
            g.drawImage(img, null, w / 4, h / 4);
            this.hmImageData.put(this.fileAddress, dimg);
            return this.rotate(path, degree);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public void createDummy(final Color color) {
        this.file = new File("ImageData/Objects/Default/Dummy" + this.vColor.indexOf(color) + ".pngU");
        final int w = 28;
        final int h = 18;
        final BufferedImage img = new BufferedImage(w, h, 3);
        final Graphics2D g2d = img.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.clearRect(0, 0, w, h);
        g2d.setColor(color);
        g2d.drawLine(0, h / 2 - 1, w - 1, h / 2 - 1);
        g2d.drawLine(0, h / 2, w, h / 2);
        g2d.drawLine(0, h / 2 + 1, w - 1, h / 2 + 1);
        g2d.drawLine(w - 1, h / 2 - 1, w / 2, 0);
        g2d.drawLine(w - 1, h / 2 + 1, w / 2, h);
        g2d.drawLine(w - 2, h / 2 - 1, w / 2 - 1, 0);
        g2d.drawLine(w - 2, h / 2 + 1, w / 2 - 1, h);
        this.hmImageData.put(this.file, img);
    }
    
    public ImageIcon createBlock(final int w, final int h, final Color color) {
        final BufferedImage img = new BufferedImage(w, h, 3);
        final Graphics2D g2d = img.createGraphics();
        g2d.setBackground(new Color(255, 255, 255, 0));
        g2d.setColor(color);
        g2d.clearRect(0, 0, w, h);
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.drawRect(2, 2, w - 3, h - 3);
        g2d.drawRect(3, 3, w - 5, h - 5);
        g2d.setColor(color);
        g2d.drawRect(0, 0, w - 3, h - 3);
        g2d.drawRect(1, 1, w - 5, h - 5);
        return new ImageIcon(img);
    }
    
    public ImageIcon createMini(final int w, final int h, final JLabel jl) {
        final BufferedImage img = new BufferedImage(jl.getWidth(), jl.getHeight(), 3);
        final Graphics2D g2d = img.createGraphics();
        g2d.setBackground(new Color(255, 255, 255, 0));
        g2d.clearRect(0, 0, jl.getWidth(), jl.getHeight());
        jl.paint(g2d);
        return new ImageIcon(img.getScaledInstance(w, h, 4));
    }
    
    public ImageIcon createBlock(final Color color) {
        final BufferedImage img = new BufferedImage(2, 2, 1);
        final Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 2, 2);
        return new ImageIcon(img);
    }
    
    public ImageIcon createStart(final Color color) {
        final BufferedImage img = new BufferedImage(5, 5, 1);
        final Graphics2D g2d = img.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(1, 1, 3, 3);
        g2d.drawLine(2, 0, 2, 4);
        g2d.drawLine(0, 2, 4, 2);
        return new ImageIcon(img);
    }
    
    public Color getColor(final int i) {
        final Color color = this.vColor.get(i);
        return color;
    }
    
    public String convert(final double d) {
        return NumberFormat.getInstance(Locale.US).format(d);
    }
    
    public int getTicks() {
        return this.tick;
    }
    
    public void setTicks(final int t) {
        this.tick = t;
    }
    
    public void setNew(final Connecter connect) {
        this.setNew = new SetNew(connect);
    }
    
    public int getNewSize() {
        return this.setNew.getMSize();
    }
    
    public int getPlayerCount() {
        return this.setNew.getPlayer();
    }
    
    public int getAICount() {
        return this.setNew.getAI();
    }
    
    public void disposeNew() {
        this.setNew.dispose();
    }
    
    public GObject clone() {
        return this.unit.clone();
    }
    
    public void setGO(final GObject go) {
        this.unit = go;
    }
}
