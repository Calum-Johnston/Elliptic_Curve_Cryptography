package ecc.ECC_Operations;

import ecc.Points.ECC_Point;
import ecc.Points.Montgomery.ECC_Point_M_xz;
import ecc.Points.Weierstrass.ECC_Point_W_xz;

import java.math.BigInteger;
import java.util.Random;

public class ECC_Key {

    BigInteger privateKey;
    ECC_Point publicKey;

    public ECC_Key(ECC_Point p, BigInteger N){
        Random rnd = new Random();
        BigInteger n = N;
        do{
            privateKey = new BigInteger(n.bitLength(), rnd);
        } while(privateKey.compareTo(n) > 0);
        publicKey = Scalar_Multiplication.doubleAndAdd(p, privateKey);
    }

    public ECC_Key(ECC_Point_M_xz p, BigInteger N){
        Random rnd = new Random();
        BigInteger n = N;
        do{
            privateKey = new BigInteger(n.bitLength(), rnd);
        } while(privateKey.compareTo(n) > 0);
        publicKey = Scalar_Multiplication.MontgomeryLadder(p, privateKey);
    }

    public ECC_Key(ECC_Point_W_xz p, BigInteger N){
        Random rnd = new Random();
        BigInteger n = N;
        do{
            privateKey = new BigInteger(n.bitLength(), rnd);
        } while(privateKey.compareTo(n) > 0);
        publicKey = Scalar_Multiplication.MontgomeryLadder(p, privateKey);
    }


    public BigInteger getPrivateKey(){
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey){
        this.privateKey = privateKey;
    }

    public ECC_Point getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(ECC_Point publicKey){
        this.publicKey = publicKey;
    }

}
