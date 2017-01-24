package com.big04.space;

/**
 * Класс для бомбы.
 */
public class Bomb extends BaseObject
{
    public Bomb(double x, double y)
    {
        super(x, y, 1);
    }

    /**
     * Отрисовываем себя на холсте.
     */
    @Override
    public void draw(Canvas canvas)
    {
        canvas.setPoint(x, y, 'B');
    }

    /**
     * Двигаем себя вниз на один ход.
     */
    @Override
    public void move()
    {
        y++;
    }
}
