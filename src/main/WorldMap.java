package main;

import main.dynamics.Creature;
import main.dynamics.Herbivore;
import main.dynamics.Predator;
import main.statics.Grass;
import main.statics.Rock;
import main.statics.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldMap {
    private final Map<Cell, Entity> cells = new HashMap<>();

    private void add(Cell cell, Entity entity) {
        cells.put(cell, entity);
    }

    public TypeEntity getTypeCell(Cell cell) {
        return cells.containsKey(cell) ? cells.get(cell).getType() : TypeEntity.NONE;
    }

    public Entity getEntityFromCell(Cell cell) {
        return cells.get(cell);
    }

    private final int Y = 10; // height
    private final int X = 10; // width
    private final int sizeMap = Y * X;

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }

    //    private final float[] chancesSpawn = {0.03f, 0.10f, 0.10f, 0.12f, 0.08f}; // predators, herbivore, tree, grass, rock
    private final float[] chancesSpawn = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    private final int[] allMaxCounts = new int[5];
    private final int[] chancesIntervals = new int[4];

    public boolean isEmptyCell(Cell cell) {
        return !cells.containsKey(cell) && cell.getY() < this.Y && cell.getX() < this.X;
    }

    public void setEntityInCell(Cell cell, Creature creature) {
//        System.out.println(creature+" SEIC "+creature.getHealthPoints());
        cells.remove(cell);
        cells.put(cell, creature);
//        System.out.println(creature+" 2SEIC2 "+creature.getHealthPoints());
    }

    public void clearCell(Cell cell) {
        cells.remove(cell);
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

    public ArrayList<Cell> getByType(TypeEntity typeEntity) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Map.Entry<Cell, Entity> entry : cells.entrySet()) {
            Entity tmp = entry.getValue();
            if (tmp.getType() == typeEntity) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private void fillMap() {
        int minIndexObjects = 0;
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                int rand = minIndexObjects + (int) (Math.random() * ((sizeMap - minIndexObjects) + 1));
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
//            add(new Cell(5, 5), new Predator());
            add(new Cell(Y - 1, X - 1), new Herbivore());
//            add(new Cell(0 , 9), new Herbivore());

//            add(new Cell(9, 3), new Grass());
            add(new Cell(4, 4), new Grass());
//
//            ///////////
//            add(new Cell(5, 5), new Rock());
//            add(new Cell(6, 5), new Rock());
//            add(new Cell(7, 5), new Rock());
//            add(new Cell(8, 5), new Rock());
//            add(new Cell(3, 3), new Rock());
//            add(new Cell(4, 3), new Rock());
//            add(new Cell(5, 6), new Rock());
//            add(new Cell(1, 2), new Rock());
//            add(new Cell(8, 8), new Rock());
//            add(new Cell(9, 8), new Rock());
////            add(new Cell(7, 8), new Rock());
//            add(new Cell(6, 8), new Rock());
        }
    }
}
