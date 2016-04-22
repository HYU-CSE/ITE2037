package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */

public class Player_������_���������� extends Player
{
	
	
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_������_����������(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "���������Ƣ�");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	
		
		/**
		 * '���� �켱����'�� ����� �δ� �迭�Դϴ�.
		 * �� field�� �ݵ�� �ʿ��մϴ�.
		 */
		DirectionCode[] directions = new DirectionCode[4];
		
		Point favoritePoint = new Point(0, 0);
		
		/**
		 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�.
		 * �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�.
		 * �� �޼���� �ݵ�� �ʿ��մϴ�.
		 */
		void Init_Data()
		{
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
		 
			favoritePoint.row = 6;
			favoritePoint.column = 6;
				
		
		}
		
		/**
		 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�.
		 * �� �޼���� �ݵ�� �ʿ��մϴ�.
		 */
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
		
		
		
		
		
		@Override
		public DirectionCode Infected_Move()
		{
			// �� �ؿ� ��ü�� ��� ������ ������
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
			
			// �׷��� ������ ��ȭ �⵵ �õ�
			return DirectionCode.Stay;
		}
		
		@Override
		public void Soul_Stay()
		{
			if ( this.turnInfo.turnNumber == 0 )
			{
				// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
				Init_Data();
			}
		}
		
		@Override
		public Point Soul_Spawn()
		{
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			if( turnInfo.turnNumber == 0 )
				return new Point(7,7);
			
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
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
		
		@Override
		public DirectionCode Survivor_Move()
		{
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
			
			int row = myInfo.position.row;
			int column = myInfo.position.column;
			
			// ���� ���̴� 13���� ��쿡 ���� ������ �� ���
			
			// 0
			row -= 2;
			
			if ( row >= 0 )
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			// 1, 2, 3
			++row;
			
			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			
			// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
			++row;
			
			if ( column >= 1 )
			{
				numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column >= 2 )
					numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			
			if ( column < Constants.Classroom_Width - 1 )
			{
				numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			
			// 9, A, B
			++row;
			
			if ( row < Constants.Classroom_Height)
			{
				if ( column >= 1 )
					numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);		
			}
			
			// C
			++row;
			
			if ( row < Constants.Classroom_Height)
				numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			
			// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
			int[] weights = new int[4];

			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				switch ( directions[iWeights] )
				{
				case Down:
					// ��: 01234578
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[7] + numberOfPlayers[8];
					break;
				case Right:
					// ����: 145902ac
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9] + numberOfPlayers[0] + numberOfPlayers[2] + numberOfPlayers[10] + numberOfPlayers[12];
					break;
				case Left:
					// ������: 378B02ac
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11] +  numberOfPlayers[0] + numberOfPlayers[2] + numberOfPlayers[10] + numberOfPlayers[12];
					break;
				case Up:
					// �Ʒ�: 9ABC4578
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[7] + numberOfPlayers[8];
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
		
		
		
		

}
