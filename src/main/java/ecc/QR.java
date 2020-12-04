package ecc;

import java.math.BigInteger;

public class QR {

    BigInteger q;
    BigInteger r;

    public QR(BigInteger q, BigInteger r){
        this.q = q;
        this.r = r;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getR() {
        return r;
    }
}
