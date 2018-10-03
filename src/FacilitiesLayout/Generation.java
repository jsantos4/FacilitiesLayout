package FacilitiesLayout;

import java.util.Random;

class Generation implements Runnable {
    private Random random = new Random();
    private final static int total = 2;
    Thread thread = new Thread();

    private Floor ground;

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

    private void swap() {
        try {
            ground.calcTotalAffinity();
            ground.generateSwaps();
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
        thread.start();

        Floor previousBest = new Floor(ground, -1);

        System.out.println("Affinity: " + ground.getTotalAffinity());

        Floor[] floors = new Floor[total];
        Generation[] generations = new Generation[total];

        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(ground, i);
        }

        loadGenerations(floors, generations);

        int genCount = 0;
        //while (previousBest.getTotalAffinity() > (ground.getRows() * ground.getColumns()) * 400) {
        for (int j = 0; j < 1000; j++) {
            System.out.println("Generation: " + ++genCount);
            System.out.println("Affinity: " + previousBest.getTotalAffinity());
            for (int i = 0; i < total; i++){
                generations[i].swap();
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