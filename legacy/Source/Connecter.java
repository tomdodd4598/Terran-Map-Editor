import java.awt.Insets;
import java.awt.GraphicsConfiguration;
import java.awt.Dimension;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Connecter extends JFrame
{
    private static final long serialVersionUID = 1L;
    JPanel jpMain;
    Mainbar mainbar;
    Map map;
    Overview overview;
    Dummy dummy;
    JPanel jpRight;
    List list;
    CommandPackage cp;
    DataManagement dm;
    EventHandler eh;
    ListOptions lo;
    Diplomacy diplo;
    Settings settings;
    
    public Connecter() {
        this.dummy = new Dummy(this);
        this.cp = new CommandPackage(this);
        this.dm = new DataManagement();
        this.eh = new EventHandler(this);
        this.lo = new ListOptions(this);
        this.diplo = new Diplomacy(this);
        this.setDefaultCloseOperation(3);
        this.setTitle(this.cp.txt(7));
        final Toolkit kit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = kit.getScreenSize();
        final GraphicsConfiguration config = this.getGraphicsConfiguration();
        final Insets insets = kit.getScreenInsets(config);
        final Dimension dimension = screenSize;
        dimension.width -= insets.left + insets.right;
        final Dimension dimension2 = screenSize;
        dimension2.height -= insets.top + insets.bottom;
        this.setSize(screenSize);
        this.setExtendedState(1);
        (this.jpMain = new JPanel()).setLayout(new BorderLayout());
        this.add(this.jpMain);
        this.mainbar = new Mainbar(this);
        this.jpMain.add(this.mainbar, "North");
        this.map = new Map(this);
        this.jpMain.add(this.map, "Center");
        (this.jpRight = new JPanel()).setLayout(new BoxLayout(this.jpRight, 1));
        this.jpMain.add(this.jpRight, "East");
        this.setVisible(true);
        this.overview = new Overview(this);
        this.map.createMinimap(false);
        this.settings = new Settings(this, 2, 0);
        this.jpRight.add(this.lo);
        this.list = new List(this);
        this.jpRight.add(this.list, "East");
        this.setVisible(true);
        this.setExtendedState(6);
        this.overview.renew();
    }
}
