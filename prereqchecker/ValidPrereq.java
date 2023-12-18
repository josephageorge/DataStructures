package prereqchecker;

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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	
        StdIn.setFile(args[0]);
        StdOut.setFile(args[2]);

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();

        int n = StdIn.readInt(); 
        String[] allCourses = new String[n];


        for (int i = 0; i<n; i++){
            String s = StdIn.readString();
            ArrayList<String> list = new ArrayList<String>();
            map.put(s, list);
            map2.put(s, false);
            allCourses[i] = s;
        }

        int edges = StdIn.readInt();
        for (int i = 0; i<edges; i++){
            String course = StdIn.readString();
            String preReq = StdIn.readString(); 
            map.get(course).add(preReq);
        }


        StdIn.setFile(args[1]);

        String course1 = StdIn.readString();
        String course2 = StdIn.readString();

        HashSet<String> allPreReqs = new HashSet<String>();
        DFS(course2, allPreReqs, map, map2);

        if (allPreReqs.contains(course1)){
            StdOut.println("NO");
        } else {
            StdOut.println("YES");
        }
        
    }

    public static void DFS(String course, HashSet<String> allPreReqs, HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> map2){
        if (map.get(course).size()==0) return;

        for (String s : map.get(course)){
            if (map2.get(s)==false){
                allPreReqs.add(s);
                DFS(s, allPreReqs, map, map2);
            }
        }
        map2.replace(course, true);

    }
}