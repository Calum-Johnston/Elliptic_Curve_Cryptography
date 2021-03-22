package ecc.Doche_Icart_Kohel;

import ecc.Curves.ECC_Curve_DIK2;
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_DIK2 extends ECC_Point {

    ECC_Curve_DIK2 curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger zz;

    // Constructors
    public ECC_Point_DIK2(ECC_Curve_DIK2 curve, BigInteger x, BigInteger y, BigInteger z, BigInteger zz){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.zz = zz;
    }

    public ECC_Point_DIK2(ECC_Curve_DIK2 curve){
        super(true, null);
        this.curve = curve;
    }


    // Point Operations
    public ECC_Point_DIK2 pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_DIK2 point = (ECC_Point_DIK2) p;

        // Perform required checks
        if(isInfinity() == true){ // P == O
            return point;
        } else if(point.isInfinity() == true){ // Q == O
            return this;
        }

        // Calculations 12M+5S+1D
        BigInteger A = subtract(multiple(this.y, point.zz), multiple(point.y, this.zz));
        BigInteger AA = square(A);
        BigInteger x2z1 = multiple(point.x, this.z);
        BigInteger B = subtract(multiple(this.x, point.zz), x2z1);
        BigInteger C = multiple(B, point.z);
        BigInteger E = multiple(C, this.z);
        BigInteger EE = square(E);
        BigInteger F = multiple(E, C);
        BigInteger D = multiple(F, this.x);
        BigInteger U = subtract(subtract(subtract(AA, multiple(curve.getA(), EE)), D), multiple(multiple(x2z1, E), B));
        BigInteger x3 = multiple(BigInteger.TWO, U);
        BigInteger y3 = multiple(BigInteger.TWO, subtract(multiple(subtract(subtract(square(add(E, A)), EE), AA), subtract(D, U)), multiple(this.y, square(multiple(BigInteger.TWO, F)))));
        BigInteger z3 = multiple(BigInteger.TWO, EE);
        BigInteger zz3 = square(z3);

        // Return the calculated value
        return new ECC_Point_DIK2(this.curve, x3, y3, z3, zz3);
    }

    public ECC_Point_DIK2 pointDoubling(){

        // Perform required checks
        if(isInfinity() == true) { // P = O
            return this;
        }

        // Calculations 2M+5S
        BigInteger A = square(this.x);
        BigInteger U = multiple(multiple(BigInteger.TWO, curve.getA()), this.zz);
        BigInteger B = subtract(A, multiple(new BigInteger("8", 10), U));
        BigInteger C = multiple(A, U);
        BigInteger YY = square(this.y);
        BigInteger YY2 = multiple(BigInteger.TWO, YY);
        BigInteger z3 = multiple(BigInteger.TWO, YY2);
        BigInteger x3 = square(B);
        BigInteger V = subtract(subtract(square(add(this.y, B)), YY), x3);
        BigInteger y3 = multiple(V, add(x3, add(multiple(new BigInteger("64"), C), multiple(curve.getA(), subtract(YY2, C)))));
        BigInteger zz3 = square(z3);

        // Return the calculated value
        return new ECC_Point_DIK2(this.curve, x3, y3, z3, zz3);
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
