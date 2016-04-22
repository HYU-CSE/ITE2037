package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */

public class Nintendojam extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Nintendojam(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "주윤님어서!");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0,0);
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
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
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
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		if(this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse)>0){
			return DirectionCode.Stay;
		}
		else
			return DirectionCode.Stay;
			
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
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
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		return new Point(Max_row,Max_column);
	}
}
