package l4g2ep1.sampleplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �ִ��� �����ϰ� ��� ���� ��� �÷��̾�Լ� �������� ����ϴ� ���� �÷��̾��Դϴ�.
 * 
 * ������ �̵�: �ٸ� �÷��̾�(������, ��ü, ����ü)�� ���� ���� �������� �̵��մϴ�.
 * ����ü �̵�: �׻� ������ ���ڸ��� �ӹ����ϴ�.
 * ��ȥ ��ġ: �׻� ���� ��ǥ�� ��ġ�մϴ�.
 * ���� ����: �׻� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class SamplePlayer_Loner extends Player
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
	 * �������� �÷��̾� Ŭ������ �޸� ���� �÷��̾���� �� ���ӿ� ���� ���� �����ϴ� ��찡 �����Ƿ�
	 * �÷��̾ ���� �� �̸� ID�� �߱޹޾� �ش� ���ڸ� �̸� �ڿ� �ٿ� �ݴϴ�.
	 */
	public SamplePlayer_Loner(int ID)
	{
		name = String.format("������#%d", ID);
		
		//���� ������ �׻� ����
		this.acceptDirectInfection = false;
		
		//�����̴� ������ �̵����� �ٸ� �÷��̾ ���� ���� �������� �̵��ϱ� ���� ���� ������ ������
		this.receiveOthersInfo_detected = true;
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
		/*
		 * �������� ������ �̵�: �ٸ� �÷��̾ ���� ���� ������ ����
		 */
		
		//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;
		
		//���� ������ ��� �÷��̾ ���� ���赵 ���
		for ( PlayerInfo other : othersInfo_detected )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);

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

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}

	@Override
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	{
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
