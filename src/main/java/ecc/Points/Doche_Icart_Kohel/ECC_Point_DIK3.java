package ecc.Points.Doche_Icart_Kohel;

import ecc.Curves.ECC_Curve_DIK3;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_DIK3 extends ECC_Point {

    ECC_Curve_DIK3 curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger zz;

    // Constructors
    public ECC_Point_DIK3(ECC_Curve_DIK3 curve, BigInteger x, BigInteger y, BigInteger z, BigInteger zz){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.zz = zz;
    }

    public ECC_Point_DIK3(ECC_Curve_DIK3 curve){
        super(true, null);
        this.curve = curve;
    }


    // Point Operations
    public ECC_Point_DIK3 pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_DIK3 point = (ECC_Point_DIK3) p;

        // Calculations 12M+5S+1D
        BigInteger zzz2 = multiple(point.z, point.zz);
        BigInteger zzz1 = multiple(this.z, this.zz);
        BigInteger A = subtract(multiple(point.x, this.zz), multiple(this.x, point.zz));
        BigInteger B = subtract(multiple(point.y, zzz1), multiple(zzz2, this.y));
        BigInteger AA = square(A);
        BigInteger C = subtract(subtract(square(add(point.z, A)), point.zz), AA);
        BigInteger CC = square(C);
        BigInteger CC8 = multiple(new BigInteger("8"), CC);
        BigInteger D = multiple(this.x, CC8);
        BigInteger z3 = subtract(subtract(square(add(this.z, C)), zz), CC);
        BigInteger E = square(z3);
        BigInteger x3 = subtract(subtract(multiple(new BigInteger("16"), subtract(square(B), multiple(A, AA))), D), multiple(E, multiple(new BigInteger("3"), curve.getA())));
        BigInteger y3 = subtract(multiple(BigInteger.TWO, multiple(B, subtract(D, multiple(BigInteger.TWO, x3)))), multiple(this.y, multiple(C, CC8)));
        BigInteger zz3 = E;

        // Return the calculated value
        return new ECC_Point_DIK3(this.curve, x3, y3, z3, zz3);
    }

    public ECC_Point_DIK3 pointDoubling(){

        // Calculations 2M+5S
        BigInteger A = square(this.x);
        BigInteger B = multiple(BigInteger.TWO, multiple(curve.getA(), multiple(this.zz, add(this.x, this.zz))));
        BigInteger C = multiple(new BigInteger("3"), add(A, B));
        BigInteger D = square(this.y);
        BigInteger E = square(D);
        BigInteger z3 = subtract(subtract(square(add(this.y, this.z)), D), this.zz);
        BigInteger zz3 = square(z3);
        BigInteger F = multiple(BigInteger.TWO, subtract(subtract(square(add(this.x, D)), A), E));
        BigInteger x3 = subtract(subtract(square(C), multiple(multiple(new BigInteger("3"), curve.getA()), zz3)), multiple(BigInteger.TWO, F));
        BigInteger y3 = subtract(multiple(C, subtract(F, x3)), multiple(new BigInteger("8"), E));

        // Return the calculated value
        return new ECC_Point_DIK3(this.curve, x3, y3, z3, zz3);
    }

    public ECC_Point_DIK3 mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_DIK3 point = (ECC_Point_DIK3) p;

        // Computations required 8M+4S+1D
        BigInteger A = multiple(point.x, this.zz);
        BigInteger B = multiple(point.y, multiple(this.zz, this.z));
        BigInteger C = subtract(this.x, A);
        BigInteger D = multiple(BigInteger.TWO, subtract(this.y, B));
        BigInteger F = square(C);
        BigInteger F4 = multiple(new BigInteger("4"), F);
        BigInteger z3 = subtract(subtract(square(add(this.z, C)), this.zz), F);
        BigInteger E = square(z3);
        BigInteger G = multiple(C, F4);
        BigInteger H = multiple(A, F4);
        BigInteger x3 = subtract(subtract(subtract(square(D), G), multiple(BigInteger.TWO, H)), multiple(new BigInteger("3"), multiple(curve.getA(), E)));
        BigInteger y3 = subtract(multiple(D, subtract(H, x3)), multiple(BigInteger.TWO, multiple(B, G)));
        BigInteger zz3 = E;

        // Return the calculated value
        return new ECC_Point_DIK3(this.curve, x3, y3, z3, zz3);
    }

    public ECC_Point_DIK3 reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_DIK3 negate(){
        return new ECC_Point_DIK3(this.curve, this.x, this.y.negate(), this.z, this.zz);
    }

    public ECC_Point_DIK3 convertAffine(){
        BigInteger z1 = inverse(this.z);
        BigInteger x1 = multiple(square(z1), this.x);
        BigInteger y1 = multiple(multiple(z1, square(z1)), this.y);
        return new ECC_Point_DIK3(curve, x1, y1, BigInteger.ONE, BigInteger.ONE);
    }



    // Getters and setters
    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getY(){
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

}
