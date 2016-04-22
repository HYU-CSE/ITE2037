package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_HYCat extends Player
{
	//���� �켱������ ��� �迭
	DirectionCode[] directions = new DirectionCode[4];
	//��ȣ�ϴ� ĭ�� �����ϴ� field
	Point favoritePoint = new Point(0, 0);
	
	
	int abs(int num) {
		if (num < 0) return -num;
		else return num;
	}
	
	void Init_Data() {
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		favoritePoint.row = 4;
		favoritePoint.column = 4;
	}
	
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}
	
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_HYCat(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�ϳ���");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		
		//if(gameNumber/10000000 <= 10) this.trigger_acceptDirectInfection = false;
		//else if(gameNumber/10000000 <= 13) this.trigger_acceptDirectInfection = true;
		//else if(gameNumber/10000000 <= 15) this.trigger_acceptDirectInfection = false;
		
		
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	
	/*
	 * TODO#5	���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	 * 			�翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	 * 			�ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * 			L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * 			�������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	 * 			�׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * 			��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	 * 			'�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{	// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		/*
		 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�.
		 *              �������� �þ� ������
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..�� ��
		 *              ��:     0123�� �ִ� ������ ��
		 *              ����:   1459�� �ִ� ������ ��
		 *              ������: 378B�� �ִ� ������ ��
		 *              �Ʒ�:   9ABC�� �ִ� ������ ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];
		
		// �þ߿� ���̴� ĭ�� ��������� �����ϴ� �迭, ��ó�� �����ڰ� ���� ���� �ش� ĭ�� ��������� ����, �����ڰ� ���� ���� ����.
		int[] priorityOfMove = new int[13];
		for (int i = 0; i < priorityOfMove.length; i++) {
			priorityOfMove[i] = 0;
		}
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// ���� ���̴� 13���� ��쿡 ���� ������ �� ���
		
		// 0
		row -= 2;
		
		if ( row >= 0 ) {
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[0] = ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
		}
		// 1, 2, 3
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 ) {
				numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[1] = ( cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[2] = ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
			
			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[3] = ( cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
		}
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;
		
		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[5] = ( cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
			
			if ( column >= 2 ) {
				numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[4] = ( cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
		}
		
		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[7] = ( cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
			
			if ( column < Constants.Classroom_Width - 2 ) {
				numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[8] = ( cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
		}
		
		// 9, A, B
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 ) {
				numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[9] = ( cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
			
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[10] = ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
			
			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				priorityOfMove[11] = ( cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
						cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
							-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
						);
			}
		}
		
		// C
		++row;
		
		if ( row < Constants.Classroom_Height) {
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			priorityOfMove[12] = ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0 ?
					cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) :
						-cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)
					);
		}
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3]
						+ priorityOfMove[0] + priorityOfMove[1] + priorityOfMove[2] + priorityOfMove[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9]
						+ priorityOfMove[1] + priorityOfMove[4] + priorityOfMove[5] + priorityOfMove[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11]
				+ priorityOfMove[3] + priorityOfMove[7] + priorityOfMove[8] + priorityOfMove[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12]
						+ priorityOfMove[9] + priorityOfMove[10] + priorityOfMove[11] + priorityOfMove[12];
				break;
			}
		}
		
		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int max_weight = -100;
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
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		if (turnInfo.turnNumber == 0) {
			return favoritePoint;
		} else if ( turnInfo.turnNumber <= 80 ) {
			CellInfo cell = this.cells[favoritePoint.row][favoritePoint.column];
			while(cell.CountIf_Players(player -> player.state == StateCode.Infected) != 0) {
				cell = this.cells[favoritePoint.row][favoritePoint.column];
				if (favoritePoint.row < Constants.Classroom_Height) {
					favoritePoint.row++;
				} else {
					favoritePoint.row = 0;
				}
				if (favoritePoint.column < Constants.Classroom_Width) {
					favoritePoint.column++;
				} else {
					favoritePoint.column = 0;
				}
			}
			return favoritePoint;
		}
		else {
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = this.cells[row][column];
					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
					for (int i = 1; i <= 2; i++) {
						if ( row + i < Constants.Classroom_Height) {
							cell = this.cells[row + i][column];
							numberOfCorpses += cell.CountIf_Players(player -> player.state == StateCode.Corpse);
							numberOfInfecteds += cell.CountIf_Players(player -> player.state == StateCode.Infected);
						} 
						if (row - i > 0 ){
							cell = this.cells[row - i][column];
							numberOfCorpses += cell.CountIf_Players(player -> player.state == StateCode.Corpse);
							numberOfInfecteds += cell.CountIf_Players(player -> player.state == StateCode.Infected);
						}
						
						if (column + i < Constants.Classroom_Width) {
							cell = this.cells[row][column + i];
							numberOfCorpses += cell.CountIf_Players(player -> player.state == StateCode.Corpse);
							numberOfInfecteds += cell.CountIf_Players(player -> player.state == StateCode.Infected);
						}
						if (column - i > 0) {
							cell = this.cells[row][column - i];
							numberOfCorpses += cell.CountIf_Players(player -> player.state == StateCode.Corpse);
							numberOfInfecteds += cell.CountIf_Players(player -> player.state == StateCode.Infected);
						}
					}
					
					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
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
			
			// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ġ ����
			if ( max_weight == 0 )
			{
				int variableToMakeError = 0;
				
				variableToMakeError = variableToMakeError / variableToMakeError;
			}
			
			return new Point(max_row, max_column);
		}
	}
}
