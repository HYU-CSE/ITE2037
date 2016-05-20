package utility;

import java.util.Random;

public class PiCalculator {
	Random rand; //�����ϰ� Random�� m �����ʿ� Ŀ���� �� ���� Ctrl + Space�� import�κ� �߰�
	Double pi;
	
	private Thread thr_runner;        // Thread �� ��ü
	private boolean isStarted;        // �� ���ο� ���� Start()�� ȣ��Ǿ������� ��Ÿ��
	private boolean isStopRequested;  // �� ���ο� ���� Stop()�� ȣ��Ǿ������� ��Ÿ��

	
	public PiCalculator() {
		rand = new Random();	
		
		thr_runner = new Thread(() -> {
			int x;
			int y;
			int count_in;
			
			count_in = 0;
			
			for ( int i = 0; isStopRequested == false; i++ )
			{
				x = rand.nextInt(Constants.Width);
				y = rand.nextInt(Constants.Width);
				
				x -= Constants.Center_X;
				x *= x;
				
				y -= Constants.Center_Y;
				y *= y;
				
				if ( x + y <= Constants.Radius_Square )
					++count_in;
				
				pi = (double)count_in / (i + 1) * 4.0;
			}
		});
	}
	
	public void Start()
	{
		if ( isStarted == false )
		{
			isStarted = true;
			
			isStopRequested = false;
			thr_runner.start();
		}
	}
		
	public void Print()
	{
		System.out.println("PI: " + pi + "\n");
	}

	public void Stop()
	{
		isStopRequested = true;
	}

}
