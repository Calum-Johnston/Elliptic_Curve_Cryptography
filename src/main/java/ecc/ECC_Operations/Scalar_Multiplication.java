package ecc.ECC_Operations;

import ecc.ECC_Point;

import java.math.BigInteger;

public class Scalar_Multiplication {

    public static ECC_Point doubleAndAdd(ECC_Point point, BigInteger n){
        ECC_Point N = point;
        ECC_Point Q = null;
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                if(Q == null){
                    Q = N;
                }
            }
            N = N.pointDoubling();
        }
        return Q;
    }


    // wNAF method




    public static StringBuilder wNAF(int w, BigInteger k){
        StringBuilder s = new StringBuilder(k.toString(2));
        StringBuilder wNaf = new StringBuilder();
        int i = 0;
        while(k.compareTo(BigInteger.ONE) >= 0){
            if(k.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
                wNaf.insert(0, '0');
            }else{
                BigInteger val = mods(k, w);
                wNaf.insert(0, val.toString());
                k = k.subtract(val);
            }
            k = k.divide(BigInteger.TWO);
            i+=1;
        }
        return wNaf;
    }

    private static BigInteger mods(BigInteger d, int w){
        BigInteger t = BigInteger.TWO.pow(w);
        BigInteger t2 = BigInteger.TWO.pow(w-1);
        if(d.mod(t).compareTo(t2) >= 0){
            return d.mod(t).subtract(t);
        }else{
            return d.mod(t);
        }
    }

}
