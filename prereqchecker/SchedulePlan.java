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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
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
    ArrayList<String> needToTakeArr = new ArrayList<String>();
    HashMap<String, Integer> courseSems = new HashMap<>();

    for (String s : allPreReqsForDesiredSet){
        if (!completedCoursesSet.contains(s)){
            needToTakeArr.add(s);
            map3.replace(s, false);
            courseSems.put(s, 1);
        }
    }

    for (String s : needToTakeArr){
        DFS3(s, map, map3, courseSems);
    }

    int maxSem = 1; 
    for (int sem : courseSems.values()){
        if (sem>maxSem) maxSem=sem;

    }

    StdOut.println(maxSem);
    for (int i = 1; i<maxSem+1; i++ ){
        for (String key : courseSems.keySet()){
            if (courseSems.get(key)==i){
                StdOut.print(key+" ");
            }
        }
        StdOut.println();
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



public static void DFS3(String course, HashMap<String, ArrayList<String>> map, HashMap<String, Boolean> map3, HashMap<String, Integer> courseSems){
        if (map3.get(course)==true) return;

        
        for (String s : map.get(course)){
            if (map3.get(s)==false){
                DFS3(s, map, map3, courseSems);
            }

            if (courseSems.containsKey(s)){
                if (courseSems.get(s)+1>courseSems.get(course)){
                    courseSems.replace(course, courseSems.get(s)+1);
                }
            }
        }
        map3.replace(course, true);
        
     }
}