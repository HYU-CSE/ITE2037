package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.Action;
import l4g.data.Action.TypeCode;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_Jeon_Hyeong_Jun extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_Jeon_Hyeong_Jun(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "품");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		
		
		
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

	/**
	 * 그 위치에 감염자가 있으면 true 없으면 false를 반환
	 */
	boolean is_there_infected(int row,int column){
		if(cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected)>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 그 위치에 생존자가 있으면 true 없으면 false를 반환
	 */
	boolean is_there_survivor(int row,int column){
		if(cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor)>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 이 위치에 있는 감염체가 가만히 있었거나 배치되었으면 true 아니면 false
	 * @param row
	 * @param column
	 * @return
	 */
	boolean do_not_move(int row,int column){
		ArrayList<Action> moves = cells[row][column].Select_Actions(action->action.type==TypeCode.Move);
		for (Action move : moves) {
			if(move.location_from==move.location_to){
				return true;
			}
		}
		ArrayList<PlayerInfo> corps = cells[row][column].Select_Players(player->player.state==StateCode.Corpse);
		for (PlayerInfo playerInfo : corps) {
			if(playerInfo.transition_cooldown==0){
				return true;
			}
		}
			
		
		return false;
	}
	/**
	 * 절대값으로 만들어주는 메소드
	 * @param value
	 * @return
	 */
	int make_absolute_value(int value){
		if(value>0){
			return value;
		}
		else{
			return -value;
		}
	}
	/**
	 * 거리 구하기
	 * @param p_row
	 * @param p_column
	 * @param row
	 * @param column
	 * @return
	 */
	int distance(int p_row,int p_column,int row,int column){
		int x = make_absolute_value(p_row-row);
		int y = make_absolute_value(p_column-column);
		return x+y;
	}
	/**
	 * 생존자가 그 위치에 있을때 피하는 방향 예상
	 * @param p_row
	 * @param p_column
	 * @return
	 */
	DirectionCode assume(int p_row,int p_column){
		int row;
		int column;
		int up=0;
		int down=0;
		int right=0;
		int left=0;
		int result=0;
		int[][] count_infected=new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int[][] count_players=new int[Constants.Classroom_Height][Constants.Classroom_Width];
		for(row=0;row<Constants.Classroom_Height;row++){
			for(column=0;column<Constants.Classroom_Width;column++){
				count_infected[row][column]= cells[row][column].CountIf_Players(player->player.state==StateCode.Infected);
				count_players[row][column]= cells[row][column].Count_Players();
			}
		}
		for(row=0;row<Constants.Classroom_Height;row++){
			for(column=0;column<Constants.Classroom_Width;column++){
				if(count_infected[row][column]>0){
					int dist = distance(p_row,p_column,row,column);
					if(row<p_row){
						if(do_not_move(row,column)){
							switch(dist){
							case 2:
								if(column==p_column){
									up-=count_infected[row][column];
								}
								else{
								}
								break;
							case 1:
								up+=count_infected[row][column]*10000;
								break;
							}
						}
						else{
							switch(dist){
							case 2:
								if(column==p_column){
									up+=200;
								}
								else{
									up+=500;
								}
								break;
							case 1:
								up+=100;
								break;
							}
						}
					}
					if(row>p_row){
						if(do_not_move(row,column)){
							switch(dist){
							case 2:
								if(column==p_column){
									down-=count_infected[row][column];
								}
								else{
								}
								break;
							case 1:
								down+=count_infected[row][column]*10000;
								break;
							}
						}
						else{
							switch(dist){
							case 2:
								if(column==p_column){
									down+=200;
								}
								else{
									down+=500;
								}
								break;
							case 1:
								down+=100;
								break;
							}
						}
					}
					if(column<p_column){
						if(do_not_move(row,column)){
							switch(dist){
							case 2:
								if(column==p_column){
									left-=count_infected[row][column];
								}
								else{
								}
								break;
							case 1:
								left+=count_infected[row][column]*10000;
								break;
							}
						}
						else{
							switch(dist){
							case 2:
								if(column==p_column){
									left+=200;
								}
								else{
									left+=500;
								}
								break;
							case 1:
								left+=100;
								break;
							}
						}
					}
					if(column>p_column){
						if(do_not_move(row,column)){
							switch(dist){
							case 2:
								if(column==p_column){
									right-=count_infected[row][column];
								}
								else{
								}
								break;
							case 1:
								right+=count_infected[row][column]*10000;
								break;
							}
						}
						else{
							switch(dist){
							case 2:
								if(column==p_column){
									right+=200;
								}
								else{
									right+=500;
								}
								break;
							case 1:
								right+=100;
								break;
							}
						}
					}
				}
				if(count_players[row][column]>0){
					if(row<p_row){
						up-=count_players[row][column];
					}
					if(row>p_row){
						down-=count_players[row][column];
					}
					if(column<p_column){
						left-=count_players[row][column];
					}
					if(column>p_column){
						right-=count_players[row][column];
					}
	
				}
			}
		}
		if(p_row==0){
			up+=100000000;
		}
		if(p_row==Constants.Classroom_Height-1){
			down+=100000000;
		}
		if(p_column==0){
			left+=100000000;
		}
		if(p_column==Constants.Classroom_Width-1){
			right+=100000000;
		}
		if(left>down){
			result = down;
		}
		else {
			result = left;
		}
		if(result>right){
			result = right;
		}
		if(result>up){
			result = up;
		}
		if(result==down){
			return DirectionCode.Down;
		}
		if(result==right){
			return DirectionCode.Right;
		}
		if(result==left){
			return DirectionCode.Left;
		}
		else{
			return DirectionCode.Up;
		}
				
	}
	boolean no_cells(){
		int row;
		int column;
		for(row=0;row<Constants.Classroom_Height;row++){
			for(column=0;column<Constants.Classroom_Width;column++){
				if(is_there_survivor(row,column) || is_there_survivor(row,column)){
					return false;
				}
			}
		}
		return true;
	}		
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		DirectionCode[] directions = new DirectionCode[4];
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
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
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
	
		return DirectionCode.Stay;
//		int row=0;
//		int column=0;
//		int up=0;
//		int down=0;
//		int left=0;
//		int right=0;
//		int dist=0;
//		int result=0;
//		int result_row=0;
//		int result_column=0;
//		DirectionCode predict=DirectionCode.Stay;
//		int[][] count_corps = new int[Constants.Classroom_Height][Constants.Classroom_Width];
//		int[][] count_infected = new int[Constants.Classroom_Height][Constants.Classroom_Width];
//		int[][] count_survivors = new int[Constants.Classroom_Height][Constants.Classroom_Width];
//		int[][] mark=new int[Constants.Classroom_Height][Constants.Classroom_Width];
//		for(row=0;row<Constants.Classroom_Height;row++){
//			for(column=0;column<Constants.Classroom_Width;column++){
//				count_corps[row][column]=cells[row][column].CountIf_Players(player->player.state==StateCode.Corpse);
//				count_infected[row][column]=cells[row][column].CountIf_Players(player->player.state==StateCode.Infected);
//				count_survivors[row][column]=cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
//				mark[row][column]=0;
//				if(count_survivors[row][column]>0){
//					dist=distance(myInfo.position.row,myInfo.position.column,row,column);
//					if(dist==1){
//						predict=assume(row,column);
//						
//						if(row>myInfo.position.row){
//							if(predict==DirectionCode.Up){
//								return DirectionCode.Stay;
//							}
//							else{
//								return predict;
//							}
//						}
//						if(row<myInfo.position.row){
//							if(predict==DirectionCode.Down){
//								return DirectionCode.Stay;
//							}
//							else{
//								return predict;
//						}
//						}
//						if(column>myInfo.position.column){
//							if(predict==DirectionCode.Left){
//								return DirectionCode.Stay;
//							}
//							else{
//								return predict;
//							}
//						}
//						if(column<myInfo.position.column){
//							if(predict==DirectionCode.Right){
//								return DirectionCode.Stay;
//							}
//							else{
//								return predict;
//						}
//						}
//					}
//				}
//				if(count_corps[row][column]>0){
//					dist=distance(myInfo.position.row,myInfo.position.column,row,column);
//					if(dist==0||dist==1){
//							if(myInfo.position.row<row){
//								mark[row][column]+=count_corps[row][column]*100;
//							}
//							if(myInfo.position.row>row){
//								mark[row][column]+=count_corps[row][column]*100;
//							}
//							if(myInfo.position.column<column){
//								mark[row][column]+=count_corps[row][column]*100;
//							}
//							if(myInfo.position.column>column){
//								mark[row][column]+=count_corps[row][column]*100;
//							}
//					}
//				}
//			
//				dist=distance(myInfo.position.row,myInfo.position.column,row,column);
//				if(dist>1){
//					switch(dist){
//					case 2:
//						mark[row][column]+=6*count_survivors[row][column];
//						break;
//					case 3:
//						mark[row][column]+=4*count_survivors[row][column];
//						break;
//					default:
//						mark[row][column]+=3*count_survivors[row][column];
//						break;
//					}
//				}
//								
//			}
//			
//		}
//		for(row=0;row<Constants.Classroom_Height;row++){
//			for(column=0;column<Constants.Classroom_Width;column++){
//				if(mark[row][column]>result){
//					result=mark[row][column];
//					result_row=row;
//					result_column=column;
//					predict=assume(result_row,result_column);
//				}
//			}
//		}
//		if(predict==DirectionCode.Down){
//			result_row+=1;
//		}
//		if(predict==DirectionCode.Up){
//			result_row-=1;
//		}
//		if(predict==DirectionCode.Left){
//			result_column-=1;
//		}
//		if(predict==DirectionCode.Right){
//			result_column+=1;
//		}
//		
//		if(result_row<myInfo.position.row){
//			return DirectionCode.Up;
//			
//		}
//		if(result_row>myInfo.position.row){
//			return DirectionCode.Down;
//		}
//		if(result_column<myInfo.position.column){
//			return DirectionCode.Left;
//		}
//		if(result_column>myInfo.position.column){
//			return DirectionCode.Right;
//		}
//
//		return DirectionCode.Stay;
	}
	public DirectionCode Survivor_Move()
	{
		//DirectionCode result = null;
	
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		if(no_cells()){//시야에 아무도 없을때
			int gap_row=myInfo.position.row-Constants.Classroom_Height/2;
			int gap_column=myInfo.position.column-Constants.Classroom_Width/2;
			//벽에 붙어있어 시야가 좁아지면 벽에서 벌어짐
			if(myInfo.position.row<2){
				return DirectionCode.Down;
			}
			else if(myInfo.position.row>10){
				return DirectionCode.Up;
			}
			else if(myInfo.position.column<2){
				return DirectionCode.Right;
			}
			else if(myInfo.position.column>10){
				return DirectionCode.Left;
			}
			else{
				//중앙으로 이동함
				int absolute_gap_row=make_absolute_value(gap_row);
				int absolute_gap_column=make_absolute_value(gap_column);
				if(absolute_gap_row>absolute_gap_column){
					if(gap_row<0){
						return DirectionCode.Down;
					}
					else {
						return DirectionCode.Up;
					}
				}
				else{
					if(gap_column<0){
						return DirectionCode.Right;
					}
					else{
						return DirectionCode.Left;
					}
				}
			}
		}
		else{//시야에 무언가 있을때
			return assume(myInfo.position.row,myInfo.position.column);
						
				
			}
		}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{	trigger_acceptDirectInfection=false;
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
		int row=0;
		int column=0;
		int key=0;
		int[][] count_infected = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int[][] count_survivor = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int[][] count_marks = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		for(row=0;row<Constants.Classroom_Height;row++){
			for(column=0;column<Constants.Classroom_Width;column++){
				count_infected[row][column]= cells[row][column].CountIf_Players(player->player.state==StateCode.Infected);
				count_survivor[row][column]= cells[row][column].CountIf_Players(player->player.state==StateCode.Survivor);
				count_marks[row][column]=count_infected[row][column]*count_infected[row][column];
				if(key<count_marks[row][column]){
					key=count_marks[row][column];
					result_row=row;
					result_column=column;
				}
			}
		}
		
		
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		return new Point(result_row, result_column);
	}
	
}
