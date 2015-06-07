import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge08
{
    private static BigInteger[] memoizeFactorial = new BigInteger[50];

    private static BigInteger factorial(int n)
    {
        if ( n <= 0 )
            return BigInteger.ONE;

        if ( memoizeFactorial[n] != null )
            return memoizeFactorial[n];

        memoizeFactorial[n] = BigInteger.valueOf(n).multiply(factorial(n - 1));

        return memoizeFactorial[n];
    }

    private static BigInteger choose(int n, int k)
    {
        return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
    }

    private static BigInteger[][] memoizeSubanswer = new BigInteger[50][50];

    private static BigInteger subanswer(int x, int n)
    {
        if ( x == 0 )
        {
            if ( n == 0 )
                return BigInteger.ONE;

            return BigInteger.ZERO;
        }

        if ( memoizeSubanswer[x][n] != null )
            return memoizeSubanswer[x][n];

        BigInteger ans = BigInteger.ZERO;

        for (int i = x; i <= n; ++i)
            ans = ans.add(choose(n-1, i-1).multiply(subanswer(x-1, i-1)).multiply(factorial(n-i)));

        memoizeSubanswer[x][n] = ans;

        return ans;
    }

    public static String answer(int x, int y, int n)
    {
        BigInteger ans = BigInteger.ZERO;

        for (int i = x; i <= n - y + 1; ++i)
            ans = ans.add(choose(n-1, i-1).multiply(subanswer(x-1, i-1)).multiply(subanswer(y-1, n-i)));

        return ans.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(answer(2,2,3));
        System.out.println(answer(1,2,6));
        System.out.println(answer(21,13,40));
    }
}
