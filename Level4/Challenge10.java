import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge10
{
    private static BigInteger[][] memoizeHelper;

    private static BigInteger helper(int location, int t, int n)
    {
        if ( location == n )
            return BigInteger.ONE;

        if ( location == 0 || t == 0 )
            return BigInteger.ZERO;

        if ( memoizeHelper[location][t] != null )
            return memoizeHelper[location][t];

        memoizeHelper[location][t] = helper(location - 1, t - 1, n).add(helper(location, t - 1, n)).add(helper(location + 1, t - 1, n));

        return memoizeHelper[location][t];
    }

    public static int answer(int t, int n)
    {
        memoizeHelper = new BigInteger[1024][1024];
        return helper(1, t, n).mod(BigInteger.valueOf(123454321)).intValue();
    }

    public static void main(String[] args)
    {
        System.out.println("" + answer(1,2));
        System.out.println("" + answer(3,2));
        System.out.println("" + answer(1000, 1000));
        System.out.println("" + answer(0,2));
    }
}
