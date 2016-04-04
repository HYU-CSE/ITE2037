package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;


/**
 * ���� ���ӿ� ������ �÷��̾��Դϴ�.
 * 
 * ������ �̵�: ���� ���� �ǻ簡 �ݿ��Ǳ� ��(0~10��)���� �ٸ� �÷��̾ ������ ���մϴ�.
 *                   ���� ���� �ǻ簡 �ݿ��ǰ� ����(11��), 20�� �������� ��ü�� ����ü�� �ִ��� ���ϰ�, �� ���ĺ��ʹ� ���� ������ ���̱� ���� ������ �� x ��ü �� ����ü ���� ���� ū �������� �̵��մϴ�.
 * ����ü �̵�: ���� �� ���� �ִ� ���� �� ���� ���� 10 �̸��� ��� ������ ĭ�� ������ �þ� ���� ������ �����ڰ� ���� ���� �������� �̵��մϴ�.
 *                   �� ��, ������ ���� ������� ������ �̵��� �� �ִ� ��ġ�� ��ü�� �ִ����� ���ǰ�, ������ ��ü�� �ִ� ������ �̵�, ���� ��� ��ȭ �⵵�� �ø��ϴ�.
 *                   ���� �� ���� �ִ� ���� �� ���� ���� 10 �̻��� ��� �� ��ġ�� ��ü�� �ִ� ��� �ֺ� ĭ���� �̵��ϸ� �׷��� ���� ��� ��ȭ �⵵�� �ø��ϴ�.
 * ��ȥ ��ġ: �����ں��� ��ü+����ü ���� ����, �ִ� ���� �� ���� 15 �̸��̰�, ���� �� ���� 75 �̸��̸� �������� ��ġ�մϴ�. 
 *                �׷��� ���� ���, ���� ����ü�� ���� ĭ�� �켱���� �ϰ�,
 *                ����ü�� ���ڰ� ���� ù ���� ���� ȹ�� ����� ���� ĭ�� ���� ���� ��� ��ü�� ������ 3���� �� ����� ����Ͽ� ���� ���� ĭ���� ��ġ�մϴ�.
 * ���� ����: �׻� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class Player_2012004678_YoungJu_YOON extends Player
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
	 * ���޴���� �� ���� Ȱ���Ͽ� ������ / ����ü �̵��� �����մϴ�.
	 */
	int index_directionToMove;

	/**
	 * �÷��̾� Ŭ������ �������Դϴ�.
	 */
	public Player_2012004678_YoungJu_YOON()
	{
		name = "NEWBIE^_^";
		
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
		
		// �� ���� 11 �̸��� ��� ������ ���شٴմϴ�. �ƹ��� �� ����.
		if(this.gameInfo.GetCurrentTurnNumber() < 11){
			
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
			
		}
		
		// �� ���� 11 �̻� 20 �̸��� ��� ��ü�� ����ü�� �ִ��� ���մϴ�. 
		else if(this.gameInfo.GetCurrentTurnNumber() >= 11 && this.gameInfo.GetCurrentTurnNumber() < 20){
			
			//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//���� ������ ��� �÷��̾ ���� ���赵 ���
			for ( PlayerInfo other : othersInfo_detected )
			{

				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);
				
				// ������ �÷��̾ ����ü ������ ��
				if ( other.GetState() == PlayerInfo.State.Infected ){

					//�ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
					if ( v.y_offset < 0 )
						weights[0] += 3;
	
					//�ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if ( v.x_offset < 0 )
						weights[1] += 3;
					
					//�ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if ( v.x_offset > 0 )
						weights[2] += 3;
					
					//�ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if ( v.y_offset > 0 )
						weights[3] += 3;
				
				}
				
				// ������ �÷��̾ ��ü ������ ��. ��ü�� �� �����̴ϱ� �� ĭ�� �˻��ϸ� �� ��?
				// ����ġ : ���� ��ü��� �ν����� �� ��� Ȯ�� 1/3�̹Ƿ� ����ü�� 1/3
				else if ( other.GetState() == PlayerInfo.State.Infected ){

					//�ش� �÷��̾ ������ �� ĭ ���� �ִٸ� ���� ���� ���� ������ ��?
					if ( v.y_offset == -1 )
						weights[0] += 1;
	
					//�ش� �÷��̾ ������ �� ĭ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if ( v.x_offset == -1 )
						weights[1] += 1;
					
					//�ش� �÷��̾ ������ �� ĭ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if ( v.x_offset == 1 )
						weights[2] += 1;
					
					//�ش� �÷��̾ ������ �� ĭ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if ( v.y_offset == 1 )
						weights[3] += 1;
				}
				
				// ����ü�� ��ü�� �ƴϸ� �ƹ��͵� �� ��
				else{
				
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
			
		
		}
		
		// �� ���� 20�� ������ �ִ��� ���� ������ ���� �ױ� ���� ���������� �̵��մϴ�
		// ���� ������ ä��� ���� ��Ȱ�Ѵٸ� ������ ����� �ɴϴ�. ������ �׶� ���� �ִ� ���� �� �� ä��� �ٸ� ������ ��������.
		else{
			
				/*
				 * �������� ������ �̵�: ������ �� x ��ü�� ����ü ���� ���� ū ������ ����
				 */
				
				//���⺰�� �� �÷��̾� �� �� ���� ��밪�� ����ϱ� ���� �迭 ���
				//0: Up, 1: Left, 2: Right, 3: Down
				int[] survivors = new int[4];
				int[] others = new int[4];
				int[] weights = new int[4];
				int max_weight = -1;
				
				//���� ������ ��� �÷��̾ ���� �˻� ����
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
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	{
		
		DirectionCode result;
		
		// �ϴ� ������ ����� �Ƶչٵ��ϴ� �����ڵ��� ��ƸԾ� ���ô�
		if(this.gameInfo.GetCurrentTurnNumber() - this.myScore.GetSurvivor_Max_Survived_Turns() < 10){
			
			//���⺰�� ������ ���� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			double[] weights = new double[4];
			double max_weight = -1;
			
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
				//�þ߿� ���� �������� �� ���� 
				else if ( other.GetState() == PlayerInfo.State.Corpse && v.GetDistance() > 2 )
				{
					if ( v.y_offset < 0 )
						weights[0] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.x_offset < 0 )
						weights[1] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.x_offset > 0 )
						weights[2] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.y_offset > 0 )
						weights[3] += 2 / (v.GetDistance() * v.GetDistance());
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
			for (double weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			// �̵� �ǻ� ����
			// 1. ����غ��� �����ڰ� ���� ����. �� ���� �͵� ���µ� ��ü�Ա� ������ ���� �� �ֳ� ����?
			if( max_weight < 1 ){
				
				// ���⺰�� ��ü ���� ����ϱ� ���� �迭 ���
				//0: Up, 1: Left, 2: Right, 3: Down
				int[] corpse_counter = new int[4];
				int max_corpse = -1;
				
				// ���� �Ͽ� �̵� ������ �ڸ��� ���� ��ü �� ���
				for ( PlayerInfo other : othersInfo_withinSight ){
					
					Vector v = GetDistanceVectorBetweenPlayers(other);

					//������ ĭ(�Ÿ��� 1�� ĭ)�� �ִ� ��ü�� ���� ���⺰ �� ����  
					if ( other.GetState() == PlayerInfo.State.Corpse )
					{
						if ( v.y_offset == -1 )
							++corpse_counter[0];
			
						else if ( v.x_offset == -1 )
							++corpse_counter[1];
						
						else if ( v.x_offset == 1 )
							++corpse_counter[2];
						
						else if ( v.y_offset == 1 )
							++corpse_counter[3];
					}
					
				}
				
				//�� �� ���� ���⿡ ���� ��ü ���� �ּҰ����� ����
				if ( IsValidMove(DirectionCode.Up) == false )
					corpse_counter[0] = -1;
				
				if ( IsValidMove(DirectionCode.Left) == false )
					corpse_counter[1] = -1;

				if ( IsValidMove(DirectionCode.Right) == false )
					corpse_counter[2] = -1;

				if ( IsValidMove(DirectionCode.Down) == false )
					corpse_counter[3] = -1;

				//������ ��ü ���� �ִ밪�� ������ ���
				for ( int corpses : corpse_counter )
					if ( corpses > max_corpse )
						max_corpse = corpses;
				
				//�ֺ��� ��ü�� �ִٸ� ��ü�Ա� �����̳� �ø���
				if(max_corpse > 0){
					//'���� ����' ������ ���� ��ü ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
					for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
						if ( corpse_counter[ shuffledDirection_values[iShuffledDirection] ] == max_corpse )
							return shuffledDirections[iShuffledDirection];
		
				}
				
				//��ü�� ����! ���ߴ� �� �ڻ� ����
				else{
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
					
					//��ü�� �ִٸ� �̵��� �� �ִ� �ѵ� ������ �� ĭ���� �̵�
					if ( isCorpseHere == true ){
						do
						{
							result = shuffledDirections[index_directionToMove];
							++index_directionToMove;
							index_directionToMove %= 4;
						}
						while ( IsValidMove(result) == false );
					}
					
					//�׷��� �ʴٸ� ��ȭ �⵵
					else
						result = DirectionCode.Stay;
					
					return result;
				}
				
			}
			
			//���հ� ���� �ڻ� ���� �����ڰ� ���� ������ �̵�
			else{
			
				//'���� ����' ������ ���� ������ ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
				for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
					if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
						return shuffledDirections[iShuffledDirection];
				
			}
	
		}

		
		// �Ĺݿ��� �� �ڻ� ����
		else{
			
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
				
				//��ü�� �ִٸ� �̵��� �� �ִ� �ѵ� ������ �� ĭ���� �̵�
				if ( isCorpseHere == true )
					do
					{
						result = shuffledDirections[index_directionToMove];
						++index_directionToMove;
						index_directionToMove %= 4;
					}
					while ( IsValidMove(result) == false );
				
				//�׷��� �ʴٸ� ��ȭ �⵵
				else
					result = DirectionCode.Stay;
				
				return result;
			
		}
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
		// ���� ������ / ����ü+��ü�� �� ���̳� ���ƴٴϰ� �ִ��� �˾ƺ��ϴ�.
		int survivor = 0;
		int others = 0;
		for ( PlayerInfo other : othersInfo_withinSight ){
			if ( other.GetState() == PlayerInfo.State.Survivor )
				++survivor;
			else
				++others;
		}
		
		// ����ü + ��ü�� �� ����, �����ڷ� �־��� �ְ� ���� 15 �̸��̸� ���� ���� ������ �츮�� ���� �����ڷ� Ȱ���մϴ�.
		// �ٵ� ��� ��Ƴ��⸸ �ϸ� �ٸ� �������� �޵� ����� �����Ƿ� 75�� �Ǳ� ��������
		if( survivor < others && this.myScore.GetSurvivor_Max_Survived_Turns() < 20 && this.gameInfo.GetCurrentTurnNumber() < 75){
			
			return basePoint;
			
		}
		
		// �����ڰ� �� ���ų�, �� ������ �ְ� ���� 15�� �Ѱų�, �׷��� �ʾƵ� ������ �Ĺ����� �޷�����(75��° �̻�) ��ü �����̳� ������ �� �ڻ� ����
		// ����ü �������� ���� �����ϹǷ� ���� ����ü�� ���� ĭ�� �켱���� �ϰ�,
		// ����ü�� ���ڰ� ���� ù ���� ���� ȹ�� ����� ���� ĭ�� ���� ���� ��� ��ü�� ������ 3���� �� ����� ����Ͽ� ���� ���� ĭ ����
		else{
			
			Point pointToSpawn = basePoint;

			//�� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
			double [][] weights = new double[Constants.Classroom_Height][Constants.Classroom_Width];
			double max_weight = -1;
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
			
			//�˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� ���� ��ü�� �� 3�� ���� ������ ����ü ���� ����� ���� ���� ĭ ����
			double max_expectation = -1;
			
			for ( Point pos_max_weight : list_pos_max_weight )
			{
				double current_expectation = 0;
				
				// ����ü�� ���� ���� ĭ �ֺ��� �� ĭ�� ��ȿ�ϸ� �ű� �ִ� ����ü ���� ����ġ�� ���� �����ݴϴ�.
				// �Ÿ��� 2�� �� : ����ġ 0.04
				//-2, 0
				if(pos_max_weight.x - 2 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x - 2][pos_max_weight.y]);
				
				//-1, -1
				if(pos_max_weight.x - 1 > -1 && pos_max_weight.y - 1 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x - 1][pos_max_weight.y - 1]);
				
				//0, -2
				if(pos_max_weight.y - 2 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x][pos_max_weight.y - 2]);
				
				//1, -1
				if(pos_max_weight.x + 1 < Constants.Classroom_Width && pos_max_weight.y -1 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 1][pos_max_weight.y - 1]);
				
				//2, 0
				if(pos_max_weight.x + 2 < Constants.Classroom_Width)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 2][pos_max_weight.y]);
					
				//1, 1
				if(pos_max_weight.x + 1 < Constants.Classroom_Width && pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 1][pos_max_weight.y + 1]);
				
				//0, 2
				if(pos_max_weight.y + 2 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x][pos_max_weight.y + 2]);
					
				//-1, 1
				if(pos_max_weight.x -1 > -1 && pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x -1][pos_max_weight.y + 1]);
				
				//�Ÿ��� 1�� �� : ����ġ 0.2
				//Up
				if(pos_max_weight.y - 1 > -1)
					current_expectation += 0.2 * (weights[pos_max_weight.x][pos_max_weight.y - 1]);
				
				//Left
				if(pos_max_weight.x - 1 > -1)
					current_expectation += 0.2 * (weights[pos_max_weight.x - 1][pos_max_weight.y]);
				
				//Right
				if(pos_max_weight.x + 1 < Constants.Classroom_Width)
					current_expectation += 0.2 * (weights[pos_max_weight.x + 1][pos_max_weight.y]);
				
				//Down
				if(pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.2 * (weights[pos_max_weight.x][pos_max_weight.y + 1]);
				
				//�Ÿ��� 0�� ��(����ü�� ���� ���� ĭ) : ����ġ 1
				++current_expectation;

				//�ִ밪�� �ٲ���ٸ� �ִ밪 ������ ���� �ٲ� ������ �����ϰ�, ������ ĭ �ٲٱ�
				if ( current_expectation > max_expectation )
				{
					max_expectation = current_expectation;
					pointToSpawn = pos_max_weight;
				}
				
			}
			
			return pointToSpawn;
		}
	}
}
