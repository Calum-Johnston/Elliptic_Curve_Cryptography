import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;
import ecc.Points.Weierstrass.ECC_Point_W_Jacob;
import ecc.Points.Weierstrass.ECC_Point_W_Proj;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args){
        Main main = new Main();
        main.generateSpeeds();
    }

    public void generateSpeeds(){
        // Weierstrass
        ECC_Curve_W curve_w = Curves.secp256r1();

        long startTime;
        long endTime;

        ECC_Point point = new ECC_Point_W_Jacob(curve_w, curve_w.getX(), curve_w.getY(), BigInteger.ONE);
        ECC_Point point2 = new ECC_Point_W_Jacob(curve_w, curve_w.getX(), curve_w.getY(), BigInteger.ONE);

        startTime = System.nanoTime();
        point.pointAddition(point2);
        endTime = System.nanoTime();
        System.out.println(startTime + " " + endTime);
        System.out.println("Weierstrass Jacobian Doubling: " + ((endTime-startTime)));

        System.gc();

        startTime = System.nanoTime();
        point.pointAddition(point2);
        endTime = System.nanoTime();
        System.out.println(startTime + " " + endTime);
        System.out.println("Weierstrass Jacobian Doubling: " + ((endTime-startTime)));

        System.gc();

        point = new ECC_Point_W_Jacob(curve_w, curve_w.getX(), curve_w.getY(), BigInteger.ONE);
        point2 = new ECC_Point_W_Jacob(curve_w, curve_w.getX(), curve_w.getY(), BigInteger.ONE);
        startTime = System.nanoTime();
        point.pointAddition(point2);
        endTime = System.nanoTime();
        System.out.println(startTime + " " + endTime);
        System.out.println("Weierstrass Jacobian Doubling: " + ((endTime-startTime)));

        System.gc();

        startTime = System.nanoTime();
        point.pointAddition(point2);
        endTime = System.nanoTime();
        System.out.println(startTime + " " + endTime);
        System.out.println("Weierstrass Jacobian Doubling: " + ((endTime-startTime)));


    }



}
