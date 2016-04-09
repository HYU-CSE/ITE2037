package l4g2ep1.customplayers;

import java.util.ArrayList;

//import javax.sound.midi.MidiDevice.Info;

import l4g2ep1.*;
import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class Player_Druwa extends Player
{	
	
	DirectionCode[] directions;
	int pos_directionToMove;
	Point basePoint;
	int[] shuffledDirection_values;
	int count=0;
	
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
				
		directions = new DirectionCode[4];
		
		for ( int i = 0; i < 4; ++i )
			switch ( shuffledDirection_values[i] )
			{
			case 0:
				directions[i] = DirectionCode.Up;
				break;
			case 1:
				directions[i] = DirectionCode.Left;
				break;
			case 2:
				directions[i] = DirectionCode.Right;
				break;
			default:
				directions[i] = DirectionCode.Down;
				break;
			}
	}
	void SetBasePoint(){
		basePoint = new Point(3,3);
	}
	
	public Player_Druwa()
	{
		name = "절오빠";	
		acceptDirectInfection = false;			
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{	
		if(gameInfo.GetCurrentTurnNumber()<=8 || gameInfo.GetCurrentTurnNumber()>40 && myInfo.GetState()==State.Survivor){
			int[] Infected = new int[4];
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
			
			for(PlayerInfo other : othersInfo_withinSight){
				Vector v = GetDistanceVectorBetweenPlayers(other);
				if ( other.GetState() == PlayerInfo.State.Infected )
				{
					if ( v.y_offset < 0 )
						++Infected[0];
		
					if ( v.x_offset < 0 )
						++Infected[1];
					
					if ( v.x_offset > 0 )
						++Infected[2];
					
					if ( v.y_offset > 0 )
						++Infected[3];			
				}		
			}
			for(int i=0;i<4;i++)
				if(Infected[i]!=0){
					weights[i]=-1;
				}
			
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return directions[iShuffledDirection];
			
			
			return DirectionCode.Stay;
		}else if(myInfo.GetState()==State.Survivor){
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			for ( PlayerInfo other : othersInfo_detected )
			{
				Vector v = GetDistanceVectorBetweenPlayers(other);
 
				if ( v.y_offset < 0 )
					++weights[0];

				if ( v.x_offset < 0 )
					++weights[1];
				
				if ( v.x_offset > 0 )
					++weights[2];
				
				if ( v.y_offset > 0 )
					++weights[3];
			}
			
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = Integer.MAX_VALUE;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = Integer.MAX_VALUE;

			for ( int weight : weights )
				if ( weight < min_weight )
					min_weight = weight;
			
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == min_weight )
					return directions[iShuffledDirection];

			return DirectionCode.Stay;
			}
		
		else{
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
					return directions[iShuffledDirection];
			
			return DirectionCode.Stay;
			
		}
		
	}
	
	@Override
	public void Corpse_Stay()
	{
	}

	@Override
	public DirectionCode Infected_Move()
	{
		count+=1;
		if(count>20){
			count-=5;
			return DirectionCode.Stay;
		}
		else{
			return Survivor_Move();
		}
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			
			ShuffleDirections();
			SetBasePoint();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		Point pointToSpawn = basePoint;
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
