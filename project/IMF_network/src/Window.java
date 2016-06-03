import java.awt.Font;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import imf.network.CharacterInfoSyncher;
import imf.network.ConnectionManager;
import loot.GameFrame;
import loot.GameFrameSettings;
import loot.InputManager;
import loot.InputManager.ButtonState;
import loot.graphics.TextBox;


/**
 * Window.java
 * Temporary Window Frame
 * 
 * @author Prev
 *
 */
public class Window extends GameFrame {
	
	private static final long serialVersionUID = 1L;
	public TextBox tb;
	
	Player player;
	Partner partner;
	InputManager im;
	
	
	int intervalHandle = 0;
	
	public Window(GameFrameSettings settings) {
		super(settings);
	}

	
	@Override
	public boolean Initialize() {
		tb = new TextBox(10, 10, 900, 900);
		tb.text = "Waiting...\n";
		tb.font = new Font("Verdana", Font.PLAIN, 20);
		
		images.LoadImage("res/ball.png", "ball1");
		images.LoadImage("res/ball2.png", "ball2");
		
		im = new InputManager(this.canvas, 4);
		im.BindKey(KeyEvent.VK_A, 0);
		im.BindKey(KeyEvent.VK_D, 1);
		im.BindKey(KeyEvent.VK_W, 2);
		im.BindKey(KeyEvent.VK_S, 3);
		
		
		player = new Player( images.GetImage("ball1") );
		partner = new Partner( images.GetImage("ball2") );
		
		return true;
	}

	public void registerCharacters() {
		CharacterInfoSyncher.registerPlayer(player);
		CharacterInfoSyncher.registerPartner(partner);
	}
	
	public void appendToTextBox(String str) {
		tb.text += str + "\n";
	}
	
	
	@Override
	public boolean Update(long timeStamp) {
		im.AcceptInputs();
		
		for (ButtonState bs : im.buttons) {
			if (bs.isPressed) {
				switch (bs.ID) {
					case 0:
						player.x -= 10;
						break;
	
					case 1:
						player.x += 10;
						break;
						
					case 2:
						player.y -= 10;
						break;
						
					case 3:
						player.y += 10;
						break;
				}
			}
		}
		
		if (++intervalHandle == 5) {
			intervalHandle = 0;
			
			if (ConnectionManager.getPartnerSessionID() != null)
				CharacterInfoSyncher.fetch();
		}
		
		return true;
	}

	@Override
	public void Draw(long timeStamp) {
		BeginDraw();
		
		tb.Draw(g);
		player.Draw(g);
		partner.Draw(g);
		
		EndDraw();
	}
	
}
