import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class JCB extends JButton implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private boolean friendly;
    private Connecter connect;
    
    public JCB(final Connecter connect) {
        this.friendly = false;
        this.connect = connect;
        this.setBackground(null);
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.setIcon(connect.dm.loadImageSafety("Objects/Default/RedSign00.png", false));
        this.setRolloverIcon(connect.dm.loadImageSafety("Objects/Default/RedSign01.png", false));
        this.setPressedIcon(connect.dm.loadImageSafety("Objects/Default/YellowSign00.png", false));
        this.addActionListener(this);
        this.setPreferredSize(new Dimension(28, 21));
        this.setToolTipText(connect.cp.txt(20));
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        if (this.friendly) {
            this.setRelat(!this.friendly);
        }
        else {
            this.setRelat(!this.friendly);
        }
    }
    
    public boolean isAllyed() {
        return this.friendly;
    }
    
    public void setRelat(final boolean ally) {
        if (ally) {
            this.friendly = true;
            this.setIcon(this.connect.dm.loadImageSafety("Objects/Default/GreenSign00.png", false));
            this.setRolloverIcon(this.connect.dm.loadImageSafety("Objects/Default/GreenSign01.png", false));
            this.setPressedIcon(this.connect.dm.loadImageSafety("Objects/Default/YellowSign01.png", false));
        }
        else {
            this.friendly = false;
            this.setIcon(this.connect.dm.loadImageSafety("Objects/Default/RedSign00.png", true));
            this.setRolloverIcon(this.connect.dm.loadImageSafety("Objects/Default/RedSign01.png", false));
            this.setPressedIcon(this.connect.dm.loadImageSafety("Objects/Default/YellowSign00.png", false));
        }
    }
}
