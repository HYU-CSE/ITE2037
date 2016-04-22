package l4g.customplayers;

import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 * 계속해서 한 사람만 쫓아다닙니다.
 * 처음으로 쫓아다닐 사람은 생존자입니다.
 * 쫓아다닐 사람이 생존자든 감염자든 늘 따라다닙니다.
 * 내가 생존자든 감염자든 늘 같은 사람을 따라다닙니다.
 * 내가 생존자일 경우, 가만히 있어야 하는 상황에서는 좌우 반복 이동을 합니다.
 * 쫓아다닐 사람이 영혼 상태인 경우 새로운 사람을 찾습니다.
 *
 */
public class Player_Yandere extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_Yandere(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "Yandere");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = true;
		
		
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
	
	int loveID;	// 쫓아다닐 ID
	int spawn_row=3;
	int spawn_col=3;
	int lrCheck=0;
	
	// 쫓아다닐 ID를 정하는 함수
	public void FindLoveID()
	{
		int rowArr[]={-1,0,0,1};
		int colArr[]={0,-1,1,0};
		loveID = 0;
		
		// 내가 영혼 상태가 아닌 경우에는 내 상하좌우만 검사(두 칸 이상 떨어지면 안 됨)
		if( myInfo.state != StateCode.Soul ){
			for( int i = 0; i < 4; i++ ){
				if( loveID != 0 ) break;
				if( myInfo.position.row+rowArr[i] >= 0 && myInfo.position.row+rowArr[i] < Constants.Classroom_Height && myInfo.position.column+colArr[i] >= 0 && myInfo.position.column+colArr[i] < Constants.Classroom_Width ){
					cells[myInfo.position.row+rowArr[i]][myInfo.position.column+colArr[i]].ForEach_Players(player ->
					{
						if( player.state == StateCode.Survivor ){
							loveID = player.ID;
						}
					});
				}
			}
		}
		// 내가 영혼 상태인 경우 0,0부터 끝까지 쭉 검사
		for (int row = 0; row < Constants.Classroom_Height; ++row )
		{
			if( loveID != 0 ) break;
			for (int col = 0; col < Constants.Classroom_Width; ++col )
			{
				if( loveID != 0 ) break;
				cells[row][col].ForEach_Players(player ->
				{
					if( player.state == StateCode.Survivor ){
						loveID = player.ID;
						spawn_row = player.position.row;
						spawn_col = player.position.column;
					}
				});
			}
		}
	}
	DirectionCode iFoundYou;
	int iForFindYou;
	// 쫓아다니고자 하는 ID가 어디에 있는지 찾아내는 함수
	public void WhereAreYou()
	{
		int rowArr[]={-1,0,0,1};
		int colArr[]={0,-1,1,0};
		iFoundYou = null;
		
		for( iForFindYou = 0; iForFindYou < 4; iForFindYou++ ){
			if( myInfo.position.row+rowArr[iForFindYou] >= 0 && myInfo.position.row+rowArr[iForFindYou] < Constants.Classroom_Height && myInfo.position.column+colArr[iForFindYou] >= 0 && myInfo.position.column+colArr[iForFindYou] < Constants.Classroom_Width ){
				cells[myInfo.position.row+rowArr[iForFindYou]][myInfo.position.column+colArr[iForFindYou]].ForEach_Players(player ->
				{
					if( player.ID == loveID ){
						if( iForFindYou == 0 ) iFoundYou = DirectionCode.Up;
						if( iForFindYou == 1 ) iFoundYou = DirectionCode.Left;
						if( iForFindYou == 2 ) iFoundYou = DirectionCode.Right;
						if( iForFindYou == 3 ) iFoundYou = DirectionCode.Down;
					}
				});
			}
		}
		// 만약 loveID가 사라진 id라면 한 번 더 실행
		if( iFoundYou == null ){
			FindLoveID();
			for( iForFindYou = 0; iForFindYou < 4; iForFindYou++ ){
				if( myInfo.position.row+rowArr[iForFindYou] >= 0 && myInfo.position.row+rowArr[iForFindYou] < Constants.Classroom_Height && myInfo.position.column+colArr[iForFindYou] >= 0 && myInfo.position.column+colArr[iForFindYou] < Constants.Classroom_Width ){
					cells[myInfo.position.row+rowArr[iForFindYou]][myInfo.position.column+colArr[iForFindYou]].ForEach_Players(player ->
					{
						if( player.ID == loveID ){
							if( iForFindYou == 0 ) iFoundYou = DirectionCode.Up;
							if( iForFindYou == 1 ) iFoundYou = DirectionCode.Left;
							if( iForFindYou == 2 ) iFoundYou = DirectionCode.Right;
							if( iForFindYou == 3 ) iFoundYou = DirectionCode.Down;
						}
					});
				}
			}
		}
		// (만약 아직도 loveID를 못 찾는다면)
		if( iFoundYou == null ){
			if( lrCheck == 0 ){
				if( myInfo.position.column+1 < Constants.Classroom_Width ){
					iFoundYou = DirectionCode.Right;
				}else{
					iFoundYou = DirectionCode.Left;
				}
				lrCheck = 1;
			}else{
				if( myInfo.position.column-1 >= 0 ){
					iFoundYou = DirectionCode.Left;
				}else{
					iFoundYou = DirectionCode.Right;
				}
				lrCheck = 0;
			}
		}
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		WhereAreYou();
		return iFoundYou;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		WhereAreYou();
		return iFoundYou;
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
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		FindLoveID();

		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		return new Point(spawn_row, spawn_col);
	}
}

