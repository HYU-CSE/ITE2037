package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
//import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;


public class Player_one extends Player
{

	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;


	Point basePoint;
	Point basePoint2;
	

	int index_directionToMove;
		


	public Player_one()
	{
		name = "LSD";
		 
				 this.acceptDirectInfection = true ;
		         receiveOthersInfo_detected = true;
	}
	

	void ShuffleDirections()
	{

		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		

		int[] offsets = new int[4];
		
		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;


		shuffledDirection_values = new int[4];

		for ( int iCurrent = 0; iCurrent < 4; ++iCurrent )
		{
			int current_value = 0;
			
			while ( true )
			{

				boolean isSameValueFound = false;
				
				for ( int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious )
					if ( shuffledDirection_values[iPrevious] == current_value )
					{
						isSameValueFound = true;
						break;
					}
				

				if ( isSameValueFound == true )
				{
					++current_value;
				}

				else if ( offsets[iCurrent] != 0 )
				{
					--offsets[iCurrent];
					++current_value;
				}

				else
				{
					break;
				}
			}
			

			shuffledDirection_values[iCurrent] = current_value;
		}
				
 
		shuffledDirections = new DirectionCode[4];
		
		for ( int i = 0; i < 4; ++i )
			switch ( shuffledDirection_values[i] )
			{
			case 0:
				shuffledDirections[i] = DirectionCode.Up;
				break;
			case 1:
				shuffledDirections[i] = DirectionCode.Left;
				break;
			case 2:
				shuffledDirections[i] = DirectionCode.Right;
				break;
			default:
				shuffledDirections[i] = DirectionCode.Down;
				break;
			}
	}
	


	void SetBasePoint()
	{

		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		

		int base_y = seed % ( Constants.Classroom_Width * Constants.Classroom_Height ) / Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;
		
		basePoint = new Point(base_x, base_y);
	}
	void SetBasePoint2(int x, int y)
	{

		

		int base_y = y; 
		int base_x = x;
	
		basePoint2 = new Point(base_x, base_y);
	}
	


	@Override
	public DirectionCode Survivor_Move()
	{
		if(myScore.GetCorpse_Total_Heals()<100 )
		{

			int[] weights = new int[4];
			int max_weight = -1;
			

			for ( PlayerInfo other : othersInfo_withinSight )
			{

				Vector v = GetDistanceVectorBetweenPlayers(other);

 
				if ( other.GetState() == PlayerInfo.State.Infected && v.GetDistance() >= 1 )
				{
					if ( v.y_offset < 0 )
						++weights[0];
		
					if ( v.x_offset < 0 )
						++weights[1];
					
					if ( v.x_offset > 0 )
						++weights[2];
					
					if ( v.y_offset > 0 )
						++weights[3];
				}
			}
			

			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;


			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			

			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];


			return DirectionCode.Stay;
		}

	else
       {
		int[] survivors = new int[4];
		int[] others = new int[4];
		int[] weights = new int[4];
		int max_weight = -1;
		

		for ( PlayerInfo other : othersInfo_detected )
		{

			Vector v = GetDistanceVectorBetweenPlayers(other);


			if ( other.GetState() == PlayerInfo.State.Survivor )
			{
				if ( v.y_offset < 0 )
					++survivors[0];
	
				if ( v.x_offset < 0 )
					++survivors[1];
				
				if ( v.x_offset > 0 )
					++survivors[2];
				
				if ( v.y_offset > 0 )
					++survivors[3];
			}
			else
			{
				if ( v.y_offset < 0 )
					++others[0];
	
				if ( v.x_offset < 0 )
					++others[1];
				
				if ( v.x_offset > 0 )
					++others[2];
				
				if ( v.y_offset > 0 )
					++others[3];
			}
		}


		for ( int i = 0; i < 4; ++i )
			weights[i] = survivors[i] * others[i];
		

		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;


		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		

		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];


		return DirectionCode.Stay;
       }
     
		
}

	@Override
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	
	{
		 DirectionCode result;
		   result = DirectionCode.Stay;
		 
		  return result;  
	
	}

	@Override
	public void Soul_Stay()
	{

		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			ShuffleDirections();
			SetBasePoint();	
		}
		
		else
		{
		int k=0,j=0,h=0,a=0,b=0;//,c=0,d=0;
		
		for ( PlayerInfo other : othersInfo_detected )
		{
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				++k;
				 a+=other.GetPosition().x;
				 b+=other.GetPosition().y;
			}
		}
		if(k==0)
		{
			j=4;
			h=4;
		}
		else
		{
		j=a/k;
		h=b/k;
		}
		SetBasePoint2(j, h);
		}

		
		
	}

	@Override
	public Point Soul_Spawn()
	{
          

		
		
		Point pointToSpawn = basePoint;
		//Point pointToSpawn2 = basePoint2;
		
		if(gameInfo.GetCurrentTurnNumber()!=0&&myScore.GetCorpse_Total_Heals()>100)
		{

			
			return basePoint2;
		}
		
		
		else
		{
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		for ( PlayerInfo other : othersInfo_withinSight )
		{

			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				++weights[pos_other.y][pos_other.x];


				if ( weights[pos_other.y][pos_other.x] > max_weight )
				{
					++max_weight;
					list_pos_max_weight.clear();
				}
				

				if ( weights[pos_other.y][pos_other.x] == max_weight )
					list_pos_max_weight.add(pos_other);
			}
		}
		

		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();
		
		for ( Point pos_max_weight : list_pos_max_weight )
		{
			int distance = GetDistance(myPosition, pos_max_weight);
			
			if ( distance < min_distance )
			{
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}
		
		return pointToSpawn;
	}
	}
	
}
