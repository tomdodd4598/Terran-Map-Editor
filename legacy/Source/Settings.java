import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.Vector;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Settings extends JFrame implements WindowListener
{
    private static final long serialVersionUID = 1L;
    private int players;
    private int ai;
    private Vector<Player> vP;
    private int size;
    private JPanel jpDefault;
    private JPanel jpAdd;
    private JButton jbAPlayer;
    private JButton jbRPlayer;
    private JButton jbAAI;
    private JButton jbRAI;
    private JPanel jpPlayerSettings;
    private JPanel jpPlayer;
    private JPanel jpDiffer;
    private JPanel jpAI;
    private JSlider jsSize;
    private JButton jbOk;
    private Connecter connect;
    
    public Settings(final Connecter connect, final int players, final int ai) {
        this.players = 0;
        this.ai = 0;
        this.connect = connect;
        this.setSize(400, 200);
        this.vP = new Vector<Player>();
        this.size = 175;
        (this.jpDefault = new JPanel()).setLayout(new BoxLayout(this.jpDefault, 1));
        this.add(this.jpDefault);
        (this.jpAdd = new JPanel()).setLayout(new BoxLayout(this.jpAdd, 0));
        this.jpDefault.add(this.jpAdd);
        this.jbAPlayer = connect.cp.createButton(connect.cp.txt(40), "AddPlayer", null, null);
        this.jpAdd.add(this.jbAPlayer);
        (this.jbRPlayer = connect.cp.createButton(connect.cp.txt(41), "RemovePlayer", null, null)).setEnabled(false);
        this.jpAdd.add(this.jbRPlayer);
        this.jbAAI = connect.cp.createButton(connect.cp.txt(42), "AddAI", null, null);
        this.jpAdd.add(this.jbAAI);
        (this.jbRAI = connect.cp.createButton(connect.cp.txt(43), "RemoveAI", null, null)).setEnabled(false);
        this.jpAdd.add(this.jbRAI);
        (this.jpPlayerSettings = new JPanel()).setBorder(BorderFactory.createTitledBorder(connect.cp.txt(44)));
        this.jpPlayerSettings.setLayout(new BorderLayout());
        this.jpDefault.add(this.jpPlayerSettings);
        (this.jpPlayer = new JPanel()).setLayout(new BoxLayout(this.jpPlayer, 1));
        this.jpPlayerSettings.add(this.jpPlayer, "North");
        this.jpDiffer = new JPanel();
        this.jpPlayerSettings.add(this.jpDiffer, "Center");
        (this.jpAI = new JPanel()).setLayout(new BoxLayout(this.jpAI, 1));
        this.jpPlayerSettings.add(this.jpAI, "South");
        for (int i = 0; i < players; ++i) {
            this.createPlayer();
        }
        for (int i = 0; i < ai; ++i) {
            this.createAI();
        }
        (this.jsSize = new JSlider(100, 300, this.size)).setMinorTickSpacing(5);
        this.jsSize.setMajorTickSpacing(50);
        this.jsSize.setPaintTicks(true);
        this.jsSize.setPaintLabels(true);
        this.jsSize.setBorder(BorderFactory.createTitledBorder(connect.cp.txt(36)));
        this.jpDefault.add(this.jsSize);
        (this.jbOk = new JButton(connect.cp.txt(38))).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                Settings.this.end();
            }
        });
        this.jpDefault.add(this.jbOk);
        this.setDefaultCloseOperation(0);
        this.setTitle(String.valueOf(connect.cp.txt(7)) + " - " + connect.cp.txt(45));
        this.setResizable(true);
        this.setVisible(false);
        this.addWindowListener(this);
        this.setAlwaysOnTop(true);
    }
    
    public void createPlayer() {
        new Player(true, this, this.connect);
        ++this.players;
    }
    
    public void removePlayer() {
        this.vP.get(this.players - 1).remove();
        this.vP.remove(this.players - 1);
        --this.players;
    }
    
    public void createAI() {
        new Player(false, this, this.connect);
        ++this.ai;
    }
    
    public void removeAI() {
        this.vP.get(this.players + this.ai - 1).remove();
        this.vP.remove(this.players + this.ai - 1);
        --this.ai;
    }
    
    public void end() {
        if (this.isVisible()) {
            this.setVisible(false);
            this.connect.diplo.createDiplMenu(this.players, this.ai);
            final int i;
            if ((i = this.jsSize.getValue()) != this.size) {
                this.connect.map.createMap(i * 20, i * 20, true);
                this.size = i;
                this.connect.overview.resetView();
            }
        }
    }
    
    public boolean isPlayer(final int i) {
        return i < this.players;
    }
    
    public int getPlayerCount() {
        return this.players;
    }
    
    public int getAICount() {
        return this.ai;
    }
    
    public Player getPlayer(final int i) {
        return this.vP.get(i);
    }
    
    public void addPlayer(final int i, final Player p) {
        this.vP.add(i, p);
    }
    
    public void addPlayerRowP(final PlayerRow row) {
        this.jpPlayer.add(row);
    }
    
    public void addPlayerRowA(final PlayerRow row) {
        this.jpAI.add(row);
    }
    
    public void removePlayerRowP(final PlayerRow row) {
        this.jpPlayer.remove(row);
    }
    
    public void removePlayerRowA(final PlayerRow row) {
        this.jpAI.remove(row);
    }
    
    public void enPAdd(final boolean b) {
        this.jbAPlayer.setEnabled(b);
    }
    
    public void enPRem(final boolean b) {
        this.jbRPlayer.setEnabled(b);
    }
    
    public void enAAdd(final boolean b) {
        this.jbAAI.setEnabled(b);
    }
    
    public void enARem(final boolean b) {
        this.jbRAI.setEnabled(b);
    }
    
    @Override
    public void windowActivated(final WindowEvent e) {
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void windowClosed(final WindowEvent e) {
    }
    
    @Override
    public void windowClosing(final WindowEvent e) {
        this.end();
    }
    
    @Override
    public void windowDeactivated(final WindowEvent e) {
    }
    
    @Override
    public void windowDeiconified(final WindowEvent e) {
    }
    
    @Override
    public void windowIconified(final WindowEvent e) {
        this.setState(0);
    }
    
    @Override
    public void windowOpened(final WindowEvent e) {
    }
}
