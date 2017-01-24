package com.big09.html_editor.actions;

import com.big09.html_editor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction
{
    private View view;

    public UndoAction(View view)
    {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        view.undo();
    }
}