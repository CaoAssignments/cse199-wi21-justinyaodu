import java.io.*;
import java.util.*;

public class haywire
{
    static final int INFINITY = 987654321;
    static final int CONNECTIONS_PER_COW = 3;

    static int N;

    // Whether two cows are friends.
    static boolean[][] friends;

    // Total number of connections between cows.
    static int totalWires;

    // Memoize the minimum cost for each subset of cows.
    static int[] minCostMemo;

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("haywire.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("haywire.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        N = scanner.nextInt();

        totalWires = N * CONNECTIONS_PER_COW / 2;

        minCostMemo = new int[1 << N];
        Arrays.fill(minCostMemo, INFINITY);
        minCostMemo[0] = 0;

        friends = new boolean[N][N];
        for (int a = 0; a < N; a++)
        {
            for (int i = 0; i < CONNECTIONS_PER_COW; i++)
            {
                int b = scanner.nextInt() - 1;
                friends[a][b] = true;
            }
        }

        int allCows = (1 << N) - 1;
        out.println(findMinCost(allCows));
    }

    static int findMinCost(int cows)
    {
        if (minCostMemo[cows] != INFINITY)
        {
            return minCostMemo[cows];
        }

        int minCost = INFINITY;

        // Find the minimum cost of the current set of cows by checking what
        // happens when each cow is added last.
        for (int i = 0; i < N; i++)
        {
            int testCow = 1 << i;
            if ((cows & testCow) == 0) continue;

            int newCost = findMinCost(cows ^ testCow) + wiresUnfinished(cows);
            minCost = Math.min(minCost, newCost);
        }

        return minCostMemo[cows] = minCost;
    }

    static int wiresUnfinished(int cows)
    {
        return totalWires
                - wiresBetween(cows)
                - wiresBetween(~cows);
    }

    static int wiresBetween(int cows)
    {
        int wires = 0;
        for (int i = 0; i < N; i++)
        {
            if ((cows & (1 << i)) == 0) continue;

            for (int j = i + 1; j < N; j++)
            {
                if ((cows & (1 << j)) == 0) continue;

                if (friends[i][j])
                {
                    wires++;
                }
            }
        }
        return wires;
    }
}
