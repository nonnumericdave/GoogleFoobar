import java.lang.*;

public class Challenge01
{
    public static String answer(String str)
    {
        StringBuilder ans = new StringBuilder(str.length());

        int additionCount = 0;
        int multiplyCount = 0;
        for (int i = 0; i < str.length(); ++i)
        {
            char c = str.charAt(i);
            switch ( c )
            {
            case '+':
                ++additionCount;

                for ( ; multiplyCount > 0; --multiplyCount )
                    ans.append('*');

                break;

            case '*':
                ++multiplyCount;
                break;

            default:
                ans.append(c);
                break;
            }
        }

        while ( multiplyCount-- > 0 )
            ans.append('*');

        while ( additionCount-- > 0 )
            ans.append('+');

        return ans.toString();
    }

    public static void main(String [] args)
    {
        System.out.print(answer(args[0]));
    }
}
