import java.io.*;
import java.util.*;

public class cowrace
{
    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("cowrace.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("cowrace.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        List<Integer> bessieSpeeds = new ArrayList<>();
        List<Integer> elsieSpeeds = new ArrayList<>();

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        for (int i = 0; i < N; i++)
        {
            int speed = scanner.nextInt();
            int time = scanner.nextInt();
            for (int j = 0; j < time; j++)
            {
                bessieSpeeds.add(speed);
            }
        }

        for (int i = 0; i < M; i++)
        {
            int speed = scanner.nextInt();
            int time = scanner.nextInt();
            for (int j = 0; j < time; j++)
            {
                elsieSpeeds.add(speed);
            }
        }

        int bessie = 0;
        int elsie = 0;
        int prevCompare = 0;
        int leadChanges = 0;

        for (int i = 0; i < bessieSpeeds.size(); i++)
        {
            bessie += bessieSpeeds.get(i);
            elsie += elsieSpeeds.get(i);
            int newCompare = Integer.compare(bessie, elsie);

            if (newCompare != 0)
            {
                if (prevCompare * newCompare < 0)
                {
                    leadChanges++;
                }
                prevCompare = newCompare;
            }
        }

        out.println(leadChanges);
    }
}
