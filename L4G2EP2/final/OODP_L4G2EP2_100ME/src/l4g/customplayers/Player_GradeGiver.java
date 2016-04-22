package l4g.customplayers;

import l4g.common.*;
import l4g.data.TurnInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_GradeGiver extends Player
{

	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_GradeGiver(int ID)
	{
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "������Ʈ");
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = true;
	}

	DirectionCode[] direction = new DirectionCode[4];
	Point center = new Point(6, 6);
	int[][] survivorCounts = new int[Constants.Classroom_Height][Constants.Classroom_Width];
	int[][] infectedCounts = new int[Constants.Classroom_Height][Constants.Classroom_Width];
	int[] countInfected = new int[13];
	int[] countSurvivor = new int[13];
	int[] addtionalInfo = new int[12];

	DirectionCode GetMovableAdjacentDirection()
	{
		direction[0] = DirectionCode.Up;
		direction[1] = DirectionCode.Left;
		direction[2] = DirectionCode.Right;
		direction[3] = DirectionCode.Down;

		int checkNum = 0;

		for (checkNum = 0; checkNum < 4; checkNum++)
		{
			Point checkPoint = myInfo.position.GetAdjacentPoint(direction[checkNum]);
			if (checkPoint.row >= 0 && checkPoint.row < Constants.Classroom_Height && checkPoint.column >= 0 && checkPoint.column < Constants.Classroom_Width)
				break;
		}
		return direction[checkNum];
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		// ���� �迭
		direction[0] = DirectionCode.Up;
		direction[1] = DirectionCode.Left;
		direction[2] = DirectionCode.Right;
		direction[3] = DirectionCode.Down;

		// �����ڼ� ���� (loner bot �ڵ� )
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		if (turnInfo.turnNumber < 11)
		{
			row -= 2;

			if (row >= 0)
				countSurvivor[0] = cells[row][column].Count_Players();
			++row;

			if (row >= 0)
			{
				if (column >= 1)
					countSurvivor[1] = cells[row][column - 1].Count_Players();
				countInfected[2] = cells[row][column].Count_Players();
				if (column < Constants.Classroom_Width - 1)
					countSurvivor[3] = cells[row][column + 1].Count_Players();
			}
			++row;

			if (column >= 1)
			{
				countSurvivor[5] = cells[row][column - 1].Count_Players();
				if (column >= 2)
					countSurvivor[4] = cells[row][column - 2].Count_Players();
			}
			if (column < Constants.Classroom_Width - 1)
			{
				countSurvivor[7] = cells[row][column + 1].Count_Players();
				if (column < Constants.Classroom_Width - 2)
					countSurvivor[8] = cells[row][column + 2].Count_Players();
			}
			++row;

			if (row < Constants.Classroom_Height)
			{
				if (column >= 1)
					countSurvivor[9] = cells[row][column - 1].Count_Players();
				countSurvivor[10] = cells[row][column].Count_Players();
				if (column < Constants.Classroom_Width - 1)
					countSurvivor[11] = cells[row][column + 1].Count_Players();
			}
			++row;

			// �����ڼ� ���ϱ� (�� ��ġ ����)
			int[] sumSurvivor = new int[4];
			sumSurvivor[0] = countSurvivor[0] + countSurvivor[1] + countSurvivor[2] + countSurvivor[3]; // up
			sumSurvivor[1] = countSurvivor[1] + countSurvivor[4] + countSurvivor[5] + countSurvivor[9]; // left
			sumSurvivor[2] = countSurvivor[3] + countSurvivor[7] + countSurvivor[8] + countSurvivor[11]; // right
			sumSurvivor[3] = countSurvivor[9] + countSurvivor[10] + countSurvivor[11] + countSurvivor[12]; // down

			// ���� ���� �� üũ �� �̵����� Ȯ��
			int min_sum = Integer.MAX_VALUE;
			int min_idx = 0;
			for (int num = 0; num < 4; num++)
			{
				if (sumSurvivor[num] < min_sum)
				{
					Point movableCheck = myInfo.position.GetAdjacentPoint(direction[num]);

					if (movableCheck.row >= 0 && movableCheck.row < Constants.Classroom_Height && movableCheck.column >= 0 && movableCheck.column < Constants.Classroom_Width)
					{
						min_sum = sumSurvivor[num];
						min_idx = num;
					}
				}
			}
			// �̵�
			return direction[min_idx];
		} else

		{
			row -= 2;

			if (row >= 0)
				countInfected[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			++row;

			if (row >= 0)
			{
				if (column >= 1)
					countInfected[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				countInfected[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column < Constants.Classroom_Width - 1)
					countInfected[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			++row;

			if (column >= 1)
			{
				countInfected[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column >= 2)
					countInfected[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			if (column < Constants.Classroom_Width - 1)
			{
				countInfected[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column < Constants.Classroom_Width - 2)
					countInfected[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			++row;

			if (row < Constants.Classroom_Height)
			{
				if (column >= 1)
					countInfected[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				countInfected[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column < Constants.Classroom_Width - 1)
					countInfected[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			++row;

			if (row < Constants.Classroom_Height)
				countInfected[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			// �α� ������ �߰����� �̿�

			row = myInfo.position.row;
			column = myInfo.position.column;
			row -= 1;
			if (row >= 0)
			{
				if (column - 1 >= 0 && cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
				{
					if (column - 2 >= 0)
						addtionalInfo[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
					if (row - 1 >= 0)
						addtionalInfo[1] = cells[row - 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

				}
				if (cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
				{
					if (row - 2 >= 0)
						addtionalInfo[0] = cells[row - 2][column].CountIf_Players(player -> player.state == StateCode.Infected);
					if (row - 1 >= 0 && column - 1 >= 0)
						addtionalInfo[1] = cells[row - 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
					if (row - 1 >= 0 && column + 1 < Constants.Classroom_Width)
						addtionalInfo[2] = cells[row - 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
					if (column - 2 >= 0)
						addtionalInfo[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
					if (column + 2 < Constants.Classroom_Width)
						addtionalInfo[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
				}
				if (column + 1 < Constants.Classroom_Width && cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
				{
					if (row - 1 >= 0)
						addtionalInfo[2] = cells[row - 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
					if (column + 2 < Constants.Classroom_Width)
						addtionalInfo[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
				}
			}
		}

		++row;

		if (column - 1 >= 0 && cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
		{
			if (column - 3 >= 0)
				addtionalInfo[5] = cells[row][column - 3].CountIf_Players(player -> player.state == StateCode.Infected);
			if (column - 2 >= 0)
			{
				if (row - 1 >= 0)
					addtionalInfo[3] = cells[row - 1][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
				if (row + 1 < Constants.Classroom_Height)
					addtionalInfo[7] = cells[row + 1][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			if (row - 2 >= 0)
				addtionalInfo[1] = cells[row - 2][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			if (row + 2 < Constants.Classroom_Height)
				addtionalInfo[9] = cells[row + 2][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

		}
		if (column + 1 < Constants.Classroom_Width && cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
		{
			if (column + 3 < Constants.Classroom_Width)
				addtionalInfo[6] = cells[row][column + 3].CountIf_Players(player -> player.state == StateCode.Infected);
			if (column + 2 < Constants.Classroom_Width)
			{
				if (row - 1 >= 0)
					addtionalInfo[4] = cells[row - 1][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
				if (row + 1 < Constants.Classroom_Height)
					addtionalInfo[8] = cells[row + 1][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			if (row - 2 >= 0)
				addtionalInfo[2] = cells[row - 2][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			if (row + 2 < Constants.Classroom_Height)
				addtionalInfo[10] = cells[row + 2][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		++row;

		if (row < Constants.Classroom_Height)
		{
			if (column - 1 >= 0 && cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
			{
				if (column - 2 >= 0)
					addtionalInfo[7] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
				if (row + 1 < Constants.Classroom_Height)
					addtionalInfo[9] = cells[row + 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			if (cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
			{
				if (row + 2 < Constants.Classroom_Height)
					addtionalInfo[11] = cells[row + 2][column].CountIf_Players(player -> player.state == StateCode.Infected);
				if (row + 1 < Constants.Classroom_Height)
				{
					if (column - 1 >= 0)
						addtionalInfo[9] = cells[row + 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
					if (column + 1 < Constants.Classroom_Width)
						addtionalInfo[10] = cells[row + 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
				}
				if (column - 2 >= 0)
					addtionalInfo[7] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column + 2 < Constants.Classroom_Width)
					addtionalInfo[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			if (column + 1 < Constants.Classroom_Width && cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor) != 0)
			{
				if (row + 1 < Constants.Classroom_Height)
					addtionalInfo[10] = cells[row + 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
				if (column + 2 < Constants.Classroom_Width)
					addtionalInfo[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		// �����ڼ� ���ϱ� (�� ��ġ ����)
		int[] sumInfected = new int[4];
		sumInfected[0] = countInfected[0] + countInfected[1] + countInfected[2] + countInfected[3] + addtionalInfo[0] + addtionalInfo[1] + addtionalInfo[2] + addtionalInfo[3] + addtionalInfo[4]; // up
		sumInfected[1] = countInfected[1] + countInfected[4] + countInfected[5] + countInfected[9] + addtionalInfo[1] + addtionalInfo[3] + addtionalInfo[5] + addtionalInfo[7] + addtionalInfo[9]; // left
		sumInfected[2] = countInfected[3] + countInfected[7] + countInfected[8] + countInfected[11] + addtionalInfo[2] + addtionalInfo[4] + addtionalInfo[6] + addtionalInfo[8] + addtionalInfo[10]; // right
		sumInfected[3] = countInfected[9] + countInfected[10] + countInfected[11] + countInfected[12] + addtionalInfo[7] + addtionalInfo[8] + addtionalInfo[9] + addtionalInfo[10] + addtionalInfo[11]; // down

		// ���� ���� �� üũ �� �̵����� Ȯ��
		int min_sum = Integer.MAX_VALUE;
		int min_idx = 0;
		for (int num = 0; num < 4; num++)
		{
			if (sumInfected[num] < min_sum)
			{
				Point movableCheck = myInfo.position.GetAdjacentPoint(direction[num]);

				if (movableCheck.row >= 0 && movableCheck.row < Constants.Classroom_Height && movableCheck.column >= 0 && movableCheck.column < Constants.Classroom_Width)
				{
					min_sum = sumInfected[num];
					min_idx = num;
				}
			}

		}
		// �̵�
		return direction[min_idx];
	}

	@Override
	public void Corpse_Stay()
	{
	}

	@Override
	public DirectionCode Infected_Move()
	{
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if (turnInfo.turnNumber == 0)
		{
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		int row = 0;
		int column = 0;


		for (row = 0; row < Constants.Classroom_Height; row++)
			for (column = 0; column < Constants.Classroom_Width; column++)
			{
				infectedCounts[row][column] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			}

		if (turnInfo.turnNumber <33)
		{
			int min_infected = Integer.MAX_VALUE;
			int min_row = -1;
			int min_column = -1;
			int min_distance = Constants.Classroom_Height * Constants.Classroom_Width;

			for (row = 0; row < Constants.Classroom_Height; row++)
				for (column = 0; column < Constants.Classroom_Width; column++)
				{
					int infectedCount = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
					int distance = center.GetDistance(row, column);

					if (infectedCount < min_infected)
					{
						min_infected = infectedCount;
						min_row = row;
						min_column = column;
						min_distance = distance;
					} else if (infectedCount == min_infected)
					{
						if (distance < min_distance)
						{
							min_row = row;
							min_column = column;
							min_distance = distance;
						}
					}
				}

			return new Point(min_row, min_column);
		} else
		{
			int max_infected = 0;
			int max_row = -1;
			int max_column = -1;
			int max_distance = Constants.Classroom_Height * Constants.Classroom_Width;

			for (row = 0; row < Constants.Classroom_Height; row++)
				for (column = 0; column < Constants.Classroom_Width; column++)
				{
					int infectedCount = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
					int distance = center.GetDistance(row, column);

					if (infectedCount > max_infected)
					{
						max_infected = infectedCount;
						max_row = row;
						max_column = column;
						max_distance = distance;

					} else if (infectedCount == max_infected)
					{
						if (distance < max_distance)
						{
							max_row = row;
							max_column = column;
							max_distance = distance;
						}
					}
				}
			return new Point(max_row, max_column);
		}
	}
}