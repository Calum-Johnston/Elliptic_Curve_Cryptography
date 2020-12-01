package ecc;

import java.math.BigInteger;
import java.util.Random;

public class ECC_Key {

    BigInteger privateKey;
    ECC_Point publicKey;

    public ECC_Key(ECC_Curve curve){
        Random rnd = new Random();
        do{
            privateKey = new BigInteger(curve.getN().bitLength(), rnd);
        } while(privateKey.compareTo(curve.getN()) > 0);
        publicKey = curve.getG().pointMultiplication(privateKey, curve.getP(), curve.getA());
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
