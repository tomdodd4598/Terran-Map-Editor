import java.awt.event.MouseWheelEvent;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.36
// 

public class EventHandler implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, ChangeListener
{
    private Connecter connect;
    private int lastDegree;
    private JFileChooser chooser;
    
    public EventHandler(final Connecter connect) {
        this.lastDegree = 0;
        this.chooser = new JFileChooser();
        this.connect = connect;
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        final String arg = arg0.getActionCommand();
        if (arg.equals("New")) {
            this.connect.dm.setNew(this.connect);
            this.connect.diplo.end();
            this.connect.settings.end();
        }
        else if (!arg.equals("Load")) {
            if (arg.equals("Save")) {
                this.chooser.setAcceptAllFileFilterUsed(false);
                this.chooser.setFileFilter(new WorldFilter(".twt", "World Files"));
                this.chooser.setCurrentDirectory(this.connect.cp.getGamePath());
                final int choosen = this.chooser.showSaveDialog(this.connect);
                switch (choosen) {
                    case 0: {
                        this.connect.cp.setSettings(this.chooser.getSelectedFile().getParent());
                        new SaveMap(this.chooser.getSelectedFile(), this.connect);
                        break;
                    }
                    case -1: {
                        this.connect.cp.display(String.valueOf(this.connect.cp.txt(5)) + " EH:FC:aP:sv", 0);
                        break;
                    }
                }
            }
            else if (arg.equals("SetNew")) {
                final int d = this.connect.dm.getNewSize() * 20;
                final int p = this.connect.dm.getPlayerCount();
                final int a = this.connect.dm.getAICount();
                for (int i = this.connect.settings.getPlayerCount(); i > 0; --i) {
                    this.connect.settings.removePlayer();
                }
                for (int i = this.connect.settings.getAICount(); i > 0; --i) {
                    this.connect.settings.removeAI();
                }
                this.connect.settings = new Settings(this.connect, p, a);
                this.connect.diplo.createDiplMenu(p, a);
                this.connect.map.createMap(d, d, true);
                this.connect.dm.disposeNew();
            }
            else if (arg.equals("jcChange")) {
                this.connect.list.createList(this.connect.lo.getType(), this.connect.lo.getNation());
            }
            else if (arg.equals("Look")) {
                if (this.connect.dm.specific) {
                    this.connect.dm.specific = false;
                }
                else {
                    this.connect.dm.specific = true;
                }
                this.connect.cp.resetGO();
            }
            else if (arg.equals("MapSettings")) {
                this.connect.diplo.end();
                this.connect.settings.setVisible(true);
                this.connect.settings.toFront();
            }
            else if (arg.equals("Diplomacy")) {
                this.connect.settings.end();
                this.connect.diplo.setVisible(true);
                this.connect.diplo.toFront();
                this.connect.settings.end();
            }
            else if (arg.equals("AddPlayer")) {
                this.connect.settings.createPlayer();
            }
            else if (arg.equals("AddAI")) {
                this.connect.settings.createAI();
            }
            else if (arg.equals("RemoveAI")) {
                this.connect.settings.removeAI();
            }
            else if (arg.equals("RemovePlayer")) {
                this.connect.settings.removePlayer();
            }
        }
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        this.connect.dummy.setPosition(e.getX(), e.getY());
        this.connect.dummy.setVisible(true);
    }
    
    @Override
    public void mouseClicked(final MouseEvent arg0) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent arg0) {
        this.connect.lo.setRot(this.lastDegree);
    }
    
    @Override
    public void mouseExited(final MouseEvent arg0) {
        this.lastDegree = this.connect.lo.getRot();
        this.connect.dummy.setVisible(false);
    }
    
    @Override
    public void mousePressed(final MouseEvent arg0) {
        if (arg0.getButton() == 1) {
            try {
                final int x = arg0.getX();
                final int y = arg0.getY();
                final GObject o = this.connect.dm.clone();
                o.setPos(x - o.getLocFixW(), y - o.getLocFixH());
                this.connect.map.addGO(o);
                this.connect.settings.getPlayer(this.connect.lo.getSelectedPlayer()).addGO(o);
                this.connect.map.repaint();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (arg0.getButton() == 2) {
            if (this.connect.lo.getMTick() == 45) {
                this.connect.lo.setMTick(10);
            }
            else {
                this.connect.lo.setMTick(45);
            }
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent arg0) {
    }
    
    @Override
    public void stateChanged(final ChangeEvent arg0) {
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent arg0) {
        final int notches = arg0.getWheelRotation();
        if (notches < 0) {
            int i = this.connect.lo.getRot() + this.connect.dm.getTicks();
            if (i > 180) {
                i -= 360;
            }
            this.connect.lo.setRot(i);
        }
        else {
            int i = this.connect.lo.getRot() - this.connect.dm.getTicks();
            if (i < -180) {
                i += 360;
            }
            this.connect.lo.setRot(i);
        }
    }
}
