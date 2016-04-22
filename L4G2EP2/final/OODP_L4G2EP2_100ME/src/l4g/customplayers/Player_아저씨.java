package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * PlayerName: ���ø����
 * 
 * Concept: ĳ������ ������ ��ȭ '������'�� ���ΰ� ���½��Դϴ�. �� ĳ���ʹ� ������ �����̷�, �� �Ϸ縸 ����� ����մϴ�. �׸���
 * �װԵǸ� �� �������ʹ� �׻� ��(����ü)�� �ִ� ���� �پ���, �ڽ��� �Ǹ� ������ �Ǹ� �� �ڸ����� ���������ʰ� ȸ���մϴ�.
 * 
 * ���� ����: �׻� �����մϴ�.
 * 
 * ������ �̵�: �÷��̾� ���� ���� ���� �������� �̵��մϴ�.
 *              �������� �þ� ������
 *                    0
 *                   123
 *                  45678
 *                   9AB
 *                    C
 *              ..�� ��
 *              ��:     0123�� �ִ� �÷��̾� ��
 *              ����:   1459�� �ִ� �÷��̾� ��
 *              ������: 378B�� �ִ� �÷��̾� ��
 *              �Ʒ�:   9ABC�� �ִ� �÷��̾� ��
 *              ..�� �ջ��Ͽ� ���մϴ�.
 * 
 * ����ü �̵�: �׻� ��ȭ �⵵�� �帳�ϴ�.
 * 
 * ��ȥ ��ġ: ����ü�� ���� ���� ĭ�� ��� ��ġ�մϴ�.
 * 
 * @author Racin
 *
 */
public class Player_������ extends Player {
	public Player_������(int ID) {

		super(ID, "���ø����");

		// ���� ������ �׻� �����մϴ�. ������ �ǵ帮�� ������, ��ȭ�Ӱ� ��ư��� ���մϴ�.
		this.trigger_acceptDirectInfection = false;

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
	void Init_Data() {

		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		// �����ϴ� �켱������ �� ����� �����߽��ϴ�.
		favoritePoint.row = 7;
		favoritePoint.column = 7;
	}

	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode GetMovableAdjacentDirection() {
		int iDirection;

		for (iDirection = 0; iDirection < 4; iDirection++) {
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break;
		}

		return directions[iDirection];
	}

	@Override
	public DirectionCode Survivor_Move() {
		/*
		 * ������ �̵�: �÷��̾� ���� ���� ���� �������� �̵��մϴ�.
		 *              �������� �þ� ������
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..�� ��
		 *              ��:     0123�� �ִ� �÷��̾� ��
		 *              ����:   1459�� �ִ� �÷��̾� ��
		 *              ������: 378B�� �ִ� �÷��̾� ��
		 *              �Ʒ�:   9ABC�� �ִ� �÷��̾� ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

		// 0
		row -= 2;

		if (row >= 0)
			numberOfPlayers[0] = cells[row][column].Count_Players();

		// 1, 2, 3
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfPlayers[1] = cells[row][column - 1].Count_Players();

			numberOfPlayers[2] = cells[row][column].Count_Players();

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[3] = cells[row][column + 1].Count_Players();
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1) {
			numberOfPlayers[5] = cells[row][column - 1].Count_Players();

			if (column >= 2)
				numberOfPlayers[4] = cells[row][column - 2].Count_Players();
		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].Count_Players();

			if (column < Constants.Classroom_Width - 2)
				numberOfPlayers[8] = cells[row][column + 2].Count_Players();
		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfPlayers[9] = cells[row][column - 1].Count_Players();

			numberOfPlayers[10] = cells[row][column].Count_Players();

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[11] = cells[row][column + 1].Count_Players();
		}

		// C
		++row;

		if (row < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[row][column].Count_Players();

		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
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

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			if (weights[iWeights] < min_weight) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
					min_weight = weights[iWeights];
					min_idx_weights = iWeights;
				}
			}
		}

		return directions[min_idx_weights];
	}

	@Override
	public void Corpse_Stay() {
		
	}

	@Override
	public DirectionCode Infected_Move() {

		// �׻� ��ȭ�⵵�� �帳�ϴ�.
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
			Init_Data();
		}
		/*
		 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
		 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
		 */

	}

	@Override
	public Point Soul_Spawn() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// ��ü ĭ�� �˻��Ͽ� ����ü ���� ���� ���� ĭ�� ã��
		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				CellInfo cell = this.cells[row][column];

				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

				int weight = numberOfInfecteds != 0 ? numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// ���� ���� ĭ�� �߰ߵǸ� ����
				if (weight > max_weight) {
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if (weight == max_weight) {
					// �Ÿ��� �� ������ ����
					if (distance < min_distance) {
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
					else if (distance == min_distance) {
						for (int iDirection = 0; iDirection < 4; iDirection++) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

							if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
									max_column)) {
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
}
