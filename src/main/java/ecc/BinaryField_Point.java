package ecc;

import java.util.List;
import java.util.Vector;

public class BinaryField_Point {

    List<Integer> x;
    List<Integer> y;

    public BinaryField_Point(List<Integer> x, List<Integer> y){
        this.x = x;
        this.y = y;
    }


    public List<Integer> getX(){
        return x;
    }

    public List<Integer> getY(){
        return y;
    }
}
