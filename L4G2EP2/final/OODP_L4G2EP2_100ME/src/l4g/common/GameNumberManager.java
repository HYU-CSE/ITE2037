package l4g.common;

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
public class GameNumberManager
{
	public int lengthOfData;
	public long[] data;
	
	Random rand;

	public GameNumberManager(int numberOfGames)
	{
		lengthOfData = numberOfGames;
		data = new long[numberOfGames];
	}
	
	/**
	 * �־��� ���Ͽ��� ����Ʈ �迭�� �о� �� ���� ��ȣ ����� ����ϴ�.
	 * ���� ������ ����ϸ� �׻� ���� ����� �����˴ϴ�.
	 * ���� ���θ� ��ȯ�մϴ�.<br>
	 * <br>
	 * <b>����:</b> ������ ������ ����� ���� ���� ��� ������ ������ �� �ֽ��ϴ�.
	 * 
	 * @param fileName_seed ���� ��ȣ ��� ������ ����� ���� �̸��Դϴ�.
	 */
	public boolean Create(String fileName_seed)
	{
		try
		{
			//Seed�� �� ���Ͽ��� ���ڵ��� �о�� ���� 
			FileReader rs = new FileReader(fileName_seed);
			
			long seed = 0;
			int input = 0;
			
			while ( input != -1 )
			{
				seed += input;
				input = rs.read();
			}			

			rs.close();

			rand = new Random(seed);
			
			boolean isSameGameNumberFound;
			long newGameNumber;
				
			for ( int iNew = 0; iNew < lengthOfData; ++iNew )
			{
				isSameGameNumberFound = false;
				
				newGameNumber = rand.nextLong();
				
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
			System.err.println("���� ��ȣ ����� ������ �� �����ϴ�. ���� �̸��� �߸��Ǿ��ų� �ش� �̸��� ������ ����� �� �����ϴ�.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/**
	 * �־��� seed���� ����� ��� �̸� ���� ������ ���� ��ȣ ����� ����ϴ�.
	 * seed���� 0 �Ǵ� ������ ��� ���� �ð� ������ �������� ������ ���� ��ȣ ����� ����ϴ�.
	 * ���� ���θ� ��ȯ�մϴ�.
	 * 
	 * @param seed ����� ��� ������ ���� ��ȣ ����� ����� seed�� ���˴ϴ�. �׷��� ���� ��� ���� �ð� ������ �������� ������ ���� ��ȣ ����� ����ϴ�.
	 */
	public boolean Create(int seed)
	{
		//seed�� ����� ��� �ش� seed���� ���� ���� ��� �ʱ�ȭ
		if ( seed > 0 )
			rand = new Random(seed);
		//�׷��� ���� ��� ���� �ð� ������ �������� ���� ��� �ʱ�ȭ
		else
			rand = new Random();
		
		boolean isSameGameNumberFound;
		long newGameNumber;
		
		for ( int iNew = 0; iNew < lengthOfData; ++iNew )
		{
			isSameGameNumberFound = false;
			newGameNumber = rand.nextLong();
			
			if ( newGameNumber < 0 )
				newGameNumber += Long.MAX_VALUE;
			
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
	 * �־��� ������ �����Ͽ� 1�� �����ϴ� ���� ��ȣ ����� ����ϴ�.
	 * �־��� ���� 0 �Ǵ� ������ ��� �����մϴ�.
	 * ���� ���θ� ��ȯ�մϴ�.
	 * 
	 * @param startNumber ����� ù ��°�� �ش��ϴ� ���� ��ȣ�Դϴ�.
	 */
	public boolean Create_Sequence(long startNumber)
	{
		if ( startNumber <= 0 )
			return false;
		
		for ( int iData = 0; iData < lengthOfData; ++iData )
		{
			data[iData] = startNumber;
			++startNumber;
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
			
			for ( long gameNumber : data )
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
				
				data[i] = scanner.nextLong();
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
