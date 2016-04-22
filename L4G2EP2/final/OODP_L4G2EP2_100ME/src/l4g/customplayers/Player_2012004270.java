package l4g.customplayers;
// �׳� �ѹ� ����� ��ü ��ź  ����

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 *
 */
public class Player_2012004270 extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_2012004270(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "201200XX70");

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

	final int GoRight = 0;
	final int GoDown = 1;
	final int GoLeft = 2;
	final int GoUp = 3;
	final int Stay = 4;
	private int state = GoRight;

	private int surMode = 0;
	private int infMode = 0;
	private int stateInfCnt = 0;

	private int[][] GetAdjacentInfectedtable() {
		int[][] result = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 } };
		int row = myInfo.position.row - 2;
		int col = myInfo.position.column - 2;
		if (row >= 0) {
			if (col >= 0) {
				result[0][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col >= 0) {
				result[0][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			result[0][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			col++;
			if (col <= 12) {
				result[0][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col <= 12) {
				result[0][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col -= 4;
		}
		row++;
		if (row >= 0) {
			if (col >= 0) {
				result[1][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col >= 0) {
				result[1][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			result[1][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			col++;
			if (col <= 12) {
				result[1][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col <= 12) {
				result[1][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col -= 4;
		}
		row++;
		if (col >= 0) {
			result[2][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		col++;
		if (col >= 0) {
			result[2][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		col++;
		result[2][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
		col++;
		if (col <= 12) {
			result[2][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		col++;
		if (col <= 12) {
			result[2][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		col -= 4;
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[3][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col >= 0) {
				result[3][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			result[3][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			col++;
			if (col <= 12) {
				result[3][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col <= 12) {
				result[3][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col -= 4;
		}
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[4][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col >= 0) {
				result[4][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			result[4][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			col++;
			if (col <= 12) {
				result[4][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			col++;
			if (col <= 12) {
				result[4][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		return result;
	}

	private int[][] GetAdjacentCorpsetable() {
		int[][] result = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 } };
		int row = myInfo.position.row - 2;
		int col = myInfo.position.column - 2;
		if (row >= 0) {
			if (col >= 0) {
				result[0][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col >= 0) {
				result[0][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			result[0][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			col++;
			if (col <= 12) {
				result[0][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col <= 12) {
				result[0][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col -= 4;
		}
		row++;
		if (row >= 0) {
			if (col >= 0) {
				result[1][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col >= 0) {
				result[1][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			result[1][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			col++;
			if (col <= 12) {
				result[1][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col <= 12) {
				result[1][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col -= 4;
		}
		row++;
		if (col >= 0) {
			result[2][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		col++;
		if (col >= 0) {
			result[2][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		col++;
		result[2][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
		col++;
		if (col <= 12) {
			result[2][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		col++;
		if (col <= 12) {
			result[2][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		col -= 4;
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[3][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col >= 0) {
				result[3][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			result[3][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			col++;
			if (col <= 12) {
				result[3][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col <= 12) {
				result[3][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col -= 4;
		}
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[4][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col >= 0) {
				result[4][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			result[4][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			col++;
			if (col <= 12) {
				result[4][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
			col++;
			if (col <= 12) {
				result[4][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		return result;
	}

	private int[][] GetAdjacentSurvivortable() {
		int[][] result = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 } };
		int row = myInfo.position.row - 2;
		int col = myInfo.position.column - 2;
		if (row >= 0) {
			if (col >= 0) {
				result[0][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col >= 0) {
				result[0][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			result[0][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			col++;
			if (col <= 12) {
				result[0][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col <= 12) {
				result[0][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col -= 4;
		}
		row++;
		if (row >= 0) {
			if (col >= 0) {
				result[1][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col >= 0) {
				result[1][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			result[1][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			col++;
			if (col <= 12) {
				result[1][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col <= 12) {
				result[1][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col -= 4;
		}
		row++;
		if (col >= 0) {
			result[2][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		col++;
		if (col >= 0) {
			result[2][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		col++;
		result[2][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
		col++;
		if (col <= 12) {
			result[2][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		col++;
		if (col <= 12) {
			result[2][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		col -= 4;
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[3][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col >= 0) {
				result[3][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			result[3][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			col++;
			if (col <= 12) {
				result[3][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col <= 12) {
				result[3][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col -= 4;
		}
		row++;
		if (row <= 12) {
			if (col >= 0) {
				result[4][0] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col >= 0) {
				result[4][1] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			result[4][2] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			col++;
			if (col <= 12) {
				result[4][3] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			col++;
			if (col <= 12) {
				result[4][4] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
		}

		return result;
	}

	private void UpdateStateToMaxDirection(int left, int right, int down, int up) {
		if (left >= right && left >= down && left >= up) {
			if (left == 0 && myInfo.position.column == 0) {
				left = -100;
				UpdateStateToMaxDirection(left, right, down, up);
				return;
			} else if (left == right && myInfo.position.column < 6) {
				state = GoRight;
				return;
			} else {
				state = GoLeft;
				return;
			}
		}
		if (right >= left && right >= down && right >= up) {
			if (right == 0 && myInfo.position.column == 12) {
				right = -100;
				UpdateStateToMaxDirection(left, right, down, up);
				return;
			} else {
				state = GoRight;
				return;
			}

		}
		if (up >= right && up >= down && up >= down) {
			if (up == 0 && myInfo.position.row == 0) {
				up = -100;
				UpdateStateToMaxDirection(left, right, down, up);
				return;
			} else if (up == down && myInfo.position.row < 6) {
				state = GoDown;
				return;
			} else {
				state = GoUp;
				return;
			}
		}
		if (down >= right && down >= left && down >= up) {
			if (down == 0 && myInfo.position.row == 12) {
				down = -100;
				UpdateStateToMaxDirection(left, right, down, up);
				return;
			} else {
				state = GoDown;
				return;
			}
		}
	}

	private void UpdateStateToMaxDirection(int left, int right, int down, int up, int stay) {
		if (stay >= left && stay >= right && stay >= up && stay > down) {
			state = Stay;
			return;
		}

		if (left >= right && left >= down && left >= up) {
			if (left == 0 && myInfo.position.column < 6) {
				state = GoRight;
			} else {
				state = GoLeft;
			}

			return;
		}
		if (right >= left && right >= down && right >= up) {
			state = GoRight;
			return;
		}
		if (up >= right && up >= down && up >= down) {
			if (up == down && myInfo.position.row < 6) {
				state = GoDown;
			} else {
				state = GoUp;
			}
			return;
		}
		if (down >= right && down >= left && down >= up) {
			state = GoDown;
			return;
		}
	}

	private void UpdateStateToMinDirection(int left, int right, int down, int up) {
		if (left <= right && left <= down && left <= up) {
			state = GoLeft;
			return;
		}
		if (right <= left && right <= down && right <= up) {
			state = GoRight;
			return;
		}
		if (down <= right && down <= left && down <= up) {
			state = GoDown;
			return;
		}
		if (up <= right && up <= down && up <= down) {
			state = GoUp;
			return;
		}
	}

	private void UpdateSurvivorState() {
		if (this.turnInfo.turnNumber <= 8) {
			switch (this.turnInfo.turnNumber % 4) {
			case 0:
				state = GoUp;
				return;
			case 1:
				state = GoRight;
				return;
			case 2:
				state = GoDown;
				return;
			case 3:
				state = GoLeft;
				return;
			}
		}

		if (this.turnInfo.turnNumber <= 11) {
			int left, right, down, up;
			left = cells[myInfo.position.row - 1][myInfo.position.column - 1].Count_Players()
					+ cells[myInfo.position.row + 1][myInfo.position.column - 1].Count_Players()
					+ cells[myInfo.position.row][myInfo.position.column - 2].Count_Players();
			right = cells[myInfo.position.row - 1][myInfo.position.column + 1].Count_Players()
					+ cells[myInfo.position.row + 1][myInfo.position.column + 1].Count_Players()
					+ cells[myInfo.position.row][myInfo.position.column + 2].Count_Players();
			down = cells[myInfo.position.row - 2][myInfo.position.column].Count_Players()
					+ cells[myInfo.position.row - 1][myInfo.position.column + 1].Count_Players()
					+ cells[myInfo.position.row - 1][myInfo.position.column - 1].Count_Players();
			up = cells[myInfo.position.row + 2][myInfo.position.column].Count_Players()
					+ cells[myInfo.position.row + 1][myInfo.position.column - 1].Count_Players()
					+ cells[myInfo.position.row + 1][myInfo.position.column + 1].Count_Players();
			UpdateStateToMinDirection(left, right, down, up);
			return;
			// ���� ���Կü� �ִ� ���� �̵�;
		}

		if (this.turnInfo.turnNumber >= 70&&myScore.corpse_max==0)
			surMode++; // 60�� ��� ����־��ٸ� �����ױ���� ����

		if (surMode == 1) { // �ױ��� 12�Ϻ����� ��Ƴ��� �ൿ ������Ʈ �ʿ�**
							// �������� �����鼭 ����
			// ���� �ش���⿡ ��ü�ִµ� �ֺ��� ����ü������ ����ġ
			if (myInfo.position.row <= 10 && myInfo.position.row >= 2 && myInfo.position.column >= 2
					&& myInfo.position.column <= 10) {
				int left, right, down, up;
				if (cells[myInfo.position.row - 1][myInfo.position.column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row][myInfo.position.column - 2]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Infected) == 0) {

					left = 0;
				} else {
					left = -(cells[myInfo.position.row - 1][myInfo.position.column - 1]
							.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column - 2]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Corpse) );
				}
				if (cells[myInfo.position.row - 1][myInfo.position.column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row][myInfo.position.column + 2]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
					right = 0;
				} else {
					right =-( cells[myInfo.position.row - 1][myInfo.position.column + 1]
							.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column + 2]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Corpse) );
				}
				if (cells[myInfo.position.row + 2][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row + 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Infected) == 0) {

					down = 0;

				} else {
					down = -(cells[myInfo.position.row + 2][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Corpse)) ;
				}
				if (cells[myInfo.position.row - 2][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Infected)
						+ cells[myInfo.position.row - 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
					up = 0;
				} else {
					up = -(cells[myInfo.position.row - 2][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row - 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[myInfo.position.row - 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Corpse)) ;
				}
				UpdateStateToMaxDirection(left, right, down, up);
				return;
			}
			// �׵θ� ����;
			else {
				int left, right, down, up;
				int[][] infectedArr = GetAdjacentInfectedtable();
				left = -(infectedArr[1][1] + infectedArr[2][0] + infectedArr[2][1] + infectedArr[3][1]);
				right = -(infectedArr[1][3] + infectedArr[2][3] + infectedArr[2][4] + infectedArr[3][3]);
				down = -(infectedArr[3][1] + infectedArr[3][2] + infectedArr[3][3] + infectedArr[4][2]);
				up = -(infectedArr[1][1] + infectedArr[1][2] + infectedArr[1][3] + infectedArr[0][2]);
				UpdateStateToMaxDirection(left, right, down, up);
			}
		} else {// �ѹ��������� ������ ������ �����ױ�. ���帹���� �ֺ� �������µ� ����.
				// �׵θ��� �ȶ������Ƿ� ����߰���ü ���� ���������� �̵� O.K
			int left, right, down, up;
			int[][] infectedArr = GetAdjacentInfectedtable();
			left = (infectedArr[1][1] + infectedArr[2][0] + infectedArr[2][1] + infectedArr[3][1]);
			right = (infectedArr[1][3] + infectedArr[2][3] + infectedArr[2][4] + infectedArr[3][3]);
			down = (infectedArr[3][1] + infectedArr[3][2] + infectedArr[3][3] + infectedArr[4][2]);
			up = (infectedArr[1][1] + infectedArr[1][2] + infectedArr[1][3] + infectedArr[0][2]);
			UpdateStateToMaxDirection(left, right, down, up);
		}

	}

	private void UpdateInfectedState() {
		/*
		 * if(this.turnInfo.turnNumber<33){ infMode++; // 30�� ����Ƽ�� ������ �������� ��������
		 * ��Ű��. ������� �׳� ����ü�������� �Ⱦ�. }
		 */
		if (infMode == 1) {
			// �������� ��ȭ�⵵
			if (myInfo.position.row != 0 && myInfo.position.row != 12 && myInfo.position.column != 0
					&& myInfo.position.column != 12) {
				if (cells[myInfo.position.row][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
						&& cells[myInfo.position.row - 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) { // ��ü
																												// ����,
																												// 4��
																												// 1����
																												// �̳�
					state = Stay;
					return;
				} else {
					int left, up, right, down, stay;
					left = -(cells[myInfo.position.row][myInfo.position.column - 1]
							.CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor));
					right = -(cells[myInfo.position.row][myInfo.position.column + 1]
							.CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor));
					down = -(cells[myInfo.position.row + 1][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor));
					up = -(cells[myInfo.position.row - 1][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row - 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor));
					stay = -(cells[myInfo.position.row - 1][myInfo.position.column - 1]
							.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
									.CountIf_Players(player -> player.state == StateCode.Survivor));
					UpdateStateToMaxDirection(left, right, down, up, stay);

				}
			} else {
				// ���� �پ��ִ»�Ȳ���� ���� ��ȭ�⵵
				// *********************************
				// �𼭸� �׳� ������
				if ((myInfo.position.row == 0 && myInfo.position.column == 0)
						|| (myInfo.position.row == 0 && myInfo.position.column == 12)
						|| (myInfo.position.row == 12 && myInfo.position.column == 12)
						|| (myInfo.position.row == 12 && myInfo.position.column == 0)) {
					state = Stay;
					return;
				}
				// �׳� ���� �پ��ִ»�Ȳ
				else if (myInfo.position.row == 0) {
					if (cells[myInfo.position.row][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
							&& cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column - 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column + 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) { // �ش��ڸ�
																													// ��ü����
																													// �ֺ�
																													// 1����
																													// ����
																													// ������
						state = Stay;
						return;
					} else { // õ�忡�� �𼭸� �ƴѵ� ��ü �ְų� �ֺ��� 2�����̻�..
						int left, up, right, down;
						left = cells[myInfo.position.row][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						right = cells[myInfo.position.row][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						down = cells[myInfo.position.row + 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						up = 100;
						UpdateStateToMinDirection(left, right, down, up);
						return;

					}
				} else if (myInfo.position.row == 12) {
					if (cells[myInfo.position.row][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
							&& cells[myInfo.position.row - 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column - 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column + 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) {
						state = Stay;
						return;
					} else {
						int left, up, right, down;
						left = cells[myInfo.position.row][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);

						right = cells[myInfo.position.row][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);

						down = 100;
						up = cells[myInfo.position.row - 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row - 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						UpdateStateToMinDirection(left, right, down, up);
						return;
					}
				} else if (myInfo.position.column == 12) {
					if (cells[myInfo.position.row][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
							&& cells[myInfo.position.row - 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column - 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row + 1][myInfo.position.column]
											.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) {
						state = Stay;
						return;
					} else {
						int left, up, right, down;
						left = cells[myInfo.position.row][myInfo.position.column - 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						right = 100;
						down = cells[myInfo.position.row + 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row + 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						up = cells[myInfo.position.row - 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column - 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row - 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						UpdateStateToMinDirection(left, right, down, up);
						return;
					}
				} else if (myInfo.position.column == 0) {
					if (cells[myInfo.position.row][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
							&& cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row - 1][myInfo.position.column]
											.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column + 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) {
						state = Stay;
						return;
					} else {
						int left, up, right, down;
						left = 100;
						right = cells[myInfo.position.row][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						down = cells[myInfo.position.row + 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row + 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row + 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						up = cells[myInfo.position.row - 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Corpse)
								+ cells[myInfo.position.row - 1][myInfo.position.column]
										.CountIf_Players(player -> player.state == StateCode.Survivor)
								+ cells[myInfo.position.row - 1][myInfo.position.column + 1]
										.CountIf_Players(player -> player.state == StateCode.Survivor);
						UpdateStateToMinDirection(left, right, down, up);
						return;
					}
				}
			}
		}

		else {
			// 40ȸ ��ä��� �׾ ���ٲ�.
			// �ִ��� �������̰� ���� ��ü�Ա�.
			// ************************************************************8

			int[][] survivorArr = GetAdjacentSurvivortable();
			int[][] corpseArr = GetAdjacentCorpsetable();
			int left, up, right, down, stay;
			int leftsum, rightsum, upsum, downsum;
			leftsum = rightsum = upsum = downsum = 0;
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 2; j++) {
					leftsum += survivorArr[i][j];
				}
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 3; j < 5; j++) {
					rightsum += survivorArr[i][j];
				}
			}
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					upsum += survivorArr[i][j];
				}
			}
			for (int i = 3; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					downsum += survivorArr[i][j];
				}
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 2; j++) {
					leftsum += corpseArr[i][j];
				}
			}
			for (int i = 0; i < 5; i++) {
				for (int j = 3; j < 5; j++) {
					rightsum += corpseArr[i][j];
				}
			}
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					upsum += corpseArr[i][j];
				}
			}
			for (int i = 3; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					downsum += corpseArr[i][j];
				}
			}
			left = survivorArr[2][0] * 2 + survivorArr[3][1] * 2 + survivorArr[1][1] * 2 + leftsum;
			right = survivorArr[2][4] * 2 + survivorArr[3][3] * 2 + survivorArr[1][3] * 2 + rightsum;
			up = survivorArr[0][2] * 2 + survivorArr[1][1] * 2 + survivorArr[1][3] * 2 + upsum;
			down = survivorArr[4][2] * 2 + survivorArr[3][1] * 2 + survivorArr[3][3] * 2 + downsum;
			stay = survivorArr[2][1] * 3 + survivorArr[2][3] * 3 + survivorArr[1][2] * 3 + survivorArr[3][2] * 3
					+ corpseArr[2][2] * 3;
			UpdateStateToMaxDirection(left, right, down, up, stay);
			return;
		}
	}

	private Point resultPointToInfected(int[][] arr) {
		Point result = new Point(0,0);
		int max = 0;
		for (int i = 1; i < 12; i++) {
			for (int j = 1; j < 12; j++) {
				if (arr[i][j] > max) {
					max = arr[i][j];
					result = new Point(i, j);
				}
			}
		}
		if(cells[result.row][result.column].CountIf_Players(a->a.state==StateCode.Infected)==0){
			arr[result.row][result.column]=0;
			result=resultPointToInfected(arr);
			return result;
		}
		else{
			return result;
		}
	}
	
	private Point resultPointToForSurvivor(int[][] arr) {
		Point result = new Point(0,0);
		int min = 1000;
		for (int i = 1; i < 12; i++) {
			for (int j = 1; j < 12; j++) {
				if (arr[i][j] < min) {
					min = arr[i][j];
					result = new Point(i, j);
				}
			}
		}
		if(cells[result.row][result.column].CountIf_Players(a->a.state==StateCode.Infected)!=0||cells[result.row][result.column].CountIf_Players(a->a.state==StateCode.Corpse)!=0){
			arr[result.row][result.column]=1000;
			result=resultPointToForSurvivor(arr);
			return result;
		}
		else{
			return result;
		}
	}
		

	public DirectionCode Survivor_Move() {
		DirectionCode result = null;

		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		UpdateSurvivorState();

		switch (state) {
		case GoRight:
			result = DirectionCode.Right;
			break;
		case GoDown:
			result = DirectionCode.Down;
			break;
		case GoLeft:
			result = DirectionCode.Left;
			break;
		case GoUp:
			result = DirectionCode.Up;
			break;
		case Stay:
			result = DirectionCode.Stay; // ��ȭ�⵵
		default:
			break;
		}

		return result;
	}

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {

		DirectionCode result = null;
		UpdateInfectedState();
		switch (state) {
		case GoRight:
			result = DirectionCode.Right;

			break;
		case GoDown:
			result = DirectionCode.Down;

			break;
		case GoLeft:
			result = DirectionCode.Left;

			break;
		case GoUp:
			result = DirectionCode.Up;

			break;
		case Stay:
			result = DirectionCode.Stay;

		default:
			break;
		}

		return result;

		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			infMode++; // ����ü �ʱ� 1�����
			surMode++; // ������ �ʱ� 1�����
			/*
			 * ° Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������
			 * 0�Ͽ��� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
		}
	}

	@Override
	public Point Soul_Spawn() {
		if(turnInfo.turnNumber==0){
			return new Point(6,6);
		}
		if (this.myScore.survivor_max >= 35) {
			surMode++; // �ʱ� 1�� ��� , �װ��� 35���̻� ���� ���������� �����ױ� ���
		}
		if (this.turnInfo.turnNumber >= 30){
			surMode++;                    
		}
		
		//surMode++;
		if (surMode != 1) {
			int[][] count = new int[13][13];
			for (int i = 2; i < 11; i++) {
				for (int j = 2; j < 11; j++) {
					count[i][j] = cells[i - 2][j].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i - 1][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i - 1][j].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j + 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j - 2].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j + 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i][j + 2].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i + 1][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i + 1][j].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i + 1][j + 1].CountIf_Players(player -> player.state == StateCode.Infected)
							+ cells[i + 2][j].CountIf_Players(player -> player.state == StateCode.Infected);
				}
			}
			for (int i = 0; i < 13; i++) {
				count[0][i] = 0;
				count[12][i] = 0;
				count[i][0] = 0;
				count[i][12] = 0;
			}
			count[1][1] = cells[0][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[0][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[0][2].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][2].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][3].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][2].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[3][1].CountIf_Players(player -> player.state == StateCode.Infected);
			count[1][11] = cells[0][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[0][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[0][12].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][9].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[1][12].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[2][12].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[3][11].CountIf_Players(player -> player.state == StateCode.Infected);
			count[11][1] = cells[9][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][2].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][2].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][3].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][0].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][1].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][2].CountIf_Players(player -> player.state == StateCode.Infected);
			count[11][11] = cells[9][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[10][12].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][9].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[11][12].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][10].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][11].CountIf_Players(player -> player.state == StateCode.Infected)
					+ cells[12][12].CountIf_Players(player -> player.state == StateCode.Infected);
			for (int i = 2; i < 11; i++) {
				count[1][i] =

						cells[0][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[0][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[0][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[1][i - 2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[1][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[1][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[1][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[1][i + 2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[2][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[2][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[2][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[3][i].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			for (int i = 2; i < 11; i++) {
				count[11][i] =

						cells[10][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[10][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[10][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[11][i - 2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[11][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[11][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[11][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[11][i + 2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[12][i - 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[12][i].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[12][i + 1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[9][i].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			for (int i = 2; i < 11; i++) {
				count[i][1] =

						cells[i - 2][1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][0].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][0].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][3].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 1][0].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 1][1].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 1][2].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 2][1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			for (int i = 2; i < 11; i++) {
				count[i][11] =

						cells[i - 2][11].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][10].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][11].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i - 1][12].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][9].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][10].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][11].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i][12].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 1][11].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 1][12].CountIf_Players(player -> player.state == StateCode.Infected)
								+ cells[i + 2][11].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			return resultPointToInfected(count);
		} else {
			//count ���  ������.  ���� �����Ѱ� ����   �ֺ� 3ĭ ��ü ����ü ���� ������.
			int[][] count ={{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000}};
			for (int i = 3; i <= 9; i++) {
				for (int j = 3; j <= 9; j++) {
					count[i][j] = cells[i - 3][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i - 2][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i - 2][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-2][j + 1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-1][j - 2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-1][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-1][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-1][j + 1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i-1][j + 2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i ][j - 3].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i ][j-2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i ][j - 1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i][j+1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i][j+2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i][j+3].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+1][j-2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+1][j-1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+1][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+1][j+1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+1][j+2].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+2][j-1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+2][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+2][j+1].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+ cells[i+3][j].CountIf_Players(player -> player.state == StateCode.Infected)*2
							+cells[i - 3][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i - 2][j - 1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i - 2][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-2][j + 1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-1][j - 2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-1][j - 1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-1][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-1][j + 1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i-1][j + 2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i ][j - 3].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i ][j-2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i ][j - 1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i][j+1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i][j+2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i][j+3].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+1][j-2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+1][j-1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+1][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+1][j+1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+1][j+2].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+2][j-1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+2][j].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+2][j+1].CountIf_Players(player -> player.state == StateCode.Corpse)
							+ cells[i+3][j].CountIf_Players(player -> player.state == StateCode.Corpse);
				}
			}
			
			
			
			return resultPointToForSurvivor(count);         // ����ū������ ���� ���� ����Ʈ ��ȯ
		}
	}
}
