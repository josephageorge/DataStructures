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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();
        HashMap<String, Boolean> map3 = new HashMap<String, Boolean>();

        int n = StdIn.readInt(); 

        for (int i = 0; i<n; i++){
            String s = StdIn.readString();
            ArrayList<String> list = new ArrayList<String>();
            map.put(s, list);
            map2.put(s, false);
            map3.put(s, false);

        }

        int edges = StdIn.readInt();
        for (int i = 0; i<edges; i++){
            String course = StdIn.readString();
            String preReq = StdIn.readString(); 
            map.get(course).add(preReq);
        }
            StdIn.setFile(args[1]);
            StdOut.setFile(args[2]);
            String desiredCourse = StdIn.readString();
            int numCoursesTaken = StdIn.readInt();

            HashSet<String> completedCourses = new HashSet<String>();
            for (int i =0; i<numCoursesTaken; i++){
            String completed = StdIn.readString();
            for ( String s : map.get(completed)){
                DFS2(s, completedCourses, map, map2);
            }
            completedCourses.add(completed);

        }
    
            HashSet<String> allPreReqsForDesired = new HashSet<>();
            DFS(desiredCourse, allPreReqsForDesired, map, map3);
            

            Set<String> allPreReqsForDesiredSet = allPreReqsForDesired;
            Set<String> completedCoursesSet = completedCourses;
            for (String s : allPreReqsForDesiredSet){
                if (!completedCoursesSet.contains(s)){
                    StdOut.println(s);
                }
            }



    }

   public static void DFS(String course, HashSet<String> allPreReqs, HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> map3){
        if (map3.get(course)==true) return;
        for (String s : map.get(course)){
            if (map3.get(s)==false){
                allPreReqs.add(s);
                DFS(s, allPreReqs, map, map3);
            }
            map3.replace(course, true);
        }

    }




    public static void DFS2(String course, HashSet<String> allPreReqs, HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> map2){
        if (map2.get(course)==true) return;
        allPreReqs.add(course);
        for (String s : map.get(course)){
            if (map2.get(s)==false){
                allPreReqs.add(s);
                DFS2(s, allPreReqs, map, map2);
            }
        }
        map2.replace(course, true);

    }
}