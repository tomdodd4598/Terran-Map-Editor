import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.FileReader;
import java.io.File;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Icon;
import java.awt.Dimension;
import java.util.ArrayList;
import java.io.BufferedReader;

// 
// Decompiled by Procyon v0.5.36
// 

public class CommandPackage
{
    private BufferedReader brS;
    private ArrayList<String> alData;
    private String gameLanguage;
    private String language;
    private String gamePath;
    private String version;
    private String contentVersion;
    private Connecter connect;
    
    public CommandPackage(final Connecter connect) {
        this.gameLanguage = "English";
        this.language = "Data/English";
        this.gamePath = "";
        this.version = "1.0";
        this.contentVersion = "1.0";
        this.connect = connect;
        this.getSettings();
        this.getTexts();
    }
    
    public JButton createButton(final String txt, final String com, final Dimension size, final Icon icon) {
        final JButton jbDefault = new JButton();
        jbDefault.setText(txt);
        jbDefault.addActionListener(this.connect.eh);
        jbDefault.setActionCommand(com);
        if (icon == null) {
            if (size != null) {
                jbDefault.setSize(size);
            }
        }
        else {
            jbDefault.setSize(icon.getIconWidth(), icon.getIconHeight());
        }
        return jbDefault;
    }
    
    public void display(final String txt, final int type) {
        String title = null;
        switch (type) {
            case 0: {
                title = this.txt(0);
                break;
            }
            case 1: {
                title = this.txt(1);
                break;
            }
            case 2: {
                title = this.txt(2);
                break;
            }
            default: {
                title = this.txt(3);
                break;
            }
        }
        JOptionPane.showMessageDialog(this.connect, txt, title, type);
    }
    
    public String askFor(final String quest) {
        return JOptionPane.showInputDialog(this.connect, quest, "");
    }
    
    public Boolean confirm(final String quest) {
        if (JOptionPane.showConfirmDialog(this.connect, quest, this.txt(4), 0) == 0) {
            return true;
        }
        return false;
    }
    
    public int confirmExtended(final String quest) {
        return JOptionPane.showConfirmDialog(this.connect, quest, this.txt(4), 1);
    }
    
    public String translate(final String string) {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(new File("Strings/" + this.gameLanguage + "_GameStrings.txt")));
            String txtline;
            while ((txtline = br.readLine()) != null) {
                if (txtline.equals(string)) {
                    txtline = br.readLine();
                    br.close();
                    return txtline;
                }
            }
            br.close();
            return string;
        }
        catch (Exception e) {
            e.printStackTrace();
            return string;
        }
    }
    
    public void getTexts() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(new File(this.language)));
            this.alData = new ArrayList<String>();
            String txtline;
            while ((txtline = br.readLine()) != null) {
                this.alData.add(txtline);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void openStream(final File file) {
        try {
            this.brS = new BufferedReader(new FileReader(file));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String read() {
        try {
            return this.brS.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int countObjects() {
        final int i1 = this.connect.settings.getPlayerCount() + this.connect.settings.getAICount();
        int size = 0;
        for (int i2 = 0; i2 < i1; ++i2) {
            size += this.connect.settings.getPlayer(i2).getSize();
        }
        return size;
    }
    
    public GObject getGO(final int c) {
        int size = 0;
        int csize = 0;
        for (int i = 0; i < this.connect.settings.getPlayerCount() + this.connect.settings.getAICount(); ++i) {
            csize = this.connect.settings.getPlayer(i).getSize();
            size += csize;
            if (c < size) {
                return this.connect.settings.getPlayer(i).getGO(csize - size + c);
            }
        }
        this.display(String.valueOf(this.txt(6)) + "\n" + this.txt(5) + ":" + "CP:GO:" + c + ":" + size, 0);
        return null;
    }
    
    public void resetGO() {
        for (int i = 0; i < this.connect.settings.getPlayerCount() + this.connect.settings.getAICount(); ++i) {
            this.connect.settings.getPlayer(i).startPos();
            for (int size = this.connect.settings.getPlayer(i).getSize(), j = 0; j < size; ++j) {
                this.connect.settings.getPlayer(i).getGO(j).changePic();
                this.connect.settings.getPlayer(i).getGO(j).dummyPos();
                this.connect.settings.getPlayer(i).getGO(j).repaint();
            }
        }
    }
    
    public void getSettings() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(new File("Data/Settings.set")));
            String txtline;
            while ((txtline = br.readLine()) != null) {
                if (txtline.startsWith("Language ")) {
                    this.language = txtline.substring(9);
                }
                if (txtline.startsWith("GameLanguage ")) {
                    this.gameLanguage = txtline.substring(13);
                }
                if (txtline.startsWith("GamePath ")) {
                    this.gamePath = txtline.substring(9);
                }
                if (txtline.startsWith("Version ")) {
                    this.version = txtline.substring(8);
                }
                if (txtline.startsWith("ContentVersion ")) {
                    this.contentVersion = txtline.substring(15);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setSettings(final String gamePath) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Data/Settings.set")));
            bw.write("Language " + this.language);
            bw.newLine();
            bw.write("GameLanguage " + this.gameLanguage);
            bw.newLine();
            bw.write("GamePath " + gamePath);
            bw.newLine();
            bw.write("Version " + this.version);
            bw.newLine();
            bw.write("ContentVersion " + this.contentVersion);
            bw.newLine();
            this.gamePath = gamePath;
            bw.close();
        }
        catch (Exception ex) {}
    }
    
    public File getGamePath() {
        return new File(this.gamePath);
    }
    
    public String txt(final int i) {
        return this.alData.get(i);
    }
}
