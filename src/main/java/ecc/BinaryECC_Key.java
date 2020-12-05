package ecc;

import java.math.BigInteger;
import java.util.Random;

public class BinaryECC_Key {

    BigInteger privateKey;
    BinaryField_Point publicKey;

    public BinaryECC_Key(BinaryField_Curve curve){
        Random rnd = new Random();
        do{
            privateKey = new BigInteger(curve.getN().bitLength(), rnd);
        } while(privateKey.compareTo(curve.getN()) > 0);
        publicKey = curve.getG().pointMultiplication(privateKey);
    }

    public BigInteger getPrivateKey(){
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey){
        this.privateKey = privateKey;
    }

    public BinaryField_Point getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(BinaryField_Point publicKey){
        this.publicKey = publicKey;
    }

}
