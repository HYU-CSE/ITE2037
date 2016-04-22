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
public class corpse_king extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public corpse_king(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "시체왕");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}

	
	/**
	 * '선호하는 칸'을 기록해 두는 field입니다.
	 * 이 field는 반드시 필요합니다.
	 */

	
	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다.
	 * 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	
	
	
	/**
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	
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
		DirectionCode result = null;
	
		if(turnInfo.turnNumber<10 && turnInfo.turnNumber%2==0)
			result=DirectionCode.Right;
		else if(turnInfo.turnNumber<10 && turnInfo.turnNumber%2!=0)
			result=DirectionCode.Left;
		else if(this.myInfo.position.column<=Constants.Classroom_Width-3 &&cells[this.myInfo.position.row][this.myInfo.position.column+2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&cells[this.myInfo.position.row][this.myInfo.position.column+1].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row==0 &&cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Down;
			}
			
		}		
		else if(this.myInfo.position.column>=2 && cells[this.myInfo.position.row][this.myInfo.position.column-2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row][this.myInfo.position.column-1].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
							player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Up;
			}
			
		
		}
		else if(this.myInfo.position.row<=Constants.Classroom_Height-3 &&cells[this.myInfo.position.row+2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&cells[this.myInfo.position.row+1][this.myInfo.position.column].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0 )
		{	
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Down;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Right;
			}
		}
		
		else if(this.myInfo.position.row>=2 &&  cells[this.myInfo.position.row-2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&  cells[this.myInfo.position.row-1][this.myInfo.position.column].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column==0 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Up;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Left;
			}
			
		}	
		else if(this.myInfo.position.column<=Constants.Classroom_Width-3 &&cells[this.myInfo.position.row][this.myInfo.position.column+2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0)
		{
			
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row==0 &&cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Down;
			}
			
		}		
		else if(this.myInfo.position.column>=2 && cells[this.myInfo.position.row][this.myInfo.position.column-2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 )
		{
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
							player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Up;
			}
			
		
		}
		else if(this.myInfo.position.row<=Constants.Classroom_Height-3 &&cells[this.myInfo.position.row+2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 )
		{	
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Down;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Right;
			}
		}
		
		else if(this.myInfo.position.row>=2 &&  cells[this.myInfo.position.row-2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column==0 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Up;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Left;
			}
			
		}	
		else if(result==null)
		{
			
			if(this.myInfo.position.column<Constants.Classroom_Width-1)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row<Constants.Classroom_Height-1)
	        	 result=DirectionCode.Down;
			else if(this.myInfo.position.column==Constants.Classroom_Width-1)
	        	result=DirectionCode.Left;
			else if(this.myInfo.position.row==Constants.Classroom_Height-1)
	        	result=DirectionCode.Up;
		} 
		
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
		
			return DirectionCode.Stay;

		
		

		// 모든 칸을 조사하여 강의실에 있는 모든 시체들에 대한 목록을 만듦 
	
		//if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			//return GetMovableAdjacentDirection();
		
		// 그렇지 않으면 정화 기도 시도
		
		
		// 그렇지 않으면 정화 기도 시도
		
		//return DirectionCode.Stay;
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
		int result_row = 6;
		int result_column = 6;
		int tmp,icheck,ccheck,weight,imax=0;//현재위치의 시체수를 저장할변수, 현재위치 주변에있는 감염체수를 저장할 변수,시체가있는 위치주변에 감염자수의 최댓값
		for(int row=1 ;row<Constants.Classroom_Height-1;row++)
		{
			for(int column=1;column<Constants.Classroom_Width-1;column++)
			{
				icheck=0;
				ccheck=0;
				tmp=cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse );
				if(tmp>=0)
				{
					for(int i=-1;i<=1;i++)
					{
						for(int j=-1;j<=1;j++)
						if(cells[row+i][column+j].CountIf_Players(player -> player.state == StateCode.Infected )>0 )
						{
								++ccheck;
						}
					}
					icheck=cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
				}	
					weight=icheck+tmp+ccheck;
					if(imax<weight)
					{
						imax=weight;
						result_row=row;
						result_column=column;
					}
				
			}
			
		}
		
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		
		return new Point(result_row, result_column);
		
	
	}
}
