package l4g.customplayers;

import java.awt.HeadlessException;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_MIDSIN extends Player {
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
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;

		// 중앙을 좋아함.
		favoritePoint.row = Constants.Classroom_Height / 2;
		favoritePoint.column = Constants.Classroom_Width / 2;
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
	public Player_MIDSIN(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "OOPArts.Midsin");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = true;

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

	@Override
	public DirectionCode Survivor_Move() {
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
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column >= 2) {
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[4] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

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

		if (row < Constants.Classroom_Height) {
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
			case Up:
				// 위: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3]
						- numberOfInfected[0] * 10;
				break;
			case Left:
				// 왼쪽: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9]
						- numberOfInfected[4] * 10;
				break;
			case Right:
				// 오른쪽: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11]
						- numberOfInfected[8] * 10;
				break;
			default:
				// 아래: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12]
						- numberOfInfected[12] * 10;
				break;
			}
		}

		// 생존자 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
		int max_weight = -10000;
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

	@Override
	public void Corpse_Stay() {
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move() {
		DirectionCode result = DirectionCode.Stay;
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		int maxCorpseCount = -1;
		int checkMovablePlace = 1;

		// 내 밑에 시체가 깔려 있으면 도망감
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 ) {
			// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
			int[] numberOfCorpses = {-1, -1, -1, -1};
			
			if (row > 0)
				numberOfCorpses[0] = cells[row - 1][column].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (column > 0)
				numberOfCorpses[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (column < Constants.Classroom_Width - 1)
				numberOfCorpses[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (row < Constants.Classroom_Height - 1)
				numberOfCorpses[3] = cells[row + 1][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			// 시체없는곳이 있다면 도망감
			if(numberOfCorpses[0] == 0) {
				return DirectionCode.Up;
			}
			if(numberOfCorpses[1] == 0) {
				return DirectionCode.Left;
			}
			if(numberOfCorpses[2] == 0) {
				return DirectionCode.Right;
			}
			if(numberOfCorpses[3] == 0) {
				return DirectionCode.Down;
			}
			
			// 시체가 사방에 있으면 중앙으로 감
			int maxDistance = -1;
			Math.abs(maxDistance);
			if(row > Constants.Classroom_Height / 2) {
				maxDistance = row - Constants.Classroom_Height / 2;
				result = DirectionCode.Up;
			} else {
				maxDistance = Constants.Classroom_Height / 2 - row;	
				result = DirectionCode.Down;			
			}
			
			if(column > Constants.Classroom_Width / 2 && (column - Constants.Classroom_Height / 2 > maxDistance)) {
				result = DirectionCode.Left;
			} else if (column < Constants.Classroom_Width / 2 && (Constants.Classroom_Height / 2 - column > maxDistance)) {
				result = DirectionCode.Right;
			}
		}
	
		return result;
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

	@Override
	public Point Soul_Spawn() {
		int min_row = -1;
		int min_column = -1;
		int min_count = 1;

		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;

		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		if (turnInfo.turnNumber > Constants.Max_Turn_Numbers / 2) {

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
			int NumberOfInfested = cells[max_row][max_column]
					.CountIf_Players(player -> player.state == StateCode.Infected);

			if (NumberOfInfested > 10) {
				return new Point(max_row, max_column);
			}
		}

		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				int count = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				int distance = favoritePoint.GetDistance(row, column);

				if (cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
					continue;
				}

				// 플레이어 수가 더 많다면 항상 갱신
				if (count > min_count) {
					min_row = row;
					min_column = column;
					min_count = count;
					min_distance = distance;
				}
				// 플레이어 수가 같으면 선호하는 칸과 더 가까운 칸을 선택
				else if (count == min_count) {
					// 거리가 더 가까우면 갱신
					if (distance < min_distance) {
						min_row = row;
						min_column = column;
						min_distance = distance;
					}
					// 거리마저 같으면 더 좋아하는 방향을 선택
					else if (distance == min_distance) {
						for (int iDirection = 0; iDirection < 4; iDirection++) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

							if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row,
									min_column)) {
								min_row = row;
								min_column = column;
								break;
							}
						}

						// 여기까지 왔다면 이제 그만 놓아 주자
					}
				}
			}
		}

		return new Point(min_row, min_column);
	}
}
