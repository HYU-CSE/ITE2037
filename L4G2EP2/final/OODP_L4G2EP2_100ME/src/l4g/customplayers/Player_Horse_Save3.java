package l4g.customplayers;


import java.util.ArrayList;

//import l4g.PlayerStat;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Horse_Save3 extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_Horse_Save3(int ID)
	{

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "������������踻");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = true;

		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.

	}

	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */

	DirectionCode[] directions = new DirectionCode[4]; // ��ȣ�ϴ� ���� ����� �迭
	Point favoritePoint = new Point(0, 0);// ��ȣ�ϴ� ĭ ���
	int SpawnCount=0;
	void Init_Data() // ���� �켱������ ��ȣ�ϴ� ĭ ���(Soul�� ��Ȱ�� ��)
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		long seed = (ID + 8421) * gameNumber + ID;	
		if ( seed < 0 )
			seed = -seed;
		favoritePoint.row = (int)(seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int)(seed % Constants.Classroom_Height);
	}

	DirectionCode GetMovableAdjacentDirection() // ���� �켱������ ����Ͽ� ��ĭ�̵��� ���� ��ȯ(Ŭ����
												// ����� �ʰ�)
	{
		int iDirection;

		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width )
				break;
		}

		return directions[iDirection];
	}

	final int first = 0; /// ���� ��ȣ�ű��.
	final int mid = 1;
	final int fin = 2;
	final int ten = 3;
	
	int state = ten; // ó������ ����

	void UpdateState()
	{ // ��������
		switch ( state )
		{
		case ten: ///
			if(this.turnInfo.turnNumber>=11)
				state=first;
			break;
		case first:
			if (this.turnInfo.turnNumber >= 20)
				state = mid;
			break;
		case mid: ///
			if (this.turnInfo.turnNumber >= 80)
				state = fin;
			break;
		case fin: ///
				state = fin;
			break;


		}
	}

	DirectionCode Survivor_to_Survivor()
	{
		int[] numberOfPlayers = new int[13]; // ���� �÷��̾� ��� ����

		int row = myInfo.position.row; // �÷��̾��� ���� ��ġ
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� ������ �� ���

		// 0
		row -= 2;

		if ( row >= 0 )
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 1, 2, 3
		++row;

		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 9, A, B
		++row;

		if ( row < Constants.Classroom_Height )
		{
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// C
		++row;

		if ( row < Constants.Classroom_Height )
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
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

				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}

		return directions[max_idx_weights];

	}

	DirectionCode Infector_Cool_Die()
	{
		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();

		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	DirectionCode Survivor_Avoid()
	{
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

		// 0
		row -= 2;

		if ( row >= 0 )
			numberOfPlayers[0] = cells[row][column].Count_Players();

		// 1, 2, 3
		++row;

		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfPlayers[1] = cells[row][column - 1].Count_Players();

			numberOfPlayers[2] = cells[row][column].Count_Players();

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1].Count_Players();
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].Count_Players();

			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2].Count_Players();
		}

		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].Count_Players();

			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2].Count_Players();
		}

		// 9, A, B
		++row;

		if ( row < Constants.Classroom_Height )
		{
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1].Count_Players();

			numberOfPlayers[10] = cells[row][column].Count_Players();

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1].Count_Players();
		}

		// C
		++row;

		if ( row < Constants.Classroom_Height )
			numberOfPlayers[12] = cells[row][column].Count_Players();

		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
				break;
			}
		}

		// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int min_weight = Integer.MAX_VALUE;
		int min_idx_weights = 0;

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			if ( weights[iWeights] < min_weight )
			{
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					min_weight = weights[iWeights];
					min_idx_weights = iWeights;
				}
			}
		}

		return directions[min_idx_weights];
	}

	Point Spawn_Avoid_Anyone()
	{
		int min_row = -1;
		int min_column = -1;
		int min_count = 1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				int count = cells[row][column].Count_Players();
				int distance = favoritePoint.GetDistance(row, column);

				// �÷��̾� ���� �� ���ٸ� �׻� ����
				if ( count < min_count )
				{
					min_row = row;
					min_column = column;
					min_count = count;
					min_distance = distance;
				}
				// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
				else if ( count == min_count )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						min_row = row;
						min_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
					else if ( distance == min_distance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row, min_column) )
							{
								min_row = row+1;
								min_column = column+1;
								break;
							}
						} 
						
						//������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		
		return new Point(min_row, min_column);
	}

	Point Spawn_Near_Survivor()
	{
		int min_row = -1;
		int min_column = -1;
		int min_distance = Integer.MAX_VALUE;

		// ��ü ĭ�� �˻��Ͽ� ����ü�� �ִ� ĭ�� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Survivor);

				if ( numberOfInfecteds != 0 )
				{
					int distance = favoritePoint.GetDistance(row, column);

					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						min_distance = distance;
						min_row = row;
						min_column = column;
					}
					// �Ÿ��� ������ �� �����ϴ� ������ ����
					else
						if ( distance == min_distance )
						{
							for ( int iDirection = 0; iDirection < 4; iDirection++ )
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

								if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row,
										min_column) )
								{
									min_row = row;
									min_column = column;
									break;
								}
							}

							// ������� �Դٸ� ���� �׸� ���� ����
						}
				}
			}
		}

		// �˻��ߴµ� ����ü�� �ϳ��� ���ٸ� ��ġ ����
		if ( min_distance == Integer.MAX_VALUE )
		{
			int variableToMakeError = 0;

			variableToMakeError = variableToMakeError / variableToMakeError;
		}

		return new Point(min_row, min_column);
	}

	DirectionCode Infector_to_Corpse()
	{
		// ���� ĭ�� ��ü�� �ִٸ� ������ ���
		if ( this.cells[myInfo.position.row][myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) != 0 )
			return DirectionCode.Stay;

		ArrayList<PlayerInfo> corpses = new ArrayList<>();

		// ��� ĭ�� �����Ͽ� ���ǽǿ� �ִ� ��� ��ü�鿡 ���� ����� ����
		for ( CellInfo[] rows : cells )
			for ( CellInfo cell : rows )
				corpses.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));

		// �̵� ������ �� ĭ�鿡 ����, '���� ��ü�� ������ ���� �� �ִ� ĭ�� ����
		int min_weight = Integer.MAX_VALUE;
		int min_idx_directions = 0;

		for ( int iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width )
			{
				int weight = Integer.MAX_VALUE - 1;

				for ( PlayerInfo corpse : corpses )
				{
					int distance = corpse.position.GetDistance(adjacentPoint);

					if ( distance < weight )
						weight = distance;
				}

				if ( weight < min_weight )
				{
					min_weight = weight;
					min_idx_directions = iDirection;
				}
			}
		}

		return directions[min_idx_directions];

	}

	Point Spawn_Near_Corpse()
	{
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
				else
					if ( weight == max_weight )
					{
						// �Ÿ��� �� ������ ����
						if ( distance < min_distance )
						{
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else
							if ( distance == min_distance )
							{
								for ( int iDirection = 0; iDirection < 4; iDirection++ )
								{
									Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

									if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
											max_column) )
									{
										max_row = row;
										max_column = column;
										break;
									}
								}

								// ������� �Դٸ� ���� �׸� ���� ����
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

	DirectionCode Survivor_to_Infector()
	{
		int[] numberOfPlayers = new int[13]; // ���� �÷��̾� ��� ����

		int row = myInfo.position.row; // �÷��̾��� ���� ��ġ
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
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 9, A, B
		++row;

		if ( row < Constants.Classroom_Height )
		{
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// C
		++row;

		if ( row < Constants.Classroom_Height )
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
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

				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}

		return directions[max_idx_weights];

	}

	DirectionCode Infector_to_Survivor()
	{
		int[] numberOfSurvivors = new int[8];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfSurvivors[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfSurvivors[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 3, 4
		++row;
		
		if ( column >= 2 )
			numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		if ( column < Constants.Classroom_Width - 2 )
			numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 5, 6
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 )
				numberOfSurvivors[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfSurvivors[6] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// 7
		++row;
		
		if ( row < Constants.Classroom_Height)
			numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 012
				weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2];
				break;
			case Left:
				// ����: 135
				weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[3] + numberOfSurvivors[5];
				break;
			case Right:
				// ������: 246
				weights[iWeights] = numberOfSurvivors[2] + numberOfSurvivors[4] + numberOfSurvivors[6];
				break;
			default:
				// �Ʒ�: 567
				weights[iWeights] = numberOfSurvivors[5] + numberOfSurvivors[6] + numberOfSurvivors[7];
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

	@Override
	public DirectionCode Survivor_Move() 
	{
		DirectionCode result = DirectionCode.Right;
		UpdateState();
		switch ( state )
		{
		case ten:
			result = Survivor_to_Survivor();
			break;
		case first:
			result = Survivor_Avoid();
			break;
		case mid:
			result = Survivor_Avoid();
			break;
		case fin:
			result = Survivor_to_Infector();
			break;
		}
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Stay;
		UpdateState();
		switch ( state )
		{
		case ten:
			result = Infector_to_Survivor();
			break;
		case first:
			result = Infector_Cool_Die();
			break;
		case mid:
			result = Infector_Cool_Die();
			break;
		case fin:
			result = Infector_Cool_Die();
			break;
		}
		return result;

	}

	@Override
	public void Soul_Stay()
	{
		if ( this.turnInfo.turnNumber == 0 )
		{
			Init_Data();
			SpawnCount=0;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		Point PointResult = new Point(0,0);
		UpdateState();
		long seed = (ID + 8421) * gameNumber + ID+3*SpawnCount;	
		if ( seed < 0 )
			seed = -seed;
		PointResult.row=(int)(seed / Constants.Classroom_Width % Constants.Classroom_Height);
		PointResult.column=(int)(seed % Constants.Classroom_Height);
		switch ( state )
		{
		case first:
			PointResult = Spawn_Near_Survivor();
			break;
		case mid:
			PointResult =Spawn_Near_Survivor();
			break;
		case fin:
			PointResult = Spawn_Near_Corpse();
			break;
		}
		SpawnCount++;
		return PointResult;
	}
}
