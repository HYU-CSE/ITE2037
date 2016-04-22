package l4g.customplayers;

import java.util.ArrayList;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_�̸��ҰԾ��� extends Player {
	
	
	public Player_�̸��ҰԾ���(int ID) {
		
		super(ID, "�̸��ҰԾ���");
		
		this.trigger_acceptDirectInfection = true;
		
	}
	int[] preferDirec = new int[4];
	int turningPoint = 20;
	int corpsetime = 100;
	int[][] tmp2 = new int[13][13];
	
	DirectionCode[] directions = new DirectionCode[4];
	
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
	
	DirectionCode GetMovableAdjacentDirection() {
		int iDirection;
		
		for (iDirection = 0; iDirection < 4; iDirection++){
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break; // ���� �Ѿ�ٸ� break �ǰ� ���ϰ��� ���� directions[4]��� ���� movable,
						// 4���ϸ� �װ��� �����ٴ°�
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
		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���
		
		/*
		 * 0 123 45678 9AB C
		 */
		row -= 2;
		// 0
		if (row >= 0) // ���� �Ⱥپ��ִٸ�
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
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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
		
		if (row >= 0) // ���� �Ⱥپ��ִٸ�
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
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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
		
		// �켱���� �ϴ� ���ϱ�
		int[] weightsofSurv = new int[4];
		int[] weightsofInf = new int[4];
		
		for (int iWeights = 0; iWeights < 4; iWeights++){
			switch (directions[iWeights]){
				case Up:
					// ��: 0123
					weightsofSurv[iWeights] = numberOfSurv[0] + numberOfSurv[1] + numberOfSurv[2] + numberOfSurv[3];
					break;
				case Left:
					// ����: 1459
					weightsofSurv[iWeights] = numberOfSurv[1] + numberOfSurv[4] + numberOfSurv[5] + numberOfSurv[9];
					break;
				case Right:
					// ������: 378B
					weightsofSurv[iWeights] = numberOfSurv[3] + numberOfSurv[7] + numberOfSurv[8] + numberOfSurv[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weightsofSurv[iWeights] = numberOfSurv[9] + numberOfSurv[10] + numberOfSurv[11] + numberOfSurv[12];
					break;
			}
		}
		for (int iWeights = 0; iWeights < 4; iWeights++){
			switch (directions[iWeights]){
				case Up:
					// ��: 0123
					weightsofInf[iWeights] = numberOfInf[0] + numberOfInf[1] + numberOfInf[2] + numberOfInf[3];
					break;
				case Left:
					// ����: 1459
					weightsofInf[iWeights] = numberOfInf[1] + numberOfInf[4] + numberOfInf[5] + numberOfInf[9];
					break;
				case Right:
					// ������: 378B
					weightsofInf[iWeights] = numberOfInf[3] + numberOfInf[7] + numberOfInf[8] + numberOfInf[11];
					break;
				default:
					// �Ʒ�: 9ABC
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
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}
	
	@Override
	public DirectionCode Infected_Move() {
		ArrayList<PlayerInfo> corpsePrefer = new ArrayList<>();
		for (CellInfo[] columns : cells)
			for (CellInfo cell : columns)
				corpsePrefer.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
		for (PlayerInfo corpsetmp : corpsePrefer) // ��ü�� �󸶳� ���� ����� �� �� �� ���
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
			
			// ��� ĭ�� �����Ͽ� ���ǽǿ� �ִ� ��� ��ü�鿡 ���� ����� ����
			for (CellInfo[] rows : cells)
				for (CellInfo cell : rows)
					corpses.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
			// TODO
			// �̵� ������ �� ĭ�鿡 ����, '���� ��ü�� ������ ���� �� �ִ� ĭ�� ����
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
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
	}
	
	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0){
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
		}
		else{
			ArrayList<PlayerInfo> corpsePrefer = new ArrayList<>();
			for (CellInfo[] columns : cells)
				for (CellInfo cell : columns)
					corpsePrefer.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));
			for (PlayerInfo corpsetmp : corpsePrefer) // ��ü�� �󸶳� ���� ����� �� �� ��
														// ���
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
					
					// ���� ���� ĭ�� �߰ߵǸ� ����
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
					// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
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
				
				// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
				for (int row = 0; row < Constants.Classroom_Height; row++){
					for (int column = 0; column < Constants.Classroom_Width; column++){
						CellInfo cell = this.cells[row][column];
						
						int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
						int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
						
						int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
						int distance = favoritePoint.GetDistance(row, column);
						
						// ���� ���� ĭ�� �߰ߵǸ� ����
						if (weight > max_weight){
							max_weight = weight;
							max_row2 = row;
							max_column2 = column;
							min_distance = distance;
						}
						// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
						else if (weight == max_weight){
							// �Ÿ��� �� ������ ����
							if (distance < min_distance){
								max_row2 = row;
								max_column2 = column;
								min_distance = distance;
							}
							// �Ÿ����� ������ �� �����ϴ� ������ ����
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
								
								// ������� �Դٸ� ���� �׸� ���� ����
							}
						}
					}
				}
				return new Point(max_row2, max_column2);
			}
		}
	}
}