package l4g2ep1.sampleplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �׻� �����ڸ� ���� �ٴϷ� ����ϴ� ���� �÷��̾��Դϴ�.
 * 
 * ������ �̵�: '���� ����' ������� ����� �� ������ ���� 4���� �� �ֱ�� �ΰ� �ݺ������� ����� �ڸ��� �ɵ��ϴ�.
 * ����ü �̵�: ������ ĭ�� ������ �þ� ���� ������ �����ڰ� ���� ���� �������� �̵��մϴ�.
 * ��ȥ ��ġ: �׻� ���� ��ǥ�� ��ġ�մϴ�.
 * ���� ����: �׻� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class SamplePlayer_Seeker extends Player
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
	 * �����ڴ� �� ���� Ȱ���Ͽ� ������ �̵��� �����մϴ�.
	 */
	int index_directionToMove;
		
	/**
	 * �������� �÷��̾� Ŭ������ �޸� ���� �÷��̾���� �� ���ӿ� ���� ���� �����ϴ� ��찡 �����Ƿ�
	 * �÷��̾ ���� �� �̸� ID�� �߱޹޾� �ش� ���ڸ� �̸� �ڿ� �ٿ� �ݴϴ�.
	 */
	public SamplePlayer_Seeker(int ID)
	{
		name = String.format("������#%d", ID);
		
		//���� ������ �׻� ����
		this.acceptDirectInfection = true;
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
		/*
		 * �������� ����ü �̵�: ������ ĭ�� ������ �þ� ���� ������ �����ڰ� ���� ���� �������� �̵�
		 */
		
		//���⺰�� ������ ���� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
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

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
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
		return basePoint;
	}
}
