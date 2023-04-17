import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Diplomacy extends JFrame implements WindowListener
{
    private static final long serialVersionUID = 1L;
    private JCB[][] aObject;
    private Dimension d;
    private int player;
    private int ai;
    private JPanel jpBottom;
    private JPanel jpRow;
    private JPanel jpBox;
    private JCB jcbRelation;
    private JButton jbOk;
    private Connecter connect;
    
    public Diplomacy(final Connecter connect) {
        this.d = new Dimension(46, 21);
        this.connect = connect;
        (this.jpBottom = new JPanel()).setLayout(new BoxLayout(this.jpBottom, 1));
        this.add(this.jpBottom);
        this.aObject = new JCB[19][11];
        this.createDiplMenu(2, 0);
        this.setDefaultCloseOperation(1);
        this.setTitle(String.valueOf(connect.cp.txt(7)) + " - " + connect.cp.txt(19));
        this.setResizable(true);
        this.setVisible(false);
        this.addWindowListener(this);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
    }
    
    public void createDiplMenu(final int iPlayer, final int iAI) {
        this.player = iPlayer;
        this.ai = iAI;
        this.setSize((this.player + this.ai + 1) * 50 + 20, (this.ai + 1) * 26 + 100);
        this.jpBottom.removeAll();
        for (int row = 0; row <= iAI; ++row) {
            (this.jpRow = new JPanel()).setLayout(new BoxLayout(this.jpRow, 0));
            for (int column = 0; column <= iPlayer + iAI; ++column) {
                (this.jpBox = new JPanel()).setPreferredSize(this.d);
                if (row == 0 && column != 0) {
                    if (column > iPlayer) {
                        this.jpBox.add(new JLabel("AI " + (column - iPlayer)));
                    }
                    else {
                        this.jpBox.add(new JLabel("Player " + column));
                    }
                }
                else if (row != 0 && column == 0) {
                    this.jpBox.add(new JLabel("AI " + row));
                }
                else if (column - iPlayer != row && column != 0 && row != 0 && (row <= column - iPlayer || column <= iPlayer)) {
                    this.jpBox.add(this.createJCB(column, row, iPlayer));
                }
                this.jpRow.add(this.jpBox);
            }
            this.jpBottom.add(this.jpRow);
        }
        (this.jbOk = new JButton("Ok")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                Diplomacy.this.end();
            }
        });
        this.jpBottom.add(this.jbOk);
    }
    
    private JCB createJCB(final int column, final int row, final int iPlayer) {
        if (this.aObject[column][row] == null) {
            this.jcbRelation = new JCB(this.connect);
            if (column == 1) {
                this.connect.settings.getPlayer(row + iPlayer - 1).setButton(this.jcbRelation);
            }
            this.aObject[column][row] = this.jcbRelation;
        }
        else {
            this.jcbRelation = this.aObject[column][row];
        }
        return this.jcbRelation;
    }
    
    public boolean getRelat(final int c, final int r) {
        return this.aObject[c][r].isAllyed();
    }
    
    public void end() {
        if (this.isVisible()) {
            this.setVisible(false);
        }
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
