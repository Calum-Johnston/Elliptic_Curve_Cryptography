package ecc;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryField_Operations {

    // Field Must be closed under addition

    public static BigInteger addition(BigInteger a, BigInteger b) {
        return a.xor(b);
    }

    // p is reduction polynomial of GF(2^m)
    public static BigInteger multiple(BigInteger a, BigInteger b, BigInteger p, int degree){
        BigInteger mask1 = BigInteger.TWO.pow(degree);
        BigInteger mask2 = mask1.subtract(BigInteger.ONE);
        p = p.and(mask2);
        BigInteger result = new BigInteger("0");
        for(int i = 0; i < b.bitLength() ; i++){
            if(b.testBit(i)){
                result = result.xor(a);
            }
            a = a.shiftLeft(1);
            if(a.and(mask1).testBit(degree )){
                a = a.xor(p);
            }
        }
        return result.and(mask2);
    }

    public static BigInteger multiple2(BigInteger a, BigInteger b){
        BigInteger result = new BigInteger("0");
        for(int i = 0; i < b.bitLength() ; i++){
            if(b.testBit(i)){
                result = result.xor(a);
            }
            a = a.shiftLeft(1);
        }
        return result;
    }

    public static BigInteger multiplicativeInverse(BigInteger x, BigInteger p, int degree){
        BigInteger[] row1 = new BigInteger[3];
        BigInteger[] row2 = new BigInteger[3];
        BigInteger[] temp = new BigInteger[3];
        row1[0] = p; row1[1] = BigInteger.ZERO; row1[2] = BigInteger.ONE;
        row2[0] = x; row2[1] = BigInteger.ONE; row2[2] = BigInteger.ZERO;
        temp[0] = BigInteger.ZERO; temp[1] = BigInteger.ZERO; temp[2] = BigInteger.ZERO;
        while(!(temp[0].equals(BigInteger.ONE))) {
            BigInteger q = getQandR(row1[0], row2[0]).getQ();
            temp[0] = row1[0].xor(multiple2(q, row2[0]));
            temp[1] = row1[1].xor(multiple2(q, row2[1]));
            temp[2] = row1[2].xor(multiple2(q, row2[2]));
            row1 = Arrays.copyOf(row2,3);
            row2 = Arrays.copyOf(temp, 3);
        }
        return temp[1];
    }

    public static QR getQandR(BigInteger a1, BigInteger b1){
        int[] a = new int[a1.bitLength()];
        int[] b = new int[b1.bitLength()];
        String a2 = a1.toString(2);
        String b2 = b1.toString(2);
        for(int i = 0; i < a2.length(); i++){
            a[i] = Character.getNumericValue(a2.charAt(i));
        }
        for(int i = 0; i < b2.length(); i++){
            b[i] = Character.getNumericValue(b2.charAt(i));
        }
        String result = "";
        int aLength = a.length;
        int bLength = b.length;
        int aPointer = 0;
        while(bLength <= aLength && aLength > 0){
            if(a[aPointer] == 1) {
                aPointer++; aLength -= 1;
                for (int j = 0; j < b.length - 1; j++) {
                    a[j + aPointer] = a[j + aPointer] ^ b[j + 1];
                }
                result += "1";
            }
            else{
                aPointer++; aLength -= 1;
                for (int j = 0; j < b.length - 1; j++) {
                    a[j + aPointer] = a[j + aPointer] ^ 0;
                }
                result += "0";
            }
        }
        if (result == "") {
            result ="0";
        }
        BigInteger q = new BigInteger(result, 2);
        String temp = "";
        for(int i = a.length - aLength; i < a.length; i++){
            temp += a[i];
        }
        BigInteger r = new BigInteger(temp, 2);
        return new QR(q, r);
    }

    public static BigInteger division(BigInteger a, BigInteger b, BigInteger p, int degree){
        BigInteger multiplicativeInverse_b = multiplicativeInverse(b, p, degree);
        BigInteger result = multiple(a, multiplicativeInverse_b, p, degree);
        return result;
    }

}
