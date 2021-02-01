import java.io.*;
import java.util.*;

public class proximity
{
    static final int MAX_BREED_ID = 1000000;

    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("proximity.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("proximity.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        int[] cows = new int[N];
        for (int i = 0; i < N; i++)
        {
            cows[i] = scanner.nextInt();
        }

        int maxCrowdedBreed = -1;

        int[] lastSeen = new int[MAX_BREED_ID + 1];
        Arrays.fill(lastSeen, -1);

        for (int i = 0; i < N; i++)
        {
            if (lastSeen[cows[i]] != -1 && i - lastSeen[cows[i]] <= K)
            {
                maxCrowdedBreed = Math.max(maxCrowdedBreed, cows[i]);
            }

            lastSeen[cows[i]] = i;
        }

        out.println(maxCrowdedBreed);
    }
}
