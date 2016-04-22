package l4g.customplayers;

import java.util.ArrayList;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_이름할게없다 extends Player {
	
	
	public Player_이름할게없다(int ID) {
		
		super(ID, "이름할게없다");
		
		this.trigger_acceptDirectInfection = true;
		
	}
	int[] preferDirec = new int[4];
	int turningPoint = 20;
	int corpsetime = 100;
	int[][] tmp2 = new int[13][13];
	
	DirectionCode[] directions = new DirectionCode[4];
	
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
	
	DirectionCode GetMovableAdjacentDirection() {
		int iDirection;
		
		for (iDirection = 0; iDirection < 4; iDirection++){
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break; // 만약 넘어간다면 break 되고 리턴값이 만약 directions[4]라면 전부 movable,
						// 4이하면 그곳은 못간다는거
		}
		
		return directions[iDirection];
	}
	
	@Override
	public DirectionCode Survivor_Move() {
		if (turnInfo.turnNumber == 11){
			
		}
		for (int i = 0; i < 4; i++){
			preferDirec[i] = 0;
		}
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		int[] numberOfSurv = new int[13];
		int[] numberOfInf = new int[13];
		// 위에 보이는 13가지 경우에 대한 플레이어 수 기록
		
		/*
		 * 0 123 45678 9AB C
		 */
		row -= 2;
		// 0
		if (row >= 0) // 벽에 안붙어있다면
			numberOfSurv[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2, 3
		++row;
		
		if (row >= 0){
			if (column >= 1)
				numberOfSurv[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfSurv[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if (column < Constants.Classroom_Width - 1)
				numberOfSurv[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;
		
		if (column >= 1){
			numberOfSurv[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if (column >= 2)
				numberOfSurv[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		if (column < Constants.Classroom_Width - 1){
			numberOfSurv[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if (column < Constants.Classroom_Width - 2)
				numberOfSurv[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 9, A, B
		++row;
		
		if (row < Constants.Classroom_Height){
			if (column >= 1)
				numberOfSurv[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfSurv[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if (column < Constants.Classroom_Width - 1)
				numberOfSurv[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// C
		++row;
		
		if (row < Constants.Classroom_Height)
			numberOfSurv[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		row = myInfo.position.row;
		
		row -= 2;
		
		if (row >= 0) // 벽에 안붙어있다면
			numberOfInf[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		
		// 1, 2, 3
		++row;
		
		if (row >= 0){
			if (column >= 1)
				numberOfInf[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			numberOfInf[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if (column < Constants.Classroom_Width - 1)
				numberOfInf[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;
		
		if (column >= 1){
			numberOfInf[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if (column >= 2)
				numberOfInf[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		if (column < Constants.Classroom_Width - 1){
			numberOfInf[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if (column < Constants.Classroom_Width - 2)
				numberOfInf[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 9, A, B
		++row;
		
		if (row < Constants.Classroom_Height){
			if (column >= 1)
				numberOfInf[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			numberOfInf[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if (column < Constants.Classroom_Width - 1)
				numberOfInf[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// C
		++row;
		
		if (row < Constants.Classroom_Height)
			numberOfInf[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		
		// 우선순위 일단 정하기
		int[] weightsofSurv = new int[4];
		int[] weightsofInf = new int[4];
		
		for (int iWeights = 0; iWeights < 4; iWeights++){
			switch (directions[iWeights]){
				case Up:
					// 위: 0123
					weightsofSurv[iWeights] = numberOfSurv[0] + numberOfSurv[1] + numberOfSurv[2] + numberOfSurv[3];
					break;
				case Left:
					// 왼쪽: 1459
					weightsofSurv[iWeights] = numberOfSurv[1] + numberOfSurv[4] + numberOfSurv[5] + numberOfSurv[9];
					break;
				case Right:
					// 오른쪽: 378B
					weightsofSurv[iWeights] = numberOfSurv[3] + numberOfSurv[7] + numberOfSurv[8] + numberOfSurv[11];
					break;
				default:
					// 아래: 9ABC
					weightsofSurv[iWeights] = numberOfSurv[9] + numberOfSurv[10] + numberOfSurv[11] + numberOfSurv[12];
					break;
			}
		}
		for (int iWeights = 0; iWeights < 4; iWeights++){
			switch (directions[iWeights]){
				case Up:
					// 위: 0123
					weightsofInf[iWeights] = numberOfInf[0] + numberOfInf[1] + numberOfInf[2] + numberOfInf[3];
					break;
				case Left:
					// 왼쪽: 1459
					weightsofInf[iWeights] = numberOfInf[1] + numberOfInf[4] + numberOfInf[5] + numberOfInf[9];
					break;
				case Right:
					// 오른쪽: 378B
					weightsofInf[iWeights] = numberOfInf[3] + numberOfInf[7] + numberOfInf[8] + numberOfInf[11];
					break;
				default:
					// 아래: 9ABC
					weightsofInf[iWeights] = numberOfInf[9] + numberOfInf[10] + numberOfInf[11] + numberOfInf[12];
					break;
			}
		}
		for (int i = 0; i < 4; i++){
			preferDirec[i] += weightsofSurv[i];
			preferDirec[i] -= weightsofInf[i];
		}
		
		int maxprefer = preferDirec[0];
		int preferindex = 0;
		int nextpreferi = preferindex;
		int thirdprefer = nextpreferi;
		for (int i = 1; i < 4; i++){
			if (preferDirec[i] >= maxprefer){
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[i]);
				
				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width){
					thirdprefer = nextpreferi;
					nextpreferi = preferindex;
					maxprefer = preferDirec[i];
					preferindex = i;
				}
			}
		}
		int flag = 0;
		while (flag < 3){
			
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[preferindex]);
			if (preferDirec[preferindex] == 2){
				switch (directions[preferindex]){
					case Up:
						if (numberOfInf[2] == 1 || (numberOfInf[0] == 0 && numberOfInf[1] == 0 && numberOfInf[3] == 0))
							if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
									&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
							return directions[preferindex];
							
							else{
							preferindex = nextpreferi;
							nextpreferi = thirdprefer;
							}
						break;
					case Left:
						if (numberOfInf[5] == 1 || (numberOfInf[1] == 0 && numberOfInf[4] == 0 && numberOfInf[9] == 0))
							if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
									&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
							return directions[preferindex];
							else{
							preferindex = nextpreferi;
							nextpreferi = thirdprefer;
							}
						break;
					case Right:
						if (numberOfInf[7] == 1 || (numberOfInf[3] == 0 && numberOfInf[8] == 0 && numberOfInf[11] == 0))
							if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
									&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
							return directions[preferindex];
							else{
							preferindex = nextpreferi;
							nextpreferi = thirdprefer;
							}
						break;
					default:
						if (numberOfInf[10] == 1
								|| (numberOfInf[9] == 0 && numberOfInf[11] == 0 && numberOfInf[12] == 0))
							if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
									&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
							return directions[preferindex];
							else{
							preferindex = nextpreferi;
							nextpreferi = thirdprefer;
							}
						break;
				}
			}
			else if (preferDirec[preferindex] > 0)
				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
				return directions[preferindex];
				else if (preferDirec[preferindex] == 0){
				if (cells[adjacentPoint.row][adjacentPoint.column].Count_Players() == 0) if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
				return directions[preferindex];
				else if (cells[adjacentPoint.row][adjacentPoint.column].CountIf_Players(player -> player.state == StateCode.Infected) == 1) if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
				return directions[preferindex];
				else{
				preferindex = nextpreferi;
				nextpreferi = thirdprefer;
				}
				}
				else if (preferDirec[preferindex] < 0){
				if ((cells[adjacentPoint.row][adjacentPoint.column].CountIf_Players(player -> player.state == StateCode.Infected)) == 1) if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width)
				return directions[preferindex];
				else{
				preferindex = nextpreferi;
				nextpreferi = thirdprefer;
				}
				}
				
			flag++;
		}
		return GetMovableAdjacentDirection();
		
	}
	
	@Override
	public void Corpse_Stay() {
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}
	
	@Override
	public DirectionCode Infected_Move() {
		ArrayList<PlayerInfo> corpsePrefer = new ArrayList<>();
		for (CellInfo[] columns : cells)
			for (CellInfo cell : columns)
				corpsePrefer.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
		for (PlayerInfo corpsetmp : corpsePrefer) // 시체가 얼마나 자주 생기는 지 빈도 수 계산
			tmp2[corpsetmp.position.row][corpsetmp.position.column]++;
		if (turnInfo.turnNumber < corpsetime){
			if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
				return GetMovableAdjacentDirection();
			
			else
				return DirectionCode.Stay;
		}
		
		else{
			if (this.cells[myInfo.position.row][myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
				return DirectionCode.Stay;
			
			ArrayList<PlayerInfo> corpses = new ArrayList<>();
			
			// 모든 칸을 조사하여 강의실에 있는 모든 시체들에 대한 목록을 만듦
			for (CellInfo[] rows : cells)
				for (CellInfo cell : rows)
					corpses.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
			// TODO
			// 이동 가능한 옆 칸들에 대해, '가장 시체와 가까이 있을 수 있는 칸을 선택
			int min_weight = Integer.MAX_VALUE;
			int min_idx_directions = 0;
			
			for (int iDirection = 0; iDirection < 4; iDirection++){
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
				
				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width){
					int weight = Integer.MAX_VALUE - 1;
					
					for (PlayerInfo corpse : corpses){
						int distance = corpse.position.GetDistance(adjacentPoint);
						if (distance < weight) weight = distance;
					}
					
					if (weight < min_weight){
						min_weight = weight;
						min_idx_directions = iDirection;
					}
				}
			}
			return directions[min_idx_directions];
		}
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		
	}
	
	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0){
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
		}
		else{
			ArrayList<PlayerInfo> corpsePrefer = new ArrayList<>();
			for (CellInfo[] columns : cells)
				for (CellInfo cell : columns)
					corpsePrefer.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
			for (PlayerInfo corpsetmp : corpsePrefer) // 시체가 얼마나 자주 생기는 지 빈도 수
														// 계산
				tmp2[corpsetmp.position.row][corpsetmp.position.column]++;
		}
	}
	
	@Override
	public Point Soul_Spawn() {
		if (turnInfo.turnNumber == 0){
			return new Point(7, 7);
		}
		else if (turnInfo.turnNumber < turningPoint){
			int max_weight = 0;
			int max_row = 0;
			int max_column = 0;
			int danger = 0;
			for (int row = 0; row < Constants.Classroom_Height; row++){
				for (int column = 0; column < Constants.Classroom_Width; column++){
					CellInfo cell = this.cells[row][column];
					
					int numberOfSurv = cell.CountIf_Players(player -> player.state == StateCode.Survivor);
					int numberOfInf = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					int weight = numberOfSurv;
					
					// 가장 많은 칸이 발견되면 갱신
					if (weight > max_weight){
						if (numberOfInf == 0){
							danger = 0;
							for (int rowtmp = row - 1; rowtmp < row + 2; rowtmp++){
								for (int coltmp = column - 1; coltmp < column + 2; coltmp++){
									if ((cells[rowtmp][coltmp]
											.CountIf_Players(player -> player.state == StateCode.Infected)) >= 1){
										danger++;
									}
								}
							}
							if (danger <= 4){
								max_weight = weight;
								max_row = row;
								max_column = column;
							}
						}
						
					}
					// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
				}
			}
			return new Point(max_row, max_column);
		}
		else{
			int maxtmp = 0;
			int max_row = 0;
			int max_column = 0;
			for (int r = 0; r < Constants.Classroom_Height; r++){
				for (int c = 0; c < Constants.Classroom_Width; c++){
					if (tmp2[r][c] > maxtmp){
						maxtmp = tmp2[r][c];
						max_row = r;
						max_column = c;
					}
				}
				
			}
			if ((cells[max_row][max_column].CountIf_Players(player -> player.state == StateCode.Infected)) == 1)
				return new Point(max_row, max_column);
			else{
				Point favoritePoint = new Point(7, 6);
				int max_weight = 0;
				int max_row2 = -1;
				int max_column2 = -1;
				int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
				
				// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
				for (int row = 0; row < Constants.Classroom_Height; row++){
					for (int column = 0; column < Constants.Classroom_Width; column++){
						CellInfo cell = this.cells[row][column];
						
						int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
						int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
						
						int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
						int distance = favoritePoint.GetDistance(row, column);
						
						// 가장 많은 칸이 발견되면 갱신
						if (weight > max_weight){
							max_weight = weight;
							max_row2 = row;
							max_column2 = column;
							min_distance = distance;
						}
						// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
						else if (weight == max_weight){
							// 거리가 더 가까우면 갱신
							if (distance < min_distance){
								max_row2 = row;
								max_column2 = column;
								min_distance = distance;
							}
							// 거리마저 같으면 더 좋아하는 방향을 선택
							else if (distance == min_distance){
								for (int iDirection = 0; iDirection < 4; iDirection++){
									Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
									
									if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
											max_column)){
										max_row2 = row;
										max_column2 = column;
										break;
									}
								}
								
								// 여기까지 왔다면 이제 그만 놓아 주자
							}
						}
					}
				}
				return new Point(max_row2, max_column2);
			}
		}
	}
}