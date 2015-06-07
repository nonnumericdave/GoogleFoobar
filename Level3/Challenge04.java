import java.util.*;
import java.lang.*;

public class Challenge04
{
    static int answer(int s, int x)
    {
        int count = 0;
        for (int a = 0; a <= s; ++a)
        {
            if ( x == (a ^ (s - a)) )
                count++;
        }

        return count;
    }

    public static void main(String[] args)
    {
        Integer count = answer(0, 0);
        System.out.println(count.toString());
    }
}
