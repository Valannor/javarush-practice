package com.big11.sokoban.controller;

import com.big11.sokoban.model.Direction;

public interface EventListener
{
    void move(Direction direction);

    void restart();

    void startNextLevel();

    void levelCompleted(int level);
}
