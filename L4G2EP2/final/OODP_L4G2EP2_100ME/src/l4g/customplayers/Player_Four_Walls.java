package l4g.customplayers;

import l4g.Grader;
import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_Four_Walls extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_Four_Walls(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "난 벽이 좋아!");

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
	static final int GoRight = 0;
	static final int GoDown = 1;
	static final int GoLeft = 2;
	static final int GoUp = 3;

	int state = GoRight;
	
	DirectionCode[] directions = new DirectionCode[4];
	
	Point favoritePoint = new Point(0, 0);
	
	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다.
	 * 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	void Init_Data()
	{
		/*
		 * 여러분이 작성하는 플레이어는 언제나 강의실에 단 한 명 존재하므로
		 * 사실 아래의 코드를 쓸 필요 없이
		 * 
		 * directions[0] = DirectionCode.Up;
		 * directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right;
		 * directions[3] = DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6;
		 * favoritePoint.column = 6;
		 * 
		 * ...와 같이 여러분이 좋아하는 우선 순위를 그냥 바로 할당해 써도 무방합니다.
		 * (Bot들은 똑같은 클래스의 인스턴스가 여럿 존재하므로 이런 짓을 해야 합니다).
		 */
		
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		favoritePoint.column = 1;
		favoritePoint.row = 1;
	}
	
	/**
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}

	void UpdateState() {
		switch (state) {
		case GoRight:
			if (this.myInfo.position.column == Constants.Classroom_Width - 1)
				state = GoDown;
			break;
		case GoDown:
			if (this.myInfo.position.row == Constants.Classroom_Height - 1)
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

	@Override
	public DirectionCode Survivor_Move() // 방향 리턴 레프트 라이트 업 다운
	{
		DirectionCode result = DirectionCode.Right;

		UpdateState();

		switch (state) {
		case GoRight:
			result = DirectionCode.Right;
			break;
		case GoLeft:
			result = DirectionCode.Left;
			break;
		case GoUp:
			result = DirectionCode.Up;
			break;
		case GoDown:
			result = DirectionCode.Down;
			break;
		}

		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}

	@Override
	public void Corpse_Stay() // 아무것도안넣어도 상관 ㄴ , 강의실나가고싶으면 0으로 나누면 댐
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move() // 감염체 무브, 방향 리턴, stay라는 값존재
	{
		DirectionCode result = DirectionCode.Stay;

		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}

	@Override
	public void Soul_Stay() // 한번 눌러서 스테이를 하고, 0번째에 강의시로 진입, 가장먼저 실행되는 메서드.
	{
		if ( this.turnInfo.turnNumber == 0 )
		{
			// 이 부분은 Bot 플레이어 코드를 복붙해 사용하기 위해 반드시 필요합니다.
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn() {
		int result_row = -1;
		int result_column = -1;
		state = GoRight;
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		if (turnInfo.turnNumber > 60 && turnInfo.turnNumber < 121) {
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
				//int variableToMakeError = 0;

				//variableToMakeError = variableToMakeError / variableToMakeError;
				return favoritePoint;
				
			}
			return new Point(max_row, max_column);
		}
		return favoritePoint;
	}

	// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
}
