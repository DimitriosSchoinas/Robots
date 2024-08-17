
public class Robot {

	private static int CIMA = 1;
	private static int BAIXO = 2;
	private static int DIREITA = 3;
	private static int ESQUERDA = 4;
	private static int BORDER_MAX_LIMIT = 100;
	private static int BORDER_MIN_LIMIT = 1;

	private int x;
	private int y;
	private int autonomy;

	public Robot(int x, int y, int autonomy) {

		this.x = x;
		this.y = y;
		this.autonomy = autonomy;
	}
	public void setAutonomy(int value) {
		autonomy = autonomy - value;
	}

	public int getPositionX() {

		return x;
	}

	public int getPositionY() {
		return y;
	}

	public boolean willColide(int deslocate, int direction, Robot other) {

		boolean result = false;

		if (direction == CIMA) {

			if (  x == other.getPositionX()  && y < other.getPositionY() && y + deslocate >= other.getPositionY() )
				result = true;
		} else if (direction == BAIXO) {
			if (x == other.getPositionX() && y > other.getPositionY() && y - deslocate <= other.getPositionY()  )
				result = true;
		} else if (direction == DIREITA) {
			if (y == other.getPositionY() && x < other.getPositionX()&& x + deslocate >= other.getPositionX()  )
				result = true;
		} else {
			if (y == other.getPositionY() && x > other.getPositionX()&& x - deslocate <= other.getPositionX())
				result = true;
		}

		return result;
	}

	public int calculateMaxPossibleSteps(int direction, int deslocate) {

		int result = -1;

		if (direction == CIMA) {
			
			result = BORDER_MAX_LIMIT - y;
			
				
			
			
		} else if (direction == BAIXO) {
			
			result =  y - BORDER_MIN_LIMIT;
			
				
			
		} else if (direction == DIREITA) {
			
			result = BORDER_MAX_LIMIT - x ;
		
				
			
		} else {
			
			result = x - BORDER_MIN_LIMIT;
		
				
			
		}

		return result;
	}

	public void move(int deslocate, int direction) {

		if (direction == CIMA) {
			y = y + deslocate;
		} else if (direction == BAIXO) {
			y = y - deslocate;
		} else if (direction == DIREITA) {
			x = x + deslocate;
		} else {
			x = x - deslocate;
		}

	}
	
	public int getClosestWall(Robot otherRobot){
		
		int direction = 0;
		
		int leftDistance = x - BORDER_MIN_LIMIT;
		int RightDistance = BORDER_MAX_LIMIT - x;
		int TopDistance =  BORDER_MAX_LIMIT - y;
		int BottomDistance = y - BORDER_MIN_LIMIT;
		
		
		if(leftDistance < RightDistance && leftDistance < TopDistance && leftDistance <BottomDistance) {
			
			if(!willColide(leftDistance, ESQUERDA, otherRobot)) {
				direction = ESQUERDA;
			}else {
				
				if(RightDistance < TopDistance && RightDistance < BottomDistance) {
					direction = DIREITA;
				}else if(TopDistance < RightDistance && TopDistance < BottomDistance) {
					direction = CIMA;
				}else if(BottomDistance < RightDistance && BottomDistance < TopDistance) {
					direction = BAIXO;
				}
			}
			
			
		}else if(RightDistance < leftDistance && RightDistance < TopDistance && RightDistance <BottomDistance) {
			
			if(!willColide(RightDistance, DIREITA, otherRobot)) {
				direction = DIREITA;
			}else {
				
				if(leftDistance < TopDistance && leftDistance < BottomDistance) {
					direction = ESQUERDA;
				}else if(TopDistance < leftDistance && TopDistance < BottomDistance) {
					direction = CIMA;
				}else if(BottomDistance < leftDistance && BottomDistance < TopDistance) {
					direction = BAIXO;
				}
			}
			
		}else if((TopDistance < RightDistance && TopDistance < leftDistance && TopDistance <BottomDistance)) {
			
			if(!willColide(TopDistance, CIMA, otherRobot)) {
				direction = CIMA;
			}else {
				
				if(leftDistance < RightDistance && leftDistance < BottomDistance) {
					direction = ESQUERDA;
				}else if(RightDistance < leftDistance && RightDistance < BottomDistance) {
					direction = DIREITA;
				}else if(BottomDistance < leftDistance && BottomDistance < RightDistance) {
					direction = BAIXO;
				}
			}
			
		}else if((BottomDistance < RightDistance && BottomDistance < TopDistance && BottomDistance <leftDistance) ) {
			
			if(!willColide(BottomDistance, BAIXO, otherRobot)) {
				direction = BAIXO;
			}else {
				
				if(leftDistance < RightDistance && leftDistance < TopDistance) {
					direction = ESQUERDA;
				}else if(RightDistance < leftDistance && RightDistance < TopDistance) {
					direction = DIREITA;
				}else if(TopDistance < leftDistance && TopDistance < RightDistance) {
					direction = CIMA;
				}
			}
			
		}else if( leftDistance == TopDistance && leftDistance<RightDistance && leftDistance<BottomDistance) {
			if(!willColide(TopDistance, CIMA, otherRobot)) {
				direction = CIMA;
			}else {
				direction = ESQUERDA;
			}
			
		}else if( RightDistance == TopDistance && RightDistance<leftDistance && RightDistance<BottomDistance) {
			if(!willColide(TopDistance, CIMA, otherRobot)) {
				direction = CIMA;
			}else {
				direction = DIREITA;
			}
		}else if(leftDistance == BottomDistance && leftDistance<RightDistance && leftDistance<TopDistance) {
			if(!willColide(BottomDistance, BAIXO, otherRobot)) {
				direction = BAIXO;
			}else {
				direction = ESQUERDA;
			}
		}else if( RightDistance == BottomDistance && RightDistance<leftDistance && RightDistance<TopDistance) {
			if(!willColide(BottomDistance, BAIXO, otherRobot)) {
				direction = BAIXO;
			}else {
				direction = DIREITA;
			}
		}
		
		return direction;
	}
	public boolean canReturn(int x, int y ,Robot otherRobot) {
		
		boolean result = false;
		
		int safeX = this.x;
		int safeY = this.y;
		
		this.x = x;
		this.y = y;
		
		int closestDirection = getClosestWall(otherRobot);
		int minNumSteps  = 0;
		
		
		
		if (closestDirection == CIMA) {
			minNumSteps = BORDER_MAX_LIMIT - y;
		} else if (closestDirection == BAIXO) {
			minNumSteps = y - BORDER_MIN_LIMIT;
		} else if (closestDirection == DIREITA) {
			minNumSteps = BORDER_MAX_LIMIT - x;
		} else if(closestDirection == ESQUERDA){
			minNumSteps = x - BORDER_MIN_LIMIT;
		}
		
		
		if(hasBattery(closestDirection, minNumSteps) )
		result = true;
		
		this.x = safeX;
		this.y = safeY;
		
		return result;
	}

	public boolean canDoSurvey(int posX, int posY, Robot otherRobot) {
		boolean result = true;
		int neededStepsToTarget  = Math.abs(posX - x) + Math.abs(posY - y);
		
		int saveAutonomy = autonomy;
		autonomy = autonomy - neededStepsToTarget;
		
		int horizontalDirection = 0;
		int verticalDirection = 0;
		int horizontalSteps = posX - x;
		int verticalSteps = posY - y;
		
		if(horizontalSteps >= 0) {
			horizontalDirection = DIREITA;
		}else {
			horizontalDirection = ESQUERDA;
		}
		
		if(verticalSteps >= 0) {
			verticalDirection = CIMA;
		}else {
			verticalDirection = BAIXO;
		}
		
		
		if((this.y == posY && willColide(Math.abs(horizontalSteps), horizontalDirection,otherRobot)) || (this.x == posX && willColide(Math.abs(verticalSteps), verticalDirection,otherRobot))  ) {
			result = false;
		}
		
		
		if(!canReturn(posX, posY,otherRobot))
		result = false;
		
		if(( ( posX  == otherRobot.getPositionX()) && ( posY  == otherRobot.getPositionY())))
			result = false;
		
			autonomy = saveAutonomy;
		
		return result;
	}

	public boolean hasBattery(int direction, int deslocate) {
		boolean result = false;
		
		
	if(	autonomy - deslocate>= 0)
		result = true;
	
		return result;
	}
}
