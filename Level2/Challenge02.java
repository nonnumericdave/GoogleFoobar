import java.lang.*;
import java.util.*;

public class Challenge02
{
    public static int answer(int[][] intervals)
    {
        if ( intervals.length == 0 )
            return 0;

        Integer[][] boxedIntervals = new Integer[intervals.length][2];
        for (int i = 0; i < intervals.length; ++i)
        {
            boxedIntervals[i][0] = intervals[i][0];
            boxedIntervals[i][1] = intervals[i][1];
        }

        Arrays.sort(boxedIntervals, new Comparator<Integer[]>()
        {
            public int compare(Integer[] o1, Integer[] o2)
            {
                return o1[0].compareTo(o2[0]);
            }

            public boolean equals(Object obj)
            {
                return false;
            }
        });

        int total = 0;
        int curBeginInterval = 0;
        int curEndInterval = 0;
        for (int i = 0; i < boxedIntervals.length; ++i)
        {
            if ( boxedIntervals[i][0] > curEndInterval )
            {
                total += curEndInterval - curBeginInterval;

                curBeginInterval = boxedIntervals[i][0];
                curEndInterval = boxedIntervals[i][1];
            }
            else if ( curEndInterval < boxedIntervals[i][1] )
            {
                curEndInterval = boxedIntervals[i][1];
            }
        }

        total += curEndInterval - curBeginInterval;

        return total;
    }

    public static void main(String[] args)
    {
        int[][] intervals = {{1,3}, {3,6}}; //{{10,14}, {4,18}, {19,20}, {19, 20}, {13, 20}};
        Integer ans = answer(intervals);
        System.out.println(ans.toString());
    }
}
