package ecc.ECC_Operations;

import ecc.Points.ECC_Point;
import ecc.Points.Montgomery.ECC_Point_M_xz;
import ecc.Points.Weierstrass.ECC_Point_W_xz;

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
    public static ArrayList<Integer> wNAF(BigInteger k, int w){
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
        ArrayList<Integer> wNAF = wNAF(k, w);
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

    // Sliding window method
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

    // Fractional wNAF method
    private static ArrayList<Integer> fractwNAF(BigInteger k, int w0, double w1){
        ArrayList<Integer> fractwNAF = new ArrayList<>();
        while(k.compareTo(BigInteger.ONE) >= 0) {
            if(k.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0){
                fractwNAF.add(0, 0);
            }else{
                BigInteger val = mods(k,  w0+1);
                double comp = (((1+w1)* Math.pow(2, w0-1)) -1);
                BigInteger add;
                if(val.abs().compareTo(BigInteger.valueOf((long) comp)) == 1){
                    add = mods(k, w0);
                }else{
                    add = val;
                }
                fractwNAF.add(0, add.intValue());
                k = k.subtract(add);
            }
            k = k.divide(BigInteger.TWO);
        }
        return fractwNAF;
    }

    public static ECC_Point fractional_wNAF(ECC_Point point, BigInteger k, int w0, double w1){
        ArrayList<Integer> wNAF = fractwNAF(k, w0, w1);
        ECC_Point Q = null;
        ArrayList<ECC_Point> P = new ArrayList<>();
        ArrayList<ECC_Point> nP = new ArrayList<>();
        double endPoint = Math.pow(2, w0-1) + (Math.pow(2, w0-1) * w1);
        for(int i = 1; i < endPoint; i+=2){
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

    // NOF method
    private static ArrayList<Integer> NOF(BigInteger k){
        ArrayList<Integer> NOF = new ArrayList<>();
        String binary = k.toString(2);
        NOF.add(Integer.parseInt(binary.substring(0, 1)));
        for(int i = 0; i < binary.length() - 1; i++){
            int d = Integer.parseInt(binary.substring(i+1, i+2));
            int di = Integer.parseInt(binary.substring(i, i+1));
            NOF.add(d - di);
        }
        NOF.add(-Integer.parseInt(binary.substring(binary.length() - 1)));
        return NOF;
    }

    public static ECC_Point binaryNOF(ECC_Point point, BigInteger n){
        ArrayList<Integer> NAF = NOF(n);
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

    // wNOF method
    public static ArrayList<Integer> wNOF(BigInteger k, int w){
        ArrayList<Integer> NOF = NOF(k);
        ArrayList<Integer> wNOF = new ArrayList<Integer>();
        int i = 0;
        while(i <= NOF.size() - w){ //change
            if(NOF.get(i) == NOF.get(i+1)){
                wNOF.add(0);
                i += 1;
            }else {
                while (NOF.get(i) == 0) {
                    wNOF.add(0);
                    i+=1;
                }
                int pos = w - 1;
                if(i + pos >= NOF.size()){
                    break;
                }
                while (NOF.get(i + pos) == 0) {
                    pos -= 1;
                }
                int val = 0;
                int q = 0;
                for (int l = pos; l >= 0; l--){
                    val += Math.pow(2, q) * NOF.get(i+l);
                    q+= 1;
                }
                for(int p = 0; p < w; p++){
                    if(p == pos){
                        wNOF.add(val);
                    }else{
                        wNOF.add(0);
                    }
                }
                i += w;
            }
        }
        if(i <= NOF.size() - 1){
            while (NOF.get(i) == 0) {
                wNOF.add(0);
                i+= 1;
                if(i >= NOF.size()){
                    return wNOF;
                }
            }
            w = NOF.size() - i;
            int pos = NOF.size() - i - 1;
            while (NOF.get(i + pos) == 0) {
                pos -= 1;
            }
            int val = 0;
            int q = 0;
            for (int l = pos; l >= 0; l--){
                val += Math.pow(2, q) * NOF.get(i+l);
                q+= 1;
            }
            for(int p = 0; p < w; p++){
                if(p == pos){
                    wNOF.add(val);
                }else{
                    wNOF.add(0);
                }
            }
        }
        return wNOF;
    }

    public static ECC_Point windowNOF(ECC_Point point, BigInteger k, int w){
        ArrayList<Integer> wNOF = wNOF(k, w);
        ECC_Point Q = null;
        ArrayList<ECC_Point> P = new ArrayList<>();
        ArrayList<ECC_Point> nP = new ArrayList<>();
        for(int i = 1; i < Math.pow(2, w-1); i+=2){
            ECC_Point po = doubleAndAdd(point, BigInteger.valueOf(i));
            P.add(po);
            nP.add(po.negate());
        }
        for(Integer val : wNOF){
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

    // Fractional wNOF method
    public static ArrayList<Integer> fractwNOF(BigInteger k, int w0, double w1){
        ArrayList<Integer> NOF = NOF(k);
        ArrayList<Integer> wNOF = new ArrayList<Integer>();
        int i = 0;
        while(i <= NOF.size() - w0 - 1){ //change
            if(NOF.get(i) == NOF.get(i+1)){
                wNOF.add(0);
                i += 1;
            }else {
                while (NOF.get(i) == 0) {
                    wNOF.add(0);
                    i+=1;
                }
                int pos;
                int val = 0;
                int q = 0;
                if(i + w0 >= NOF.size()){
                    break;
                }
                for (int l = w0; l >= 0; l--){
                    val += Math.pow(2, q) * NOF.get(i+l);
                    q+= 1;
                }
                double comp = (((1+w1)* Math.pow(2, w0-1)) -1);
                if(Math.abs(val) >= comp){
                    pos = w0 - 1;
                }else{
                    pos = w0;
                }
                int pos2 = pos;
                while (NOF.get(i + pos2) == 0) {
                    pos2 -= 1;
                }
                val = 0;
                q = 0;
                for (int l = pos2; l >= 0; l--){
                    val += Math.pow(2, q) * NOF.get(i+l);
                    q+= 1;
                }
                for(int p = 0; p < pos + 1; p++){
                    if(p == pos2){
                        wNOF.add(val);
                    }else{
                        wNOF.add(0);
                    }
                }
                i += pos + 1;
            }
        }
        if(i <= NOF.size() - 1){
            while (NOF.get(i) == 0) {
                wNOF.add(0);
                i+= 1;
                if(i >= NOF.size()){
                    return wNOF;
                }
            }
            w0 = NOF.size() - i;
            int pos = NOF.size() - i - 1;
            while (NOF.get(i + pos) == 0) {
                pos -= 1;
            }
            int val = 0;
            int q = 0;
            for (int l = pos; l >= 0; l--){
                val += Math.pow(2, q) * NOF.get(i+l);
                q+= 1;
            }
            for(int p = 0; p < w0; p++){
                if(p == pos){
                    wNOF.add(val);
                }else{
                    wNOF.add(0);
                }
            }
        }
        return wNOF;
    }

    public static ECC_Point fractional_wNOF(ECC_Point point, BigInteger k, int w0, double w1){
        ArrayList<Integer> wNOF = fractwNOF(k, w0, w1);
        ECC_Point Q = null;
        ArrayList<ECC_Point> P = new ArrayList<>();
        ArrayList<ECC_Point> nP = new ArrayList<>();
        for(int i = 1; i <= ((Math.pow(2, w0 - 1) * (1 + w1)) - 1); i+=2){
            ECC_Point po = doubleAndAdd(point, BigInteger.valueOf(i));
            P.add(po);
            nP.add(po.negate());
        }
        for(Integer val : wNOF){
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




    // UNIFIED VERSIONS

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







    // MONTGOMERY STUFF

    // Montgomery Method (General)
    public static ECC_Point MontgomeryBinary(ECC_Point point, BigInteger n) {
        ECC_Point R0 = null;
        ECC_Point R1 = point;
        String d = n.toString(2);
        for(int i = 0; i <= d.length() - 1; i++){
            if(d.charAt(i) == '0'){
                if(R0 != null){
                    R1 = R0.pointAddition(R1);
                    R0 = R0.pointDoubling();
                }
            }else{
                if(R0 == null){
                    R0 = R1;
                }else{
                    R0 = R0.pointAddition(R1);
                }
                R1 = R1.pointDoubling();
            }
        }
        return R0;
    }

    // Montgomery Method (x-line variation)
    public static ECC_Point MontgomeryLadder(ECC_Point_M_xz point, BigInteger n) {
        ECC_Point_M_xz R0 = null;
        ECC_Point_M_xz R1 = point;
        String d = n.toString(2);
        for (int i = 0; i <= d.length() - 1; i++) {
            if(d.charAt(i) == '0') {
                if(R0 != null){
                    R1 = R0.diffAddition(R1, point);
                    R0 = R0.pointDoubling();
                }
            }else{
                if(R0 == null){
                    R0 = R1;
                }else{
                    R0 = R0.diffAddition(R1, point);
                }
                R1 = R1.pointDoubling();
            }
        }
        return R0;
    }

    public static ECC_Point MontgomeryLadder(ECC_Point_W_xz point, BigInteger n) {
        ECC_Point_W_xz R0 = null;
        ECC_Point_W_xz R1 = point;
        String d = n.toString(2);
        for (int i = 0; i <= d.length() - 1; i++) {
            if(d.charAt(i) == '0') {
                if(R0 != null){
                    R1 = R0.diffAddition(R1, point);
                    R0 = R0.pointDoubling();
                }
            }else{
                if(R0 == null){
                    R0 = R1;
                }else{
                    R0 = R0.diffAddition(R1, point);
                }
                R1 = R1.pointDoubling();
            }
        }
        return R0;
    }




}
