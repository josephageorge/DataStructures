package prereqchecker;
import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        int n = StdIn.readInt(); 
        String[] courses = new String[n];

        for (int i = 0; i<n; i++){
                String b = StdIn.readString();
                ArrayList<String> list = new ArrayList<String>();
                map.put(b, list);
                courses[i] = b;
            }


        int surround = StdIn.readInt()
        for(i = 0; i <surround; i++) {
            String course = StdIn.readString();
            String preReq = StdIn.readString(); 
            map.get(course).add(preReq);

        }

        for(int i =0; i < n; i++) {
            StdOut.print(courses[i]);
            ArrayList<String> list = map.get(courses[i])
            for (String string : list){
                StdOut.print(" " + string);
            }
            StdOut.println()
        }

    }
}
