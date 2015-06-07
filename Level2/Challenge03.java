import java.util.*;
import java.lang.*;

public class Challenge03
{
    public static int answer(String[] x)
    {
        Set<String> set = new HashSet<String>();
        for (String str : x )
        {
            String strReverse = new StringBuilder(str).reverse().toString();
            if ( str.compareTo(strReverse) > 0 )
                str = strReverse;

            set.add(str);
        }

        return set.size();
    }

    public static void main(String[] args)
    {
        String[] strs = {"x", "y", "xy", "yy", "", "yx"}; //{"foo", "bar", "oof", "bar"};
        Integer count = answer(strs);
        System.out.println(count.toString());
    }
}
