import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge12
{
    private static BigInteger[] memoizeFactorial = new BigInteger[64];

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

    private static BigInteger[] memoizeConnectedDirectedPseudotrees = new BigInteger[64];

    private static BigInteger connectedDirectedPseudotrees(int n)
    {
        if ( memoizeConnectedDirectedPseudotrees[n] != null )
            return memoizeConnectedDirectedPseudotrees[n];

        BigInteger ans = BigInteger.ZERO;
        for (int i = 1; i <= (n - 1); ++i)
            ans = ans.add(choose(n, i).multiply(BigInteger.valueOf(n - i).pow(n - i)).multiply(BigInteger.valueOf(i).pow(i)));

        memoizeConnectedDirectedPseudotrees[n] = ans.divide(BigInteger.valueOf(n));

        return memoizeConnectedDirectedPseudotrees[n];
    }

    private static interface PartitionVisitor<T>
    {
        public void visitPartition(int[] parts, int lastIndex);

        public T result();
    }

    private static void partition(int n, PartitionVisitor<BigInteger> partitionVisitor)
    {
        int[] parts = new int[n];
        int lastIndex = 0;
        parts[lastIndex] = n;

        for (;;)
        {
            if ( parts[lastIndex] != 1 )
                partitionVisitor.visitPartition(parts, lastIndex);

            int r = 0;
            while ( lastIndex >= 0 && parts[lastIndex] == 1 )
                r += parts[lastIndex--];

            if ( lastIndex < 0 )
                break;

            parts[lastIndex]--;
            r++;

            while ( r > parts[lastIndex] )
            {
                parts[lastIndex + 1] = parts[lastIndex];
                r -= parts[lastIndex++];
            }

            parts[lastIndex++ + 1] = r;
        }
    }

    private static BigInteger assignItemsToPartitionCombinations(int items, int[] parts, int lastIndex)
    {
        BigInteger ans = BigInteger.ONE;
        BigInteger reduction = BigInteger.ONE;

        int currentPartitionSize = 1;
        int currentPartitionSizeCount = 1;

        for (int i = 0; i <= lastIndex; ++i)
        {
            ans = ans.multiply(choose(items, parts[i]));
            items -= parts[i];

            if ( parts[i] != currentPartitionSize )
            {
                reduction = reduction.multiply(factorial(currentPartitionSizeCount));

                currentPartitionSize = parts[i];
                currentPartitionSizeCount = 1;
            }
            else
            {
                currentPartitionSizeCount++;
            }
        }

        reduction = reduction.multiply(factorial(currentPartitionSizeCount));

        return ans.divide(reduction);
    }

    public static String answer(int n)
    {
        final int fn = n;

        PartitionVisitor<BigInteger> partitionVisitor = new PartitionVisitor<BigInteger>()
        {
            public BigInteger ans = BigInteger.ZERO;

            public void visitPartition(int[] parts, int lastIndex)
            {
                BigInteger subans = assignItemsToPartitionCombinations(fn, parts, lastIndex);

                int largestPartitionSize = 1;
                for (int i = 0; i <= lastIndex; ++i)
                {
                    subans = subans.multiply(connectedDirectedPseudotrees(parts[i]));

                    if ( parts[i] > largestPartitionSize )
                        largestPartitionSize = parts[i];
                }

                ans = ans.add(subans.multiply(BigInteger.valueOf(largestPartitionSize)));
            }

            public BigInteger result()
            {
                return ans;
            }
        };

        partition(n, partitionVisitor);

        BigInteger directedForestCount = BigInteger.valueOf(n - 1).pow(n);

        BigInteger greatestCommonDivisor = partitionVisitor.result().gcd(directedForestCount);

        return partitionVisitor.result().divide(greatestCommonDivisor).toString() +
               "/" +
               directedForestCount.divide(greatestCommonDivisor).toString();
    }

    public static void main(String[] args)
    {
        System.out.println("expect: 106/27 got: " + answer(4));
        System.out.println("expect: 2/1 got: " + answer(2));
    }
}
