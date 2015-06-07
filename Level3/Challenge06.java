import java.util.*;
import java.lang.*;

public class Challenge06
{
    public static String answer(String chunk, String word)
    {
        StringBuilder candidateString = new StringBuilder(chunk);
        int candidateLength = candidateString.length();

        LinkedList<StringBuilder> workingSet = new LinkedList<StringBuilder>();
        HashSet<String> hashWorkingSet = new HashSet<String>();

        workingSet.add(candidateString);
        hashWorkingSet.add(candidateString.toString());

        int wordLength = word.length();

        int workingStringThreshold = candidateLength;

        while ( ! workingSet.isEmpty() )
        {
            StringBuilder workingString = workingSet.removeFirst();
            hashWorkingSet.remove(workingString.toString());

            int newWorkingLength = workingString.length() - wordLength;
            if ( newWorkingLength > workingStringThreshold )
                continue;

            workingStringThreshold = newWorkingLength;

            System.out.println(workingString.toString());

            int workingSetSize = workingSet.size();
            int startIndex = 0;
            for (startIndex = workingString.indexOf(word, startIndex);
                    startIndex != -1;
                    startIndex = workingString.indexOf(word, ++startIndex))
            {
                StringBuilder newWorkingString = new StringBuilder(workingString);
                newWorkingString.delete(startIndex, startIndex + wordLength);

                if ( hashWorkingSet.add(newWorkingString.toString()) )
                    workingSet.add(newWorkingString);
            }

            if ( workingSetSize == workingSet.size() )
            {
                int workingLength = workingString.length();

                if ( workingLength < candidateLength )
                {
                    candidateString = workingString;
                    candidateLength = workingLength;
                }
                else if ( workingLength == candidateLength )
                {
                    for (int i = 0; i < workingLength; ++i)
                    {
                        char workingChar = workingString.charAt(i);
                        char candidateChar = candidateString.charAt(i);
                        if ( workingChar < candidateChar )
                        {
                            candidateString = workingString;
                            candidateLength = workingLength;
                            break;
                        }
                        else if ( candidateChar < workingChar )
                        {
                            break;
                        }
                    }
                }
            }
        }

        return candidateString.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(answer("lololololo", "lol"));
    }
}
