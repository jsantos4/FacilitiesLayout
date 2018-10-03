package FacilitiesLayout;

public class Main {
    private static Floor floor = new Floor(10, 10);
    private static Generation generation = new Generation(floor);
    private static Generation generation1 = new Generation(floor);

    public static void main(String args[]) {
        generation.run();
    }
}
