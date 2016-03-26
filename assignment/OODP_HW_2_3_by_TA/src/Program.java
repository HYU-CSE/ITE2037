import java.util.Random;

public class Program
{
	/* -----------------------------------------
	 * 성능 통계를 위한 필드 선언 부분
	 */
	
	/**
	 * 현재까지 Pitch()를 호출한 총 횟수(base를 맞추기 위해 수열을 던진 수)입니다. 
	 */
	static int count_pitches;
	
	/**
	 * 현재까지 ResetData()를 호출한 총 횟수(전체 게임 수)입니다.
	 */
	static int count_games;
	
	
	
	/* -----------------------------------------
	 * 추가 데이터 선언 부분
	 */
	
	/**
	 * 각 숫자가 base가 될 수 있는 가능성(포텐)을 기록해 두는 필드입니다. 
	 * 포텐이 0인 경우 해당 숫자는 절대 base가 될 수 없습니다.
	 */
	static int[] potents = new int[1000];
	
	/**
	 * 현재까지 집계된 가장 큰 포텐 값입니다.
	 */
	static int max_potents;
	
	/**
	 * 현재까지 집계된, 포텐이 가장 큰 숫자입니다.
	 */
	static int number_max_potents;
	
	/**
	 * 다음 게임을 시작하기 위해 추가 데이터를 초기화합니다.
	 */
	static void ResetData()
	{
		++count_games;
		
		for ( int number = 0; number < potents.length; ++number )
		{
			// 애초에 말이 안 되는 숫자는 포텐을 0으로 두고 나머지는 1로 초기화
			if ( IsValidNumber(number) == false )
				potents[number] = 0;
			else
				potents[number] = 1;
		}
		
		// 전부 1이니 포텐 최대값도 1
		max_potents = 1;
		
		// 최대값에 해당하는 가장 작은 숫자는 언제나 123 --> 언제나 123을 가장 먼저 던짐
		number_max_potents = 123;
	}

	
	
	/* -----------------------------------------
	 * 포텐 산정 코드를 튜닝하기 위한 필드 선언 부분
	 */
	
	/**
	 * 포텐 재산정시 사용하는 케이스별 '가중치'를 기록해 두는 필드입니다.
	 * 각각 0S0B, 0S1B, 0S2B, 0S3B, 1S0B, 1S1B, 1S2B, 2S0B에 대한 가중치를 의미합니다.
	 * 어차피 여기서는 가중치를 랜덤으로 막 설정해 가며 '최선의 가중치'를 때려맞출 예정이므로 순서를 기억해 둘 필요는 없습니다. 
	 */
	static int[] weights = { 4, 4, 98, 82, 2, 0, 89, 86 };
	
	/**
	 * 현재까지 기록한 가장 낮은 '총 던진 수'입니다.
	 * 초기값은 '초기 가중치를 반영했을 때의 값'입니다.
	 */
	static int min_pitches = 2408;
	
	/**
	 * 가장 '총 던진 수'가 낮을 때의 가중치 설정을 담아 둘 필드입니다.
	 */
	static int[] weights_min_pitches = new int[8];

	
	
	/* -----------------------------------------
	 * 숫자 야구를 위한 유틸리티 메서드 정의 부분
	 */
	
	/**
	 * 숫자를 수열로 변환합니다.
	 * 이 메서드는 숫자의 유효성(validity) 여부를 고려하지 않습니다.
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
	 * 수열을 숫자로 변환합니다.
	 * 이 메서드는 수열의 유효성(validity) 여부를 고려하지 않으므로 범위를 벗어난 수열에 대해 엉뚱한 결과가 나올 수 있습니다.
	 */
	static int BallToNumber(int[] ball)
	{
		return ball[0] * 100 + ball[1] * 10 + ball[2];
	}
	
	/**
	 * 해당 수열이 숫자 야구에서 사용 가능한 수열(1부터 9까지, 겹침 불가)인지 여부를 반환합니다.
	 */
	static boolean IsValid(int[] ball)
	{
		// 각 자릿수가 1 이상 9 이하면서 서로 겹치지 않으면 됨
		return ball[0] >= 1 && ball[0] <= 9 &&
				ball[1] >= 1 && ball[1] <= 9 &&
				ball[2] >= 1 && ball[2] <= 9 &&
				ball[0] != ball[1] &&
				ball[0] != ball[2] &&
				ball[1] != ball[2];		
	}
	
	/**
	 * 해당 숫자가 숫자 야구에서 사용 가능한 수(1부터 9까지, 겹침 불가)인지 여부를 반환합니다. 
	 * 이 메서드는 내부적으로 숫자를 수열로 바꾼 다음 IsValid()를 호출합니다.
	 */
	static boolean IsValidNumber(int number)
	{
		int[] ball = NumberToBall(number);
		
		return IsValid(ball);
	}
	
	
	/* -----------------------------------------
	 * 숫자 야구를 진행하기 위한 메서드 정의 부분
	 */
	
	/**
	 * Base를 맞추기 위한 새로운 수열을 만들어 반환합니다. 
	 * 새로운 수열은 언제나 '현재 가장 포텐이 높은 숫자'를 기반으로 만듭니다.
	 * (포텐 재계산은 Think()에서 이루어집니다)
	 */
	static int[] Pitch()
	{
		++count_pitches;
		
		int[] pitch = NumberToBall(number_max_potents);
		
		return pitch;
	}
	
	/**
	 * 주어진 수열에 대한 판정을 수행하고 그 결과를 반환합니다. 
	 * [0]은 strike 수,  
	 * [1]은 ball 수를 의미합니다. 
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
	 * 이번에 던진 수열과 그 결과를 바탕으로 '포텐'을 재산정합니다.
	 */
	static void Think(int[] ball, int[] result)
	{
		// 먼저 이번 결과로 인해 포텐을 상실하는 숫자들을 파악
		for ( int number = 0; number < potents.length; ++number )
		{
			// 아직 포텐이 남아 있는 숫자들에 대해 검사 수행
			if ( potents[number] != 0 )
			{
				// 해당 숫자가 만약 base라면 나왔어야 할 결과를 추론
				int[] expectedResult = Catch(ball, NumberToBall(number));
				
				// 추론된 결과와 실제 결과가 모순되는 경우 이 숫자는 포텐 상실
				// (여기에는 '방금 던진 수열' 또한 포함됨. 방금 던진 수열이 base였다면 반드시 3S가 떴어야 했을테고 그랬다면 Think()가 호출되지 않았을테니)
				if ( result[0] != expectedResult[0] || result[1] != expectedResult[1] )
					potents[number] = 0;
				
				// 추론된 결과와 실제 결과가 일치하면 우선 '포텐 있음'으로 표기해 둠
				else
					potents[number] = 1;
			}
		}
		
		
		// 아직 포텐이 남아 있는 숫자들에 대해 포텐 재산정 및 최대값 산출
		max_potents = 0;
		
		// 각 숫자가 base라는 가정 하에...
		for ( int numberOfAssumedBase = 0; numberOfAssumedBase < potents.length; ++numberOfAssumedBase )
		{
			if ( potents[numberOfAssumedBase] != 0 )
			{
				int[] base_assumed = NumberToBall(numberOfAssumedBase);
				
				// 다른 숫자들을 던져 보고...
				for ( int numberToTest = 0; numberToTest < potents.length; ++numberToTest )
				{
					if ( potents[numberToTest] != 0 && numberOfAssumedBase != numberToTest )
					{
						// 던졌을 때 기대되는 결과에 따라 가중치를 부여하여 포텐 산정
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
				
				// 합산된 포텐이 최대값을 갱신한다면 이를 기록해 둠
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
			// 귀찮으니 000부터 999까지 전부 for문을 돌리되...
			for ( int number = 0; number < 1000; ++number )
			{
				// 유효한 숫자에 대해서만 게임을 진행
				if ( IsValidNumber(number) == true )
				{
					// 시작하기 위해 추가 데이터 초기화
					ResetData();
	
					// 이번 숫자에 대한 base 생성
					base = NumberToBall(number);
					
					while ( true )
					{
						// 던지고
						pitch = Pitch();
						
						// 받고
						result = Catch(pitch, base);
						
						// 결과가 3S면 승리(루프 종료)
						if ( result[0] == 3 )
							break;
						
						// 그렇지 않으면 다음에 뭐 던질지 생각
						Think(pitch, result);
					}
				}
			}
			
			double score = (double)count_pitches / count_games;

			// 결과 출력
			System.out.println("Test#" + count_tests);
			System.out.println("Tot. pitches: " + count_pitches);
			System.out.println("Tot.   games: " + count_games);
			System.out.println("Avg. pitches: " + score);
			
			// 모든 경우의 수에 대해 실행이 끝나면 이번 시도가 최저 기록을 갱신했는지 여부 확인
			if ( count_pitches < min_pitches )
			{
				// 갱신했다면 이를 기록하고 콘솔에 즉시 출력
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
				// 동점이 나온 경우에도 가중치 배열은 갱신
				for ( int iWeight = 0; iWeight < weights.length; ++iWeight )
					weights_min_pitches[iWeight] = weights[iWeight];
			}
			
			
			System.out.println();

			
			// 다음 테스트를 위해 가중치 배열 약간 조절해 보기
			int idxToModify = rand.nextInt(weights.length);
			weights[idxToModify] += rand.nextInt(5) - 2;
			
			// 방금 바꾼 가중치가 너무 작거나 너무 크지 않도록 적당히 보정
			if ( weights[idxToModify] < 0 )
				weights[idxToModify] = 0;
			
			if ( weights[idxToModify] > 100 )
				weights[idxToModify] = 100;

			
			count_games = 0;
			count_pitches = 0;
			++count_tests;

			// 100회마다 현재까지 찾은 최저 기록과 가중치를 출력(돌려놓고 딴 짓 해도 종종 확인할 수 있도록)
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
