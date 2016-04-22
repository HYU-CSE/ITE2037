package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

public class Player_Jaeseok extends Player {

	public Player_Jaeseok(int ID) {
		super(ID, "급식전사");
		this.trigger_acceptDirectInfection = false;
	}

	final int GoRight = 2;
	final int GoLeft = 0;
	final int GoUp = 1;
	final int GoDown = 3;
	boolean isDeadBefore;

	private boolean[] DeleteDecision() {
		boolean[] directSet = { true, true, true, true };
		if (IsLeftCorner())
			directSet[0] = false;
		if (IsRightCorner())
			directSet[2] = false;
		if (IsUpCorner())
			directSet[1] = false;
		if (IsDownCorner())
			directSet[3] = false;
		return directSet;
	}

	private boolean IsLeftCorner() {
		boolean result = false;

		if (this.myInfo.position.column == 0) {
			result = true;
		}

		return result;
	}

	private boolean IsRightCorner() {
		boolean result = false;

		if (this.myInfo.position.column == Constants.Classroom_Width - 1) {
			result = true;
		}

		return result;
	}

	private boolean IsUpCorner() {
		boolean result = false;

		if (this.myInfo.position.row == 0) {
			result = true;
		}

		return result;
	}

	private boolean IsDownCorner() {
		boolean result = false;

		if (this.myInfo.position.row == Constants.Classroom_Height - 1) {
			result = true;
		}

		return result;
	}

	private int countPlayers(int row, int col) {
		if (row < 0 || row >= Constants.Classroom_Height || col < 0 || col >= Constants.Classroom_Width) {
			return -1;
		}
		return cells[row][col].Count_Players();
	}

	private int countPlayers(int row, int col, StateCode state) {
		if (row < 0 || row >= Constants.Classroom_Height || col < 0 || col >= Constants.Classroom_Width)
			return -1;
		return cells[row][col].CountIf_Players(player -> player.state == state);
	}

	private DirectionCode FindSurviorDirection(boolean[] directSet) {
		DirectionCode result = null;

		int myRow = this.myInfo.position.row;
		int myCol = this.myInfo.position.column;

		int[] getRandom = { 1000, 1000, 1000, 1000 };

		// 왼쪽부터 시계방향순으로 체크해서 가능한 곳으로 ㄱㄱ
		// 각 방향별로 안됨 = -1, 보류 = 0, 고 = 1

		// 왼쪽
		if (directSet[0]) {
			int leftIsOk = 0;

			// 다 비어있으면 OK
			if (countPlayers(myRow, myCol - 1, StateCode.Infected) == 0
					&& countPlayers(myRow, myCol - 2, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol - 1, StateCode.Infected) == 0
					&& countPlayers(myRow + 1, myCol - 1, StateCode.Infected) == 0) {
				return DirectionCode.Left;
			} else if (countPlayers(myRow, myCol - 1, StateCode.Infected) >= 1
					&& countPlayers(myRow, myCol - 1, StateCode.Corpse) >= 1) {
				leftIsOk = -1;
			} else if (countPlayers(myRow, myCol - 2, StateCode.Infected) > 0
					&& (countPlayers(myRow - 1, myCol - 1, StateCode.Infected) > 0
							|| countPlayers(myRow + 1, myCol - 1, StateCode.Infected) > 0)) {
				leftIsOk = -1;
			} else if (countPlayers(myRow, myCol - 1, StateCode.Infected) > 0
					&& countPlayers(myRow, myCol - 2, StateCode.Infected) > 0) {
				leftIsOk = -1;
			} else if (countPlayers(myRow, myCol - 2, StateCode.Infected) > 0) {
				leftIsOk = -1;
			} else if (countPlayers(myRow - 1, myCol - 1, StateCode.Infected) > 0
					&& countPlayers(myRow + 1, myCol - 1, StateCode.Infected) > 0) {
				leftIsOk = 0;
			} else
				return DirectionCode.Left;

			if (leftIsOk == 0) {
				getRandom[0] = countPlayers(myRow, myCol - 1, StateCode.Infected)
						+ countPlayers(myRow, myCol - 2, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol - 1, StateCode.Infected)
						+ countPlayers(myRow + 1, myCol - 1, StateCode.Infected);
			}
		}
		// 위
		if (directSet[1]) {
			int upIsOk = 0;

			// 다 비어있으면 OK
			if (countPlayers(myRow - 1, myCol, StateCode.Infected) == 0
					&& countPlayers(myRow - 2, myCol, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol - 1, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol + 1, StateCode.Infected) == 0) {
				return DirectionCode.Up;
			} else if (countPlayers(myRow - 1, myCol, StateCode.Infected) >= 1
					&& countPlayers(myRow - 1, myCol, StateCode.Corpse) >= 1) {
				upIsOk = -1;
			} else if (countPlayers(myRow - 2, myCol, StateCode.Infected) > 0
					&& (countPlayers(myRow - 1, myCol - 1, StateCode.Infected) > 0
							|| countPlayers(myRow - 1, myCol + 1, StateCode.Infected) > 0)) {
				upIsOk = -1;
			} else if (countPlayers(myRow - 1, myCol, StateCode.Infected) > 0
					&& countPlayers(myRow - 2, myCol, StateCode.Infected) > 0) {
				upIsOk = -1;
			} else if (countPlayers(myRow - 2, myCol, StateCode.Infected) > 0) {
				upIsOk = -1;
			} else if (countPlayers(myRow - 1, myCol - 1, StateCode.Infected) > 0
					&& countPlayers(myRow - 1, myCol + 1, StateCode.Infected) > 0) {
				upIsOk = 0;
			} else
				return DirectionCode.Up;

			if (upIsOk == 0) {
				getRandom[1] = countPlayers(myRow - 1, myCol, StateCode.Infected)
						+ countPlayers(myRow - 2, myCol, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol - 1, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol + 1, StateCode.Infected);
			}
		}
		// 오른쪽
		if (directSet[2]) {
			int rightIsOk = 0;

			// 다 비어있으면 OK
			if (countPlayers(myRow, myCol + 1, StateCode.Infected) == 0
					&& countPlayers(myRow, myCol + 2, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol + 1, StateCode.Infected) == 0
					&& countPlayers(myRow + 1, myCol + 1, StateCode.Infected) == 0) {
				return DirectionCode.Right;
			} else if (countPlayers(myRow, myCol + 1, StateCode.Infected) >= 1
					&& countPlayers(myRow, myCol + 1, StateCode.Corpse) >= 1) {
				rightIsOk = -1;
			} else if (countPlayers(myRow, myCol + 2, StateCode.Infected) > 0
					&& (countPlayers(myRow - 1, myCol + 1, StateCode.Infected) > 0
							|| countPlayers(myRow + 1, myCol + 1, StateCode.Infected) > 0)) {
				rightIsOk = -1;
			} else if (countPlayers(myRow, myCol + 1, StateCode.Infected) > 0
					&& countPlayers(myRow, myCol + 2, StateCode.Infected) > 0) {
				rightIsOk = -1;
			} else if (countPlayers(myRow, myCol + 2, StateCode.Infected) > 0) {
				rightIsOk = -1;
			} else if (countPlayers(myRow - 1, myCol + 1, StateCode.Infected) > 0
					&& countPlayers(myRow + 1, myCol + 1, StateCode.Infected) > 0) {
				rightIsOk = 0;
			} else
				return DirectionCode.Right;

			if (rightIsOk == 0) {
				getRandom[2] = countPlayers(myRow, myCol + 1, StateCode.Infected)
						+ countPlayers(myRow, myCol + 2, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol + 1, StateCode.Infected)
						+ countPlayers(myRow + 1, myCol + 1, StateCode.Infected);
			}
		}
		// 아래
		if (directSet[3]) {
			int downIsOk = 0;

			// 다 비어있으면 OK
			if (countPlayers(myRow + 1, myCol, StateCode.Infected) == 0
					&& countPlayers(myRow - 2, myCol, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol - 1, StateCode.Infected) == 0
					&& countPlayers(myRow - 1, myCol + 1, StateCode.Infected) == 0) {
				return DirectionCode.Down;
			} else if (countPlayers(myRow + 1, myCol, StateCode.Infected) >= 1
					&& countPlayers(myRow + 1, myCol, StateCode.Corpse) >= 1) {
				downIsOk = -1;
			} else if (countPlayers(myRow + 2, myCol, StateCode.Infected) > 0
					&& (countPlayers(myRow + 1, myCol - 1, StateCode.Infected) > 0
							|| countPlayers(myRow + 1, myCol + 1, StateCode.Infected) > 0)) {
				downIsOk = -1;
			} else if (countPlayers(myRow + 1, myCol, StateCode.Infected) > 0
					&& countPlayers(myRow + 2, myCol, StateCode.Infected) > 0) {
				downIsOk = -1;
			} else if (countPlayers(myRow + 2, myCol, StateCode.Infected) > 0) {
				downIsOk = -1;
			} else if (countPlayers(myRow + 1, myCol - 1, StateCode.Infected) > 0
					&& countPlayers(myRow + 1, myCol + 1, StateCode.Infected) > 0) {
				downIsOk = 0;
			} else
				return DirectionCode.Down;

			if (downIsOk == 0) {
				getRandom[3] = countPlayers(myRow + 1, myCol, StateCode.Infected)
						+ countPlayers(myRow - 2, myCol, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol - 1, StateCode.Infected)
						+ countPlayers(myRow - 1, myCol + 1, StateCode.Infected);
			}
		}

		int temp = 1000;
		int minDirection = -1;
		for (int i = 0; i < 3; ++i) {
			if (temp > getRandom[i]) {
				temp = getRandom[i];
				minDirection = i;
			}
		}
		if (minDirection != -1) {
			switch (minDirection) {
			case 0:
				return DirectionCode.Left;
			case 1:
				return DirectionCode.Up;
			case 2:
				return DirectionCode.Right;
			case 3:
				return DirectionCode.Down;
			}
		}
		return result;
	}

	public DirectionCode Survivor_Move() {
		DirectionCode result = null;

		/*
		 * 0 ~ 3까지 순서 왼쪽 위 오른쪽 아래 :: 시계방향 코너에 있을 경우의 선택지를 제거
		 */
		boolean[] directSet = DeleteDecision();

		if (turnInfo.turnNumber <= 60 && !isDeadBefore) {
			/* 도망치기모드! */
			if (FindSurviorDirection(directSet) != null) {
				return FindSurviorDirection(directSet);
			} else {
				int state = 0;
				while (true) {
					if (directSet[state]) {
						// 최후의 방향결정
						switch (state) {
						case GoRight:
							return DirectionCode.Right;
						case GoUp:
							return DirectionCode.Up;
						case GoLeft:
							return DirectionCode.Left;
						case GoDown:
							return DirectionCode.Down;
						}
					}
					state++;
				}
			}
		}

		// 살만큼 살았다 더 살려면 살고 죽을라면 죽어라
		else {
			int state = 0;
			while (true) {
				if (directSet[state]) {
					// 최후의 방향결정
					switch (state) {
					case GoRight:
						return DirectionCode.Right;
					case GoUp:
						return DirectionCode.Up;
					case GoLeft:
						return DirectionCode.Left;
					case GoDown:
						return DirectionCode.Down;
					}
				}
				state++;
			}
		}
	}

	public void Corpse_Stay() {
	}

	// 어차피 죽은 위치를 선택하는데 완벽했다 맘대로 움직여라!
	// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	public DirectionCode Infected_Move() {
		DirectionCode result = null;

		int myRow = this.myInfo.position.row;
		int myCol = this.myInfo.position.column;

		/*
		 * 0 ~ 3까지 순서 왼쪽 위 오른쪽 아래 :: 시계방향 코너에 있을 경우의 선택지를 제거
		 */
		boolean[] directSet = DeleteDecision();

		if (this.turnInfo.turnNumber > 70) {
			int state = 0;
			while (true) {
				if (directSet[state]) {
					// 최후의 방향결정
					switch (state) {
					case GoRight:
						return DirectionCode.Right;
					case GoUp:
						return DirectionCode.Up;
					case GoLeft:
						return DirectionCode.Left;
					case GoDown:
						return DirectionCode.Down;
					}
				}
				state++;
			}
		} else
			return DirectionCode.Stay;
	}

	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			isDeadBefore = false;
		}
		if (turnInfo.turnNumber < 30) {
			isDeadBefore = true;
		}
	}

	public Point Soul_Spawn() {
		int result_row = 6;
		int result_column = 6;
		int max = 0;

		// 감염자와 시체가 둘다 존재하는 곳 + 가장 사람이 많은 곳
		for (int i = 0; i < 12; ++i) {
			for (int j = 0; j < 12; ++j) {
				if (countPlayers(i, j, StateCode.Infected) >= 1 || countPlayers(i, j, StateCode.Corpse) >= 1) {
					if (max < countPlayers(i, j)) {
						result_row = i;
						result_column = j;
						max = countPlayers(i, j);
					}
				}
			}
		}
		return new Point(result_row, result_column);
	}
}
