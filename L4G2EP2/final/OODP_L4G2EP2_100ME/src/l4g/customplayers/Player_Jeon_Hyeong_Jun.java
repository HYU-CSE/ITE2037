package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.Action;
import l4g.data.Action.TypeCode;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Jeon_Hyeong_Jun extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_Jeon_Hyeong_Jun(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "ǰ");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	
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

	/**
	 * �� ��ġ�� �����ڰ� ������ true ������ false�� ��ȯ
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
	 * �� ��ġ�� �����ڰ� ������ true ������ false�� ��ȯ
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
	 * �� ��ġ�� �ִ� ����ü�� ������ �־��ų� ��ġ�Ǿ����� true �ƴϸ� false
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
	 * ���밪���� ������ִ� �޼ҵ�
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
	 * �Ÿ� ���ϱ�
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
	 * �����ڰ� �� ��ġ�� ������ ���ϴ� ���� ����
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
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{	
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
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
	
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		if(no_cells()){//�þ߿� �ƹ��� ������
			int gap_row=myInfo.position.row-Constants.Classroom_Height/2;
			int gap_column=myInfo.position.column-Constants.Classroom_Width/2;
			//���� �پ��־� �þ߰� �������� ������ ������
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
				//�߾����� �̵���
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
		else{//�þ߿� ���� ������
			return assume(myInfo.position.row,myInfo.position.column);
						
				
			}
		}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{	trigger_acceptDirectInfection=false;
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
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
		
		
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return new Point(result_row, result_column);
	}
	
}
