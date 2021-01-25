import java.io.*;
import java.util.*;

public class angry
{
    /**
     * Return the number of haybales exploded by a cow launch.
     * @param bales The array of haybale positions.
     * @param initial The index of the initial haybale to launch a cow at.
     * @return The total number of haybales exploded.
     */
    private static int explode(int[] bales, int initial)
    {
        int rightBale = initial;
        for (int radius = 1; ; radius++)
        {
            // Find the index of the rightmost haybale reached by this set of
            // explosions.
            int nextRightBale = rightBale;
            while (nextRightBale + 1 < bales.length
                    && bales[nextRightBale + 1] - bales[rightBale] <= radius)
            {
                nextRightBale++;
            }

            if (nextRightBale == rightBale)
            {
                // Cannot reach any more haybales.
                break;
            }
            else
            {
                rightBale = nextRightBale;
            }
        }

        int leftBale = initial;
        for (int radius = 1; ; radius++)
        {
            int nextLeftBale = leftBale;
            while (nextLeftBale - 1 >= 0
                    && bales[leftBale] - bales[nextLeftBale - 1] <= radius)
            {
                nextLeftBale--;
            }

            if (nextLeftBale == leftBale)
            {
                break;
            }
            else
            {
                leftBale = nextLeftBale;
            }
        }

        return rightBale - leftBale + 1;
    }

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("angry.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("angry.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        int N = scanner.nextInt();

        // Read and sort the haybale positions.
        int[] bales = new int[N];
        for (int i = 0; i < N; i++)
        {
            bales[i] = scanner.nextInt();
        }
        Arrays.sort(bales);

        // Try shooting the cow at each haybale, taking the maximum.
        int maxBales = 0;
        for (int i = 0; i < N; i++)
        {
            maxBales = Math.max(maxBales, explode(bales, i));
        }

        out.println(maxBales);
    }
}
