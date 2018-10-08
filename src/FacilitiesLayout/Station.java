package FacilitiesLayout;

import javafx.scene.paint.Color;
import java.util.Random;

class Station {
    private int x, y;
    double affinity;
    Color color;
    double colorTotal;

    Station() {
        initializeStation();
    }

    private void initializeStation(){
        Random rand = new Random();
        int value = rand.nextInt(256);

        colorTotal = value;
        color = color.rgb(0, value, 0);
        affinity = 0;

    }

    int getX() {
        return x;
    }
    int getY() {
        return y;
    }

    void setPosition(int x, int y) { this.x = x; this.y = y;}

    double getAffinity(){return affinity;}

    public int compareTo(Station s) {
        return Double.compare(s.getAffinity(), this.getAffinity());
    }

}
