package com.big09.html_editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        return (f.isDirectory() ||
                f.getName().toLowerCase().endsWith(".html") ||
                f.getName().toLowerCase().endsWith(".htm"));
    }

    @Override
    public String getDescription()
    {
        return "HTML и HTM файлы";
    }
}
