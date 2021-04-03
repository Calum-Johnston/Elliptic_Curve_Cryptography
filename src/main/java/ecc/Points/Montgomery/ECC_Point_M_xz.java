package ecc.Points.Montgomery;

import ecc.Curves.ECC_Curve_Montgomery;
import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_M_xz extends ECC_Point {

    ECC_Curve_Montgomery curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_M_xz(ECC_Curve_Montgomery curve, BigInteger x, BigInteger y, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_M_xz(ECC_Curve_Montgomery curve, BigInteger x, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_M_xz(ECC_Curve_Montgomery curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_M_xz pointDoubling(){

        // Computations required 2M+2S
        BigInteger A = add(this.x, this.z);
        BigInteger AA = square(A);
        BigInteger B = subtract(this.x, this.z);
        BigInteger BB = square(B);
        BigInteger C = subtract(AA, BB);
        BigInteger x3 = multiple(AA, BB);
        BigInteger z3 = multiple(C, add(BB, multiple(C, add(curve.getA(), BigInteger.TWO))));

        // Return the calculated value
        return new ECC_Point_M_xz(this.curve, x3, z3);
    }

    public ECC_Point_M_xz diffAddition(ECC_Point p, ECC_Point d){

        // Convert point
        ECC_Point_M_xz point = (ECC_Point_M_xz) p;
        ECC_Point_M_xz diff = (ECC_Point_M_xz) d;

        // Computations required 4M+2S
        BigInteger A = add(this.x, this.z);
        BigInteger B = subtract(this.x, this.z);
        BigInteger C = add(point.x, point.z);
        BigInteger D = subtract(point.x, point.z);
        BigInteger DA = multiple(D, A);
        BigInteger CB = multiple(C, B);
        BigInteger x4 = multiple(diff.z, square(add(DA, CB)));
        BigInteger z4 = multiple(diff.x, square(subtract(DA, CB)));

        // Return the calculated value
        return new ECC_Point_M_xz(this.curve, x4, z4);
    }

    public ECC_Point_M_xz recoverY(ECC_Point p, ECC_Point d){

        // Convert point
        ECC_Point_M_xz point = (ECC_Point_M_xz) p;
        ECC_Point_M_xz diff = (ECC_Point_M_xz) d;

        // Computations required 10M+1S+2D
        BigInteger T1 = multiple(point.x, this.z);
        BigInteger T2 = add(this.x, T1);
        BigInteger T3 = subtract(this.x, T1);
        T3 = square(T3);
        T3 = multiple(diff.x, T3);
        T1 = multiple(BigInteger.TWO, multiple(curve.getA(), this.z));
        T2 = add(T1, T2);
        BigInteger T4 = multiple(point.x, this.x);
        T4 = add(T4, this.z);
        T2 = multiple(T2, T4);
        T1 = multiple(T1, this.z);
        T2 = subtract(T2, T1);
        T2 = multiple(T2, this.z);
        BigInteger y1 = subtract(T2, T3);
        T1 = multiple(BigInteger.TWO, multiple(curve.getB(), point.y));
        T1 = multiple(T1, this.z);
        T1 = multiple(T1, diff.z);
        BigInteger x1 = multiple(T1, this.x);
        BigInteger z1 = multiple(T1, this.z);

        // Return the calculated value
        return new ECC_Point_M_xz(this.curve, x1,y1,z1);
    }


    // Getters and setters
    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }


}
