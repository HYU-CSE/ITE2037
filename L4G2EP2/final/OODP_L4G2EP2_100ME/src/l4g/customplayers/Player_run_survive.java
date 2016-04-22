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
 * 직접 감염: 항상 거절합니다.
 * 
 * 생존자 이동:1. 벽에 붙는 경우 게임판 밖으로 나가지 않도록 벽에서 떨어집니다
 * 2. 사방의 감염체의 유무를 확인해 감염체가 없는 곳으로 이동합니다
 * 3. 대각선 방향과 두칸 떨어진 곳의 감염체의 유무를 확인해 피합니다
 * 4. else 생존자 수가 가장 많은 방향으로 이동합니다.(Scout참고) 생존자의 시야 범위가 0 123 45678 9AB C ..일 때 위: 0123에
 * 있는 생존자 수 왼쪽: 1459에 있는 생존자 수 오른쪽: 378B에 있는 생존자 수 아래: 9ABC에 있는 생존자 수 ..를 합산하여
 * 비교합니다.
 * 
 * 감염체 이동: 1. 현재 칸에 시체가 있다면 가만히 서있습니다
 * 2.옆  칸에 시체가 있다면 옆 칸으로 이동합니다. 그렇지 않은 경우 정화 기도를 드립니다.
 * 
 * 영혼 배치: 게임판을 탐색해  3 X 3 칸 안에 감염체가 없는곳에서 부활합니다.
 * 최적화된 장소를 못찾으면 기본 부활장소에서 부활
 * 
 * @author Racin
 *
 */
public class Player_run_survive extends Player {
	/*
	 * -------------------------------------------------- TODO 꼭 읽어 보세요!
	 * 
	 * 모든 Bot 플레이어들은 서로 다른 의사 결정을 수행하기 위해 자신의 ID와 게임 번호를 이용하여 임의의 '방향 우선순위'와
	 * '선호하는 칸'을 정해 의사 결정에 활용합니다. 만약 여러분이 Bot 플레이어의 코드를 가져와 사용하려는 경우 다음 작업들을 꼭
	 * 함께 수행해야 합니다.
	 * 
	 * - 아래에 '이 field, 메서드는 반드시 필요합니다'라 적혀 있는 것들을 모두 복붙해 와야 합니다.
	 * 
	 * - Soul_Stay()에 있는, 초기화 관련 코드를 복붙해 와야 합니다.
	 */

	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다. 이 field는 반드시 필요합니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	/**
	 * '선호하는 칸'을 기록해 두는 field입니다. 이 field는 반드시 필요합니다.
	 */
	Point favoritePoint = new Point(0, 0);

	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다. 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다. 이 메서드는 반드시
	 * 필요합니다.
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

	public Player_run_survive(int ID) {
		super(ID, "연습클래스" + ID);

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
	}/// 생존자로 많이 살아남고, 주위 플레이어들에게 정보를 주는것이 목적입니다. 직접 감염을 절대 받지 않습니다

	@Override
	public DirectionCode Survivor_Move() {
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		// 플레이어의 현재 위치를 반환
		
		int UP = 0, DP = 0, RP = 0, LP = 0; // 기본값은영 U D R L는 방향 P는 Predator의 약자
		int UUP = 0, URP = 0, RRP = 0, DRP = 0, DDP = 0, DLP = 0, LLP = 0, ULP = 0; 
		/// U위 L왼쪽 R오른쪽 D아래
		/// 대각선방향과 두칸 떨어진곳에 감염체가 있는지 확인
		////if 조건 이후 감염체들의 개수를 세는 이유 =>> 게임판 밖의 범위를 참조하는 일이 없도록 하려고
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
		/// 플레이어의 사방에 감염체 수를 구함 , 배열범위를 벗어나지않도록 조심!!
		if (row == 0) {
			return DirectionCode.Down;
		} else if (row == Constants.Classroom_Height - 1) {
			return DirectionCode.Up;
		} else if (column == Constants.Classroom_Width - 1) {
			return DirectionCode.Left;
		} else if (column == 0) {
			return DirectionCode.Right;
		} //// 플레이어가 밖으로 나가지 않게 벽에 붙게되면 떨어지게함
		else if (UP > 0 && DP > 0 && RP > 0) { //// 감염체 존재 방향에 따른 이동 위의 코드일수록
												//// 우선순위 아래로 갈수록 우선순위가 낮은 이동
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
		} else {/*///여기서부터 나머지 케이스에서는  bot의 Scout의 이동을 참조한다
				 * 생존자 이동: 생존자 수가 가장 많은 방향으로 이동합니다. 생존자의 시야 범위가 0 123 45678 9AB
				 * C ..일 때 위: 0123에 있는 생존자 수 왼쪽: 1459에 있는 생존자 수 오른쪽: 378B에 있는
				 * 생존자 수 아래: 9ABC에 있는 생존자 수 ..를 합산하여 비교합니다.
				 */
			int[] numberOfPlayers = new int[13];

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
			int[] weights = new int[4];

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
		// 현재 칸에 시체가 있다면 무조건 대기
		if (this.cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
			return DirectionCode.Stay;
		// 위,아래,오른쪽,왼쪽 순으로 시체가 있는지 탐색 있다면 이동
		// 배열범위를 벗어나지않는지 확인 필수
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

		// 그렇지 않으면 정화 기도 시도
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// 이 부분은 Bot 플레이어 코드를 복붙해 사용하기 위해 반드시 필요합니다.
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn() {
		int result_row = 7;
		int result_column = 6; /// 부활 장소 기본값

		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		/// 전 영역을 1,1부터 탐색하여 주변 3X3 영역에 감영체가 없는곳에서 부활
		/// 1,1 부터 부활장소를 탐색

		OUT: for (int i = 1; i < 12; i++) {
			for (int j = 1; j < 12; j++) {
				boolean good_point = true; /// 부활하기 좋은 장소이다 가정하고 반복문 시작
				IN: for (int k = -1; k <= 1; k++) {
					for (int l = -1; l <= 1; l++) {
						if (cells[i + k][j + l].CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
							good_point = false; //// 탐색하는 자리에 감염체가있으면 false로 바꾸고
												//// 반복문 탈출
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
		/// 선택된 부활장소에서 부활 만약 최적의장소를 못찾았다면 기본값에서 부활
		return new Point(result_row, result_column);
	}

}
