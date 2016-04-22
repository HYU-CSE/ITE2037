package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class SangU extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);
	boolean deadOnce = false;

	void Init_Data() {
		/*
		 * �������� �ۼ��ϴ� �÷��̾�� ������ ���ǽǿ� �� �� �� �����ϹǷ� ��� �Ʒ��� �ڵ带 �� �ʿ� ����
		 * 
		 * directions[0] = DirectionCode.Up; directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right; directions[3] =
		 * DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6; favoritePoint.column = 6;
		 * 
		 * ...�� ���� �������� �����ϴ� �켱 ������ �׳� �ٷ� �Ҵ��� �ᵵ �����մϴ�. (Bot���� �Ȱ��� Ŭ������ �ν��Ͻ��� ����
		 * �����ϹǷ� �̷� ���� �ؾ� �մϴ�).
		 */

		// �÷��̾�� �ٸ� ID, ���Ӹ��� �ٸ� ���� ��ȣ�� �����Ͽ� ���� �̻��� ���� �����
		long seed = (ID + 2016) * gameNumber + ID;

		if (seed < 0)
			seed = -seed;

		// �� ���� 24�� ���� �������� ���� ���� �켱���� ����
		switch ((int) (seed % 24)) {
			case 0:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Down;
				break;
			case 1:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Right;
				break;
			case 2:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Down;
				break;
			case 3:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Left;
				break;
			case 4:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Right;
				break;
			case 5:
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Left;
				break;
			case 6:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Down;
				break;
			case 7:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Right;
				break;
			case 8:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Down;
				break;
			case 9:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Up;
				break;
			case 10:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Right;
				break;
			case 11:
				directions[0] = DirectionCode.Left;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Up;
				break;
			case 12:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Down;
				break;
			case 13:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Left;
				break;
			case 14:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Down;
				break;
			case 15:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Up;
				break;
			case 16:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Left;
				break;
			case 17:
				directions[0] = DirectionCode.Right;
				directions[1] = DirectionCode.Down;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Up;
				break;
			case 18:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Right;
				break;
			case 19:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Up;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Left;
				break;
			case 20:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Right;
				break;
			case 21:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Right;
				directions[3] = DirectionCode.Up;
				break;
			case 22:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Left;
				break;
			case 23:
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Left;
				directions[3] = DirectionCode.Up;
				break;
		}

		favoritePoint.row = (int) seed / Constants.Classroom_Width % Constants.Classroom_Height;
		favoritePoint.column = (int) seed % Constants.Classroom_Height;
	}

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

	public SangU(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "���ġ������");

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
	public DirectionCode Survivor_Move() {
		// �ѹ��� ���׾��� ���� �����̿� ���� �˰��� ���� Ƚ���� ���Դϴ�
		if (!deadOnce && Math.abs(gameNumber) % 2 == 1 && turnInfo.turnNumber < 80) {
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
		} else {
			// �ѹ��̶� �׾��� ��� ���� ������ �ִ��� ���̴� �������� �̵��մϴ�.
			int[] survivors = new int[4];
			int[] others = new int[4];
			int[] weights = new int[4];
			int row, col, max_weight = -1;

			// ���� ������ ��� �÷��̾ ���� �˻� ����
			for (row = 0; row < Constants.Classroom_Height; row++) {
				for (col = 0; col < Constants.Classroom_Width; col++) {
					if (cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor) > 0) {
						if (myInfo.position.row > row)
							survivors[0] += cells[row][col]
									.CountIf_Players(player -> player.state == StateCode.Survivor);
						if (myInfo.position.column > col)
							survivors[1] += cells[row][col]
									.CountIf_Players(player -> player.state == StateCode.Survivor);
						if (myInfo.position.column < col)
							survivors[2] += cells[row][col]
									.CountIf_Players(player -> player.state == StateCode.Survivor);
						if (myInfo.position.row < row)
							survivors[3] += cells[row][col]
									.CountIf_Players(player -> player.state == StateCode.Survivor);
					} else if (cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected) > 0
							|| cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
						if (myInfo.position.row > row)
							others[0] += (cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected)
									+ cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse));
						if (myInfo.position.column > col)
							others[1] += (cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected)
									+ cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse));
						if (myInfo.position.column < col)
							others[2] += (cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected)
									+ cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse));
						if (myInfo.position.row < row)
							others[3] += (cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected)
									+ cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse));
					}
				}
			}

			// ���� ��밪: ������ �� x ��ü �� ����ü �� ���
			for (int i = 0; i < 4; i++)
				weights[i] = survivors[i] * others[i];

			// �� �� ���� ���⿡ ���� ��밪�� �ּҰ����� ����
			if (IsValidMove(DirectionCode.Up) == false)
				weights[0] = -1;

			if (IsValidMove(DirectionCode.Left) == false)
				weights[1] = -1;

			if (IsValidMove(DirectionCode.Right) == false)
				weights[2] = -1;

			if (IsValidMove(DirectionCode.Down) == false)
				weights[3] = -1;

			// ������ ��밪�� �ִ밪�� ������ ���
			for (int weight : weights) {
				if (weight > max_weight) {
					max_weight = weight;
				}
			}

			// ó�� directions ������ ���� ��밪�� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for (int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection) {
				if (directions[iShuffledDirection] == DirectionCode.Up) {
					if (weights[0] == max_weight)
						return directions[iShuffledDirection];
				} else if (directions[iShuffledDirection] == DirectionCode.Left) {
					if (weights[1] == max_weight)
						return directions[iShuffledDirection];
				} else if (directions[iShuffledDirection] == DirectionCode.Right) {
					if (weights[2] == max_weight)
						return directions[iShuffledDirection];
				} else if (directions[iShuffledDirection] == DirectionCode.Down) {
					if (weights[3] == max_weight)
						return directions[iShuffledDirection];
				}
			}
		}
		return DirectionCode.Stay;
	}

	boolean IsValidMove(DirectionCode direction) {
		// ������ �̵����� �˻�
		// ���� ���� ���� ������ �Ǵ� ����ü���
		if (myInfo.state == StateCode.Survivor || myInfo.state == StateCode.Infected) {
			// ������ ������ '���'�� ����
			if (direction == null)
				direction = DirectionCode.Stay;

			// �����ڰ� ���ڸ��� ����ϴ� ���� �Ұ���
			if (myInfo.state == StateCode.Survivor && direction == DirectionCode.Stay)
				return false;

			// ����������, �ش� ��ǥ�� ��ȿ���� ���� �˻�
			switch (direction) {
				case Up:
					return (myInfo.position.row - 1 >= 0);
				case Left:
					return (myInfo.position.column - 1 >= 0);
				case Down:
					return (myInfo.position.row + 1 < Constants.Classroom_Height);
				case Right:
					return (myInfo.position.column + 1 < Constants.Classroom_Width);
				case Stay:
					if (myInfo.state == StateCode.Infected)
						return true;
				default:
					return false;
			}
		}

		// �����ڳ� ����ü�� �ƴ϶�� �̵� ��ü�� �Ұ���
		else
			return false;
	}

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		// ��ȭ�⵵ ������ �մϴ� ��ü ������ ��¦ �����ִ� �ų�
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();

		// ������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
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

	@Override
	public Point Soul_Spawn() {
		// ó�� ��ġ�Ҷ� �� �������� ��ġ��ġ �ٸ����Ѵ�.
		if (turnInfo.turnNumber == 0) {
			if (Math.abs(gameNumber % 4) == 0) {
				return new Point(3, 3);
			} else if (Math.abs(gameNumber % 4) == 1) {
				return new Point(Constants.Classroom_Height - 4, 3);
			} else if (Math.abs(gameNumber % 4) == 2) {
				return new Point(3, Constants.Classroom_Width - 4);
			} else {
				return new Point(Constants.Classroom_Height - 4, Constants.Classroom_Width - 4);
			}
		}
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
		// �� ��쿡 ���� ��ġ.
		// ������,��ȥ ������ : ����ü�ִ°�����, �����ȴ�.
		// ����ü, ��ü ������ : ������ �ֺ����� �� ���������� ���δ�.
		Point pointToSpawn = favoritePoint;

		int row, col;
		
		// ���� 70���� �Ѿ�� ��ü��ź���� ���մϴ�.
		if (turnInfo.turnNumber > 70) {
			// �� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
			int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int max_weight = -1;
			ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

			// ��� ����ü�� ���� �˻� ���� - 13x13ĭ�� �÷��̾�� 100���̹Ƿ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
			for (row = 0; row < Constants.Classroom_Height; row++) {
				for (col = 0; col < Constants.Classroom_Width; col++) {
					Point nowPoint = new Point(row, col);
					weights[row][col] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
					// �ִ밪�� �ٲ�� '����ü�� ���� ���� ĭ' ����� �ʱ�ȭ
					if (weights[row][col] > max_weight) {
						max_weight = weights[row][col];
						list_pos_max_weight.clear();
					}
					// ���� ĭ�� �������� ���� �ִ밪�� ������ '�����ڰ� ���� ���� ĭ' ��Ͽ� ���� ĭ �߰�
					if (weights[row][col] == max_weight)
						list_pos_max_weight.add(nowPoint);
				}
			}

			// �˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� ����Ʈ�� ������ �� �ֺ��� ���� ������ ������ Ž��
			int min_distance = Integer.MAX_VALUE;
			Point myPosition = new Point(myInfo.position.row, myInfo.position.column);

			for (Point pos_max_weight : list_pos_max_weight) {
				int distance = myPosition.GetDistance(pos_max_weight);

				if (distance < min_distance) {
					min_distance = distance;
					pointToSpawn = pos_max_weight;
				}
			}

			return pointToSpawn;
		}
		// ����ü, ��ü ������ : ������ �ֺ����� �� ���������� ���δ�.
		// else {
		// �� ĭ�� �ִ� ������ ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		for (int i = 0; i < Constants.Classroom_Height; i++) {
			for (int j = 0; j < Constants.Classroom_Width; j++) {
				weights[i][j] = 0;
			}
		}

		// ��� ����ü�� ���� �˻� ���� - 13x13ĭ�� �÷��̾�� 100���� ���� �����ڰ� ���� ���� ĭ ����� ���ÿ� ����

		for (row = 0; row < Constants.Classroom_Height; row++) {
			for (col = 0; col < Constants.Classroom_Width; col++) {
				Point nowPoint = new Point(row, col);
				weights[row][col] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
				// �ִ밪�� �ٲ�� '������ ���� ���� ĭ' ����� �ʱ�ȭ
				if (weights[row][col] > max_weight) {
					max_weight = weights[row][col];
					list_pos_max_weight.clear();
				}
				// ���� ĭ�� �������� ���� �ִ밪�� ������ ����ĭ�� '�����ڰ� ���� ���� ĭ' ��Ͽ� �߰�
				if (weights[row][col] == max_weight)
					list_pos_max_weight.add(nowPoint);
			}
		}

		// �˻簡 ������ �����ڰ� ���� ���� ĭ ��Ͽ��� '���� ����' ������� ���� �� ������ ���� ����� ù��° ĭ ����
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = new Point(myInfo.position.row, myInfo.position.column);

		for (Point pos_max_weight : list_pos_max_weight) {
			int distance = myPosition.GetDistance(pos_max_weight);
			if (distance < min_distance) {
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}

		return pointToSpawn;
	}
}
