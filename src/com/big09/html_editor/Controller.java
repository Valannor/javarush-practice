package com.big09.html_editor;

import com.big09.html_editor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller
{
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view)
    {
        this.view = view;
    }

    public HTMLDocument getDocument()
    {
        return document;
    }

    public void init()
    {
        createNewDocument();
    }

    public void exit()
    {
        System.exit(0);
    }

    public void resetDocument()
    {
        UndoListener undoListener = view.getUndoListener();
        if (document != null)
            document.removeUndoableEditListener(undoListener);

        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(undoListener);
        view.update();
    }

    public void setPlainText(String text)
    {
        resetDocument();
        StringReader reader = new StringReader(text);
        HTMLEditorKit kit = new HTMLEditorKit();
        try
        {
            kit.read(reader, document, 0);
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText()
    {
        StringWriter writer = new StringWriter();
        HTMLEditorKit kit = new HTMLEditorKit();
        try
        {
            kit.write(writer, document, 0, document.getLength());
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }

        return writer.toString();
    }

    public static void main(String[] args)
    {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    public void createNewDocument()
    {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument()
    {
        try
        {
            view.selectHtmlTab();
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new HTMLFileFilter());
            jFileChooser.setDialogTitle("Open File");

            int opResult = jFileChooser.showOpenDialog(view);
            if (opResult == JFileChooser.OPEN_DIALOG)
            {
                currentFile = jFileChooser.getSelectedFile();
                resetDocument();
                view.setTitle(currentFile.getName());

                try(FileReader reader = new FileReader(currentFile);)
                {
                    HTMLEditorKit kit = new HTMLEditorKit();
                    kit.read(reader, document, 0);
                }

                view.resetUndo();

            }
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void saveDocument()
    {
        try
        {
            if (currentFile == null)
                saveDocumentAs();
            else
            {
                view.selectHtmlTab();
                view.setTitle(currentFile.getName());
                try (FileWriter writer = new FileWriter(currentFile);)
                {
                    HTMLEditorKit kit = new HTMLEditorKit();
                    kit.write(writer, document, 0, document.getLength());
                }
            }
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void saveDocumentAs()
    {
        try
        {
            view.selectHtmlTab();
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new HTMLFileFilter());
            jFileChooser.setDialogTitle("Save File");

            int opResult = jFileChooser.showSaveDialog(view);
            if (opResult == JFileChooser.APPROVE_OPTION)
            {
                currentFile = jFileChooser.getSelectedFile();
                view.setTitle(currentFile.getName());

                try (FileWriter writer = new FileWriter(currentFile);)
                {
                    HTMLEditorKit kit = new HTMLEditorKit();
                    kit.write(writer, document, 0, document.getLength());
                }
            }
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }
}
