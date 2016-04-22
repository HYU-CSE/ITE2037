package l4g.customplayers;

import l4g.Cell;
import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_��Ư¡��Ư¡ extends Player {
	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(6, 6);

	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�. �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�. �� �޼���� �ݵ��
	 * �ʿ��մϴ�.
	 */
	void Init_Data() {

		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Down;
		directions[2] = DirectionCode.Left;
		directions[3] = DirectionCode.Right;

		favoritePoint.row = 6;
		favoritePoint.column = 6;
	}

	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�. �� �޼���� �ݵ�� �ʿ��մϴ�.
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

	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_��Ư¡��Ư¡(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "��Ư¡��Ư¡");

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

	/*
	 * WildCard 0���� Loner WildCard 1���� Survivor WildCard 2���� Scout WildCard 3����
	 * CorpseBomb WildCard 4���� Predator WildCard 5���� Suicide WildCard 6���� Prayer
	 */

	int wildCard = 0;

	void UpdateState() {
		// ���¿� ���� ���� ����
		switch (wildCard) {
		case 0: // WildCard 0���� Loner
			if (this.turnInfo.turnNumber < 12) {
				wildCard = 0;
				break;
			}

			if (this.turnInfo.turnNumber == 12) {
				if (this.myInfo.state == StateCode.Survivor) {
					wildCard = 1;
					break;
				} else {
					wildCard = 6;
					break;
				}
			}

		case 1: // WildCard 1���� Survivor
			if (this.turnInfo.turnNumber > 12 && this.turnInfo.turnNumber < 41) {
				if (this.myInfo.state == StateCode.Survivor) {
					wildCard = 1;
					break;
				} else {
					wildCard = 6;
					break;
				}
			}

			if (this.turnInfo.turnNumber >= 41) {
				if (this.myInfo.state == StateCode.Survivor) {
					wildCard = 2;
					break;
				} else {
					wildCard = 6;
					break;
				}
			}

		case 2: // WildCard 2���� Scout
			if (this.turnInfo.turnNumber <= 70) {
				if (this.myInfo.state == StateCode.Survivor) {
					wildCard = 2;
					break;
				} else {
					wildCard = 6;
					break;
				}
			} else { // 70�ϱ��� ��� ������ �ϰ� ������ �� �ǹǷ� �ڻ�
				wildCard = 5;
				break;
			}

		case 3: // WildCard 3���� CorpseBomb
			wildCard = 4;
			break;

		case 4: // WildCard 4���� Predator
			wildCard = 4;
			break;

		case 5: // WildCard 5���� Suicide
			if (this.myInfo.state == StateCode.Survivor) {
				wildCard = 5;
				break;
			} else { // �ڻ� ���� �� Prayer ���� �ٷ� ����
				wildCard = 6;
				break;
			}

		case 6: // WildCard 6���� Prayer
			if (this.myInfo.state == StateCode.Infected) {
				wildCard = 6;
				break;
			} else { // ��ȥ ����
				if (this.turnInfo.turnNumber > 60) {
					wildCard = 3;
					break;
				} else {
					wildCard = 2;
					break;
				}
			}
		}
	}

	@Override
	public DirectionCode Survivor_Move() {
		// wildCard�� 0�̸� Loner ���� �����δ�.
		// wildCard�� 1�̸� Survivor ���� �����δ�.
		// wildCard�� 2�̸� Scout ���� �����δ�.
		// wildCard�� 5�� ��쿡�� �ڻ� �õ��� �Ѵ�.

		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		int[] weights = new int[4];
		int[] weights1 = new int[4];
		int max_weight = -1;
		int max_idx_weights = 0;
		int min_weight = Integer.MAX_VALUE;
		int min_idx_weights = 0;

		UpdateState();
		switch (wildCard) {
		case 0: // Loner ���� ������

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

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// ��: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// ����: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// ������: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)

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

		case 1: // Survivor ���� ������
			int[] numberOfInfecteds = new int[13];

			// 0
			row -= 2;

			if (row >= 0)
				numberOfInfecteds[0] = cells[row][column]	.CountIf_Players(player -> player.state == StateCode.Infected);

			// 1, 2, 3
			++row;

			if (row >= 0) {
				if (column >= 1)
					numberOfInfecteds[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

				numberOfInfecteds[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

				if (column < Constants.Classroom_Width - 1)
					numberOfInfecteds[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);;
			}

			// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
			++row;

			if (column >= 1) {
				numberOfInfecteds[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);;

				if (column >= 2)
					numberOfInfecteds[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}

			if (column < Constants.Classroom_Width - 1) {
				numberOfInfecteds[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

				if (column < Constants.Classroom_Width - 2)
					numberOfInfecteds[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			}

			// 9, A, B
			++row;

			if (row < Constants.Classroom_Height) {
				if (column >= 1)
					numberOfInfecteds[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

				numberOfInfecteds[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

				if (column < Constants.Classroom_Width - 1)
					numberOfInfecteds[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}

			// C
			++row;

			if (row < Constants.Classroom_Height)
				numberOfInfecteds[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// ��: 0123
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2]
							+ numberOfInfecteds[3];
					break;
				case Left:
					// ����: 1459
					weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[4] + numberOfInfecteds[5]
							+ numberOfInfecteds[9];
					break;
				case Right:
					// ������: 378B
					weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[7] + numberOfInfecteds[8]
							+ numberOfInfecteds[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weights[iWeights] = numberOfInfecteds[9] + numberOfInfecteds[10] + numberOfInfecteds[11]
							+ numberOfInfecteds[12];
					break;
				}
			}

			// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)

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

		case 2: // Scout ���� ������

			// ���� ���̴� 13���� ��쿡 ���� ������ �� ���

			// 0
			row -= 2;

			if (row >= 0)
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			// 1, 2, 3
			++row;

			if (row >= 0) {
				if (column >= 1)
					numberOfPlayers[1] = cells[row][column - 1]
							.CountIf_Players(player -> player.state == StateCode.Survivor);

				numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

				if (column < Constants.Classroom_Width - 1)
					numberOfPlayers[3] = cells[row][column + 1]
							.CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
			++row;

			if (column >= 1) {
				numberOfPlayers[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

				if (column >= 2)
					numberOfPlayers[4] = cells[row][column - 2]
							.CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[7] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

				if (column < Constants.Classroom_Width - 2)
					numberOfPlayers[8] = cells[row][column + 2]
							.CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			// 9, A, B
			++row;

			if (row < Constants.Classroom_Height) {
				if (column >= 1)
					numberOfPlayers[9] = cells[row][column - 1]
							.CountIf_Players(player -> player.state == StateCode.Survivor);

				numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

				if (column < Constants.Classroom_Width - 1)
					numberOfPlayers[11] = cells[row][column + 1]
							.CountIf_Players(player -> player.state == StateCode.Survivor);
			}

			// C
			++row;

			if (row < Constants.Classroom_Height)
				numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// ��: 0123
					weights1[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// ����: 1459
					weights1[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// ������: 378B
					weights1[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weights1[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				if (weights1[iWeights] > max_weight) {
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

					if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
							&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
						max_weight = weights1[iWeights];
						max_idx_weights = iWeights;
					}
				}
			}

			return directions[max_idx_weights];

		default: // wildCard�� 5�� ��
			// �ڻ��� �ؾ� �ϹǷ� ����ü�� ���� ���� ��� ã�ư�.

			// ���� ���̴� 13���� ��쿡 ���� ������ �� ���

			// 0
			row -= 2;

			if (row >= 0)
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			// 1, 2, 3
			++row;

			if (row >= 0) {
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

			if (column >= 1) {
				numberOfPlayers[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

				if (column >= 2)
					numberOfPlayers[4] = cells[row][column - 2]
							.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[7] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

				if (column < Constants.Classroom_Width - 2)
					numberOfPlayers[8] = cells[row][column + 2]
							.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			// 9, A, B
			++row;

			if (row < Constants.Classroom_Height) {
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

			// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// ��: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// ����: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// ������: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				if (weights[iWeights] > max_weight) {
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

					if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
							&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
						max_weight = weights[iWeights];
						max_idx_weights = iWeights;
					}
				}
			}

			return directions[max_idx_weights];
		}

		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// wildCard�� 4�� ��� Predator ���� �����Ѵ�.
		// wildCard�� 6�� ��� ��ü�� ���� �⵵�� �ø���.
		UpdateState();
		switch (wildCard) {
		case 4:
			// ���� ĭ�� ��ü�� �ִٸ� ������ ���
			if (this.cells[myInfo.position.row][myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Stay;

			else { // ���� ĭ�� �ִ� ������ ���� �þ� �� ĭ�鿡 �ִ� ������ ���� ���ؼ� �ӹ����� ���������� ������
				for (int row = 0; row < Constants.Classroom_Height; row++) {
					for (int column = 0; column < Constants.Classroom_Width; column++) {
						CellInfo cell = this.cells[row][column];

						int weight = cell.CountIf_Players(player -> player.state == StateCode.Infected);
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

				// �˻��ߴµ� �����ڰ� �ϳ��� ���ٸ� �׳� �ӹ�����
				if (max_weight == 0) {
					return DirectionCode.Stay;
				}

				if (cells[max_row][max_column].CountIf_Players(
						player -> player.state == StateCode.Infected) == this.cells[this.myInfo.position.row][this.myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Infected)) {
					return DirectionCode.Stay;
				}
				return GetMovableAdjacentDirection();
			}

		default: // wildCard�� 6�� ���
			// �� �ؿ� ��ü�� ��� ������ ������
			if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
				return GetMovableAdjacentDirection();

			// �׷��� ������ ��ȭ �⵵ �õ�
			return DirectionCode.Stay;
		}

		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
			Init_Data();
		}
	}

	int[][] counts = new int[Constants.Classroom_Height][Constants.Classroom_Width];

	@Override
	public Point Soul_Spawn() {
		// wildCard�� 2�� ��� �����ڵ��� ���� ���� ���� �������� ������ �� �� �ֵ��� �Ѵ�.
		// wildCard�� 3�̸� �����ڵ��� ���� ���� ���� �������� ��ü��ź�� �ȴ�.
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		UpdateState();

		switch (wildCard) {
		case 0: // ���� ��ġ
			return new Point(6, 6);

		case 2: // ������ ���� ������ ���� ���� ���� ĭ�� ã��
			// ��ü ĭ�� �˻��Ͽ� ������ ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int weight = cell.CountIf_Players(player -> player.state == StateCode.Survivor);
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

			// �˻��ߴµ� �����ڰ� �ϳ��� ���ٸ� ��ġ ����
			if (max_weight == 0) {
				int variableToMakeError = 0;

				variableToMakeError = variableToMakeError / variableToMakeError;
			}

			return new Point(max_row, max_column);

		default: // wildCard�� 3�� ��
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
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

			// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ġ ����
			if (max_weight == 0) {
				int variableToMakeError = 0;

				variableToMakeError = variableToMakeError / variableToMakeError;
			}

			return new Point(max_row, max_column);
		}

		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}
}
