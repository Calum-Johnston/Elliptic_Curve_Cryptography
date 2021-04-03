import ecc.ECC_Operations.Scalar_Multiplication;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args){
        Main main = new Main();
        main.generateSpeeds();
    }

    public void generateSpeeds(){
        int q = 7;
        int w0 = (int) Math.floor(((Math.log(q) / Math.log(2)) + 2));
        int r = q - (int) Math.pow(2, w0-2);
        double w1 = r/Math.pow(2, w0-2);

        BigInteger temp = new BigInteger("11101001100100010101110101010111", 2);
        System.out.println(Scalar_Multiplication.wNOF(temp, w0));
        System.out.println(Scalar_Multiplication.fractwNOF(temp, w0, w1));

    }



}
