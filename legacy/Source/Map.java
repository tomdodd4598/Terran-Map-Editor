import java.awt.event.AdjustmentEvent;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollPane;

// 
// Decompiled by Procyon v0.5.36
// 

public class Map extends JScrollPane implements AdjustmentListener
{
    private static final long serialVersionUID = 1L;
    private JLayeredPane jpLookAt;
    private JPanel jpBGr;
    private JLabel jlAdd;
    private Connecter connect;
    private ImageIcon imgBG00;
    private ImageIcon imgBG01;
    private String imgPath;
    private int size;
    
    public Map(final Connecter connect) {
        this.imgPath = "Space/Space_Yellow_Alternative.png";
        this.connect = connect;
        this.getVerticalScrollBar().addAdjustmentListener(this);
        this.getHorizontalScrollBar().addAdjustmentListener(this);
        (this.jlAdd = new JLabel()).addMouseListener(connect.eh);
        this.jlAdd.addMouseMotionListener(connect.eh);
        this.jlAdd.addMouseWheelListener(connect.eh);
        this.createMap(3500, 3500, false);
    }
    
    public void createMap(final int mapW, final int mapH, final boolean minimap) {
        this.size = mapW;
        (this.jpLookAt = new JLayeredPane()).setLayout(null);
        this.jpLookAt.add(this.jpBGr = new JPanel(null), new Integer(1));
        this.jpLookAt.add(this.jlAdd, new Integer(2));
        this.jpLookAt.add(this.connect.dummy, new Integer(3));
        this.jpLookAt.setBackground(Color.black);
        this.imgBG00 = this.connect.dm.loadImageSafety(this.imgPath, false);
        final int w = this.imgBG00.getIconWidth();
        final int h = this.imgBG00.getIconHeight();
        final int dw = mapW / w;
        final int dh = mapH / h + 1;
        final int rw = mapW - w * dw;
        final int rh = h;
        this.imgBG01 = this.connect.dm.loadImage(this.imgPath, rw, rh);
        for (int i = 0; i < dh; ++i) {
            for (int j = 0; j < dw; ++j) {
                final JLabel jlDefault = new JLabel();
                jlDefault.setIcon(this.imgBG00);
                jlDefault.setBounds(j * w, i * h, w, h);
                this.jpBGr.add(jlDefault);
            }
            final JLabel jlDefault2 = new JLabel();
            jlDefault2.setIcon(this.imgBG01);
            jlDefault2.setBounds(dw * w, i * h, rw, rh);
            this.jpBGr.add(jlDefault2);
        }
        this.jpBGr.setSize(mapW, mapH);
        this.jlAdd.setSize(mapW, mapH);
        this.jpLookAt.setPreferredSize(new Dimension(mapW, mapH));
        this.getViewport().setView(this.jpLookAt);
        if (minimap) {
            this.createMinimap(true);
        }
    }
    
    public void createMinimap(final boolean b) {
        this.connect.overview.renew();
        this.jpLookAt.add(this.connect.overview, new Integer(5));
        if (b) {
            this.connect.cp.resetGO();
        }
    }
    
    public int getMapSize() {
        return this.size;
    }
    
    public int getVValue() {
        return this.getVerticalScrollBar().getValue();
    }
    
    public int getHValue() {
        return this.getHorizontalScrollBar().getValue();
    }
    
    public void setVValue(final double d) {
        this.getVerticalScrollBar().setValue((int)d);
    }
    
    public void setHValue(final double i) {
        this.getHorizontalScrollBar().setValue((int)i);
    }
    
    public int getVSize() {
        return this.getVerticalScrollBar().getHeight();
    }
    
    public int getHSize() {
        return this.getHorizontalScrollBar().getWidth();
    }
    
    public void setView(final int x, final int y) {
        this.getViewport().setViewPosition(new Point(x, y));
    }
    
    @Override
    public void adjustmentValueChanged(final AdjustmentEvent arg0) {
        try {
            this.connect.overview.resetView();
        }
        catch (Exception ex) {}
    }
    
    public void addGO(final GObject go) {
        this.jlAdd.add(go);
    }
    
    public void removeGO(final GObject go) {
        this.jlAdd.remove(go);
    }
}
