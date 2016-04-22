package l4g.customplayers;

import l4g.Classroom;
import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;
import l4g.common.StateCode;

/**
 * 
 * 
 * ���� ����: �׻� �����մϴ�.
 * 
 * ������ �̵�:1. ���� �ٴ� ��� ������ ������ ������ �ʵ��� ������ �������ϴ�
 * 2. ����� ����ü�� ������ Ȯ���� ����ü�� ���� ������ �̵��մϴ�
 * 3. �밢�� ����� ��ĭ ������ ���� ����ü�� ������ Ȯ���� ���մϴ�
 * 4. else ������ ���� ���� ���� �������� �̵��մϴ�.(Scout����) �������� �þ� ������ 0 123 45678 9AB C ..�� �� ��: 0123��
 * �ִ� ������ �� ����: 1459�� �ִ� ������ �� ������: 378B�� �ִ� ������ �� �Ʒ�: 9ABC�� �ִ� ������ �� ..�� �ջ��Ͽ�
 * ���մϴ�.
 * 
 * ����ü �̵�: 1. ���� ĭ�� ��ü�� �ִٸ� ������ ���ֽ��ϴ�
 * 2.��  ĭ�� ��ü�� �ִٸ� �� ĭ���� �̵��մϴ�. �׷��� ���� ��� ��ȭ �⵵�� �帳�ϴ�.
 * 
 * ��ȥ ��ġ: �������� Ž����  3 X 3 ĭ �ȿ� ����ü�� ���°����� ��Ȱ�մϴ�.
 * ����ȭ�� ��Ҹ� ��ã���� �⺻ ��Ȱ��ҿ��� ��Ȱ
 * 
 * @author Racin
 *
 */
public class Player_run_survive extends Player {
	/*
	 * -------------------------------------------------- TODO �� �о� ������!
	 * 
	 * ��� Bot �÷��̾���� ���� �ٸ� �ǻ� ������ �����ϱ� ���� �ڽ��� ID�� ���� ��ȣ�� �̿��Ͽ� ������ '���� �켱����'��
	 * '��ȣ�ϴ� ĭ'�� ���� �ǻ� ������ Ȱ���մϴ�. ���� �������� Bot �÷��̾��� �ڵ带 ������ ����Ϸ��� ��� ���� �۾����� ��
	 * �Բ� �����ؾ� �մϴ�.
	 * 
	 * - �Ʒ��� '�� field, �޼���� �ݵ�� �ʿ��մϴ�'�� ���� �ִ� �͵��� ��� ������ �;� �մϴ�.
	 * 
	 * - Soul_Stay()�� �ִ�, �ʱ�ȭ ���� �ڵ带 ������ �;� �մϴ�.
	 */

	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(0, 0);

	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�. �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�. �� �޼���� �ݵ��
	 * �ʿ��մϴ�.
	 */
	void Init_Data() {
		long seed = (ID + 2016) * gameNumber + ID;

		if (seed < 0)
			seed = -seed;

		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;

		favoritePoint.row = (int) (seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int) (seed % Constants.Classroom_Height);
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

	public Player_run_survive(int ID) {
		super(ID, "����Ŭ����" + ID);

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
	}/// �����ڷ� ���� ��Ƴ���, ���� �÷��̾�鿡�� ������ �ִ°��� �����Դϴ�. ���� ������ ���� ���� �ʽ��ϴ�

	@Override
	public DirectionCode Survivor_Move() {
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		// �÷��̾��� ���� ��ġ�� ��ȯ
		
		int UP = 0, DP = 0, RP = 0, LP = 0; // �⺻������ U D R L�� ���� P�� Predator�� ����
		int UUP = 0, URP = 0, RRP = 0, DRP = 0, DDP = 0, DLP = 0, LLP = 0, ULP = 0; 
		/// U�� L���� R������ D�Ʒ�
		/// �밢������� ��ĭ ���������� ����ü�� �ִ��� Ȯ��
		////if ���� ���� ����ü���� ������ ���� ���� =>> ������ ���� ������ �����ϴ� ���� ������ �Ϸ���
		if (column != 0) {
			LP = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (column != 12) {
			RP = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (row != 0) {
			UP = cells[row - 1][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (row != 12) {
			DP = cells[row + 1][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if (column != 0 && row != 0) {
			ULP = cells[row - 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (column != 0 && row != 12) {
			DLP = cells[row + 1][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (column != 12 && row != 0) {
			URP = cells[row - 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (column != 12 && row != 12) {
			DRP = cells[row + 1][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if (column != 12 && column != 11) {
			RRP = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (row != 12 && row != 11) {
			DDP = cells[row + 2][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (column != 0 && column != 1) {
			LLP = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		if (row != 0 && row != 1) {
			UUP = cells[row - 2][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		/// �÷��̾��� ��濡 ����ü ���� ���� , �迭������ ������ʵ��� ����!!
		if (row == 0) {
			return DirectionCode.Down;
		} else if (row == Constants.Classroom_Height - 1) {
			return DirectionCode.Up;
		} else if (column == Constants.Classroom_Width - 1) {
			return DirectionCode.Left;
		} else if (column == 0) {
			return DirectionCode.Right;
		} //// �÷��̾ ������ ������ �ʰ� ���� �ٰԵǸ� ����������
		else if (UP > 0 && DP > 0 && RP > 0) { //// ����ü ���� ���⿡ ���� �̵� ���� �ڵ��ϼ���
												//// �켱���� �Ʒ��� ������ �켱������ ���� �̵�
			return DirectionCode.Left;
		} else if (UP > 0 && DP > 0 && LP > 0) {
			return DirectionCode.Right;
		} else if (UP > 0 && RP > 0 && LP > 0) {
			return DirectionCode.Down;
		} else if (DP > 0 && LP > 0 && RP > 0) {
			return DirectionCode.Up;
		} else if (DP > 0 && UP > 0) {
			return DirectionCode.Right;
		} else if (DP > 0 && LP > 0) {
			return DirectionCode.Up;
		} else if (RP > 0 && UP > 0) {
			return DirectionCode.Left;
		} else if (RP > 0 && LP > 0) {
			return DirectionCode.Up;
		} else if (LP > 0 && UP > 0) {
			return DirectionCode.Right;
		} else if (LP > 0 && RP > 0) {
			return DirectionCode.Down;
		} else if (LP > 0) {
			return DirectionCode.Right;
		} else if (UP > 0) {
			return DirectionCode.Down;
		} else if (DP > 0) {
			return DirectionCode.Up;
		} else if (RP > 0) {
			return DirectionCode.Left;
		} else if (LLP > 0 || DLP > 0) {
			return DirectionCode.Right;
		} else if (UUP > 0 || ULP > 0) {
			return DirectionCode.Down;
		} else if (DDP > 0 || DRP > 0) {
			return DirectionCode.Up;
		} else if (URP > 0 || RRP > 0) {
			return DirectionCode.Left;
		} else {/*///���⼭���� ������ ���̽�������  bot�� Scout�� �̵��� �����Ѵ�
				 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�. �������� �þ� ������ 0 123 45678 9AB
				 * C ..�� �� ��: 0123�� �ִ� ������ �� ����: 1459�� �ִ� ������ �� ������: 378B�� �ִ�
				 * ������ �� �Ʒ�: 9ABC�� �ִ� ������ �� ..�� �ջ��Ͽ� ���մϴ�.
				 */
			int[] numberOfPlayers = new int[13];

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

			// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
			int max_weight = -1;
			int max_idx_weights = 0;

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
	}

	@Override
	public void Corpse_Stay() {
	}

	@Override
	public DirectionCode Infected_Move() {

		int row = myInfo.position.row;
		int column = myInfo.position.column;
		// ���� ĭ�� ��ü�� �ִٸ� ������ ���
		if (this.cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
			return DirectionCode.Stay;
		// ��,�Ʒ�,������,���� ������ ��ü�� �ִ��� Ž�� �ִٸ� �̵�
		// �迭������ ������ʴ��� Ȯ�� �ʼ�
		if (column != 0) {

			if (this.cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Left;
		}
		if (column != Constants.Classroom_Height - 1) {

			if (this.cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Right;
		}
		if (row != Constants.Classroom_Width - 1) {

			if (this.cells[row + 1][column].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Down;
		}
		if (row != 0) {

			if (this.cells[row - 1][column].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Up;
		}

		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn() {
		int result_row = 7;
		int result_column = 6; /// ��Ȱ ��� �⺻��

		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		/// �� ������ 1,1���� Ž���Ͽ� �ֺ� 3X3 ������ ����ü�� ���°����� ��Ȱ
		/// 1,1 ���� ��Ȱ��Ҹ� Ž��

		OUT: for (int i = 1; i < 12; i++) {
			for (int j = 1; j < 12; j++) {
				boolean good_point = true; /// ��Ȱ�ϱ� ���� ����̴� �����ϰ� �ݺ��� ����
				IN: for (int k = -1; k <= 1; k++) {
					for (int l = -1; l <= 1; l++) {
						if (cells[i + k][j + l].CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
							good_point = false; //// Ž���ϴ� �ڸ��� ����ü�������� false�� �ٲٰ�
												//// �ݺ��� Ż��
							break IN;
						}
					}
				}

				if (good_point == true) {
					result_row = i;
					result_column = j;
					break OUT;
				}

			}
		}
		/// ���õ� ��Ȱ��ҿ��� ��Ȱ ���� ��������Ҹ� ��ã�Ҵٸ� �⺻������ ��Ȱ
		return new Point(result_row, result_column);
	}

}
