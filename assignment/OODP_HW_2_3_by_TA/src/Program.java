import java.util.Random;

public class Program
{
	/* -----------------------------------------
	 * ���� ��踦 ���� �ʵ� ���� �κ�
	 */
	
	/**
	 * ������� Pitch()�� ȣ���� �� Ƚ��(base�� ���߱� ���� ������ ���� ��)�Դϴ�. 
	 */
	static int count_pitches;
	
	/**
	 * ������� ResetData()�� ȣ���� �� Ƚ��(��ü ���� ��)�Դϴ�.
	 */
	static int count_games;
	
	
	
	/* -----------------------------------------
	 * �߰� ������ ���� �κ�
	 */
	
	/**
	 * �� ���ڰ� base�� �� �� �ִ� ���ɼ�(����)�� ����� �δ� �ʵ��Դϴ�. 
	 * ������ 0�� ��� �ش� ���ڴ� ���� base�� �� �� �����ϴ�.
	 */
	static int[] potents = new int[1000];
	
	/**
	 * ������� ����� ���� ū ���� ���Դϴ�.
	 */
	static int max_potents;
	
	/**
	 * ������� �����, ������ ���� ū �����Դϴ�.
	 */
	static int number_max_potents;
	
	/**
	 * ���� ������ �����ϱ� ���� �߰� �����͸� �ʱ�ȭ�մϴ�.
	 */
	static void ResetData()
	{
		++count_games;
		
		for ( int number = 0; number < potents.length; ++number )
		{
			// ���ʿ� ���� �� �Ǵ� ���ڴ� ������ 0���� �ΰ� �������� 1�� �ʱ�ȭ
			if ( IsValidNumber(number) == false )
				potents[number] = 0;
			else
				potents[number] = 1;
		}
		
		// ���� 1�̴� ���� �ִ밪�� 1
		max_potents = 1;
		
		// �ִ밪�� �ش��ϴ� ���� ���� ���ڴ� ������ 123 --> ������ 123�� ���� ���� ����
		number_max_potents = 123;
	}

	
	
	/* -----------------------------------------
	 * ���� ���� �ڵ带 Ʃ���ϱ� ���� �ʵ� ���� �κ�
	 */
	
	/**
	 * ���� ������� ����ϴ� ���̽��� '����ġ'�� ����� �δ� �ʵ��Դϴ�.
	 * ���� 0S0B, 0S1B, 0S2B, 0S3B, 1S0B, 1S1B, 1S2B, 2S0B�� ���� ����ġ�� �ǹ��մϴ�.
	 * ������ ���⼭�� ����ġ�� �������� �� ������ ���� '�ּ��� ����ġ'�� �������� �����̹Ƿ� ������ ����� �� �ʿ�� �����ϴ�. 
	 */
	static int[] weights = { 4, 4, 98, 82, 2, 0, 89, 86 };
	
	/**
	 * ������� ����� ���� ���� '�� ���� ��'�Դϴ�.
	 * �ʱⰪ�� '�ʱ� ����ġ�� �ݿ����� ���� ��'�Դϴ�.
	 */
	static int min_pitches = 2408;
	
	/**
	 * ���� '�� ���� ��'�� ���� ���� ����ġ ������ ��� �� �ʵ��Դϴ�.
	 */
	static int[] weights_min_pitches = new int[8];

	
	
	/* -----------------------------------------
	 * ���� �߱��� ���� ��ƿ��Ƽ �޼��� ���� �κ�
	 */
	
	/**
	 * ���ڸ� ������ ��ȯ�մϴ�.
	 * �� �޼���� ������ ��ȿ��(validity) ���θ� ������� �ʽ��ϴ�.
	 */
	static int[] NumberToBall(int number)
	{
		int[] result = new int[3];
		
		result[0] = number / 100;
		result[1] = number % 100 / 10;
		result[2] = number % 10;
		
		return result;
	}
	
	/**
	 * ������ ���ڷ� ��ȯ�մϴ�.
	 * �� �޼���� ������ ��ȿ��(validity) ���θ� ������� �����Ƿ� ������ ��� ������ ���� ������ ����� ���� �� �ֽ��ϴ�.
	 */
	static int BallToNumber(int[] ball)
	{
		return ball[0] * 100 + ball[1] * 10 + ball[2];
	}
	
	/**
	 * �ش� ������ ���� �߱����� ��� ������ ����(1���� 9����, ��ħ �Ұ�)���� ���θ� ��ȯ�մϴ�.
	 */
	static boolean IsValid(int[] ball)
	{
		// �� �ڸ����� 1 �̻� 9 ���ϸ鼭 ���� ��ġ�� ������ ��
		return ball[0] >= 1 && ball[0] <= 9 &&
				ball[1] >= 1 && ball[1] <= 9 &&
				ball[2] >= 1 && ball[2] <= 9 &&
				ball[0] != ball[1] &&
				ball[0] != ball[2] &&
				ball[1] != ball[2];		
	}
	
	/**
	 * �ش� ���ڰ� ���� �߱����� ��� ������ ��(1���� 9����, ��ħ �Ұ�)���� ���θ� ��ȯ�մϴ�. 
	 * �� �޼���� ���������� ���ڸ� ������ �ٲ� ���� IsValid()�� ȣ���մϴ�.
	 */
	static boolean IsValidNumber(int number)
	{
		int[] ball = NumberToBall(number);
		
		return IsValid(ball);
	}
	
	
	/* -----------------------------------------
	 * ���� �߱��� �����ϱ� ���� �޼��� ���� �κ�
	 */
	
	/**
	 * Base�� ���߱� ���� ���ο� ������ ����� ��ȯ�մϴ�. 
	 * ���ο� ������ ������ '���� ���� ������ ���� ����'�� ������� ����ϴ�.
	 * (���� ������ Think()���� �̷�����ϴ�)
	 */
	static int[] Pitch()
	{
		++count_pitches;
		
		int[] pitch = NumberToBall(number_max_potents);
		
		return pitch;
	}
	
	/**
	 * �־��� ������ ���� ������ �����ϰ� �� ����� ��ȯ�մϴ�. 
	 * [0]�� strike ��,  
	 * [1]�� ball ���� �ǹ��մϴ�. 
	 */
	static int[] Catch(int[] ball, int[] base)
	{
		int[] result = new int[2];
		
		if ( ball[0] == base[0] )
			++result[0];
		else if ( ball[0] == base[1] || ball[0] == base[2] )
			++result[1];
		
		if ( ball[1] == base[1] )
			++result[0];
		else if ( ball[1] == base[0] || ball[1] == base[2] )
			++result[1];
		
		if ( ball[2] == base[2] )
			++result[0];
		else if ( ball[2] == base[0] || ball[2] == base[1] )
			++result[1];
		
		return result;
	}
	
	/**
	 * �̹��� ���� ������ �� ����� �������� '����'�� ������մϴ�.
	 */
	static void Think(int[] ball, int[] result)
	{
		// ���� �̹� ����� ���� ������ ����ϴ� ���ڵ��� �ľ�
		for ( int number = 0; number < potents.length; ++number )
		{
			// ���� ������ ���� �ִ� ���ڵ鿡 ���� �˻� ����
			if ( potents[number] != 0 )
			{
				// �ش� ���ڰ� ���� base��� ���Ծ�� �� ����� �߷�
				int[] expectedResult = Catch(ball, NumberToBall(number));
				
				// �߷е� ����� ���� ����� ����Ǵ� ��� �� ���ڴ� ���� ���
				// (���⿡�� '��� ���� ����' ���� ���Ե�. ��� ���� ������ base���ٸ� �ݵ�� 3S�� ����� �����װ� �׷��ٸ� Think()�� ȣ����� �ʾ����״�)
				if ( result[0] != expectedResult[0] || result[1] != expectedResult[1] )
					potents[number] = 0;
				
				// �߷е� ����� ���� ����� ��ġ�ϸ� �켱 '���� ����'���� ǥ���� ��
				else
					potents[number] = 1;
			}
		}
		
		
		// ���� ������ ���� �ִ� ���ڵ鿡 ���� ���� ����� �� �ִ밪 ����
		max_potents = 0;
		
		// �� ���ڰ� base��� ���� �Ͽ�...
		for ( int numberOfAssumedBase = 0; numberOfAssumedBase < potents.length; ++numberOfAssumedBase )
		{
			if ( potents[numberOfAssumedBase] != 0 )
			{
				int[] base_assumed = NumberToBall(numberOfAssumedBase);
				
				// �ٸ� ���ڵ��� ���� ����...
				for ( int numberToTest = 0; numberToTest < potents.length; ++numberToTest )
				{
					if ( potents[numberToTest] != 0 && numberOfAssumedBase != numberToTest )
					{
						// ������ �� ���Ǵ� ����� ���� ����ġ�� �ο��Ͽ� ���� ����
						int[] expectedResult = Catch(NumberToBall(numberToTest), base_assumed);
						
						switch ( expectedResult[0] * 10 + expectedResult[1] )
						{
						case 00:
							potents[numberToTest] += weights[0];
							break;
						case 01:
							potents[numberToTest] += weights[1];
							break;
						case 02:
							potents[numberToTest] += weights[2];
							break;
						case 03:
							potents[numberToTest] += weights[3];
							break;
						case 10:
							potents[numberToTest] += weights[4];
							break;
						case 11:
							potents[numberToTest] += weights[5];
							break;
						case 12:
							potents[numberToTest] += weights[6];
							break;
						case 20:
							potents[numberToTest] += weights[7];
							break;
						}
					}
				}
				
				// �ջ�� ������ �ִ밪�� �����Ѵٸ� �̸� ����� ��
				if ( potents[numberOfAssumedBase] > max_potents )
				{
					max_potents = potents[numberOfAssumedBase];
					number_max_potents = numberOfAssumedBase;
				}
			}
		}
	}
	
	

	
	
	
	
	public static void main(String[] args)
	{
		Random rand = new Random();
		
		int[] base;
		int[] pitch;
		int[] result;
		
		int count_tests = 0;
		
		while ( true )
		{
			// �������� 000���� 999���� ���� for���� ������...
			for ( int number = 0; number < 1000; ++number )
			{
				// ��ȿ�� ���ڿ� ���ؼ��� ������ ����
				if ( IsValidNumber(number) == true )
				{
					// �����ϱ� ���� �߰� ������ �ʱ�ȭ
					ResetData();
	
					// �̹� ���ڿ� ���� base ����
					base = NumberToBall(number);
					
					while ( true )
					{
						// ������
						pitch = Pitch();
						
						// �ް�
						result = Catch(pitch, base);
						
						// ����� 3S�� �¸�(���� ����)
						if ( result[0] == 3 )
							break;
						
						// �׷��� ������ ������ �� ������ ����
						Think(pitch, result);
					}
				}
			}
			
			double score = (double)count_pitches / count_games;

			// ��� ���
			System.out.println("Test#" + count_tests);
			System.out.println("Tot. pitches: " + count_pitches);
			System.out.println("Tot.   games: " + count_games);
			System.out.println("Avg. pitches: " + score);
			
			// ��� ����� ���� ���� ������ ������ �̹� �õ��� ���� ����� �����ߴ��� ���� Ȯ��
			if ( count_pitches < min_pitches )
			{
				// �����ߴٸ� �̸� ����ϰ� �ֿܼ� ��� ���
				min_pitches = count_pitches;
				
				for ( int iWeight = 0; iWeight < weights.length; ++iWeight )
					weights_min_pitches[iWeight] = weights[iWeight];

				System.out.println("Weights:");
				System.out.println("    0S0B - " + weights[0]);
				System.out.println("    0S1B - " + weights[1]);
				System.out.println("    0S2B - " + weights[2]);
				System.out.println("    0S3B - " + weights[3]);
				System.out.println("    1S0B - " + weights[4]);
				System.out.println("    1S1B - " + weights[5]);
				System.out.println("    1S2B - " + weights[6]);
				System.out.println("    2S0B - " + weights[7]);
			}
			else if ( count_pitches == min_pitches )
			{
				// ������ ���� ��쿡�� ����ġ �迭�� ����
				for ( int iWeight = 0; iWeight < weights.length; ++iWeight )
					weights_min_pitches[iWeight] = weights[iWeight];
			}
			
			
			System.out.println();

			
			// ���� �׽�Ʈ�� ���� ����ġ �迭 �ణ ������ ����
			int idxToModify = rand.nextInt(weights.length);
			weights[idxToModify] += rand.nextInt(5) - 2;
			
			// ��� �ٲ� ����ġ�� �ʹ� �۰ų� �ʹ� ũ�� �ʵ��� ������ ����
			if ( weights[idxToModify] < 0 )
				weights[idxToModify] = 0;
			
			if ( weights[idxToModify] > 100 )
				weights[idxToModify] = 100;

			
			count_games = 0;
			count_pitches = 0;
			++count_tests;

			// 100ȸ���� ������� ã�� ���� ��ϰ� ����ġ�� ���(�������� �� �� �ص� ���� Ȯ���� �� �ֵ���)
			if ( count_tests % 100 == 0 )
			{
				for ( int iWeight = 0; iWeight < weights.length; ++iWeight )
					weights[iWeight] = weights_min_pitches[iWeight];				

				System.out.println("Min. Avg. pitches: " + min_pitches);
				System.out.println("Weights:");
				System.out.println("    0S0B - " + weights[0]);
				System.out.println("    0S1B - " + weights[1]);
				System.out.println("    0S2B - " + weights[2]);
				System.out.println("    0S3B - " + weights[3]);
				System.out.println("    1S0B - " + weights[4]);
				System.out.println("    1S1B - " + weights[5]);
				System.out.println("    1S2B - " + weights[6]);
				System.out.println("    2S0B - " + weights[7]);
			}
		}
	}
}
