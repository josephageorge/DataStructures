package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
    

        StdIn.setFile(args[0]);
        
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();

        int n = StdIn.readInt(); 
        String[] arr = new String[n];


        for (int i = 0; i<n; i++){
            String s = StdIn.readString();
            ArrayList<String> list = new ArrayList<String>();
            map.put(s, list);
            map2.put(s, false);
            arr[i] = s;
        }

        int edges = StdIn.readInt();
        for (int i = 0; i<edges; i++){
            String course = StdIn.readString();
            String preReq = StdIn.readString(); 
            map.get(course).add(preReq);
        }

        StdIn.setFile(args[1]);
        StdOut.setFile(args[2]);

        int c = StdIn.readInt(); 
        HashSet<String> completedCourses = new HashSet<String>();
        for (int i =0; i<c; i++){
            String completed = StdIn.readString();
            for ( String s : map.get(completed)){   
                DFS2(s, completedCourses, map, map2);
            }
            completedCourses.add(completed);

        }

       
    
        Queue<String> canTake = new LinkedList<>();
        HashSet<String> checkDupe = new HashSet<String>();

        for (int i =0; i<n; i++){
            DFS(map, map2, arr[i], canTake, completedCourses);
        }
        

        while(!canTake.isEmpty()){
            String s = canTake.remove();
            if (!checkDupe.contains(s)){
                StdOut.println(s);
            }
            checkDupe.add(s);

        }

    }

       public static void DFS(HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> map2, String course, Queue<String> canTake, HashSet<String> completedCourses){
            if (map2.get(course)==true){ return; }
            boolean check = true; 
            for (String s : map.get(course)){
                if (map2.get(s)==false){
                    DFS(map, map2, s, canTake, completedCourses);
                }

                if (!completedCourses.contains(s)){
                    check=false;
                }  
                map2.replace(course, true);
            }
            if (check==true&&!completedCourses.contains(course)){
                canTake.add(course);
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




