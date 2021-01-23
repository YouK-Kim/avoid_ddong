package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ddong_game.*;

public class MakeStickMan implements Runnable{
	
	BufferedImage stickman_img;
	JPanel stickman;
	protected static int stickman_X_location;
	
	public MakeStickMan() {
		
	}
	
	public JPanel makeStickMan() {
		try {
			stickman_img = ImageIO.read(new File(".//img//man.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stickman = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(stickman_img, 0, 0, 32, 32, null);
			}
		};
		stickman_X_location = 160;
		stickman.setBounds(stickman_X_location, 480, 30, 30);
		stickman.setBackground(Color.black);
		
		return stickman;
	}

	@Override
	public void run() {
		
	}

}
