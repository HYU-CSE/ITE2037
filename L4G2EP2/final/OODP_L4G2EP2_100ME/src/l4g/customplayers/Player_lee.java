package l4g.customplayers;

import java.awt.PointerInfo;

import l4g.PlayerStat;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;
import l4g.data.TurnInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_lee extends Player {

	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_lee( int ID ) {
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "su");
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.
	}

	DirectionCode[] directions = new DirectionCode [4];

	Point favoritePoint = new Point(6, 6);

	void Init_Data( ) {

		long seed = (ID + 2016) * gameNumber + ID;
		if ( seed < 0 ) seed = -seed;
		// 그 수를 24로 나눈 나머지를 토대로 방향 우선순위 제작
		switch ( (int) (seed % 24) ) {
		case 0 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 1 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 2 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 3 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 4 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 5 :
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 6 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 7 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 8 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 9 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 10 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 11 :
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 12 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 13 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 14 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 15 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 16 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 17 :
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		case 18 :
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 19 :
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 20 :
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 21 :
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 22 :
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 23 :
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		}
		favoritePoint.row = (int) (seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int) (seed % Constants.Classroom_Height);
	}

	DirectionCode GetMovableAdjacentDirection( ) {

		int iDirection;
		for ( iDirection = 0 ; iDirection < 4 ; iDirection++ ) {
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		return directions[iDirection];
	}

	int[][] move = { { 0 , 1 } , { 0 , -1 } , { -1 , 0 } , { 1 , 0 } , { 0 , 0 } };

	int[] before_infected_total = { 0 , 0 , 0 , 0 };

	Point my_point , init_my_point; // 현재 위치와 초기 위치

	Point[] infected_point; // 시야내의 감염체들의 좌표를 저장

	int num , want_state = 0;

	int[] where = { 0 , 0 , 0 , 0 };

	private boolean isIn( int x , int y ) {

		return x >= 0 && x < Constants.Classroom_Height && y >= 0 && y < Constants.Classroom_Width;
	} // 좌표가 보드 안에 있는지 확인

	private boolean IsSameSign( int x , int y ) {

		return (x < 0 && y <= 0) || (x > 0 && y >= 0) || (x == 0 && y == 0);
	}

	private int makeWeight( int idx , Point init_Point , int turn_num ) {//저장한 좌표들의 경우의 수를 만들어주는 메소드

		if ( idx == num && turn_num == 1 ) {//두 턴 돌면 감염체들과의 위치에 따라 가중치 설정
			int[] is_infected_direction = { 0 , 0 , 0 , 0 };
			int weight = 4;
			for ( int i = 0 ; i < num ; i++ )
				if ( infected_point[i].row == my_point.row - 1 && infected_point[i].column == my_point.column )
					is_infected_direction[0] = 1;
				else if ( infected_point[i].row == my_point.row + 1 && infected_point[i].column == my_point.column )
					is_infected_direction[1] = 1;
				else if ( infected_point[i].row == my_point.row && infected_point[i].column == my_point.column - 1 )
					is_infected_direction[2] = 1;
				else if ( infected_point[i].row == my_point.row && infected_point[i].column == my_point.column + 1 )
					is_infected_direction[3] = 1;
			for ( int i = 0 ; i < 4 ; i++ )
				weight -= is_infected_direction[i];
			return weight;
		}
		else if ( idx == num ) {//한 턴 돌 았을 경우
			int toTal = 0;
			Point new_point = new Point(my_point.row, my_point.column);
			for ( int i = 0 ; i < 4 ; i++ ) {
				int row1 = my_point.row + move[i][0] , column1 = my_point.column + move[i][1];
				if ( isIn(row1, column1) ) {
					my_point.row = row1;
					my_point.column = column1;
					toTal += makeWeight(0, new_point, turn_num + 1);
					my_point.row -= move[i][0];
					my_point.column -= move[i][1];
				}
			}
			return toTal;
		}
		int total = 0;
		for ( int k = 0 ; k < 5 ; k++ ) {
			int row1 = infected_point[idx].row + move[k][0] , column1 = infected_point[idx].column + move[k][1];
			if ( isIn(row1, column1) && IsSameSign(init_Point.row - infected_point[idx].row, move[k][0])
					&& IsSameSign(init_Point.column - infected_point[idx].column, move[k][1]) ) {//감염체는 나만 쫓아 오게 설정
				infected_point[idx].row = row1;
				infected_point[idx].column = column1;
				if ( row1 != my_point.row || column1 != my_point.column ) //감염체와 나와 좌표가 같은지 확인
					total += makeWeight(idx + 1, init_Point, turn_num);
				infected_point[idx].row -= move[k][0];
				infected_point[idx].column -= move[k][1];
			}
		}
		return total;
	}

	@Override
	public DirectionCode Survivor_Move( ) {

		num = 0;// 저장할 감염자 좌표 수 
		infected_point = new Point [10];
		my_point = myInfo.position.Copy();
		init_my_point = myInfo.position.Copy();
		int[] favorite_distance = { 100 , 100 , 100 , 100 }; // 선호하는 좌표와의 거리
		Point init_Point = new Point(my_point.row, my_point.column);
		for ( int row = 0 ; row < Constants.Classroom_Height ; ++row )
			for ( int column = 0 ; column < Constants.Classroom_Width ; ++column )
				if ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected) > 0
						&& my_point.GetDistance(row, column) <= 2 && num < 7 )
					infected_point[num++] = new Point(row, column);
		// 현재위치와 거리
		// 2이하 인곳에
		// 감염자가 있을 경우 저장
		int[] weight = { 0 , 0 , 0 , 0 } , distance = { 0 , 0 , 0 , 0 };
		for ( int iWeights = 0 ; iWeights < 4 ; iWeights++ ) {
			switch ( directions[iWeights] ) {
			case Up :
				where[iWeights] = 2;
				break;
			case Left :
				where[iWeights] = 1;
				break;
			case Right :
				where[iWeights] = 0;
				break;
			case Down :
				where[iWeights] = 3;
				break;
			}
		}
		for ( int iWeights = 0 ; iWeights < 4 ; iWeights++ ) {
			int row1 = my_point.row + move[where[iWeights]][0] , column1 = my_point.column + move[where[iWeights]][1];//이동할 좌표 설정
			if ( isIn(row1, column1) ) {
				my_point.row = row1;
				my_point.column = column1;
				weight[where[iWeights]] = makeWeight(0, init_Point, 0);
				int height = my_point.row < Constants.Classroom_Height - 1 - my_point.row ? my_point.row
						: Constants.Classroom_Height - 1 - my_point.row;
				int width = my_point.column < Constants.Classroom_Width - 1 - my_point.column ? my_point.column
						: Constants.Classroom_Width - 1 - my_point.column;
				distance[where[iWeights]] = height < width ? height : width;//벽에서 떨어진 정도
				favorite_distance[where[iWeights]] = my_point.GetDistance(favoritePoint.row, favoritePoint.column);
				my_point.row -= move[where[iWeights]][0];
				my_point.column -= move[where[iWeights]][1];
			}
			else weight[where[iWeights]] = -1; // 벽밖으로 나가면 -1로 설정
		}
		
		int max_weight = -1;
		int max_idx_weights = 0;
		for ( int iWeights = 0 ; iWeights < 4 ; iWeights++ )
			if ( turnInfo.turnNumber <= 10 ) { //10턴 전에는 선호하는 위치로 이동
				if ( favorite_distance[where[iWeights]] < favorite_distance[where[max_idx_weights]] ) 
					max_idx_weights = iWeights;
			}
			else { //10턴 이후 가중치가 가장 큰 곳으로 이동
				if ( weight[where[iWeights]] > max_weight ) {
					max_weight = weight[where[iWeights]];
					max_idx_weights = iWeights;
				}
				else if ( weight[where[iWeights]] == max_weight //가중치가 같다면 감염자가 가장 적은곳으로 이동
						&& before_infected_total[where[iWeights]] < before_infected_total[where[max_idx_weights]] )
					max_idx_weights = iWeights;
				else if ( weight[where[iWeights]] == max_weight//가중치가 같고 감염자가 수도 같다면 벽에서 가장 떨어진 곳으로 이동
						&& before_infected_total[where[iWeights]] == before_infected_total[where[max_idx_weights]]
						&& distance[where[max_idx_weights]] <  distance[where[iWeights]] )
					max_idx_weights = iWeights;
			}
		
		for ( int i = 0 ; i < 4 ; i++ )
			before_infected_total[i] = 0;
		for ( int row = 0 ; row < Constants.Classroom_Height ; ++row )
			for ( int column = 0 ; column < Constants.Classroom_Width ; ++column ) //각 방향의 감염자 수 갱신
				if ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected) > 0 ) {
					if ( column > init_my_point.column ) before_infected_total[0]++;
					if ( column < init_my_point.column ) before_infected_total[1]++;
					if ( row < init_my_point.row ) before_infected_total[2]++;
					if ( row > init_my_point.row ) before_infected_total[3]++;
				}
		return directions[max_idx_weights];
	}

	@Override
	public void Corpse_Stay( ) {

	}

	@Override
	public DirectionCode Infected_Move( ) {

		// 내 밑에 시체가 깔려 있으면 도망감
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		// 그렇지 않으면 정화 기도 시도
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay( ) {

		if ( turnInfo.turnNumber == 0 ) {
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn( ) {

		if ( want_state == 0 ) {
			want_state = 1;
			return favoritePoint;
		}
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
		for ( int row = 0 ; row < Constants.Classroom_Height ; row++ ) {
			for ( int column = 0 ; column < Constants.Classroom_Width ; column++ ) {
				CellInfo cell = this.cells[row][column];
				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
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
						for ( int iDirection = 0 ; iDirection < 4 ; iDirection++ ) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
									max_column) ) {
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
		if ( max_weight == 0 ) {
			int variableToMakeError = 0;
			variableToMakeError = variableToMakeError / variableToMakeError;
		}
		return new Point(max_row, max_column);
	}
}
