import java.io.*;
import java.util.*;

public class tracing
{
    static int numCows;
    static int numShakes;

    // Whether each cow was observed to be sick.
    static boolean[] actualSick;

    // Hoof shakes, sorted by time.
    static Shake[] shakes;

    /**
     * Represent a hoof shake between two cows. Make hoof shakes comparable by
     * the time at which they occurred.
     */
    static class Shake implements Comparable<Shake>
    {
        final int time;
        final int[] cows;

        Shake(int time, int firstCow, int secondCow)
        {
            this.time = time;
            this.cows = new int[]{firstCow, secondCow};
        }

        @Override
        public int compareTo(Shake shake)
        {
            return Integer.compare(time, shake.time);
        }
    }

    /**
     * Simulate the disease spread for a given patient zero and value of K.
     */
    static class Simulation
    {
        boolean[] simulatedSick;
        int[] sickShakesByCow;
        int k;

        Simulation(int patientZero, int k)
        {
            this.simulatedSick = new boolean[numCows];
            this.simulatedSick[patientZero] = true;

            this.sickShakesByCow = new int[numCows];

            this.k = k;
        }

        /**
         * Run the simulation and return whether it matches the actual data.
         */
        boolean simulate()
        {
            for (Shake shake : shakes)
            {
                doShake(shake);
            }

            return Arrays.equals(simulatedSick, actualSick);
        }

        /**
         * Simulate a hoof shake between two cows.
         */
        void doShake(Shake shake)
        {
            for (int cow : shake.cows)
            {
                // Is this cow sick?
                if (simulatedSick[cow])
                {
                    // Update the number of hoof shakes made while sick.
                    sickShakesByCow[cow]++;
                }
            }

            for (int i = 0; i < shake.cows.length; i++)
            {
                int cow = shake.cows[i];
                int other = shake.cows[1 - i];

                // Is this cow sick and within its first K shakes?
                if (simulatedSick[cow] && sickShakesByCow[cow] <= k)
                {
                    // Infect the other cow.
                    simulatedSick[other] = true;
                }
            }
        }
    }

    /**
     * Simulate the spread of infection for all possible starting cows and
     * values of K, and output the results.
     */
    static void simulateAll(PrintStream out)
    {
        // Set of cows that could be patient zero.
        Set<Integer> couldBePatientZero = new HashSet<>();

        // Minimum and maximum possible values of K. It is guaranteed that there
        // will be at least one valid value, so these placeholder values will
        // always be replaced.
        int minK = 987654321;
        int maxK = -987654321;

        // Iterate over all possible patient zeroes.
        for (int patientZero = 0; patientZero < numCows; patientZero++)
        {
            // Iterate over all values of K that need to be considered. Since a
            // cow cannot shake hooves more than the total number of hoof
            // shakes, values of K larger than that do not need to be
            // considered. (It is also valid to use the maximum number of hoof
            // shakes done by any individual cow, but that optimization is not
            // necessary to run in time.)
            for (int k = 0; k <= numShakes; k++)
            {
                Simulation simulation = new Simulation(patientZero, k);
                if (simulation.simulate())
                {
                    couldBePatientZero.add(patientZero);
                    minK = Math.min(minK, k);
                    maxK = Math.max(maxK, k);
                }
            }
        }

        // If K is larger than the total number of hoof shakes, then it behaves
        // the same as if K was the number of hoof shakes. Thus, if the number
        // of hoof shakes is a valid value of K for some patient zero, then K
        // can be arbitrarily large for that choice of patient zero.
        String maxKString;
        if (maxK == numShakes)
        {
            maxKString = "Infinity";
        }
        else
        {
            maxKString = "" + maxK;
        }

        out.printf("%d %d %s\n", couldBePatientZero.size(), minK, maxKString);
    }

    /**
     * Parse whether each cow is sick.
     */
    static void parseActualSick(Scanner scanner)
    {
        String line = scanner.next();
        actualSick = new boolean[line.length()];

        for (int i = 0; i < actualSick.length; i++)
        {
            actualSick[i] = line.charAt(i) == '1';
        }
    }

    /**
     * Parse the hoof shakes between cows.
     */
    static void parseShakes(Scanner scanner)
    {
        shakes = new Shake[numShakes];

        for (int i = 0; i < numShakes; i++)
        {
            int time = scanner.nextInt();

            // Renumber the cows from the range [1, N] to the range [0, N-1], so
            // that the cow numbers can be used directly as array indices.
            int firstCow = scanner.nextInt() - 1;
            int secondCow = scanner.nextInt() - 1;

            shakes[i] = new Shake(time, firstCow, secondCow);
        }

        // Sort the hoof shakes by when they occurred.
        Arrays.sort(shakes);
    }

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("tracing.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("tracing.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        numCows = scanner.nextInt();
        numShakes = scanner.nextInt();

        parseActualSick(scanner);
        parseShakes(scanner);

        simulateAll(out);
    }
}
