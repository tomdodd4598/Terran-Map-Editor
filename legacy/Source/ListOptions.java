import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.util.Vector;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class ListOptions extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Vector<String> vDataNations;
    private Vector<String> vDataTypes;
    private Vector<String> vDataPlayers;
    private Vector<ImageIcon> vDataImgs;
    private Vector<ImageIcon> vDataNation;
    private Vector<ImageIcon> vDataRelation;
    private JComboBox jcType;
    private CustomComboBox ccbNations;
    private CustomComboBox ccbPlayer;
    private JSlider jsDegree;
    private Connecter connect;
    
    public ListOptions(final Connecter connect) {
        this.setLayout(new BoxLayout(this, 1));
        this.connect = connect;
        (this.vDataNations = new Vector<String>()).add(connect.cp.txt(23));
        this.vDataNations.add(connect.cp.txt(24));
        this.vDataNations.add(connect.cp.txt(25));
        this.vDataNations.add(connect.cp.txt(26));
        this.vDataNations.add(connect.cp.txt(27));
        (this.vDataTypes = new Vector<String>()).add(connect.cp.txt(23));
        this.vDataTypes.add(connect.cp.txt(28));
        this.vDataTypes.add(connect.cp.txt(29));
        this.vDataTypes.add(connect.cp.txt(30));
        this.vDataTypes.add(connect.cp.txt(31));
        this.vDataTypes.add(connect.cp.txt(32));
        this.vDataTypes.add(connect.cp.txt(33));
        (this.vDataImgs = new Vector<ImageIcon>()).add(connect.dm.loadImageSafety("Objects/Default/AllM.png", true));
        this.vDataImgs.add(connect.dm.loadImageSafety("Objects/Default/CivilianM.png", true));
        this.vDataImgs.add(connect.dm.loadImageSafety("Objects/Default/NavyM.png", true));
        this.vDataImgs.add(connect.dm.loadImageSafety("Objects/Default/PiratesM.png", true));
        this.vDataImgs.add(connect.dm.loadImageSafety("Objects/Default/ProcyonM.png", true));
        this.vDataPlayers = new Vector<String>();
        this.vDataNation = new Vector<ImageIcon>();
        this.vDataRelation = new Vector<ImageIcon>();
        (this.jcType = new JComboBox((Vector<E>)this.vDataTypes)).addActionListener(connect.eh);
        this.jcType.setActionCommand("jcChange");
        this.add(this.jcType);
        (this.ccbNations = new CustomComboBox(this.vDataNations, this.vDataImgs)).addActionListener(connect.eh);
        this.ccbNations.setActionCommand("jcChange");
        this.ccbNations.setTextPosition(4);
        this.add(this.ccbNations);
        (this.ccbPlayer = new CustomComboBox(this.vDataPlayers, this.vDataNation, this.vDataRelation)).setTextPosition(4);
        this.add(this.ccbPlayer);
        (this.jsDegree = new JSlider(-180, 180, 0)).setMinorTickSpacing(45);
        this.jsDegree.setMajorTickSpacing(90);
        this.jsDegree.setPaintTicks(true);
        this.jsDegree.setPaintLabels(true);
        this.jsDegree.setSnapToTicks(true);
        this.jsDegree.setBorder(BorderFactory.createTitledBorder(connect.cp.txt(48)));
        this.add(this.jsDegree);
    }
    
    public int getRot() {
        return this.jsDegree.getValue();
    }
    
    public void setRot(final int degree) {
        this.jsDegree.setValue(degree);
        this.connect.dummy.resetPic();
    }
    
    public int getMTick() {
        return this.jsDegree.getMinorTickSpacing();
    }
    
    public void setMTick(final int tick) {
        this.connect.dm.setTicks(tick);
        this.jsDegree.setMinorTickSpacing(tick);
        this.jsDegree.setValue(this.jsDegree.getValue() + this.jsDegree.getValue() % tick);
        this.connect.dummy.resetPic();
    }
    
    public String getType() {
        switch (this.jcType.getSelectedIndex()) {
            case 0: {
                return "All";
            }
            case 1: {
                return "Animal";
            }
            case 2: {
                return "Asteroid";
            }
            case 3: {
                return "Base";
            }
            case 4: {
                return "Island";
            }
            case 5: {
                return "Ship";
            }
            case 6: {
                return "Terrain";
            }
            default: {
                return "All";
            }
        }
    }
    
    public String getNation() {
        switch (this.ccbNations.getSelectedIndex()) {
            case 0: {
                return "All";
            }
            case 1: {
                return "Civilian";
            }
            case 2: {
                return "Navy";
            }
            case 3: {
                return "Pirate";
            }
            case 4: {
                return "Procyon";
            }
            default: {
                return "All";
            }
        }
    }
    
    public Vector<String> getNations() {
        return this.vDataNations;
    }
    
    public Vector<ImageIcon> getIVector() {
        return this.vDataImgs;
    }
    
    public int getSelectedPlayer() {
        return this.ccbPlayer.getSelectedIndex();
    }
    
    public void changePlayerSettings(final int i, final String s, final ImageIcon img, final ImageIcon img2) {
        this.changePlayerSettings(i, s, img);
        this.vDataRelation.set(i, img2);
    }
    
    public void changePlayerSettings(final int i, final String s, final ImageIcon img) {
        this.vDataPlayers.set(i, s);
        this.vDataNation.set(i, img);
    }
    
    public void addItem(final String s, final ImageIcon img01, final ImageIcon img02, final int index) {
        this.ccbPlayer.addItem(s, img01, img02, index);
    }
    
    public void resetCCBP() {
        this.ccbPlayer.removeAll();
    }
    
    public void removeItem(final int i) {
        this.ccbPlayer.removeItem(i);
    }
}
