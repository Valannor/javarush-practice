package com.big11.sokoban.model;

import java.util.HashSet;
import java.util.Set;

public class GameObjects
{
    private Set<Wall> walls;
    private Set<Box> boxes;
    private Set<Home> homes;
    private Player player;

    public GameObjects(Set<Wall> walls, Set<Box> boxes, Set<Home> homes, Player player)
    {
        this.walls = walls;
        this.boxes = boxes;
        this.homes = homes;
        this.player = player;
    }

    public Set<Wall> getWalls()
    {
        return walls;
    }

    public Set<Box> getBoxes()
    {
        return boxes;
    }

    public Set<Home> getHomes()
    {
        return homes;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Set<GameObject> getAll()
    {
        Set<GameObject> allObjects = new HashSet<>();
        allObjects.addAll(walls);
        allObjects.addAll(boxes);
        allObjects.addAll(homes);
        allObjects.add(player);

        return allObjects;
    }
}
