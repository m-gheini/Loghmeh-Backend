package IECA.logic;


public class Location {
    private int x;
    private int y;

    public void setX(int _x){x = _x;}
    public void setY(int _y){y = _y;}
    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "location : { \"x\" : " + x + ",\"y\" : " + y + "}";
    }
}
