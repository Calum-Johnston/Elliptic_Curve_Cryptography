package future;

import java.math.BigInteger;
import java.util.Random;

public class BinaryECC_Key {

    BigInteger privateKey;
    BinaryField_Point publicKey;

    public BinaryECC_Key(BinaryField_Curve curve){
        Random rnd = new Random();
        BigInteger n = curve.getN();
        do{
            privateKey = new BigInteger(n.bitLength(), rnd);
        } while(privateKey.compareTo(n) >= 0 || privateKey.compareTo(BigInteger.ZERO) < 1);
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
