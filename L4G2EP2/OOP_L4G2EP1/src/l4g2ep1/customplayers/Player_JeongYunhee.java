package l4g2ep1.customplayers;

//import java.util.ArrayList;

//import javax.swing.text.Position;

import l4g2ep1.*;
import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class Player_JeongYunhee extends Player
{
	public Player_JeongYunhee()
	{
		name = "Whoooooo";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false;				// TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고 아닌 경우 false로 두세요.
		receiveOthersInfo_detected = true;
		receiveActions=true;
		receiveReactions=true;
	}
	
	public int max(int[] danger){
		int maximum=0;
		int[] point= new int[4];
		int direction=0;
		for(int j=0;j<4;j++){
			
			if(j==0){
				if(this.IsValidMove(DirectionCode.Up)==false) {
					point[0]=-1;
				}
			}
			else if(j==1){
				if(this.IsValidMove(DirectionCode.Left)==false) point[1]=-1;
			}
			else if(j==2){
				if(this.IsValidMove(DirectionCode.Right)==false) point[2]=-1;
			}
			else{
				if(this.IsValidMove(DirectionCode.Down)==false) point[3]=-1;
			}
						
				if(danger[j]>maximum){
					maximum=danger[j];
					if(j==0) direction= 3;
					else if(j==1) direction= 2;
					else if(j==2) direction= 1;
					else if(j==3) direction= 0;
				}
			
		}
		
		if(point[direction]==-1){
			if(direction==0) direction= 3;
			else if(direction==1) direction= 2;
			else if(direction==2) direction= 1;
			else if(direction==3) direction= 0;
		}
		
		return direction;
}
	

	private int max_i(int[] survivor) {
		int maximum=0;
		int[] point= new int[4];
		int direction=0;
		for(int j=0;j<4;j++){
			
			if(j==0){
				if(this.IsValidMove(DirectionCode.Up)==false) {
					point[0]=-1;
				}
			}
			else if(j==1){
				if(this.IsValidMove(DirectionCode.Left)==false) point[1]=-1;
			}
			else if(j==2){
				if(this.IsValidMove(DirectionCode.Right)==false) point[2]=-1;
			}
			else{
				if(this.IsValidMove(DirectionCode.Down)==false) point[3]=-1;
			}
						
				if(survivor[j]>maximum){
					maximum=survivor[j];
					if(j==0) direction= 0;
					else if(j==1) direction= 1;
					else if(j==2) direction= 2;
					else if(j==3) direction= 3;
				}
			
		}
		
		if(point[direction]==-1){
			if(direction==0) direction= 3;
			else if(direction==1) direction= 2;
			else if(direction==2) direction= 1;
			else if(direction==3) direction= 0;
		}
		
		return direction;
	}
	
	private int Super_surviovor(PlayerInfo other) {
		Vector v = GetDistanceVectorBetweenPlayers(other);
		if(v.x_offset==0&&v.y_offset==-2) return 0;
		else if(v.x_offset==-2&&v.y_offset==0) return 1;
		else if(v.x_offset==2&&v.y_offset==0) return 2;
		else if(v.x_offset==0&&v.y_offset==2) return 3;
		else if(v.x_offset==1&&v.y_offset==-1) return 20;
		else if(v.x_offset==-1&&v.y_offset==-1) return 10;
		else if(v.x_offset==-1&&v.y_offset==1) return 13;
		else if(v.x_offset==1&&v.y_offset==1) return 23;
		return 5;
	}
	@Override
	public DirectionCode Survivor_Move()
	{
		//int i=0;//반복문용 변수
		int[] danger = new int[4];//위험포인트 저장용 배열, danger[0]부터 위, 왼쪽, 오른쪽, 아래쪽 순서
		
		for ( PlayerInfo other : othersInfo_detected )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);
			
			//해당 플레이어가 나보다 위에 있다면 위로 가는 것은 위험할 듯?
			if(other.GetState()==State.Infected){
				if ( v.y_offset < 0 )
					danger[0]+=1;

				//해당 플레이어가 나보다 왼쪽에 있다면 왼쪽으로 가는 것은 위험할 듯?
				if ( v.x_offset < 0 )
					danger[1]+=1;
				
				//해당 플레이어가 나보다 오른쪽에 있다면 오른쪽으로 가는 것은 위험할 듯?
				if ( v.x_offset > 0 )
					danger[2]+=1;
				
				//해당 플레이어가 나보다 아래에 있다면 아래로 가는 것은 위험할 듯?
				if ( v.y_offset > 0 )
					danger[3]+=1;
			}
			if(other.GetState()==State.Survivor){
				if ( v.y_offset < 0 )
					danger[0]-=1;

				if ( v.x_offset < 0 )
					danger[1]-=1;
				
				if ( v.x_offset > 0 )
					danger[2]-=1;
				
				if ( v.y_offset > 0 )
					danger[3]-=1;
			}
		}
		
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);
			
		
			if(other.GetState()==State.Infected){
				if ( v.y_offset < 0 )
					danger[0]+=10;

				if ( v.x_offset < 0 )
					danger[1]+=10;
				
				if ( v.x_offset > 0 )
					danger[2]+=10;
				
				if ( v.y_offset > 0 )
					danger[3]+=10;
			}
			if(other.GetState()==State.Survivor){
				if ( v.y_offset < 0 )
					danger[0]-=1;

				if ( v.x_offset < 0 )
					danger[1]-=1;
				
				if ( v.x_offset > 0 )
					danger[2]-=1;
				
				if ( v.y_offset > 0 )
					danger[3]-=1;
			}
		}
		
		
		DirectionCode direction=null;
		if(max(danger)==0) direction=DirectionCode.Up;
		else if(max(danger)==1) direction=DirectionCode.Left;
		else if(max(danger)==2) direction=DirectionCode.Right;
		else if(max(danger)==3) direction=DirectionCode.Down;


		// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요.
		return direction;
	}

	@Override
	public void Corpse_Stay()
	{
		
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		int corpse=0,num=0;
		PlayerInfo pos = null;
		DirectionCode direction=DirectionCode.Stay;
		for(PlayerInfo other: othersInfo_withinSight){
			Vector v= GetDistanceVectorBetweenPlayers(other);
			if((v.x_offset<=2||v.x_offset>=-2||v.y_offset<=2||v.y_offset>=-2) && other.GetState()==State.Corpse){
				corpse=-1;
				if(pos==null) pos=other;
				else if(GetDistance(myInfo.GetPosition(), other.GetPosition())<GetDistance(myInfo.GetPosition(), pos.GetPosition()))
					pos=other;
			}
		
		}
		
		if(corpse==-1){
			Vector v=GetDistanceVectorBetweenPlayers(pos);
			if ( v.y_offset < 0 )
				direction=DirectionCode.Up;

			if ( v.x_offset < 0 )
				direction=DirectionCode.Left;
			
			if ( v.x_offset > 0 )
				direction=DirectionCode.Right;
			
			if ( v.y_offset > 0 )
				direction=DirectionCode.Down;

			
		}
	
		else{
			//int i=0;//반복문용 변수
			int[] survivor = new int[4];//위험포인트 저장용 배열, danger[0]부터 위, 왼쪽, 오른쪽, 아래쪽 순서
			
			for ( PlayerInfo other : othersInfo_withinSight )
				{
					//해당 플레이어와 나 사이의 거리 비교
					Vector v = GetDistanceVectorBetweenPlayers(other);
					num++;
					if(other.GetState()==State.Survivor){
						if ( v.y_offset < 0 )
							if(Super_surviovor(other)==0) survivor[0]+=5;
							else if(v.y_offset==-1&&v.x_offset==0) ;
							else survivor[0]+=1;

						if ( v.x_offset < 0 )
							if(Super_surviovor(other)==1) survivor[1]+=5;
							else if(v.x_offset==-1&&v.y_offset==0) ;
							else survivor[1]+=1;
						
						if ( v.x_offset > 0 )
							if(Super_surviovor(other)==2) survivor[2]+=5;
							else if(v.x_offset==1&&v.y_offset==0) ;
							else survivor[2]+=1;
						
						if ( v.y_offset > 0 )
							if(Super_surviovor(other)==3) survivor[3]+=5;
							else if(v.y_offset==1&&v.x_offset==0) ;
							else survivor[3]+=1;
					}
				}
								
				if(num<3&&myInfo.GetHitPoint()<11) direction=DirectionCode.Stay;
				else if(max_i(survivor)==0) direction=DirectionCode.Up;
				else if(max_i(survivor)==1) direction=DirectionCode.Left;
				else if(max_i(survivor)==2) direction=DirectionCode.Right;
				else if(max_i(survivor)==3) direction=DirectionCode.Down;
			
				// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요.
				
		}
		
		return direction;
	}


	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
		}

		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn()
	{
		Point origin =new Point();
		
			origin.x=4;
			origin.y=4;
				
		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
		return origin;
	}

}
