package main;

import main.dynamics.*;
import main.statics.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldMap {
    private final Map<Cell, Entity> cells = new HashMap<>();

    private void add(Cell cell, Entity entity) {
        cells.put(cell, entity);
    }

    public EnumObjects getTypeObjectInCell(Cell cell) {
        return cells.containsKey(cell) ? cells.get(cell).getType() : EnumObjects.NONE;
    }

    private final int Y = 5; // height
    private final int X = 5; // width
    private final int sizeMap = Y * X;
    public int getY() { return Y; }
    public int getX() { return X; }
//    public int getSizeMap() { return sizeMap; }

//    private final float[] chancesSpawn = {0.05f, 0.10f, 0.10f, 0.10f, 0.08f}; // predators, herbivore, tree, grass, rock
    private final float[] chancesSpawn = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    private final int[] allMaxCounts = new int[5];
    private final int[] chancesIntervals = new int[4];
//    public int[] getAllMaxCounts() { return allMaxCounts; }
//    public int[] getChancesIntervals() { return chancesIntervals; }

    public boolean isEmptyCell(Cell cell) {
        return (!cells.containsKey(cell) || Objects.equals(cells.get(cell), new Herbivore())) && cell.getY() < this.Y && cell.getX() < this.X;
    }

    public void cleanCell(Cell cell) {
        cells.remove(cell);
    }

    void setEntityInCell(Cell cell, Entity entity) {
        cells.remove(cell);
        cells.put(cell, entity);
    }

    public void initStartMap() {
        fromChancesToCounts();
        createChancesIntervals();
        fillMap();
    }

    private void fromChancesToCounts() {
        for (int v = 0; v < 5; v++) {
            allMaxCounts[v] = (int)(sizeMap*chancesSpawn[v]);
        }
    }

    private void createChancesIntervals() {
        int temp = allMaxCounts[0];
        for (int x = 0; x < 4; x++) {
            chancesIntervals[x] = temp + allMaxCounts[x + 1];
            temp = chancesIntervals[x];
        }
    }
    
    private void fillMap() {
        int minIndexObjects = 0;
//        System.out.println("rand "+rand);
//        System.out.println("max count "+Arrays.toString(allMaxCounts));
//        System.out.println("inters "+Arrays.toString(chancesIntervals));

        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                int rand = minIndexObjects + (int)(Math.random() * ((sizeMap - minIndexObjects) + 1));
                if (rand < allMaxCounts[0]) {
                    add(new Cell(i, j), new Predator());
                } else if (rand > allMaxCounts[0] && rand < chancesIntervals[0]) {
                    add(new Cell(i, j), new Herbivore());
                } else if (rand > chancesIntervals[0] && rand < chancesIntervals[1]) {
                    add(new Cell(i, j), new Tree());
                } else if (rand > chancesIntervals[1] && rand < chancesIntervals[2]) {
                    add(new Cell(i, j), new Grass());
                } else if (rand > chancesIntervals[2] && rand < chancesIntervals[3]) {
                    add(new Cell(i, j), new Rock());
                }
            }
        }

        if (!cells.containsValue(new Predator()) || !cells.containsValue(new Herbivore())) {
            add(new Cell(1, 1), new Predator());
            add(new Cell(Y - 1 , X - 1), new Herbivore());

            ///////////
            add(new Cell(2, 2), new Rock());
            add(new Cell(3, 3), new Rock());
            add(new Cell(4, 3), new Rock());
            add(new Cell(2, 3), new Rock());
//            add(new Cell(1, 3), new Rock()); // dont work
        }
    }
}
