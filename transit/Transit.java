package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
    trainZero = new TNode();
    TNode trainNode = trainZero;
    TNode busNode = new TNode();
    TNode walkNode = new TNode();

    trainNode.setDown(busNode);
    busNode.setDown(walkNode);
    walkNode.setDown(null);

    int busIndex = 0;
    int walkIndex = 0;

    for (int i = 0; i < trainStations.length; i++) {
        TNode nextTrainNode = new TNode(trainStations[i]);
        trainNode.setNext(nextTrainNode);
        trainNode = trainNode.getNext();

        while (busNode.getLocation() != trainNode.getLocation()) {
            TNode nextBusNode = new TNode(busStops[busIndex]);
            busNode.setNext(nextBusNode);
            busNode = busNode.getNext();
            busIndex++;

            while (walkNode.getLocation() != busNode.getLocation()) {
                TNode nextWalkNode = new TNode(locations[walkIndex]);
                walkNode.setNext(nextWalkNode);
                walkNode = walkNode.getNext();
                walkIndex++;
            }

            busNode.setDown(walkNode);
        }

        trainNode.setDown(busNode);
    }

    while (busIndex < busStops.length) {
        TNode nextBusNode = new TNode(busStops[busIndex]);
        busNode.setNext(nextBusNode);
        busNode = busNode.getNext();
        busIndex++;

        while (walkNode.getLocation() != busNode.getLocation()) {
            TNode nextWalkNode = new TNode(locations[walkIndex]);
            walkNode.setNext(nextWalkNode);
            walkNode = walkNode.getNext();
            walkIndex++;
        }

        busNode.setDown(walkNode);
    }

    while (walkIndex < locations.length) {
        TNode nextWalkNode = new TNode(locations[walkIndex]);
        walkNode.setNext(nextWalkNode);
        walkNode = walkNode.getNext();
        walkIndex++;
    }
}
	

	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
    TNode prevTrainNode = trainZero;
    TNode currTrainNode = trainZero.getNext();

    while (currTrainNode.getNext() != null) {
        if (currTrainNode.getLocation() == station) {
            prevTrainNode.setNext(currTrainNode.getNext());
            break;
        } else {
            prevTrainNode = prevTrainNode.getNext();
            currTrainNode = prevTrainNode.getNext();
        }
    }
}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
    TNode busNode = trainZero.getDown();
    TNode nextBusNode = busNode.getNext();
    TNode walkNode = busNode.getDown();

    while (nextBusNode.getLocation() < busStop && nextBusNode.getNext() != null) {
        busNode = nextBusNode;
        nextBusNode = nextBusNode.getNext();
    }

    if (nextBusNode.getLocation() > busStop) {
        TNode newBusNode = new TNode(busStop);
        newBusNode.setNext(nextBusNode);
        busNode.setNext(newBusNode);

        while (walkNode.getLocation() != newBusNode.getLocation()) {
            walkNode = walkNode.getNext();
        }

        busNode = busNode.getNext();
        busNode.setDown(walkNode);
    }

    if (nextBusNode.getNext() == null) {
        TNode newBusNode = new TNode(busStop);
        nextBusNode.setNext(newBusNode);
        nextBusNode = nextBusNode.getNext();

        while (walkNode.getLocation() != nextBusNode.getLocation()) {
            walkNode = walkNode.getNext();
        }

        nextBusNode.setDown(walkNode);
    }
}






	
	/**
 * Determines the optimal path to get to a given destination in the walking layer, and 
 * collects all the nodes which are visited in this path into an ArrayList. 
 * 
 * @param destination An int representing the destination
 * @return ArrayList of TNodes representing the best path
 */
public ArrayList<TNode> bestPath(int destination) {
    ArrayList<TNode> path = new ArrayList<>();
    TNode trainNode = trainZero;
    TNode nextTrainNode = trainNode.getNext();

    while (nextTrainNode.getLocation() < destination) {
        path.add(trainNode);
        trainNode = nextTrainNode;
        if (nextTrainNode.getNext() == null) {
            break;
        }
        nextTrainNode = nextTrainNode.getNext();
    }

    if (nextTrainNode.getLocation() == destination) {
        path.add(trainNode);
        trainNode = nextTrainNode;
        path.add(trainNode);
        path.add(trainNode.getDown());
        path.add(trainNode.getDown().getDown());
    } else {
        path.add(trainNode);
        TNode busNode = trainNode.getDown();

        if (busNode.getNext() != null) {
            TNode nextBusNode = busNode.getNext();
            path.add(busNode);

            while (nextBusNode.getLocation() < destination) {
                busNode = nextBusNode;
                path.add(busNode);
                if (nextBusNode.getNext() == null) {
                    break;
                }
                nextBusNode = nextBusNode.getNext();
            }

            if (nextBusNode.getLocation() == destination) {
                busNode = nextBusNode;
                path.add(busNode);
                path.add(busNode.getDown());
            } else {
                TNode walkNode = busNode.getDown();
                path.add(walkNode);

                while (walkNode.getLocation() != destination) {
                    walkNode = walkNode.getNext();
                    path.add(walkNode);
                }
            }
        } else {
            path.add(busNode);
            TNode walkNode = busNode.getDown();
            path.add(walkNode);

            while (walkNode.getLocation() != destination) {
                walkNode = walkNode.getNext();
                path.add(walkNode);
            }
        }
    }

    return path;
}


	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train start node of a deep copy
	 */
	/**
 * Returns a deep copy of the given layered list, which contains exactly the same
 * locations and connections, but every node is a new node.
 * 
 * @return A reference to the train start node of a deep copy
 */
public TNode duplicate() {
    TNode originalTrainNode = trainZero;
    TNode duplicateTrainNode = new TNode(trainZero.getLocation());
    TNode duplicateTrainRunner = duplicateTrainNode;

    TNode duplicateBusNode = new TNode();
    TNode originalBusNode = trainZero.getDown();
    TNode duplicateWalkNode = new TNode();
    TNode originalWalkNode = trainZero.getDown().getDown();

    duplicateTrainNode.setDown(duplicateBusNode);
    duplicateBusNode.setDown(duplicateWalkNode);
    duplicateWalkNode.setDown(null);

    while (originalTrainNode.getNext() != null) {
        TNode duplicateTrainNext = new TNode(originalTrainNode.getNext().getLocation());
        duplicateTrainRunner.setNext(duplicateTrainNext);
        duplicateTrainRunner = duplicateTrainRunner.getNext();

        while (duplicateBusNode.getLocation() < duplicateTrainRunner.getLocation()) {
            TNode duplicateBusNext = new TNode(originalBusNode.getNext().getLocation());
            duplicateBusNode.setNext(duplicateBusNext);
            duplicateBusNode = duplicateBusNode.getNext();

            while (duplicateWalkNode.getLocation() < duplicateBusNode.getLocation()) {
                TNode duplicateWalkNext = new TNode(originalWalkNode.getNext().getLocation());
                duplicateWalkNode.setNext(duplicateWalkNext);
                duplicateWalkNode = duplicateWalkNode.getNext();
                originalWalkNode = originalWalkNode.getNext();
            }

            duplicateBusNode.setDown(duplicateWalkNode);
            originalBusNode = originalBusNode.getNext();
        }

        duplicateTrainRunner.setDown(duplicateBusNode);
        originalTrainNode = originalTrainNode.getNext();
    }

    while (originalBusNode.getNext() != null) {
        TNode duplicateBusNext = new TNode(originalBusNode.getNext().getLocation());
        duplicateBusNode.setNext(duplicateBusNext);
        duplicateBusNode = duplicateBusNode.getNext();

        while (duplicateWalkNode.getLocation() < duplicateBusNode.getLocation()) {
            TNode duplicateWalkNext = new TNode(originalWalkNode.getNext().getLocation());
            duplicateWalkNode.setNext(duplicateWalkNext);
            duplicateWalkNode = duplicateWalkNode.getNext();
            originalWalkNode = originalWalkNode.getNext();
        }

        duplicateBusNode.setDown(duplicateWalkNode);
        originalBusNode = originalBusNode.getNext();
    }

    while (originalWalkNode.getNext() != null) {
        TNode duplicateWalkNext = new TNode(originalWalkNode.getNext().getLocation());
        duplicateWalkNode.setNext(duplicateWalkNext);
        duplicateWalkNode = duplicateWalkNode.getNext();
        originalWalkNode = originalWalkNode.getNext();
    }

    return duplicateTrainNode;
}







	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	/**
 * Modifies the given layered list to add a scooter layer in between the bus and
 * walking layer.
 * 
 * @param scooterStops An int array representing where the scooter stops are located
 */
public void addScooter(int[] scooterStops) {
    TNode trainNode = trainZero;
    TNode busNode = trainNode.getDown();
    TNode walkNode = busNode.getDown();
    TNode walkRunner = walkNode;
    TNode scooterHead = new TNode();
    TNode scooterRunner = scooterHead;

    // Assembles scooter layer connected to walking layer up to scooter max, not walking max
    for (int i = 0; i < scooterStops.length; i++) {
        while (walkRunner.getLocation() < scooterRunner.getLocation()) {
            walkRunner = walkRunner.getNext();
        }
        scooterRunner.setDown(walkRunner);
        TNode scooter = new TNode(scooterStops[i]);
        scooterRunner.setNext(scooter);
        scooterRunner = scooterRunner.getNext();
    }

    // Creates last down connection between scooter and walk
    while (walkRunner.getLocation() < scooterRunner.getLocation()) {
        walkRunner = walkRunner.getNext();
    }
    scooterRunner.setDown(walkRunner);
    while (busNode != null) {
        while (scooterHead.getLocation() < busNode.getLocation()) {
            scooterHead = scooterHead.getNext();
        }
        busNode.setDown(scooterHead);
        busNode = busNode.getNext();
    }
}


	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}