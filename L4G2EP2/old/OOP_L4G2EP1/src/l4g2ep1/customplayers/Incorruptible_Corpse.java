package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/** �� �ڵ�� '���޴��' ���ҿ� ������ �ΰ� �ۼ��Ǿ����ϴ�. */

public class Incorruptible_Corpse extends Player
{
	public Incorruptible_Corpse()
	{
		name = "Incorruptible_Corpse";	
		acceptDirectInfection = true;		//������ ������ �ϵ��� �մϴ�.				
	}
	
	DirectionCode[] directions;	

	/** 
	 * �������� ��� �ִ��� ���� �ױ����� �þ� ���� �����ڸ� �����ϰ� 
	 * ���� �þƿ� �����ڰ� ���ٸ� �����ڰ� ���� ������ ���� ������ ���ϱ� �ٶ�� �������� ��ٸ��ϴ�.
	 * ����ü�� ���� ��� ����ü�� ���� ������, ���� ��� �����ڰ� ���� ������ �̵��ϱ� ������ if / else ���� �ڵ�� �����մϴ�.
	 * 
	 * ������������ ���Ǵ� upward, leftward, downward, rightward �� �÷��̾ �߽����� �� ���� �ݰ��� ���մϴ�.
	 * upward = �÷��̾�� ���ʿ� ��ġ�� 4ĭ 
	 * leftward = �÷��̾�� ���ʿ� ��ġ�� 4ĭ 
	 * downward = �÷��̾�� �Ʒ��ʿ� ��ġ�� 4ĭ 
	 * rightward = �÷��̾�� �����ʿ� ��ġ�� 4ĭ 
	 */
	
	@Override
	public DirectionCode Survivor_Move()		 
	{											
		int upward = 0, leftward = 0, downward = 0, rightward = 0;
		
		for ( PlayerInfo other : othersInfo_withinSight )			//�� �þ� ���� ������ �ľ��Ͽ�		
		{
			if ( other.GetState() == PlayerInfo.State.Infected )	//����ü�� �ִٸ�
			{
				Vector v = GetDistanceVectorBetweenPlayers(other);

				if ( v.y_offset < 0 )		 
					++upward;				//�÷��̾� ���� 4ĭ�� ����ü�� ���� ��� 1 ����
	
				if ( v.x_offset < 0 )
					++leftward;				//�÷��̾� ���� 4ĭ�� ����ü�� ���� ��� 1 ����
								
				if ( v.y_offset > 0 )
					++downward;				//�÷��̾� �Ʒ��� 4ĭ�� ����ü�� ���� ��� 1 ����
				
				if ( v.x_offset > 0 )
					++rightward;			//�÷��̾� ������ 4ĭ�� ����ü�� ���� ��� 1 ����
						
				if (upward >= leftward && upward >= downward && upward >= rightward && this.IsValidMove( directions[0] ) == true)
				{
					return directions [0];		//upward �� ���������� ���ų� ũ�� �������� �� �� �ִٸ� ���� �̵�
				}
				else if (leftward >= upward && leftward >= downward && leftward >= rightward && this.IsValidMove( directions[1] ) == true)
				{
					return directions [1];		//leftward �� ���������� ���ų� ũ�� �������� �� �� �ִٸ� �������� �̵�
				}
				else if (downward >= upward && downward >= leftward && downward >= rightward && this.IsValidMove( directions[2] ) == true)
				{
					return directions [2];		//downward �� ���������� ���ų� ũ�� �Ʒ������� �� �� �ִٸ� �Ʒ��� �̵�
				}
				else if (rightward >= upward && rightward >= leftward && rightward >=downward && this.IsValidMove( directions[3] ) == true)
				{
					return directions [3];		//rightward �� ���������� ���ų� ũ�� ���������� �� �� �ִٸ� ���������� �̵�
				}
				else if (upward == leftward && upward == downward && upward == rightward)	//���� ��� ���� ���� ���� �ִٸ� �� �� �ִ� �������� ����.
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
	public void Corpse_Stay()		//��ü�� ���� ��⸸ �մϴ�.
	{
		
	}
	
	/**
	 * ����ü�� ��� ������ ���� �״� ���� ��ǥ�� �����Դϴ�.
	 * ���� ��ü�� ���� ��ġ�� ���� ���� �̻� �������� �ʰ�
	 * �ε��� �����̴��� �����ڰ� ���� �������� �����̵��� �Ͽ����ϴ�.
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
	 * ���� ���� �ڵ忡�� ���� Evade_Move �Դϴ�. (Survive_Move() ��� ���)
	 * ��ü�� ���� ������ ��� ���ϱ� ���� ����� �������ϴ�.
	 */

	public DirectionCode Evade_Move()	
	{
		int upward = 0, leftward = 0, downward = 0, rightward = 0;
		
		int num_survivor = 0;
		for ( PlayerInfo other : othersInfo_withinSight )		//�� �þ߿� ���̴� �����ڴ� �� �� ���� �ľ�
		{
			if ( other.GetState() == PlayerInfo.State.Survivor )	
				num_survivor++;
		}
			
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			if (num_survivor == 0)										//�þ߿� ���̴� �����ڰ� ���ٸ�
			{			
				if (this.IsValidMove( directions[0] ) == true)			//������ �����ϸ� ����
				{
					return directions [0];
				}
				else if (this.IsValidMove( directions[1] ) == true)	//������ ���� �ϸ� ����
				{
					return directions [1];
				}
				else if (this.IsValidMove( directions[2] ) == true)	//�Ʒ����� �����ϸ� �Ʒ���
				{
					return directions [2];
				}
				else if (this.IsValidMove( directions[3] ) == true)	//�������� �����ϸ� ������
				{
					return directions [3];
				}
			}
			else 														//�����ڰ� �� ���̶� �����Ѵٸ�
			{
				Vector v = GetDistanceVectorBetweenPlayers(other);
							
				if ( v.y_offset == -1 )		
				{
					++upward;				//�÷��̾� ���� 4ĭ�� ���� ������ �� ����
				}
				
				if ( v.x_offset == -1 )		
				{
					++leftward;				//�÷��̾� ���� 4ĭ�� ���� ������ �� ����
				}
									
				if ( v.y_offset == 1 )		
				{
					++downward;				//�÷��̾� �Ʒ��� 4ĭ�� ���� ������ �� ����
				}
					
				if ( v.x_offset == 1 )		
				{
					++rightward;			//�÷��̾� ������ 4ĭ�� ���� ������ �� ����
				}
								
				if ( upward == 0 && this.IsValidMove( directions[0] ) == true)				//���ʿ� �����ڰ� ���� �� �� �ִٸ� ���� �̵�
				{
					return directions [0];
				}
				else if (leftward == 0 && this.IsValidMove( directions[1] ) == true)		//���ʿ� �����ڰ� ���� �� �� �ִٸ� �������� �̵�
				{
					return directions [1];
				}
				else if (downward == 0 && this.IsValidMove( directions[2] ) == true)		//�Ʒ��ʿ� �����ڰ� ���� �� �� �ִٸ� �Ʒ��� �̵�
				{
					return directions [2];
				}
				else if (rightward == 0 && this.IsValidMove( directions[3] ) == true)		//�����ʿ� �����ڰ� ���� �� �� �ִٸ� ���������� �̵�
				{
					return directions [3];
				}
				else 																		//���� ��� ���⿡ �����ڰ� �ִٸ� �׳� �� �� �ִ� ������ ��
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
	public void Soul_Stay()		//�ϴ� ���� �����ϴ�
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
	 * �ִ��� ���� �ð����� �ױ����ؼ� �����ڰ� ���� ���� ���� ���Ͽ� ��Ȱ�մϴ�.
	 * Ȥ�ö� ����ü�� �ϳ��� �������� �ʴٸ� ����� ���� ���� ������ �������ϴ�.
	 */
		
	@Override
	public Point Soul_Spawn()
	{				
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		int num_infected = 0;
		for ( PlayerInfo other : othersInfo_withinSight )		//����ü�� ���̳� �Ǵ��� �˾ƺ��ϴ�.
		{
			if ( other.GetState() == PlayerInfo.State.Infected )	
				num_infected++;
		}
		
		int spawn_x = 0, spawn_y = 0;		
		
		for ( PlayerInfo other : othersInfo_withinSight )			
		{
			if ( num_infected == 0)										//����ü�� �ϳ��� ������ �����ڰ� ���� ���� ĭ�� ã�Ƽ� ��Ȱĭ���� ����
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
			else														//����ü�� ������ ü���� ���� ����ü���� ���� ���� ��Ȱĭ���� ���� 
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
