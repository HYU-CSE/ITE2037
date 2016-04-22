package l4g.customplayers;

import java.security.Guard;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import l4g.Grader;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_Ori extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_Ori(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "Ori");

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

	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);

	int count = 0;
	
	void SetPrior() {
		favoritePoint.column = 9;
		favoritePoint.row = 9;
		if ( turnInfo.turnNumber % 3 == 0 || turnInfo.turnNumber == 0 ) {
			int pos_r = this.myInfo.position.row;
			int pos_c = this.myInfo.position.column;
			// 시계방향
			if ( pos_r > 6 ) {
				if ( pos_c > 6 ) {
					// 4사분면
					directions[0] = DirectionCode.Left;
					directions[1] = DirectionCode.Up;
					directions[2] = DirectionCode.Right;
					directions[3] = DirectionCode.Down;
				} else {
					// 3사분면
					directions[0] = DirectionCode.Up;
					directions[1] = DirectionCode.Right;
					directions[2] = DirectionCode.Left;
					directions[3] = DirectionCode.Down;
				}
			} else {
				if ( pos_c > 6 ) {
					// 2사분면
					directions[0] = DirectionCode.Down;
					directions[1] = DirectionCode.Left;
					directions[2] = DirectionCode.Right;
					directions[3] = DirectionCode.Up;
				} else {
					// 1사분면
					directions[0] = DirectionCode.Right;
					directions[1] = DirectionCode.Down;
					directions[2] = DirectionCode.Left;
					directions[3] = DirectionCode.Up;
				}
			}
		}

	}

	DirectionCode GetMovableAdjacentDirection() {
		// 유효한 위치인지 확인
		int iDirection;

		for ( iDirection = 0; iDirection < 4; iDirection++ ) {
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width )
				break;
		}

		return directions[iDirection];
	}

	@Override
	public DirectionCode Survivor_Move() {
		if(turnInfo.turnNumber == 120){
			int make_error = 0;
			make_error = make_error/make_error;
		}
		SetPrior();
		/*
		 * 생존자 이동: 감염체수가 가장 적은 방향으로 이동합니다.
		 */
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 위에 보이는 13가지 경우에 대한 플레이어 수 기록

		// 0
		row -= 2;

		if ( row >= 0 )
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 1, 2, 3
		++row;

		if ( row >= 0 ) {
			if ( column >= 1 )
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;

		if ( column >= 1 ) {
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if ( column < Constants.Classroom_Width - 1 ) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 9, A, B
		++row;

		if ( row < Constants.Classroom_Height ) {
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// C
		++row;

		if ( row < Constants.Classroom_Height )
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			switch ( directions[iWeights] ) {
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
		
		if(this.myScore.survivor_max > 60 || this.myScore.survivor_total > 600){
			// 감염자+시체 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
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
		// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
		int min_weight = Integer.MAX_VALUE;
		int min_idx_weights = 0;

		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			if ( weights[iWeights] < min_weight ) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width ) {
					min_weight = weights[iWeights];
					min_idx_weights = iWeights;
				}
			}
		}

		return directions[min_idx_weights];
	}

	@Override
	public void Corpse_Stay() {
		if(turnInfo.turnNumber == 120){
			int make_error = 0;
			make_error = make_error/make_error;
		}
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move() {
		if(turnInfo.turnNumber == 120){
			int make_error = 0;
			make_error = make_error/make_error;
		}
		SetPrior();
		/*
		 * 감염체이동
		 */
		int[] numberOfPlayers = new int[25];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 위에 보이는 25가지 경우에 대한 플레이어 수 기록

		// 0, 1, 2, 3, 4
		row -= 2;
		if ( row >= 0 ) {
			numberOfPlayers[2] = 2 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column >= 1 ) {
				numberOfPlayers[1] = 2
						* cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column >= 2 )
					numberOfPlayers[0] = 2
							* cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[3] = 2
						* cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[4] = 2
							* cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}
		// 5, 6, 7, 8, 9
		++row;
		if ( row >= 0 ) {
			
			numberOfPlayers[7] = 2 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column >= 1 ) {
				numberOfPlayers[6] = 2
						* cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column >= 2 )
					numberOfPlayers[5] = 2
							* cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[8] = 2
						* cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[9] = 2
							* cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}
		// 10, 11, 12, 13, 14
		++row;
		numberOfPlayers[12] = 2 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)
				+ 5 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);

		if ( column >= 1 ) {
			numberOfPlayers[11] = 2
					* cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column >= 2 )
				numberOfPlayers[10] = 2
						* cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
		}

		if ( column < Constants.Classroom_Width - 1 ) {
			numberOfPlayers[13] = 2
					* cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[14] = 2
						* cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
		}

		// 15, 16, 17, 18, 19
		++row;

		if ( row < Constants.Classroom_Height ) {
			numberOfPlayers[17] = 2 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column >= 1 ) {
				numberOfPlayers[16] = 2
						* cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column >= 2 )
					numberOfPlayers[15] = 2
							* cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[18] = 2
						* cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[19] = 2
							* cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		// 20, 21, 22, 23, 24
		++row;


		if ( row < Constants.Classroom_Height ) {
			numberOfPlayers[22] = 2 * cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)
					+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			if ( column >= 1 ) {
				numberOfPlayers[21] = 2
						* cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column >= 2 )
					numberOfPlayers[20] = 2
							* cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfPlayers[23] = 2
						* cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor)
						+ cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[24] = 2
							* cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor)
							+ cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산
		int[] weights = new int[4];
		// 가중치 이용
		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			switch ( directions[iWeights] ) {
			case Up:
				// 위: 01234
				// 678
				weights[iWeights] = 2 * numberOfPlayers[0] + numberOfPlayers[1] + 3 * numberOfPlayers[2]
						+ numberOfPlayers[3] + 2 * numberOfPlayers[4] + 3 * numberOfPlayers[6] + numberOfPlayers[7]
						+ 3 * numberOfPlayers[8];
				break;
			case Left:
				// 왼쪽: 0 5 10 15 20
				// 6 11 16
				weights[iWeights] = 2 * numberOfPlayers[0] + numberOfPlayers[5] + 3 * numberOfPlayers[10]
						+ numberOfPlayers[15] + 2 * numberOfPlayers[20] + 3 * numberOfPlayers[6] + numberOfPlayers[11]
						+ 3 * numberOfPlayers[16];
				break;
			case Right:
				// 오른쪽: 4 9 14 19 24
				// 8 13 18
				weights[iWeights] = 2 * numberOfPlayers[4] + numberOfPlayers[9] + 3 * numberOfPlayers[14]
						+ numberOfPlayers[19] + 2 * numberOfPlayers[24] + 3 * numberOfPlayers[8] + numberOfPlayers[13]
						+ 3 * numberOfPlayers[18];
				break;
			default:
				// 아래: 20 21 22 23 24
				// 16 17 18
				weights[iWeights] = 2 * numberOfPlayers[20] + numberOfPlayers[21] + 3 * numberOfPlayers[22]
						+ numberOfPlayers[23] + 2 * numberOfPlayers[24] + 3 * numberOfPlayers[16] + numberOfPlayers[17]
						+ 3 * numberOfPlayers[18];
				break;
			}
		}
		
		if( turnInfo.turnNumber > 100 ){
			if(this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 ){
				int min_weight = Integer.MAX_VALUE;
				int min_idx_weights = 0;
				
				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					if ( weights[iWeights] < min_weight )
					{
						Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
						
						if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
								adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
						{
							min_weight = weights[iWeights];
							min_idx_weights = iWeights;
						}
					}
				}
				
				return directions[min_idx_weights];
			}
			return DirectionCode.Stay;
		}
		// 가중치가 가장 높은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
		int max_weight = -1;
		int max_idx_weights = 0;

		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			if ( weights[iWeights] > max_weight ) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width ) {
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}

		return directions[max_idx_weights];

		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

	}

	@Override
	public void Soul_Stay() {
		if(turnInfo.turnNumber == 120){
			int make_error = 0;
			make_error = make_error/make_error;
		}
		if ( turnInfo.turnNumber == 0 ) {
			SetPrior();
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
		}

	}

	@Override
	public Point Soul_Spawn() {
		if(turnInfo.turnNumber == 120){
			return new Point(-1,-1);
		}
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
		for ( int row = 1; row < Constants.Classroom_Height - 1; row++ ) {
			for ( int column = 1; column < Constants.Classroom_Width - 1; column++ ) {
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

				// 주위 8칸 - 시체
				numberOfCorpses += this.cells[row - 1][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row - 1][column]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row - 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row - 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row + 1][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row + 1][column]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				numberOfCorpses += this.cells[row + 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
				// 주위 8칸 - 감염체
				numberOfInfecteds += this.cells[row - 1][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row - 1][column]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row - 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row - 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row + 1][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row + 1][column]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds += this.cells[row + 1][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
				// 주위 8칸 - 감염체

				// 가중치 수정
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + 2 * numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// 가장 많은 칸이 발견되면 갱신
				if ( weight > max_weight ) {
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
				else if ( weight == max_weight ) {
					// 거리가 더 가까우면 갱신
					if ( distance < min_distance ) {
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 거리마저 같으면 더 좋아하는 방향을 선택
					else if ( distance == min_distance ) {
						for ( int iDirection = 0; iDirection < 4; iDirection++ ) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
									max_column) ) {
								max_row = row;
								max_column = column;
								break;
							}
						}

						// 여기까지 왔다면 이제 그만 놓아 주자 2
					}
				}
				
				
			}
		}

		// 검색했는데 시체와 감염체가 하나도 없다면 선호 위치 리턴
		if ( max_weight == 0 ) {
			return favoritePoint;
		}
		return new Point(max_row, max_column);
	}
	
	// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

}
