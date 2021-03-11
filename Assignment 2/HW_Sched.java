import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) { //using weights as a comparable factor
		if (a1.weight==a2.weight) { //if the two weights are equal, then return 0
			return 0;
		} else if (a1.weight>a2.weight) { //if a1 is weighted more heavily than a2 (i.e. a1 should appear before a2) then we return -1
			return -1;
		}	
		return 1; //otherwise, if a2 is weighted more heavily than a1 we return 1
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th time slot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		
		for (Assignment a: Assignments) { //for each assignment
			for(int i=a.deadline-1; i >= 0 ; i--) { 
				if(homeworkPlan[i] == -1) { //place assignments in the plan if the slot is empty
					homeworkPlan[i] = a.number; 
					break;
				}
			}
		}
		return homeworkPlan;
	}
}
