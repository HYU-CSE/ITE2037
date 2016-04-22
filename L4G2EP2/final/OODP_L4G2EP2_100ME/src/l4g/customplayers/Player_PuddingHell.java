package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

public class Player_PuddingHell extends Player
{
	public Player_PuddingHell(int ID)
	{
		//�ַ� ����ü�� ��ư�
		super(ID, "Ǫ���ӱײ���");
		
		this.trigger_acceptDirectInfection = true;
	}
	
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);
	int SwitchForCorpseBomb = 0;
	int TurnToBomb = 0;
	
	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Down;
		directions[3] = DirectionCode.Right;
		
		favoritePoint.row = 6;
		favoritePoint.column = 6;
		
		SwitchForCorpseBomb = 0; // �������� ���ƿ��� ��ü�� �������� �� ����ġ�� 1�� Ű��
		
		TurnToBomb = 105; //���������Ѽ��ڴ�? 106 ���� �Ʒ�.
	}
	void Change_Data()
	{
		// ����ü�� ��ȣ������ �߾��� ���ϵ��� ������Ʈ�ϴ� �Լ�
		if (myInfo.position.column - favoritePoint.column > 0){
			if (myInfo.position.row - favoritePoint.row > 0){
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Right;
			}
			else{
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Right;
			}
		}
		else{
			if (myInfo.position.row - favoritePoint.row > 0){
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Left;
			}
			else{
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Left;
			}
		}
	}
	
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height 
					&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}
	
	DirectionCode Infected_Move_Tosurvivor()
	{
		// ����ü�� 1������ �߿�ÿ���� �̵���Ģ - �����ڸ� ���� ��ȸ�� �ִٸ� ���󰣴�.
		/*
		 * ����ü �̵�: �����ڰ� �̵��� ���ɼ��� ���� ���� �������� �̵��մϴ�.
		 *              �� �ֺ� ĭ�� ����
		 *                    0
		 *                   1 2
		 *                  3 X 4
		 *                   5 6
		 *                    7
		 *              ..�� ��ȣ�� �Ű��� ��
		 *              ��:     012�� �ִ� ��� ��
		 *              ����:   135�� �ִ� ��� ��
		 *              ������: 246�� �ִ� ��� ��
		 *              �Ʒ�:   567�� �ִ� ��� ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfInfecteds = new int[8];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfInfecteds[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfInfecteds[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfecteds[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 3, 4
		++row;
		
		if ( column >= 2 )
			numberOfInfecteds[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		if ( column < Constants.Classroom_Width - 2 )
			numberOfInfecteds[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 5, 6
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 )
				numberOfInfecteds[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfecteds[6] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// 7
		++row;
		
		if ( row < Constants.Classroom_Height)
			numberOfInfecteds[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		

		//������ �����ڰ� �ƹ������ٸ� ���ڸ� ��ȯ->2���� ������ �����Ϸ� ������ ..
		int numberOfTotalSurvivors = 0;
		for ( int i = 0; i < 8; i++ )
		{
			numberOfTotalSurvivors += numberOfInfecteds[i];
		}
		if (numberOfTotalSurvivors == 0)
			return DirectionCode.Stay;
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 012
				weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2];
				break;
			case Left:
				// ����: 135
				weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[3] + numberOfInfecteds[5];
				break;
			case Right:
				// ������: 246
				weights[iWeights] = numberOfInfecteds[2] + numberOfInfecteds[4] + numberOfInfecteds[6];
				break;
			default:
				// �Ʒ�: 567
				weights[iWeights] = numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[7];
				break;
			}
		}
		
		
		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int max_weight = -1;
		int max_idx_weights = 0;
		
		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			if ( weights[iWeights] > max_weight )
			{
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
				
				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
						adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}
				
		return directions[max_idx_weights];
	}
	DirectionCode Infected_Move_Toinfected()
	{
		// ������(1����)�� �ƹ��� ������ ���� ��ü�����ִ°�(2����)�� �ƴҶ� -> ����ü�� ���󰡰� �ϴ°��� �ٷ� �� �Լ�(3����)
		/*
		 * ����ü �̵�(1): ����ü�� ���� ���� �������� �̵��մϴ�.
		 *              �� �ֺ� ĭ�� ����
		 *                0 1 2 3 4
		 *                5 6 7 8 9
		 *                A B/X/C D
		 *                E F G H I
		 *                J K L M N
		 *              ..�� ��ȣ�� �Ű��� ��
		 *              ��:    0123456789�� �ִ� ��� ��
		 *              ����:   (����)�� �ִ� ��� ��
		 *              ������:  (����)�� �ִ� ��� ��
		 *              �Ʒ�:   (����)�� �ִ� ��� ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		/*
		 * ����ü �̵�(2): ����ü�� ���󰡵�, �������� ����Ⱦ���
		 * 			����ü�� ���󰡷��� ������ �Ÿ��� 1ĭ ���Ϸ� ������ �ʴ´�.
		 * 			���⺰�� ����ü ���� �ջ��� �� ����.
		 */
		
		int[] numberOfInfecteds = new int[24];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// 1°��
		row -= 2;
		
		if ( row >= 0 ){
			if ( column >= 2 )
				numberOfInfecteds[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[1] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[3] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[4] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 2°��
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 2 )
				numberOfInfecteds[5] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[6] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[8] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[9] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 3°��
		++row;
		
		if (true)
		{
			if ( column >= 2 )
				numberOfInfecteds[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[11] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[13] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 4°��
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 2 )
				numberOfInfecteds[14] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[15] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[16] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[17] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[18] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);		
		}
		
		// 5°��
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 2 )
				numberOfInfecteds[19] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[20] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[21] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[22] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[23] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// �ʹ� ���� ����ü(10���̻�)�� �ִٸ� ����� ġ�� ������
		for ( int i = 0; i < 24; i++)
		{
			if ( numberOfInfecteds[i] >= 10)
				numberOfInfecteds[i] = 0;
		}
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ����ü �� �ջ�
		// (�߿�)������ ������ 2ĭ �̻� ������ �ְ� �ʹ� : ���� 2ĭ�������ִٸ� (�������� �Ȱ���) ���� ����ü�� ���Ը� 0���� ..
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123456789
				if ( myInfo.position.row <= 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2] + numberOfInfecteds[3] + numberOfInfecteds[4]
							 + numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[7] + numberOfInfecteds[8] + numberOfInfecteds[9];
				break;
			case Left:
				// ����: 0156,10,11,14,15,19,20
				if ( myInfo.position.column <= 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[10]
							 + numberOfInfecteds[11] + numberOfInfecteds[14] + numberOfInfecteds[15] + numberOfInfecteds[19] + numberOfInfecteds[20];
				break;
			case Right:
				// ������: 3489,12,13,17,18,22,23
				if ( myInfo.position.column >= Constants.Classroom_Width - 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[4] + numberOfInfecteds[8] + numberOfInfecteds[9] + numberOfInfecteds[12]
							 + numberOfInfecteds[13] + numberOfInfecteds[17] + numberOfInfecteds[18] + numberOfInfecteds[22] + numberOfInfecteds[23];
				break;
			default:
				// �Ʒ�: 14,15,16,17,18,19,20,21,22,23
				if ( myInfo.position.row >= Constants.Classroom_Height - 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[14] + numberOfInfecteds[15] + numberOfInfecteds[16] + numberOfInfecteds[17] + numberOfInfecteds[18]
							 + numberOfInfecteds[19] + numberOfInfecteds[20] + numberOfInfecteds[21] + numberOfInfecteds[22] + numberOfInfecteds[23];
				break;
			}
		}
		
		// ����ü ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int max_weight = -1;
		int max_idx_weights = 0;
		
		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			if ( weights[iWeights] > max_weight )
			{
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
				
				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
						adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}
				
		return directions[max_idx_weights];
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// ó�� 1~11�ϵ��� �ڸ���µ����� ����
		// �׷��� ���� 11�� ���ķ� �����ڰ� �Ǵ� ��찡 �ִµ� ���� �幮 ����̹Ƿ� �Ƹ���. - �������� �𸣰���
		return Infected_Move();
	}

	@Override
	public void Corpse_Stay()
	{
		// ������ ���� ����
	}

	@Override
	public DirectionCode Infected_Move()
	{
		//���Ĺݿ� - ���ƿ��� ��ü��ź �������� �����־������
		if (SwitchForCorpseBomb == 1){
			return DirectionCode.Stay;
		}
		
		//�Ĺݿ� - ��ü��ź�Ϸ��� �ڻ�õ� �ϴ���
		if (turnInfo.turnNumber > TurnToBomb){
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
			else
				return DirectionCode.Stay;
		}
		
		//�ʹ�~�߹� - ������ ����
		Change_Data();
		DirectionCode Survivor = Infected_Move_Tosurvivor();
		DirectionCode Infected = Infected_Move_Toinfected();
		
		if ( Survivor != DirectionCode.Stay )
			return Survivor;
		else{
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return DirectionCode.Stay;
			else{
				return Infected;
			}
		}
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			Init_Data();
		}

		if (turnInfo.turnNumber > TurnToBomb){
			//����ġ ��
			SwitchForCorpseBomb = 1;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		//  [(��ü ��)/2]+(����ü ��)   -> �� �������� ��ü��ź�����غ�
		
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		// ��ü ĭ�� �˻��Ͽ� [(��ü ��)/2]+(����ü ��)�� ���� ���� ĭ�� ã��
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? (numberOfCorpses/2) + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( weight == max_weight )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
					else if ( distance == min_distance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
							{
								max_row = row;
								max_column = column;
								break;
							}
						} 
						
						//������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		
		// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� (=��ó����), (6,6)���� ��������.
		if ( max_weight == 0 )
		{
			return favoritePoint;
		}
		
		return new Point(max_row, max_column);
	}
}
