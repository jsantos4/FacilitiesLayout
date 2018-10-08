package FacilitiesLayout;

import java.util.Random;
import java.util.concurrent.Exchanger;

class GeneticAlgorithm implements Runnable {
    private Random random = new Random();
    private final static int total = 16;
    private Floor ground;
    private Exchanger<Floor> exchanger = new Exchanger<>();
    private Floor best;

    GeneticAlgorithm(Floor floor) {
        this.ground = floor;
    }


    Floor getBest() {
        return best;
    }

    private void mutate() {
        final double mutationRate = 0.02;
        for (int i = 0; i < ground.getPosSwaps().size(); i++) {
            if (random.nextDouble() < mutationRate)
                ground.getPosSwaps().set(i, ground.randomStations());
        }
    }

    private void swap(Floor[] floors) {
        for (Floor f : floors) {
            try {
                f.calcTotalAffinity();
                f.generateSwaps();
                mutate();
                f.executeSwaps();
                f.calcTotalAffinity();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private static void loadGenerations(Floor[] floors, GeneticAlgorithm[] generations) {
        for (int i = 0; i < total; i++) {
            generations[i] = new GeneticAlgorithm(floors[i]);
        }
    }

    public void run() {

        Floor previousBest = new Floor(ground, -1);

        System.out.println("Affinity: " + ground.getTotalAffinity());

        Floor[] floors = new Floor[total];
        GeneticAlgorithm[] generations = new GeneticAlgorithm[total];

        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(ground, i);
        }

        loadGenerations(floors, generations);

        int genCount = 1;
        for (;;) {

            best = previousBest;
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

            if ((++genCount % 100) == 0) {
                System.out.println("Generation: " + genCount);
                System.out.println("Affinity: " + previousBest.getTotalAffinity());
                try {
                    exchanger.exchange(previousBest);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}