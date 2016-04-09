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
public class Player_JangHo extends Player
{
	/* �ʹ� �����ڸ� ���� �ν��Ͻ� ���� */
	private int pos_directionToMoveForInitial;
	private DirectionCode[] directionsForInitial;
	
	/* ��ü ��ź�� ���� �ν��ϼ� ���� */
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	Point basePoint;
	
	public Player_JangHo()
	{
		name = "JangHo";	// TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;				// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ� �ƴ� ��� false�� �μ���.
		receiveActions = true;
	}

	void ShuffleDirections()
	{
		//�ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		/*
		 * �� ���� ������ ������ �� �ִ� ����� �� 4 * 3 * 2 * 1 = 24���� �����ϹǷ�
		 * seed�� 24�� ���� �������� ���� ���� ����.
		 * (24������ �׳� switch�� ���°� �� ���� ���������� �ڵ尡 ������� ���� ���)
		 */
		//�� �ڸ����� '���� ����'�ؾ� �ϴ� ������ ���� ����. seed�� 24�� ���� �������� 0�� ��� 0000, 23�� ��� 3210�� ��.
		int[] offsets = new int[4];
		
		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		//������ ������ offset�� ���� �� �ڸ��� ���� ���.
		//��� ����� ������ offset�� 0000�̾��� �� ���� 0123�� �� (�����ϰ� offset�� 3210�̾��� ���� �� �迭 �״�� ���� ��)
		shuffledDirection_values = new int[4];

		for ( int iCurrent = 0; iCurrent < 4; ++iCurrent )
		{
			int current_value = 0;
			
			while ( true )
			{
				//���� �ڸ����� �տ� �̹� ���� ���� �ִ��� �˻� 
				boolean isSameValueFound = false;
				
				for ( int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious )
					if ( shuffledDirection_values[iPrevious] == current_value )
					{
						isSameValueFound = true;
						break;
					}
				
				//���� ���� �ִ� ��� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				if ( isSameValueFound == true )
				{
					++current_value;
				}
				//���� ���� ���� ���� �ڸ��� offset�� 0�� �ƴ� ���(���⼭ ���� �������Ѿ� �ϴ� ���)
				//offset�� 1 ���� ���� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻� 
				else if ( offsets[iCurrent] != 0 )
				{
					--offsets[iCurrent];
					++current_value;
				}
				//���� ���� ���� offset�� 0�� ��� �� ��� �Ϸ�
				else
				{
					break;
				}
			}
			
			//����� ���� ���� �ڸ��� ���� ���
			shuffledDirection_values[iCurrent] = current_value;
		}
				
		//0: Up, 1: Left, 2: Right, 3: Down���� �����Ͽ� �� �ڸ��� ���� ���� ���� ���� ���� 
		shuffledDirections = new DirectionCode[4];
		
		for ( int i = 0; i < 4; ++i )
			switch ( shuffledDirection_values[i] )
			{
			case 0:
				shuffledDirections[i] = DirectionCode.Up;
				break;
			case 1:
				shuffledDirections[i] = DirectionCode.Left;
				break;
			case 2:
				shuffledDirections[i] = DirectionCode.Right;
				break;
			default:
				shuffledDirections[i] = DirectionCode.Down;
				break;
			}
	}
	
	void SetBasePoint()
	{
		//�ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		//seed�� ���ǽ��� �� ĭ ���� ���� �������� ���� ���� ��ǥ ����
		int base_y = seed % ( Constants.Classroom_Width * Constants.Classroom_Height ) / Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;
		
		basePoint = new Point(base_x, base_y);
	}

	
	// ��ó ��ü�� ���� ����������  ȹ��
	int calculateDirectionOfNearObject(Point original, Point target)
	{
		
		if( (original.x == target.x) && ((original.y-target.y)==1))
		{
			// 0 �� ���� �ǹ�, ���ʿ� ��ǥ���� �ִٴ� �Ҹ�
			return 0;	
		}
		else if( (original.x == target.x) && ((original.y-target.y)==-1))
		{
			// 1 �� ���� �ǹ�, ���ʿ� ��ǥ���� �ִٴ� �Ҹ�
			return 1;	
		}
		else if( (original.y == target.y) && ((original.x-target.x)==1))
		{
			// 2 �� ���� �ǹ�, ���ʿ� ��ǥ���� �ִٴ� �Ҹ�
			return 2;	
		}
		else if( (original.y == target.y) && ((original.x-target.x)==-1))
		{
			// 2 �� ���� �ǹ�, ���ʿ� ��ǥ���� �ִٴ� �Ҹ�
			return 3;	
		}
		
		// -1�� �� ����Ϳ��� �ش���� �ʴ´ٴ� �Ҹ�
		return -1;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// ���� �� ��° ������ �޾ƿɴϴ�.
		int nowTurn = gameInfo.GetCurrentTurnNumber();
		
		// goTo[0] = ��, goTo[1] = ��, goTo[2] = ��, goTo[3] = �� 
		int []goTo = new int[4];
		int index = 0;
		int min = Integer.MAX_VALUE;
	
		
		for(int i=0;i<4;i++)
			goTo[i] = 0;
		
		// 30 �ϱ����� �ǽ� ������ ��, "�� ���������� �����ڰ� �Ⱦ�!" ���
		if(nowTurn<=30)
		{
			if(nowTurn<11)
			{
				while(IsValidMove(directionsForInitial[pos_directionToMoveForInitial])==false)
				{
					pos_directionToMoveForInitial++;
					pos_directionToMoveForInitial%=4;
				}
				return directionsForInitial[pos_directionToMoveForInitial];
			}
			
			// 11��° �� ���� ����ü�� ����� ������. �׷��Ƿ� 
			// ��û���� ���شٳ�� ��.
			else if(nowTurn>=11)
			{
				for(PlayerInfo other : othersInfo_withinSight)
				{
					Point targetPosition = other.GetPosition();
					Point myPosition = myInfo.GetPosition();
					
					switch(calculateDirectionOfNearObject(myPosition, targetPosition))
					{
					case 0:
						// ���ʿ� ����ü�� ����
						if(other.GetState()==PlayerInfo.State.Infected)
							goTo[0]++;
						break;
					case 1:
						// ���ʿ� ����ü�� ����
						if(other.GetState()==PlayerInfo.State.Infected)
							goTo[1]++;
						break;
					case 2:
						// ���ʿ� ����ü�� ����
						if(other.GetState()==PlayerInfo.State.Infected)
							goTo[2]++;
						break;
					case 3:
						// ���ʿ� ����ü�� ����
						if(other.GetState()==PlayerInfo.State.Infected)
							goTo[3]++;
						break;
					default:
						break;
					}
					
				}
				
				if( IsValidMove(DirectionCode.Up)==false )
					goTo[0] = Integer.MAX_VALUE;
				if( IsValidMove(DirectionCode.Down)==false )
					goTo[1] = Integer.MAX_VALUE;
				if( IsValidMove(DirectionCode.Left)==false )
					goTo[2] = Integer.MAX_VALUE;
				if( IsValidMove(DirectionCode.Right)==false )
					goTo[3] = Integer.MAX_VALUE;
				
				for(int i:goTo)
				{
					if(i<min)
						min = i;
				}
				
				
				for(index=3;index>=0;index--)
				{
					if(goTo[index]==min)
					{
						break;
					}
				}
				
				switch(index)
				{
				case 0:	return (DirectionCode.Up);
				case 1: return (DirectionCode.Down);
				case 2: return (DirectionCode.Left);
				case 3: return (DirectionCode.Right);
				}
			}
		}
		// 30 �� ���ķδ� �������� ��ü��� ����.
		else if(nowTurn>30)
		{
			DirectionCode result;
			
			do
			{
				result = shuffledDirections[pos_directionToMoveForInitial];
				pos_directionToMoveForInitial++;
				pos_directionToMoveForInitial%=4;
			}while( IsValidMove(result)==false);
			return result;
		}
		
		return DirectionCode.Right;
	}

	@Override
	public void Corpse_Stay()
	{
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
		
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// TODO ����ü ������ �� �̵� �Ǵ� ����ϱ� ���� ������ ���⿡ ��������.
		DirectionCode result;
		
		//�� ��ġ�� ��ü�� �ִ��� �˻�
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
			result = Survivor_Move();
		//�׷��� �ʴٸ� ��ȭ �⵵
		else
			result = DirectionCode.Stay;
 
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
			directionsForInitial = new DirectionCode[4];
			directionsForInitial[0] = DirectionCode.Right;
			directionsForInitial[1] = DirectionCode.Down;
			directionsForInitial[2] = DirectionCode.Left;
			directionsForInitial[3] = DirectionCode.Up;
			ShuffleDirections();
			SetBasePoint();

		}

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn()
	{
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
		/*
		 * ��ü ��ź�� ��ȥ ��ġ: ���� ����ü�� ���� ĭ�� ��� ��ġ
		 */
		Point pointToSpawn = basePoint;

		//�� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//��� ����ü�� ���� �迭 ���� �� �ִ밪 ���, �ִ밪�� ���� ĭ�� ���� ��ǥ ��� ����
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				++weights[pos_other.y][pos_other.x];

				//�ִ밪�� �ٲ���ٸ� '����ü�� ���� ���� ĭ' ��� �ʱ�ȭ
				if ( weights[pos_other.y][pos_other.x] > max_weight )
				{
					++max_weight; //weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
					list_pos_max_weight.clear();
				}
				
				//���� ĭ�� ���� �ִ밪�� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ִ밪�� �ٲ���ٸ� �׻� �߰���)
				if ( weights[pos_other.y][pos_other.x] == max_weight )
					list_pos_max_weight.add(pos_other);
			}
		}
		
		//�˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� '���� ����' ������� ���� �� ������ ���� ����� ù��° ĭ ����
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();
		
		for ( Point pos_max_weight : list_pos_max_weight )
		{
			int distance = GetDistance(myPosition, pos_max_weight);
			
			if ( distance < min_distance )
			{
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}
		
		return pointToSpawn;
	}

}
