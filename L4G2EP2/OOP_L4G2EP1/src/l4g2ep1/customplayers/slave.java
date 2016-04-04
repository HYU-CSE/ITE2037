package l4g2ep1.customplayers;

import java.util.ArrayList;


import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
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
		name = "slave";	// TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;	
		receiveOthersInfo_detected = true;
		// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ� �ƴ� ��� false�� �μ���.
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		Point myPosition = myInfo.GetPosition();
		ArrayList<Point> listOfDefected = new ArrayList<Point>();
		ArrayList<Point> listOfSurvivor = new ArrayList<Point>();
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int[][] weightsOfSurvivor = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		
		if( gameInfo.GetCurrentTurnNumber() <= 11 ) // 11�� �����϶� �����ڳ��� ���� ��� ������ ���ϱ�
		{
			for ( PlayerInfo other : othersInfo_withinSight ) // �� �þ� �˻�
			{
				if ( other.GetState() == PlayerInfo.State.Survivor ) //�����ڰ� ������
				{
					Point pos_other = other.GetPosition();
					if(weightsOfSurvivor[pos_other.y][pos_other.x] == 0) //�ߺ�����
					{
						++weightsOfSurvivor[pos_other.y][pos_other.x];
						listOfSurvivor.add(pos_other);	
					}
				}
			}
			if(listOfSurvivor.isEmpty() == true) // �ƹ��� ���ٸ�.
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
			else // ������ ���ؼ� �����ٴ�
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
				if( distance2 == 1 && i == 1) // �Ÿ� 2 �� �����ڰ� �ϳ��̸�
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
					if( totalx - myPosition.x*i >=i) // �� ������
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
					else if( totalx - myPosition.x*i <= -i) // �� ����
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
					else if( totaly - myPosition.y*i <= -i) // �� ����
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
					else if( totaly - myPosition.y*i >= i) // �� ����
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
					else if( totalx - myPosition.x*i == 0) // �� ���Ʒ�
					{
						if(this.IsValidMove(directions[0]) == false)
							return directions[2];
					
						return directions[0];
					}
					else if( totaly - myPosition.y*i == 0) // �� �¿�
					{
						if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
						return directions[3];
					}
				}
			}
		//����ü Ž��
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				if(weights[pos_other.y][pos_other.x] == 0) //�ߺ�����
				{
					++weights[pos_other.y][pos_other.x];
					listOfDefected.add(pos_other);	
				}
			}
		}
		
		if(listOfDefected.isEmpty() == true)  // ����ü�� ���þ߿� ���ٸ�? 
		{
			ArrayList<Point> listOfDefected2 = new ArrayList<Point>();
			for ( PlayerInfo other : othersInfo_detected ) // ģ���� �þ� �˻���.
			{
				if ( other.GetState() == PlayerInfo.State.Infected )
				{
					Point pos_other = other.GetPosition();
					listOfDefected2.add(pos_other);	
				}
			}
			if(listOfDefected2.isEmpty() == true) // ģ���� �þ߿��� ���ٸ�?
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
			else // ģ���� �þ߿��� ���� -> ����ü�� �ٰ�����!
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
		else // �� �þ߿� ����ü�� �ִٸ� 
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
			
			if( ( distance2 == 1 || distance1 == 1 ) && i == 1) // �Ÿ� 2 �� ����ü�� �ϳ��̸�
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
			else // ������
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
				
				if( totalx - myPosition.x*i >=i) // �� ������
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
				else if( totalx - myPosition.x*i <= -i) // �� ����
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
				else if( totaly - myPosition.y*i <= -i) // �� ����
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
				else if( totaly - myPosition.y*i >= i) // �� ����
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
				else if( totalx - myPosition.x*i == 0) // �� ���Ʒ�
				{
					if(this.IsValidMove(directions[0]) == false)
							return directions[2];
					
					return directions[0];
				}
				else if( totaly - myPosition.y*i == 0) // �� �¿�
				{
					if(this.IsValidMove(directions[3]) == false)
							return directions[1];
					
					return directions[3];
				}
			}
		}
		//Ȥ�� �� �ڵ尡 �ȵ��� ������.
		while (this.IsValidMove(directions[pos_directionToMove]) == false)
		{
			++pos_directionToMove;
			pos_directionToMove %= 4;
		}
		return directions[pos_directionToMove];
		// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������. ;������� 0~3 ��������
	}

	@Override
	public void Corpse_Stay()
	{
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}
	
	int checkright = 0; // ���� ���� ��ü���� �˻� (�ǵ��� ���� �ʱ�����)
	int checkleft = 0;
	int checkup = 0;
	int checkdown = 0;
	@Override
	public DirectionCode Infected_Move()
	{
		// TODO ����ü ������ �� �̵� �Ǵ� ����ϱ� ���� ������ ���⿡ ��������.	
		Point myPosition = myInfo.GetPosition();
		ArrayList<Point> listOfCorpse = new ArrayList<Point>();
		
	    // ��ü Ž�� 
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
			if ( distance == 1 ) // �Ÿ�1�� ��ü -> �ű�� ����
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
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
		}
		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn()
	{   // ��ü���� �ʱ�ȭ
		checkright = 0; 
		checkleft = 0;
		checkup = 0;
		checkdown = 0;
		
		if( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			return new Point(2,2);
		}
		 // �ѹ� �׾��ٸ� �������� �ڻ�
		
			int[][] weightsOfI = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int[][] weightsOfC = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int max_weight = -1;
			ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

			for ( PlayerInfo other : othersInfo_withinSight )  //����ü,��ü �����˻�
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
			for ( PlayerInfo other : othersInfo_withinSight )  //����ü,��ü �����˻�
			{
				if ( other.GetState() == PlayerInfo.State.Corpse )
				{
					Point pos_other = other.GetPosition();
					weightsOfC[pos_other.y][pos_other.x]++;
				}
			}
			
			if(list_pos_max_weight.isEmpty() == true) //����ü�� ������
				return new Point(2,2);

			int []total = new int[list_pos_max_weight.size()];
			Point []maxPoint = new Point[list_pos_max_weight.size()];
			
			int i=0;
			for ( Point pos_max_weight : list_pos_max_weight ) // ���� ����ġ �ΰ�
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
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
	}
