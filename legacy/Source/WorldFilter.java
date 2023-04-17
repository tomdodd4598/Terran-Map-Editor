import java.io.File;
import javax.swing.filechooser.FileFilter;

// 
// Decompiled by Procyon v0.5.36
// 

public class WorldFilter extends FileFilter
{
    private String extension;
    private String description;
    
    public WorldFilter(final String extension, final String description) {
        this.extension = extension;
        this.description = description;
    }
    
    @Override
    public boolean accept(final File arg0) {
        return arg0.isDirectory() || arg0.getName().toLowerCase().endsWith(this.extension);
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }
}
