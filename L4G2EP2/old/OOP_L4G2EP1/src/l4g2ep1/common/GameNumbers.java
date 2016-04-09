package l4g2ep1.common;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 * ���� ���ӿ��� ����� ���� ��ȣ ����� �����ϴ� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class GameNumbers
{
	public int lengthOfData;
	public int[] data;

	public GameNumbers(int numberOfGames)
	{
		lengthOfData = numberOfGames;
		data = new int[numberOfGames];
	}
	
	/**
	 * �־��� ���Ͽ��� ����Ʈ �迭�� �о� �� ���� ��ȣ ����� ����ϴ�.
	 * ���� ������ ����ϸ� �׻� ���� ����� �����˴ϴ�.
	 * ����: ������ ������ ����� ���� ���� ��� ������ ������ �� �ֽ��ϴ�.
	 * 
	 * @param fileName_seed ���� ��ȣ ��� ������ ����� ���� �̸��Դϴ�.
	 * @return ������ ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public boolean Create(String fileName_seed)
	{
		try
		{
			//Seed�� �� ���Ͽ��� ���ڵ��� �о�� ���� 
			FileReader rs = new FileReader(fileName_seed);
			
			try
			{
				boolean isSameGameNumberFound;
				int newGameNumber;
				
				for ( int iNew = 0; iNew < lengthOfData; ++iNew )
				{
					isSameGameNumberFound = false;
					newGameNumber = rs.read();
					
					//����� ��� ä��� ���� ������ ���� ������ ��� ���� ����
					if ( newGameNumber == -1 )
					{
						if ( rs != null )
							rs.close();
						rs = null;
						
						throw new Exception();
					}
					
					//��� ���� ���ڰ� 16��Ʈ ������ ��� ���� ���� �ϳ��� �����Ͽ� 32��Ʈ ���� ����
					if ( newGameNumber > 0x100)
					{
						newGameNumber <<= 16;
						newGameNumber += rs.read();
					}
					//��� ���� ���ڰ� 8��Ʈ ������ ��� ���� ���� �Ѱ� �����Ͽ� 24 ~ 32��Ʈ ���� ���� 
					else
					{
						newGameNumber <<= 8;
						newGameNumber += rs.read();
						newGameNumber <<= 8;
						newGameNumber += rs.read();
					}
					
					//����������, ������ ���ڰ� ������ ��� ����� ����
					if ( newGameNumber < 0 )
						newGameNumber += Integer.MAX_VALUE;
					
					//��ġ�� �ʴ� ���� ��ȣ ������ ����� ���� ������ ���� ���� ��ȣ��� ��
					for ( int iCurrent = 0; iCurrent < iNew; ++iCurrent )
					{
						if ( newGameNumber == data[iCurrent] )
						{
							isSameGameNumberFound = true;
							break;
						}
					}
					
					//��ġ�� ��� �̹� ���� ��ȣ�� �ٽ� ����
					if ( isSameGameNumberFound == true )
						--iNew;
					//��ġ�� �ʴ� ��� ���� ��ȣ ��Ͽ� ���
					else
						data[iNew] = newGameNumber;
				}
			}
			catch ( Exception e )
			{
				System.err.println("���� ��ȣ ����� ������ �� �����ϴ�. Seed ������ ũ�Ⱑ �ʹ� �۽��ϴ�.");
				e.printStackTrace();
				
				if ( rs != null )
					rs.close();
				
				return false;
			}

			rs.close();
		}
		catch ( Exception e )
		{
			System.err.println("���� ��ȣ ����� ������ �� �����ϴ�. ���� �̸��� �߸��Ǿ��ų� �ش� �̸��� ������ ����� �� �����ϴ�.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * �־��� seed���� ����� ��� �̸� ���� ������ ���� ��ȣ ����� ����ϴ�.
	 * seed���� 0 �Ǵ� ������ ��� ���� �ð� ������ �������� ������ ���� ��ȣ ����� ����ϴ�.
	 * 
	 * @param seed ����� ��� ������ ���� ��ȣ ����� ����� seed�� ���˴ϴ�. �׷��� ���� ��� ���� �ð� ������ �������� ������ ���� ��ȣ ����� ����ϴ�.
	 * @return ������ ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public boolean Create(int seed)
	{
		java.util.Random rand;

		//seed�� ����� ��� �ش� seed���� ���� ���� ��� �ʱ�ȭ
		if ( seed > 0 )
			rand = new Random(seed);
		//�׷��� ���� ��� ���� �ð� ������ �������� ���� ��� �ʱ�ȭ
		else
			rand = new Random();
		
		boolean isSameGameNumberFound;
		int newGameNumber;
		
		for ( int iNew = 0; iNew < lengthOfData; ++iNew )
		{
			isSameGameNumberFound = false;
			newGameNumber = rand.nextInt();
			
			if ( newGameNumber < 0 )
				newGameNumber += Integer.MAX_VALUE;
			
			//��ġ�� �ʴ� ���� ��ȣ ������ ����� ���� ������ ���� ���� ��ȣ��� ��
			for ( int iCurrent = 0; iCurrent < iNew; ++iCurrent )
			{
				if ( newGameNumber == data[iCurrent] )
				{
					isSameGameNumberFound = true;
					break;
				}
			}
			
			//��ġ�� ��� �̹� ���� ��ȣ�� �ٽ� ����
			if ( isSameGameNumberFound == true )
				--iNew;
			//��ġ�� �ʴ� ��� ���� ��ȣ ��Ͽ� ���
			else
				data[iNew] = newGameNumber;
		}
		
		return true;
	}

	/**
	 * ���� ���� ��ȣ ����� �־��� ���Ͽ� �����մϴ�.
	 * 
	 * @param fileName_result ������ ���� �̸��Դϴ�.
	 * @return ���忡 ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public boolean Save(String fileName_result)
	{
		try
		{
			PrintStream ws = new PrintStream(fileName_result);
			
			for ( int gameNumber : data )
				ws.println(gameNumber);
			
			ws.close();
		}
		catch ( Exception e )
		{
			System.err.println("���� ��ȣ ����� ������ �� �����ϴ�. ���� �̸��� �߸��Ǿ��ų� �ش� �̸��� ������ ����� �� �����ϴ�.");
			e.printStackTrace();
			return false;			
		}
		
		return true;
	}
	
	/**
	 * �־��� ���Ͽ��� ���� ��ȣ ����� �ҷ��ɴϴ�.
	 * 
	 * @param fileName_savedNumbers ���� ��ȣ ����� �ҷ��� ���� �̸��Դϴ�.
	 * @return �ҷ����⿡ ������ ��� true, �׷��� ���� ��� false�Դϴ�.
	 */
	public boolean Load(String fileName_savedNumbers)
	{
		try
		{
			FileInputStream fs = new FileInputStream(fileName_savedNumbers);
			Scanner scanner = new Scanner(fs);
			
			for ( int i = 0; i < lengthOfData; ++i )
			{
				if ( scanner.hasNext() == false )
				{
					scanner.close();
					System.err.println("���� ��ȣ ����� �ҷ��� �� �����ϴ�. ������ ������ �ʹ� ª���ϴ�.");
					return false;
				}
				
				data[i] = scanner.nextInt();
			}
			
			scanner.close();
		}
		catch (Exception e)
		{
			System.err.println("���� ��ȣ ����� �ҷ��� �� �����ϴ�. ���� �̸��� �߸��Ǿ��ų� �ش� �̸��� ������ ����� �� �����ϴ�.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
