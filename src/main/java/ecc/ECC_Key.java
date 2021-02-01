package ecc;

import java.math.BigInteger;
import java.util.Random;

public class ECC_Key {

    BigInteger privateKey;
    ECC_Point_Aff publicKey;

    public ECC_Key(ECC_Curve curve){
        Random rnd = new Random();
        BigInteger n = curve.getN();
        do{
            privateKey = new BigInteger(n.bitLength(), rnd);
        } while(privateKey.compareTo(n) > 0);
        publicKey = curve.getG().pointMultiplication(privateKey);
    }

    public BigInteger getPrivateKey(){
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey){
        this.privateKey = privateKey;
    }

    public ECC_Point_Aff getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(ECC_Point_Aff publicKey){
        this.publicKey = publicKey;
    }

}
