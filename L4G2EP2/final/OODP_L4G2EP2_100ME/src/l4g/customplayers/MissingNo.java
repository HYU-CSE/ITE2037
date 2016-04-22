package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class MissingNo extends Player
{

	DirectionCode[] directions = new DirectionCode[4];

	Point favoritePoint = new Point(0, 0);

	int numberofdeath = -1;

	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;

		favoritePoint.row = 6;
		favoritePoint.column = 6;

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

	public MissingNo(int ID)
	{
		super(ID, "MissingNo");

		this.trigger_acceptDirectInfection = false;

	}

	@Override
	public DirectionCode Survivor_Move()
	{
		int turn_count = turnInfo.turnNumber;

		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� 60�� �̸�
		if ( turn_count < 60 )
		{

			if ( numberofdeath >= 1 )
			{
				return null;
			}

			else
			{

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
						weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
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

						if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
								adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
						{
							min_weight = weights[iWeights];
							min_idx_weights = iWeights;
						}
					}
				}

				return directions[min_idx_weights];
			}

		}

		// ���� 60�� �̻�
		else
		{

			// 0
			row -= 2;

			if ( row >= 0 )
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			// 1, 2, 3
			++row;

			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
			++row;

			if ( column >= 1 )
			{
				numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				if ( column >= 2 )
					numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			if ( column < Constants.Classroom_Width - 1 )
			{
				numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			// 9, A, B
			++row;

			if ( row < Constants.Classroom_Height )
			{
				if ( column >= 1 )
					numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
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
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
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

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{

		if ( this.turnInfo.turnNumber == 0 )
		{
			Init_Data();
		}

		++numberofdeath;

	}

	@Override
	public Point Soul_Spawn()
	{
		int turn_count = turnInfo.turnNumber;

		if ( turn_count < 60 )
		{
			if ( numberofdeath >= 1 )
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
			
			else
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
										min_row = row;
										min_column = column;
										break;
									}
								} 
								
							}
						}
					}
				}
				
				return new Point(min_row, min_column);
			}
		}

		else
		{
			return favoritePoint;
		}
	}
}
