package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class Hungry extends Player {
	public Hungry() {
		name = "Hungry"; // TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false; // TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고
										// 아닌
										// 경우 false로 두세요.
		this.receiveOthersInfo_detected = true;
	}

	DirectionCode[] directions;
	int pos_directionToMove;

	@Override
	public DirectionCode Survivor_Move() {
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;

		for (PlayerInfo other : othersInfo_detected) {

			Vector v = GetDistanceVectorBetweenPlayers(other);

			if (v.y_offset < 0)
				++weights[0];

			if (v.x_offset < 0)
				++weights[1];

			if (v.x_offset > 0)
				++weights[2];

			if (v.y_offset > 0)
				++weights[3];
		}

		if (IsValidMove(DirectionCode.Up) == false)
			weights[0] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Left) == false)
			weights[1] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Right) == false)
			weights[2] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Down) == false)
			weights[3] = Integer.MAX_VALUE;

		for (int weight : weights)
			if (weight < min_weight)
				min_weight = weight;
		if (min_weight == weights[0])
			return directions[0];
		else if (min_weight == weights[1])
			return directions[1];
		else if (min_weight == weights[2])
			return directions[2];
		else
			return directions[3];

	}

	@Override
	public void Corpse_Stay() {
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public DirectionCode Infected_Move() {
		{

			if (myScore.GetCorpse_Max_Healed_Players() < 70) {
				boolean isCorpseHere = false;
				CellInfo here = GetCellInfo(myInfo.GetPosition());

				for (int iPlayer = 0; iPlayer < here
						.GetNumberOfPlayersInTheCell(); ++iPlayer) {
					PlayerInfo other = here.GetPlayerInfo(iPlayer);

					if (other.GetState() == PlayerInfo.State.Corpse) {
						isCorpseHere = true;
						break;
					}
				}

				if (isCorpseHere == true)
					return DirectionCode.Stay;

				int[] weights = new int[4];
				int max_weight = -1;

				for (PlayerInfo other : othersInfo_withinSight) {

					Vector v = GetDistanceVectorBetweenPlayers(other);

					if (other.GetState() != PlayerInfo.State.Infected) {
						if (v.y_offset < 0)
							++weights[0];

						if (v.x_offset < 0)
							++weights[1];

						if (v.x_offset > 0)
							++weights[2];

						if (v.y_offset > 0)
							++weights[3];
					}
				}

				if (IsValidMove(DirectionCode.Up) == false)
					weights[0] = -1;

				if (IsValidMove(DirectionCode.Left) == false)
					weights[1] = -1;

				if (IsValidMove(DirectionCode.Right) == false)
					weights[2] = -1;

				if (IsValidMove(DirectionCode.Down) == false)
					weights[3] = -1;

				for (int weight : weights)
					if (weight > max_weight)
						max_weight = weight;
				if (max_weight == weights[0])
					return directions[0];
				else if (max_weight == weights[1])
					return directions[1];
				else if (max_weight == weights[2])
					return directions[2];
				else
					return directions[3];

			} else {
				int[] weights = new int[4];
				int max_weight = -1;

				for (PlayerInfo other : othersInfo_withinSight) {
					Vector v = GetDistanceVectorBetweenPlayers(other);

					if (other.GetState() == PlayerInfo.State.Survivor
							&& v.GetDistance() > 1) {
						if (v.y_offset < 0)
							++weights[0];

						if (v.x_offset < 0)
							++weights[1];

						if (v.x_offset > 0)
							++weights[2];

						if (v.y_offset > 0)
							++weights[3];
					}
				}

				if (IsValidMove(DirectionCode.Up) == false)
					weights[0] = -1;

				if (IsValidMove(DirectionCode.Left) == false)
					weights[1] = -1;

				if (IsValidMove(DirectionCode.Right) == false)
					weights[2] = -1;

				if (IsValidMove(DirectionCode.Down) == false)
					weights[3] = -1;

				for (int weight : weights)
					if (weight > max_weight)
						max_weight = weight;
				if (max_weight == weights[0])
					return directions[0];
				else if (max_weight == weights[1])
					return directions[1];
				else if (max_weight == weights[2])
					return directions[2];
				else
					return directions[3];

			}

		}
		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.

	}

	@Override
	public void Soul_Stay() {
		if (gameInfo.GetCurrentTurnNumber() == 0) {
			directions = new DirectionCode[4];
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저
			// 호출됩니다.
		}

		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn() {
		if (gameInfo.GetCurrentTurnNumber() == 0) {
			return new Point(5, 5);
		}
		Point pointToSpawn = new Point(5, 5);
		if (myScore.GetCorpse_Max_Healed_Players() < 70) {

			int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int max_weight = -1;
			ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

			for (PlayerInfo other : othersInfo_withinSight) {

				if (other.GetState() == PlayerInfo.State.Infected) {
					Point pos_other = other.GetPosition();

					++weights[pos_other.y][pos_other.x];

					if (weights[pos_other.y][pos_other.x] > max_weight) {
						++max_weight; // weight는 항상 1씩 증가하므로 당연히 최대값도 1씩 증가함
						list_pos_max_weight.clear();
					}

					if (weights[pos_other.y][pos_other.x] == max_weight)
						list_pos_max_weight.add(pos_other);
				}
			}

			int min_distance = Integer.MAX_VALUE;
			Point myPosition = myInfo.GetPosition();

			for (Point pos_max_weight : list_pos_max_weight) {
				int distance = GetDistance(myPosition, pos_max_weight);

				if (distance < min_distance) {
					min_distance = distance;
					pointToSpawn = pos_max_weight;
				}
			}

			return pointToSpawn;
		} else

			return new Point(5, 5);

	}
}
