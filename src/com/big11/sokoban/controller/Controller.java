package com.big11.sokoban.controller;

import com.big11.sokoban.model.Direction;
import com.big11.sokoban.model.GameObjects;
import com.big11.sokoban.model.Model;
import com.big11.sokoban.view.View;

public class Controller implements EventListener
{
    private View view;
    private Model model;

    public Controller()
    {
        view = new View(this);
        model = new Model();
        view.init();

        model.restart();
        model.setEventListener(this);
        view.setEventListener(this);
    }

    @Override
    public void move(Direction direction)
    {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart()
    {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel()
    {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level)
    {
        view.completed(level);
    }

    public GameObjects getGameObjects()
    {
        return model.getGameObjects();
    }

    public static void main(String[] args)
    {
        Controller controller = new Controller();
    }
}
