package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class �����ǿ���������� extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	DirectionCode[] directions = new DirectionCode[4];
	DirectionCode[] temp = new DirectionCode[1];
	Point favoritePoint = new Point(0, 0);

	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�. �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�. �� �޼���� �ݵ��
	 * �ʿ��մϴ�.
	 */
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

		for (iDirection = 0; iDirection < 4; iDirection++)
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break;
		}

		return directions[iDirection];
	}

	public �����ǿ����������(int ID)
	{

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "�����ǿ����������");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;

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

	@Override
	public DirectionCode Survivor_Move()
	{

		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		int[] numberOfPlayers = new int[13];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		temp[0]=directions[0];
		directions[0]=directions[3];
		directions[3]=temp[0];
		temp[0]=directions[1];
		directions[1]=directions[2];
		directions[2]=temp[0];
		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

		// 0
		row -= 2;

		if (row >= 0)
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 1, 2, 3
		++row;

		if (row >= 0)
		{
			if (column >= 1)
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1)
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column >= 2)
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if (column < Constants.Classroom_Width - 1)
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 2)
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height)
		{
			if (column >= 1)
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// C
		++row;

		if (row < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++)
		{
			switch (directions[iWeights])
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

		for (int iWeights = 0; iWeights < 4; iWeights++)
		{
			if (weights[iWeights] < min_weight)
			{
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
				{
					min_weight = weights[iWeights];
					min_idx_weights = iWeights;
				}
			}
		}
		
		return directions[min_idx_weights];
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
	

		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if (turnInfo.turnNumber == 0)
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		if (turnInfo.turnNumber > 60||myScore.survivor_max>15)
		{
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++)
			{
				for (int column = 0; column < Constants.Classroom_Width; column++)
				{
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if (weight > max_weight)
					{
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
					else if (weight == max_weight)
					{
						// �Ÿ��� �� ������ ����
						if (distance < min_distance)
						{
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if (distance == min_distance)
						{
							for (int iDirection = 0; iDirection < 4; iDirection++)
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

								if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
										max_column))
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
			return new Point(max_row, max_column);
		}
		else
		{
			int min_row = -1;
			int min_column = -1;
			int min_count = 1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

			for (int row = 0; row < Constants.Classroom_Height; row++)
			{
				for (int column = 0; column < Constants.Classroom_Width; column++)
				{
					int count = cells[row][column].Count_Players();
					int distance = favoritePoint.GetDistance(row, column);

					// �÷��̾� ���� �� ���ٸ� �׻� ����
					if (count < min_count)
					{
						min_row = row;
						min_column = column;
						min_count = count;
						min_distance = distance;
					}
					// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
					else if (count == min_count)
					{
						// �Ÿ��� �� ������ ����
						if (distance < min_distance)
						{
							min_row = row;
							min_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if (distance == min_distance)
						{
							for (int iDirection = 0; iDirection < 4; iDirection++)
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

								if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row,
										min_column))
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

			return new Point(min_row, min_column);
		}
	}
}
