import java.math.BigInteger;

public class Main {

    public static void main(String[] args){
        Curve curve = new Curve("37", "0", "7", "6", "8", "200", "1");
        Main main = new Main();
        Pair p = main.pointAddition(new BigInteger("6"), new BigInteger("1"), new BigInteger("8"), new BigInteger("1"));
        System.out.println(p.getX());
    }

    // Potential Curves https://tools.ietf.org/html/rfc5639
    public void generateCurve(){
        String p = "E95E4A5F737059DC60DFC7AD95B3D8139515620F";
        String a = "340E7BE2A280EB74E2BE61BADA745D97E8F7C300";
        String b = "1E589A8595423412134FAA2DBDEC95C8D8675E58";
        String x = "BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC3";
        String y = "1667CB477A1A8EC338F94741669C976316DA6321";
        String q = "E95E4A5F737059DC60DF5991D45029409E60FC09";
        String h = "1";
        Curve curve = new Curve(p, a, b, x, y, q, h);
    }

    public void generateKeyPair(Curve curve){

    }

    public void pointDoubling(BigInteger d ){

    }

    public Pair pointAddition(BigInteger x1, BigInteger y1, BigInteger x2, BigInteger y2){
        BigInteger gradient = (y2.subtract(y1)).divide((x2.subtract(x1)));
        BigInteger x3 = gradient.pow(2).subtract(x1).subtract(x2);
        BigInteger y3 = ((x1.subtract(x3)).multiply(gradient)).subtract(y1);
        Pair pair = new Pair(x3, y3);
        return pair;
    }

    //https://medium.com/asecuritysite-when-bob-met-alice/adding-points-in-elliptic-curve-cryptography-a1f0a1bce638
    //https://en.wikipedia.org/wiki/Elliptic_curve_point_multiplication

}
