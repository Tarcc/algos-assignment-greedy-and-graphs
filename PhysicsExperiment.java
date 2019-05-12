/**
 * Author: Brian Schlapp and Carolyn Yao
 * Does this compile or finish running within 5 seconds? Y
 */
public class PhysicsExperiment {
	
	public int[][] scheduleExperiments(int numStudents, int numSteps, int[][] signUpTable) {
		// Your scheduleTable is initialized as all 0's so far. Your code will put 1's
		// in the table in the right places based on the return description
		int[][] scheduleTable = new int[numStudents + 1][numSteps + 1];
		// Your code goes here
		int student = 1; 
		int currentStep = 1; 
		int currentStudent = 1;
		int step = 1; // used to count how many times something is added to the schedule table
		int conStep;
		int maxConStep;
		int conStudent;
		while (step <= numSteps) {

			if (signUpTable[student][currentStep] == 1) //checks if the selected student can take the current step
			{
				scheduleTable[student][currentStep] = 1; 
				currentStep++; 
				step++;
			} else 
			{
				if (currentStep != numSteps) {
					maxConStep = 0; 
					conStudent = 0; 

					// finds the student that will do the most consecutive steps.
					while (++currentStudent <= numStudents) {
						conStep = 0;
						int tempStep = currentStep;
						while (tempStep <= numSteps && signUpTable[currentStudent][tempStep] != 0) {
							conStep++;
							if (conStep > maxConStep) { // checks if the temporary conStep is larger than max, if so, it replaces the max
								maxConStep = conStep;
								conStudent = currentStudent;
							}
							tempStep++;
						}
						
					}
					currentStudent = 1;
					student = conStudent; // makes the global student variable take the temporary value
				} else {
					if (student == numStudents)
						student = 1; 
					else
						student++; 
				}
			}
		}
		return scheduleTable;

	}

	/**
	 * Makes the convenient lookup table based on the steps each student says they
	 * can do
	 * 
	 * @param numSteps       the number of steps in the experiment
	 * @param studentSignUps student sign ups ex: {{1, 2, 4}, {3, 5}, {6, 7}}
	 * @return a lookup table so if we want to know if student x can do step y,
	 *         lookupTable[x][y] = 1 if student x can do step y lookupTable[x][y] =
	 *         0 if student x cannot do step y
	 */
	public int[][] makeSignUpLookup(int numSteps, int[][] studentSignUps) {
		int numStudents = studentSignUps.length;
		int[][] lookupTable = new int[numStudents + 1][numSteps + 1];
		for (int student = 1; student <= numStudents; student++) {
			int[] signedUpSteps = studentSignUps[student - 1];
			for (int i = 0; i < signedUpSteps.length; i++) {
				lookupTable[student][signedUpSteps[i]] = 1;
			}
		}
		return lookupTable;
	}

	/**
	 * Prints the optimal schedule by listing which steps each student will do
	 * Example output is Student 1: 1, 3, 4
	 * 
	 * @param schedule The table of 0's and 1's of the optimal schedule, where
	 *                 schedule[x][y] means whether in the optimal schedule student
	 *                 x is doing step y
	 */
	public void printResults(int[][] schedule) {
		for (int student = 1; student < schedule.length; student++) {
			int[] curStudentSchedule = schedule[student];
			System.out.print("Student " + student + ": ");
			for (int step = 1; step < curStudentSchedule.length; step++) {
				if (curStudentSchedule[step] == 1) {
					System.out.print(step + " ");
				}
			}
			System.out.println("");
		}
	}

	/**
	 * This validates the input data about the experiment step sign-ups.
	 * 
	 * @param numStudents the number of students
	 * @param numSteps    the number of steps
	 * @param signUps     the data given about which steps each student can do
	 * @return true or false whether the input sign-ups match the given number of
	 *         students and steps, and whether all the steps are guaranteed at least
	 *         one student.
	 */
	public boolean inputsValid(int numStudents, int numSteps, int signUps[][]) {
		int studentSignUps = signUps.length;

		// Check if there are any students or signups
		if (numStudents < 1 || studentSignUps < 1) {
			System.out.println("You either did not put in any student or any signups");
			return false;
		}

		// Check if the number of students and sign-up rows matches
		if (numStudents != studentSignUps) {
			System.out.println("You input " + numStudents + " students but your signup suggests " + signUps.length);
			return false;
		}

		// Check that all steps are guaranteed in the signups
		int[] stepsGuaranteed = new int[numSteps + 1];
		for (int i = 0; i < studentSignUps; i++) {
			for (int j = 0; j < signUps[i].length; j++) {
				stepsGuaranteed[signUps[i][j]] = 1;
			}
		}
		for (int step = 1; step <= numSteps; step++) {
			if (stepsGuaranteed[step] != 1) {
				System.out.println("Your signup is incomplete because not all steps are guaranteed.");
				return false;
			}
		}

		return true;
	}

	/**
	 * This sets up the scheduling test case and calls the scheduling method.
	 * 
	 * @param numStudents the number of students
	 * @param numSteps    the number of steps
	 * @param signUps     which steps each student can do, in order of students and
	 *                    steps
	 */
	public void makeExperimentAndSchedule(int experimentNum, int numStudents, int numSteps, int[][] signUps) {
		System.out.println("----Experiment " + experimentNum + "----");
		if (!inputsValid(numStudents, numSteps, signUps)) {
			System.out.println("Experiment signup info is invalid");
			return;
		}
		int[][] signUpsLookup = makeSignUpLookup(numSteps, signUps);
		int[][] schedule = scheduleExperiments(numStudents, numSteps, signUpsLookup);
		printResults(schedule);
		System.out.println("");
	}

	/**
	 * You can make additional test cases using the same format. In fact the helper
	 * functions I've provided will even check your test case is set up correctly.
	 * Do not touch any of of the existing lines. Just make sure to comment out or
	 * delete any of your own test cases when you submit. The three experiment test
	 * cases existing in this main method should be the only output when running
	 * this file.
	 */
	public static void main(String args[]) {
		PhysicsExperiment pe = new PhysicsExperiment();

		// Experiment 1: Example 1 from README, 3 students, 6 steps:
		int[][] signUpsExperiment1 = { { 1, 2, 3, 5 }, { 2, 3, 4 }, { 1, 4, 5, 6 } };
		pe.makeExperimentAndSchedule(1, 3, 6, signUpsExperiment1);

		// Experiment 2: Example 2 from README, 4 students, 8 steps
		int[][] signUpsExperiment2 = { { 5, 7, 8 }, { 2, 3, 4, 5, 6 }, { 1, 5, 7, 8 }, { 1, 3, 4, 8 } };
		pe.makeExperimentAndSchedule(2, 4, 8, signUpsExperiment2);

		// Experiment 3: Another test case, 5 students, 11 steps
		int[][] signUpsExperiment3 = { { 7, 10, 11 }, { 8, 9, 10 }, { 2, 3, 4, 5, 7 }, { 1, 5, 6, 7, 8 },
				{ 1, 3, 4, 8 } };
		pe.makeExperimentAndSchedule(3, 5, 11, signUpsExperiment3);
	}

}
