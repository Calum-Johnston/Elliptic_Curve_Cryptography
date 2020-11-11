import java.math.BigInteger;

public class ECC_Point {

    BigInteger x;
    BigInteger y;

    public ECC_Point(BigInteger x, BigInteger y){
        this.x = x;
        this.y = y;
    }

    public BigInteger getX(){
        return x;
    }

    public BigInteger getY(){
        return y;
    }

    public ECC_Point pointMultiplication(BigInteger n, BigInteger p, BigInteger a){
        ECC_Point N = this;
        ECC_Point Q = new ECC_Point(null, null);
        boolean infinity = true;
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                if(infinity == true){
                    infinity = false;
                    Q = N;
                }else {
                    Q = pointAddition(Q.x, Q.y, N.x, N.y, p);
                }
            }
            N = pointDoubling(N.x, N.y, a, p);
        }
        return Q;
    }

    private ECC_Point pointAddition(BigInteger x1, BigInteger y1, BigInteger x2, BigInteger y2, BigInteger p){
        BigInteger gradientNumerator = y2.subtract(y1);
        BigInteger gradientDenominator = (x2.subtract(x1)).modInverse(p);
        BigInteger gradient = gradientNumerator.multiply(gradientDenominator).mod(p);
        BigInteger x3 = (gradient.pow(2).subtract(x1).subtract(x2)).mod(p);
        BigInteger y3 = (((x1.subtract(x3)).multiply(gradient)).subtract(y1)).mod(p);
        return new ECC_Point(x3, y3);
    }

    private ECC_Point pointDoubling(BigInteger x1, BigInteger y1, BigInteger a, BigInteger p){
        BigInteger gradientNumerator = ((x1.pow(2)).multiply(new BigInteger("3")).add(a));
        BigInteger gradientDenominator = y1.multiply(new BigInteger("2")).modInverse(p);
        BigInteger gradient = gradientNumerator.multiply(gradientDenominator).mod(p);
        BigInteger x3 = (gradient.pow(2).subtract(x1).subtract(x1)).mod(p);
        BigInteger y3 = (((x1.subtract(x3)).multiply(gradient)).subtract(y1)).mod(p);
        return new ECC_Point(x3, y3);
    }





}
