import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge11
{
    public static int answer(int[] L, int k)
    {
        int[] currentFirstValue = new int[k];
        int[] currentSum = new int[k];
        int[] currentMax = new int[k];

        currentFirstValue[0] = L[0];
        currentSum[0] = L[0];

        for (int i = 1; i < k; ++i)
        {
            currentFirstValue[i] = L[0];
            currentSum[i] = currentSum[i - 1] + L[i];
            currentMax[i] = currentSum[i];
        }

        for (int i = 1; i < L.length; ++i)
        {
            for (int j = 0; j < k; ++j)
            {
                int index = i + j;
                if ( index < L.length )
                {
                    currentSum[j] = currentSum[j] - currentFirstValue[j] + L[index];
                    if ( currentSum[j] > currentMax[j] )
                        currentMax[j] = currentSum[j];

                    currentFirstValue[j] = L[i];
                }
            }
        }

        int max = currentMax[0];
        for (int i = 1; i < k; ++i)
        {
            if ( currentMax[i] > max )
                max = currentMax[i];
        }

        return max;
    }

    public static void main(String[] args)
    {
        System.out.println("" + answer(new int[] {-100, 95, 86, 47}, 3));
        System.out.println("" + answer(new int[] {40, 91, -68, -36, 24, -67, -32, -23, -33, -52}, 7));
    }
}
