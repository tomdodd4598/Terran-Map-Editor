import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class SetNew extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel jpDefault;
    private JSlider jsPlayer;
    private JSlider jsAI;
    private JSlider jsSize;
    private JLabel jlInfo;
    private JButton jbOk;
    
    public SetNew(final Connecter connect) {
        (this.jpDefault = new JPanel()).setLayout(new BoxLayout(this.jpDefault, 1));
        this.add(this.jpDefault);
        (this.jsPlayer = new JSlider(2, 8, 2)).setMajorTickSpacing(1);
        this.jsPlayer.setPaintTicks(true);
        this.jsPlayer.setPaintLabels(true);
        this.jsPlayer.setSnapToTicks(true);
        this.jsPlayer.setBorder(BorderFactory.createTitledBorder(connect.cp.txt(34)));
        this.jpDefault.add(this.jsPlayer);
        (this.jsAI = new JSlider(0, 20, 0)).setMinorTickSpacing(1);
        this.jsAI.setMajorTickSpacing(5);
        this.jsAI.setPaintTicks(true);
        this.jsAI.setPaintLabels(true);
        this.jsAI.setSnapToTicks(true);
        this.jsAI.setBorder(BorderFactory.createTitledBorder(connect.cp.txt(35)));
        this.jpDefault.add(this.jsAI);
        (this.jsSize = new JSlider(100, 300, 175)).setMinorTickSpacing(5);
        this.jsSize.setMajorTickSpacing(50);
        this.jsSize.setPaintTicks(true);
        this.jsSize.setPaintLabels(true);
        this.jsSize.setBorder(BorderFactory.createTitledBorder(connect.cp.txt(36)));
        this.jpDefault.add(this.jsSize);
        (this.jlInfo = new JLabel()).setFont(new Font("Arial", 1, 9));
        this.jlInfo.setForeground(Color.red);
        this.jlInfo.setText(connect.cp.txt(37));
        this.jpDefault.add(this.jlInfo);
        (this.jbOk = new JButton(connect.cp.txt(38))).addActionListener(connect.eh);
        this.jbOk.setActionCommand("SetNew");
        this.jpDefault.add(this.jbOk);
        this.setDefaultCloseOperation(2);
        this.setTitle(String.valueOf(connect.cp.txt(7)) + " - " + connect.cp.txt(39));
        this.setSize(300, 280);
        this.setResizable(false);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
    }
    
    public int getMSize() {
        return this.jsSize.getValue();
    }
    
    public int getPlayer() {
        return this.jsPlayer.getValue();
    }
    
    public int getAI() {
        return this.jsAI.getValue();
    }
}
