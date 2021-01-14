import java.io.*;
import java.util.*;

public class socdist2
{
    /**
     * Represent a cow's position and whether it is sick. Make cows comparable
     * by position.
     */
    static class Cow implements Comparable<Cow>
    {
        final int position;
        final boolean sick;

        Cow(int position, boolean sick)
        {
            this.position = position;
            this.sick = sick;
        }

        @Override
        public int compareTo(Cow cow)
        {
            return Integer.compare(position, cow.position);
        }
    }

    /**
     * Return the maximum possible infection radius, or a very large integer
     * if it cannot be determined.
     */
    static int maxInfectionRadius(Cow[] cows)
    {
        int maxRadius = 987654321;

        // Compare each pair of adjacent cows.
        for (int i = 0; i + 1 < cows.length; i++)
        {
            Cow a = cows[i];
            Cow b = cows[i + 1];

            // Is one sick and the other healthy?
            if (a.sick != b.sick)
            {
                // The sick cow did not infect the healthy cow, so the infection
                // radius must be less than the distance between these cows.
                int distance = b.position - a.position;
                maxRadius = Math.min(maxRadius, distance - 1);
            }
        }

        return maxRadius;
    }

    /**
     * Return the smallest possible number of initially infected cows.
     */
    static int minInitiallyInfected(Cow[] cows)
    {
        /*
        If the distance between two cows is closer than the infection radius,
        then the infection is able to spread from one cow to the other. Consider
        a separation of the cows into groups based on whether they can infect
        each other: cows in the same group can infect each other, cows in
        different groups cannot.

        Since the disease is no longer spreading, either all of the cows in a
        group are infected, or none of the cows in a group are infected. If a
        group of cows is infected, then there must have been at least one cow
        in the group which was initially infected. Since the desired result is
        the minimum possible number of initially infected cows, assume that only
        one cow in the group was initially infected.

        Since it does not matter which cow in the group was initially infected,
        assume that it was the leftmost cow. Using the assumptions made so far,
        a cow was initially infected if:

        - The cow is currently infected, indicating that its group is infected
        - The cow to its left is farther than the infection radius, or there is
          no cow to its left

        This means that the minimum number of initially infected cows is equal
        to the number of cows satisfying the requirements above.
        */

        // Use the largest infection radius which is possible for the input.
        int radius = maxInfectionRadius(cows);

        int initialInfected = 0;

        for (int i = 0; i < cows.length; i++)
        {
            Cow current = cows[i];
            if (current.sick)
            {
                if (i == 0 || current.position - cows[i - 1].position > radius)
                {
                    initialInfected++;
                }
            }
        }

        return initialInfected;
    }

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("socdist2.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("socdist2.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        int numCows = scanner.nextInt();

        Cow[] cows = new Cow[numCows];
        for (int i = 0; i < numCows; i++)
        {
            cows[i] = new Cow(scanner.nextInt(), scanner.nextInt() == 1);
        }

        // Sort the cows by position.
        Arrays.sort(cows);

        // Output the minimum possible number of initially infected cows.
        out.println(minInitiallyInfected(cows));
    }
}
