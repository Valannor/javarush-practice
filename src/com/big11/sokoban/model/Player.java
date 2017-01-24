package com.big11.sokoban.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable
{
    public Player(int x, int y)
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
        graphics.setColor(Color.YELLOW);
        //graphics.drawOval(getX(), getY(), getWidth(), getHeight());
        graphics.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
