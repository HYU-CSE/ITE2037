package l4g.customplayers;

import l4g.Cell;
import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_무특징이특징 extends Player {
	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다. 이 field는 반드시 필요합니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	/**
	 * '선호하는 칸'을 기록해 두는 field입니다. 이 field는 반드시 필요합니다.
	 */
	Point favoritePoint = new Point(6, 6);

	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다. 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다. 이 메서드는 반드시
	 * 필요합니다.
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
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다. 이 메서드는 반드시 필요합니다.
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

	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_무특징이특징(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "무특징이특징");

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

	/*
	 * WildCard 0번은 Loner WildCard 1번은 Survivor WildCard 2번은 Scout WildCard 3번은
	 * CorpseBomb WildCard 4번은 Predator WildCard 5번은 Suicide WildCard 6번은 Prayer
	 */

	int wildCard = 0;

	void UpdateState() {
		// 상태에 따라 작전 변경
		switch (wildCard) {
		case 0: // WildCard 0번은 Loner
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

		case 1: // WildCard 1번은 Survivor
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

		case 2: // WildCard 2번은 Scout
			if (this.turnInfo.turnNumber <= 70) {
				if (this.myInfo.state == StateCode.Survivor) {
					wildCard = 2;
					break;
				} else {
					wildCard = 6;
					break;
				}
			} else { // 70턴까지 계속 생존만 하고 있으면 안 되므로 자살
				wildCard = 5;
				break;
			}

		case 3: // WildCard 3번은 CorpseBomb
			wildCard = 4;
			break;

		case 4: // WildCard 4번은 Predator
			wildCard = 4;
			break;

		case 5: // WildCard 5번은 Suicide
			if (this.myInfo.state == StateCode.Survivor) {
				wildCard = 5;
				break;
			} else { // 자살 성공 시 Prayer 모드로 바로 진입
				wildCard = 6;
				break;
			}

		case 6: // WildCard 6번은 Prayer
			if (this.myInfo.state == StateCode.Infected) {
				wildCard = 6;
				break;
			} else { // 영혼 상태
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
		// wildCard가 0이면 Loner 모드로 움직인다.
		// wildCard가 1이면 Survivor 모드로 움직인다.
		// wildCard가 2이면 Scout 모드로 움직인다.
		// wildCard가 5일 경우에는 자살 시도를 한다.

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
		case 0: // Loner 모드로 움직임

			// 위에 보이는 13가지 경우에 대한 플레이어 수 기록

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

			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
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

			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// 위: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)

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

		case 1: // Survivor 모드로 움직임
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

			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
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

			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// 위: 0123
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2]
							+ numberOfInfecteds[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[4] + numberOfInfecteds[5]
							+ numberOfInfecteds[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[7] + numberOfInfecteds[8]
							+ numberOfInfecteds[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfInfecteds[9] + numberOfInfecteds[10] + numberOfInfecteds[11]
							+ numberOfInfecteds[12];
					break;
				}
			}

			// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)

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

		case 2: // Scout 모드로 움직임

			// 위에 보이는 13가지 경우에 대한 생존자 수 기록

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

			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
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

			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// 위: 0123
					weights1[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights1[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights1[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights1[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// 생존자 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)

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

		default: // wildCard가 5일 때
			// 자살을 해야 하므로 감염체가 많은 곳을 골라 찾아감.

			// 위에 보이는 13가지 경우에 대한 감염자 수 기록

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

			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
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

			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// 위: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// 생존자 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)

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

		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	}

	@Override
	public void Corpse_Stay() {
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// wildCard가 4인 경우 Predator 모드로 진입한다.
		// wildCard가 6인 경우 시체를 피해 기도를 올린다.
		UpdateState();
		switch (wildCard) {
		case 4:
			// 현재 칸에 시체가 있다면 무조건 대기
			if (this.cells[myInfo.position.row][myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Stay;

			else { // 현재 칸에 있는 감염자 수와 시야 내 칸들에 있는 감염자 수를 비교해서 머무를지 움직일지를 선택함
				for (int row = 0; row < Constants.Classroom_Height; row++) {
					for (int column = 0; column < Constants.Classroom_Width; column++) {
						CellInfo cell = this.cells[row][column];

						int weight = cell.CountIf_Players(player -> player.state == StateCode.Infected);
						int distance = favoritePoint.GetDistance(row, column);

						// 가장 많은 칸이 발견되면 갱신
						if (weight > max_weight) {
							max_weight = weight;
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
						else if (weight == max_weight) {
							// 거리가 더 가까우면 갱신
							if (distance < min_distance) {
								max_row = row;
								max_column = column;
								min_distance = distance;
							}
							// 거리마저 같으면 더 좋아하는 방향을 선택
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

								// 여기까지 왔다면 이제 그만 놓아 주자
							}
						}
					}
				}

				// 검색했는데 감염자가 하나도 없다면 그냥 머무르기
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

		default: // wildCard가 6인 경우
			// 내 밑에 시체가 깔려 있으면 도망감
			if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
				return GetMovableAdjacentDirection();

			// 그렇지 않으면 정화 기도 시도
			return DirectionCode.Stay;
		}

		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
			Init_Data();
		}
	}

	int[][] counts = new int[Constants.Classroom_Height][Constants.Classroom_Width];

	@Override
	public Point Soul_Spawn() {
		// wildCard가 2일 경우 생존자들이 제일 많은 곳에 떨어져서 정찰을 할 수 있도록 한다.
		// wildCard가 3이면 감염자들이 제일 많은 곳에 떨어지는 시체폭탄이 된다.
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		UpdateState();

		switch (wildCard) {
		case 0: // 시작 위치
			return new Point(6, 6);

		case 2: // 정찰을 위해 생존자 수가 가장 많은 칸을 찾음
			// 전체 칸을 검색하여 생존자 수가 가장 많은 칸을 찾음
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int weight = cell.CountIf_Players(player -> player.state == StateCode.Survivor);
					int distance = favoritePoint.GetDistance(row, column);

					// 가장 많은 칸이 발견되면 갱신
					if (weight > max_weight) {
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
					else if (weight == max_weight) {
						// 거리가 더 가까우면 갱신
						if (distance < min_distance) {
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// 거리마저 같으면 더 좋아하는 방향을 선택
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

							// 여기까지 왔다면 이제 그만 놓아 주자
						}
					}
				}
			}

			// 검색했는데 생존자가 하나도 없다면 배치 유예
			if (max_weight == 0) {
				int variableToMakeError = 0;

				variableToMakeError = variableToMakeError / variableToMakeError;
			}

			return new Point(max_row, max_column);

		default: // wildCard가 3일 때
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// 가장 많은 칸이 발견되면 갱신
					if (weight > max_weight) {
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
					else if (weight == max_weight) {
						// 거리가 더 가까우면 갱신
						if (distance < min_distance) {
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// 거리마저 같으면 더 좋아하는 방향을 선택
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
							// 여기까지 왔다면 이제 그만 놓아 주자
						}
					}
				}
			}

			// 검색했는데 시체와 감염체가 하나도 없다면 배치 유예
			if (max_weight == 0) {
				int variableToMakeError = 0;

				variableToMakeError = variableToMakeError / variableToMakeError;
			}

			return new Point(max_row, max_column);
		}

		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
	}
}
