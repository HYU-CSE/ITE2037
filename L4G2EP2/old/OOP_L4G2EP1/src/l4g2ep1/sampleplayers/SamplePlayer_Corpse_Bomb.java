package l4g2ep1.sampleplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * ����ü�� ���� ���� ĭ�� ��ٷ� �����ϴ� ���� �÷��̾��Դϴ�.
 * 
 * ������ �̵�: '���� ����' ������� ����� �� ������ ���� 4���� �� �ֱ�� �ΰ� �ݺ������� ����� �ڸ��� �ɵ��ϴ�.
 * ����ü �̵�: �� ��ġ�� ��ü�� �ִ� ��� �ֺ� ĭ���� �̵��ϸ� �׷��� ���� ��� ��ȭ �⵵�� �ø��ϴ�.
 * ��ȥ ��ġ: ����ü�� ���� ���� �ִ� ĭ���� ��ġ�մϴ�. (��κ��� ��� ��ġ ��� ����մϴ�)
 * ���� ����: ID�� ¦���� ��� �����մϴ�. �׷��� ���� ��� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class SamplePlayer_Corpse_Bomb extends Player
{
	/**
	 * '���� ����' ��ɿ� ���Ǵ� ���� ��� �� �ش� ������ ��Ÿ���� ���� ����Դϴ�.
	 * ��� ���� �÷��̾���� ���� ����� �� �ϳ��� ������ �� ���� �ִ� ��Ͽ��� �� �տ� �ִ� ���� �켱���մϴ�.
	 * �� ����� ���� ��ȣ�� ID�� ���� ù ���� Soul_Stay()���� �����˴ϴ�.
	 */
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;

	/**
	 * '���� ����' ��ɿ� ���Ǵ� ���� ��ǥ�Դϴ�.
	 * ��� ���� �÷��̾���� ���� ��ǥ�� �� �ϳ��� ������ �� ���� ��ǥ���� �Ÿ��� ����� ���� �켱���մϴ�.
	 * �� ��ǥ���� ���� ��ȣ�� ID�� ���� ù ���� Soul_Stay()���� �����˴ϴ�. 
	 */
	Point basePoint;
	
	/**
	 * �̹� �̵����� �����ϰ� �� ������ '���� ����' ����� ���� ���� ���� ��� �� �� ��° �������� ��Ÿ���ϴ�.
	 * ��ü ��ź�� �� ���� Ȱ���Ͽ� ������ / ����ü �̵��� �����մϴ�.
	 */
	int index_directionToMove;
		
	/**
	 * �������� �÷��̾� Ŭ������ �޸� ���� �÷��̾���� �� ���ӿ� ���� ���� �����ϴ� ��찡 �����Ƿ�
	 * �÷��̾ ���� �� �̸� ID�� �߱޹޾� �ش� ���ڸ� �̸� �ڿ� �ٿ� �ݴϴ�.
	 */
	public SamplePlayer_Corpse_Bomb(int ID)
	{
		name = String.format("��ü ��ź#%d", ID);
		
		//'ID�� ¦������ ����'�� '���� ���� ���� ����'�� ��		
		this.acceptDirectInfection = ID % 2 == 0;
	}
	
	/**
	 * '���� ����' ����� ����ϱ� ���� ���� ����� �ʱ�ȭ�մϴ�.
	 * �� �޼���� ù ���� Soul_Stay()���� ȣ��Ǿ�� �մϴ�.
	 */
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

	/**
	 * '���� ����' ����� ����ϱ� ���� ���� ��ǥ�� �ʱ�ȭ�մϴ�.
	 * �� �޼���� ù ���� Soul_Stay()���� ȣ��Ǿ�� �մϴ�.
	 */
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

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result;
		
		//�̵��� �� �ִ� �ѵ� ������ '���� ����' ������� ���� �� ������ ���� ���������� �̵� ���� - �̵� ������ ������ �ּ� �� �̻� ����
		do
		{
			result = shuffledDirections[index_directionToMove];
			++index_directionToMove;
			index_directionToMove %= 4;
		}
		while ( IsValidMove(result) == false );
		
		return result;
	}

	@Override
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	{
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
		
		//��ü�� �ִٸ� ������ �̵��� ���� ������� �ֺ� ĭ���� �̵�
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
		//�̹� ���� ù ���� ��� '���� ����' ��ɰ� '���� ����' ��ɿ� �ʿ��� �ʵ� �ʱ�ȭ
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			ShuffleDirections();
			SetBasePoint();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
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
