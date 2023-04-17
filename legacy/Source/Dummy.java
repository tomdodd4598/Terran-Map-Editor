import javax.swing.Icon;
import javax.swing.JLabel;

// 
// Decompiled by Procyon v0.5.36
// 

public class Dummy extends JLabel
{
    private static final long serialVersionUID = 1L;
    private int dx;
    private int dy;
    private int degree;
    private String type;
    private String file0;
    private String file1;
    private String file2;
    private String relation;
    private String img;
    private Connecter connect;
    
    public Dummy(final Connecter connect) {
        this.dx = 0;
        this.dy = 0;
        this.degree = 0;
        this.connect = connect;
        this.file0 = "Objects/Default/";
        this.file1 = "Type";
        this.file2 = ".png";
        this.relation = "N";
    }
    
    public void setPic(final String img, final String type) {
        this.type = type;
        this.img = img;
        this.changePic();
    }
    
    public void changeRelat(final String relat) {
        this.relation = relat;
        this.changePic();
    }
    
    public void resetPic() {
        this.degree = this.connect.lo.getRot();
        this.changePic();
    }
    
    private void changePic() {
        try {
            if (this.connect.dm.specific) {
                this.setIcon(this.connect.dm.rotate(String.valueOf(this.file0) + this.type + this.file1 + this.relation + this.file2, this.degree));
            }
            else {
                this.setIcon(this.connect.dm.rotate(this.img, this.degree));
            }
            this.setSize(this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
            this.dx = this.getIcon().getIconWidth() / 2;
            this.dy = this.getIcon().getIconHeight() / 2;
        }
        catch (Exception ex) {}
    }
    
    public void setPosition(final int x, final int y) {
        this.setLocation(x - this.dx, y - this.dy);
    }
}
