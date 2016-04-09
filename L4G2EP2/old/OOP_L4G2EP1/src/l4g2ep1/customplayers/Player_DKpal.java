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
public class Player_DKpal extends Player
{
	public Player_DKpal()
	{
		name = "DKpal";
		acceptDirectInfection = false;
		receiveOthersInfo_detected = true;
		death_point=0;
	}

	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	int death_point;

	Point basePoint;
	
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

	@Override
	public DirectionCode Survivor_Move()
	{
		// TODO �������� �ൿ ������ �״�� ��ƿ�.

		//���⺰�� �� �÷��̾� �� �� ���� ��밪�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] survivors = new int[4];
		int[] others = new int[4];
		int[] weights = new int[4];
		int max_weight = -1;
		
		//���� ������ ��� �÷��̾ ���� �˻� ����
		if(this.gameInfo.GetCurrentTurnNumber()<12)
		{
			for ( PlayerInfo other : othersInfo_detected )
			{
				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);
	
				//�ش� �÷��̾��� ���� ���¿� ���� �÷��̾� �� ���
				if ( other.GetState() == PlayerInfo.State.Survivor )
				{
					if ( v.y_offset < 0 )
						++survivors[0];
		
					if ( v.x_offset < 0 )
						++survivors[1];
					
					if ( v.x_offset > 0 )
						++survivors[2];
					
					if ( v.y_offset > 0 )
						++survivors[3];
				}
			}
			
	
			//���� ��밪: ������ �� x ��ü �� ����ü �� ���
			for ( int i = 0; i < 4; ++i )
				weights[i] = survivors[i];
			
			//�� �� ���� ���⿡ ���� ��밪�� �ּҰ����� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;
	
			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;
	
			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;
	
			//������ ��밪�� �ִ밪�� ������ ���
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			//'���� ����' ������ ���� ��밪�� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
		}
		
		else
		{
			for ( PlayerInfo other : othersInfo_detected )
			{
				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);
	
				//�ش� �÷��̾��� ���� ���¿� ���� �÷��̾� �� ���
				if ( other.GetState() == PlayerInfo.State.Survivor )
				{
					if ( v.y_offset < 0 )
						++survivors[0];
		
					if ( v.x_offset < 0 )
						++survivors[1];
					
					if ( v.x_offset > 0 )
						++survivors[2];
					
					if ( v.y_offset > 0 )
						++survivors[3];
				}
				else
				{
					if ( v.y_offset < 0 )
						++others[0];
		
					if ( v.x_offset < 0 )
						++others[1];
					
					if ( v.x_offset > 0 )
						++others[2];
					
					if ( v.y_offset > 0 )
						++others[3];
				}
			}
	
			//���� ��밪: ������ �� x ��ü �� ����ü �� ���
			for ( int i = 0; i < 4; ++i )
				weights[i] = survivors[i] * others[i];
			
			//�� �� ���� ���⿡ ���� ��밪�� �ּҰ����� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;
	
			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;
	
			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;
	
			//������ ��밪�� �ִ밪�� ������ ���
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			//'���� ����' ������ ���� ��밪�� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
		}
		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
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
		DirectionCode result = DirectionCode.Stay;

		boolean isCorpseHere = false;
		if(this.myInfo.GetHitPoint() < 15 && (this.gameInfo.GetCurrentTurnNumber()-death_point) < 5)
		{		
			//�� ��ġ�� ��ü�� �ִ��� �˻�
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
			
			//��ü�� �ִٸ� ���
			if ( isCorpseHere == true )
				result = DirectionCode.Stay;
			//�׷��� �ʴٸ� ��ȭ �⵵
			else
				result = DirectionCode.Stay;
		}
		else
		{
			int[] weights = new int[4];
			int max_weight = -1;
			
			//�þ� ���� ��� �÷��̾ ���� ������ ĭ�� �����ϰ� ������ �� ���
			for ( PlayerInfo other : othersInfo_withinSight )
			{
				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);

				//������ ĭ(�Ÿ��� 1�� ĭ)�� ���� ���� �����ڿ� ���� ���⺰ �� ����  
				if ( other.GetState() == PlayerInfo.State.Survivor && v.GetDistance() > 1 )
				{
					if ( v.y_offset < 0 )
						++weights[0];
		
					if ( v.x_offset < 0 )
						++weights[1];
					
					if ( v.x_offset > 0 )
						++weights[2];
					
					if ( v.y_offset > 0 )
						++weights[3];
				}
			}
			
			//�� �� ���� ���⿡ ���� ������ ���� �ּҰ����� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;

			//������ ������ ���� �ִ밪�� ������ ���
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			//'���� ����' ������ ���� ������ ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];


		}
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
			basePoint = new Point(2,2);	// ���� �� �⺻ ���� ������ ������ ������� 2,2�� ����.
			death_point=0;				//��ü�� �ȵ� �����ڰ� �Ǳ� ���� �����ڰ� �� �ϼ��� ������ ����.
			ShuffleDirections();
		}

		// TODO ��ü�� �Ǹ� ���� ���� �ϼ��� ����. �����ڰ� �� �ڿ� �����ڰ� ���� �󸶳� �ƴ��� �Ǻ��ϱ� ���� ����.
		death_point = this.gameInfo.GetCurrentTurnNumber();
	}

	@Override
	public Point Soul_Spawn()
	{
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
		// S_max������ 3���� ���� ��쿡�� 3���� ����, �׷��� ������ S_max ���� +1 ���� ���� ������ ��ü �� ���� �ִ� ĭ�� �ִ� ��� �� ĭ�� ������.
		// �ƴ� ��� ������ x ��ü �� ������ �� ���� ��(���� ������ ���� ��)���� ��ġ.  
		Point pointToSpawn = new Point(4,4);

		//�� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü �� ��ü�� ���� ���� ĭ ����� ���ÿ� ����
		if(gameInfo.GetCurrentTurnNumber()>12)
			return new Point(2,2);
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
			if ( other.GetState() == PlayerInfo.State.Corpse )
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
		if(max_weight > 3)
		{
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
		}
		else
		{
			int[][] survivor_value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int[][] other_value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int[][] value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int max_value=-1;
			ArrayList<Point> list_pos_max_value = new ArrayList<Point>();
			
			for(int a=0;a<Constants.Classroom_Width+4;a++)
			{
				for(int b=0;b<Constants.Classroom_Height+4;b++)
				{	
					value[a][b] = survivor_value[a][b] = other_value[a][b] = 0;					
				}
			}
			for( PlayerInfo other : othersInfo_withinSight)
			{
				if(other.GetState() == PlayerInfo.State.Survivor )
				{
					++survivor_value[other.GetPosition().x][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+4];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+4][other.GetPosition().y+2];
				}
				
				else
				{
					++other_value[other.GetPosition().x][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+2][other.GetPosition().y];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+4];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+4][other.GetPosition().y+2];
				}
			}
			
			for(int i=0;i<Constants.Classroom_Width+4;i++)
			{
				for(int j=0;j<Constants.Classroom_Height+4;j++)
				{	
					value[i][j] = survivor_value[i][j] * other_value[i][j];
				}
			}
			
			for(int i=2;i<Constants.Classroom_Width+2;i++)
			{
				for(int j=2;j<Constants.Classroom_Height+2;j++)
				{
					if(value[i][j] > max_value)
					{
						max_value = value[i][j];
						list_pos_max_value.clear();
					}
					if(value[i][j] == max_value)
						list_pos_max_value.add(new Point(i-2, j-2));
				}
			}
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
		}
		
		return pointToSpawn;

	}

}