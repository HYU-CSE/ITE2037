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
public class Player_YOURNAMEHERE extends Player
{

	private int[] shuffledDirection_values;
	private DirectionCode[] shuffledDirections;
	
	public Player_YOURNAMEHERE()
	{
	
		name = "�Ѽ���";	// TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;				// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ� �ƴ� ��� false�� �μ���.
		
		receiveOthersInfo_detected = true;
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		
		DirectionCode result = DirectionCode.Stay;
		//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int[] survivors = new int[4];
		int[] others = new int[4];
		int max_weight = -1;
		
		int min_weight = Integer.MAX_VALUE;
		
	
		
		if(this.myScore.GetSurvivor_Max_Survived_Turns()<10)
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
			

			//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
		}
		else 
		{
			/*
			 * �������� ������ �̵�: �ٸ� �÷��̾ ���� ���� ������ ����
			 */
			
			//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			
			
			//���� ������ ��� �÷��̾ ���� ���赵 ���
			for ( PlayerInfo other : othersInfo_detected )
			{
				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);
				
				if ( other.GetState() == PlayerInfo.State.Infected )
				{

					//�ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
					if ( v.y_offset < 0 )
						++weights[0];

					//�ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if ( v.x_offset < 0 )
						++weights[1];
				
					//�ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if ( v.x_offset > 0 )
						++weights[2];
				
					//�ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if ( v.y_offset > 0 )
						++weights[3];
				}
			}
			
			//�� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = Integer.MAX_VALUE;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = Integer.MAX_VALUE;

			//������ ���赵�� �ּҰ��� ������ ���
			for ( int weight : weights )
				if ( weight < min_weight )
					min_weight = weight;
			
			//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == min_weight )
					return shuffledDirections[iShuffledDirection];
				
			
			//�̵��� �� �ִ� �ѵ� ������ '���� ����' ������� ���� �� ������ ���� ���������� �̵� ���� - �̵� ������ ������ �ּ� �� �̻� ����
			
				
				
	
		}
		// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Stay;
		/*
		 * �������� ����ü �̵�: �� ��ġ�� ��ü�� �ִ� ��� ���ڸ��� �ӹ���. �׷��� ���� ��� ������ �� ��ü�� ���� ���� �������� �̵�.
		 */
		
		//�� ��ġ�� ��ü�� �ִ��� ���� �˻�
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
		
		//��ü�� �ִ� ��� ���ڸ��� �ӹ���
		if ( isCorpseHere == true )
			return DirectionCode.Stay;
		
		//���⺰�� ������ �� ��ü�� ���� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int max_weight = -1;
		
		//�þ� ���� ��� �÷��̾ ���� ������ �� ��ü �� ���
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);

			if ( other.GetState() != PlayerInfo.State.Infected )
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
		
		//�� �� ���� ���⿡ ���� ������ �� ��ü ���� �ּҰ����� ����
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;

		//������ ������ �� ��ü ���� �ִ밪�� ������ ���
		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		
		//'���� ����' ������ ���� ������ �� ��ü ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return result;

		// TODO ����ü ������ �� �̵� �Ǵ� ����ϱ� ���� ������ ���⿡ ��������.
		
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
				
				shuffledDirection_values = new int [4];
				shuffledDirections = new DirectionCode[4];
				
				shuffledDirection_values[0]=0;
				shuffledDirection_values[1]=3;
				shuffledDirection_values[2]=2;
				shuffledDirection_values[3]=1;
				
				shuffledDirections[0] = DirectionCode.Up;
				shuffledDirections[1] = DirectionCode.Down;
				shuffledDirections[2] = DirectionCode.Right;
				shuffledDirections[3] = DirectionCode.Left;
				
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
		}
		
		

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn()
	{
		/*
		 * ��ü ��ź�� ��ȥ ��ġ: ���� ����ü�� ���� ĭ�� ��� ��ġ
		 */
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		int base_y = seed % ( Constants.Classroom_Width * Constants.Classroom_Height ) / Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;
		
		Point basePoint = new Point(base_x, base_y);
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

		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
		
	}

}
