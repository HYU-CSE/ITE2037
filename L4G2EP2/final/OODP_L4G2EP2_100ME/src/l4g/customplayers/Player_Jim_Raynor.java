package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_Jim_Raynor extends Player {
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
		/*
		 * 여러분이 작성하는 플레이어는 언제나 강의실에 단 한 명 존재하므로 사실 아래의 코드를 쓸 필요 없이
		 * 
		 * directions[0] = DirectionCode.Up; directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right; directions[3] =
		 * DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6; favoritePoint.column = 6;
		 * 
		 * ...와 같이 여러분이 좋아하는 우선 순위를 그냥 바로 할당해 써도 무방합니다. (Bot들은 똑같은 클래스의 인스턴스가 여럿
		 * 존재하므로 이런 짓을 해야 합니다).
		 */
		directions[0] = DirectionCode.Down;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Up;

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

	public Player_Jim_Raynor(int ID) {
		super(ID, "죽창 앞에선 너도한방!나도한방!#" + ID);

		this.trigger_acceptDirectInfection = false;
	}
	public void ChangeDirectionPriority(PlayerInfo myinfo){
		int pos_row = myinfo.position.row;
		int pos_col = myinfo.position.column;
		
		// 북쪽 섹터에 있는 경우
		if(0 <= pos_row && pos_row <= 3 && 3 <= pos_col && pos_col <=9){
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
		}
		// 동쪽 섹터
		if(3 <= pos_row && pos_row <= 9 && 9 <= pos_col){
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
		}
		
		// 남쪽 섹터
		if(9 <= pos_row && 3 <= pos_col && pos_col <=9){
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
		}
		// 서쪽 섹터
		if(0 <= pos_col && pos_col <= 3 && 3 <= pos_row && pos_row <=9){
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
		}
		
			
	};
	
	@Override
	public DirectionCode Survivor_Move() {
		ChangeDirectionPriority(myInfo);
		/*
		 * 생존자 이동: 생존자 수가 가장 많은 방향으로 이동합니다. 생존자의 시야 범위가 0 123 45678 9AB C ..일 때
		 * 위: 0123에 있는 생존자 수 왼쪽: 1459에 있는 생존자 수 오른쪽: 378B에 있는 생존자 수 아래: 9ABC에 있는
		 * 생존자 수 ..를 합산하여 비교합니다.
		 */
		int[] numberOfPlayers = new int[13];
		int[] numberOfInfected = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 위에 보이는 13가지 경우에 대한 생존자 수 기록

		// 0
		row -= 2;

		if (row >= 0) {
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 1, 2, 3
		++row;

		if (row >= 0) {
			if (column >= 1) {
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;

		if (column >= 1) {
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column >= 2) {
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 2) {
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1) {
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		// C
		++row;

		if (row < Constants.Classroom_Height) {
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산
		int[] survivor_weight = new int[4];
		int[] infected_weight = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
			case Up:
				// 위: 0123
				survivor_weight[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
						+ numberOfPlayers[3];
				infected_weight[iWeights] = numberOfInfected[0] + numberOfInfected[1] + numberOfInfected[2]
						+ numberOfInfected[3];
				break;
			case Left:
				// 왼쪽: 1459
				survivor_weight[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
						+ numberOfPlayers[9];
				infected_weight[iWeights] = numberOfInfected[1] + numberOfInfected[4] + numberOfInfected[5]
						+ numberOfInfected[9];
				break;
			case Right:
				// 오른쪽: 378B
				survivor_weight[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
						+ numberOfPlayers[11];
				infected_weight[iWeights] = numberOfInfected[3] + numberOfInfected[7] + numberOfInfected[8]
						+ numberOfInfected[11];
				break;
			default:
				// 아래: 9ABC
				survivor_weight[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
				infected_weight[iWeights] = numberOfInfected[9] + numberOfInfected[10] + numberOfInfected[11]
						+ numberOfInfected[12];
				break;
			}

		}

		// 생존자 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
		// 감염체가 없는 방향인 경우 최우선적으로 선택
		int max_weight = -1;
		int max_idx_weights = 0;

		for (int iNoInfected = 0; iNoInfected < 4; iNoInfected++) {
			if (this.turnInfo.turnNumber <= 10)
				break;
			if (infected_weight[iNoInfected] == 0) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iNoInfected]);
				
				// 감염체가 아예 없는 섹터가 발견될 경우 그곳으로 이동
				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
					return directions[iNoInfected];
				}
			}
		}

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			if (survivor_weight[iWeights] > max_weight) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
					max_weight = survivor_weight[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}

		return directions[max_idx_weights];
	}

	@Override
	public void Corpse_Stay() {

	}

	@Override
	public DirectionCode Infected_Move() {
		// 내 밑에 시체가 깔려 있으면 도망감
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();

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

	// 감염체라 주변에 가장 많은 지점을 탐색하는 함수(시체폭탄 시전 시 사용)
	public Point WhereToGoToBeCorpes() {
		int max_infected_row = favoritePoint.row, max_infected_col = favoritePoint.column;
		int max_infected = 0;
		int infected_num;
		for (int i = 1; i < Constants.Classroom_Height - 1; i++) {
			for (int j = 1; j < Constants.Classroom_Width - 1; j++) {
				CellInfo cell = this.cells[i][j];
				infected_num = 0;
				for (int row_offset = -1; row_offset <= 1; row_offset++) {
					for (int col_offset = -1; col_offset <= 1; col_offset++) {
						infected_num += cell.CountIf_Players(player -> player.state == StateCode.Infected);
					}
				}
				if (infected_num > max_infected) {
					max_infected = infected_num;
					max_infected_row = i;
					max_infected_col = j;
				}
			}
		}
		return new Point(max_infected_row, max_infected_col);
	}

	// TODO 3*3 주변에 가장 생존자가 많고 감염체가 없는 지점 탐색, 없을 시 선호하는 칸 주변에서 감염체가 없는 지점 탐색
	// 최소한 떨어지자마자 시체가 되는 일을 방지하기 위함
	public Point WhereToGoToBeSafe() {
		int safest_row = favoritePoint.row, safest_col = favoritePoint.column;
		int max_survivor = 0;
		int survivor_num;
		boolean is_there_infected = false;

		// 3*3구역에 감염체가 하나도 없는 구역이 있는지
		boolean is_there_safezone = false;

		for (int i = 1; i < Constants.Classroom_Height - 1; i++) {
			for (int j = 1; j < Constants.Classroom_Width - 1; j++) {
				CellInfo cell = this.cells[i][j];
				survivor_num = 0;
				is_there_infected = false;
				for (int row_offset = -1; row_offset <= 1; row_offset++) {
					for (int col_offset = -1; col_offset <= 1; col_offset++) {
						CellInfo offsetcell = this.cells[i + row_offset][j + col_offset];
						// 3*3 구역의 생존자 숫자를 집계
						survivor_num += offsetcell.CountIf_Players(player -> player.state == StateCode.Survivor);

						// 만약 해당 셀 주변에 감염자가 하나라도 있으면 반복문 탈출, 다음 칸으로 넘어감
						if (offsetcell.CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
							is_there_infected = true;
							break;
						}
					}
					if (is_there_infected)
						break;
				}

				// 해당 포인트 주변에 감염체가 있는 경우 다음 포인트를 탐색
				if (is_there_infected)
					continue;

				// 여기까지 코드가 왔다는 건 일단은 3*3 구역 안에 안전한 지점에 있다는 의미이므로
				// is_there_safezone을 true로 변경
				is_there_safezone = true;

				if (survivor_num > max_survivor) {
					max_survivor = survivor_num;
					safest_row = i;
					safest_col = j;
				}
			}
		}

		// 주변 3*3 구역 안에 감염체가 없는 칸이 존재하지 않을 경우 가운데 5*5 구역 안에서 감염체가 없는 지점으로 떨어진다
		if (!is_there_safezone) {
			for (int i = 4; i <= 8; i++) {
				for (int j = 4; j <= 8; j++) {
					CellInfo cell = this.cells[i][j];
					if (cell.CountIf_Players(player -> player.state == StateCode.Infected) != 0)
						return new Point(i, j);
				}
			}
		}

		// 가운데 5*5 구역마저도 감염체가 없는 곳이 없다면 선호하는 칸으로 떨어짐
		return new Point(safest_row, safest_col);
	}

	@Override
	public Point Soul_Spawn() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		// 생존자와 감염체 수 집계
		int total_survivor = 0, total_infected = 0;
		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				CellInfo cell = this.cells[row][column];
				total_survivor += cell.CountIf_Players(player -> player.state == StateCode.Survivor);
				total_infected += cell.CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		// 생존자>감염체인 경우 또는 턴수가 60턴 이하인 경우 안전한 곳으로 생존자 부활
		if (total_survivor > total_infected || this.turnInfo.turnNumber < 60) {
			// int min_row = -1;
			// int min_column = -1;
			// min_distance = Integer.MAX_VALUE;
			//
			// // 전체 칸을 검색하여 생존자가 있는 칸들 중 '선호하는 칸'과 가장 가까운 칸을 선택
			// for (int row = 0; row < Constants.Classroom_Height; row++) {
			// for (int column = 0; column < Constants.Classroom_Width;
			// column++) {
			// CellInfo cell = this.cells[row][column];
			//
			// int numberOfInfecteds = cell.CountIf_Players(player ->
			// player.state == StateCode.Survivor);
			//
			// if (numberOfInfecteds != 0) {
			// int distance = favoritePoint.GetDistance(row, column);
			//
			// // 거리가 더 가까우면 갱신
			// if (distance < min_distance) {
			// min_distance = distance;
			// min_row = row;
			// min_column = column;
			// }
			// // 거리가 같으면 더 좋아하는 방향을 선택
			// else if (distance == min_distance) {
			// for (int iDirection = 0; iDirection < 4; iDirection++) {
			// Point adjacentPoint =
			// favoritePoint.GetAdjacentPoint(directions[iDirection]);
			//
			// if (adjacentPoint.GetDistance(row, column) <
			// adjacentPoint.GetDistance(min_row,
			// min_column)) {
			// min_row = row;
			// min_column = column;
			// break;
			// }
			// }
			//
			// // 여기까지 왔다면 이제 그만 놓아 주자
			// }
			// }
			// }
			// }
			// return new Point(min_row, min_column);
			return WhereToGoToBeSafe();
		}

		// 생존자<감염체 인 경우 주변에 감염체가 가장 많은 곳으로 시체폭탄 투하
		else {
			min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			boolean Can_I_Get_More_Cmax = false;
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// 가장 많은 칸이 발견되면 갱신
					if (weight > this.myScore.corpse_max) {
						Can_I_Get_More_Cmax = true;
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
			}
			if(Can_I_Get_More_Cmax)return new Point(max_row, max_column);
			else return WhereToGoToBeCorpes();
		}
	}
}
