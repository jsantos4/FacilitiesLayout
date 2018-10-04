package FacilitiesLayout;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

class Generation implements Runnable {
    private Random random = new Random();
    private final static int total = 2;
    private Floor ground;
    private Exchanger<List<int[]>> exchanger;

    Generation(Floor floor) {
        this.ground = floor;
    }

    private void mutate() {
        final double mutationRate = 0.02;
        for (int i = 0; i < ground.getPosSwaps().size(); i++) {
            if (random.nextDouble() < mutationRate)
                ground.getPosSwaps().set(i, ground.randomStations());
        }
    }

    private void swap(Floor[] floors) {
        //int crossoverPoint = random.nextInt(ground.getRows()/ground.getColumns() );
        try {
            ground.calcTotalAffinity();
            ground.generateSwaps();
            //for (Floor floor : floors) {
            //floor.updateCrossedOverSwaps(exchanger.exchange(floor.crossover(crossoverPoint)));
            //}
            mutate();
            ground.executeSwaps();
            ground.calcTotalAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void loadFloors(Floor[] floors, Floor best) {
        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(best, i);
        }
    }

    private static Floor bestFloor(Floor[] floors) {
        int bestIndex = 0;
        for (int i = 0; i < floors.length; i++) {
            if (floors[bestIndex].getTotalAffinity() > floors[i].getTotalAffinity()) {
                bestIndex = i;
            }
        }
        return floors[bestIndex];
    }

    private static void loadGenerations(Floor[] floors, Generation[] generations) {
        for (int i = 0; i < total; i++) {
            generations[i] = new Generation(floors[i]);
        }
    }


    public void run() {

        Floor previousBest = new Floor(ground, -1);

        System.out.println("Affinity: " + ground.getTotalAffinity());

        Floor[] floors = new Floor[total];
        Generation[] generations = new Generation[total];

        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(ground, i);
        }

        loadGenerations(floors, generations);

        int genCount = 0;
        while (previousBest.getTotalAffinity() > 37000) {
        //for (int j = 0; j < 1000; j++) {
            System.out.println("Generation: " + ++genCount);
            System.out.println("Affinity: " + previousBest.getTotalAffinity());
            for (int i = 0; i < total; i++){
                generations[i].swap(floors);
            }

            Floor temp = new Floor(bestFloor(floors), -1);

            if(temp.getTotalAffinity() < previousBest.getTotalAffinity()) {
                loadFloors(floors, temp);
                loadGenerations(floors, generations);
                previousBest = new Floor(temp, -1);
            } else {
                loadFloors(floors, previousBest);
                loadGenerations(floors, generations);
            }
        }

        System.out.println("Final affinity: " + previousBest.getTotalAffinity());
    }

}