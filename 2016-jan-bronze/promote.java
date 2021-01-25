import java.io.*;
import java.util.*;

public class promote
{
    public static void main(String[] args) throws IOException
    {
        boolean useFileIO = true;

        InputStream in = useFileIO
                ? new FileInputStream("promote.in")
                : System.in;
        PrintStream out = useFileIO
                ? new PrintStream("promote.out")
                : System.out;

        Scanner scanner = new Scanner(in);

        int bronzeBefore = scanner.nextInt();
        int bronzeAfter = scanner.nextInt();
        int silverBefore = scanner.nextInt();
        int silverAfter = scanner.nextInt();
        int goldBefore = scanner.nextInt();
        int goldAfter = scanner.nextInt();
        int platinumBefore = scanner.nextInt();
        int platinumAfter = scanner.nextInt();

        /*
         * Consider any division D. If there are X participants above D before
         * the most recent contest, then those X participants passed division D
         * before the most recent contest. If there are Y participants above D
         * after the most recent contest, then Y - X of those participants
         * passed division D in the most recent contest. This means that Y - X
         * participants were promoted to the division after D.
         */

        int aboveGoldBefore = platinumBefore;
        int aboveGoldAfter = platinumAfter;
        int aboveSilverBefore = goldBefore + aboveGoldBefore;
        int aboveSilverAfter = goldAfter + aboveGoldAfter;
        int aboveBronzeBefore = silverBefore + aboveSilverBefore;
        int aboveBronzeAfter = silverAfter + aboveSilverAfter;

        out.println(aboveBronzeAfter - aboveBronzeBefore);
        out.println(aboveSilverAfter - aboveSilverBefore);
        out.println(aboveGoldAfter - aboveGoldBefore);
    }
}
