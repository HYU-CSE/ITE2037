package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class SangU extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);
	boolean deadOnce = false;

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

		// 플레이어마다 다른 ID, 게임마다 다른 게임 번호를 조합하여 뭔가 이상한 수를 만들고
		long seed = (ID + 2016) * gameNumber + ID;

		if (seed < 0)
			seed = -seed;

		// 그 수를 24로 나눈 나머지를 토대로 방향 우선순위 제작
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

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "까데기치지마라");

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

	@Override
	public DirectionCode Survivor_Move() {
		// 한번도 안죽었을 때는 외톨이와 같은 알고리즘 생존 횟수를 높입니다
		if (!deadOnce && Math.abs(gameNumber) % 2 == 1 && turnInfo.turnNumber < 80) {
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
			// 한번이라도 죽었을 경우 포착 점수를 최대한 높이는 방향으로 이동합니다.
			int[] survivors = new int[4];
			int[] others = new int[4];
			int[] weights = new int[4];
			int row, col, max_weight = -1;

			// 현재 포착된 모든 플레이어에 대해 검사 수행
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

			// 포착 기대값: 생존자 수 x 시체 및 감염체 수 계산
			for (int i = 0; i < 4; i++)
				weights[i] = survivors[i] * others[i];

			// 갈 수 없는 방향에 대해 기대값을 최소값으로 설정
			if (IsValidMove(DirectionCode.Up) == false)
				weights[0] = -1;

			if (IsValidMove(DirectionCode.Left) == false)
				weights[1] = -1;

			if (IsValidMove(DirectionCode.Right) == false)
				weights[2] = -1;

			if (IsValidMove(DirectionCode.Down) == false)
				weights[3] = -1;

			// 측정된 기대값의 최대값이 몇인지 계산
			for (int weight : weights) {
				if (weight > max_weight) {
					max_weight = weight;
				}
			}

			// 처음 directions 설정에 따라 기대값이 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
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
		// 가능한 이동인지 검사
		// 만약 내가 지금 생존자 또는 감염체라면
		if (myInfo.state == StateCode.Survivor || myInfo.state == StateCode.Infected) {
			// 방향이 없으면 '대기'로 간주
			if (direction == null)
				direction = DirectionCode.Stay;

			// 생존자가 제자리에 대기하는 것은 불가능
			if (myInfo.state == StateCode.Survivor && direction == DirectionCode.Stay)
				return false;

			// 마지막으로, 해당 좌표가 유효한지 여부 검사
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

		// 생존자나 감염체가 아니라면 이동 자체가 불가능
		else
			return false;
	}

	@Override
	public void Corpse_Stay() {
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move() {
		// 정화기도 열심히 합니다 시체 있으면 살짝 피해주는 매너
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();

		// 여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
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
		// 처음 배치할때 판 수에따라 배치위치 다르게한다.
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
		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
		// 각 경우에 따라 배치.
		// 생존자,영혼 많을때 : 감염체있는곳으로, 감염된다.
		// 감염체, 시체 많을때 : 생존자 주변으로 가 포착점수를 높인다.
		Point pointToSpawn = favoritePoint;

		int row, col;
		
		// 턴이 70번이 넘어가면 시체폭탄으로 변합니다.
		if (turnInfo.turnNumber > 70) {
			// 각 칸에 있는 감염체 수를 기록하기 위한 배열 사용
			int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int max_weight = -1;
			ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

			// 모든 감염체에 대해 검사 수행 - 13x13칸에 플레이어는 100명이므로 감염체가 가장 많은 칸 목록을 동시에 구성
			for (row = 0; row < Constants.Classroom_Height; row++) {
				for (col = 0; col < Constants.Classroom_Width; col++) {
					Point nowPoint = new Point(row, col);
					weights[row][col] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Infected);
					// 최대값이 바뀌면 '감염체가 가장 많은 칸' 목록을 초기화
					if (weights[row][col] > max_weight) {
						max_weight = weights[row][col];
						list_pos_max_weight.clear();
					}
					// 현재 칸의 감염자의 수가 최대값과 같으면 '감염자가 가장 많은 칸' 목록에 현재 칸 추가
					if (weights[row][col] == max_weight)
						list_pos_max_weight.add(nowPoint);
				}
			}

			// 검사가 끝나면 감염체가 가장 많은 칸 목록에서 포인트를 지정해 그 주변에 가장 적절한 지점을 탐색
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
		// 감염체, 시체 많을때 : 생존자 주변으로 가 포착점수를 높인다.
		// else {
		// 각 칸에 있는 생존자 수를 기록하기 위한 배열 사용
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		for (int i = 0; i < Constants.Classroom_Height; i++) {
			for (int j = 0; j < Constants.Classroom_Width; j++) {
				weights[i][j] = 0;
			}
		}

		// 모든 감염체에 대해 검사 수행 - 13x13칸에 플레이어는 100명이 존재 생존자가 가장 많은 칸 목록을 동시에 구성

		for (row = 0; row < Constants.Classroom_Height; row++) {
			for (col = 0; col < Constants.Classroom_Width; col++) {
				Point nowPoint = new Point(row, col);
				weights[row][col] = cells[row][col].CountIf_Players(player -> player.state == StateCode.Survivor);
				// 최대값이 바뀌면 '생존자 가장 많은 칸' 목록을 초기화
				if (weights[row][col] > max_weight) {
					max_weight = weights[row][col];
					list_pos_max_weight.clear();
				}
				// 현재 칸의 생존자의 값이 최대값과 같으면 현재칸을 '생존자가 가장 많은 칸' 목록에 추가
				if (weights[row][col] == max_weight)
					list_pos_max_weight.add(nowPoint);
			}
		}

		// 검사가 끝나면 생존자가 가장 많은 칸 목록에서 '기점 설정' 기능으로 정해 둔 기점과 가장 가까운 첫번째 칸 선택
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
