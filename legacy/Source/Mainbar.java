import java.awt.Component;
import javax.swing.Icon;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class Mainbar extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JButton jbNew;
    private JButton jbLoad;
    private JButton jbSave;
    private JButton jbLook;
    private JButton jbDiplomacy;
    private JButton jbMapSettings;
    
    public Mainbar(final Connecter connect) {
        (this.jbNew = connect.cp.createButton("", "New", null, null)).setIcon(connect.dm.loadImageSafety("Default/folder.png", true));
        this.add(this.jbNew);
        (this.jbLoad = connect.cp.createButton("", "Load", null, null)).setIcon(connect.dm.loadImageSafety("Default/up_alt.png", true));
        this.add(this.jbLoad);
        (this.jbSave = connect.cp.createButton("", "Save", null, null)).setIcon(connect.dm.loadImageSafety("Default/down_alt.png", true));
        this.add(this.jbSave);
        (this.jbLook = connect.cp.createButton("", "Look", null, null)).setIcon(connect.dm.loadImageSafety("Default/Load01.png", true));
        this.jbLook.setRolloverIcon(connect.dm.loadImageSafety("Default/Load00.png", true));
        this.add(this.jbLook);
        (this.jbDiplomacy = connect.cp.createButton("", "Diplomacy", null, null)).setIcon(connect.dm.loadImageSafety("Default/Globe00.png", true));
        this.jbDiplomacy.setRolloverIcon(connect.dm.loadImageSafety("Default/Globe01.png", true));
        this.add(this.jbDiplomacy);
        (this.jbMapSettings = connect.cp.createButton("", "MapSettings", null, null)).setIcon(connect.dm.loadImageSafety("Default/advanced.png", true));
        this.add(this.jbMapSettings);
    }
}
