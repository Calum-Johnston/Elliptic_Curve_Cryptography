package ecc;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BaseOperations {

    // Field Must be closed under addition

    public static List<Integer> addition(List<Integer> a, List<Integer> b) {
        List<Integer> c = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            c.add(a.get(i) ^ b.get(i));
        }
        return c;
    }

    // p is reduction polynomial of GF(2^m)
    public static BigInteger multiple(BigInteger a, BigInteger b, BigInteger p, int degree){
        BigInteger mask = BigInteger.TWO.pow(degree).subtract(BigInteger.ONE);
        BigInteger result = new BigInteger("0");
        p = p.and(mask);
        for(int i = 0; i < b.bitLength(); i++){
            if(b.testBit(i)){
                result = result.xor(a);
            }
            boolean carry = a.testBit(degree - 1);
            a = a.shiftLeft(1).and(mask);
            if(carry){
                a = a.xor(p);
            }
        }
        return result;
    }

    public static void multiplicativeInverse(BigInteger x, BigInteger p){

    }

    public static void getQandR(int[] a, int[] b){
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
        System.out.println(result);
        System.out.println(a.toString());
    }

}
