package com.big11.sokoban.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable
{
    public Box(int x, int y)
    {
        super(x, y);
    }

    @Override
    public void move(int x, int y)
    {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.orange);

        //TODO Добавил крестик к коробке
        graphics.drawLine(getX(), getY(), getX() + Model.FIELD_SELL_SIZE, getY() + Model.FIELD_SELL_SIZE);
        graphics.drawLine(getX() + Model.FIELD_SELL_SIZE, getY(), getX(), getY() + Model.FIELD_SELL_SIZE);

        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}
