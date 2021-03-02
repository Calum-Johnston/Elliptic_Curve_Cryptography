import ecc.*;
import ecc.Weierstrass.*;

import java.math.BigInteger;

public class Main {

    // Potential Curves https://tools.ietf.org/html/rfc5639
    public ECC_Curve_Weierstrass generatePrimeFieldCurve(){
        BigInteger p = new BigInteger("E95E4A5F737059DC60DFC7AD95B3D8139515620F",16);
        BigInteger a = new BigInteger("340E7BE2A280EB74E2BE61BADA745D97E8F7C300",16);
        BigInteger b = new BigInteger("1E589A8595423412134FAA2DBDEC95C8D8675E58",16);
        BigInteger x = new BigInteger("BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC3",16);
        BigInteger y = new BigInteger("1667CB477A1A8EC338F94741669C976316DA6321",16);
        BigInteger n = new BigInteger("E95E4A5F737059DC60DF5991D45029409E60FC09",16);
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_Weierstrass(p, a, b, x, y, n, h);
    }

    // From https://www.ijert.org/research/implementation-of-elliptic-curve-arithmetic-operations-for-prime-field-and-binary-field-using-java-biginteger-class-IJERTV6IS080211.pdf
    public ECC_Curve_Weierstrass generatePrimeFieldCurve_192(){
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF",16);
        BigInteger a = new BigInteger("-3");
        BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1",16);
        BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012",16);
        BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811",16);
        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831",16);
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_Weierstrass(p, a, b, x, y, n, h);
    }

    public ECC_Curve_Weierstrass generatePrimeFieldCurve_basic(){
        BigInteger p = new BigInteger("23");
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("1");
        BigInteger x = new BigInteger("3");
        BigInteger y = new BigInteger("10");
        BigInteger n = new BigInteger("28");
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_Weierstrass(p, a, b, x, y, n, h);
    }

    public void test_doubleandadd(){
        ECC_Curve_Weierstrass curve = generatePrimeFieldCurve();
        BigInteger multiple = new BigInteger("3");

        // Affine
        ECC_Point_Aff affine = curve.getG();
        ECC_Point_Aff temp = affine.doubleAndAdd(multiple);
        BigInteger x = temp.getX();
        BigInteger y = temp.getY();

        // Projective
        ECC_Point_Proj proj = new ECC_Point_Proj(affine);
        ECC_Point_Proj temp2 = proj.doubleAndAdd(multiple);
        ECC_Point_Aff aff2 = new ECC_Point_Aff(temp2);

        // Jacobian
        ECC_Point_Jacob jac = new ECC_Point_Jacob(affine);
        ECC_Point_Jacob temp3 = jac.doubleAndAdd(multiple);
        ECC_Point_Aff aff3 = new ECC_Point_Aff(temp3);

        // Chudnovsky
        ECC_Point_Chud chud = new ECC_Point_Chud(affine);
        ECC_Point_Chud temp4 = chud.doubleAndAdd(multiple);
        ECC_Point_Aff aff4 = new ECC_Point_Aff(temp4);

        // Modified Jacobian
        ECC_Point_ModJacob modjac = new ECC_Point_ModJacob(affine);
        ECC_Point_ModJacob temp5 = modjac.doubleAndAdd(multiple);
        ECC_Point_Aff aff5 = new ECC_Point_Aff(temp5);

        boolean projective;
        boolean jacobian;
        boolean chudnovsky;
        boolean modjacob;
        if(x.equals(aff2.getX()) && y.equals(aff2.getY())){
            projective = true;
        }else{
            projective = false;
        }
        if(x.equals(aff3.getX()) && y.equals(aff3.getY())){
            jacobian = true;
        }else{
            jacobian = false;
        }
        if(x.equals(aff4.getX()) && y.equals(aff4.getY())){
            chudnovsky = true;
        }else {
            chudnovsky = false;
        }
        if(x.equals(aff5.getX()) && y.equals(aff5.getY())){
            modjacob = true;
        }else {
            modjacob = false;
        }
        System.out.println("Projective: " + projective);
        System.out.println("Jacobian: " + jacobian);
        System.out.println("Chudnovsky: " + chudnovsky);
        System.out.println("Modified Jacobian: " + modjacob);
    }

    public void test_montgomery(){
        ECC_Curve_Weierstrass curve = generatePrimeFieldCurve();
        BigInteger multiple = new BigInteger("3");

        // Affine
        ECC_Point_Aff affine = curve.getG();
        ECC_Point_Aff temp = affine.Montgomery(multiple);
        BigInteger x = temp.getX();
        BigInteger y = temp.getY();

        // Projective
        ECC_Point_Proj proj = new ECC_Point_Proj(affine);
        ECC_Point_Proj temp2 = proj.Montgomery(multiple);
        ECC_Point_Aff aff2 = new ECC_Point_Aff(temp2);

        // Jacobian
        ECC_Point_Jacob jac = new ECC_Point_Jacob(affine);
        ECC_Point_Jacob temp3 = jac.Montgomery(multiple);
        ECC_Point_Aff aff3 = new ECC_Point_Aff(temp3);

        // Chudnovsky
        ECC_Point_Chud chud = new ECC_Point_Chud(affine);
        ECC_Point_Chud temp4 = chud.Montgomery(multiple);
        ECC_Point_Aff aff4 = new ECC_Point_Aff(temp4);

        // Modified Jacobian
        ECC_Point_ModJacob modjac = new ECC_Point_ModJacob(affine);
        ECC_Point_ModJacob temp5 = modjac.Montgomery(multiple);
        ECC_Point_Aff aff5 = new ECC_Point_Aff(temp5);

        boolean projective;
        boolean jacobian;
        boolean chudnovsky;
        boolean modjacob;
        if(x.equals(aff2.getX()) && y.equals(aff2.getY())){
            projective = true;
        }else{
            projective = false;
        }
        if(x.equals(aff3.getX()) && y.equals(aff3.getY())){
            jacobian = true;
        }else{
            jacobian = false;
        }
        if(x.equals(aff4.getX()) && y.equals(aff4.getY())){
            chudnovsky = true;
        }else {
            chudnovsky = false;
        }
        if(x.equals(aff5.getX()) && y.equals(aff5.getY())){
            modjacob = true;
        }else {
            modjacob = false;
        }
        System.out.println("Projective: " + projective);
        System.out.println("Jacobian: " + jacobian);
        System.out.println("Chudnovsky: " + chudnovsky);
        System.out.println("Modified Jacobian: " + modjacob);
    }

    public void test_Conversions(){
        // Affine to others
        ECC_Curve_Weierstrass curve = generatePrimeFieldCurve();
        ECC_Point_Aff affine = curve.getG();
        ECC_Point_Proj proj = new ECC_Point_Proj(curve, affine.getX(), affine.getY(), BigInteger.ONE);
        ECC_Point_Jacob jacob = new ECC_Point_Jacob(curve, affine.getX(), affine.getY(), BigInteger.ONE);
        ECC_Point_Chud chud = new ECC_Point_Chud(curve, affine.getX(), affine.getY(), BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);
        ECC_Point_ModJacob modJacob = new ECC_Point_ModJacob(curve, affine.getX(), affine.getY(), BigInteger.ONE, curve.getA());

        boolean affine_ = false;
        ECC_Point_Proj aff_to_proj = new ECC_Point_Proj(affine);
        ECC_Point_Jacob aff_to_jacob = new ECC_Point_Jacob(affine);
        ECC_Point_Chud aff_to_chud = new ECC_Point_Chud(affine);
        ECC_Point_ModJacob aff_to_modJacob = new ECC_Point_ModJacob(affine);
        if(aff_to_proj.getX() == proj.getX() && aff_to_proj.getY() == proj.getY() && aff_to_proj.getZ() == proj.getZ()){
            if(aff_to_jacob.getX() == jacob.getX() && aff_to_jacob.getY() == jacob.getY() && aff_to_jacob.getZ() == jacob.getZ()){
                if(aff_to_chud.getX() == chud.getX() && aff_to_chud.getY() == chud.getY() && aff_to_chud.getZ() == chud.getZ() && aff_to_chud.getZ2() == chud.getZ2() && aff_to_chud.getZ3() == chud.getZ3()){
                    if(aff_to_modJacob.getX() == modJacob.getX() && aff_to_modJacob.getY() == modJacob.getY() && aff_to_modJacob.getZ() == modJacob.getZ() && aff_to_modJacob.getAz4() == modJacob.getAz4()){
                        affine_ = true;
                    }
                }
            }
        }

        boolean proj_ = false;
        ECC_Point_Aff proj_to_aff = new ECC_Point_Aff(proj);
        ECC_Point_Jacob proj_to_jacob = new ECC_Point_Jacob(proj);
        ECC_Point_Chud proj_to_chud = new ECC_Point_Chud(proj);
        ECC_Point_ModJacob proj_to_modJacob = new ECC_Point_ModJacob(proj);
        if(proj_to_aff.getX().equals(affine.getX()) && proj_to_aff.getY().equals(affine.getY())){
            if(proj_to_jacob.getX().equals(jacob.getX()) && proj_to_jacob.getY().equals(jacob.getY()) && proj_to_jacob.getZ().equals(jacob.getZ())){
                if(proj_to_chud.getX().equals(chud.getX()) && proj_to_chud.getY().equals(chud.getY()) && proj_to_chud.getZ().equals(chud.getZ()) && proj_to_chud.getZ2().equals(chud.getZ2()) && proj_to_chud.getZ3().equals(chud.getZ3())){
                    if(proj_to_modJacob.getX().equals(modJacob.getX()) && proj_to_modJacob.getY().equals(modJacob.getY()) && proj_to_modJacob.getZ().equals(modJacob.getZ()) && proj_to_modJacob.getAz4().equals(modJacob.getAz4())){
                        proj_ = true;
                    }
                }
            }
        }

        boolean jacob_ = false;
        ECC_Point_Aff jacob_to_aff = new ECC_Point_Aff(jacob);
        ECC_Point_Proj jacob_to_proj = new ECC_Point_Proj(jacob);
        ECC_Point_Chud jacob_to_chud = new ECC_Point_Chud(jacob);
        ECC_Point_ModJacob jacob_to_modJacob = new ECC_Point_ModJacob(jacob);
        if(jacob_to_aff.getX().equals(affine.getX()) && jacob_to_aff.getY().equals(affine.getY())){
            if(jacob_to_proj.getX().equals(proj.getX()) && jacob_to_proj.getY().equals(proj.getY()) && jacob_to_proj.getZ().equals(proj.getZ())){
                if(jacob_to_chud.getX().equals(chud.getX()) && jacob_to_chud.getY().equals(chud.getY()) && jacob_to_chud.getZ().equals(chud.getZ()) && jacob_to_chud.getZ2().equals(chud.getZ2()) && jacob_to_chud.getZ3().equals(chud.getZ3())){
                    if(jacob_to_modJacob.getX().equals(modJacob.getX()) && jacob_to_modJacob.getY().equals(modJacob.getY()) && jacob_to_modJacob.getZ().equals(modJacob.getZ()) && jacob_to_modJacob.getAz4().equals(modJacob.getAz4())){
                        jacob_ = true;
                    }
                }
            }
        }

        boolean chud_ = false;
        ECC_Point_Aff chud_to_aff = new ECC_Point_Aff(chud);
        ECC_Point_Proj chud_to_proj = new ECC_Point_Proj(chud);
        ECC_Point_Jacob chud_to_jacob = new ECC_Point_Jacob(chud);
        ECC_Point_ModJacob chud_to_modJacob = new ECC_Point_ModJacob(chud);
        if(chud_to_aff.getX().equals(affine.getX()) && chud_to_aff.getY().equals(affine.getY())){
            if(chud_to_proj.getX().equals(proj.getX()) && chud_to_proj.getY().equals(proj.getY()) && chud_to_proj.getZ().equals(proj.getZ())){
                if(chud_to_jacob.getX().equals(jacob.getX()) && chud_to_jacob.getY().equals(jacob.getY()) && chud_to_jacob.getZ().equals(jacob.getZ())){
                    if(chud_to_modJacob.getX().equals(modJacob.getX()) && chud_to_modJacob.getY().equals(modJacob.getY()) && chud_to_modJacob.getZ().equals(modJacob.getZ()) && chud_to_modJacob.getAz4().equals(modJacob.getAz4())){
                        chud_ = true;
                    }
                }
            }
        }

        boolean modJacob_ = false;
        ECC_Point_Aff modJacob_to_affine = new ECC_Point_Aff(modJacob);
        ECC_Point_Proj modJacob_to_proj = new ECC_Point_Proj(modJacob);
        ECC_Point_Jacob modJacob_to_jacob = new ECC_Point_Jacob(modJacob);
        ECC_Point_Chud modJacob_to_chud = new ECC_Point_Chud(modJacob);
        if(modJacob_to_affine.getX().equals(affine.getX()) && modJacob_to_affine.getY().equals(affine.getY())){
            if(modJacob_to_proj.getX().equals(proj.getX()) && modJacob_to_proj.getY().equals(proj.getY()) && modJacob_to_proj.getZ().equals(proj.getZ())){
                if(modJacob_to_jacob.getX().equals(jacob.getX()) && modJacob_to_jacob.getY().equals(jacob.getY()) && modJacob_to_jacob.getZ().equals(jacob.getZ())){
                    if(modJacob_to_chud.getX().equals(chud.getX()) && modJacob_to_chud.getY().equals(chud.getY()) && modJacob_to_chud.getZ().equals(chud.getZ()) && modJacob_to_chud.getZ2().equals(chud.getZ2()) && modJacob_to_chud.getZ3().equals(chud.getZ3())){                        chud_ = true;
                        modJacob_ = true;
                    }
                }
            }
        }
        System.out.println("Affine: " + affine_);
        System.out.println("Projective: " + proj_);
        System.out.println("Jacobian: " + jacob_);
        System.out.println("Chudnovsky: " + chud_);
        System.out.println("Modified Jacobian: " + modJacob_);

    }

    public void test_PrimeFieldCurve(){
        String message = "Alisha Jane Walsh";
        ECC_Curve_Weierstrass curve = generatePrimeFieldCurve();

        // Generate key pair
        ECC_Key k1 = new ECC_Key(curve);
        ECC_Key k2 = new ECC_Key(curve);

        // ECDSA
        ECC_Signature sig = ECDSA.Sign(curve, k1, message);
        Boolean valid = ECDSA.Verify(curve, sig, k1, message);
        System.out.println("Prime field ECDSA: " + valid);

        // ECDH
        BigInteger sharedSecret = ECDH.computeSecret(curve, k1.getPublicKey(), k2.getPrivateKey());
        BigInteger sharedSecret2 = ECDH.computeSecret(curve, k2.getPublicKey(), k1.getPrivateKey());
        System.out.println("Prime field ECDH: " + sharedSecret.equals(sharedSecret2));
    }

    public static void main(String[] args){
        Main main = new Main();
        main.test_PrimeFieldCurve();

    }

}
