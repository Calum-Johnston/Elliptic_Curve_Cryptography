package ecc.ECC_Operations;

import ecc.Points.ECC_Point;

import java.math.BigInteger;
import java.util.ArrayList;

public class Scalar_Multiplication {

    // RtoL binary method
    public static ECC_Point doubleAndAdd(ECC_Point point, BigInteger n){
        ECC_Point N = point;
        ECC_Point Q = null;
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                if(Q == null){
                    Q = N;
                }else{
                    Q = Q.pointAddition(N);
                }
            }
            N = N.pointDoubling();
        }
        return Q;
    }


    // NAF method
    private static ArrayList<Integer> NAF(BigInteger k){
        ArrayList<Integer> NAF = new ArrayList<>();
        BigInteger four = new BigInteger("4");
        while(k.compareTo(BigInteger.ONE) >= 0){
            if(k.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
                NAF.add(0, 0);
            }else{
                BigInteger val = BigInteger.TWO.subtract(k.mod(four));
                NAF.add(0, val.intValue());
                k = k.subtract(val);
            }
            k = k.divide(BigInteger.TWO);
        }
        return NAF;
    }

    public static ECC_Point binaryNAF(ECC_Point point, BigInteger n){
        ArrayList<Integer> NAF = NAF(n);
        ECC_Point P = point;
        ECC_Point nP = point.negate();
        ECC_Point P_ = P.convertAffine();
        ECC_Point nP_ = nP.convertAffine();
        ECC_Point Q = null;
        for(Integer val : NAF){
            if(Q != null){
                Q = Q.pointDoubling();
            }
            if(val == -1){
                if(Q == null){
                    Q = P;
                }else{
                    Q = Q.mixedAddition(nP_);
                }
            }
            if(val == 1){
                if(Q == null){
                    Q = P;
                }else{
                    Q = Q.mixedAddition(P_);
                }
            }
        }
        return Q;
    }


    // wNAF method
    private static ArrayList<Integer> wNAF(int w, BigInteger k){
        ArrayList<Integer> wNAF = new ArrayList<>();
        while(k.compareTo(BigInteger.ONE) >= 0){
            if(k.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
                wNAF.add(0, 0);
            }else{
                BigInteger val = mods(k, w);
                wNAF.add(0, val.intValue());
                k = k.subtract(val);
            }
            k = k.divide(BigInteger.TWO);
        }
        return wNAF;
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

    public static ECC_Point windowNAF(ECC_Point point, BigInteger k, int w){
        ArrayList<Integer> wNAF = wNAF(w, k);
        ECC_Point Q = null;
        ArrayList<ECC_Point> P = new ArrayList<>();
        ArrayList<ECC_Point> nP = new ArrayList<>();
        for(int i = 1; i < Math.pow(2, w-1); i+=2){
            ECC_Point po = doubleAndAdd(point, BigInteger.valueOf(i));
            P.add(po);
            nP.add(po.negate());
        }
        for(Integer val : wNAF){
            if(Q != null){
                Q = Q.pointDoubling();
            }
            if(val > 0){
                if(Q == null){
                    Q = P.get((int) Math.floor(val / 2));
                }else{
                    Q = Q.pointAddition(P.get((int) Math.floor(val / 2)));
                }
            }
            if(val < 0){
                if(Q == null){
                    Q = nP.get((int) Math.floor(val / 2));
                }else{
                    Q = Q.pointAddition(nP.get((int) Math.floor(Math.abs(val) / 2)));
                }
            }
        }
        return Q;
    }


    // Sliding Window
    public static ECC_Point slidingWindow(ECC_Point point, BigInteger k, int w){
        ArrayList<Integer> NAF = NAF(k);
        ECC_Point Q = null;
        ArrayList<ECC_Point> P = new ArrayList<>();
        ArrayList<ECC_Point> nP = new ArrayList<>();
        int endPoint = (int) (2 * (Math.pow(2, w) - Math.pow(-1, w)))/3;
        for(int i = 1; i <= endPoint; i+=2){
            ECC_Point po = doubleAndAdd(point, BigInteger.valueOf(i));
            P.add(po);
            nP.add(po.negate());
        }
        int i = 0;
        int t = 0;
        int u = 0;
        while(i < NAF.size()){
            int val = NAF.get(i);
            if(val == 0){
                t = 1;
                u = 0;
            }else{
                int startPoint = w - 1;
                if(i + w - 1 >= NAF.size()){
                    startPoint = NAF.size() - 1 - i;
                }
                for(int j = startPoint; j >= 0; j--){
                    if(NAF.get(i + j) != 0){ //means its odd
                        u = 0;
                        int p = 0;
                        for(int v = j; v >=0; v--){
                            u += (Math.pow(2, p) * NAF.get(i+v));
                            p++;
                        }
                        t = j + 1;
                        break;
                    }
                }
            }
            if(Q != null){
                for(int a = 0; a < t; a++){
                    Q = Q.pointDoubling();
                }
            }
            if(u > 0){
                if(Q == null){
                    Q = P.get((int) Math.floor(u / 2));
                }else{
                    Q = Q.pointAddition(P.get((int) Math.floor(u / 2)));
                }
            }
            if(u < 0){
                if(Q == null){
                    Q = nP.get((int) Math.floor(Math.abs(u) / 2));
                }else{
                    Q = Q.pointAddition(nP.get((int) Math.floor(Math.abs(u) / 2)));
                }
            }
            i += t;
        }
        return Q;
    }



    // NAF method (unified)
    public static ECC_Point binaryNAF_Uni(ECC_Point point, BigInteger n){
        ArrayList<Integer> NAF = NAF(n);
        ECC_Point P = point;
        ECC_Point nP = point.negate();
        ECC_Point Q = null;
        for(Integer val : NAF){
            if(Q != null){
                Q = Q.unifiedAddition(Q);
            }
            if(val == -1){
                if(Q == null){
                    Q = P;
                }else{
                    Q = Q.unifiedAddition(nP);
                }
            }
            if(val == 1){
                if(Q == null){
                    Q = P;
                }else{
                    Q = Q.unifiedAddition(P);
                }
            }
        }
        return Q;
    }


}
