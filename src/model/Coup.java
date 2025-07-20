import  java.awt.Point;

public class Coup {
    private int xDepart;
    private int xArrivee;
    private int yDepart;
    private int yArrivee;

    public Coup(int xDepart, int yDepart, int xArrivee, int yArrivee) {
        this.xDepart = xDepart;
        this.yDepart = yDepart;
        this.xArrivee = xArrivee;
        this.yArrivee = yArrivee;
    }

    public Point getOrigine(){
        return new Point(xDepart, yDepart);
    }
    public Point getDestination(){
        return new Point(xArrivee, yArrivee);
    }

}
