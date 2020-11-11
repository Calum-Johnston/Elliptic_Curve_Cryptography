import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main(String[] args){
        Main main = new Main();
        EllipticCurve c = main.generateCurve();
        main.generateKeyPair(c);
    }

    // Potential Curves https://tools.ietf.org/html/rfc5639
    public EllipticCurve generateCurve(){
        String p = "E95E4A5F737059DC60DFC7AD95B3D8139515620F";
        String a = "340E7BE2A280EB74E2BE61BADA745D97E8F7C300";
        String b = "1E589A8595423412134FAA2DBDEC95C8D8675E58";
        String x = "BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC3";
        String y = "1667CB477A1A8EC338F94741669C976316DA6321";
        String q = "E95E4A5F737059DC60DF5991D45029409E60FC09";
        String h = "1";
        return new EllipticCurve(p, a, b, x, y, q, h);
    }

    public void generateKeyPair(EllipticCurve curve){
        // Pick random number (private key)
        // Calculate private * generator to get public
        Random rnd = new Random();
        BigInteger randomNumber;
        do{
            randomNumber = new BigInteger(curve.getN().bitLength(), rnd);
        } while(randomNumber.compareTo(curve.getN()) > 0);
        System.out.println(randomNumber);
    }

}
