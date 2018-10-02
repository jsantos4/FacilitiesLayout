package FacilitiesLayout;

import java.util.Random;

class Generation {
    Random random = new Random();
    Floor floor;
    final double mutationRate = 0.02;

    Generation(Floor floor) {
        this.floor = floor;
    }

    void mutate() {
        for (int i = 0; i < floor.getPosSwaps().size(); i++) {
            if (random.nextDouble() < mutationRate)
                floor.getPosSwaps().set(i, floor.randomStations());
        }
    }

    void swap() {
        try {
            floor.calcTotalAffinity();
            floor.generateSwaps();
            mutate();
            floor.executeSwaps();
            floor.calcTotalAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}