package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.TurnInfo;
/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다. 생존자 Sn_move()움직 시체 void Co_Stay()
 * 가만히 있음 //시체상태일때는 나를 먹은 아이디를 알려줌 감염체 In_Move() point void Soul_stay() 턴수가 11이
 * 넘었냐 생존자점수가 20이 넘었
 * 
 * @author Racin
 *
 */
import java.util.ArrayList;
import l4g.data.PlayerInfo;

public class 검은수염티치_봇열매여러개먹음 extends Player {
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

		favoritePoint.row = (int) (seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int) (seed % Constants.Classroom_Height);
	}

	/**
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다. 이 메서드는 반드시 필요합니다.
	 */
	DirectionCode GetMovableAdjacentDirection() {
		int iDirection;

		for (iDirection = 0; iDirection < 4; iDirection++) {
			Point adjacentPoint = myInfo.position
					.GetAdjacentPoint(directions[iDirection]);

			if (adjacentPoint.row >= 0
					&& adjacentPoint.row < Constants.Classroom_Height
					&& adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break;
		}

		return directions[iDirection];
	}

	@Override
	public DirectionCode Survivor_Move() {
		if (turnInfo.turnNumber < 50) {
			/*능력 1 from 은둔 bot
			 * 생존자 이동: 플레이어 수가 가장 적은 방향으로 이동합니다. 생존자의 시야 범위가 0 123 45678 9AB C
			 * ..일 때 위: 0123에 있는 플레이어 수 왼쪽: 1459에 있는 플레이어 수 오른쪽: 378B에 있는 플레이어 수
			 * 아래: 9ABC에 있는 플레이어 수 ..를 합산하여 비교합니다.
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
					numberOfPlayers[11] = cells[row][column + 1]
							.Count_Players();
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
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1]
							+ numberOfPlayers[2] + numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4]
							+ numberOfPlayers[5] + numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7]
							+ numberOfPlayers[8] + numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfPlayers[9]
							+ numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
			int min_weight = Integer.MAX_VALUE;
			int min_idx_weights = 0;

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				if (weights[iWeights] < min_weight) {
					Point adjacentPoint = myInfo.position
							.GetAdjacentPoint(directions[iWeights]);

					if (adjacentPoint.row >= 0
							&& adjacentPoint.row < Constants.Classroom_Height
							&& adjacentPoint.column >= 0
							&& adjacentPoint.column < Constants.Classroom_Width) {
						min_weight = weights[iWeights];
						min_idx_weights = iWeights;
					}
				}
			}

			return directions[min_idx_weights];
		} 
		else{

			/*
			 * 생존자 이동: 생존자가 이동할 가능성이 가장 많은 방향으로 이동합니다.
			 *              내 주변 칸에 대해
			 *                    0
			 *                   1 2
			 *                  3 X 4
			 *                   5 6
			 *                    7
			 *              ..로 번호를 매겼을 때
			 *              위:     012에 있는 사람 수
			 *              왼쪽:   135에 있는 사람 수
			 *              오른쪽: 246에 있는 사람 수
			 *              아래:   567에 있는 사람 수
			 *              ..를 합산하여 비교합니다.
			 */
			int[] numberOfSurvivors = new int[8];
			int row = myInfo.position.row;
			int column = myInfo.position.column;
			
			// 0
			row -= 2;
			
			if ( row >= 0 )
				numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			// 1, 2
			++row;
			
			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfSurvivors[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfSurvivors[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			
			// 3, 4
			++row;
			
			if ( column >= 2 )
				numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 2 )
				numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			// 5, 6
			++row;
			
			if ( row < Constants.Classroom_Height)
			{
				if ( column >= 1 )
					numberOfSurvivors[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfSurvivors[6] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
			}
			
			// 7
			++row;
			
			if ( row < Constants.Classroom_Height)
				numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			
			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산		
			int[] weights = new int[4];

			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				switch ( directions[iWeights] )
				{
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
			
			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				if ( weights[iWeights] > max_weight )
				{
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
					
					if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
							adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
					{
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
		if (turnInfo.turnNumber < 70) {
			int min_row = -1;
			int min_column = -1;
			int min_count = 1;
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					int count = cells[row][column].Count_Players();
					// 플레이어 수가 더 적다면 항상 갱신
					if (count < min_count) {
						min_row = row;
						min_column = column;
						min_count = count;
					}
					
				}
			}

			return new Point(min_row, min_column);
		}

		else {
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell
							.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell
							.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses
							+ numberOfInfecteds : 0;
					// 가장 많은 칸이 발견되면 갱신
					if (weight > max_weight) {
						max_weight = weight;
						max_row = row;
						max_column = column;
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
	}

	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.

	public 검은수염티치_봇열매여러개먹음(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "검은수염모드(survivor)");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false; // true면은 받고 false면 안받는다.

		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.

	}

	static final int GoRight = 0; // 스테이트에 대한 상태
	static final int GoDown = 1; // 스테이트에 대한 상태
	static final int GoLeft = 2; // 스테이트에 대한 상태
	static final int GoUp = 3; // 스테이트에 대한 상태

	int state = GoRight;

	// 현재 내상태에 따른 상태변화가 필요
	void Updatestate() {
		switch (state) {
		case GoRight:
			if (this.myInfo.position.column == Constants.Classroom_Width - 1)
				state = GoDown;
			break;

		case GoDown:
			if (this.myInfo.position.row == Constants.Classroom_Width - 1)
				state = GoLeft;
			break;

		case GoLeft:
			if (this.myInfo.position.column == 0)
				state = GoUp;
			break;

		case GoUp:
			if (this.myInfo.position.row == 0)
				state = GoRight;
			break;
		}

	}

}
