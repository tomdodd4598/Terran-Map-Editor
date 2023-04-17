import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

// 
// Decompiled by Procyon v0.5.36
// 

public class Overview extends JLabel implements MouseListener, MouseMotionListener
{
    private static final long serialVersionUID = 1L;
    private JLabel jlBox;
    private Connecter connect;
    private double dw;
    private double dh;
    private int w;
    private int h;
    
    public Overview(final Connecter connect) {
        this.connect = connect;
        this.setSize(300, 300);
        this.setBorder(new LineBorder(Color.black, 3));
        this.jlBox = new JLabel();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    public void renew() {
        this.dw = 300.0 / this.connect.map.getMapSize();
        this.dh = 300.0 / this.connect.map.getMapSize();
        this.w = (int)(this.dw * this.connect.map.getHSize());
        this.h = (int)(this.dh * this.connect.map.getVSize());
        this.jlBox.setIcon(this.connect.dm.createBlock(this.w, this.h, Color.red));
        this.add(this.jlBox);
        this.jlBox.setSize(this.w, this.h);
        this.resetView();
        this.w /= (int)this.dw;
        this.h /= (int)this.dh;
    }
    
    public int getFixX(final int x) {
        return (int)(this.dw * x);
    }
    
    public int getFixY(final int y) {
        return (int)(this.dh * y);
    }
    
    public void resetView() {
        this.jlBox.setLocation((int)(this.dw * this.connect.map.getHValue()), (int)(this.dh * this.connect.map.getVValue()));
        this.setLocation(this.connect.map.getHValue(), this.connect.map.getVValue());
    }
    
    @Override
    public void mouseDragged(final MouseEvent arg0) {
        this.connect.map.setVValue(arg0.getY() / this.dh - this.h / 2);
        this.connect.map.setHValue(arg0.getX() / this.dw - this.w / 2);
        this.resetView();
    }
    
    @Override
    public void mouseMoved(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseClicked(final MouseEvent arg0) {
        this.mouseDragged(arg0);
    }
    
    @Override
    public void mouseEntered(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseExited(final MouseEvent arg0) {
    }
    
    @Override
    public void mousePressed(final MouseEvent arg0) {
        this.mouseDragged(arg0);
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
    }
}
