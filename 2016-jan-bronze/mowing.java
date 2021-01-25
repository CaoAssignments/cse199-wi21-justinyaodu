import java.io.*;
import java.util.*;

public class mowing
{
    // Maximum distance FJ can travel from his starting position.
    static final int MAX_DIST = 100 * 10;

    static final int INFINITY = 987654321;

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("mowing.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("mowing.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        // Store the last time that each cell was visited, or -1 if it was never
        // visited before. The grid has to be large enough to represent every
        // cell that FJ can reach, even if he uses the maximum number of moves
        // and only goes in one direction.
        int[][] lastSeen = new int[2 * MAX_DIST + 1][2 * MAX_DIST + 1];
        for (int i = 0; i < lastSeen.length; i++)
        {
            Arrays.fill(lastSeen[i], -1);
        }

        // FJ starts in the middle of the grid.
        int posX = MAX_DIST;
        int posY = MAX_DIST;

        // The current time.
        int t = 0;

        // The maximum possible grass regrowth time.
        int maxValidX = INFINITY;

        int numLines = scanner.nextInt();
        for (int i = 0; i < numLines; i++)
        {
            String direction = scanner.next();
            int distance = scanner.nextInt();

            for (int j = 0; j < distance; j++)
            {
                // If this cell was visited before, x must be no greater than
                // the difference between the current time and the time of the
                // last visit.
                if (lastSeen[posX][posY] >= 0)
                {
                    maxValidX = Math.min(maxValidX, t - lastSeen[posX][posY]);
                }

                // Visit this cell.
                lastSeen[posX][posY] = t;
                t++;

                // Update FJ's position.
                switch (direction)
                {
                    case "N":
                        posY++;
                        break;
                    case "E":
                        posX++;
                        break;
                    case "S":
                        posY--;
                        break;
                    case "W":
                        posX--;
                        break;
                }
            }
        }

        out.println(maxValidX == INFINITY ? -1 : maxValidX);
    }
}
