import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.io.File;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.util.Vector;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class PlayerRow extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Vector<String> vNames;
    private JLabel jlID;
    private JComboBox jcbName;
    private CustomComboBox ccbNations;
    
    public PlayerRow(final String name, final Color color, final Connecter connect, final Player player) {
        this.vNames = new Vector<String>();
        connect.cp.openStream(new File("Strings/GameDefines/TPTEAMNAMES_GameStrings.h"));
        String txtline;
        while ((txtline = connect.cp.read()) != null) {
            if (txtline.startsWith("#define")) {
                txtline = txtline.split(" ")[1];
                this.vNames.add(connect.cp.translate(txtline));
            }
        }
        final Vector<String> vNations = new Vector<String>();
        vNations.add(connect.cp.txt(23));
        vNations.add(connect.cp.txt(24));
        vNations.add(connect.cp.txt(25));
        vNations.add(connect.cp.txt(26));
        vNations.add(connect.cp.txt(27));
        final Vector<ImageIcon> vImages = new Vector<ImageIcon>();
        vImages.add(connect.dm.loadImageSafety("Objects/Default/AllM.png", true));
        vImages.add(connect.dm.loadImageSafety("Objects/Default/CivilianM.png", true));
        vImages.add(connect.dm.loadImageSafety("Objects/Default/NavyM.png", true));
        vImages.add(connect.dm.loadImageSafety("Objects/Default/PiratesM.png", true));
        vImages.add(connect.dm.loadImageSafety("Objects/Default/ProcyonM.png", true));
        this.setLayout(new BoxLayout(this, 0));
        (this.jlID = new JLabel(String.valueOf(name) + "  ")).setForeground(color);
        this.add(this.jlID);
        (this.jcbName = new JComboBox((Vector<E>)this.vNames)).addActionListener(player);
        this.jcbName.setPreferredSize(this.jcbName.getPreferredSize());
        this.add(this.jcbName);
        (this.ccbNations = new CustomComboBox(vNations, vImages)).setTextPosition(4);
        this.ccbNations.addActionListener(player);
        this.add(this.ccbNations);
    }
    
    public Color getColor() {
        return this.jlID.getForeground();
    }
    
    public String getSelectedItem() {
        return (String)this.jcbName.getSelectedItem();
    }
    
    public ImageIcon getImg() {
        return this.ccbNations.getImg();
    }
    
    public int getPSize() {
        return this.ccbNations.getPreferredSize().height;
    }
}
