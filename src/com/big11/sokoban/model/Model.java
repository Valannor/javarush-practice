package com.big11.sokoban.model;

import com.big11.sokoban.controller.EventListener;

import java.nio.file.Paths;

public class Model
{
    public static int FIELD_SELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("..\\res\\levels.txt")); //TODO НЕМНОГО ИЗМЕНИЛ ТУТ (Было "res\levels.txt")

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects()
    {
        return gameObjects;
    }

    public void restartLevel(int level)
    {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart()
    {
        restartLevel(currentLevel);
    }

    public void startNextLevel()
    {
        currentLevel++;
        restart();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction)
    {
        for (Wall wall : gameObjects.getWalls())
            if (gameObject.isCollision(wall, direction))
                return true;

        return false;
    }

    public boolean checkBoxCollision(Direction direction)
    {
        Player player = gameObjects.getPlayer();
        for (Box box : gameObjects.getBoxes())
        {
            //Если в направлении direction находится коробка, то мы или не можем двигаться, или сдвигаем коробку.
            if (player.isCollision(box, direction))
            {
                //Если коробка стоит перед стеной или другой коробкой, то будет возвращено true (игром не сможет сдвинуться).
                if (checkWallCollision(box, direction)) return true;
                for (Box otherBox : gameObjects.getBoxes())
                {
                    if (box.isCollision(otherBox, direction))
                        return true;
                }

                switch (direction)
                {
                    case RIGHT:
                        box.move(+FIELD_SELL_SIZE, 0);
                        break;
                    case LEFT:
                        box.move(-FIELD_SELL_SIZE, 0);
                        break;
                    case UP:
                        box.move(0, -FIELD_SELL_SIZE);
                        break;
                    case DOWN:
                        box.move(0, +FIELD_SELL_SIZE);
                        break;
                }
                return false;
            }
        }

        return false;
    }

    public void checkCompletion()
    {
        boolean isCompleted = true;

        for (Home home : gameObjects.getHomes())
        {
            boolean isCoincide = false;

            for (Box box : gameObjects.getBoxes())
                if (home.getX() == box.getX() && home.getY() == box.getY())
                    isCoincide = true;

            if (!isCoincide) isCompleted = false;
        }

        if (isCompleted) eventListener.levelCompleted(currentLevel);
    }

    public void move(Direction direction)
    {
        Player player = gameObjects.getPlayer();

        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollision(direction)) return;

        switch (direction)
        {
            case RIGHT:
                player.move(+FIELD_SELL_SIZE, 0);
                break;
            case LEFT:
                player.move(-FIELD_SELL_SIZE, 0);
                break;
            case UP:
                player.move(0, -FIELD_SELL_SIZE);
                break;
            case DOWN:
                player.move(0, +FIELD_SELL_SIZE);
                break;
        }

        checkCompletion();
    }
}
