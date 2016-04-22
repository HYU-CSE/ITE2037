package l4g.customplayers;
// 그냥 한번 못살면 시체 폭탄  ㄱㄱ

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 *
 */
public class Player_2012004270 extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_2012004270(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "201200XX70");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.
	}

	/*
	 * TODO#5 이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요. 당연히 한 방에 될 리
	 * 없으니, 중간중간 코드를 백업해 두는 것이 좋으며, 코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다. 그러니
	 * 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면, '코드에 노력이란게 묻어 날
	 * 수도 있구나'라는 생각이 절로 들게 될 것입니다.
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
			// 가장 적게올수 있는 셀로 이동;
		}

		if (this.turnInfo.turnNumber >= 70&&myScore.corpse_max==0)
			surMode++; // 60턴 계속 살아있었다면 빨리죽기모드로 변경

		if (surMode == 1) { // 죽기전 12턴부터의 살아남기 행동 업데이트 필요**
							// 정찰점수 얻으면서 생존
			// 생존 해당방향에 시체있는데 주변에 감염체있으면 가중치
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
			// 테두리 두줄;
			else {
				int left, right, down, up;
				int[][] infectedArr = GetAdjacentInfectedtable();
				left = -(infectedArr[1][1] + infectedArr[2][0] + infectedArr[2][1] + infectedArr[3][1]);
				right = -(infectedArr[1][3] + infectedArr[2][3] + infectedArr[2][4] + infectedArr[3][3]);
				down = -(infectedArr[3][1] + infectedArr[3][2] + infectedArr[3][3] + infectedArr[4][2]);
				up = -(infectedArr[1][1] + infectedArr[1][2] + infectedArr[1][3] + infectedArr[0][2]);
				UpdateStateToMaxDirection(left, right, down, up);
			}
		} else {// 한번죽은후의 생존자 움직임 빨리죽기. 가장많은곳 주변 떨어졌는데 산경우.
				// 테두리는 안떨어지므로 사방중감염체 가장 많은곳으로 이동 O.K
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
		 * if(this.turnInfo.turnNumber<33){ infMode++; // 30턴 못버티고 죽으면 작전변경 감염많이
		 * 시키기. 작전취소 그냥 감염체점수버림 안씀. }
		 */
		if (infMode == 1) {
			// 적당지역 정화기도
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
										.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) { // 시체
																												// 없고,
																												// 4방
																												// 1마리
																												// 이내
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
				// 벽에 붙어있는상황에서 빨리 정화기도
				// *********************************
				// 모서리 그냥 스테이
				if ((myInfo.position.row == 0 && myInfo.position.column == 0)
						|| (myInfo.position.row == 0 && myInfo.position.column == 12)
						|| (myInfo.position.row == 12 && myInfo.position.column == 12)
						|| (myInfo.position.row == 12 && myInfo.position.column == 0)) {
					state = Stay;
					return;
				}
				// 그냥 벽에 붙어있는상황
				else if (myInfo.position.row == 0) {
					if (cells[myInfo.position.row][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Corpse) == 0
							&& cells[myInfo.position.row + 1][myInfo.position.column]
									.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column - 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor)
									+ cells[myInfo.position.row][myInfo.position.column + 1]
											.CountIf_Players(player -> player.state == StateCode.Survivor) <= 1) { // 해당자리
																													// 시체없고
																													// 주변
																													// 1마리
																													// 이하
																													// 스테이
						state = Stay;
						return;
					} else { // 천장에서 모서리 아닌데 시체 있거나 주변에 2마리이상..
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
			// 40회 못채우고 죽어서 모드바뀜.
			// 최대한 많이죽이고 많은 시체먹기.
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

		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
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
			result = DirectionCode.Stay; // 정화기도
		default:
			break;
		}

		return result;
	}

	@Override
	public void Corpse_Stay() {
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
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

		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			infMode++; // 감염체 초기 1번모드
			surMode++; // 생존자 초기 1번모드
			/*
			 * 째 Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은
			 * 0턴에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
		}
	}

	@Override
	public Point Soul_Spawn() {
		if(turnInfo.turnNumber==0){
			return new Point(6,6);
		}
		if (this.myScore.survivor_max >= 35) {
			surMode++; // 초기 1번 모드 , 죽고나서 35턴이상 생존 성공했으면 빨리죽기 모드
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
			//count 어레이  값설정.  가장 안전한곳 지수   주변 3칸 시체 감염체 가장 적은곳.
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
			
			
			
			return resultPointToForSurvivor(count);         // 가장큰값같는 곳과 같은 포인트 반환
		}
	}
}
