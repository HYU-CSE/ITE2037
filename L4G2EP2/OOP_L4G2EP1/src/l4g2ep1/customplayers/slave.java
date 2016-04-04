package l4g2ep1.customplayers;

import java.util.ArrayList;


import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class slave extends Player
{
	DirectionCode[] directions;
	int pos_directionToMove=0;
	
	public slave()
	{
		name = "slave";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false;	
		receiveOthersInfo_detected = true;
		// TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고 아닌 경우 false로 두세요.
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		Point myPosition = myInfo.GetPosition();
		ArrayList<Point> listOfDefected = new ArrayList<Point>();
		ArrayList<Point> listOfSurvivor = new ArrayList<Point>();
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int[][] weightsOfSurvivor = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		
		if( gameInfo.GetCurrentTurnNumber() <= 11 ) // 11턴 이하일때 생존자끼리 있을 경우 생존자 피하기
		{
			for ( PlayerInfo other : othersInfo_withinSight ) // 내 시야 검사
			{
				if ( other.GetState() == PlayerInfo.State.Survivor ) //생존자가 있으면
				{
					Point pos_other = other.GetPosition();
					if(weightsOfSurvivor[pos_other.y][pos_other.x] == 0) //중복무시
					{
						++weightsOfSurvivor[pos_other.y][pos_other.x];
						listOfSurvivor.add(pos_other);	
					}
				}
			}
			if(listOfSurvivor.isEmpty() == true) // 아무도 없다면.
			{
				if( myPosition.x >=3 && myPosition.y >=3 && myPosition.x <= 5 && myPosition.y <= 5 )
				{
					++pos_directionToMove ;
					pos_directionToMove %= 4;
					return directions[pos_directionToMove];
				}
				else if( myPosition.x >=0 && myPosition.x <= 2 )
					return directions[0];
				else if( myPosition.x >=6 && myPosition.x <= 8 )
					return directions[2];
				else if( myPosition.y <= 2 &&  myPosition.y>=0 )
					return directions[1];
				else
					return directions[3];
			}
			else // 생존자 피해서 도망다님
			{
				int i =0, distance1 = 0, distance2 = 0;
				int totalx=0, totaly=0;
				Point[] shortest = new Point[12];
				for ( Point pos_max : listOfSurvivor )
				{
					int distance = GetDistance(myPosition, pos_max);
					if ( distance == 1 ) 
						distance1++;
					else
						distance2++;
					
					shortest[i] = pos_max;
					totalx += shortest[i].x;
					totaly += shortest[i].y;
					i++;
				}
				if( distance2 == 1 && i == 1) // 거리 2 인 생존자가 하나이면
				{
					if( myPosition.x < shortest[0].x)
					{
						if(this.IsValidMove(directions[2]) == false)
						{
							if(this.IsValidMove(directions[3]) == false)
								return directions[1];
							else
								return directions[3];
						}
						return directions[2];
					}
					else if( myPosition.x > shortest[0].x )
					{
						if(this.IsValidMove(directions[0]) == false)
						{
							if(this.IsValidMove(directions[1]) == false)
								return directions[3];
							else
								return directions[1];
						}
						return directions[0];
					}
					else if( myPosition.y > shortest[0].y )
					{
						if(this.IsValidMove(directions[1]) == false)
						{
							if(this.IsValidMove(directions[0]) == false)
								return directions[2];
							else
								return directions[0];
						}
						return directions[1];
					}
					else
					{
						if(this.IsValidMove(directions[3]) == false)
						{
							if(this.IsValidMove(directions[0]) == false)
								return directions[2];
							else
								return directions[0];
						}
						return directions[3];
					}
				}
				
				if( (distance1 + distance2) == 2 )
				{
					if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == 1) &&
						(shortest[0].y + shortest[1].y - totaly) == 1 )
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
					
						return directions[1];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == -1) &&
							(shortest[0].x + shortest[1].x - totalx) == 1 )
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
						
						return directions[2];
					}
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == -1) &&
							(shortest[0].y + shortest[1].y - totaly) == 1 )
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
					
						return directions[1];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == -1) &&
							(shortest[0].x + shortest[1].x - totalx) == -1 )
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[3];
					
						return directions[0];
					}
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == 1) &&
							(shortest[0].y + shortest[1].y - totaly) == -1 )
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
						return directions[3];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == 1) &&
							(shortest[0].x + shortest[1].x - totalx) == -1 )
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[1];
						
						return directions[0];
					}
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == -1) &&
							(shortest[0].y + shortest[1].y - totaly) == -1 )
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[0];
					
						return directions[3];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == 1) &&
							(shortest[0].x + shortest[1].x - totalx) == 1 )
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
					
						return directions[2];
					}
				}
					if( totalx - myPosition.x*i >=i) // 내 오른쪽
					{
						if(this.IsValidMove(directions[2]) == false)
						{
							if(this.IsValidMove(directions[1]) == false)
								return directions[3];
							else
								return directions[1];
						}
						return directions[2];
					}
					else if( totalx - myPosition.x*i <= -i) // 내 왼쪽
					{
						if(this.IsValidMove(directions[0]) == false)
						{
							if(this.IsValidMove(directions[1]) == false)
								return directions[3];
							else
								return directions[1];
						}
						return directions[0];
					}
					else if( totaly - myPosition.y*i <= -i) // 내 위쪽
					{
						if(this.IsValidMove(directions[1]) == false)
						{
							if(this.IsValidMove(directions[2]) == false)
								return directions[0];
							else
								return directions[2];
						}
						return directions[1];
					}
					else if( totaly - myPosition.y*i >= i) // 내 밑쪽
					{
						if(this.IsValidMove(directions[3]) == false)
						{
							if(this.IsValidMove(directions[2]) == false)
								return directions[0];
							else
								return directions[2];
						}
						return directions[3];
					}
					else if( totalx - myPosition.x*i == 0) // 내 위아래
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[2];
					
						return directions[0];
					}
					else if( totaly - myPosition.y*i == 0) // 내 좌우
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
						return directions[3];
					}
				}
			}
		//감염체 탐색
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				if(weights[pos_other.y][pos_other.x] == 0) //중복무시
				{
					++weights[pos_other.y][pos_other.x];
					listOfDefected.add(pos_other);	
				}
			}
		}
		
		if(listOfDefected.isEmpty() == true)  // 감염체가 내시야에 없다면? 
		{
			ArrayList<Point> listOfDefected2 = new ArrayList<Point>();
			for ( PlayerInfo other : othersInfo_detected ) // 친구들 시야 검사함.
			{
				if ( other.GetState() == PlayerInfo.State.Infected )
				{
					Point pos_other = other.GetPosition();
					listOfDefected2.add(pos_other);	
				}
			}
			if(listOfDefected2.isEmpty() == true) // 친구들 시야에도 없다면?
			{
				if( myPosition.x >=3 && myPosition.y >=3 && myPosition.x <= 5 && myPosition.y <= 5 )
				{
					++pos_directionToMove ;
					pos_directionToMove %= 4;
					return directions[pos_directionToMove];
				}
				else if( myPosition.x >=0 && myPosition.x <= 2 )
					return directions[0];
				else if( myPosition.x >=6 && myPosition.x <= 8 )
					return directions[2];
				else if( myPosition.y <= 2 &&  myPosition.y>=0 )
					return directions[1];
				else
					return directions[3];
			}
			else // 친구들 시야에만 있음 -> 감염체에 다가간다!
			{
				int min_distance = Integer.MAX_VALUE;

				Point[] shortest = new Point[1];
				for ( Point pos_max : listOfDefected2 )
				{
					int distance = GetDistance(myPosition, pos_max);
					
					if ( distance < min_distance )
					{
						min_distance = distance;
						shortest[0] = pos_max;
					}
				}
				if( myPosition.x < shortest[0].x)
					return directions[0];
				else if( myPosition.x > shortest[0].x )
					return directions[2];
				else if( myPosition.y > shortest[0].y )
					return directions[3];
				else
					return directions[1];
			}
		}
		else // 내 시야에 감염체가 있다면 
		{
			int i =0, distance1 = 0, distance2 = 0;
			int totalx=0, totaly=0;
			Point[] shortest = new Point[12];
			for ( Point pos_max : listOfDefected )
			{
				int distance = GetDistance(myPosition, pos_max);
				if ( distance == 2 ) 
					distance2++;
				else
					distance1++;
				
				shortest[i] = pos_max;
				totalx += shortest[i].x;
				totaly += shortest[i].y;
				i++;
			}
			
			if( ( distance2 == 1 || distance1 == 1 ) && i == 1) // 거리 2 인 감염체가 하나이면
			{
				if( myPosition.x < shortest[0].x)
				{
					if(this.IsValidMove(directions[2]) == false)
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[1];
						else
							return directions[3];
					}
					return directions[2];
				}
				else if( myPosition.x > shortest[0].x )
				{
					if(this.IsValidMove(directions[0]) == false)
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
						else
							return directions[1];
					}
					return directions[0];
				}
				else if( myPosition.y > shortest[0].y )
				{
					if(this.IsValidMove(directions[1]) == false)
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[2];
						else
							return directions[0];
					}
					return directions[1];
				}
				else
				{
					if(this.IsValidMove(directions[3]) == false)
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[2];
						else
							return directions[0];
					}
					return directions[3];
				}
			}
			else // 나머지
			{
				if( (distance1 + distance2) == 2 )
				{
					if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == 1) &&
						(shortest[0].y + shortest[1].y - totaly) == 1 )
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
					
						return directions[1];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == -1) &&
							(shortest[0].x + shortest[1].x - totalx) == 1 )
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
						
						return directions[2];
					}
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == -1) &&
							(shortest[0].y + shortest[1].y - totaly) == 1 )
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
					
						return directions[1];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == -1) &&
							(shortest[0].x + shortest[1].x - totalx) == -1 )
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[3];
					
						return directions[0];
					} 
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == 1) &&
							(shortest[0].y + shortest[1].y - totaly) == -1 )
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
						return directions[3];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == 1) &&
							(shortest[0].x + shortest[1].x - totalx) == -1 )
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[1];
						
						return directions[0];
					}
					else if(((shortest[0].x + shortest[1].x - totalx) == 0 || (shortest[0].x + shortest[1].x - totalx) == -1) &&
							(shortest[0].y + shortest[1].y - totaly) == -1 )
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[0];
					
						return directions[3];
					}
					else if(((shortest[0].y + shortest[1].y - totaly) == 0 || (shortest[0].y + shortest[1].y - totalx) == 1) &&
							(shortest[0].x + shortest[1].x - totalx) == 1 )
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
					
						return directions[2];
					}
					
				}	
				
				if( totalx - myPosition.x*i >=i) // 내 오른쪽
				{
					if(this.IsValidMove(directions[2]) == false)
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
						else
							return directions[1];
					}
					return directions[2];
				}
				else if( totalx - myPosition.x*i <= -i) // 내 왼쪽
				{
					if(this.IsValidMove(directions[0]) == false)
					{
						if(this.IsValidMove(directions[1]) == false)
							return directions[3];
						else
							return directions[1];
					}
					return directions[0];
				}
				else if( totaly - myPosition.y*i <= -i) // 내 위쪽
				{
					if(this.IsValidMove(directions[1]) == false)
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
						else
							return directions[2];
					}
					return directions[1];
				}
				else if( totaly - myPosition.y*i >= i) // 내 밑쪽
				{
					if(this.IsValidMove(directions[3]) == false)
					{
						if(this.IsValidMove(directions[2]) == false)
							return directions[0];
						else
							return directions[2];
					}
					return directions[3];
				}
				else if( totalx - myPosition.x*i == 0) // 내 위아래
				{
					if(this.IsValidMove(directions[0]) == false)
							return directions[2];
					
					return directions[0];
				}
				else if( totaly - myPosition.y*i == 0) // 내 좌우
				{
					if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
					return directions[3];
				}
			}
		}
		//혹시 위 코드가 안돌아 갔을때.
		while (this.IsValidMove(directions[pos_directionToMove]) == false)
		{
			++pos_directionToMove;
			pos_directionToMove %= 4;
		}
		return directions[pos_directionToMove];
		// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요. ;현재방향 0~3 다음방향
	}

	@Override
	public void Corpse_Stay()
	{
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}
	
	int checkright = 0; // 전에 갔던 시체인지 검사 (되돌아 가지 않기위해)
	int checkleft = 0;
	int checkup = 0;
	int checkdown = 0;
	@Override
	public DirectionCode Infected_Move()
	{
		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.	
		Point myPosition = myInfo.GetPosition();
		ArrayList<Point> listOfCorpse = new ArrayList<Point>();
		
	    // 시체 탐색 
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			if ( other.GetState() == PlayerInfo.State.Corpse )
			{
				Point pos_other = other.GetPosition();
				listOfCorpse.add(pos_other);	
			}
		}
		for ( Point pos_max : listOfCorpse )
		{
			int distance = GetDistance(myPosition, pos_max);
			if ( distance == 1 ) // 거리1인 시체 -> 거기로 간다
			{
				if( myPosition.x == pos_max.x)
				{
					if( myPosition.y > pos_max.y && checkup == 0 )
					{
						checkup++;
						return directions[3];
					}
					else if( myPosition.y < pos_max.y && checkdown == 0 )
					{
						checkdown++;
						return directions[1];
					}
				}
				else
				{
					if( myPosition.x > pos_max.x && checkleft == 0 )
					{
						checkleft++;
						return directions[2];
					}
					else if( myPosition.x < pos_max.x && checkright == 0 )
					{
						checkright++;
						return directions[0];
					}
				}
			}
		}
		DirectionCode Temp = DirectionCode.Stay;
		return Temp;
	}

	@Override
	public void Soul_Stay()
	{	
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			directions = new DirectionCode[4];
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
		}
		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn()
	{   // 시체정보 초기화
		checkright = 0; 
		checkleft = 0;
		checkup = 0;
		checkdown = 0;
		
		if( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			return new Point(2,2);
		}
		 // 한번 죽었다면 생존포기 자살
		
			int[][] weightsOfI = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int[][] weightsOfC = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int max_weight = -1;
			ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

			for ( PlayerInfo other : othersInfo_withinSight )  //감염체,시체 개수검사
			{
				if ( other.GetState() == PlayerInfo.State.Infected )
				{
					Point pos_other = other.GetPosition();
					
					weightsOfI[pos_other.y][pos_other.x] += 2;

					if ( weightsOfI[pos_other.y][pos_other.x] > max_weight )
					{
						max_weight = weightsOfI[pos_other.y][pos_other.x]; 
						list_pos_max_weight.clear();
					}
					
					if ( weightsOfI[pos_other.y][pos_other.x] == max_weight )
					{
						list_pos_max_weight.add(pos_other);
					}
				}
			}
			for ( PlayerInfo other : othersInfo_withinSight )  //감염체,시체 개수검사
			{
				if ( other.GetState() == PlayerInfo.State.Corpse )
				{
					Point pos_other = other.GetPosition();
					weightsOfC[pos_other.y][pos_other.x]++;
				}
			}
			
			if(list_pos_max_weight.isEmpty() == true) //감염체가 없으면
				return new Point(2,2);

			int []total = new int[list_pos_max_weight.size()];
			Point []maxPoint = new Point[list_pos_max_weight.size()];
			
			int i=0;
			for ( Point pos_max_weight : list_pos_max_weight ) // 주위 가중치 부과
			{
				Point temp = new Point(pos_max_weight.x,pos_max_weight.y);
				maxPoint[i] = temp;
				
					Point Right = new Point(pos_max_weight.x+1, pos_max_weight.y);
					Point Left = new Point(pos_max_weight.x-1, pos_max_weight.y);
					Point Up = new Point(pos_max_weight.x, pos_max_weight.y-1);
					Point Down = new Point(pos_max_weight.x, pos_max_weight.y+1);
					total[i] += weightsOfC[pos_max_weight.x][pos_max_weight.y]*12;
					if(this.IsValidMove(GetAdjacentPoint(pos_max_weight,directions[0])) == true)
						total[i] += weightsOfI[pos_max_weight.x+1][pos_max_weight.y]*3 + weightsOfC[pos_max_weight.x+1][pos_max_weight.y]*3;
					if(this.IsValidMove(GetAdjacentPoint(pos_max_weight,directions[1])) == true)
						total[i] += weightsOfI[pos_max_weight.x][pos_max_weight.y+1]*3 + weightsOfC[pos_max_weight.x][pos_max_weight.y+1]*3;
					if(this.IsValidMove(GetAdjacentPoint(pos_max_weight,directions[2])) == true)
						total[i] += weightsOfI[pos_max_weight.x-1][pos_max_weight.y]*3 + weightsOfC[pos_max_weight.x-1][pos_max_weight.y]*3;
					if(this.IsValidMove(GetAdjacentPoint(pos_max_weight,directions[3])) == true)
						total[i] += weightsOfI[pos_max_weight.x][pos_max_weight.y-1]*3 + weightsOfC[pos_max_weight.x][pos_max_weight.y-1]*3;
					if(this.IsValidMove(GetAdjacentPoint(Right,directions[0])) == true)
						total[i] += weightsOfI[pos_max_weight.x+2][pos_max_weight.y];
					if(this.IsValidMove(GetAdjacentPoint(Right,directions[1])) == true)
						total[i] += weightsOfI[pos_max_weight.x+1][pos_max_weight.y+1];
					if(this.IsValidMove(GetAdjacentPoint(Right,directions[3])) == true)
						total[i] += weightsOfI[pos_max_weight.x+1][pos_max_weight.y-1];
					if(this.IsValidMove(GetAdjacentPoint(Left,directions[2])) == true)
						total[i] += weightsOfI[pos_max_weight.x-2][pos_max_weight.y];
					if(this.IsValidMove(GetAdjacentPoint(Left,directions[1])) == true)
						total[i] += weightsOfI[pos_max_weight.x-1][pos_max_weight.y+1];
					if(this.IsValidMove(GetAdjacentPoint(Left,directions[3])) == true)
						total[i] += weightsOfI[pos_max_weight.x-1][pos_max_weight.y-1];
					if(this.IsValidMove(GetAdjacentPoint(Up,directions[3])) == true)
						total[i] += weightsOfI[pos_max_weight.x][pos_max_weight.y-2];
					if(this.IsValidMove(GetAdjacentPoint(Down,directions[1])) == true)
						total[i] += weightsOfI[pos_max_weight.x][pos_max_weight.y+2];
				i++;
			}
			int a=0, max=0;
			for( int k =0; k < total.length ; k++)
			{
				if( total[k] > max)
				{
					max = total[k];
					a = k;
				}
			}
			return maxPoint[a];
		}
		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
	}
