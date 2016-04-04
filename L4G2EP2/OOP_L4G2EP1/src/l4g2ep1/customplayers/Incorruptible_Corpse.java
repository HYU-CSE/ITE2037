package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/** 본 코드는 '구급대원' 역할에 초점을 두고 작성되었습니다. */

public class Incorruptible_Corpse extends Player
{
	public Incorruptible_Corpse()
	{
		name = "Incorruptible_Corpse";	
		acceptDirectInfection = true;		//감염은 무조건 하도록 합니다.				
	}
	
	DirectionCode[] directions;	

	/** 
	 * 생존자일 경우 최대한 빨리 죽기위해 시야 내의 감염자를 수색하고 
	 * 만약 시아에 감염자가 없다면 생존자가 많은 곳으로 가서 누군가 변하길 바라는 마음으로 기다립니다.
	 * 감염체가 있을 경우 감염체가 많은 곳으로, 없을 경우 생존자가 많은 곳으로 이동하기 때문에 if / else 문의 코드는 동일합니다.
	 * 
	 * 수색과정에서 사용되는 upward, leftward, downward, rightward 는 플레이어를 중심으로 한 주위 반경을 뜻합니다.
	 * upward = 플레이어보다 위쪽에 위치한 4칸 
	 * leftward = 플레이어보다 왼쪽에 위치한 4칸 
	 * downward = 플레이어보다 아래쪽에 위치한 4칸 
	 * rightward = 플레이어보다 오른쪽에 위치한 4칸 
	 */
	
	@Override
	public DirectionCode Survivor_Move()		 
	{											
		int upward = 0, leftward = 0, downward = 0, rightward = 0;
		
		for ( PlayerInfo other : othersInfo_withinSight )			//내 시야 내의 정보를 파악하여		
		{
			if ( other.GetState() == PlayerInfo.State.Infected )	//감염체가 있다면
			{
				Vector v = GetDistanceVectorBetweenPlayers(other);

				if ( v.y_offset < 0 )		 
					++upward;				//플레이어 위쪽 4칸에 감염체가 있을 경우 1 증가
	
				if ( v.x_offset < 0 )
					++leftward;				//플레이어 왼쪽 4칸에 감염체가 있을 경우 1 증가
								
				if ( v.y_offset > 0 )
					++downward;				//플레이어 아래쪽 4칸에 감염체가 있을 경우 1 증가
				
				if ( v.x_offset > 0 )
					++rightward;			//플레이어 오른쪽 4칸에 감염체가 있을 경우 1 증가
						
				if (upward >= leftward && upward >= downward && upward >= rightward && this.IsValidMove( directions[0] ) == true)
				{
					return directions [0];		//upward 가 나머지보다 같거나 크고 위쪽으로 갈 수 있다면 위로 이동
				}
				else if (leftward >= upward && leftward >= downward && leftward >= rightward && this.IsValidMove( directions[1] ) == true)
				{
					return directions [1];		//leftward 가 나머지보다 같거나 크고 왼쪽으로 갈 수 있다면 왼쪽으로 이동
				}
				else if (downward >= upward && downward >= leftward && downward >= rightward && this.IsValidMove( directions[2] ) == true)
				{
					return directions [2];		//downward 가 나머지보다 같거나 크고 아래쪽으로 갈 수 있다면 아래로 이동
				}
				else if (rightward >= upward && rightward >= leftward && rightward >=downward && this.IsValidMove( directions[3] ) == true)
				{
					return directions [3];		//rightward 가 나머지보다 같거나 크고 오른쪽으로 갈 수 있다면 오른쪽으로 이동
				}
				else if (upward == leftward && upward == downward && upward == rightward)	//만약 모두 같은 값을 갖고 있다면 갈 수 있는 방향으로 진행.
				{																			
					if (this.IsValidMove( directions[0] ) == true)							
					{																		
						return directions [0];
					}
					else if (this.IsValidMove( directions[1] ) == true)
					{
						return directions [1];
					}
					else if (this.IsValidMove( directions[2] ) == true)
					{
						return directions [2];
					}
					else if (this.IsValidMove( directions[3] ) == true)
					{
						return directions [3];
					}
				}
			}
			else 
			{
				if ( other.GetState() == PlayerInfo.State.Survivor )
				{
					Vector v = GetDistanceVectorBetweenPlayers(other);

					if ( v.y_offset < 0 )
						++upward;
		
					if ( v.x_offset < 0 )
						++leftward;
									
					if ( v.y_offset > 0 )
						++downward;
					
					if ( v.x_offset > 0 )
						++rightward;
					
					if (upward >= leftward && upward >= downward && upward >= rightward && this.IsValidMove( directions[0] ) == true)
					{
						return directions [0];
					}
					else if (leftward >= upward && leftward >= downward && leftward >= rightward && this.IsValidMove( directions[1] ) == true)
					{
						return directions [1];
					}
					else if (downward >= upward && downward >= leftward && downward >= rightward && this.IsValidMove( directions[2] ) == true)
					{
						return directions [2];
					}
					else if (rightward >= upward && rightward >= leftward && rightward >=downward && this.IsValidMove( directions[3] ) == true)
					{
						return directions [3];
					}
					else if (upward == leftward && upward == downward && upward == rightward)
					{
						if (this.IsValidMove( directions[0] ) == true)
						{
							return directions [0];
						}
						else if (this.IsValidMove( directions[1] ) == true)
						{
							return directions [1];
						}
						else if (this.IsValidMove( directions[2] ) == true)
						{
							return directions [2];
						}
						else if (this.IsValidMove( directions[3] ) == true)
						{
							return directions [3];
						}
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public void Corpse_Stay()		//시체일 때는 대기만 합니다.
	{
		
	}
	
	/**
	 * 감염체일 경우 무조건 빨리 죽는 것을 목표로 움직입니다.
	 * 따라서 시체랑 같은 위치에 있지 않은 이상 움직이지 않고
	 * 부득이 움직이더라도 생존자가 없는 방향으로 움직이도록 하였습니다.
	 */

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result;
		
		boolean isCorpseHere = false;
		CellInfo here = GetCellInfo(myInfo.GetPosition());
		
		for ( int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer )
		{
			PlayerInfo other = here.GetPlayerInfo(iPlayer);
			
			if ( other.GetState() == PlayerInfo.State.Corpse )
			{
				isCorpseHere = true;
				break;
			}
		}
		
		if ( isCorpseHere == true )
			result = Evade_Move();	
		else
			result = DirectionCode.Stay;
 
		return result;
	}
	
	/**
	 * 원래 샘플 코드에는 없는 Evade_Move 입니다. (Survive_Move() 대신 사용)
	 * 시체랑 같이 겹쳤을 경우 피하기 위한 방법을 적었습니다.
	 */

	public DirectionCode Evade_Move()	
	{
		int upward = 0, leftward = 0, downward = 0, rightward = 0;
		
		int num_survivor = 0;
		for ( PlayerInfo other : othersInfo_withinSight )		//내 시야에 보이는 생존자는 몇 명 인지 파악
		{
			if ( other.GetState() == PlayerInfo.State.Survivor )	
				num_survivor++;
		}
			
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			if (num_survivor == 0)										//시야에 보이는 생존자가 없다면
			{			
				if (this.IsValidMove( directions[0] ) == true)			//위쪽이 가능하면 위쪽
				{
					return directions [0];
				}
				else if (this.IsValidMove( directions[1] ) == true)	//왼쪽이 가능 하면 왼쪽
				{
					return directions [1];
				}
				else if (this.IsValidMove( directions[2] ) == true)	//아래쪽이 가능하면 아래쪽
				{
					return directions [2];
				}
				else if (this.IsValidMove( directions[3] ) == true)	//오른쪽이 가능하면 오른쪽
				{
					return directions [3];
				}
			}
			else 														//생존자가 한 명이라도 존재한다면
			{
				Vector v = GetDistanceVectorBetweenPlayers(other);
							
				if ( v.y_offset == -1 )		
				{
					++upward;				//플레이어 위쪽 4칸에 대한 생존자 수 증가
				}
				
				if ( v.x_offset == -1 )		
				{
					++leftward;				//플레이어 왼쪽 4칸에 대한 생존자 수 증가
				}
									
				if ( v.y_offset == 1 )		
				{
					++downward;				//플레이어 아래쪽 4칸에 대한 생존자 수 증가
				}
					
				if ( v.x_offset == 1 )		
				{
					++rightward;			//플레이어 오른쪽 4칸에 대한 생존자 수 증가
				}
								
				if ( upward == 0 && this.IsValidMove( directions[0] ) == true)				//위쪽에 생존자가 없고 갈 수 있다면 위로 이동
				{
					return directions [0];
				}
				else if (leftward == 0 && this.IsValidMove( directions[1] ) == true)		//왼쪽에 생존자가 없고 갈 수 있다면 왼쪽으로 이동
				{
					return directions [1];
				}
				else if (downward == 0 && this.IsValidMove( directions[2] ) == true)		//아래쪽에 생존자가 없고 갈 수 있다면 아래로 이동
				{
					return directions [2];
				}
				else if (rightward == 0 && this.IsValidMove( directions[3] ) == true)		//오른쪽에 생존자가 없고 갈 수 있다면 오른쪽으로 이동
				{
					return directions [3];
				}
				else 																		//만약 모든 방향에 생존자가 있다면 그냥 갈 수 있는 곳으로 감
				{																			
					if (this.IsValidMove( directions[0] ) == true)
					{
						return directions [0];
					}
					else if (this.IsValidMove( directions[1] ) == true)
					{
						return directions [1];
					}
					else if (this.IsValidMove( directions[2] ) == true)
					{
						return directions [2];
					}
					else if (this.IsValidMove( directions[3] ) == true)
					{
						return directions [3];
					}
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void Soul_Stay()		//하는 일은 없습니다
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			directions = new DirectionCode[4];
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
		}
	}
	
	/**
	 * 최대한 빠른 시간내에 죽기위해서 감염자가 제일 많은 곳을 택하여 부활합니다.
	 * 혹시라도 감염체가 하나도 존재하지 않다면 사람이 가장 많은 곳으로 떨어집니다.
	 */
		
	@Override
	public Point Soul_Spawn()
	{				
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		int num_infected = 0;
		for ( PlayerInfo other : othersInfo_withinSight )		//감염체가 몇이나 되는지 알아봅니다.
		{
			if ( other.GetState() == PlayerInfo.State.Infected )	
				num_infected++;
		}
		
		int spawn_x = 0, spawn_y = 0;		
		
		for ( PlayerInfo other : othersInfo_withinSight )			
		{
			if ( num_infected == 0)										//감염체가 하나도 없으면 생존자가 가장 많은 칸을 찾아서 부활칸으로 설정
			{
				if ( other.GetState() == PlayerInfo.State.Survivor )
				{
					Point pos_other = other.GetPosition();
					weights[pos_other.y][pos_other.x]++;	 
					
					if ( weights[pos_other.y][pos_other.x] > max_weight )
					{
						max_weight = weights[pos_other.y][pos_other.x];
						list_pos_max_weight.clear();
					}
				
					if ( weights[pos_other.y][pos_other.x] == max_weight )
					{
						spawn_y = pos_other.y;
						spawn_x = pos_other.x;
					}
				}
			}
			else														//감염체가 있으면 체력이 낮은 감염체들이 많은 곳을 부활칸으로 설정 
			{															
				if ( other.GetState() == PlayerInfo.State.Infected )
				{
					Point pos_other = other.GetPosition();
				
					int needToHeal = 10 - other.GetHitPoint();
				
					if ( needToHeal < 1 )
						needToHeal = 1;
				
					weights[pos_other.y][pos_other.x] += needToHeal;
					if ( weights[pos_other.y][pos_other.x] > max_weight )
					{
						max_weight = weights[pos_other.y][pos_other.x];
						list_pos_max_weight.clear();
					}
				
					if ( weights[pos_other.y][pos_other.x] == max_weight )
					{
						spawn_y = pos_other.y;
						spawn_x = pos_other.x;
					}
				}
			}
		}
		
		Point pointToSpawn =  new Point(spawn_y,spawn_x);
		
		return pointToSpawn;
	}
}
