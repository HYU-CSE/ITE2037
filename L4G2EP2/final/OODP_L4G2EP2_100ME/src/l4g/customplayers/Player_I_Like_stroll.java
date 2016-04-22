package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_I_Like_stroll extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_I_Like_stroll(int ID)
	{
		
		super(ID, "����å������");
	      
	      // TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
	      // �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
	      this.trigger_acceptDirectInfection = false;
	      
	      
	      // TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
	      
	      
	   }
	   
	   /*
	    * TODO#5   ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	    *          �翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	    *          �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	    * 
	    *          L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	    * 
	    *          �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	    *          �׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	    * 
	    *          ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	    *          '�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	    */
	   
	   static final int GoRight = 0;
	   static final int GoDown = 1;
	   static final int GoLeft = 2;
	   static final int GoUp = 3;
	   
	   int state = GoRight;
	   
	   void UpdateState()
	   {
	      switch (state) 
	      {
	      case GoRight:
	         if(this.myInfo.position.column == Constants.Classroom_Width - 1)
	         {
	            state = GoDown;
		         
	         }
	         break;
	      case GoDown:
	    //     if(this.myInfo.position.column == Constants.Classroom_Height - 1)
	         {
	            state = GoLeft;
	         }
	         break;
	      case GoLeft:
	         if(this.myInfo.position.column == 0)
	         {
	            state = GoUp;

	         }
	         break;
	      case GoUp:
	      //   if(this.myInfo.position.row == 0 )
	         {
	            state = GoRight;
	         }
	         break;
	      }
	   }
	   
	   @Override
	   public DirectionCode Survivor_Move()
	   {
	      //DirectionCode result = DirectionCode.Right;
	      
	      UpdateState();
	      
	      switch (state) 
	      {
	      case GoRight:
	         return DirectionCode.Right;
	      case GoDown:
	         return DirectionCode.Down;
	      case GoLeft:
	         return DirectionCode.Left;
	      default:
	         return DirectionCode.Up;

	      
	      }
	      
	      // Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	      
	   
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
	      return Survivor_Move();
	   }
	   @Override
	   public void Soul_Stay()
	   {
	      if ( turnInfo.turnNumber == 0 )
	      {
	         /*
	          * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
	          *        �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
	          */
	      }
	   }

	   @Override
	   public Point Soul_Spawn()
	   {
	      int result_row = 6;
	      int result_column = 6;

	      // Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

	      return new Point(result_row, result_column);
	   }
	}