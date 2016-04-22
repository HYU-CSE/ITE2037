package l4g.customplayers;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;

/**
 * �� ���𸶴� �÷��̾� �� ���� ��� ������ ���� �ٴϴ� team Sigma �Ҽ� �÷��̾��Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_�����1 extends Player
{
	int IDtoFollow;
	
	DirectionCode[] directions = { DirectionCode.Up, DirectionCode.Left, DirectionCode.Right, DirectionCode.Down };
	
	public Player_�����1(int ID)
	{
		super(ID, "�����#1");
	}
	
	/**
	 * �ش� ��ǥ�� ��ȿ����(���ǽ� ���� ĭ�� ����Ű�� ��ǥ����) ���θ� ��ȯ�մϴ�.
	 */
	public boolean IsValid(int row, int column)
	{
		return row >= 0 && row < Constants.Classroom_Height &&
				column >= 0 && column < Constants.Classroom_Width;
	}
	
	/**
	 * �ش� ��ǥ�� ������ �� �� �ִ� ���� ������ ������ �����Ͽ� ��ȯ�մϴ�.
	 */
	public DirectionCode GetDirectionToTheCell(int row, int column, boolean allowStay)
	{
		int min_distance = Constants.Classroom_Height * Constants.Classroom_Width;
		int min_idx_direction = 0;
		
		for ( int iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( IsValid(adjacentPoint.row, adjacentPoint.column) == true )
			{
				int distance = adjacentPoint.GetDistance(row, column);
				
				if ( distance < min_distance )
				{
					min_distance = distance;
					min_idx_direction = iDirection;
				}
			}
		}
		
		if ( allowStay == true )
		{
			int distance = myInfo.position.GetDistance(row, column);
			
			if ( distance < min_distance )
				return DirectionCode.Stay;
		}
		
		return directions[min_idx_direction];
	}
	
	boolean isRightMoveRequired = false;
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// �׻� ���� �Ѵ� �� �÷��̾� ������ ��Ȱ
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return GetDirectionToTheCell(row, column, false);
		
		// �� �÷��̾�� ���ǽ��� �����ų� �þ߿��� ������� ���� ���ڸ����� ��ȸ
		if ( isRightMoveRequired == true )
		{
			isRightMoveRequired = false;
			
			if ( myInfo.position.column != Constants.Classroom_Width - 1 )
				return DirectionCode.Right;
			else
				return DirectionCode.Left;
		}
		else
		{
			isRightMoveRequired = true;
			
			if ( myInfo.position.column != 0 )
				return DirectionCode.Left;
			else
				return DirectionCode.Right;
		}
	}
	
	@Override
	public void Corpse_Stay()
	{
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		// �׻� ���� �Ѵ� �� �÷��̾� ������ ��Ȱ
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return GetDirectionToTheCell(row, column, true);
		
		// �� �÷��̾�� ���ǽ��� �����ų� �þ߿��� ������� ���� ��ȭ �⵵
		return DirectionCode.Stay;
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			// �̹� ���� ���� �÷��̾� ID ����
			int seasonNumber = (int)(gameNumber / 10000) % 10 + ( (int)(gameNumber / 1000000) - 2006 ) * 10;
			
			if ( seasonNumber < 0 )
				seasonNumber = -seasonNumber;
			
			seasonNumber %= 100;
			
			if ( seasonNumber == ID )
			{
				// �� �ڽ��� �Ѿƾ� �� �����̾��ٸ� 100�� �ֱ�� �ٸ� �÷��̾ ����
				IDtoFollow = (int)(gameNumber % 100);
				
				// 100�� �ֱ⿡������ �� ID�� ���͹����� �� �ڽ��� �ٷ� ���� �÷��̾�(team Sigma �Ҽ� ������)�� ����
				if ( myInfo.ID == IDtoFollow )
				{
					IDtoFollow = ID + 1;
				}
			}
			else
			{
				IDtoFollow = seasonNumber;
			}
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		// �׻� ���� �Ѵ� �� �÷��̾� ������ ��Ȱ
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return new Point(row, column);
		
		// ���� �� �÷��̾ ���ٸ� ��ġ ����
		return null;
	}
	
}
