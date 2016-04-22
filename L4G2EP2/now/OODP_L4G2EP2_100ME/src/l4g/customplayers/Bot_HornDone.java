package l4g.customplayers;

import java.util.Random;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;

/**
 * ������ �ǻ� ������ �����ϴ� Bot �÷��̾��Դϴ�.
 * �����ڱ� ������ ���� ���� ��ȣ, ���� ID�� ����ϸ� �׻� ������ �ǻ� ������ �����մϴ�.<br>
 * <br>
 * ���� ����: 50% Ȯ���� �����մϴ�.<br>
 * ������ �̵�: �յ� Ȯ���� �̵� ������ ����� �� �ϳ��� ���ϴ�.<br>
 * ����ü �̵�: 20% Ȯ���� ��ȭ �⵵�� �õ��մϴ�. �׷��� ���� ��� �յ� Ȯ���� �̵� ������ ����� �� �ϳ��� ���ϴ�.<br>
 * ��ȥ ��ġ: �յ� Ȯ���� �ƹ� ĭ�̳� ���ϴ�.<br>
 * <br>
 * <b>����:</b> ������ Bot �÷��̾�� ���� ���ӿ����� ������ �ʽ��ϴ�.
 * ����, �������� �ۼ��ϴ� �÷��̾�� � �����ε� <code>java.util.Random class</code>�� ����� �� �����ϴ�.
 * ������ Bot �÷��̾�� �ܼ��� �׽�Ʈ�� ���� '�������� �÷��̾ ������� �ø��� ��Ȳ'�� �� �� ���� ������ ���� �뵵�� ����ؾ� �մϴ�.
 *  
 * @author Racin
 *
 */
public class Bot_HornDone extends Player
{
	/**
	 * �̵��� ������ �켱 ������ �����ϱ� ���� �迭�Դϴ�.
	 * ��� Bot �÷��̾���� �ڽ��� ID ���� ���� ���� �ٸ� �켱 ������ ������ �̵��� �����մϴ�.
	 */
	DirectionCode[] directions;
	
	Random rand;

	public Bot_HornDone(int ID)
	{
		super(ID, "������#" + ID);
		
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result;
		int pos_directions;

		trigger_acceptDirectInfection = rand.nextBoolean();

		//�̵� ������ ���� ������ ����(�� ���� �� ��� ���� �׻� �̵� �����ϹǷ� ���� ���� ���) 
		while ( true )
		{
			pos_directions = rand.nextInt(4);
			result = directions[pos_directions];
			
			//�ش� �������� �̵� �����ϴٸ� ����
			Point pos_destination = myInfo.position.GetAdjacentPoint(result);
			if ( pos_destination.row >= 0 && pos_destination.row < Constants.Classroom_Height &&
					pos_destination.column >= 0 && pos_destination.column < Constants.Classroom_Width )
				return result;
		}
	}

	@Override
	public void Corpse_Stay()
	{
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result;
		int pos_directions = rand.nextInt(5);
		
		//20% Ȯ���� Stay ����
		if ( pos_directions == 4 )
			return DirectionCode.Stay;
		
		//�̵� ������ ���� ������ ����(�� ���� �� ��� ���� �׻� �̵� �����ϹǷ� ���� ���� ���) 
		while ( true )
		{
			result = directions[pos_directions];
			
			//�ش� �������� �̵� �����ϴٸ� ����
			Point pos_destination = myInfo.position.GetAdjacentPoint(result);
			if ( pos_destination.row >= 0 && pos_destination.row < Constants.Classroom_Height &&
					pos_destination.column >= 0 && pos_destination.column < Constants.Classroom_Width )
				return result;
			
			pos_directions = rand.nextInt(4);
		}
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			rand = new Random(ID + gameNumber);
			trigger_acceptDirectInfection = rand.nextBoolean();
			
			/*
			 * �̵� �켱 ���� ����(ID���� ����Ͽ� 4x3x2x1 = 24���� ��� �� �ϳ� ����)
			 */
			switch ( ID % 24 )
			{
			case 0:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Left, DirectionCode.Right, DirectionCode.Down};
				break;
			case 1:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Left, DirectionCode.Down, DirectionCode.Right};
				break;
			case 2:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Right, DirectionCode.Left, DirectionCode.Down};
				break;
			case 3:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Right, DirectionCode.Down, DirectionCode.Left};
				break;
			case 4:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Down, DirectionCode.Left, DirectionCode.Right};
				break;
			case 5:
				directions = new DirectionCode[]{DirectionCode.Up, DirectionCode.Down, DirectionCode.Right, DirectionCode.Left};
				break;

			case 6:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Up, DirectionCode.Right, DirectionCode.Down};
				break;
			case 7:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Up, DirectionCode.Down, DirectionCode.Right};
				break;
			case 8:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Right, DirectionCode.Up, DirectionCode.Down};
				break;
			case 9:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Right, DirectionCode.Down, DirectionCode.Up};
				break;
			case 10:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Down, DirectionCode.Up, DirectionCode.Right};
				break;
			case 11:
				directions = new DirectionCode[]{DirectionCode.Left, DirectionCode.Down, DirectionCode.Right, DirectionCode.Up};
				break;
			
			case 12:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Up, DirectionCode.Left, DirectionCode.Down};
				break;
			case 13:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Up, DirectionCode.Down, DirectionCode.Left};
				break;
			case 14:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Left, DirectionCode.Up, DirectionCode.Down};
				break;
			case 15:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Left, DirectionCode.Down, DirectionCode.Up};
				break;
			case 16:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Down, DirectionCode.Up, DirectionCode.Left};
				break;
			case 17:
				directions = new DirectionCode[]{DirectionCode.Right, DirectionCode.Down, DirectionCode.Left, DirectionCode.Up};
				break;

			case 18:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Up, DirectionCode.Left, DirectionCode.Right};
				break;
			case 19:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Up, DirectionCode.Right, DirectionCode.Left};
				break;
			case 20:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Left, DirectionCode.Up, DirectionCode.Right};
				break;
			case 21:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Left, DirectionCode.Right, DirectionCode.Up};
				break;
			case 22:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Right, DirectionCode.Up, DirectionCode.Left};
				break;
			default:
				directions = new DirectionCode[]{DirectionCode.Down, DirectionCode.Right, DirectionCode.Left, DirectionCode.Up};
				break;
			}
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		return new Point(rand.nextInt(Constants.Classroom_Height), rand.nextInt(Constants.Classroom_Width));
	}

}
