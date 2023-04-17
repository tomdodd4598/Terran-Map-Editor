import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Vector;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.36
// 

public class Player implements ActionListener
{
    private PlayerRow pr;
    private JCB jcb;
    private Connecter connect;
    private int playerIndex;
    private ImageIcon img;
    private boolean isPlayer;
    private Vector<GObject> vGO;
    private GObject go;
    
    public Player(final boolean isPlayer, final Settings settings, final Connecter connect) {
        this.connect = connect;
        this.isPlayer = isPlayer;
        this.vGO = new Vector<GObject>();
        if (isPlayer) {
            this.playerIndex = settings.getPlayerCount();
            final int cplayer = this.playerIndex + 1;
            final Color color = connect.dm.getColor(this.playerIndex);
            this.go = new GObject("Dummy", this.playerIndex, "Objects/Default/Dummy" + this.playerIndex + ".pngU", connect);
            settings.addPlayerRowP(this.pr = new PlayerRow("Player " + cplayer, color, connect, this));
            if (cplayer == 8) {
                settings.enPAdd(false);
            }
            else if (cplayer == 3) {
                settings.enPRem(true);
            }
            if (this.playerIndex == 0) {
                this.img = connect.dm.loadImageSafety("Objects/Default/BlueSign00.png", true);
            }
            else {
                this.img = connect.dm.loadImageSafety("Objects/Default/YellowSign00.png", true);
            }
        }
        else {
            this.playerIndex = settings.getAICount() + settings.getPlayerCount();
            final int cai = settings.getAICount() + 1;
            settings.addPlayerRowA(this.pr = new PlayerRow("AI " + cai, null, connect, this));
            if (cai == 10) {
                settings.enAAdd(false);
            }
            if (cai == 1) {
                settings.enARem(true);
            }
            this.img = connect.dm.loadImageSafety("Objects/Default/RedSign00.png", true);
        }
        settings.addPlayer(this.playerIndex, this);
        settings.setSize((this.pr.getPreferredSize().width + 40 > settings.getPreferredSize().width) ? (this.pr.getPreferredSize().width + 40) : settings.getPreferredSize().width, settings.getSize().height + this.pr.getPSize());
        settings.repaint();
        this.addAgain();
    }
    
    public void setButton(final JCB jcb) {
        (this.jcb = jcb).addActionListener(this);
    }
    
    public void addAgain() {
        this.connect.lo.addItem(this.pr.getSelectedItem(), this.pr.getImg(), this.img, this.playerIndex);
    }
    
    public void remove() {
        if (this.isPlayer) {
            this.connect.settings.removePlayerRowP(this.pr);
            if (this.connect.settings.getPlayerCount() - 1 == 2) {
                this.connect.settings.enPRem(false);
            }
            if (this.connect.settings.getPlayerCount() - 1 == 7) {
                this.connect.settings.enPAdd(true);
            }
            this.connect.map.removeGO(this.go);
        }
        else {
            this.connect.settings.removePlayerRowA(this.pr);
            if (this.connect.settings.getAICount() - 1 == 0) {
                this.connect.settings.enARem(false);
            }
            if (this.connect.settings.getAICount() - 1 == 9) {
                this.connect.settings.enAAdd(true);
            }
        }
        this.removeObjects();
        this.connect.settings.setSize(this.connect.settings.getSize().width, this.connect.settings.getSize().height - this.pr.getPSize());
        this.connect.lo.removeItem(this.playerIndex);
    }
    
    private void removeObjects() {
        for (int i = 0; i < this.vGO.size(); ++i) {
            this.vGO.get(i).remove();
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        try {
            if (!this.jcb.isAllyed()) {
                this.img = this.connect.dm.loadImageSafety("Objects/Default/GreenSign00.png", true);
            }
            else {
                this.img = this.connect.dm.loadImageSafety("Objects/Default/RedSign00.png", true);
            }
            this.connect.lo.changePlayerSettings(this.playerIndex, this.pr.getSelectedItem(), this.pr.getImg(), this.img);
        }
        catch (Exception exc) {
            this.connect.lo.changePlayerSettings(this.playerIndex, this.pr.getSelectedItem(), this.pr.getImg());
        }
    }
    
    public void addGO(final GObject go) {
        this.vGO.add(go);
    }
    
    public void removeGO(final GObject go) {
        this.vGO.remove(go);
    }
    
    public Vector<GObject> getGOs() {
        return this.vGO;
    }
    
    public int getSize() {
        return this.vGO.size();
    }
    
    public GObject getGO(final int i) {
        return this.vGO.get(i);
    }
    
    public String getStartPoint() {
        return "\t\tStartPoint Vector3( " + this.connect.dm.convert(this.go.getLocation().x - this.connect.map.getMapSize() / 2) + ", " + this.connect.dm.convert(-(this.go.getLocation().y - this.connect.map.getMapSize() / 2)) + ", 0.000000 )";
    }
    
    public String getMatrix() {
        return "\t\tStartPointForwardVector Vector3( " + this.go.cos() + ", " + this.go.msin() + ", 0.000000 )";
    }
    
    public void startPos() {
        try {
            this.go.dummyPos();
        }
        catch (Exception ex) {}
    }
}
