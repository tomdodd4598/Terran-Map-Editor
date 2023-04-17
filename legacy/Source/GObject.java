import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import java.awt.Component;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

// 
// Decompiled by Procyon v0.5.36
// 

public class GObject extends JLabel implements MouseMotionListener, MouseListener, MouseWheelListener
{
    private static final long serialVersionUID = 1L;
    private String ID;
    private String name;
    private String imgT;
    private String imgD;
    private String type;
    private String file00;
    private String file01;
    private String file02;
    private int player;
    private int degree;
    private int dw;
    private int dh;
    private int odegree;
    private int mx;
    private int my;
    private int index;
    private boolean run;
    private Dummy dummy;
    private Connecter connect;
    
    public GObject(final String ID, final String name, final int player, final String imgD, final String type, final int odegree, final Connecter connect) {
        this.file00 = "Objects/Default/";
        this.file01 = "Type";
        this.file02 = ".png";
        this.index = 0;
        this.run = false;
        this.connect = connect;
        this.player = player;
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.odegree = odegree + 90;
        this.imgD = imgD;
        this.imgT = "Objects/Default/" + type + "Type" + this.proovAlly() + ".png";
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.rotPicture();
        this.dummy = new Dummy(connect);
        this.createDummy();
    }
    
    public GObject(final String ID, final int player, final String imgD, final Connecter connect) {
        this.file00 = "Objects/Default/";
        this.file01 = "Type";
        this.file02 = ".png";
        this.index = 0;
        this.run = false;
        this.connect = connect;
        this.player = player;
        this.name = ID;
        this.ID = ID;
        this.type = imgD.substring(0, imgD.length() - 1);
        this.odegree = 0;
        this.imgD = imgD;
        this.file00 = "";
        this.file01 = "";
        this.file02 = "";
        this.setLocation(connect.map.getMapSize() / 2, connect.map.getMapSize() / 2);
        connect.map.addGO(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.rotPicture();
        this.dummy = new Dummy(connect);
        connect.overview.add(this.dummy);
        this.dummy.setIcon(connect.dm.createStart(connect.dm.getColor(player)));
        this.dummy.setSize(5, 5);
        this.dummy.setLocation(connect.overview.getFixX(this.getX()), connect.overview.getFixY(this.getY()));
    }
    
    public void createDummy() {
        final int w = this.connect.overview.getFixX(this.getIcon().getIconWidth());
        final int h = this.connect.overview.getFixY(this.getIcon().getIconHeight());
        this.connect.overview.add(this.dummy);
        if (this.type.equals("Ship")) {
            this.dummy.setIcon(this.connect.dm.createBlock(this.connect.dm.getColor(this.player)));
        }
        else {
            this.dummy.setIcon(this.connect.dm.createMini(w, h, this));
        }
        this.dummy.setSize(w, h);
        this.dummyPos();
    }
    
    private String picPath() {
        return String.valueOf(this.file00) + this.type + this.file01 + this.proovAlly() + this.file02;
    }
    
    public void rotPicture() {
        this.degree = this.connect.lo.getRot();
        this.changePic();
    }
    
    public void changePic() {
        if (this.connect.dm.specific) {
            this.imgT = this.picPath();
            this.setIcon(this.connect.dm.rotate(this.imgT, this.degree));
        }
        else {
            this.setIcon(this.connect.dm.rotate(this.imgD, this.degree));
        }
        final int w = this.getIcon().getIconWidth();
        final int h = this.getIcon().getIconHeight();
        this.setLocation((int)this.getLocation().getX() + this.dw - w / 2, (int)this.getLocation().getY() + this.dh - h / 2);
        this.dw = w / 2;
        this.dh = h / 2;
        this.setSize(w, h);
        this.repaint();
    }
    
    public String getID() {
        return this.ID;
    }
    
    public int getDegree() {
        return this.degree + this.odegree;
    }
    
    public String getMatrix() {
        final String cos = this.cos();
        final String sin = this.sin();
        final String msin = this.msin();
        final String string = "\t\tOrientation Matrix33( " + cos + ", " + msin + ", 0.000000, " + sin + ", " + cos + ", 0.000000, 0.000000, 0.000000, 1.000000 )";
        return string;
    }
    
    public String getVector() {
        return "\t\tPosition Vector3( " + (this.getLocation().x + this.dw - this.connect.map.getMapSize() / 2) + ".000000, " + -(this.getLocation().y + this.dh - this.connect.map.getMapSize() / 2) + ".000000, 0.000000 )";
    }
    
    public GObject clone() {
        final GObject objectD = new GObject(this.ID, this.name, this.connect.lo.getSelectedPlayer(), this.imgD, this.type, this.odegree, this.connect);
        return objectD;
    }
    
    public int getLocFixW() {
        return this.dw;
    }
    
    public int getLocFixH() {
        return this.dh;
    }
    
    public String proovAlly() {
        if (this.connect.settings.isPlayer(this.player)) {
            if (this.player == 0) {
                return "N";
            }
            return "U";
        }
        else {
            if (this.connect.diplo.getRelat(1, this.player - this.connect.settings.getPlayerCount() + 1)) {
                return "A";
            }
            return "E";
        }
    }
    
    public String sin() {
        return this.connect.dm.convert(Math.sin((this.degree + this.odegree) * 3.141592653589793 / 180.0));
    }
    
    public String msin() {
        return this.connect.dm.convert(-Math.sin((this.degree + this.odegree) * 3.141592653589793 / 180.0));
    }
    
    public String cos() {
        return this.connect.dm.convert(Math.cos((this.degree + this.odegree) * 3.141592653589793 / 180.0));
    }
    
    @Override
    public void mouseDragged(final MouseEvent arg0) {
        this.setPos(this.getX() + arg0.getX() - this.mx, this.getY() + arg0.getY() - this.my);
        this.repaint();
    }
    
    @Override
    public void mouseMoved(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseClicked(final MouseEvent arg0) {
        if (arg0.getButton() == 3) {
            this.remove();
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent arg0) {
        this.connect.lo.setRot(this.degree);
    }
    
    @Override
    public void mouseExited(final MouseEvent arg0) {
    }
    
    @Override
    public void mousePressed(final MouseEvent arg0) {
        this.run = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                GObject.this.fixViewport();
            }
        }).start();
        this.mx = arg0.getX();
        this.my = arg0.getY();
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
        this.run = false;
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent arg0) {
        final int notches = arg0.getWheelRotation();
        int i;
        if (notches < 0) {
            i = this.connect.lo.getRot() + this.connect.dm.getTicks();
            if (i > 180) {
                i -= 360;
            }
        }
        else {
            i = this.connect.lo.getRot() - this.connect.dm.getTicks();
            if (i < -180) {
                i += 360;
            }
        }
        this.connect.lo.setRot(i);
        this.rotPicture();
        if (this.type.equals("Island") | this.type.equals("Base") | this.type.equals("Asteroid")) {
            this.createDummy();
        }
    }
    
    public void fixViewport() {
        while (this.run) {
            final int vv = this.connect.map.getVValue();
            final int hv = this.connect.map.getHValue();
            final int vvr = vv + this.connect.map.getVSize();
            final int hvr = hv + this.connect.map.getHSize();
            int x = 0;
            int y = 0;
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (this.getY() < vv + 60) {
                y = -1;
            }
            else if (this.getY() > vvr - 60) {
                y = 1;
            }
            if (this.getX() < hv + 60) {
                x = -1;
            }
            else if (this.getX() > hvr - 60) {
                x = 1;
            }
            this.connect.map.setVValue(vv + y);
            this.connect.map.setHValue(hv + x);
            this.setPos(this.getX() + x, this.getY() + y);
        }
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public void setPos(final int x, final int y) {
        this.setLocation(x, y);
        this.dummyPos();
    }
    
    public void dummyPos() {
        this.dummy.setLocation(this.connect.overview.getFixX(this.getX()), this.connect.overview.getFixY(this.getY()));
    }
    
    private void removeDummy() {
        this.connect.overview.remove(this.dummy);
    }
    
    public void remove() {
        this.connect.map.removeGO(this);
        this.removeDummy();
        this.connect.settings.getPlayer(this.player).removeGO(this);
        this.connect.map.repaint();
    }
}
