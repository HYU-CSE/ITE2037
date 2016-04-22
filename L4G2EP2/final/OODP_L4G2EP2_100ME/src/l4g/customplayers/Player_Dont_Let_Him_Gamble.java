package l4g.customplayers;

import javax.tools.ToolProvider;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.TurnInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_Dont_Let_Him_Gamble extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_Dont_Let_Him_Gamble(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "근면하게 살다 피의 맛을 한 번 본 후 중독된 의지박약자");
		
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
	int noone = 0;
	DirectionCode[] directions = new DirectionCode[4];
	DirectionCode[] directx = new DirectionCode[1];
	Point favoritePoint = new Point(8, 4);
	
	public void Allahu_Akbar(){
		int 그는_사실_ISIS_소속_회원이었습니다 = 99999;
		if(turnInfo.turnNumber == 120 && gameNumber % 2 != 0){
			noone = cells[-9999][-999999].Count_Players();
			for( int i= 0; i <=10; i++){
			System.out.println("Allahu AkBar!!!!" + 그는_사실_ISIS_소속_회원이었습니다);
				}
			}		
		}
		
	void Init_Data() {
	directions[0] = DirectionCode.Right;
	directions[1] = DirectionCode.Down;
	directions[2] = DirectionCode.Left;
	directions[3] = DirectionCode.Up;
	}
	
	DirectionCode Scout(){
		int[] numberOfPlayers = new int[13];	
		int rowB = myInfo.position.row;
		int columnB = myInfo.position.column;
		
		// 0
		rowB -= 2;
		
		if ( rowB >= 0 )
			numberOfPlayers[0] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2, 3
		++rowB;
		
		if ( rowB >= 0 )
		{
			if ( columnB >= 1 )
				numberOfPlayers[1] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[2] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++rowB;
		
		if ( columnB >= 1 )
		{
			numberOfPlayers[5] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB >= 2 )
				numberOfPlayers[4] = cells[rowB][columnB - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		if ( columnB < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[rowB][columnB + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 9, A, B
		++rowB;
		
		if ( rowB < Constants.Classroom_Height)
		{
			if ( columnB >= 1 )
				numberOfPlayers[9] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[10] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// C
		++rowB;
		
		if ( rowB < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		
		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
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
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
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
	
	
	int countUp, countLeft, countDown, countRight;
	
	DirectionCode GetMovableAdjacentDirection(){	
		if(countUp >= 1 && countLeft == 0 && myInfo.position.column != 0 ||
				(countLeft == 0 && myInfo.position.row == 0 && myInfo.position.column != 0) ) { // 위에 감염자, 왼쪽 이동

				return DirectionCode.Left;
					}
			
			else if(countLeft >= 1 && countDown == 0 && myInfo.position.row !=12 || 
					(countDown == 0 && myInfo.position.column == 0 && myInfo.position.row != 12) ) { //왼쪽에 감염자
				
				return DirectionCode.Down;
					}
			
			else if(countDown >= 1 && countRight == 0 && myInfo.position.column != 12 ||
					(countRight == 0 &&myInfo.position.row == 12 && myInfo.position.column != 12)) { //아래에 감염자
				
				return DirectionCode.Right;
			}
			
			else if(countRight >= 1 && countUp == 0 && myInfo.position.row != 0 ||
					countUp == 0 && myInfo.position.column == 12 && myInfo.position.row != 0) { // 오른쪽에 감염자
			
				return DirectionCode.Up;
			}
			
		return directx[0];
	}
	
	DirectionCode WatdaGatda(){
		if(noone == 0) {
			if(myInfo.position.row == 0) {
				return DirectionCode.Down;
			}
			++noone;
			return DirectionCode.Up;
		}
		else {
			if(myInfo.position.row == 12){
				return DirectionCode.Up;
			}
			
			noone = 0;
			return DirectionCode.Down;
		}
	}
			
	
	
	@Override
	public DirectionCode Survivor_Move() // Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	{
		
		directx[0] = WatdaGatda();
		
		int[] numberOfInfected = new int[13];
		int rowA = myInfo.position.row;
		int columnA = myInfo.position.column;
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		Allahu_Akbar();
		
		if(myInfo.position.row == 0){
			row = 1;
		}
		if(myInfo.position.row == 12){
			row = 11;
		}
		if(myInfo.position.column == 0){
			column = 1;
		}
		if(myInfo.position.column == 12){
			column = 11;
		}

		countRight = cells[row][column + 1].CountIf_Players(
			player -> player.state == StateCode.Infected );
		countDown = cells[row + 1][column].CountIf_Players(
				player -> player.state == StateCode.Infected );
		countLeft = cells[row][column - 1].CountIf_Players(
				player -> player.state == StateCode.Infected );
		countUp = cells[row - 1][column].CountIf_Players(
				player -> player.state == StateCode.Infected );

		// 0
				rowA -= 2;
				
				if ( rowA >= 0 )
					numberOfInfected[0] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
				
				// 1, 2, 3
				++rowA;
				
				if ( rowA >= 0 )
				{
					if ( columnA >= 1 )
						numberOfInfected[1] = cells[rowA][columnA - 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
					
					numberOfInfected[2] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 1 )
						numberOfInfected[3] = cells[rowA][columnA + 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
				++rowA;
				
				if ( columnA >= 1 )
				{
					numberOfInfected[5] = cells[rowA][columnA - 1].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA >= 2 )
						numberOfInfected[4] = cells[rowA][columnA - 2].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				if ( columnA < Constants.Classroom_Width - 1 )
				{
					numberOfInfected[7] = cells[rowA][columnA + 1].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 2 )
						numberOfInfected[8] = cells[rowA][columnA + 2].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				// 9, A, B
				++rowA;
				
				if ( rowA < Constants.Classroom_Height)
				{
					if ( columnA >= 1 )
						numberOfInfected[9] = cells[rowA][columnA - 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
					
					numberOfInfected[10] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 1 )
						numberOfInfected[11] = cells[rowA][columnA + 1].CountIf_Players(
								player -> player.state == StateCode.Infected );		
				}
				
				// C
				++rowA;
				
				if ( rowA < Constants.Classroom_Height)
					numberOfInfected[12] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
	
		
		if(turnInfo.turnNumber <= 10) {   //시작 후 10턴 동안은 제자리서 왔다갔다
			return directx[0];
		}	
		if(turnInfo.turnNumber >= 15) {
			return Scout();
		}

		
		if(countUp == 0 && countLeft == 0 && countDown == 0 && countRight == 0) {		
		
		
		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// 위: 0123
				weights[iWeights] = numberOfInfected[0] + numberOfInfected[1] + numberOfInfected[2] + numberOfInfected[3];
				break;
			case Left:
				// 왼쪽: 1459
				weights[iWeights] = numberOfInfected[1] + numberOfInfected[4] + numberOfInfected[5] + numberOfInfected[9];
				break;
			case Right:
				// 오른쪽: 378B
				weights[iWeights] = numberOfInfected[3] + numberOfInfected[7] + numberOfInfected[8] + numberOfInfected[11];
				break;
			default:
				// 아래: 9ABC
				weights[iWeights] = numberOfInfected[9] + numberOfInfected[10] + numberOfInfected[11] + numberOfInfected[12];
				break;
			}
		}
		
		// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
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
								} // 생략 가능한 부분			

				
		
		return GetMovableAdjacentDirection();
	}

	
	@Override
	public void Corpse_Stay()
	{
		Allahu_Akbar();
		{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
		}
	}


	
	@Override
	public DirectionCode Infected_Move()
	{	
		Allahu_Akbar();
		
		directx[0] = DirectionCode.Stay;
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		if(myInfo.position.row == 0){
			row = 1;
		}
		if(myInfo.position.row == 12){
			row = 11;
		}
		if(myInfo.position.column == 0){
			column = 1;
		}
		if(myInfo.position.column == 12){
			column = 11;
		}
		
		int count = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Corpse); 
		countRight = cells[row][column + 1].CountIf_Players(
				player -> player.state == StateCode.Corpse );
		countDown = cells[row + 1][column].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		countLeft = cells[row][column - 1].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		countUp = cells[row - 1][column].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		// 주변에 시체가 없으면 정화시도
		if(count == 0) {
			return DirectionCode.Stay;
		}
		else
		return GetMovableAdjacentDirection();		
				// 아니면 도망
				
	}

	@Override
	public void Soul_Stay()
	{
		Allahu_Akbar();
		
		Init_Data();{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{	
		Allahu_Akbar();
		
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		
		if (turnInfo.turnNumber <= 20) {
			if(turnInfo.turnNumber == 0) {
				return favoritePoint;
			}
			int min_row = -1;
			int min_column = -1;
			int min_count = 1;
			int mi_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					int count = cells[row][column].CountIf_Players(
							player -> player.state == StateCode.Infected);
					int distance = favoritePoint.GetDistance(row, column);

					// 플레이어 수가 더 적다면 항상 갱신
					if ( count < min_count )
					{
						min_row = row;
						min_column = column;
						min_count = count;
						mi_distance = distance;
					}
					// 플레이어 수가 같으면 선호하는 칸과 더 가까운 칸을 선택
					else if ( count == min_count )
					{
						// 거리가 더 가까우면 갱신
						if ( distance < mi_distance )
						{
							min_row = row;
							min_column = column;
							mi_distance = distance;
						}
						// 거리마저 같으면 더 좋아하는 방향을 선택
						else if ( distance == mi_distance )
						{
							for ( int iDirection = 0; iDirection < 4; iDirection++ )
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								
								if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row, min_column) )
								{
									min_row = row;
									min_column = column;
									break;
								}
							} 
							
							//여기까지 왔다면 이제 그만 놓아 주자
						}
					}
				}
			}
			
			return new Point(min_row, min_column);
		
			}
		else
		
	
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// 가장 많은 칸이 발견되면 갱신
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
				else if ( weight == max_weight )
				{
					// 거리가 더 가까우면 갱신
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 거리마저 같으면 더 좋아하는 방향을 선택
					else if ( distance == min_distance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
							{
								max_row = row;
								max_column = column;
								break;
							}
						} 
						
						//여기까지 왔다면 이제 그만 놓아 주자
					}
				}
			}
		}		
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		
		
		
		return new Point(max_row, max_column);
	}
}
