package l4g.customplayers;

import l4g.common.*;


/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_JEONGMIN extends Player
{
   
   // TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
   public Player_JEONGMIN(int ID)
   {
      
      // TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
      super(ID, "���Ʒ������Ʒ�");
      
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
      
      int state = GoUp;
      
      void UpdateState()
      {
         switch(state)
         {
            case GoRight:
               if(this.myInfo.position.column == Constants.Classroom_Width-1)
                  state = GoDown;
               break;
            case GoDown:
               if(this.myInfo.position.row == Constants.Classroom_Height-1)
                  state = GoUp;
               break;
            case GoLeft:
               if(this.myInfo.position.column == 0)
                  state = GoUp;
               break;
            case GoUp:
               if(this.myInfo.position.row == 0)
                  state = GoDown;
               break;
               
         }
      }
      
      @Override
      public DirectionCode Survivor_Move()
      {
         DirectionCode result = null;
         
         UpdateState();
         switch(state)
         {
            case GoRight:
               result = DirectionCode.Right;
               break;
            case GoDown:
               result = DirectionCode.Down;
               break;
            case GoLeft:
               result = DirectionCode.Left;
               break;
            case GoUp:
               result = DirectionCode.Up;
               break;
               
         }
         
         // Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
         
         return result;
      }

      @Override
      public void Corpse_Stay()
      {
         
      }

      @Override
      public DirectionCode Infected_Move()
      {
         

         return Survivor_Move();
      }

      @Override
      public void Soul_Stay()
      {
         
      }

      @Override
      public Point Soul_Spawn()
      {
         state = GoDown;
         return new Point(6,6);
      }
   }