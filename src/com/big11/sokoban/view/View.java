package com.big11.sokoban.view;

import com.big11.sokoban.controller.Controller;
import com.big11.sokoban.controller.EventListener;
import com.big11.sokoban.model.GameObjects;

import javax.swing.*;

public class View extends JFrame
{
    private Controller controller;
    private Field field;

    public View(Controller controller)
    {
        this.controller = controller;
    }

    public void init()
    {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListener)
    {
        field.setEventListener(eventListener);
    }

    public void update()
    {
        field.repaint();
    }

    public GameObjects getGameObjects()
    {
        return controller.getGameObjects();
    }

    public void completed(int level)
    {
        update();
        JOptionPane.showMessageDialog(this, null, "Завершен уровень " + level, 0);
        controller.startNextLevel();
    }
}
