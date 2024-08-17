import java.util.Scanner;

public class Main {

	private static final String POSITION = "POSITION";
	private static final String COLLISION = "COLLISION";
	private static final String MOVE = "MOVE";
	private static final String CLOSESTWALL = "CLOSESTWALL";
	private static final String RETURN = "RETURN";
	private static final String SURVEY = "SURVEY";

	private static final String POSITION_MESSAGE = "Robot %d is at the position (%d,%d).\n";
	private static final String ROBOTS_MESSAGE_ONE = "Robots will collide.\n";
	private static final String ROBOTS_MESSAGE_TWO = "Robots will not collide.\n";
	private static final String MOVE_MESSAGE_ONE = "Robot %d moved successfully.\n";
	private static final String MOVE_MESSAGE_TWO = "Wall detected. Robot %d moved %d meters.\n";
	private static final String MOVE_MESSAGE_THREE = "Wall detected. Robot %d could not move.\n";
	private static final String MOVE_MESSAGE_FOUR = "Collision detected. Robot %d could not move.\n";
	private static final String MOVE_MESSAGE_FIVE = "No battery. Robot %d could not move.\n";
	private static final String CLOSESTWALL_MESSAGE = "%d is the closest exit direction.\n";
	private static final String RETURN_MESSAGE_SUCCESS = "Robot %d can return.\n";
	private static final String RETURN_MESSAGE_FAIL = "Robot %d cannot return.\n";
	private static final String SURVEY_MESSAGE_SUCCESS = "Position (%d,%d) can be surveyed by robot %d.\n";
	private static final String SURVEY_MESSAGE_FAIL = "Position (%d,%d) cannot be surveyed by robot %d.\n";
	

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		// creates the 2 robots and reads the program commands
		createRobots(in);

	}

	private static void createRobots(Scanner in) {

		int x1 = in.nextInt();
		int y1 = in.nextInt();
		int aut1 = in.nextInt();
		int x2 = in.nextInt();
		int y2 = in.nextInt();
		int aut2 = in.nextInt();
		in.nextLine();

		Robot robot1 = new Robot(x1, y1,aut1);
		Robot robot2 = new Robot(x2, y2,aut2);

		readCommands(in, robot1, robot2);
	}

	private static void readCommands(Scanner in, Robot robot1, Robot robot2) {

		int numberOfCommands = 1;

		while (numberOfCommands < 5) {
			String command = in.next();
			switch (command) {

			case POSITION:
				getPosition(in, robot1, robot2);
				break;
			case COLLISION:
				isCollision(in, robot1, robot2);
				break;
			case MOVE:
				move(in, robot1, robot2);
				break;
			case CLOSESTWALL:
				getClosestWall(in, robot1, robot2);
				break;
			case RETURN:
				returnCommand(in, robot1, robot2);
				break;
			case SURVEY:
				survey(in, robot1, robot2);
				break;
			}
			numberOfCommands++;

		}
		in.close();
	}

	private static void survey(Scanner in, Robot robot1, Robot robot2) {
		

		int robotNumber = in.nextInt();
		int posX = in.nextInt();
		int posY = in.nextInt();
		in.nextLine();

		Robot robot, otherRobot;

		if (robotNumber == 1) {
			robot = robot1;
			otherRobot = robot2;
		} else {
			robot = robot2;
			otherRobot = robot1;
		}
		
		if(robot.canDoSurvey(posX, posY, otherRobot)) {
			System.out.printf(SURVEY_MESSAGE_SUCCESS,posX, posY, robotNumber);
		}else {
			System.out.printf(SURVEY_MESSAGE_FAIL, posX, posY, robotNumber);
		}
	}

	private static void returnCommand(Scanner in, Robot robot1, Robot robot2) {
		
		int robotNumber = in.nextInt();
		in.nextLine();

		Robot robot, otherRobot;

		if (robotNumber == 1) {
			robot = robot1;
			otherRobot = robot2;
		} else {
			robot = robot2;
			otherRobot = robot1;
		}
		
		if(robot.canReturn(robot.getPositionX(), robot.getPositionY(), otherRobot)) {
			System.out.printf(RETURN_MESSAGE_SUCCESS, robotNumber);
		}else {
			System.out.printf(RETURN_MESSAGE_FAIL, robotNumber);
		}
	}

	private static void getClosestWall(Scanner in, Robot robot1, Robot robot2) {
	
	int robotNumber = in.nextInt();
		in.nextLine();

		Robot robot, otherRobot;

		if (robotNumber == 1) {
			robot = robot1;
			otherRobot = robot2;
		} else {
			robot = robot2;
			otherRobot = robot1;
		}
		
		System.out.printf(CLOSESTWALL_MESSAGE, robot.getClosestWall(otherRobot));
	}

	private static void getPosition(Scanner in, Robot robot1, Robot robot2) {

		int robotNumber = in.nextInt();
		in.nextLine();

		Robot robot;

		if (robotNumber == 1) {
			robot = robot1;
		} else {
			robot = robot2;
		}

		System.out.printf(POSITION_MESSAGE, robotNumber, robot.getPositionX(), robot.getPositionY());

	}

	private static void isCollision(Scanner in, Robot robot1, Robot robot2) {

		int robotNumber = in.nextInt();
		int deslocate = in.nextInt();
		int direction = in.nextInt();
		in.nextLine();

		Robot robot, otherRobot;

		if (robotNumber == 1) {
			robot = robot1;
			otherRobot = robot2;
		} else {
			robot = robot2;
			otherRobot = robot1;
		}

		if (robot.willColide(deslocate, direction, otherRobot)) {
			System.out.printf(ROBOTS_MESSAGE_ONE);
		} else {
			System.out.printf(ROBOTS_MESSAGE_TWO);
		}
	}

	private static void move(Scanner in, Robot robot1, Robot robot2) {

		int robotNumber = in.nextInt();
		int deslocate = in.nextInt();
		int direction = in.nextInt();
		in.nextLine();

		Robot robot, otherRobot;

		if (robotNumber == 1) {
			robot = robot1;
			otherRobot = robot2;
		} else {
			robot = robot2;
			otherRobot = robot1;
		}
		
		if(robot.calculateMaxPossibleSteps(direction,deslocate ) >= deslocate && !robot.willColide(deslocate, direction, otherRobot) && robot.hasBattery(direction,deslocate)) {
			
			System.out.printf(MOVE_MESSAGE_ONE, robotNumber);
			robot.move(deslocate, direction);
			robot.setAutonomy(deslocate);
		}else if(robot.calculateMaxPossibleSteps(direction,deslocate) < deslocate && robot.calculateMaxPossibleSteps(direction,deslocate) > 0 && !robot.willColide(deslocate, direction, otherRobot) && robot.hasBattery(direction,robot.calculateMaxPossibleSteps(direction,deslocate))) {
			int maxSteps = robot.calculateMaxPossibleSteps(direction,deslocate );
			System.out.printf(MOVE_MESSAGE_TWO, robotNumber, robot.calculateMaxPossibleSteps(direction,deslocate));
			robot.move(robot.calculateMaxPossibleSteps(direction,deslocate), direction);
			robot.setAutonomy(maxSteps);
		}else if(robot.calculateMaxPossibleSteps(direction,deslocate) == 0) {
		
			System.out.printf(MOVE_MESSAGE_THREE, robotNumber);
		}else if(robot.willColide(deslocate, direction, otherRobot)){
			System.out.printf(MOVE_MESSAGE_FOUR, robotNumber);
		}else {
			System.out.printf(MOVE_MESSAGE_FIVE, robotNumber);
		}

	}
}
