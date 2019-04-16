package map;

public class Color {

    private int r,g,b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public int hashCode(){
        return r*g*b;
    }

    @Override
    public boolean equals(Object o){
        Color other = (Color)o;
        int otherR = ((Color) o).getR();
        int otherB =  ((Color) o).getB();
        int otherG = ((Color) o).getG();
        if(otherR ==r && otherB == b && otherG == g){
            return true;
        }else{
            return false;
        }

    }
}
