package l4g.customplayers;

import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_PineApple extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_PineApple(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "승.리.한.다.");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}
	
	/*
	 * TODO#5	이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요.
	 * 			당연히 한 방에 될 리 없으니, 중간중간 코드를 백업해 두는 것이 좋으며,
	 * 			코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * 			L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 			여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다.
	 * 			그러니 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 			제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면,
	 * 			'코드에 노력이란게 묻어 날 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */
	
	
	
	int state = 0;
	/** 전략 짜기
	 *  초반 10턴은 생존 상태 이동 -> 중앙에 머무는게 전략
	 *  이후 45~55턴 가까이 생존 목표
	 *  만약 45~55턴이 넘어가면 바로 시체가 되기 위해 감염체에 붙는다.
	 *  이후 가장 많이 몰려있는 지역에 뛰어든다.
	 *  감염된 상태에서 주변에 머무른다.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result;
		int[] counts = new int[4];
		int PositionNum = 0;
		// 양방향 코드를 먼저 입력받는다.
		// 이는 다음에 나오는 if 문에 반복적으로 쓰이는 것을 줄어기 위해 사용되었다.
		// 초반 이동 중앙에 자리잡고 최대한 점수를 많이 획득한다.
		// 한바퀴를 돌도록 설계한다.
		/*
		 * TODO#1
		 * 먼저 초반 부근에 중앙 바로 옆에 착지하여, 한바퀴를 돈다.
		 * 횟수에 따라 이동하며 생존한다.
		 * 바로 중앙은 감염체가 등장할 위험이 존재한다. 따라서 그 바로 주변을 맴돌도록 설정한다.
		 * 이는 중앙 부근이 전체적인 생존확률을 높일 뿐만 이나라 많은 획득점수를 얻을 수 있기 때문이다.
		 */
		if( turnInfo.turnNumber < 3)
			result = DirectionCode.Down;
		else if(turnInfo.turnNumber < 5)
			result = DirectionCode.Right;
		else if(turnInfo.turnNumber < 7)
			result = DirectionCode.Up;
		else if(turnInfo.turnNumber < 9)
			result = DirectionCode.Left;
		else if(turnInfo.turnNumber < 11)
			result = DirectionCode.Down;
		/*
		 * TODO#2
		 * 다음에 나오는 코드는 생존 이후에 어디로 이동할지를 판단하는 부분이다.
		 * 이 부분은 생존하기 위해 최대한 감염체가 없는 곳으로 이동한다.
		 * 75턴 이전의 경우이다. 
		 */
		else if (turnInfo.turnNumber < 85){
			// 전체 게임판에서 감염체의 수를 받아와서 나중에 가중치 계산을 하여 이동할 수 있도록 한다.
			// 이 부분은 나중에 가장 감염체가 없는 곳을 골라내는데 쓰인다.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					counts[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					counts[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					counts[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					counts[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			// 번호별 방향 설정 //
			// 0 : up
			// 1 : down
			// 2 : left
			// 3 : right
			//=============//
			// 감염체가 가장 적은 방향으로 이동하기 위해 최대값을 받는다.
			for(int i = 1;i < 3;i++){
				if(counts[PositionNum] > counts[i]){
					PositionNum = i;
				}
			}
			// TODO #2-1 벽에 붙었을 때 이를 고려하여 무빙하는 것을 작성.
			// 봒으로 나가지 않도록 한다.
			// ADD #1 양쪽 4 귀퉁이는 벽에 붙는 경우중 특이한 경우이므로 if 문을 추가했다.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_inf = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( up_inf > left_inf ){
							result = DirectionCode.Left;
						}
						else{
							result = DirectionCode.Up;
						}
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(left_inf > down_inf ){
							
							result = DirectionCode.Down;
						}
						else{
							result = DirectionCode.Left;
						}
						
					}
						
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_inf = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(up_inf > right_inf){
							result = DirectionCode.Right;
						}else{
							
							result = DirectionCode.Up;
						}
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( down_inf > right_inf ){
							result = DirectionCode.Right;
						}
						else
							result = DirectionCode.Down;
					}
					
				}
			}
			// TODO #2-2 좌 우 벽에 붙었을 경우의 무빙이다.
			else if( myInfo.position.column % 12 == 0){
				Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
				Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
				int infect_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_up > 0 && infect_down > 0){
					if(myInfo.position.column == 0){
						result = DirectionCode.Right;
					}
					else{
						result = DirectionCode.Left;
					}
						
				}
				else if ( infect_up == 0 )
					result = DirectionCode.Up;
				else
					result = DirectionCode.Down;
			}
			// TODO #2-3 위아래로 붙었을 경우의 무빙이다.
			else if( myInfo.position.row % 12 == 0){
				Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
				Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
				int infect_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_right > 0 && infect_left > 0){
						if( myInfo.position.row == 0 ){
							result = DirectionCode.Down;
						}
						else{
							result = DirectionCode.Up;
						}
				}
				else if (infect_right == 0){
					result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Left;
			}
			// TODO #2-4 붙는 경우가 아닌경우 가중치에 따라서 이동한다.
			else{
				if(PositionNum == 0){
					result = DirectionCode.Up;
				}
				else if(PositionNum == 1){
					result = DirectionCode.Down;
				}
				else if (PositionNum == 2){
					result = DirectionCode.Left;
				}
				else{
					result = DirectionCode.Right;
				}
			}
		}
		// TODO #3 이 부분은 앞으로 감염체가 되어 살인점수를 많이 먹기 위함이다.
		// 따라서 감염체가 되도록 생존자가 감염체를 따라가도록 설계한다.
		// 75턴 이후
		else{
			int[] count = new int[4];
			int most_like_infect = 0;
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					count[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					count[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					count[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					count[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			// 번호별 방향 설정 //
			// 0 : up
			// 1 : down
			// 2 : left
			// 3 : right
			//=============//
			// 가장 큰 방향으로 이동하기 위해 최대값을 받는다.
			for(int i = 1;i < 3;i++){
				if(count[most_like_infect] < count[i]){
					most_like_infect = i;
				}
			}
			// TODO #3-1 벽에 붙었을 때 이를 고려하여 무빙하는 것을 작성.
			// 봒으로 나가지 않도록 한다.
			// ADD #1 양쪽 4 귀퉁이는 벽에 붙는 경우중 특이한 경우이므로 if 문을 추가했다.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_inf = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( up_inf > left_inf ){
							result = DirectionCode.Up;
						}
						else
							result = DirectionCode.Left;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(left_inf > down_inf ){
							result = DirectionCode.Left;
						}
						else
							result = DirectionCode.Down;
										
					}
										
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_inf = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(up_inf > right_inf){
							result = DirectionCode.Up;
						}else
							result = DirectionCode.Right;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( down_inf > right_inf ){
							result = DirectionCode.Down;
						}
						else
							result = DirectionCode.Right;
					}					
				}
			}
			// TODO #3-2 좌 우 벽에 붙었을 경우의 무빙이다.
			else if( myInfo.position.column % 12 == 0){
				Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
				Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
				int infect_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_up == 0 && infect_down == 0){
					if( myInfo.position.column == 0){
						result = DirectionCode.Right;
					}
					else{
						result = DirectionCode.Left;
					}
				}
				else if ( infect_up > 0 )
					result = DirectionCode.Up;
				else
					result = DirectionCode.Down;
			}
			// TODO #3-3 위아래로 붙었을 경우의 무빙이다.
			else if( myInfo.position.row % 12 == 0){
				Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
				Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
				int infect_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_right == 0 && infect_left == 0){
					if( myInfo.position.row == 0 ){
						result = DirectionCode.Down;
					}
					else{
						result = DirectionCode.Up;
					}
				}
				else if (infect_right > 0){
					result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Left;
			}
			// TODO #3-4 붙는 경우가 아닌경우 가중치에 따라서 이동한다.
			else{
				if(most_like_infect == 0){
					result = DirectionCode.Up;
				}
				else if(most_like_infect == 1){
					result = DirectionCode.Down;
				}
				else if (most_like_infect == 2){
					result = DirectionCode.Left;
				}
				else{
					result = DirectionCode.Right;
				}
			}
		}
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// 감염체 상태일때와 그 좌우 상화 좌표를 받아서 일정 턴 위치에서 어떻게 행동할지를 판단한다.
		int[] value_player_imp = new int[4];
		/* TODO #4 INFECTMOVE
		 * 이 부분은 75턴 이하일 경우 생존상태로 빠르게 되돌아 가기위해
		 * 감염체에 붙어서 죽기 위한 코드이다.
		 * 오른쪽의 경우 그 좌표의 상하 왼쪽의 값을 받아온다.
		 * 이후 가중치를 받은 후에 이 방향으로 이동하여 할 행동을 한다.
		 */
		DirectionCode result = null;
		// Note : 이 값이 0이면 기도를 드려 2턴만에 사망한다.
		if( turnInfo.turnNumber < 80 ){
			int most_imp_value = 0;
			//영혼이 아닌 모든 객체가 내 위에 얼마나 있는지 확인한다. 0일경우 기도를 시작한다.
			int my_point = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player->player.state == StateCode.Corpse);
			// 보이는 지점에서 감염체가 있는지 없는지 확인하고, 가장 빈곳으로 이동할수 있도록 가중치를 계산한다.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					value_player_imp[0] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					value_player_imp[1] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					value_player_imp[2] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					value_player_imp[3] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
			}
			// 이는 가장 기중치가 큰 부근을 선택한다.
			for(int i = 1;i < 4 ; i++){
				if(value_player_imp[most_imp_value] < value_player_imp[i] ){
					most_imp_value = i;
				}
			}
			
			
			if(my_point != 0){
				// 이 부부은 벽에 붙었을 경우 벗어나기 위한 코드이다.
				if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
					if( myInfo.position.column / 12 != 0 ){
						Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
						int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( myInfo.position.row / 12 != 0 ){
							Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
							int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if( up_corp != 0 || left_corp != 0){
								if( up_corp > left_corp){
									result = DirectionCode.Left;
								}else
									result = DirectionCode.Up;
							}else
								result = DirectionCode.Stay;
						}
						else{
							Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
							int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if( down_corp != 0 || left_corp != 0){
								if( down_corp > left_corp){
									result = DirectionCode.Left;
								}else
									result = DirectionCode.Down;
							}else
								result = DirectionCode.Stay;
						}
							
					}else{
						Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
						int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( myInfo.position.row / 12 != 0 ){
							Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
							int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if(up_corp != 0 || right_corp != 0){
								if(up_corp > right_corp){
									result = DirectionCode.Right;
								}else
									result = DirectionCode.Up;
							}else
								result = DirectionCode.Stay;
						}
						else{
							Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
							int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if(down_corp != 0 || right_corp != 0){
								if(down_corp > right_corp){
									result = DirectionCode.Right;
								}else
									result = DirectionCode.Down;
							}else
								result = DirectionCode.Stay;
						}
						
					}
				}
				// TODO #4-1 좌우로 붙었을 경우의 무빙이다.
				else if( myInfo.position.column % 12 == 0){
					Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
					Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
					int notinf_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state != StateCode.Infected);
					int notinf_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state != StateCode.Infected);
					if( notinf_up == 0 && notinf_down == 0){
						if(myInfo.position.column == 0){
							result = DirectionCode.Right;
						}
						else
							result = DirectionCode.Left;
					}
					else if( notinf_up > 0 )
						result = DirectionCode.Up;
					else
						result = DirectionCode.Down;
				}
				// TODO #4-2 위아래로 붙었을 경우의 무빙이다.
				else if( myInfo.position.row % 12 == 0){
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int notinf_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state != StateCode.Infected);
					int notinf_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state != StateCode.Infected);
					if( notinf_right == 0 && notinf_left == 0){
						if( myInfo.position.row == 0 ){
							result = DirectionCode.Down;
						}
						else
							result = DirectionCode.Up;
					}
					else if (notinf_right > 0){
						result = DirectionCode.Right;
					}
					else
						result = DirectionCode.Left;
				}
				else{
					if(most_imp_value == 0){
						result = DirectionCode.Right;
					}else if(most_imp_value == 1){
						result = DirectionCode.Left;
					}else if(most_imp_value == 2){
						result = DirectionCode.Up;
					}else 
						result = DirectionCode.Down;
				}
			}
			else{
				result = DirectionCode.Stay;
				// Note : 이 부분은 그 자리에서 기도를 드리는 부분입니다.
			}
		}
		/* TODO #GET_SCORE_FROM_CORPS_13
		 * 이 경우는 시체에 착륙한 후 13턴 이상이 되었을 경우 행동하는 위치이다.
		 * 13턴의 기준은 일정한 양만큼 점수를 먹고 바로 정화기도를 드린다.
		 * 이후 약 3~4번의 시행을 통해 점수를 많이 회득하려고 한다.
		 */
		else if ((turnInfo.turnNumber - 84) % 9 == 0  && turnInfo.turnNumber < 110 ){
			// 이 경우는 구석에서 시체가 들어오지 않을 경우 연속 2번 정화기도를 시도한는 구간이다.
			// 시체가 있을경우 구석에서 탈출하거나 정화기도를 곧장 하는 경우로 총 3가지의 가정문으로 형성되어 있다.
			// 다음의 변수들은 상하좌우 및 플레이어 위치에 시체의 수를 받는 변수들이다.
			int corp_my = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player->player.state == StateCode.Corpse);
			// 경우 1. 양 귀 4곳에 있을경우 시체가 없는 곳을 피해 정화기도를 한다.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( up_corp != 0 || left_corp != 0){
							if( up_corp > left_corp){
								result = DirectionCode.Left;
							}else
								result = DirectionCode.Up;
						}else
							result = DirectionCode.Stay;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( down_corp != 0 || left_corp != 0){
							if( down_corp > left_corp){
								result = DirectionCode.Left;
							}else
								result = DirectionCode.Down;
						}else
							result = DirectionCode.Stay;
					}
						
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if(up_corp != 0 || right_corp != 0){
							if(up_corp > right_corp){
								result = DirectionCode.Right;
							}else
								result = DirectionCode.Up;
						}else
							result = DirectionCode.Stay;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if(down_corp != 0 || right_corp != 0){
							if(down_corp > right_corp){
								result = DirectionCode.Right;
							}else
								result = DirectionCode.Down;
						}else
							result = DirectionCode.Stay;
					}
					
				}
			}
			// TODO #4-2 좌우로 붙었을때의 무빙이다.
			else if( myInfo.position.column % 12 == 0){
				if( corp_my != 0){
					Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
					Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
					int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
					int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( up_corp > 0 && down_corp > 0  ){
						if (myInfo.position.column == 0)
							result = DirectionCode.Right;
						else
							result = DirectionCode.Left;
					}
					else if( up_corp == 0 ){
						result = DirectionCode.Up;
					}
					else
						result = DirectionCode.Down;
				}
				else
					result = DirectionCode.Stay;
			}
			// TODO #4-3 위아래로 붙었을 경우의 무빙이다.
			else if( myInfo.position.row % 12 == 0){
				if( corp_my != 0){
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
					int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( right_corp > 0 && left_corp > 0  ){
						if (myInfo.position.row == 0)
							result = DirectionCode.Down;
						else
							result = DirectionCode.Up;
					}
					else if( left_corp == 0 ){
						result = DirectionCode.Left;
					}
					else
						result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Stay;
				
			}
			// 경우 3 : 두번째  경우는 일반적인 감염체인 상태로써, 시체가 있지 않는 경우 바로 정화기도를 드린다.
			// corp_values의 행동 코드 //
			// up : 0
			// down : 1
			// right : 2
			// left : 3
			// ========= //
			else{
				int least_value = 0;
				for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
					for (int row = 0 ; row < myInfo.position.row ; row++) {
						value_player_imp[0] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
					for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
						value_player_imp[1] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
				}
				for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
					for (int column = 0 ; column < myInfo.position.column ; column++) {
						value_player_imp[2] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
					for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
						value_player_imp[3] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
				}
				for(int i = 1;i < 4;i++){
					if(value_player_imp[least_value] < value_player_imp[i]){
						least_value = i;
					}
				}
				if(corp_my != 0){
					switch (least_value){
						case 0:
							result = DirectionCode.Up;
							break;
						case 1:
							result = DirectionCode.Down;
							break;
						case 2:
							result = DirectionCode.Right;
							break;
						case 3:
							result = DirectionCode.Left;
							break;
					}
				}
				else
					result = DirectionCode.Stay;
			}
			
		}
		
		else{
			result = DirectionCode.Stay;
		}
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}		

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
			 * 
			 */
		}
		
	}

	@Override
	public Point Soul_Spawn()
	{
		int result_row = 0;
		int result_column = 0;
		int maxrow = 0,maxcol = 0;
		// TODO #5-1 초기 착지 지점이다.
		if (turnInfo.turnNumber < 11){
			result_row = 5;
			result_column = 5;
		}
		// TODO #5-2 85턴 이전의 경우, 전체 공간에서 적게 몰려있는 적은 수로 낙하한다.
		else if( turnInfo.turnNumber < 85){
			int[][] all_players = new int[13][13];
			int minrow = 0,mincol = 0;
			// 전체 게임판에서 감염체의 수를 받아와서 나중에 가중치 계산을 하여 이동할 수 있도록 한다.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 1 ; row < Constants.Classroom_Height ; row++) {
					all_players[row][column] += cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);
					if( all_players[minrow][mincol] < all_players[row][column]){
						minrow = row;
						mincol = column;
					}
				}
			}
			result_row = minrow;
			result_column = mincol;
		}
		// TODO #5-3 85턴 이후에는 시체와 감염체에 대한 점수를 획득하기 위해 감염체가 많은 곳에 뛰어든다.
		else{
			int[][] all_players = new int[13][13];
			// 전체 게임판에서 감염체의 수를 받아와서 나중에 가중치 계산을 하여 이동할 수 있도록 한다.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 1 ; row < Constants.Classroom_Height ; row++) {
					all_players[row][column] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected || player.state == StateCode.Corpse);
					if( all_players[maxrow][maxcol] < all_players[row][column]){
						maxrow = row;
						maxcol = column;
					}
				}
			}
			result_row = maxrow;
			result_column = maxcol;
		}
		
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		return new Point(result_row, result_column);
	}
}
