import java.awt.event.ActionEvent;
import javax.swing.Icon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class ObjectReference extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private String ign;
    private String rn;
    private String type;
    private String race;
    private String img;
    private int cost;
    private int degree;
    private Connecter connect;
    
    public ObjectReference(final String inGameName, final String referenceName, final String race, final String type, final String cost, final String tooltip, final String img, final Connecter connect) {
        this.degree = 0;
        this.setPreferredSize(new Dimension(53, 42));
        this.setIcon(connect.dm.loadImageSafety(img, false));
        this.setToolTipText(tooltip);
        this.addActionListener(this);
        this.ign = inGameName;
        this.rn = referenceName;
        this.type = type;
        this.connect = connect;
        this.img = img;
        this.cost = Integer.parseInt(cost);
        this.race = race;
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        this.connect.dm.setGO(new GObject(this.rn, this.ign, 0, this.img, this.type, this.degree, this.connect));
        this.connect.dummy.setPic(this.img, this.type);
    }
    
    public void setDegree(final int degree) {
        this.degree = degree;
    }
    
    public int cost() {
        return this.cost;
    }
    
    public String race() {
        return this.race;
    }
}
