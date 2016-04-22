package l4g.customplayers;

import java.security.Guard;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import l4g.Grader;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Ori extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_Ori(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "Ori");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.

	}

	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
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
			// �ð����
			if ( pos_r > 6 ) {
				if ( pos_c > 6 ) {
					// 4��и�
					directions[0] = DirectionCode.Left;
					directions[1] = DirectionCode.Up;
					directions[2] = DirectionCode.Right;
					directions[3] = DirectionCode.Down;
				} else {
					// 3��и�
					directions[0] = DirectionCode.Up;
					directions[1] = DirectionCode.Right;
					directions[2] = DirectionCode.Left;
					directions[3] = DirectionCode.Down;
				}
			} else {
				if ( pos_c > 6 ) {
					// 2��и�
					directions[0] = DirectionCode.Down;
					directions[1] = DirectionCode.Left;
					directions[2] = DirectionCode.Right;
					directions[3] = DirectionCode.Up;
				} else {
					// 1��и�
					directions[0] = DirectionCode.Right;
					directions[1] = DirectionCode.Down;
					directions[2] = DirectionCode.Left;
					directions[3] = DirectionCode.Up;
				}
			}
		}

	}

	DirectionCode GetMovableAdjacentDirection() {
		// ��ȿ�� ��ġ���� Ȯ��
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
		 * ������ �̵�: ����ü���� ���� ���� �������� �̵��մϴ�.
		 */
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

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

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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

		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			switch ( directions[iWeights] ) {
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
				break;
			}
		}
		
		if(this.myScore.survivor_max > 60 || this.myScore.survivor_total > 600){
			// ������+��ü ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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
		// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		if(turnInfo.turnNumber == 120){
			int make_error = 0;
			make_error = make_error/make_error;
		}
		SetPrior();
		/*
		 * ����ü�̵�
		 */
		int[] numberOfPlayers = new int[25];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 25���� ��쿡 ���� �÷��̾� �� ���

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

		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
		int[] weights = new int[4];
		// ����ġ �̿�
		for ( int iWeights = 0; iWeights < 4; iWeights++ ) {
			switch ( directions[iWeights] ) {
			case Up:
				// ��: 01234
				// 678
				weights[iWeights] = 2 * numberOfPlayers[0] + numberOfPlayers[1] + 3 * numberOfPlayers[2]
						+ numberOfPlayers[3] + 2 * numberOfPlayers[4] + 3 * numberOfPlayers[6] + numberOfPlayers[7]
						+ 3 * numberOfPlayers[8];
				break;
			case Left:
				// ����: 0 5 10 15 20
				// 6 11 16
				weights[iWeights] = 2 * numberOfPlayers[0] + numberOfPlayers[5] + 3 * numberOfPlayers[10]
						+ numberOfPlayers[15] + 2 * numberOfPlayers[20] + 3 * numberOfPlayers[6] + numberOfPlayers[11]
						+ 3 * numberOfPlayers[16];
				break;
			case Right:
				// ������: 4 9 14 19 24
				// 8 13 18
				weights[iWeights] = 2 * numberOfPlayers[4] + numberOfPlayers[9] + 3 * numberOfPlayers[14]
						+ numberOfPlayers[19] + 2 * numberOfPlayers[24] + 3 * numberOfPlayers[8] + numberOfPlayers[13]
						+ 3 * numberOfPlayers[18];
				break;
			default:
				// �Ʒ�: 20 21 22 23 24
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
		// ����ġ�� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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

		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

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
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
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

		// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
		for ( int row = 1; row < Constants.Classroom_Height - 1; row++ ) {
			for ( int column = 1; column < Constants.Classroom_Width - 1; column++ ) {
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

				// ���� 8ĭ - ��ü
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
				// ���� 8ĭ - ����ü
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
				// ���� 8ĭ - ����ü

				// ����ġ ����
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + 2 * numberOfInfecteds : 0;
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
						for ( int iDirection = 0; iDirection < 4; iDirection++ ) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
									max_column) ) {
								max_row = row;
								max_column = column;
								break;
							}
						}

						// ������� �Դٸ� ���� �׸� ���� ���� 2
					}
				}
				
				
			}
		}

		// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ȣ ��ġ ����
		if ( max_weight == 0 ) {
			return favoritePoint;
		}
		return new Point(max_row, max_column);
	}
	
	// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

}
