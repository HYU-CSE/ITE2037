package l4g.customplayers;

import l4g.Classroom;
import l4g.Classroom.ClassroomStateCode;
import l4g.PlayerStat;
import l4g.common.*;
import l4g.data.CellInfo;


import java.util.ArrayList;

import l4g.Classroom;
import l4g.Classroom.ClassroomStateCode;
import l4g.PlayerStat;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 
 * @author Racin
 *
 */
public class Player_Arous_best extends Player
{
	public Player_Arous_best(int ID)
	{

		super(ID, "±èÇöÇÑ");

		this.trigger_acceptDirectInfection = false;




	}

	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(6, 6);
	int corpseBombTurnCondition = 30;
	int corpseBombScoreCondition = 300;

	boolean isValidPoint(Point point, DirectionCode direction)
	{
		boolean ret = false;

		if ( direction == DirectionCode.Up) {
			point.row -= 1;
		} else if ( direction == DirectionCode.Left ) {
			point.column -= 1;
		} else if ( direction == DirectionCode.Right ) {
			point.column += 1;
		} else if ( direction == DirectionCode.Down ) {
			point.row += 1;
		}

		if ( point.row >= 0 && point.row < Constants.Classroom_Width &&
				point.column >= 0 && point.column < Constants.Classroom_Height ) {
			ret = true;
		}

		return ret;
	}

	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;

		favoritePoint.row = 6;
		favoritePoint.column = 6;
	}

	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;

		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}

		return directions[iDirection];
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		Point currentPoint = new Point(row, column);

		int[] numberOfInfected = new int[13];
		int[] numberOfSurvivor = new int[13];



		// 0
		row -= 2;
		if ( row >= 0 ) {
			numberOfInfected[0] = cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[0] = cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);
		}

		// 1, 2, 3
		++row;
		if ( row >= 0 )
		{
			if ( column >= 1 ) {
				numberOfInfected[1] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[1] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Survivor);
			}


			numberOfInfected[2] = cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[2] = cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfInfected[3] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[3] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Survivor);
			}
		}

		// 4, 5, 7, 8
		++row;
		if ( column >= 1 )
		{
			numberOfInfected[5] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[5] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Survivor);

			if ( column >= 2 ) {
				numberOfInfected[4] = cells[row][column - 2].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[4] = cells[row][column - 2].CountIf_Players(player->player.state == StateCode.Survivor);
			}
		}

		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfInfected[7] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[7] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Survivor);


			if ( column < Constants.Classroom_Width - 2 ) {
				numberOfInfected[8] = cells[row][column + 2].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[8] = cells[row][column + 2].CountIf_Players(player->player.state == StateCode.Survivor);
			}
		}

		// 9, A, B
		++row;
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 ) { 
				numberOfInfected[9] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[9] = cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Survivor);
			}

			numberOfInfected[10] = cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[10] = cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);

			if ( column < Constants.Classroom_Width - 1 ) {
				numberOfInfected[11] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Infected);
				numberOfSurvivor[11] = cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Survivor);
			}
		}

		// C
		++row;
		if ( row < Constants.Classroom_Height) {
			numberOfInfected[12] = cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
			numberOfSurvivor[12] = cells[row][column].CountIf_Players(player->player.state == StateCode.Survivor);
		}



		// Decision Moving
		int[] numOfDirectionInfected = {0,0,0,0};
		numOfDirectionInfected[0] = numberOfInfected[0] + numberOfInfected[1] + numberOfInfected[2] + numberOfInfected[3];
		numOfDirectionInfected[1] = numberOfInfected[1] + numberOfInfected[4] + numberOfInfected[5] + numberOfInfected[9];
		numOfDirectionInfected[2] = numberOfInfected[3] + numberOfInfected[7] + numberOfInfected[8] + numberOfInfected[11];
		numOfDirectionInfected[3] = numberOfInfected[9] + numberOfInfected[10] + numberOfInfected[11] + numberOfInfected[12];
		
		int[] numOfDirectionSurvivor = {0,0,0,0};
		numOfDirectionSurvivor[0] = numberOfSurvivor[0] + numberOfSurvivor[1] + numberOfSurvivor[2] + numberOfSurvivor[3];
		numOfDirectionSurvivor[1] = numberOfSurvivor[1] + numberOfSurvivor[4] + numberOfSurvivor[5] + numberOfSurvivor[9];
		numOfDirectionSurvivor[2] = numberOfSurvivor[3] + numberOfSurvivor[7] + numberOfSurvivor[8] + numberOfSurvivor[11];
		numOfDirectionSurvivor[3] = numberOfSurvivor[9] + numberOfSurvivor[10] + numberOfSurvivor[11] + numberOfSurvivor[12];


		// Initial moving while no infected are in classroom
		if ( turnInfo.turnNumber < 10 ) {
			if ( turnInfo.turnNumber % 2 == 0 ) {
				result = DirectionCode.Right;
			}
			else {
				result = DirectionCode.Left;
			}
		}
		
		// Do suiciding for changing mode to CorpseBomb
		else if ( turnInfo.turnNumber >= corpseBombTurnCondition || myScore.survivor_max + myScore.survivor_total >= corpseBombScoreCondition) {
			for ( int i = 1; i < 14; i++ ) {
				int suffleDirection = (i + turnInfo.turnNumber) % 4;
				if ( suffleDirection % 4 != 0 ) continue;
				
				DirectionCode directionCode = null;
				int maxInfectedNum = 0;

				switch(suffleDirection) {
				case 0:
					directionCode = DirectionCode.Up;
					break;
				case 1:
					directionCode = DirectionCode.Left;
					break;
				case 2:
					directionCode = DirectionCode.Right;
					break;
				case 3:
					directionCode = DirectionCode.Down;
					break;
				}

				if ( numberOfInfected[suffleDirection] > maxInfectedNum ) {
					maxInfectedNum = numberOfInfected[suffleDirection];
					result = directionCode;
				}
			}
		}
		
		// avoid infected
		else {
			int maxNumOfSurvivor = Integer.MIN_VALUE;
			for(int direction = 1; direction < 5; direction++) {
				int suffleDirection = (direction + turnInfo.turnNumber) % 4;
				if ( numOfDirectionInfected[suffleDirection] == 0 ) {
					DirectionCode directionCode = null;
					switch(suffleDirection) {
					case 0:
						directionCode = DirectionCode.Up;
						currentPoint.row = myInfo.position.row - 1;
						currentPoint.column = myInfo.position.column;
						break;
					case 1:
						directionCode = DirectionCode.Left;
						currentPoint.row = myInfo.position.row;
						currentPoint.column = myInfo.position.column - 1;
						break;
					case 2:
						directionCode = DirectionCode.Right;
						currentPoint.row = myInfo.position.row;
						currentPoint.column = myInfo.position.column + 1;
						break;
					case 3:
						directionCode = DirectionCode.Down;
						currentPoint.row = myInfo.position.row + 1;
						currentPoint.column = myInfo.position.column;
						break;
					}
					
					if ( this.isValidPoint(currentPoint, directionCode)) {
						if ( numOfDirectionSurvivor[suffleDirection] > maxNumOfSurvivor ) {
							maxNumOfSurvivor = numOfDirectionSurvivor[suffleDirection];
							result = directionCode;
						}
					}
				}
			}
			
			/*
			 *  example ( I : Infected, M : Me )
			 *  ___I_
			 *  __M__
			 *  -I___
			 */
			if ( result == null ) {
				for(int i = 1; i < 14; i++) {
					int suffleDirection = (i + turnInfo.turnNumber) % 4;
					if ( suffleDirection != 0 ) continue;

					DirectionCode directionCode = null;

					switch(suffleDirection) {
					case 0:
						directionCode = DirectionCode.Up;
						currentPoint.row = myInfo.position.row - 1;
						currentPoint.column = myInfo.position.column;
						break;
					case 1:
						directionCode = DirectionCode.Left;
						currentPoint.row = myInfo.position.row;
						currentPoint.column = myInfo.position.column - 1;
						break;
					case 2:
						directionCode = DirectionCode.Right;
						currentPoint.row = myInfo.position.row;
						currentPoint.column = myInfo.position.column + 1;
						break;
					case 3:
						directionCode = DirectionCode.Down;
						currentPoint.row = myInfo.position.row + 1;
						currentPoint.column = myInfo.position.column;
						break;
					}

					if ( numberOfInfected[suffleDirection] == 0 ) {
						if ( this.isValidPoint(currentPoint, directionCode)) {
							result = directionCode;
							break;
						}
					}
				}
			}
		}

		if ( result == null ) {
			/*
			 *  example
			 *  __I__
			 *  ___I_
			 *  I_M_I
			 *  _I___
			 *  __I__
			 *  
			 *  then, just move.
			 */
			Point connectedPoint = myInfo.position.GetAdjacentPoint(DirectionCode.Down);
			if ( result == null && this.isValidPoint(connectedPoint, DirectionCode.Down) == true) {
				result = DirectionCode.Down;
			}
			connectedPoint = myInfo.position.GetAdjacentPoint(DirectionCode.Right);
			if ( result == null && this.isValidPoint(connectedPoint, DirectionCode.Right) == true) {
				result = DirectionCode.Right;
			}
			connectedPoint = myInfo.position.GetAdjacentPoint(DirectionCode.Left);
			if ( result == null && this.isValidPoint(connectedPoint, DirectionCode.Left) == true) {
				result = DirectionCode.Left;
			}
			connectedPoint = myInfo.position.GetAdjacentPoint(DirectionCode.Up);
			if ( result == null && this.isValidPoint(connectedPoint, DirectionCode.Up) == true) {
				result = DirectionCode.Up;
			}
		}
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = null;
		
		// CorpseBomp Mode
		if ( turnInfo.turnNumber >= corpseBombTurnCondition || myScore.survivor_max + myScore.survivor_total >= corpseBombScoreCondition) {
			if ( cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player->player.state == StateCode.Infected) > 10) {
				return DirectionCode.Stay;
			}
		}
		
		// get more Survivor point
		else {
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) < 2 )
				return GetMovableAdjacentDirection();
		}

		return DirectionCode.Stay;
	}

	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		if ( turnInfo.turnNumber == 0 ) {
			return new Point(6, 6);
		}

		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}

				else if ( weight == max_weight )
				{

					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}

					else if ( distance == min_distance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
							{
								max_row = row;
								max_column = column;
								break;
							}
						} 
						
					}
				}
			}
		}
		
		// CorpseBomb Mode
		if ( turnInfo.turnNumber >= corpseBombTurnCondition || myScore.survivor_max + myScore.survivor_total >= corpseBombScoreCondition ) {
			return new Point(max_row, max_column);
			
		}

		else {
			int[][] crowdedNum = new int[Constants.Classroom_Width][Constants.Classroom_Height];
			Point mostQuitestPoint = new Point(0, 0);
			int minPlayerNumber = Integer.MAX_VALUE;
			
			for(int totalRow = 0; totalRow < Constants.Classroom_Height; totalRow++) {
				for ( int totalCol = 0; totalCol < Constants.Classroom_Width; totalCol++) {
					int[] numberOfPlayers = new int[13];
					if (cells[totalRow][totalCol].CountIf_Players(player->player.state == StateCode.Infected) > 0) {
						continue;
					}
					
					int row = totalRow;
					int column = totalCol;
					
					// number of infected + Corpse on survivor's viewpoint

					// 0
					row -= 2;
					
					if ( row >= 0 ) {
						numberOfPlayers[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[0] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
					}
					
					// 1, 2, 3
					++row;
					
					if ( row >= 0 )
					{
						if ( column >= 1 ) {
							numberOfPlayers[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[1] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
						}
						
						numberOfPlayers[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[2] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
						
						if ( column < Constants.Classroom_Width - 1 ) {
							numberOfPlayers[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[3] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
						}
					}
					
					// 4, 5, 7, 8 
					++row;
					
					if ( column >= 1 )
					{
						numberOfPlayers[5] += cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[5] += cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Infected);
						
						if ( column >= 2 ) { 
							numberOfPlayers[4] += cells[row][column - 2].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[4] += cells[row][column - 2].CountIf_Players(player->player.state == StateCode.Infected);
						}
					}
					
					if ( column < Constants.Classroom_Width - 1 )
					{
						numberOfPlayers[7] += cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[7] += cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Infected);
						
						if ( column < Constants.Classroom_Width - 2 ) {
							numberOfPlayers[8] += cells[row][column + 2].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[8] += cells[row][column + 2].CountIf_Players(player->player.state == StateCode.Infected);
						}
					}
					
					// 9, A, B
					++row;
					
					if ( row < Constants.Classroom_Height)
					{
						if ( column >= 1 ) {
							numberOfPlayers[9] += cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[9] += cells[row][column - 1].CountIf_Players(player->player.state == StateCode.Infected);
						}
						
						numberOfPlayers[10] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[10] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
						
						if ( column < Constants.Classroom_Width - 1 ) {
							numberOfPlayers[11] += cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Corpse);
							numberOfPlayers[11] += cells[row][column + 1].CountIf_Players(player->player.state == StateCode.Infected);
						}
					}
					
					// C
					++row;
					
					if ( row < Constants.Classroom_Height) {
						numberOfPlayers[12] += cells[row][column].CountIf_Players(player->player.state == StateCode.Corpse);
						numberOfPlayers[12] += cells[row][column].CountIf_Players(player->player.state == StateCode.Infected);
					}
					
					
					// save number
					for(int i = 0; i < 13; i++) {
						crowdedNum[totalRow][totalCol] += numberOfPlayers[i];
					}
					
					
					// update Point
					if ( crowdedNum[totalRow][totalCol] < minPlayerNumber) {
						minPlayerNumber = crowdedNum[totalRow][totalCol];
						mostQuitestPoint.row = totalRow;
						mostQuitestPoint.column = totalCol;
					}
				} // end of col
			} // end of row
			
			return mostQuitestPoint;
		} // end of else

		//return new Point(max_row, max_column);
	}
}
