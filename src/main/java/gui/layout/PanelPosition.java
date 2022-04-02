package gui.layout;

public class PanelPosition {

    private int x;
    private int y;

    public PanelPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static PanelPosition parse(String position) {
        String[] pos = position.split(",");

        if (pos.length != 2) throw new IllegalArgumentException();

        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);

        return new PanelPosition(x, y);
    }
}
