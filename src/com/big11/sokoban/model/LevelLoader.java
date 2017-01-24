package com.big11.sokoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader
{
    private Path levels;

    public LevelLoader(Path levels)
    {
        this.levels = levels;
    }

    public GameObjects getLevel(int level)
    {
        ArrayList<String> listOfLevels = new ArrayList<>();
        try
        {
//            BufferedReader reader = new BufferedReader(new FileReader(levels.toFile()));
            BufferedReader reader = new BufferedReader(new FileReader("src/com/big11/sokoban/res/levels.txt"));

            StringBuilder builder = new StringBuilder();
            while (reader.ready())
            {
                String temp = reader.readLine();
                if (!temp.startsWith("**************"))
                {
                    if (!temp.contains(":") && !temp.isEmpty())
                        builder.append(temp).append("\r\n");
                }
                else
                {
                    if (!builder.toString().isEmpty())
                        listOfLevels.add(builder.toString());

                    builder = new StringBuilder();
                }
            }
        }
        catch (IOException e)
        {}

        //Если игрок пошел по второму кругу, мы определяем, какой уровень загрузить.
        int levelToLoadIndex = level;
        if (level > listOfLevels.size())
        {
            levelToLoadIndex = level % listOfLevels.size();
            if (levelToLoadIndex == 0) levelToLoadIndex = listOfLevels.size();
        }
        levelToLoadIndex--;

        //Создаем контейнеры для объектов
        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        //Создаем объекты и помещаем их в контейнеры
        String[] levelToLoad = listOfLevels.get(levelToLoadIndex).split("\r\n");
        int countY = 0;
        for (String line : levelToLoad)
        {
            int countX = 0;
            for (char element : line.toCharArray())
            {
                int fieldSell = Model.FIELD_SELL_SIZE;
                switch (element)
                {
                    case 'X':
                        walls.add(new Wall((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell)));
                        break;
                    case '*':
                        boxes.add(new Box((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell)));
                        break;
                    case '.':
                        homes.add(new Home((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell)));
                        break;
                    case '&':
                        boxes.add(new Box((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell)));
                        homes.add(new Home((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell)));
                        break;
                    case '@':
                        player = new Player((fieldSell/2 + countX*fieldSell), (fieldSell/2 + countY*fieldSell));
                        break;
                }
                countX++;
            }
            countY++;
        }

        return new GameObjects(walls, boxes, homes, player);
    }
}
