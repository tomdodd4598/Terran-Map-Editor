import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.util.Vector;
import javax.swing.JPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class CustomComboBox extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Vector<ImageIcon> images;
    private Vector<ImageIcon> images02;
    private Vector<String> strings;
    private Vector<Integer> index;
    private int alignment;
    private JComboBox jcb;
    public static final int CENTER = 0;
    public static final int MORELEFT = 1;
    public static final int LEFT = 2;
    public static final int MORERIGHT = 3;
    public static final int RIGHT = 4;
    public static final int SPREAD = 5;
    public static final int LEADING = 10;
    public static final int TRAILING = 11;
    
    public CustomComboBox(final Vector<String> text, final Vector<ImageIcon> imgs) {
        this.alignment = 4;
        this.images = imgs;
        this.strings = text;
        this.index = new Vector<Integer>();
        this.images02 = new Vector<ImageIcon>();
        for (int i = 0; i < this.strings.size(); ++i) {
            this.index.add(i);
        }
        this.jcb = new JComboBox((Vector<E>)this.index);
        final CBRendererTI renderer = new CBRendererTI();
        this.jcb.setRenderer(renderer);
        this.setLayout(new BorderLayout());
        this.add(this.jcb, "North");
        this.setPreferredSize(this.jcb.getPreferredSize());
    }
    
    public CustomComboBox(final Vector<String> text, final Vector<ImageIcon> imgs, final Vector<ImageIcon> imgs02) {
        this.alignment = 4;
        this.images = imgs;
        this.images02 = imgs02;
        this.strings = text;
        this.index = new Vector<Integer>();
        for (int i = 0; i < this.strings.size(); ++i) {
            this.index.add(i);
        }
        this.jcb = new JComboBox((Vector<E>)this.index);
        final CBRendererTII renderer = new CBRendererTII();
        this.jcb.setRenderer(renderer);
        this.setLayout(new BorderLayout());
        this.add(this.jcb, "North");
        this.setPreferredSize(this.jcb.getPreferredSize());
    }
    
    public void addActionListener(final ActionListener arg0) {
        this.jcb.addActionListener(arg0);
    }
    
    public void setActionCommand(final String arg0) {
        this.jcb.setActionCommand(arg0);
    }
    
    public void setMaximumRows(final int rows) {
        this.jcb.setMaximumRowCount(rows);
    }
    
    public void setTextPosition(final int a) {
        this.alignment = a;
    }
    
    public String getText() {
        return this.strings.get((int)this.jcb.getSelectedItem());
    }
    
    public ImageIcon getImg() {
        return this.images.get((int)this.jcb.getSelectedItem());
    }
    
    public void removeItem(final int i) {
        this.resetIndex(i, -1);
        this.jcb.removeItemAt(i);
        this.strings.remove(i);
        this.images.remove(i);
        this.images02.remove(i);
    }
    
    public void addItem(final String string, final ImageIcon img, final ImageIcon img02, final int i) {
        if (this.jcb.getItemCount() <= i) {
            this.strings.add(string);
            this.images.add(img);
            this.images02.add(img02);
            this.jcb.addItem(i);
        }
        else {
            this.strings.add(i, string);
            this.images.add(i, img);
            this.images02.add(i, img02);
            this.resetIndex(i, 1);
            this.jcb.insertItemAt(i, i);
        }
    }
    
    public int getSelectedIndex() {
        return this.jcb.getSelectedIndex();
    }
    
    private void resetIndex(final int c, final int by) {
        for (int i = c, j = c + by; i < this.index.size(); ++i, ++j) {
            this.index.set(i, j);
        }
    }
    
    class CBRendererTI extends JLabel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        
        public CBRendererTI() {
            this.setOpaque(true);
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }
            this.setHorizontalAlignment(10);
            this.setHorizontalTextPosition(CustomComboBox.this.alignment);
            final int i = (int)value;
            this.setIcon(CustomComboBox.this.images.get(i));
            this.setText(CustomComboBox.this.strings.get(i));
            return this;
        }
    }
    
    class CBRendererTII extends JPanel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        JLabel jl00;
        JLabel jl01;
        JLabel jl02;
        
        public CBRendererTII() {
            this.setOpaque(true);
            this.jl00 = new JLabel();
            this.jl01 = new JLabel();
            this.jl02 = new JLabel();
            this.setLayout(new BoxLayout(this, 0));
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }
            final int i = (int)value;
            this.jl00.setText(CustomComboBox.this.strings.get(i));
            this.jl01.setIcon(CustomComboBox.this.images.get(i));
            this.jl02.setIcon(CustomComboBox.this.images02.get(i));
            if (CustomComboBox.this.alignment == 2) {
                this.add(this.jl00);
                this.add(this.jl01);
                this.add(this.jl02);
            }
            else if (CustomComboBox.this.alignment == 4) {
                this.add(this.jl01);
                this.add(this.jl02);
                this.add(this.jl00);
            }
            else if (CustomComboBox.this.alignment == 0) {
                this.add(this.jl01);
                this.add(this.jl00);
                this.add(this.jl02);
            }
            return this;
        }
    }
}
