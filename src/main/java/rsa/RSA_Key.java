package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSA_Key {

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger N;

    public RSA_Key(){
        this(1024);
    }

    public RSA_Key(int k){
        // Select public exponent
        publicKey = new BigInteger("65537");

        // Generate prime p and prime q
        Random rand = new SecureRandom();
        BigInteger p;
        BigInteger q;
        do {
            p = BigInteger.probablePrime(k / 2, rand);
        } while(p.mod(publicKey).equals(BigInteger.ONE));
        do {
            q = BigInteger.probablePrime(k / 2, rand);
        } while(q.mod(publicKey).equals(BigInteger.ONE));

        // Calculate product
        N = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));

        // Generate private exponent
        privateKey = publicKey.modInverse(phi);
    }

    public BigInteger getPublicKey(){
        return publicKey;
    }

    public BigInteger getPrivateKey(){
        return privateKey;
    }

    public BigInteger getN(){
        return N;
    }

}
