package l4g.customplayers;

import java.util.function.Predicate;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */

public class Player_천사소녀네티 extends Player {

	public Player_천사소녀네티( int ID ) {

		super(ID, "천사소녀네티");

		this.trigger_acceptDirectInfection = true;
	}

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
		long seed = (ID + 41) * gameNumber + ID;

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

		favoritePoint.row = (int) (seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int) (seed % Constants.Classroom_Height);
	}

	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다. 이 field는 반드시 필요합니다.
	 */
	DirectionCode[]	directions		= new DirectionCode[4];

	Point			favoritePoint	= new Point(0, 0);

	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다. 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다. 이 메서드는 반드시
	 * 필요합니다.
	 */

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

	static final int	GoRight	= 0;
	static final int	GoDown	= 1;
	static final int	GoLeft	= 2;
	static final int	GoUp	= 3;

	int					state	= GoRight;

	void UpdateState() {
		switch (state) {
			case GoRight:
				if (this.myInfo.position.column == Constants.Classroom_Width - 5)
					state = GoDown;
				break;
			case GoDown:
				if (this.myInfo.position.row == Constants.Classroom_Width - 10)
					state = GoLeft;
				break;
			case GoLeft:
				if (this.myInfo.position.column == Constants.Classroom_Width - 10)
					state = GoUp;
				break;
			case GoUp:
				if (this.myInfo.position.row == Constants.Classroom_Width - 10)
					state = GoRight;
				break;
			default:
				break;
		}
	}

	@Override
	public DirectionCode Survivor_Move() {
		/*
		 * 생존자 이동: 생존자가 이동할 가능성이 가장 많은 방향으로 이동합니다. 내 주변 칸에 대해 0 1 2 3 X 4 5 6 7
		 * ..로 번호를 매겼을 때 위: 012에 있는 사람 수 왼쪽: 135에 있는 사람 수 오른쪽: 246에 있는 사람 수 아래:
		 * 567에 있는 사람 수 ..를 합산하여 비교합니다.
		 */
		int[] numberOfSurvivors = new int[8];
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 0
		row -= 2;

		if (row >= 0)
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 1, 2
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfSurvivors[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[2] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 3, 4
		++row;

		if (column >= 2)
			numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		if (column < Constants.Classroom_Width - 2)
			numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 5, 6
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfSurvivors[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[6] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 7
		++row;

		if (row < Constants.Classroom_Height)
			numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
				case Up:
					// 위: 012
					weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2];
					break;
				case Left:
					// 왼쪽: 135
					weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[3] + numberOfSurvivors[5];
					break;
				case Right:
					// 오른쪽: 246
					weights[iWeights] = numberOfSurvivors[2] + numberOfSurvivors[4] + numberOfSurvivors[6];
					break;
				default:
					// 아래: 567
					weights[iWeights] = numberOfSurvivors[5] + numberOfSurvivors[6] + numberOfSurvivors[7];
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

	@Override
	public void Corpse_Stay() {

	}

	@Override
	public DirectionCode Infected_Move() {
		/*
		 * 감염체 이동: 생존자가 이동할 가능성이 가장 많은 방향으로 이동합니다. 내 주변 칸에 대해 0 1 2 3 X 4 5 6 7
		 * ..로 번호를 매겼을 때 위: 012에 있는 사람 수 왼쪽: 135에 있는 사람 수 오른쪽: 246에 있는 사람 수 아래:
		 * 567에 있는 사람 수 ..를 합산하여 비교합니다.
		 */
		int[] numberOfSurvivors = new int[8];
		int[] numberOfCorpses = new int[4];
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 0
		row -= 2;

		if (row >= 0)
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 1, 2
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfSurvivors[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[2] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 3, 4
		++row;

		if (column >= 2)
			numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		if (column < Constants.Classroom_Width - 2)
			numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 5, 6
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfSurvivors[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[6] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 7
		++row;

		if (row < Constants.Classroom_Height)
			numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
				case Up:
					// 위: 012
					weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2];
					break;
				case Left:
					// 왼쪽: 135
					weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[3] + numberOfSurvivors[5];
					break;
				case Right:
					// 오른쪽: 246
					weights[iWeights] = numberOfSurvivors[2] + numberOfSurvivors[4] + numberOfSurvivors[6];
					break;
				default:
					// 아래: 567
					weights[iWeights] = numberOfSurvivors[5] + numberOfSurvivors[6] + numberOfSurvivors[7];
					break;
			}
		}


		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 시체 수 합산 (남은 턴 2 이상)
		Predicate<PlayerInfo> cond = player -> player.state == StateCode.Corpse && player.transition_cooldown >= 2;

		// 0
		--row;
		numberOfCorpses[0] = count_players( row, column, cond );

		// 1, 2
		++row;
		numberOfCorpses[1] = count_players( row, column-1, cond );
		numberOfCorpses[2] = count_players( row, column+1, cond );
		
		// 3
		++row;
		numberOfCorpses[3] = count_players( row, column-2, cond );

		
		// 거리 2에 있는 생존자 수가 2 이상인지 구한다.
		int max_weight = -1;
		int max_idx_weights = 0;

		int total_numberOfSurvivors = 0;
		boolean isCorpseExists;

		for(int i = 0; i < 8; i++){
			total_numberOfSurvivors += numberOfSurvivors[i];
		}

		isCorpseExists = numberOfCorpses[0]+numberOfCorpses[1]+numberOfCorpses[2]+numberOfCorpses[3] > 0;
		
		//////////////////////////////////////////////////////////////////////////////
		// 만약 거리 2에 있는 생존자 수가 2명 이상이면 생존자가 많은방향 선택,
		

		if (total_numberOfSurvivors >=2) {
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
		
		// 그렇지 않으면 거리1에 시체가 존재하고, 그들의 life가 2 이상인지 찾는다.
			
		// Yes : Predator mode(시체 사냥)
		else if(isCorpseExists){
		
			// 현재 칸에 시체가 있다면 무조건 대기
			if ( count_players( myInfo.position.row, myInfo.position.column, StateCode.Corpse) != 0 ) {
				return DirectionCode.Stay;
			}

			else {
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
						
		
		// No : 정화기도 실시
		else {
			// 내 밑에 시체가 깔려 있으면 도망감
			if ( count_players( myInfo.position.row, myInfo.position.column, StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
		
			// 그렇지 않으면 정화 기도 시도
			else return DirectionCode.Stay;
		}
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

		if (this.turnInfo.turnNumber == 0) {
			return new Point(5, 5);

		}

		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

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
	
	/**
	 * 해당 위치의 플레이어 수를 구합니다.
	 * 올바르지 못한 위치를 가리키는 경우, 0을 반환합니다.
	 */
	private int count_players( int row, int col )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		return cells[row][col].Count_Players();
	}
	
	/**
	 * 주어진 state 인 해당 위치의 플레이어 수를 구합니다.
	 * 올바르지 못한 위치를 가리키는 경우, 0을 반환합니다.
	 */
	private int count_players( int row, int col, StateCode state )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		Predicate<PlayerInfo> cond = player -> player.state == state;
		
		return cells[row][col].CountIf_Players( cond );
	}
	
	/**
	 * cond 를 만족하는 해당 위치의 플레이어 수를 구합니다.
	 * 올바르지 못한 위치를 가리키는 경우, 0을 반환합니다.
	 */
	private int count_players( int row, int col, Predicate<PlayerInfo> cond )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		return cells[row][col].CountIf_Players( cond );
	}

}
