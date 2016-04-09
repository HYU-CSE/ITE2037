package l4g2ep1.customplayers;

import l4g2ep1.*;
import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Player_������ extends Player
{
	class studentT{
		State state;
		int hitpoint;
		Point position;
		int validity;
		Action.TypeCode type;
		Point location_from;
	}
	studentT[][] myMemory = new studentT[Constants.Soul_Interval_Respawn][Constants.Total_Players];
	int presTOT = 0, preiTOT = 0;
	boolean bombChance = false;
	public Player_������()
	{
		name = "������";	// TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;				// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ� �ƴ� ��� false�� �μ���.
		receiveOthersInfo_detected = true;
		receiveActions = true;
		
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		int survivors;
		AgingAndGether();
		
		if(gameInfo.GetCurrentTurnNumber() == 1) return DirectionCode.Right;
		else if(gameInfo.GetCurrentTurnNumber() == 2) return DirectionCode.Right;
		else if(gameInfo.GetCurrentTurnNumber() == 3) return DirectionCode.Down;
		else if(gameInfo.GetCurrentTurnNumber() == 4) return DirectionCode.Down;
		else if(gameInfo.GetCurrentTurnNumber() == 5) return DirectionCode.Left;
		else if(gameInfo.GetCurrentTurnNumber() == 6) return DirectionCode.Left;
		else if(gameInfo.GetCurrentTurnNumber() == 7) return DirectionCode.Up;
		else if(gameInfo.GetCurrentTurnNumber() == 8){ // (3,4)
			survivors = 0;
			for(int i = 0; i < Constants.Total_Players; i++){
				if(myMemory[0][i].validity <= Constants.Total_Turns) survivors++;
			}
			if(survivors*4 > Constants.Total_Players*3){
				acceptDirectInfection = false;
				return DirectionCode.Left;
			}
			else{
				acceptDirectInfection = true;
				return DirectionCode.Up;
			}
		}
		else if(gameInfo.GetCurrentTurnNumber() == 9){
			if(acceptDirectInfection == false) return DirectionCode.Left;
			else return DirectionCode.Right;
		}
		else if(gameInfo.GetCurrentTurnNumber() == 10){
			if(acceptDirectInfection == false) return DirectionCode.Down; // (1,5)
			else return DirectionCode.Right; // (5,3)
		}
		else if(gameInfo.GetCurrentTurnNumber() == 11){ // ���⼭���� ȸ��, ���� ����
			if(acceptDirectInfection == false){
				//���� �Ʒ� ���� ������ ������ ����.
				boolean directions[] = {true, true, true, true}; // [0] : �� , [1] : �Ʒ�, [2] : ����, [3] : ������
				for(int i = 0; i < Constants.Total_Players; i++){
					if(myMemory[0][i].validity == 0 && GetDistance(myMemory[0][i].position, myInfo.GetPosition()) == 2){
						Point myNext = new Point(), othersMove = new Point(), othersNext = new Point();
						othersMove.x = myMemory[0][i].position.x - myMemory[0][i].location_from.x;
						othersMove.y = myMemory[0][i].position.y - myMemory[0][i].location_from.y;
						othersNext.x = myMemory[0][i].position.x + othersMove.x;
						othersNext.y = myMemory[0][i].position.y + othersMove.y;
						myNext.x = myInfo.GetPosition().x;
						myNext.y = myInfo.GetPosition().y - 1;
						if(myNext.equals(othersNext)){
							directions[0] = false;
						}
						myNext.x = myInfo.GetPosition().x;
						myNext.y = myInfo.GetPosition().y + 1;
						if(myNext.equals(othersNext)){
							directions[1] = false;
						}
						myNext.x = myInfo.GetPosition().x - 1;
						myNext.y = myInfo.GetPosition().y;
						if(myNext.equals(othersNext)){
							directions[2] = false;
						}
						myNext.x = myInfo.GetPosition().x + 1;
						myNext.y = myInfo.GetPosition().y;
						if(myNext.equals(othersNext)){
							directions[3] = false;
						}
					}
				}
				if(directions[2] == true && IsValidMove(DirectionCode.Left) == true) return DirectionCode.Left;
				else if(directions[1] == true && IsValidMove(DirectionCode.Down) == true) return DirectionCode.Down;
				else if(directions[0] == true && IsValidMove(DirectionCode.Up) == true) return DirectionCode.Up;
				else if(directions[3] == true && IsValidMove(DirectionCode.Right) == true) return DirectionCode.Right;
				else return DirectionCode.Right;
			} // �ִ��� �������� �������� ��, �ٸ� �����ڿ� ��ġ�°� ���ϴ� �ڵ�.
			else{
				int directions[] = {0, 0, 0, 0}; // [0] : �� , [1] : �Ʒ�, [2] : ����, [3] : ������
				for(int i = 0; i < Constants.Total_Players; i++){
					if(myMemory[0][i].validity == 0 && GetDistance(myMemory[0][i].position, myInfo.GetPosition()) == 2){
						Point myNext = new Point(), othersMove = new Point(), othersNext = new Point();
						othersMove.x = myMemory[0][i].position.x - myMemory[0][i].location_from.x;
						othersMove.y = myMemory[0][i].position.y - myMemory[0][i].location_from.y;
						othersNext.x = myMemory[0][i].position.x + othersMove.x;
						othersNext.y = myMemory[0][i].position.y + othersMove.y;
						myNext.x = myInfo.GetPosition().x;
						myNext.y = myInfo.GetPosition().y - 1;
						if(myNext.equals(othersNext)){
							directions[0]++;
						}
						myNext.x = myInfo.GetPosition().x;
						myNext.y = myInfo.GetPosition().y + 1;
						if(myNext.equals(othersNext)){
							directions[1]++;
						}
						myNext.x = myInfo.GetPosition().x - 1;
						myNext.y = myInfo.GetPosition().y;
						if(myNext.equals(othersNext)){
							directions[2]++;
						}
						myNext.x = myInfo.GetPosition().x + 1;
						myNext.y = myInfo.GetPosition().y;
						if(myNext.equals(othersNext)){
							directions[3]++;
						}
					}
				} // �굵 ���� �Ʒ� ���� ������ ������ ���� ��.
				int max = 0;
				for(int i = 0; i < 3; i ++){
					if(max < directions[i]) max = directions[i];
				}
				if(max == directions[2]) return DirectionCode.Left;
				else if(max == directions[1]) return DirectionCode.Down;
				else if(max == directions[0]) return DirectionCode.Up;
				else if(max == directions[3]) return DirectionCode.Right;
			} // ��� �������� ���ۉ��� �� �������� �ִ��� ��ġ�� ���� ��.
		}
		else{ // ���⼭���� �Ϲ����� ��� ����.
			int directions[] = {0, 0, 0, 0}; // �� �Ʒ� ���� ������ ��.
			int[] survivor = new int[4];
			int[] others = new int[4];
			int[] weight = new int[4];
			Vector whereIsHe;
			if(IsValidMove(DirectionCode.Up) == false) directions[0] = 9999;
			if(IsValidMove(DirectionCode.Down) == false) directions[1] = 9999;
			if(IsValidMove(DirectionCode.Left) == false) directions[2] = 9999;
			if(IsValidMove(DirectionCode.Right) == false) directions[3] = 9999; // �Ұ����� ���⿡�� direction�� 9999�� �༭ ������ ��.
			for(PlayerInfo he : othersInfo_detected){
					whereIsHe = GetDistanceVectorBetweenPlayers(he);
					if(myMemory[0][he.GetID()].state == State.Infected){
						if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -2){
							directions[0] += 100; // 2ĭ ���������϶� ������ �� Ȯ���� 50����.
							if(!(myMemory[0][he.GetID()].location_from.y + 1 == myInfo.GetPosition().y && myMemory[0][he.GetID()].location_from.x == myInfo.GetPosition().x)){
								directions[0] += 100; // �׷��� �̳��� �ٸ����� �����ؼ� ���� �ö󰡴� ���� �ƴ϶�� Ȯ���� ������.
							}
						}
						else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == 2){
							directions[1] += 100;
							if(!(myMemory[0][he.GetID()].location_from.y - 1 == myInfo.GetPosition().y && myMemory[0][he.GetID()].location_from.x == myInfo.GetPosition().x)){
								directions[1] += 100;
							}
						}
						else if(whereIsHe.x_offset == -2 && whereIsHe.y_offset == 0){
							directions[2] += 100;
							if(!(myMemory[0][he.GetID()].location_from.y == myInfo.GetPosition().y && myMemory[0][he.GetID()].location_from.x + 1 == myInfo.GetPosition().x)){
								directions[2] += 100;
							}
						}
						else if(whereIsHe.x_offset == 2 && whereIsHe.y_offset == 0){
							directions[3] += 100;
							if(!(myMemory[0][he.GetID()].location_from.y == myInfo.GetPosition().y && myMemory[0][he.GetID()].location_from.x - 1 == myInfo.GetPosition().x)){
								directions[3] += 100;
							}
						}
						else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == -1){
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.x - myMemory[0][he.GetID()].location_from.x == 1) directions[0] += 50;
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.y - myMemory[0][he.GetID()].location_from.y == 1) directions[2] += 50;
						}
						else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == -1){
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.x - myMemory[0][he.GetID()].location_from.x == -1) directions[0] += 50;
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.y - myMemory[0][he.GetID()].location_from.y == 1) directions[3] += 50;
						}
						else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 1){
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.x - myMemory[0][he.GetID()].location_from.x == 1) directions[1] += 50;
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.y - myMemory[0][he.GetID()].location_from.y == -1) directions[2] += 50;
						}
						else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 1){
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.x - myMemory[0][he.GetID()].location_from.x == -1) directions[1] += 50;
							if(myMemory[0][he.GetID()].type == Action.TypeCode.Move && myMemory[0][he.GetID()].position.y - myMemory[0][he.GetID()].location_from.y == -1) directions[3] += 50;
						} // �밢�� ���ʿ� �ִ� ���� �������� �´ٰ� ������. �ֳ��ϸ� ��κ��� ��� �����ڴ� ���� �� �� ������ �����ϰ� ���̷� ���� ���������ϱ�.
					}
					else if(myMemory[0][he.GetID()].state == State.Corpse){ // ��ü�� ���ϴ� �ڵ�.
						if(myMemory[1][he.GetID()].state == State.Corpse){
							if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -1) directions[0] += 200;
							else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == +1) directions[1] += 200;
							else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 0) directions[2] += 200;
							else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 0) directions[3] += 200;
						}
						if(myMemory[2][he.GetID()].state == State.Corpse){
							if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -1) directions[0] += 100;
							else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == +1) directions[1] += 100;
							else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 0) directions[2] += 100;
							else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 0) directions[3] += 100;
						}
					}
					if(whereIsHe.y_offset < 0){
						if(whereIsHe.GetDistance_X() <= whereIsHe.GetDistance_Y()){
							if(he.GetState() == State.Survivor) survivor[0]++;
							else others[0]++;
						}
					}
					if(whereIsHe.y_offset > 0){
						if(whereIsHe.GetDistance_X() <= whereIsHe.GetDistance_Y()){
							if(he.GetState() == State.Survivor) survivor[1]++;
							else others[1]++;
						}
					}
					if(whereIsHe.x_offset < 0){
						if(whereIsHe.GetDistance_X() >= whereIsHe.GetDistance_Y()){
							if(he.GetState() == State.Survivor) survivor[2]++;
							else others[2]++;
						}
					}
					if(whereIsHe.x_offset > 0){
						if(whereIsHe.GetDistance_X() >= whereIsHe.GetDistance_Y()){
							if(he.GetState() == State.Survivor) survivor[3]++;
							else others[3]++;
						}
					}
				}
			for(int i = 0; i < 4; i++){
				weight[i] = survivor[i] * others[i];
			}
			if(weight[0] == 0 && weight[1] == 0 && weight[2] == 0 && weight[3] == 0){
				for(int i = 0; i < 4; i++){
					weight[i] = survivor[i];
				}
			}
			if(weight[0] == 0 && weight[1] == 0 && weight[2] == 0 && weight[3] == 0){
				for(int i = 0; i < 4; i++){
					weight[i] = others[i];
				}
			}
			if(survivor[0] == 0 && survivor[1] == 0 && survivor[2] == 0 && survivor[3] == 0 && others[0] == 0 && others[1] == 0 && others[2] == 0 && others[3] == 0){
				for(int i = 0; i < Constants.Total_Players; i++){
					Point whereWasHe = new Point();
					whereWasHe.x = myMemory[0][i].position.x - myInfo.GetPosition().x;
					whereWasHe.y = myMemory[0][i].position.y - myInfo.GetPosition().y;
					if(whereWasHe.y <= 0){
						if(myMemory[0][i].state == State.Survivor) survivor[0]++;
						else others[0]++;
					}
					if(whereWasHe.y >= 0){
						if(myMemory[0][i].state == State.Survivor) survivor[0]++;
						else others[0]++;
					}
					if(whereWasHe.x <= 0){
						if(myMemory[0][i].state == State.Survivor) survivor[0]++;
						else others[0]++;
					}
					if(whereWasHe.x >= 0){
						if(myMemory[0][i].state == State.Survivor) survivor[0]++;
						else others[0]++;
					}	
					
				}
				for(int i = 0; i < 4; i++){
					weight[i] = survivor[i] * others[i];
				}
			}
			int[] rank = new int[4];
			for(int i = 0; i < 4; i++){
				int max = -1;
				for(int j = 0; j < 4; j++){
					if(max < weight[j]){
						max = weight[j];
					}
				}
				for(int j = 0; j < 4; j++){
					if(weight[j] == max){
						rank[i] = j; // i������ j���� �̶�� ��.
						weight[j] = -2;
						break;
					}
				}
			}
			if(gameInfo.GetCurrentTurnNumber() > 100 && myScore.GetSurvivor_Total_Spots() > myScore.GetInfected_Total_Infects() * 2){
				for(int i = 0; i < 4; i++){
					int max = -1;
					for(int j = 0; j < 4; j++){
						if(max < survivor[j]){
							max = survivor[j];
						}
					}
					for(int j = 0; j < 4; j++){
						if(survivor[j] == max){
							rank[i] = j; // i������ j���� �̶�� ��.
							survivor[j] = -2;
							break;
						}
					}
				}
				for(int i = 0; i < 4; i++){
					if(directions[rank[i]] < 9999){
						if(rank[i] == 0) return DirectionCode.Up;
						if(rank[i] == 1) return DirectionCode.Down;
						if(rank[i] == 2) return DirectionCode.Left;
						if(rank[i] == 3) return DirectionCode.Right;
					}
				}
			}
			for(int i = 0; i < 2; i++){ // �Ϻη� 4�� �ƴ϶� 3���� �ذ���. �־��� �������δ� ���� ����.
				if(directions[rank[i]] < 100){
					if(rank[i] == 0) return DirectionCode.Up;
					if(rank[i] == 1) return DirectionCode.Down;
					if(rank[i] == 2) return DirectionCode.Left;
					if(rank[i] == 3) return DirectionCode.Right;
				}
			}
			for(int i = 0; i < 3; i++){
				if(directions[rank[i]] < 200){
					if(rank[i] == 0) return DirectionCode.Up;
					if(rank[i] == 1) return DirectionCode.Down;
					if(rank[i] == 2) return DirectionCode.Left;
					if(rank[i] == 3) return DirectionCode.Right;
				}
			}
			for(int i = 0; i < 4; i++){
				if(directions[rank[i]] < 400){
					if(rank[i] == 0) return DirectionCode.Up;
					if(rank[i] == 1) return DirectionCode.Down;
					if(rank[i] == 2) return DirectionCode.Left;
					if(rank[i] == 3) return DirectionCode.Right;
				}
			}
			
			for(int i = 0; i < 4; i++){
				if(directions[rank[i]] < 9999){
					if(rank[i] == 0) return DirectionCode.Up;
					if(rank[i] == 1) return DirectionCode.Down;
					if(rank[i] == 2) return DirectionCode.Left;
					if(rank[i] == 3) return DirectionCode.Right;
				}
			}
		}
		// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������.
		return DirectionCode.Down;
	}

	@Override
	public void Corpse_Stay()
	{
		AgingAndGether();
		preiTOT = myScore.GetInfected_Total_Infects();
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public DirectionCode Infected_Move() // ������ ������ ���� ����
	{
		int directions[] = {0, 0, 0, 0, 0};
		boolean isThereACorpse[] = {false, false, false, false, false};
		AgingAndGether();
		for(PlayerInfo target : othersInfo_withinSight){
			if(myMemory[0][target.GetID()].validity != 0){
				myMemory[2][target.GetID()] = myMemory[1][target.GetID()];
				myMemory[1][target.GetID()] = myMemory[0][target.GetID()];
				myMemory[0][target.GetID()].state = target.GetState();
				myMemory[0][target.GetID()].hitpoint = target.GetHitPoint();
				myMemory[0][target.GetID()].position = target.GetPosition();
				myMemory[0][target.GetID()].validity = 0;
				for(Action act : actions){
					if(target.GetID() == act.GetActorID()){
						myMemory[0][target.GetID()].type = act.GetType();
						myMemory[0][target.GetID()].location_from = act.GetLocation_From();
					}
				}
			}
		}
		Vector whereIsHe;
		if(IsValidMove(DirectionCode.Up) == false) directions[0] = -9999;
		if(IsValidMove(DirectionCode.Down) == false) directions[1] = -9999;
		if(IsValidMove(DirectionCode.Left) == false) directions[2] = -9999;
		if(IsValidMove(DirectionCode.Right) == false) directions[3] = -9999;
		for(PlayerInfo he : othersInfo_withinSight){
			whereIsHe = GetDistanceVectorBetweenPlayers(he);
			if(he.GetState() == State.Survivor){
				if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -2){
					directions[0] += 200;
					Point target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y - 2);
					if(!target.IsValid()) directions[0] += 50;
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y - 2);
					if(!target.IsValid()) directions[0] += 50;
				}
				else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == 2){
					directions[1] += 200;
					Point target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y + 2);
					if(!target.IsValid()) directions[1] += 50;
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y + 2);
					if(!target.IsValid()) directions[1] += 50;
				}
				else if(whereIsHe.x_offset == -2 && whereIsHe.y_offset == 0){
					directions[2] += 200;
					Point target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y - 1);
					if(!target.IsValid()) directions[2] += 50;
					target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y + 1);
					if(!target.IsValid()) directions[2] += 50;
				}
				else if(whereIsHe.x_offset == 2 && whereIsHe.y_offset == 0){
					directions[3] += 200;
					Point target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y - 1);
					if(!target.IsValid()) directions[0] += 50;
					target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y + 1);
					if(!target.IsValid()) directions[0] += 50;
				}
				else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == -1){ // ���� ��
					directions[0] += 100;
					directions[2] += 100;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y - 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
					if(infect == true) directions[0] -= 90;
					}
					infect = false;
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y + 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[2] -= 90;
					}
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y - 2);
					if(!target.IsValid()){
						directions[0] += 50;
						directions[2] += 50;
					}
					target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y - 1);
					if(!target.IsValid()){
						directions[0] += 50;
						directions[2] += 50;
					}
				}
				else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == -1){ // ������ ��
					directions[0] += 100;
					directions[3] += 100;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y - 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[0] -= 90;
					}
					infect = false;
					target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y + 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[3] -= 90;
					}
					target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y - 2);
					if(!target.IsValid()){
						directions[0] += 50;
						directions[3] += 50;
					}
					target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y - 1);
					if(!target.IsValid()){
						directions[0] += 50;
						directions[3] += 50;
					}
				}
				else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 1){ // ���� �Ʒ�
					directions[1] += 100;
					directions[2] += 100;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y + 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[1] -= 90;
					}
					infect = false;
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y - 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[2] -= 90;
					}
					target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y + 2);
					if(!target.IsValid()){
						directions[1] += 50;
						directions[2] += 50;
					}
					target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y - 1);
					if(!target.IsValid()){
						directions[1] += 50;
						directions[2] += 50;
					}
				}
				else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 1){ // ������ �Ʒ�
					directions[1] += 100;
					directions[3] += 100;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y + 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[1] -= 90;
					}
					infect = false;
					target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y - 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[3] -= 90;
					}
					target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y + 2);
					if(!target.IsValid()){
						directions[1] += 50;
						directions[3] += 50;
					}
					target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y + 1);
					if(!target.IsValid()){
						directions[1] += 50;
						directions[3] += 50;
					}
				}
				else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -1){ // ��
					directions[4] += 50;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x, myInfo.GetPosition().y + 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[4] -= 50;
					}
					else{
						infect = false;
						target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y - 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						infect = false;
						target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y - 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						target = new Point(myInfo.GetPosition().x, myInfo.GetPosition().y - 2);
						if(!target.IsValid()) if(infect == true) directions[4] += 25;
					}
				}
				else if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == 1){ // �Ʒ�
					directions[4] += 50;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x, myInfo.GetPosition().y - 1);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[4] -= 50;
					}
					else{
						infect = false;
						target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y + 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						infect = false;
						target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y + 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						target = new Point(myInfo.GetPosition().x, myInfo.GetPosition().y + 2);
						if(!target.IsValid()) if(infect == true) directions[4] += 25;
					}
				}
				else if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 0){ // ����
					directions[4] += 50;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[4] -= 50;
					}
					else{
						infect = false;
						target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y - 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
						if(infect == true) directions[4] += 25;
						}
						infect = false;
						target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y + 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						target = new Point(myInfo.GetPosition().x - 2, myInfo.GetPosition().y);
						if(!target.IsValid()) if(infect == true) directions[4] += 25;
					}
				}
				else if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 0){ // ������
					directions[4] += 50;
					boolean infect = false;
					Point target = new Point(myInfo.GetPosition().x - 1, myInfo.GetPosition().y);
					if(target.IsValid()){
						for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
							if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
								infect = true;
							}
						}
						if(infect == true) directions[4] -= 50;
					}
					else{
						infect = false;
						target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y - 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						infect = false;
						target = new Point(myInfo.GetPosition().x + 1, myInfo.GetPosition().y + 1);
						if(target.IsValid()){
							for(int i = 0; i < GetCellInfo(target).GetNumberOfPlayersInTheCell(); i++){
								if(GetCellInfo(target).GetPlayerInfo(i).GetState() == State.Infected){
									infect = true;
								}
							}
							if(infect == true) directions[4] += 25;
						}
						target = new Point(myInfo.GetPosition().x + 2, myInfo.GetPosition().y);
						if(!target.IsValid()) if(infect == true) directions[4] += 25;
					}
				}
			}
			else if(he.GetState() == State.Corpse){
				if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == -1){
					isThereACorpse[0] = true;
				}
				if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == 1){
					isThereACorpse[1] = true;
				}
				if(whereIsHe.x_offset == -1 && whereIsHe.y_offset == 0){
					isThereACorpse[2] = true;
				}
				if(whereIsHe.x_offset == 1 && whereIsHe.y_offset == 0){
					isThereACorpse[3] = true;
				}
				if(whereIsHe.x_offset == 0 && whereIsHe.y_offset == 0){
					isThereACorpse[4] = true;
				}
			}
		}
		int rank[] = new int[5];
		int heaviest = 0;
		for(int j = 0; j < 5; j++){
			if(heaviest < directions[j]){
				heaviest = directions[j];
			}
		}
		if(heaviest < 100){
			for (PlayerInfo he : othersInfo_detected ) {
				whereIsHe = GetDistanceVectorBetweenPlayers(he);
				if (he.GetState() == State.Corpse )	{
					if ( whereIsHe.y_offset < 0 ) directions[0]++;
					if ( whereIsHe.y_offset > 0 ) directions[1]++;
					if ( whereIsHe.x_offset < 0 ) directions[2]++;
					if ( whereIsHe.x_offset > 0 ) directions[3]++;
				}
			}
		}
		int res[] = new int[5];
		for(int i = 0; i < 5; i++){
			res[i] = directions[i];
		}
		for(int i = 0; i < 5; i++){
			int max = -10000;
			for(int j = 0; j < 5; j++){
				if(max < directions[j]){
					max = directions[j];
				}
			}
			for(int j = 0; j < 5; j++){
				if(directions[j] == max){
					rank[i] = j; // i������ j���� �̶�� ��.
					directions[j] = -10001;
					break;
				}
			}
		}
		int currentTotal = myScore.GetInfected_Total_Infects() - preiTOT;
		if(gameInfo.GetCurrentTurnNumber() > 80 && myScore.GetInfected_Total_Infects() > myScore.GetSurvivor_Total_Spots() * 2 && currentTotal > 60){
			if(rank[4] == 4) return DirectionCode.Stay;
			else if(rank[3] == 4) return DirectionCode.Stay;
			else if(rank[2] == 4) return DirectionCode.Stay;
			else{
				for(int i = 4; i >= 0; i--){
					if(rank[i] == 0) if(IsValidMove(DirectionCode.Up)) return DirectionCode.Up;
					if(rank[i] == 1) if(IsValidMove(DirectionCode.Down)) return DirectionCode.Down;
					if(rank[i] == 2) if(IsValidMove(DirectionCode.Left)) return DirectionCode.Left;
					if(rank[i] == 3) if(IsValidMove(DirectionCode.Right)) return DirectionCode.Right;
				}
			}
		}
		if(myInfo.GetHitPoint() <= 3){
			for(int i = 0; i < 3; i++){
				if(isThereACorpse[rank[i]] == true){
					if(rank[i] == 0) return DirectionCode.Up;
					if(rank[i] == 1) return DirectionCode.Down;
					if(rank[i] == 2) return DirectionCode.Left;
					if(rank[i] == 3) return DirectionCode.Right;
					if(rank[i] == 4) return DirectionCode.Stay;
				}
			}
		}
		else{
			for(int i = 0; i < 5; i++){
				if(rank[i] == 0) if(IsValidMove(DirectionCode.Up)) return DirectionCode.Up;
				if(rank[i] == 1) if(IsValidMove(DirectionCode.Down)) return DirectionCode.Down;
				if(rank[i] == 2) if(IsValidMove(DirectionCode.Left)) return DirectionCode.Left;
				if(rank[i] == 3) if(IsValidMove(DirectionCode.Right)) return DirectionCode.Right;
				if(rank[i] == 4) return DirectionCode.Stay;
			}
		}
		// TODO ����ü ������ �� �̵� �Ǵ� ����ϱ� ���� ������ ���⿡ ��������.
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			
			for(int i = 0; i < Constants.Soul_Interval_Respawn; i++){
				for(int j = 0; j < Constants.Total_Players; j++){
					myMemory[i][j] = new studentT();
					myMemory[i][j].state = State.Not_Defined;
					myMemory[i][j].hitpoint = 0;
					myMemory[i][j].position = new Point();
					myMemory[i][j].validity = Constants.Total_Turns;
					myMemory[i][j].type = Action.TypeCode.Not_Defined;
					myMemory[i][j].location_from = new Point();
				}
			}
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
		}
		else{
			AgingAndGether();
			for(PlayerInfo target : othersInfo_withinSight){
				myMemory[2][target.GetID()] = myMemory[1][target.GetID()];
				myMemory[1][target.GetID()] = myMemory[0][target.GetID()];
				myMemory[0][target.GetID()].state = target.GetState();
				myMemory[0][target.GetID()].hitpoint = target.GetHitPoint();
				myMemory[0][target.GetID()].position = target.GetPosition();
				myMemory[0][target.GetID()].validity = 0;
				for(Action act : actions){
					if(target.GetID() == act.GetActorID()){
						myMemory[0][target.GetID()].type = act.GetType();
						myMemory[0][target.GetID()].location_from = act.GetLocation_From();
					}
				}
			}
			if(presTOT < 50 && myScore.GetSurvivor_Total_Spots() >= 50) bombChance = true;
			presTOT = myScore.GetSurvivor_Total_Spots();
			if(myScore.GetCorpse_Total_Heals() < 25) bombChance = true;
		}
		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn()
	{
		AgingAndGether();
		for(PlayerInfo target : othersInfo_withinSight){
			myMemory[2][target.GetID()] = myMemory[1][target.GetID()];
			myMemory[1][target.GetID()] = myMemory[0][target.GetID()];
			myMemory[0][target.GetID()].state = target.GetState();
			myMemory[0][target.GetID()].hitpoint = target.GetHitPoint();
			myMemory[0][target.GetID()].position = target.GetPosition();
			myMemory[0][target.GetID()].validity = 0;
			for(Action act : actions){
				if(target.GetID() == act.GetActorID()){
					myMemory[0][target.GetID()].type = act.GetType();
					myMemory[0][target.GetID()].location_from = act.GetLocation_From();
				}
			}
		}
		if(gameInfo.GetCurrentTurnNumber() == 0){
			return new Point(3,3);
		}
		else{ // ���������� 50���ʰ��� �ѹ�, 200���ʰ��� �ѹ� ��ȸ�� �븮��.
			int weight[][] = new int[Constants.Classroom_Width][Constants.Classroom_Height];
			for(PlayerInfo other : othersInfo_withinSight){
				Point target = new Point();
				if(other.GetState() == State.Infected){
					target.x = other.GetPosition().x - 2;
					target.y = other.GetPosition().y;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x - 1;
					target.y = other.GetPosition().y - 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x - 1;
					target.y = other.GetPosition().y;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x - 1;
					target.y = other.GetPosition().y + 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x;
					target.y = other.GetPosition().y - 2;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x;
					target.y = other.GetPosition().y - 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x ;
					target.y = other.GetPosition().y;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x ;
					target.y = other.GetPosition().y + 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x;
					target.y = other.GetPosition().y + 2;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x + 1;
					target.y = other.GetPosition().y - 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x + 1;
					target.y = other.GetPosition().y;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x + 1;
					target.y = other.GetPosition().y + 1;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
					target.x = other.GetPosition().x + 2;
					target.y = other.GetPosition().y;
					if(IsValidSpawn(target)) weight[target.x][target.y] += 6 - (2 * GetDistance(other.GetPosition(), target));
				}
				else if(other.GetState() == State.Corpse){
					target.x = other.GetPosition().x;
					target.y = other.GetPosition().y;
					weight[target.x][target.y] += 4; // ��������� ����ġ ����
				}
				
			}
			int maxWeight = 0;
			Point result = new Point();
			for(int i = 0; i < Constants.Classroom_Width; i++){
				for(int j = 0; j < Constants.Classroom_Height; j++){
					Point target = new Point(i, j);
					if(maxWeight < weight[target.x][target.y]){
						maxWeight = weight[target.x][target.y];
						result = target;
					}
				}
			}
			if(bombChance == true && maxWeight > 40){
				bombChance = false;
				return result;
			}
			else if(maxWeight > 60){ // �� ��ȸ�� ��ĥ�� ������
				bombChance = false;
				return result;
			}
			else{
				int weightSurvivors[][] = new int[Constants.Classroom_Width][Constants.Classroom_Height];
				int weightOthers[][] = new int[Constants.Classroom_Width][Constants.Classroom_Height];
				int totalWeight[][] =  new int[Constants.Classroom_Width][Constants.Classroom_Height];
				for(PlayerInfo other : othersInfo_withinSight){
					Point target = new Point();
					if(other.GetState() == State.Infected || other.GetState() == State.Corpse){
						target.x = other.GetPosition().x - 2;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y - 2;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x ;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y + 2;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
						target.x = other.GetPosition().x + 2;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightOthers[target.x][target.y]++;
					}
					if(other.GetState() == State.Survivor){
						target.x = other.GetPosition().x - 2;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y] += 2;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y - 2;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y] += 2;
						target.x = other.GetPosition().x ;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y] += 2;
						target.x = other.GetPosition().x ;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y] += 2;
						target.x = other.GetPosition().x - 1;
						target.y = other.GetPosition().y + 2;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y - 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y] += 2;
						target.x = other.GetPosition().x + 1;
						target.y = other.GetPosition().y + 1;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
						target.x = other.GetPosition().x + 2;
						target.y = other.GetPosition().y;
						if(IsValidSpawn(target)) weightSurvivors[target.x][target.y]++;
					}					
				}
				maxWeight = 0;
				for(int i = 0; i < Constants.Classroom_Width; i++){
					for(int j = 0; j < Constants.Classroom_Height; j++){
						int infects = 0;
						totalWeight[i][j] = weightSurvivors[i][j];
						Point target = new Point(i, j);
						for(int k = 0; k < GetCellInfo(target).GetNumberOfPlayersInTheCell(); k++){
							if(GetCellInfo(target).GetPlayerInfo(k).GetState() == State.Infected){
								infects++;
							}
						}
						if(maxWeight < totalWeight[target.x][target.y] && infects == 0 && weightOthers[target.x][target.y] > 0 && weightOthers[target.x][target.y] < 4){
							maxWeight = totalWeight[target.x][target.y];
							result = target;
						}
					}
				}
				return result;
			}
		}
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
	}
	
	private void AgingAndGether()
	{
		if(gameInfo.GetCurrentTurnNumber() != 0){
			for(int i = 0; i < Constants.Soul_Interval_Respawn; i++){
				for(int j = 0; j < Constants.Total_Players; j++){
					myMemory[i][j].validity = 1;
				}
			}
			for(PlayerInfo target : othersInfo_detected){
				myMemory[2][target.GetID()] = myMemory[1][target.GetID()];
				myMemory[1][target.GetID()] = myMemory[0][target.GetID()];
				myMemory[0][target.GetID()].state = target.GetState();
				myMemory[0][target.GetID()].hitpoint = target.GetHitPoint();
				myMemory[0][target.GetID()].position = target.GetPosition();
				myMemory[0][target.GetID()].validity = 0;
				if(CanSee(target.GetPosition())){
					for(Action act : actions){
						if(target.GetID() == act.GetActorID()){
							myMemory[0][target.GetID()].type = act.GetType();
							myMemory[0][target.GetID()].location_from = act.GetLocation_From();
						}
					}
				}
				else{
					myMemory[0][target.GetID()].type = Action.TypeCode.Not_Defined;
					myMemory[0][target.GetID()].location_from = myMemory[0][target.GetID()].position;
				}
			}
		}
	}
}

