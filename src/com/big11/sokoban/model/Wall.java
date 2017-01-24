package com.big11.sokoban.model;

import java.awt.*;

public class Wall extends CollisionObject
{
    public Wall(int x, int y)
    {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(new Color(220, 143, 64, 210));
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());

        //TODO Добавил изображение блоков
        graphics.setColor(Color.BLACK);
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}
