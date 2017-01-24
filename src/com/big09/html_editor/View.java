package com.big09.html_editor;

import com.big09.html_editor.listeners.FrameListener;
import com.big09.html_editor.listeners.TabbedPaneChangeListener;
import com.big09.html_editor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener
{
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public View()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController()
    {
        return controller;
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        String eventName = actionEvent.getActionCommand();

        switch (eventName)
        {
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                controller.exit();
                break;
            case "О программе":
                showAbout();
                break;
        }
    }

    public UndoListener getUndoListener()
    {
        return undoListener;
    }

    public void init()
    {
        initGui();
        FrameListener frameListener = new FrameListener(this);
        this.addWindowListener(frameListener);
        this.setVisible(true);
    }

    public void exit()
    {
        controller.exit();
    }

    public void initMenuBar()
    {
        JMenuBar jMenuBar = new JMenuBar();

        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);

        getContentPane().add(jMenuBar, BorderLayout.NORTH);
    }

    public void initEditor()
    {
        htmlTextPane.setContentType("text/html");

        JScrollPane htmlScrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML", htmlScrollPane);

        JScrollPane textScrollPane = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", textScrollPane);

        tabbedPane.setPreferredSize(new Dimension(800, 600));

        TabbedPaneChangeListener changeListener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(changeListener);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initGui()
    {
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged()
    {
        if (isHtmlTabSelected())
            controller.setPlainText(plainTextPane.getText());
        else
            plainTextPane.setText(controller.getPlainText());

        resetUndo();
    }

    public boolean canUndo()
    {
        return undoManager.canUndo();
    }

    public boolean canRedo()
    {
        return undoManager.canRedo();
    }

    public void undo()
    {
        try
        {
            undoManager.undo();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void redo()
    {
        try
        {
            undoManager.redo();
        }
        catch (Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo()
    {
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected()
    {
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectHtmlTab()
    {
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    public void update()
    {
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout()
    {
        JOptionPane.showMessageDialog(getContentPane(), "Информация", "Это учебная программа.", JOptionPane.INFORMATION_MESSAGE);
    }
}
