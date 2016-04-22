package l4g.customplayers;

import java.awt.PointerInfo;

import l4g.PlayerStat;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;
import l4g.data.TurnInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_lee extends Player {

	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_lee( int ID ) {
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "su");
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.
	}

	DirectionCode[] directions = new DirectionCode [4];

	Point favoritePoint = new Point(6, 6);

	void Init_Data( ) {

		long seed = (ID + 2016) * gameNumber + ID;
		if ( seed < 0 ) seed = -seed;
		// �� ���� 24�� ���� �������� ���� ���� �켱���� ����
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

	Point my_point , init_my_point; // ���� ��ġ�� �ʱ� ��ġ

	Point[] infected_point; // �þ߳��� ����ü���� ��ǥ�� ����

	int num , want_state = 0;

	int[] where = { 0 , 0 , 0 , 0 };

	private boolean isIn( int x , int y ) {

		return x >= 0 && x < Constants.Classroom_Height && y >= 0 && y < Constants.Classroom_Width;
	} // ��ǥ�� ���� �ȿ� �ִ��� Ȯ��

	private boolean IsSameSign( int x , int y ) {

		return (x < 0 && y <= 0) || (x > 0 && y >= 0) || (x == 0 && y == 0);
	}

	private int makeWeight( int idx , Point init_Point , int turn_num ) {//������ ��ǥ���� ����� ���� ������ִ� �޼ҵ�

		if ( idx == num && turn_num == 1 ) {//�� �� ���� ����ü����� ��ġ�� ���� ����ġ ����
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
		else if ( idx == num ) {//�� �� �� ���� ���
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
					&& IsSameSign(init_Point.column - infected_point[idx].column, move[k][1]) ) {//����ü�� ���� �Ѿ� ���� ����
				infected_point[idx].row = row1;
				infected_point[idx].column = column1;
				if ( row1 != my_point.row || column1 != my_point.column ) //����ü�� ���� ��ǥ�� ������ Ȯ��
					total += makeWeight(idx + 1, init_Point, turn_num);
				infected_point[idx].row -= move[k][0];
				infected_point[idx].column -= move[k][1];
			}
		}
		return total;
	}

	@Override
	public DirectionCode Survivor_Move( ) {

		num = 0;// ������ ������ ��ǥ �� 
		infected_point = new Point [10];
		my_point = myInfo.position.Copy();
		init_my_point = myInfo.position.Copy();
		int[] favorite_distance = { 100 , 100 , 100 , 100 }; // ��ȣ�ϴ� ��ǥ���� �Ÿ�
		Point init_Point = new Point(my_point.row, my_point.column);
		for ( int row = 0 ; row < Constants.Classroom_Height ; ++row )
			for ( int column = 0 ; column < Constants.Classroom_Width ; ++column )
				if ( cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected) > 0
						&& my_point.GetDistance(row, column) <= 2 && num < 7 )
					infected_point[num++] = new Point(row, column);
		// ������ġ�� �Ÿ�
		// 2���� �ΰ���
		// �����ڰ� ���� ��� ����
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
			int row1 = my_point.row + move[where[iWeights]][0] , column1 = my_point.column + move[where[iWeights]][1];//�̵��� ��ǥ ����
			if ( isIn(row1, column1) ) {
				my_point.row = row1;
				my_point.column = column1;
				weight[where[iWeights]] = makeWeight(0, init_Point, 0);
				int height = my_point.row < Constants.Classroom_Height - 1 - my_point.row ? my_point.row
						: Constants.Classroom_Height - 1 - my_point.row;
				int width = my_point.column < Constants.Classroom_Width - 1 - my_point.column ? my_point.column
						: Constants.Classroom_Width - 1 - my_point.column;
				distance[where[iWeights]] = height < width ? height : width;//������ ������ ����
				favorite_distance[where[iWeights]] = my_point.GetDistance(favoritePoint.row, favoritePoint.column);
				my_point.row -= move[where[iWeights]][0];
				my_point.column -= move[where[iWeights]][1];
			}
			else weight[where[iWeights]] = -1; // �������� ������ -1�� ����
		}
		
		int max_weight = -1;
		int max_idx_weights = 0;
		for ( int iWeights = 0 ; iWeights < 4 ; iWeights++ )
			if ( turnInfo.turnNumber <= 10 ) { //10�� ������ ��ȣ�ϴ� ��ġ�� �̵�
				if ( favorite_distance[where[iWeights]] < favorite_distance[where[max_idx_weights]] ) 
					max_idx_weights = iWeights;
			}
			else { //10�� ���� ����ġ�� ���� ū ������ �̵�
				if ( weight[where[iWeights]] > max_weight ) {
					max_weight = weight[where[iWeights]];
					max_idx_weights = iWeights;
				}
				else if ( weight[where[iWeights]] == max_weight //����ġ�� ���ٸ� �����ڰ� ���� ���������� �̵�
						&& before_infected_total[where[iWeights]] < before_infected_total[where[max_idx_weights]] )
					max_idx_weights = iWeights;
				else if ( weight[where[iWeights]] == max_weight//����ġ�� ���� �����ڰ� ���� ���ٸ� ������ ���� ������ ������ �̵�
						&& before_infected_total[where[iWeights]] == before_infected_total[where[max_idx_weights]]
						&& distance[where[max_idx_weights]] <  distance[where[iWeights]] )
					max_idx_weights = iWeights;
			}
		
		for ( int i = 0 ; i < 4 ; i++ )
			before_infected_total[i] = 0;
		for ( int row = 0 ; row < Constants.Classroom_Height ; ++row )
			for ( int column = 0 ; column < Constants.Classroom_Width ; ++column ) //�� ������ ������ �� ����
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

		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		// �׷��� ������ ��ȭ �⵵ �õ�
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
		// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
		for ( int row = 0 ; row < Constants.Classroom_Height ; row++ ) {
			for ( int column = 0 ; column < Constants.Classroom_Width ; column++ ) {
				CellInfo cell = this.cells[row][column];
				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);
				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( weight > max_weight ) {
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( weight == max_weight ) {
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance ) {
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
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
						// ������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ġ ����
		if ( max_weight == 0 ) {
			int variableToMakeError = 0;
			variableToMakeError = variableToMakeError / variableToMakeError;
		}
		return new Point(max_row, max_column);
	}
}
