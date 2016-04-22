package l4g.customplayers;

import java.util.function.Predicate;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_NO_ANSWER extends Player
{
	//나의 현재 위치의 행,열값을 입력하고 가고싶은 위치 행,열값을 입력한 다음 가야 할 위치 반환
	private DirectionCode WhereToGo(int myRow, int myCol, int targetRow, int targetCol)
	{
		int x, y, absx, absy;
		x= myRow-targetRow; // 행(세로) 차이값 반환
		y= myCol-targetCol; // 열(가로) 차이값 반환
		absx = x>0 ? x : -x;// 행(세로) 차이값의 절대값
		absy = y>0 ? y : -y;// 열(가로) 차이값의 절대값
		
		if(absx>absy) //위 ,아래(세로, 행) 방향 
		{
			if(x>0) 
				return DirectionCode.Up;
			else
				return DirectionCode.Down;
		}
		else //좌 ,우(가로, 열) 방향 
		{
			if(y>0)
				return DirectionCode.Left;
			else
				return DirectionCode.Right;
		}
	}
	
	//중앙이라면 true , 아니면 false
	private boolean IsCenter(int row, int col)
	{
		// (5,5) (5,6) (5,7)
		// (6,5) (6,6) (6,7)
		// (7,5) (7,6) (7,7)
		// 총 9개의 좌표를 중앙이라고 정의한다.
		
		if( (5<=row && row <=7) && ((5<=col)&&(col<=7))  )
			return true;
		else
			return false;
	}
		
	//IsCenter가 false 일때 사용, 중앙으로 간다.
	private DirectionCode GoCenter(int row, int col)
	{
		return WhereToGo(row, col, 6, 6);
	}
	
	//좌, 우, 상, 하 가고자하는 방향에 벽이 존재한지 확인 있다면 true, 없다면 false
	private boolean IsWall(int row, int col, DirectionCode direction)
	{
		//위로 가야하는데 행이 0이거나, 아래로 가야하는데 행이 12이거나
		//왼쪽으로 가야하는데 열이 0이거나, 오른쪽으로 가야하는데 열이 12면 벽이므로 
		if(direction==DirectionCode.Up && row==0)
			return true; // 가고자하는 방향에 벽이 있다
		else if(direction==DirectionCode.Down && row==Constants.Classroom_Height-1)
			return true; // 가고자하는 방향에 벽이 있다
		else if(direction==DirectionCode.Left && col==0 )
			return true; // 가고자하는 방향에 벽이 있다
		else if(direction==DirectionCode.Right && col==Constants.Classroom_Width-1)
			return true; // 가고자하는 방향에 벽이 있다
		else
			return false; // 벽이 아니니까 안심하고 가자
	}
	
	//감염자 모드일 때 사용되는 메소드, 시야에서 생존자가 가장많은 방향으로 향한다
	private DirectionCode WhereToKill(int row , int col)
	{

		int numOfSurvivor, maxNum=0, maxRow=0, maxCol=0;
		 
		for(int i=row-2; i<=row+2 ; i++)
			for(int j=col-2; j<=col+2 ; j++)
			{
				//벽뚫기방지
				if((0<=i && i<Constants.Classroom_Width )&&(0<=j && j<Constants.Classroom_Height))
				{
					numOfSurvivor = cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);
					if(maxNum<numOfSurvivor){
						maxNum= numOfSurvivor;
						maxRow =i;
						maxCol= j;
					}
				}
			}
	
		return WhereToGo(row, col, maxRow, maxCol);	
	}
	
	//생존자 모드일 때 감염자 적은곳으로 자살하기!!
	private DirectionCode WhereToSurvivorGo(int row , int col)
	{
		int numOfInfected;
		int minRow=6, minCol=6;
		for (int i = row-1; i <=row+1 ; ++i)
		{
			for (int j = col-1; j <=col+1; ++j)
			{
				
				//벽 뚫기방지
				if((0<=i && i<Constants.Classroom_Width )&&(0<=j && j<Constants.Classroom_Height))
				{
					numOfInfected= cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
					if(1<=numOfInfected && numOfInfected<=2)
					{	
						minRow =i;
						minCol =j;
					}
				}
			}
		}
		
		return WhereToGo(row, col, minRow, minCol);
		
	}
	
	
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_NO_ANSWER(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "노답중노답");
		
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
	
	@Override
	public DirectionCode Survivor_Move()
	{
		//나는 그냥 자살이 하고 싶습니다.
		DirectionCode result = null;
		int row = this.myInfo.position.row;
		int col = this.myInfo.position.column;
		
		
			result = WhereToSurvivorGo(row, col);
			if(IsWall(row, col, result))
				result = GoCenter(row, col);
		
			
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
		
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		int row = this.myInfo.position.row;
		int col = this.myInfo.position.column;
		
		DirectionCode result = null;
		
		//50탄까지는 생존자죽이러돌아다니기모드
		if(turnInfo.turnNumber <=50)
		{
			result = WhereToKill(row, col);
			//만약에 벽이라면 중앙으로 보냄
			if(IsWall(row, col, result))
				result = GoCenter(row, col);
		
		}
		else if(turnInfo.turnNumber <=100) // 50~100탄까지는 부동자세모드
		{
			result = DirectionCode.Stay;
		}
		else  //100탄~121턴은 중앙으로 올라가서 정화기도
		{
			if(IsCenter(row, col))
				result = DirectionCode.Stay;
			else
				result = GoCenter(row, col);
		}
		
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
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		int result_row=6;
		int result_column=6;
		int numOfInfecteds, maxNum=0, maxRow=0, maxCol=0;

		if(this.turnInfo.turnNumber==0 )
		{
			result_row = 6;
			result_column= 6;
		}
		else if(this.turnInfo.turnNumber<=30 ) // 1~30탄은 그냥 중앙에서 리스폰
		{
		
			for(int i=4; i<Constants.Classroom_Height-4 ; i++)
				for(int j=4; j<Constants.Classroom_Width-4 ; j++)
				{
					numOfInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
					if(numOfInfecteds==0){
						maxNum= numOfInfecteds;
						maxRow =i;
						maxCol= j;
					}
				}
			
			result_row = maxRow;
			result_column = maxCol;
			
		}
		else // 31탄부터 121탄까지는 감염자수가 제일많은곳으로 리스폰
		{
		for(int i=0; i<Constants.Classroom_Height ; i++)
			for(int j=0; j<Constants.Classroom_Width ; j++)
			{
				numOfInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				if(maxNum<numOfInfecteds){
					maxNum= numOfInfecteds;
					maxRow =i;
					maxCol= j;
				}
			}
		
		result_row = maxRow;
		result_column = maxCol;
		}
		
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		return new Point(result_row, result_column);
	}
}
