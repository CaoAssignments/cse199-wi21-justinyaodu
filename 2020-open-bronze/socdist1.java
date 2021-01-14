import java.io.*;
import java.util.*;

public class socdist1
{
    /**
     * Return the best (largest) gap from the provided values, or -1 if no
     * values are provided.
     */
    static int bestOf(int... gaps)
    {
        int best = -1;
        for (int gap : gaps)
        {
            best = Math.max(best, gap);
        }
        return best;
    }

    /**
     * Return the worst (smallest) gap in the provided values, or a very large
     * integer if no values are provided.
     */
    static int worstOf(int... gaps)
    {
        int worst = 987654321;
        for (int gap : gaps)
        {
            worst = Math.min(worst, gap);
        }
        return worst;
    }

    /**
     * Return the largest and second largest gaps, substituting -1 if there are
     * not enough values.
     */
    static int[] twoLargest(int[] gaps)
    {
        // This implementation runs in O(n) time by using a linear sweep.
        // However, since there are only up to 10^5 elements, sorting the array
        // and picking the last two elements would also be fast enough.

        int largest = -1;
        int secondLargest = -1;

        for (int gap : gaps)
        {
            if (gap > secondLargest)
            {
                // Update the second largest.
                secondLargest = gap;

                // Swap the largest and second largest, if necessary.
                if (secondLargest > largest)
                {
                    int temp = largest;
                    largest = secondLargest;
                    secondLargest = temp;
                }
            }
        }

        return new int[]{largest, secondLargest};
    }

    /**
     * Return the minimum possible distance between neighboring cows after
     * adding two cows to the barn.
     */
    static int minDist(int[] gaps)
    {
        if (gaps.length == 1)
        {
            // The barn is empty. Place one cow in the leftmost stall and the
            // other cow in the rightmost stall. Return the distance between
            // those stalls.
            return gaps[0] - 1;
        }

        // Get the gaps at the ends of the barn.
        int leftGap = gaps[0];
        int rightGap = gaps[gaps.length - 1];

        // Get the gaps between cows.
        int[] innerGaps = new int[gaps.length - 2];
        for (int i = 0; i < innerGaps.length; i++)
        {
            innerGaps[i] = gaps[i + 1];
        }

        // Try placing a new cow between the end of the barn and another cow.
        // Place it at the end of the barn.
        int oneLeft = leftGap - 1;
        int oneRight = rightGap - 1;

        // Try placing both cows between one end of the barn and another cow.
        // Place one at the end of the barn, and the other halfway.
        int twoLeft = (leftGap - 2) / 2;
        int twoRight = (rightGap - 2) / 2;

        // Find the largest and second largest gaps between cows. It is never
        // optimal to place the new cows in any smaller gap between cows.
        int[] twoLargestInnerGaps = twoLargest(innerGaps);
        int largestInnerGap = twoLargestInnerGaps[0];
        int secondLargestInnerGap = twoLargestInnerGaps[1];

        // Try placing only one cow between two cows. Place it halfway.
        int oneInner = (largestInnerGap - 1) / 2;

        // Try placing both cows between existing cows, in the same gap. Place
        // them to divide the existing gap into thirds.
        int twoInnerTogether = (largestInnerGap - 2) / 3;

        // Try placing both cows between existing cows, but in different gaps.
        // Place one cow halfway in the largest inner gap, and the other cow
        // halfway in the second largest inner gap. The first cow's new gap size
        // will never be smaller than the second cow's new gap size, so it can
        // be ignored.
        int twoInnerSeparate = (secondLargestInnerGap - 1) / 2;

        // Find the combination of the above options which maximizes the
        // minimum distance between the new cows and their neighbors.
        int minNewGap = bestOf(
            // Both cows in the leftmost gap.
            twoLeft,
            // Both cows in the rightmost gap.
            twoRight,
            // One cow in the leftmost gap, and the other cow in the
            // rightmost gap.
            worstOf(oneLeft, oneRight),
            // Both cows in the same gap between previous cows.
            twoInnerTogether,
            // Both cows in different gaps between previous cows.
            twoInnerSeparate,
            // One cow in a gap between previous cows, and the other cow
            // in the leftmost or rightmost gap.
            worstOf(bestOf(oneLeft, oneRight), oneInner)
        );

        // Account for gaps between the cows that were already in the barn.
        int minGap = Math.min(minNewGap, worstOf(innerGaps));

        // Add 1 to convert the gap size into the distance between stalls.
        return minGap + 1;
    }

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("socdist1.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("socdist1.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        // Read and discard the number of stalls, since it is not needed.
        scanner.nextInt();

        // Read the string describing the stalls.
        String stalls = scanner.next();

        // Split the string at each occupied stall to get strings of 0s
        // describing the "gaps" of unoccupied stalls. This includes empty gaps
        // between cows, and also the gaps before the leftmost cow and after the
        // rightmost cow.
        String[] zeroStrings = stalls.split("1", -1);

        // Create an array containing the size of each gap.
        int[] gaps = new int[zeroStrings.length];
        for (int i = 0; i < gaps.length; i++)
        {
            gaps[i] = zeroStrings[i].length();
        }

        // Output the minimum distance between cows after placing the new cows
        // optimally.
        out.println(minDist(gaps));
    }
}
