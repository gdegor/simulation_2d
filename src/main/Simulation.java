package main;

import main.dynamics.Creature;
import main.dynamics.Herbivore;
import main.dynamics.Predator;
public class Simulation {
    public static void main(String[] args) {
        WorldMap map = new WorldMap();
        map.initStartMap();
        RenderPicture.drawMap(map);
        System.out.println("______________________");
        try {
            Thread.sleep(2200);
        } catch (InterruptedException ex) {
        }

        while (!map.getByType(TypeEntity.HERBIVORE).isEmpty()) {

            for (Cell location : map.getByType(TypeEntity.PREDATOR)) {
                Predator pr = (Predator) map.getEntityFromCell(location);
                pr.makeMove(location, map);
            }
            for (Cell location : map.getByType(TypeEntity.HERBIVORE)) {
                Creature pr = (Herbivore) map.getEntityFromCell(location);
                pr.makeMove(location, map);
            }

            RenderPicture.drawMap(map);
            try {
                Thread.sleep(2200);
            } catch (InterruptedException ex) {
            }
            System.out.println("\033[H\033[2J");
        }
        System.out.println("Herbivores died.");
    }
}