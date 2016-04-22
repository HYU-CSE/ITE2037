package l4g.customplayers;

import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_PineApple extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_PineApple(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "��.��.��.��.");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
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
	
	
	
	int state = 0;
	/** ���� ¥��
	 *  �ʹ� 10���� ���� ���� �̵� -> �߾ӿ� �ӹ��°� ����
	 *  ���� 45~55�� ������ ���� ��ǥ
	 *  ���� 45~55���� �Ѿ�� �ٷ� ��ü�� �Ǳ� ���� ����ü�� �ٴ´�.
	 *  ���� ���� ���� �����ִ� ������ �پ���.
	 *  ������ ���¿��� �ֺ��� �ӹ�����.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result;
		int[] counts = new int[4];
		int PositionNum = 0;
		// ����� �ڵ带 ���� �Է¹޴´�.
		// �̴� ������ ������ if ���� �ݺ������� ���̴� ���� �پ�� ���� ���Ǿ���.
		// �ʹ� �̵� �߾ӿ� �ڸ���� �ִ��� ������ ���� ȹ���Ѵ�.
		// �ѹ����� ������ �����Ѵ�.
		/*
		 * TODO#1
		 * ���� �ʹ� �αٿ� �߾� �ٷ� ���� �����Ͽ�, �ѹ����� ����.
		 * Ƚ���� ���� �̵��ϸ� �����Ѵ�.
		 * �ٷ� �߾��� ����ü�� ������ ������ �����Ѵ�. ���� �� �ٷ� �ֺ��� �ɵ����� �����Ѵ�.
		 * �̴� �߾� �α��� ��ü���� ����Ȯ���� ���� �Ӹ� �̳��� ���� ȹ�������� ���� �� �ֱ� �����̴�.
		 */
		if( turnInfo.turnNumber < 3)
			result = DirectionCode.Down;
		else if(turnInfo.turnNumber < 5)
			result = DirectionCode.Right;
		else if(turnInfo.turnNumber < 7)
			result = DirectionCode.Up;
		else if(turnInfo.turnNumber < 9)
			result = DirectionCode.Left;
		else if(turnInfo.turnNumber < 11)
			result = DirectionCode.Down;
		/*
		 * TODO#2
		 * ������ ������ �ڵ�� ���� ���Ŀ� ���� �̵������� �Ǵ��ϴ� �κ��̴�.
		 * �� �κ��� �����ϱ� ���� �ִ��� ����ü�� ���� ������ �̵��Ѵ�.
		 * 75�� ������ ����̴�. 
		 */
		else if (turnInfo.turnNumber < 85){
			// ��ü �����ǿ��� ����ü�� ���� �޾ƿͼ� ���߿� ����ġ ����� �Ͽ� �̵��� �� �ֵ��� �Ѵ�.
			// �� �κ��� ���߿� ���� ����ü�� ���� ���� ��󳻴µ� ���δ�.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					counts[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					counts[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					counts[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					counts[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			// ��ȣ�� ���� ���� //
			// 0 : up
			// 1 : down
			// 2 : left
			// 3 : right
			//=============//
			// ����ü�� ���� ���� �������� �̵��ϱ� ���� �ִ밪�� �޴´�.
			for(int i = 1;i < 3;i++){
				if(counts[PositionNum] > counts[i]){
					PositionNum = i;
				}
			}
			// TODO #2-1 ���� �پ��� �� �̸� ����Ͽ� �����ϴ� ���� �ۼ�.
			// �P���� ������ �ʵ��� �Ѵ�.
			// ADD #1 ���� 4 �����̴� ���� �ٴ� ����� Ư���� ����̹Ƿ� if ���� �߰��ߴ�.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_inf = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( up_inf > left_inf ){
							result = DirectionCode.Left;
						}
						else{
							result = DirectionCode.Up;
						}
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(left_inf > down_inf ){
							
							result = DirectionCode.Down;
						}
						else{
							result = DirectionCode.Left;
						}
						
					}
						
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_inf = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(up_inf > right_inf){
							result = DirectionCode.Right;
						}else{
							
							result = DirectionCode.Up;
						}
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( down_inf > right_inf ){
							result = DirectionCode.Right;
						}
						else
							result = DirectionCode.Down;
					}
					
				}
			}
			// TODO #2-2 �� �� ���� �پ��� ����� �����̴�.
			else if( myInfo.position.column % 12 == 0){
				Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
				Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
				int infect_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_up > 0 && infect_down > 0){
					if(myInfo.position.column == 0){
						result = DirectionCode.Right;
					}
					else{
						result = DirectionCode.Left;
					}
						
				}
				else if ( infect_up == 0 )
					result = DirectionCode.Up;
				else
					result = DirectionCode.Down;
			}
			// TODO #2-3 ���Ʒ��� �پ��� ����� �����̴�.
			else if( myInfo.position.row % 12 == 0){
				Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
				Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
				int infect_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_right > 0 && infect_left > 0){
						if( myInfo.position.row == 0 ){
							result = DirectionCode.Down;
						}
						else{
							result = DirectionCode.Up;
						}
				}
				else if (infect_right == 0){
					result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Left;
			}
			// TODO #2-4 �ٴ� ��찡 �ƴѰ�� ����ġ�� ���� �̵��Ѵ�.
			else{
				if(PositionNum == 0){
					result = DirectionCode.Up;
				}
				else if(PositionNum == 1){
					result = DirectionCode.Down;
				}
				else if (PositionNum == 2){
					result = DirectionCode.Left;
				}
				else{
					result = DirectionCode.Right;
				}
			}
		}
		// TODO #3 �� �κ��� ������ ����ü�� �Ǿ� ���������� ���� �Ա� �����̴�.
		// ���� ����ü�� �ǵ��� �����ڰ� ����ü�� ���󰡵��� �����Ѵ�.
		// 75�� ����
		else{
			int[] count = new int[4];
			int most_like_infect = 0;
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					count[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					count[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					count[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					count[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
			// ��ȣ�� ���� ���� //
			// 0 : up
			// 1 : down
			// 2 : left
			// 3 : right
			//=============//
			// ���� ū �������� �̵��ϱ� ���� �ִ밪�� �޴´�.
			for(int i = 1;i < 3;i++){
				if(count[most_like_infect] < count[i]){
					most_like_infect = i;
				}
			}
			// TODO #3-1 ���� �پ��� �� �̸� ����Ͽ� �����ϴ� ���� �ۼ�.
			// �P���� ������ �ʵ��� �Ѵ�.
			// ADD #1 ���� 4 �����̴� ���� �ٴ� ����� Ư���� ����̹Ƿ� if ���� �߰��ߴ�.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_inf = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( up_inf > left_inf ){
							result = DirectionCode.Up;
						}
						else
							result = DirectionCode.Left;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(left_inf > down_inf ){
							result = DirectionCode.Left;
						}
						else
							result = DirectionCode.Down;
										
					}
										
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_inf = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_inf = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
						if(up_inf > right_inf){
							result = DirectionCode.Up;
						}else
							result = DirectionCode.Right;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_inf = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
						if( down_inf > right_inf ){
							result = DirectionCode.Down;
						}
						else
							result = DirectionCode.Right;
					}					
				}
			}
			// TODO #3-2 �� �� ���� �پ��� ����� �����̴�.
			else if( myInfo.position.column % 12 == 0){
				Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
				Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
				int infect_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_up == 0 && infect_down == 0){
					if( myInfo.position.column == 0){
						result = DirectionCode.Right;
					}
					else{
						result = DirectionCode.Left;
					}
				}
				else if ( infect_up > 0 )
					result = DirectionCode.Up;
				else
					result = DirectionCode.Down;
			}
			// TODO #3-3 ���Ʒ��� �پ��� ����� �����̴�.
			else if( myInfo.position.row % 12 == 0){
				Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
				Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
				int infect_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Infected);
				int infect_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Infected);
				if( infect_right == 0 && infect_left == 0){
					if( myInfo.position.row == 0 ){
						result = DirectionCode.Down;
					}
					else{
						result = DirectionCode.Up;
					}
				}
				else if (infect_right > 0){
					result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Left;
			}
			// TODO #3-4 �ٴ� ��찡 �ƴѰ�� ����ġ�� ���� �̵��Ѵ�.
			else{
				if(most_like_infect == 0){
					result = DirectionCode.Up;
				}
				else if(most_like_infect == 1){
					result = DirectionCode.Down;
				}
				else if (most_like_infect == 2){
					result = DirectionCode.Left;
				}
				else{
					result = DirectionCode.Right;
				}
			}
		}
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// ����ü �����϶��� �� �¿� ��ȭ ��ǥ�� �޾Ƽ� ���� �� ��ġ���� ��� �ൿ������ �Ǵ��Ѵ�.
		int[] value_player_imp = new int[4];
		/* TODO #4 INFECTMOVE
		 * �� �κ��� 75�� ������ ��� �������·� ������ �ǵ��� ��������
		 * ����ü�� �پ �ױ� ���� �ڵ��̴�.
		 * �������� ��� �� ��ǥ�� ���� ������ ���� �޾ƿ´�.
		 * ���� ����ġ�� ���� �Ŀ� �� �������� �̵��Ͽ� �� �ൿ�� �Ѵ�.
		 */
		DirectionCode result = null;
		// Note : �� ���� 0�̸� �⵵�� ��� 2�ϸ��� ����Ѵ�.
		if( turnInfo.turnNumber < 80 ){
			int most_imp_value = 0;
			//��ȥ�� �ƴ� ��� ��ü�� �� ���� �󸶳� �ִ��� Ȯ���Ѵ�. 0�ϰ�� �⵵�� �����Ѵ�.
			int my_point = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player->player.state == StateCode.Corpse);
			// ���̴� �������� ����ü�� �ִ��� ������ Ȯ���ϰ�, ���� ������� �̵��Ҽ� �ֵ��� ����ġ�� ����Ѵ�.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 0 ; row < myInfo.position.row ; row++) {
					value_player_imp[0] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
				for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
					value_player_imp[1] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
			}
			for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
				for (int column = 0 ; column < myInfo.position.column ; column++) {
					value_player_imp[2] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
				for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
					value_player_imp[3] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
				}
			}
			// �̴� ���� ����ġ�� ū �α��� �����Ѵ�.
			for(int i = 1;i < 4 ; i++){
				if(value_player_imp[most_imp_value] < value_player_imp[i] ){
					most_imp_value = i;
				}
			}
			
			
			if(my_point != 0){
				// �� �κ��� ���� �پ��� ��� ����� ���� �ڵ��̴�.
				if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
					if( myInfo.position.column / 12 != 0 ){
						Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
						int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( myInfo.position.row / 12 != 0 ){
							Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
							int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if( up_corp != 0 || left_corp != 0){
								if( up_corp > left_corp){
									result = DirectionCode.Left;
								}else
									result = DirectionCode.Up;
							}else
								result = DirectionCode.Stay;
						}
						else{
							Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
							int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if( down_corp != 0 || left_corp != 0){
								if( down_corp > left_corp){
									result = DirectionCode.Left;
								}else
									result = DirectionCode.Down;
							}else
								result = DirectionCode.Stay;
						}
							
					}else{
						Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
						int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( myInfo.position.row / 12 != 0 ){
							Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
							int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if(up_corp != 0 || right_corp != 0){
								if(up_corp > right_corp){
									result = DirectionCode.Right;
								}else
									result = DirectionCode.Up;
							}else
								result = DirectionCode.Stay;
						}
						else{
							Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
							int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
							if(down_corp != 0 || right_corp != 0){
								if(down_corp > right_corp){
									result = DirectionCode.Right;
								}else
									result = DirectionCode.Down;
							}else
								result = DirectionCode.Stay;
						}
						
					}
				}
				// TODO #4-1 �¿�� �پ��� ����� �����̴�.
				else if( myInfo.position.column % 12 == 0){
					Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
					Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
					int notinf_up = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state != StateCode.Infected);
					int notinf_down = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state != StateCode.Infected);
					if( notinf_up == 0 && notinf_down == 0){
						if(myInfo.position.column == 0){
							result = DirectionCode.Right;
						}
						else
							result = DirectionCode.Left;
					}
					else if( notinf_up > 0 )
						result = DirectionCode.Up;
					else
						result = DirectionCode.Down;
				}
				// TODO #4-2 ���Ʒ��� �پ��� ����� �����̴�.
				else if( myInfo.position.row % 12 == 0){
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int notinf_right = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state != StateCode.Infected);
					int notinf_left = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state != StateCode.Infected);
					if( notinf_right == 0 && notinf_left == 0){
						if( myInfo.position.row == 0 ){
							result = DirectionCode.Down;
						}
						else
							result = DirectionCode.Up;
					}
					else if (notinf_right > 0){
						result = DirectionCode.Right;
					}
					else
						result = DirectionCode.Left;
				}
				else{
					if(most_imp_value == 0){
						result = DirectionCode.Right;
					}else if(most_imp_value == 1){
						result = DirectionCode.Left;
					}else if(most_imp_value == 2){
						result = DirectionCode.Up;
					}else 
						result = DirectionCode.Down;
				}
			}
			else{
				result = DirectionCode.Stay;
				// Note : �� �κ��� �� �ڸ����� �⵵�� �帮�� �κ��Դϴ�.
			}
		}
		/* TODO #GET_SCORE_FROM_CORPS_13
		 * �� ���� ��ü�� ������ �� 13�� �̻��� �Ǿ��� ��� �ൿ�ϴ� ��ġ�̴�.
		 * 13���� ������ ������ �縸ŭ ������ �԰� �ٷ� ��ȭ�⵵�� �帰��.
		 * ���� �� 3~4���� ������ ���� ������ ���� ȸ���Ϸ��� �Ѵ�.
		 */
		else if ((turnInfo.turnNumber - 84) % 9 == 0  && turnInfo.turnNumber < 110 ){
			// �� ���� �������� ��ü�� ������ ���� ��� ���� 2�� ��ȭ�⵵�� �õ��Ѵ� �����̴�.
			// ��ü�� ������� �������� Ż���ϰų� ��ȭ�⵵�� ���� �ϴ� ���� �� 3������ ���������� �����Ǿ� �ִ�.
			// ������ �������� �����¿� �� �÷��̾� ��ġ�� ��ü�� ���� �޴� �������̴�.
			int corp_my = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player->player.state == StateCode.Corpse);
			// ��� 1. �� �� 4���� ������� ��ü�� ���� ���� ���� ��ȭ�⵵�� �Ѵ�.
			if( myInfo.position.column % 12 == 0 && myInfo.position.row % 12 == 0 ){
				if( myInfo.position.column / 12 != 0 ){
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( up_corp != 0 || left_corp != 0){
							if( up_corp > left_corp){
								result = DirectionCode.Left;
							}else
								result = DirectionCode.Up;
						}else
							result = DirectionCode.Stay;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if( down_corp != 0 || left_corp != 0){
							if( down_corp > left_corp){
								result = DirectionCode.Left;
							}else
								result = DirectionCode.Down;
						}else
							result = DirectionCode.Stay;
					}
						
				}else{
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( myInfo.position.row / 12 != 0 ){
						Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
						int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if(up_corp != 0 || right_corp != 0){
							if(up_corp > right_corp){
								result = DirectionCode.Right;
							}else
								result = DirectionCode.Up;
						}else
							result = DirectionCode.Stay;
					}
					else{
						Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
						int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
						if(down_corp != 0 || right_corp != 0){
							if(down_corp > right_corp){
								result = DirectionCode.Right;
							}else
								result = DirectionCode.Down;
						}else
							result = DirectionCode.Stay;
					}
					
				}
			}
			// TODO #4-2 �¿�� �پ������� �����̴�.
			else if( myInfo.position.column % 12 == 0){
				if( corp_my != 0){
					Point pos_up = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
					Point pos_down = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
					int up_corp = cells[pos_up.row][pos_up.column].CountIf_Players(player->player.state == StateCode.Corpse);
					int down_corp = cells[pos_down.row][pos_down.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( up_corp > 0 && down_corp > 0  ){
						if (myInfo.position.column == 0)
							result = DirectionCode.Right;
						else
							result = DirectionCode.Left;
					}
					else if( up_corp == 0 ){
						result = DirectionCode.Up;
					}
					else
						result = DirectionCode.Down;
				}
				else
					result = DirectionCode.Stay;
			}
			// TODO #4-3 ���Ʒ��� �پ��� ����� �����̴�.
			else if( myInfo.position.row % 12 == 0){
				if( corp_my != 0){
					Point pos_right = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
					Point pos_left = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
					int left_corp = cells[pos_left.row][pos_left.column].CountIf_Players(player->player.state == StateCode.Corpse);
					int right_corp = cells[pos_right.row][pos_right.column].CountIf_Players(player->player.state == StateCode.Corpse);
					if( right_corp > 0 && left_corp > 0  ){
						if (myInfo.position.row == 0)
							result = DirectionCode.Down;
						else
							result = DirectionCode.Up;
					}
					else if( left_corp == 0 ){
						result = DirectionCode.Left;
					}
					else
						result = DirectionCode.Right;
				}
				else
					result = DirectionCode.Stay;
				
			}
			// ��� 3 : �ι�°  ���� �Ϲ����� ����ü�� ���·ν�, ��ü�� ���� �ʴ� ��� �ٷ� ��ȭ�⵵�� �帰��.
			// corp_values�� �ൿ �ڵ� //
			// up : 0
			// down : 1
			// right : 2
			// left : 3
			// ========= //
			else{
				int least_value = 0;
				for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
					for (int row = 0 ; row < myInfo.position.row ; row++) {
						value_player_imp[0] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
					for (int row = myInfo.position.row ; row < Constants.Classroom_Height ; row++) {
						value_player_imp[1] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
				}
				for (int row = 0 ; row < Constants.Classroom_Height ; row++) {
					for (int column = 0 ; column < myInfo.position.column ; column++) {
						value_player_imp[2] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
					for (int column = myInfo.position.column ; column < Constants.Classroom_Width ; column++) {
						value_player_imp[3] += cells[row][column].CountIf_Players(player->player.state != StateCode.Infected || player.state != StateCode.Survivor);
					}
				}
				for(int i = 1;i < 4;i++){
					if(value_player_imp[least_value] < value_player_imp[i]){
						least_value = i;
					}
				}
				if(corp_my != 0){
					switch (least_value){
						case 0:
							result = DirectionCode.Up;
							break;
						case 1:
							result = DirectionCode.Down;
							break;
						case 2:
							result = DirectionCode.Right;
							break;
						case 3:
							result = DirectionCode.Left;
							break;
					}
				}
				else
					result = DirectionCode.Stay;
			}
			
		}
		
		else{
			result = DirectionCode.Stay;
		}
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return result;
	}		

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 * 
			 */
		}
		
	}

	@Override
	public Point Soul_Spawn()
	{
		int result_row = 0;
		int result_column = 0;
		int maxrow = 0,maxcol = 0;
		// TODO #5-1 �ʱ� ���� �����̴�.
		if (turnInfo.turnNumber < 11){
			result_row = 5;
			result_column = 5;
		}
		// TODO #5-2 85�� ������ ���, ��ü �������� ���� �����ִ� ���� ���� �����Ѵ�.
		else if( turnInfo.turnNumber < 85){
			int[][] all_players = new int[13][13];
			int minrow = 0,mincol = 0;
			// ��ü �����ǿ��� ����ü�� ���� �޾ƿͼ� ���߿� ����ġ ����� �Ͽ� �̵��� �� �ֵ��� �Ѵ�.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 1 ; row < Constants.Classroom_Height ; row++) {
					all_players[row][column] += cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);
					if( all_players[minrow][mincol] < all_players[row][column]){
						minrow = row;
						mincol = column;
					}
				}
			}
			result_row = minrow;
			result_column = mincol;
		}
		// TODO #5-3 85�� ���Ŀ��� ��ü�� ����ü�� ���� ������ ȹ���ϱ� ���� ����ü�� ���� ���� �پ���.
		else{
			int[][] all_players = new int[13][13];
			// ��ü �����ǿ��� ����ü�� ���� �޾ƿͼ� ���߿� ����ġ ����� �Ͽ� �̵��� �� �ֵ��� �Ѵ�.
			for (int column = 0 ; column < Constants.Classroom_Width ; column++) {
				for (int row = 1 ; row < Constants.Classroom_Height ; row++) {
					all_players[row][column] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected || player.state == StateCode.Corpse);
					if( all_players[maxrow][maxcol] < all_players[row][column]){
						maxrow = row;
						maxcol = column;
					}
				}
			}
			result_row = maxrow;
			result_column = maxcol;
		}
		
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		return new Point(result_row, result_column);
	}
}
