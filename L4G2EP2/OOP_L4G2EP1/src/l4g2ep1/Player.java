package l4g2ep1;

import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.Constants;
import l4g2ep1.common.DirectionCode;
import l4g2ep1.common.Point;
import l4g2ep1.common.Vector;

import java.util.ArrayList;

/**
 * ���ǽ� ���� �÷��̾� �� ���� ��Ÿ���� �߻� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public abstract class Player
{
	/**
	 * �÷��̾��� �̸��Դϴ�.
	 */
	public String name;

	/**
	 * '���� ����'�� ���� ������ ���θ� �����ϴ� �ʵ��Դϴ�.
	 * ���� ���� ���� ���� ���θ� �����ϴ� �Ͽ� �� �ʵ��� ���� true�� ������ �θ� ���� ������ �����ϴ� ������ ���ֵ˴ϴ�.
	 * �̹� �Ͽ� ���� ���� ���θ� �����ؾ� �ϴ��� Ȯ���Ϸ��� gameInfo.GetIsDirectInfectionChoosingTurn()�� ��ȯ���� �����ϼ���. 
	 */
	public boolean acceptDirectInfection;

	/**
	 * �÷��̾� �ڽ��� �̹� ���ӿ��� ���� ������� �����Դϴ�.
	 */
	public Score myScore;

	/**
	 * �̹� ���Ӱ� ������ ������ ��� �ֽ��ϴ�.
	 */
	public GameInfo gameInfo;

	/**
	 * �÷��̾� �ڽ��� ���� ���¸� ��� �ֽ��ϴ�.
	 */
	public PlayerInfo myInfo;

	/**
	 * �� �ϸ��� �÷��̾��� ���� ���¿� ���� �� ���縦 �˰� �� �ٸ� �÷��̾���� ���¸� �޾� �� ������ ���θ� ��Ÿ���ϴ�.
	 * 
	 * ���⿡�� ������ ������ �� �ٸ� �������� �������� ���� �˰� �� �÷��̾��,
	 * ����ü ������ �� ������ ��ü��,
	 * ��ȥ ������ �� ���ǽ� ���� �� �ִ� �ٸ� ��ȥ���� ���Ե˴ϴ�.
	 * 
	 * �� �÷��̾� ������ othersInfo_withinSight ��ϰ� ���⿡ �ߺ� ���Ե� �� �ֽ��ϴ�.
	 * (���� ���, ����ü ������ �� �þ� ���� ���� �ִ� ��ü�� �� ��Ͽ� ��� ���Ե˴ϴ�)
	 * 
	 * �� ���� true�� �����ϸ� ���� �Ϻ��� othersInfo_detected ��Ͽ� �ش� ������ ������ �˴ϴ�.
	 */
	public boolean receiveOthersInfo_detected;

	/**
	 * �� �ϸ��� �þ� ���� �ȿ� �ִ� �ڽ� �� �ٸ� �÷��̾���� ���� �Ͽ� ������ �ൿ�� �޾� �� ������ ���θ� ��Ÿ���ϴ�.
	 * �� ���� true�� �����ϸ� ���� �Ϻ��� actions ��Ͽ� �ش� ������ ������ �˴ϴ�.
	 */
	public boolean receiveActions;

	/**
	 * �� �ϸ��� �þ� ���� �ȿ��� ���� �Ͽ� �߻��� ����� �޾� �� ������ ���θ� ��Ÿ���ϴ�.
	 * �� ���� true�� �����ϸ� ���� �Ϻ��� reactions ��Ͽ� �ش� ������ ������ �˴ϴ�.
	 */
	public boolean receiveReactions;

	/**
	 * �÷��̾��� ���� �þ� ���� �ȿ� �ִ� �ڽ� �� �ٸ� �÷��̾���� ���¿� ���� ����Դϴ�.
	 * �÷��̾��� �þ� ������ ���� ���°� ���������� ���� �ٸ��ϴ�.
	 */
	public ArrayList<PlayerInfo> othersInfo_withinSight;

	/**
	 * �÷��̾��� ���� ���¿� ���� �� ���縦 �˰� �� �ٸ� �÷��̾���� ���¿� ���� ����Դϴ�.
	 * 
	 * ���⿡�� ������ ������ �� �� �ڽ� �� �ٸ� �������� �������� ���� �˰� �� �÷��̾��,
	 * ����ü ������ �� ������ ��ü��,
	 * ��ȥ ������ �� ���ǽ� ���� �� �ִ� �ٸ� ��ȥ���� ���Ե˴ϴ�.
	 * 
	 * �� �÷��̾� ������ othersInfo_withinSight ��ϰ� ���⿡ �ߺ� ���Ե� �� �ֽ��ϴ�.
	 * (���� ���, ����ü ������ �� �þ� ���� ���� �ִ� ��ü�� �� ��Ͽ� ��� ���Ե˴ϴ�)
	 * 
	 * �� ����� ����Ϸ��� �̸� receiveOthersInfo_detected ���� true�� ������ �ξ�� �մϴ�.
	 */
	public ArrayList<PlayerInfo> othersInfo_detected;

	/**
	 * �÷��̾��� ���� �þ� ���� �ȿ� �ִ� �ڽ� �� �ٸ� �÷��̾���� ���� �Ͽ� ������ �ൿ ����Դϴ�.
	 * �� ����� ����Ϸ��� �̸� receiveActions ���� true�� ������ �ξ�� �մϴ�.
	 * �÷��̾��� �þ� ������ ���� ���°� ���������� ���� �ٸ��ϴ�.
	 */
	public ArrayList<Action> actions;

	/**
	 * �÷��̾��� ���� �þ� ���� �ȿ��� ���� �Ͽ� �߻��� ��� ����Դϴ�.
	 * �� ����� ����Ϸ��� �̸� receiveReactions ���� true�� ������ �ξ�� �մϴ�.
	 * �÷��̾��� �þ� ������ ���� ���°� ���������� ���� �ٸ��ϴ�.
	 */
	public ArrayList<Reaction> reactions;

	/*
	 * ���� ���ǽ� ���� �� ĭ�� ���� ������ ��� �ֽ��ϴ�.
	 * �������� �÷��̾�� �� �ʵ带 ���� ����� �� ������
	 * ��� �Ʒ��� ���ǵ� getter �޼������ ���� ���ϴ� (�׸��� ������) ������ ���������� �޾� �� �� �ֽ��ϴ�.
	 */
	Cells cells;

	public Player()
	{
		othersInfo_withinSight = new ArrayList<PlayerInfo>();
		othersInfo_detected = new ArrayList<PlayerInfo>();
		actions = new ArrayList<Action>();
		reactions = new ArrayList<Reaction>();
	}

	/**
	 * ������ ������ �� �̹� �Ͽ� �̵��� ������ ��ȯ�մϴ�.
	 * ����: �����ڰ� DirectionCode.Stay�� ��ȯ�ϴ� ��� ���� �̵��� �ش��ϹǷ� �г�Ƽ�� �ް� �˴ϴ�.
	 */
	public abstract DirectionCode Survivor_Move();

	/**
	 * ��ü ������ �� �̹� �Ͽ� ������ ���� �ֽ��ϴ�.
	 * ��ü ���¿����� �þ� ������ ���� �� �����Ƿ�
	 * ���ϴ� ��� �� �� ������ �÷��̸� ���� ���� ������ ���� ������ ������ �� �ֽ��ϴ�.
	 */
	public abstract void Corpse_Stay();

	/**
	 * ����ü ������ �� �̹� �Ͽ� �̵��� ������ ��ȯ�մϴ�.
	 * ���� ���ڸ��� �ӹ����� ���� ��� DirectionCode.Stay�� ��ȯ�ϸ� �˴ϴ�.
	 */
	public abstract DirectionCode Infected_Move();

	/**
	 * ��ȥ ������ �� �̹� �Ͽ� ������ ���ġ�� ��ٸ��ϴ�.
	 * ��ȥ ���¿����� �þ� ������ ���� �� �����Ƿ�
	 * ���ϴ� ��� �� �� ������ �÷��̸� ���� ���� ������ ���� ������ ������ �� �ֽ��ϴ�.
	 * �� �޼���� ������ ���۵� �� ���� ���� ȣ��ǹǷ�
	 * ���� ���� ������ �ʵ�鿡 ���� �ʱ�ȭ �ڵ�� ���� 0��°���� ���θ� �˻��ϴ� if���� �Բ� ���⿡ �߰��ϴ� ���� ���ڽ��ϴ�.
	 */
	public abstract void Soul_Stay();

	/**
	 * ��ȥ ������ �� �̹� �Ͽ� ���ġ�� ���ǽ� ���� ��ǥ�� ��ȯ�մϴ�.
	 */
	public abstract Point Soul_Spawn();
	
	/*
	 * -------------------------------------------------------------------------------------
	 * 
	 * �Ʒ��� ���ǵ� �޼������ �������� �÷��̾� �ڵ忡���� ����� �� ������
	 * �������� �ۼ����� �޼��� �ȿ��� this.�� �Է��Ͽ� ����� Ȯ���� �� �ֽ��ϴ�.
	 * 
	 * ����:
	 * 		Player class�� l4g2ep1 package�� ������
	 * 		���� ���� ��Ű���� �ִ� PlayerInfo class, Cells class�� '�������� �� �� ����' �ʵ���� ���� ���� ����� �� �ֽ��ϴ�.
	 * 		�̷��� �ʵ���� ���� getter �޼������ ����ϴ� �Ϳ� ���� �� ȿ�����̹Ƿ�(Ư�� ��ǥ�� �ٷ� ��)
	 * 	 	������ ���� ���ǵǾ� �ִ� ���� ��ƿ��Ƽ �޼������ Ȱ���Ͽ� �ڵ带 �ۼ��ϵ��� �սô�.
	 * 
	 * 		->	�������� ���� Player_YOURNAMEHERE class�� l4g2ep1.customplayers package�� �����Ƿ�
	 * 			�� �Ʒ��� ���� �ִ� �ڵ带 �״�� ������ ����ϸ� ������ ������ ���� �˴ϴ�.
	 * 			� ����� �ڵ�� �ۼ��Ϸ��µ� ��� �ؾ� ���� �� �𸣰ڴٸ� �� ������ ������ ��û�ϼ���.
	 * 
	 * -------------------------------------------------------------------------------------
	 */

	/**
	 * �־��� ��ġ���� �ش� �������� ������ ĭ�� ��ǥ�� ��ȯ�մϴ�.
	 * ����: ��ȯ�Ǵ� ��ǥ�� ��ȿ�� �˻簡 �Ǿ� ���� �����Ƿ� ����ϱ� ���� �� ���� �˻縦 �����ؾ� �մϴ�.
	 * 
	 * @param origin
	 *            ������ ĭ�� ����� ���� ��ǥ�Դϴ�.
	 * @param direction
	 *            ��ǥ�� ��ȯ���� �����Դϴ�.
	 */
	public final Point GetAdjacentPoint(Point origin, DirectionCode direction)
	{
		return new Point(origin, direction);
	}

	/**
	 * �ڽ��� ���� ��ġ���� �ش� �������� ������ ĭ�� ��ǥ�� ��ȯ�մϴ�.
	 * ����: ��ȯ�Ǵ� ��ǥ�� ��ȿ�� �˻簡 �Ǿ� ���� �����Ƿ� ����ϱ� ���� �� ���� �˻縦 �����ؾ� �մϴ�.
	 * 
	 * @param direction
	 *            ��ǥ�� ��ȯ���� �����Դϴ�.
	 */
	public final Point GetAdjacentPoint(DirectionCode direction)
	{
		return GetAdjacentPoint(myInfo.position, direction);
	}

	/**
	 * �̹� �Ͽ� �ش� ĭ���� �̵��� �� �ִ��� ���θ� ��ȯ�մϴ�.
	 * Survivor_Move()�� Infected_Move()���� ������ ��ȯ�ϱ� ���� �� �޼��带 ȣ���Ͽ� Ȥ�� ���� �� �ִ� �� ������ ����ϼ���.
	 * 
	 * @param destination_x
	 *            �̵��Ϸ��� ĭ�� x��ǥ�Դϴ�.
	 * @param destination_y
	 *            �̵��Ϸ��� ĭ�� y��ǥ�Դϴ�.
	 * @return �̵��� �� �ִ� ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean IsValidMove(int destination_x, int destination_y)
	{
		// ���� ���� ���� ������ �Ǵ� ����ü���
		if ( myInfo.state == State.Survivor || myInfo.state == State.Infected )
		{
			// �����ڰ� ���ڸ��� ����ϴ� ���� �Ұ���
			if ( myInfo.state == State.Survivor && myInfo.position.equals(destination_x, destination_y) == true )
				return false;

			// �� ���� �� ĭ �̻� �̵� �Ұ���
			int offset_x = myInfo.position.x - destination_x;
			int offset_y = myInfo.position.y - destination_y;

			if ( offset_x * offset_y != 0 ||
					offset_x > 1 || offset_x < -1 || offset_y > 1 || offset_y < -1 )
				return false;

			// ����������, �ش� ��ǥ�� ��ȿ���� ���� �˻�
			return IsValidSpawn(destination_x, destination_y);
		}

		// �����ڳ� ����ü�� �ƴ϶�� �̵� ��ü�� �Ұ���
		return false;
	}

	/**
	 * �̹� �Ͽ� �ش� ĭ���� �̵��� �� �ִ��� ���θ� ��ȯ�մϴ�.
	 * Survivor_Move()�� Infected_Move()���� ������ ��ȯ�ϱ� ���� �� �޼��带 ȣ���Ͽ� Ȥ�� ���� �� �ִ� �� ������ ����ϼ���.
	 * 
	 * @param destination
	 *            �̵��Ϸ��� ĭ�� ��ǥ�Դϴ�.
	 * @return �̵��� �� �ִ� ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean IsValidMove(Point destination)
	{
		return IsValidMove(destination.x, destination.y);
	}

	/**
	 * �̹� �Ͽ� �ش� �������� �̵��� �� �ִ��� ���θ� ��ȯ�մϴ�.
	 * Survivor_Move()�� Infected_Move()���� ������ ��ȯ�ϱ� ���� �� �޼��带 ȣ���Ͽ� Ȥ�� ���� �� �ִ� �� ������ ����ϼ���.
	 * 
	 * @param direction
	 *            �̵��Ϸ��� �����Դϴ�.
	 * @return �̵��� �� �ִ� ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean IsValidMove(DirectionCode direction)
	{
		// ���� ���� ���� ������ �Ǵ� ����ü���
		if ( myInfo.state == State.Survivor || myInfo.state == State.Infected )
		{
			// ������ ������ '���'�� ����
			if ( direction == null )
				direction = DirectionCode.Stay;

			// �����ڰ� ���ڸ��� ����ϴ� ���� �Ұ���
			if ( myInfo.state == State.Survivor && direction == DirectionCode.Stay )
				return false;

			// ����������, �ش� ��ǥ�� ��ȿ���� ���� �˻�
			return IsValidSpawn(GetAdjacentPoint(direction));
		}

		// �����ڳ� ����ü�� �ƴ϶�� �̵� ��ü�� �Ұ���
		else
			return false;
	}

	/**
	 * �̹� �Ͽ� �ش� ��ǥ�� ���ġ�ϴ� ���� �������� ���θ� ��ȯ�մϴ�.
	 * Soul_Spawn()���� ��ǥ�� ��ȯ�ϱ� ���� �� �޼��带 ȣ���Ͽ� Ȥ�� ���� �� �ִ� �� ������ ����ϼ���.
	 * 
	 * @param destination_x
	 *            ���ġ�Ϸ��� x��ǥ�Դϴ�.
	 * @param destination_y
	 *            ���ġ�Ϸ��� y��ǥ�Դϴ�.
	 * @return ���ġ�� ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean IsValidSpawn(int destination_x, int destination_y)
	{
		// ��ǥ�� ���ǽ� ������ ������� ���� Ȯ��
		return destination_x >= 0 && destination_x < Constants.Classroom_Width &&
				destination_y >= 0 && destination_y < Constants.Classroom_Height;
	}

	/**
	 * �̹� �Ͽ� �ش� ��ǥ�� ���ġ�ϴ� ���� �������� ���θ� ��ȯ�մϴ�.
	 * Soul_Spawn()���� ��ǥ�� ��ȯ�ϱ� ���� �� �޼��带 ȣ���Ͽ� Ȥ�� ���� �� �ִ� �� ������ ����ϼ���.
	 * 
	 * @param destination
	 *            ���ġ�Ϸ��� ��ǥ�Դϴ�.
	 * @return ���ġ�� ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean IsValidSpawn(Point destination)
	{
		return IsValidSpawn(destination.x, destination.y);
	}

	/**
	 * �� ��ǥ ������ �Ÿ�(�̵��ϴµ� �ɸ��� ĭ ��)�� ��ȯ�մϴ�.
	 * 
	 * @param from_x
	 *            �Ÿ��� ������ ���� x��ǥ�Դϴ�.
	 * @param from_y
	 *            �Ÿ��� ������ ���� y��ǥ�Դϴ�.
	 * @param to_x
	 *            �Ÿ��� ������ ��� x��ǥ�Դϴ�.
	 * @param to_y
	 *            �Ÿ��� ������ ��� y��ǥ�Դϴ�.
	 */
	public final int GetDistance(int from_x, int from_y, int to_x, int to_y)
	{
		int offset_x = from_x - to_x;
		int offset_y = from_y - to_y;

		if ( offset_x < 0 )
			offset_x = -offset_x;

		if ( offset_y < 0 )
			offset_y = -offset_y;

		return offset_x + offset_y;
	}

	/**
	 * �� ��ǥ ������ �Ÿ�(�̵��ϴµ� �ɸ��� ĭ ��)�� ��ȯ�մϴ�.
	 * 
	 * @param from
	 *            �Ÿ��� ������ ���� ��ǥ�Դϴ�.
	 * @param to_x
	 *            �Ÿ��� ������ ��� x��ǥ�Դϴ�.
	 * @param to_y
	 *            �Ÿ��� ������ ��� y��ǥ�Դϴ�.
	 */
	public final int GetDistance(Point from, int to_x, int to_y)
	{
		return GetDistance(from.x, from.y, to_x, to_y);
	}

	/**
	 * �� ��ǥ ������ �Ÿ�(�̵��ϴµ� �ɸ��� ĭ ��)�� ��ȯ�մϴ�.
	 * 
	 * @param from
	 *            �Ÿ��� ������ ���� ��ǥ�Դϴ�.
	 * @param to
	 *            �Ÿ��� ������ ��� ��ǥ�Դϴ�.
	 */
	public final int GetDistance(Point from, Point to)
	{
		return GetDistance(from.x, from.y, to.x, to.y);
	}
	
	/**
	 * �� �÷��̾ ��ġ�� ��ǥ�� �մ� ���͸� ����� ��ȯ�մϴ�.
	 * ��ȯ�� ���ʹ� ���� �÷��̾�� ��� �÷��̾ � �������� �󸶳� ������ �ִ��� Ȯ���ϴ� �뵵�� ���� �� �ֽ��ϴ�.
	 * ���� ���� �÷��̾�� �ڱ� �ڽ� ( myInfo )�� �˴ϴ�.
	 * ���� �÷��̾�� ��� �÷��̾ ���� �ٲ�� ��� ������ ���⵵ �����˴ϴ�.
	 * 
	 * @param origin
	 * 			��ġ�� ���� ������ �Ǵ� �÷��̾��Դϴ�.
	 * @param target
	 * 			��ġ�� ���� ����� �Ǵ� �÷��̾��Դϴ�.
	 */
	public final Vector GetDistanceVectorBetweenPlayers(PlayerInfo origin, PlayerInfo target)
	{
		return new Vector(origin.position, target.position);
	}
	
	/**
	 * �ش� �÷��̾�� �� �ڽ��� ��ġ�� ��ǥ�� �մ� ���͸� ����� ��ȯ�մϴ�.
	 * ��ȯ�� ���ʹ� �ش� �÷��̾ ���� � �������� �󸶳� ������ �ִ��� Ȯ���ϴ� �뵵�� ���� �� �ֽ��ϴ�.
	 * 
	 * @param other
	 * 			��ġ�� ���� ����� �Ǵ� �÷��̾��Դϴ�.
	 */
	public final Vector GetDistanceVectorBetweenPlayers(PlayerInfo other)
	{
		return GetDistanceVectorBetweenPlayers(myInfo, other);
	}

	/**
	 * ���� �ش� ��ǥ�� ĭ�� ���� �� �� �ִ��� ���θ� ��ȯ�մϴ�.
	 * �����ڴ� �ڽ� ���� 2ĭ, ��ü�� �ڽ��� ���� �ִ� ĭ, ����ü�� �ڽ� �߽� 5x5ĭ, �׸��� ��ȥ�� ��� ĭ�� ���� �� �� �ֽ��ϴ�.
	 * 
	 * @param x
	 *            �˻��� x��ǥ�Դϴ�.
	 * @param y
	 *            �˻��� y��ǥ�Դϴ�.
	 * @return �ش� ĭ�� ���� �� �� �ִ� ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean CanSee(int x, int y)
	{
		// ��ȿ���� ���� ��ǥ�� ��� �� �� ����
		if ( IsValidSpawn(x, y) == false )
			return false;

		Point myPosition = myInfo.GetPosition();

		switch (myInfo.GetState())
		{
		case Survivor:
			// �����ڴ� �ڽ� ���� 2ĭ�� �� �� ����
			return GetDistance(myPosition, x, y) <= 2;
		case Corpse:
			// ��ü�� �ڽ��� ���� �ִ� ĭ�� �� �� ����
			return myPosition.x == x && myPosition.y == y;
		case Infected:
			// ����ü�� �ڽ� �߽� 5x5ĭ�� �� �� ����
			int offset_x = myPosition.x - x;
			int offset_y = myPosition.y - y;
			return offset_x >= -2 && offset_x <= 2 && offset_y >= -2 && offset_y <= 2;
		case Soul:
			// ��ȥ�� ��ȿ�� ��� ĭ�� �� �� ����
			return true;
		default:
			return false;
		}
	}

	/**
	 * ���� �ش� ��ǥ�� ĭ�� ���� �� �� �ִ��� ���θ� ��ȯ�մϴ�.
	 * �����ڴ� �ڽ� ���� 2ĭ, ��ü�� �ڽ��� ���� �ִ� ĭ, ����ü�� �ڽ� �߽� 5x5ĭ, �׸��� ��ȥ�� ��� ĭ�� ���� �� ��
	 * �ֽ��ϴ�.
	 * 
	 * @param location
	 *            �˻��� ��ǥ�Դϴ�.
	 * @return �ش� ĭ�� ���� �� �� �ִ� ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public final boolean CanSee(Point location)
	{
		return CanSee(location.x, location.y);
	}

	/**
	 * �ش� ��ǥ�� ĭ�� ���� ������ �����ɴϴ�.
	 * ���� �ش� ĭ�� ���� �þ� ���� ���� ���� ���� ��� '��� �ִ� ĭ'�� ��ȯ�մϴ�.
	 * 
	 * @param x
	 *            ĭ ������ ������ x��ǥ�Դϴ�.
	 * @param y
	 *            ĭ ������ ������ y��ǥ�Դϴ�.
	 */
	public final CellInfo GetCellInfo(int x, int y)
	{
		if ( CanSee(x, y) == false ) { return CellInfo.Blank; }

		return cells.data[y][x];
	}

	/**
	 * �ش� ��ǥ�� ĭ�� ���� ������ �����ɴϴ�.
	 * ���� �ش� ĭ�� ���� �þ� ���� ���� ���� ���� ��� '��� �ִ� ĭ'�� ��ȯ�մϴ�.
	 * 
	 * @param location
	 *            ĭ ������ ������ ��ǥ�Դϴ�.
	 */
	public final CellInfo GetCellInfo(Point location)
	{
		return GetCellInfo(location.x, location.y);
	}
}
