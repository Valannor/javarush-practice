package com.big11.sokoban.model;

import java.awt.*;

public class Home extends GameObject
{
    public Home(int x, int y)
    {
        super(x, y, 2, 2);
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.RED);

        //graphics.drawOval(getX(), getY(), getWidth(), getHeight());
        graphics.drawOval(getX() + Model.FIELD_SELL_SIZE/2 - 2, getY() + Model.FIELD_SELL_SIZE/2 - 2, 4, 4);
    }
}
