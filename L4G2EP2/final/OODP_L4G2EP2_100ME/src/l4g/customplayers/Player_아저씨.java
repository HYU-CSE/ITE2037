package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * PlayerName: 오늘만산다
 * 
 * Concept: 캐릭터의 컨셉은 영화 '아저씨'의 주인공 차태식입니다. 이 캐릭터는 은둔형 외톨이로, 단 하루만 살려고 노력합니다. 그리고
 * 죽게되면 그 다음부터는 항상 적(감염체)이 있는 곳에 뛰어들며, 자신이 피를 묻히게 되면 그 자리에서 움직이지않고 회개합니다.
 * 
 * 직접 감염: 항상 거절합니다.
 * 
 * 생존자 이동: 플레이어 수가 가장 적은 방향으로 이동합니다.
 *              생존자의 시야 범위가
 *                    0
 *                   123
 *                  45678
 *                   9AB
 *                    C
 *              ..일 때
 *              위:     0123에 있는 플레이어 수
 *              왼쪽:   1459에 있는 플레이어 수
 *              오른쪽: 378B에 있는 플레이어 수
 *              아래:   9ABC에 있는 플레이어 수
 *              ..를 합산하여 비교합니다.
 * 
 * 감염체 이동: 항상 정화 기도를 드립니다.
 * 
 * 영혼 배치: 감염체가 가장 많은 칸을 골라 배치합니다.
 * 
 * @author Racin
 *
 */
public class Player_아저씨 extends Player {
	public Player_아저씨(int ID) {

		super(ID, "오늘만산다");

		// 직접 감염을 항상 거절합니다. 남들이 건드리지 않으면, 평화롭게 살아가길 원합니다.
		this.trigger_acceptDirectInfection = false;

	}
	
	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다.
	 * 이 field는 반드시 필요합니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	Point favoritePoint = new Point(0, 0);

	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다.
	 * 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	void Init_Data() {

		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		// 좋아하는 우선순위로 정 가운데를 선택했습니다.
		favoritePoint.row = 7;
		favoritePoint.column = 7;
	}

	/**
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다.
	 * 이 메서드는 반드시 필요합니다.
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
		 * 생존자 이동: 플레이어 수가 가장 적은 방향으로 이동합니다.
		 *              생존자의 시야 범위가
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..일 때
		 *              위:     0123에 있는 플레이어 수
		 *              왼쪽:   1459에 있는 플레이어 수
		 *              오른쪽: 378B에 있는 플레이어 수
		 *              아래:   9ABC에 있는 플레이어 수
		 *              ..를 합산하여 비교합니다.
		 */
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

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
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
			case Up:
				// 위: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// 왼쪽: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// 오른쪽: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// 아래: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
				break;
			}
		}

		// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
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

		// 항상 정화기도를 드립니다.
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// 이 부분은 Bot 플레이어 코드를 복붙해 사용하기 위해 반드시 필요합니다.
			Init_Data();
		}
		/*
		 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
		 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
		 */

	}

	@Override
	public Point Soul_Spawn() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// 전체 칸을 검색하여 감염체 수가 가장 많은 칸을 찾음
		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				CellInfo cell = this.cells[row][column];

				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

				int weight = numberOfInfecteds != 0 ? numberOfInfecteds : 0;
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
		return new Point(max_row, max_column);
	}
}
