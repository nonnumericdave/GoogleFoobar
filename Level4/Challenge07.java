import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge07
{
    private static class BigRational
    {
        private BigInteger num;
        private BigInteger den;

        public static BigRational valueOf(int numerator)
        {
            return new BigRational(numerator);
        }

        public BigRational(int numerator)
        {
            this(numerator, 1);
        }

        public BigRational(int numerator, int denominator)
        {
            this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
        }

        public BigRational(BigInteger numerator, BigInteger denominator)
        {
            num = numerator;
            den = denominator;

            reduce();
        }

        public BigRational add(BigRational rational)
        {
            BigInteger numerator =
                num.multiply(rational.den).add(
                    den.multiply(rational.num));

            BigInteger denominator =
                den.multiply(rational.den);

            return new BigRational(numerator, denominator);
        }

        public BigRational subtract(BigRational rational)
        {
            BigInteger numerator =
                num.multiply(rational.den).subtract(
                    den.multiply(rational.num));

            BigInteger denominator =
                den.multiply(rational.den);

            return new BigRational(numerator, denominator);
        }

        public BigRational multiply(BigRational rational)
        {
            BigInteger numerator =
                num.multiply(rational.num);

            BigInteger denominator =
                den.multiply(rational.den);

            return new BigRational(numerator, denominator);
        }

        public BigRational divide(BigRational rational)
        {
            BigInteger numerator =
                num.multiply(rational.den);

            BigInteger denominator =
                den.multiply(rational.num);

            return new BigRational(numerator, denominator);
        }

        public int compareTo(BigRational rational)
        {
            reduce();
            rational.reduce();

            return num.multiply(rational.den).compareTo(
                       den.multiply(rational.num));
        }

        public BigInteger numerator()
        {
            return num;
        }

        public BigInteger denominator()
        {
            return den;
        }

        public boolean isInteger()
        {
            return den.compareTo(BigInteger.ONE) == 0;
        }

        public BigInteger bigIntegerValue()
        {
            return num.divide(den);
        }

        public int intValue()
        {
            return bigIntegerValue().intValue();
        }

        public double doubleValue()
        {
            return num.doubleValue() / den.doubleValue();
        }

        private void reduce()
        {
            BigInteger gcd = num.gcd(den);

            if ( gcd.compareTo(BigInteger.ZERO) == 0 )
                return;

            num = num.divide(gcd);
            den = den.divide(gcd);
        }
    }

    public static int answer(int[][] vertices)
    {
        BigRational[][] verts = new BigRational[3][2];
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 2; ++j)
                verts[i][j] = BigRational.valueOf(vertices[i][j]);

        return bigAnswer(verts);
    }

    private static int bigAnswer(BigRational[][] verts)
    {
        Arrays.sort(verts, new Comparator<BigRational[]>()
        {
            public int compare(BigRational[] o1, BigRational[] o2)
            {
                int comparisonY = o1[1].compareTo(o2[1]);
                if ( comparisonY != 0 )
                    return comparisonY;

                return o1[0].compareTo(o2[0]);
            }

            public boolean equals(Object obj)
            {
                return false;
            }
        });

        BigInteger permPoints = perimeterPoints(verts);
        BigRational area = null;

        if ( verts[0][1].compareTo(verts[1][1]) == 0 )
        {
            area = flatBottomArea(verts);
        }
        else if ( verts[1][1].compareTo(verts[2][1]) == 0 )
        {
            area = flatTopArea(verts);
        }
        else
        {
            BigRational splitInvSlope = inverseSlope(verts[0], verts[2]);

            BigRational splitVertX =
                verts[1][1].subtract(intercept(verts[0], splitInvSlope)).multiply(
                    splitInvSlope);

            BigRational[] splitVert =
                new BigRational[] {splitVertX, verts[1][1]};

            BigRational[][] topVerts =
                ( splitVertX.compareTo(verts[1][0]) == -1) ?
                new BigRational[][] {splitVert, verts[1], verts[2]} :
                new BigRational[][] {verts[1], splitVert, verts[2]};

            BigRational[][] bottomVerts =
                ( splitVertX.compareTo(verts[1][0]) == -1 ) ?
                new BigRational[][] {verts[0], splitVert, verts[1]} :
                new BigRational[][] {verts[0], verts[1], splitVert};

            area = flatBottomArea(topVerts).add(flatTopArea(bottomVerts));
        }

        return picksTheorem(permPoints, area).intValue();
    }

    private static BigRational flatBottomArea(BigRational[][] verts)
    {
        BigRational base = verts[1][0].subtract(verts[0][0]);
        BigRational height = verts[2][1].subtract(verts[0][1]);

        return new BigRational(1, 2).multiply(base).multiply(height);
    }

    private static BigRational flatTopArea(BigRational[][] verts)
    {
        BigRational base = verts[2][0].subtract(verts[1][0]);
        BigRational height = verts[2][1].subtract(verts[0][1]);

        return new BigRational(1, 2).multiply(base).multiply(height);
    }

    private static BigInteger perimeterPoints(BigRational[][] verts)
    {
        BigRational bottomSlope = inverseSlope(verts[1], verts[0]);

        BigInteger bottomPoints =
            (verts[0][1].compareTo(verts[1][1]) == 0) ?
            verts[1][0].subtract(verts[0][0]).bigIntegerValue() :
            verts[1][1].subtract(verts[0][1]).bigIntegerValue().divide(bottomSlope.denominator().abs());

        BigRational topSlope = inverseSlope(verts[2], verts[1]);

        BigInteger topPoints =
            (verts[2][1].compareTo(verts[1][1]) == 0) ?
            verts[2][0].subtract(verts[1][0]).bigIntegerValue() :
            verts[2][1].subtract(verts[1][1]).bigIntegerValue().divide(topSlope.denominator().abs());

        BigRational topBottomSlope = inverseSlope(verts[2], verts[0]);

        BigInteger topBottomPoints =
            verts[2][1].subtract(verts[0][1]).bigIntegerValue().divide(topBottomSlope.denominator().abs());

        return bottomPoints.add(topPoints).add(topBottomPoints);
    }

    private static BigInteger picksTheorem(BigInteger perimeterPoints, BigRational area)
    {
        BigRational interiorPoints =
            area.subtract(new BigRational(perimeterPoints, BigInteger.valueOf(2))).add(new BigRational(1));

        return interiorPoints.numerator();
    }

    private static BigRational intercept(BigRational[] vert, BigRational slope)
    {
        return vert[1].subtract(
                   vert[0].divide(slope));
    }

    private static BigRational inverseSlope(BigRational[] lowerVert, BigRational[] upperVert)
    {
        return upperVert[0].subtract(lowerVert[0]).divide(
                   upperVert[1].subtract(lowerVert[1]));
    }

    public static void main(String[] args)
    {
        //int ans = answer(new int[][]{{2,11}, {78,15}, {213,11}});
        //int ans = answer(new int[][]{{2,3}, {6,9}, {10,160}});
        int ans = answer(new int[][] {{91207, 89566}, {-88690, -83026}, {67100, 47194}});
        System.out.println(new Integer(ans).toString());
    }
}
