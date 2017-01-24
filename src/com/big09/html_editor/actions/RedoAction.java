package com.big09.html_editor.actions;

import com.big09.html_editor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction
{
    private View view;

    public RedoAction(View view)
    {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        view.redo();
    }
}
