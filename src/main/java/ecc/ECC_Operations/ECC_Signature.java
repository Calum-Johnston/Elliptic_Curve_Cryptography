package ecc.ECC_Operations;

import java.math.BigInteger;

public class ECC_Signature {

    BigInteger r;
    BigInteger s;

    public ECC_Signature(BigInteger r, BigInteger s){
        this.r = r;
        this.s = s;
    }

    public BigInteger getR(){
        return r;
    }

    public BigInteger getS(){
        return s;
    }

}
