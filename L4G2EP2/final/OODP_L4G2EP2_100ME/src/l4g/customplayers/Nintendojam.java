package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */

public class Nintendojam extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Nintendojam(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�����Ծ!");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0,0);
	/*
	 * TODO#5	���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	 * 			�翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	 * 			�ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * 			L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * 			�������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	 * 			�׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * 			��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	 * 			'�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		favoritePoint.row = 6;
		favoritePoint.column = 6;
	}
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		int[] numberOfPlayers = new int[13];
		int row= myInfo.position.row;
		int column=myInfo.position.column;
		
		row -=2;
		
		if(row>=0){
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
			
		}
		++row;
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		if(row>=0){
			if(column>=1){
				numberOfPlayers[1] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
			}
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
			
			if(column<Constants.Classroom_Width-1){
				numberOfPlayers[3] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);	
			}
		}
		++row;
		
		if(column>=1)
		{
			numberOfPlayers[5] = cells[row][column-1].CountIf_Players(player->player.state==StateCode.Survivor);
			
			if(column>=2){
				numberOfPlayers[4] = cells[row][column-2].CountIf_Players(player->player.state==StateCode.Survivor);
			}
		}
		if(column<Constants.Classroom_Width -1){
			numberOfPlayers[7] = cells[row][column+1].CountIf_Players(player->player.state==StateCode.Survivor);
			
			if(column<Constants.Classroom_Width-2){
				numberOfPlayers[8] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
			}
		}
		++row;
		
		if(row<Constants.Classroom_Height){
			if(column>=1){
				numberOfPlayers[9] = cells[row][column-1].CountIf_Players(player->player.state==StateCode.Survivor);
			}
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
			
			if(column<Constants.Classroom_Width-1){
				numberOfPlayers[11] = cells[row][column+1].CountIf_Players(player->player.state==StateCode.Survivor);
			}
		}
		++row;
		
		if(row<Constants.Classroom_Height){
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
		}
		
		int [] weights = new int [4];
		
		for(int IWeights = 0; IWeights<4;IWeights++){
			switch(directions[IWeights]){
				case Up:
					weights[IWeights]=numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
					break;
				case Left:
					weights[IWeights]=numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
					break;
				case Right:
					weights[IWeights]=numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
					break;
				default:
					weights[IWeights]=numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
					break;
			}
		}
		
		int Max_weight = -1;
		int Max_Idx_weights = 0;
		
		for(int Iweights = 0;Iweights < 4; Iweights++){
			if(weights[Iweights]>Max_weight){
				Point adPoint = myInfo.position.GetAdjacentPoint(directions[Iweights]);
				
				if(adPoint.row>=0 && adPoint.row<Constants.Classroom_Height&&adPoint.column>=0&&adPoint.column<Constants.Classroom_Width){
					Max_weight = weights[Iweights];
					Max_Idx_weights = Iweights;
				}
			}
		}
		return directions[Max_Idx_weights];
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		if(this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse)>0){
			return DirectionCode.Stay;
		}
		else
			return DirectionCode.Stay;
			
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		int Max_weight = 0;
		int Max_row = -1;
		int Max_column= -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		for(int row = 0;  row < Constants.Classroom_Height; row++){
			for(int column = 0; column<Constants.Classroom_Width;column++){
				CellInfo cell = this.cells[row][column];
				
				int numberC = cell.CountIf_Players(player->player.state==StateCode.Corpse);
				int numberI = cell.CountIf_Players(player->player.state==StateCode.Infected);
				
				int weight = numberI != 0 ? numberC + numberI : 0;
				int distance = favoritePoint.GetDistance(row,column);
				
				if(weight>Max_weight){
					Max_column = column;
					Max_row = row;
					Max_weight = weight;
					min_distance = distance;
				}
				else if(weight == Max_weight){
					if(distance<min_distance){
						Max_column = column;
						Max_row = row;
						min_distance = distance;
					}
					
					else if(distance == min_distance){
						for(int Idirection = 0; Idirection<4; Idirection++){
							Point adPoint = favoritePoint.GetAdjacentPoint(directions[Idirection]);
							
							if(adPoint.GetDistance(row, column)<adPoint.GetDistance(Max_row, Max_column)){
								Max_row = row;
								Max_column = column;
								break;
							}
						}
					}
				}
			}
		}
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		return new Point(Max_row,Max_column);
	}
}
