import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Color;

// 
// Decompiled by Procyon v0.5.36
// 

public class DDSSAPMAIN
{
    public static void main(final String[] args) {
        try {
            UIManager.put("nimbusBase", new Color(102, 102, 102));
            UIManager.put("nimbusBlueGrey", new Color(25, 25, 25));
            UIManager.put("control", new Color(76, 76, 76));
            UIManager.put("text", new Color(230, 230, 230));
            UIManager.put("nimbusLightBackground", new Color(102, 102, 102));
            UIManager.put("nimbusFocus", new Color(96, 0, 0));
            UIManager.put("nimbusAlertYellow", new Color(96, 0, 0));
            UIManager.put("nimbusSelectedText", new Color(96, 0, 0));
            UIManager.put("nimbusSelectionBackground", new Color(25, 25, 25));
            UIManager.put("info", new Color(25, 25, 25));
            UIManager.LookAndFeelInfo[] installedLookAndFeels;
            for (int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length, i = 0; i < length; ++i) {
                final UIManager.LookAndFeelInfo info = installedLookAndFeels[i];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception ex) {}
        if (args.length != 1) {
            try {
                final Process proc = Runtime.getRuntime().exec("java -jar PatchSystem.jar");
                proc.waitFor();
                System.exit(0);
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "The Programm couldn't be started!", "Error", 0);
            }
        }
        else {
            new Connecter();
        }
    }
}
