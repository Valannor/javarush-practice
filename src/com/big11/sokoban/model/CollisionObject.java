package com.big11.sokoban.model;

public abstract class CollisionObject extends GameObject
{
    public CollisionObject(int x, int y)
    {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction)
    {
        int objX = gameObject.getX();
        int objY = gameObject.getY();

        int thisObjX = getX();
        int thisObjY = getY();

        switch (direction)
        {
            case LEFT:
                thisObjX -= Model.FIELD_SELL_SIZE;
                break;
            case RIGHT:
                thisObjX += Model.FIELD_SELL_SIZE;
                break;
            case UP:
                thisObjY -= Model.FIELD_SELL_SIZE;
                break;
            case DOWN:
                thisObjY += Model.FIELD_SELL_SIZE;
                break;
        }

        return objX == thisObjX && objY == thisObjY;
    }
}
