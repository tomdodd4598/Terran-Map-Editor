import javax.swing.Box;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// 
// Decompiled by Procyon v0.5.36
// 

public class List extends JScrollPane
{
    private static final long serialVersionUID = 1L;
    private JPanel jpDefault;
    private JPanel jpLft;
    private JPanel jpMid;
    private JPanel jpRgt;
    private Vector<ObjectReference> vOR;
    private String ign;
    private String id;
    private String nation;
    private String cost;
    private String pic;
    private String aw;
    private String dw;
    private String b;
    private String typeFixed;
    private Connecter connect;
    
    public List(final Connecter connect) {
        this.b = "<br>";
        this.connect = connect;
        this.setVerticalScrollBarPolicy(22);
        this.createList(connect.lo.getType(), connect.lo.getNation());
    }
    
    public void createList(final String type, final String race) {
        (this.jpDefault = new JPanel()).setLayout(new BoxLayout(this.jpDefault, 0));
        (this.jpLft = new JPanel()).setLayout(new BoxLayout(this.jpLft, 1));
        this.jpDefault.add(this.jpLft);
        (this.jpMid = new JPanel()).setLayout(new BoxLayout(this.jpMid, 1));
        this.jpDefault.add(this.jpMid);
        (this.jpRgt = new JPanel()).setLayout(new BoxLayout(this.jpRgt, 1));
        this.jpDefault.add(this.jpRgt);
        this.jpLft.setAlignmentY(0.0f);
        this.jpMid.setAlignmentY(0.0f);
        this.jpRgt.setAlignmentY(0.0f);
        this.vOR = new Vector<ObjectReference>();
        this.createORs(type, race);
        this.sort();
        this.getViewport().setView(this.jpDefault);
    }
    
    private void createORs(final String type, final String race) {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(new File("Data/ConnectingData.h")));
            String txtline;
            while (!(txtline = br.readLine()).equals("<<" + type + ">>") && !type.equals("All")) {}
            this.typeFixed = txtline.substring(2, txtline.length() - 2);
            while (!(txtline = br.readLine()).equals("<<End>>")) {
                if (txtline.startsWith("<<")) {
                    if (!type.equals("All")) {
                        break;
                    }
                    this.typeFixed = txtline.substring(2, txtline.length() - 2);
                    txtline = br.readLine();
                }
                final String[] data = txtline.split("//");
                this.id = data[0];
                this.ign = this.connect.cp.translate(data[1]);
                this.nation = data[2];
                this.pic = data[3];
                this.aw = data[4];
                this.dw = data[5];
                this.cost = data[6];
                if (!this.nation.equals(race)) {
                    if (!race.equals("All")) {
                        continue;
                    }
                }
                try {
                    final String img = "Objects/" + this.typeFixed + "s/" + this.pic + ".png";
                    final String tooltip = "<html>" + this.ign + this.b + this.aw + " " + this.connect.cp.txt(21) + this.b + this.dw + " " + this.connect.cp.txt(22) + this.b + "</html>";
                    final ObjectReference orD = new ObjectReference(this.ign, this.id, this.nation, this.typeFixed, this.cost, tooltip, img, this.connect);
                    if (data.length == 8) {
                        orD.setDegree(Integer.parseInt(data[7]));
                    }
                    this.saveOR(orD);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    private Boolean saveOR(final ObjectReference OR) {
        for (int i = 0; i < this.vOR.size(); ++i) {
            if (OR.race().equals(this.vOR.get(i).race()) && OR.cost() > this.vOR.get(i).cost()) {
                this.vOR.add(i, OR);
                return true;
            }
        }
        this.vOR.add(OR);
        return false;
    }
    
    private void sort() {
        int c = 0;
        for (int i = 0; i < this.vOR.size(); ++i) {
            switch (c) {
                case 0: {
                    this.jpLft.add(this.vOR.get(i));
                    ++c;
                    break;
                }
                case 1: {
                    this.jpMid.add(this.vOR.get(i));
                    ++c;
                    break;
                }
                case 2: {
                    this.jpRgt.add(this.vOR.get(i));
                    c = 0;
                    break;
                }
            }
        }
        this.jpLft.add(Box.createVerticalGlue());
        this.jpMid.add(Box.createVerticalGlue());
        this.jpRgt.add(Box.createVerticalGlue());
    }
}
