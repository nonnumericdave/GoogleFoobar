import java.util.*;
import java.lang.*;
import java.math.*;

public class Challenge05
{
    public static String answer(int x, int y, int z)
    {
        boolean[] numbersToExamine = new boolean[10];
        BigInteger[] pathsToNumbers = new BigInteger[10];
        for (int i = 0; i < numbersToExamine.length; ++i)
        {
            numbersToExamine[i] = false;
            pathsToNumbers[i] = BigInteger.valueOf(0);
        }

        int length = 1;
        numbersToExamine[x] = true;
        pathsToNumbers[x] = BigInteger.valueOf(1);

        boolean[] nextNumbersToExamine = new boolean[10];
        BigInteger[] nextPathsToNumbers = new BigInteger[10];

        while ( length++ < z )
        {
            for (int i = 0; i < nextNumbersToExamine.length; ++i)
            {
                nextNumbersToExamine[i] = false;
                nextPathsToNumbers[i] = BigInteger.valueOf(0);
            }

            for (int i = 0; i < numbersToExamine.length; ++i)
            {
                if ( numbersToExamine[i] )
                {
                    BigInteger pathsToNumber = pathsToNumbers[i];

                    switch ( i )
                    {
                    case 0:
                        nextNumbersToExamine[4] = true;
                        nextNumbersToExamine[6] = true;

                        nextPathsToNumbers[4] = nextPathsToNumbers[4].add(pathsToNumber);
                        nextPathsToNumbers[6] = nextPathsToNumbers[6].add(pathsToNumber);
                        break;

                    case 1:
                        nextNumbersToExamine[6] = true;
                        nextNumbersToExamine[8] = true;

                        nextPathsToNumbers[6] = nextPathsToNumbers[6].add(pathsToNumber);
                        nextPathsToNumbers[8] = nextPathsToNumbers[8].add(pathsToNumber);
                        break;

                    case 2:
                        nextNumbersToExamine[7] = true;
                        nextNumbersToExamine[9] = true;

                        nextPathsToNumbers[7] = nextPathsToNumbers[7].add(pathsToNumber);
                        nextPathsToNumbers[9] = nextPathsToNumbers[9].add(pathsToNumber);
                        break;

                    case 3:
                        nextNumbersToExamine[4] = true;
                        nextNumbersToExamine[8] = true;

                        nextPathsToNumbers[4] = nextPathsToNumbers[4].add(pathsToNumber);
                        nextPathsToNumbers[8] = nextPathsToNumbers[8].add(pathsToNumber);
                        break;

                    case 4:
                        nextNumbersToExamine[3] = true;
                        nextNumbersToExamine[9] = true;
                        nextNumbersToExamine[0] = true;

                        nextPathsToNumbers[3] = nextPathsToNumbers[3].add(pathsToNumber);
                        nextPathsToNumbers[9] = nextPathsToNumbers[9].add(pathsToNumber);
                        nextPathsToNumbers[0] = nextPathsToNumbers[0].add(pathsToNumber);
                        break;

                    case 5:
                        break;

                    case 6:
                        nextNumbersToExamine[1] = true;
                        nextNumbersToExamine[7] = true;
                        nextNumbersToExamine[0] = true;

                        nextPathsToNumbers[1] = nextPathsToNumbers[1].add(pathsToNumber);
                        nextPathsToNumbers[7] = nextPathsToNumbers[7].add(pathsToNumber);
                        nextPathsToNumbers[0] = nextPathsToNumbers[0].add(pathsToNumber);
                        break;

                    case 7:
                        nextNumbersToExamine[2] = true;
                        nextNumbersToExamine[6] = true;

                        nextPathsToNumbers[2] = nextPathsToNumbers[2].add(pathsToNumber);
                        nextPathsToNumbers[6] = nextPathsToNumbers[6].add(pathsToNumber);
                        break;

                    case 8:
                        nextNumbersToExamine[1] = true;
                        nextNumbersToExamine[3] = true;

                        nextPathsToNumbers[1] = nextPathsToNumbers[1].add(pathsToNumber);
                        nextPathsToNumbers[3] = nextPathsToNumbers[3].add(pathsToNumber);
                        break;

                    case 9:
                        nextNumbersToExamine[2] = true;
                        nextNumbersToExamine[4] = true;

                        nextPathsToNumbers[2] = nextPathsToNumbers[2].add(pathsToNumber);
                        nextPathsToNumbers[4] = nextPathsToNumbers[4].add(pathsToNumber);
                        break;

                    default:
                        break;
                    }
                }
            }

            boolean[] tempNumbersToExamine = numbersToExamine;
            BigInteger[] tempPathsToNumbers = pathsToNumbers;

            numbersToExamine = nextNumbersToExamine;
            pathsToNumbers = nextPathsToNumbers;

            nextNumbersToExamine = tempNumbersToExamine;
            nextPathsToNumbers = tempPathsToNumbers;
        }

        return pathsToNumbers[y].toString();
    }

    public static void main(String[] args)
    {
        String count = answer(1,5,100);
        System.out.println(count);
    }
}
