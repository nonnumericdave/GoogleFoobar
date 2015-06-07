import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge09
{
    private static BigInteger[] memoizeFactorial = new BigInteger[800];

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
        if ( n < k )
            return BigInteger.ZERO;

        if ( (n == k) || (k == 0) )
            return BigInteger.ONE;

        return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
    }

    private static BigInteger[] memoizeEdges = new BigInteger[50];

    private static BigInteger edges(int n)
    {
        if ( memoizeEdges[n] != null )
            return memoizeEdges[n];

        memoizeEdges[n] = choose(n, 2);

        return memoizeEdges[n];
    }

    private static BigInteger graphs(int n, int k)
    {
        return choose(edges(n).intValue(), k);
    }

    private static BigInteger[] memoizeTrees = new BigInteger[50];

    private static BigInteger trees(int n)
    {
        if ( n <= 1 )
            return BigInteger.ONE;

        if ( memoizeTrees[n] != null )
            return memoizeTrees[n];

        memoizeTrees[n] =  BigInteger.valueOf(n).pow(n - 2);

        return memoizeTrees[n];
    }

    private static BigInteger[][] memoizeAnswers = new BigInteger[50][800];

    private static BigInteger helper(int n, int k)
    {
        if ( memoizeAnswers[n][k] != null )
            return memoizeAnswers[n][k];

        if ( (k < (n - 1)) || (BigInteger.valueOf(k).compareTo(edges(n)) == 1) )
        {
            memoizeAnswers[n][k] = BigInteger.ZERO;

            return memoizeAnswers[n][k];
        }

        if ( k == (n - 1) )
        {
            memoizeAnswers[n][k] = trees(n);

            return memoizeAnswers[n][k];
        }

        BigInteger ans = graphs(n, k);
        for (int i = 0; i <= (n - 2); ++i)
        {
            BigInteger subans = BigInteger.ZERO;
            for (int j = 0; j <= k; ++j)
            {
                subans = subans.add(choose((n - i - 1) * (n - i - 2) / 2, j).multiply(helper(i + 1, k - j)));
            }

            ans = ans.subtract(choose(n - 1, i).multiply(subans));
        }

        memoizeAnswers[n][k] = ans;

        return memoizeAnswers[n][k];
    }

    public static String answer(int N, int K)
    {
        return helper(N, K).toString();
    }

    public static void main(String[] args)
    {
        System.out.println(answer(2,1));
        System.out.println(answer(4,3));
        System.out.println(answer(4,1));
        System.out.println(answer(4,4));
        System.out.println(answer(4,5));
        System.out.println(answer(4,6));
        System.out.println(answer(40,100));
    }
}
