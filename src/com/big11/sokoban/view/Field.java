package com.big11.sokoban.view;

import com.big11.sokoban.controller.EventListener;
import com.big11.sokoban.model.Direction;
import com.big11.sokoban.model.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Field extends JPanel
{
    private View view;
    private EventListener eventListener;

    public class KeyHandler extends KeyAdapter
    {
        private Field field;

        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    eventListener.move(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    eventListener.move(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    eventListener.move(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    eventListener.move(Direction.DOWN);
                    break;
                case KeyEvent.VK_R:
                    eventListener.restart();
                    break;
            }
        }
    }

    public Field(View view)
    {
        this.view = view;

        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 700); //TODO НЕМНОГО ИЗМЕНИЛ ТУТ (Было 500, 500)
        for (GameObject gameObject : view.getGameObjects().getAll())
        {
            gameObject.draw(g);
        }
    }

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }
}
