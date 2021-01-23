package graphic;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ddong_game.*;

public class MakeDDong implements Runnable{
	
	BufferedImage ddong_img;
	JPanel ddong;
	int ddong_X_location;
	int ddong_Y_location = 30;
	
	public MakeDDong() {
		getRandomX();
		createDDong(ddong_X_location);
	}
	
	public void createDDong(int Xlocation) {
		try {
			ddong_img= ImageIO.read(new File(".//img//ddd.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ddong = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(ddong_img, 0, 0, 30, 30, null);
			}
		};
		ddong.setBounds(Xlocation, ddong_Y_location, 30, 30);
	}
	
	////////////////////////////////////////////////////////////
	// 똥 위치 랜덤생성
	////////////////////////////////////////////////////////////
	public void getRandomX() {
		Random generator = new Random();

		ddong_X_location = generator.nextInt(314) + 2;
	}

	////////////////////////////////////////////////////////////
	// 똥 내려오기
	///////////////////////////////////////////////////////////
	public void downDDong(JPanel ddong, int Xlocation) {
		while (ddong_Y_location <= 480) {
			ddong_Y_location += 5;
			ddong.setLocation(Xlocation, ddong_Y_location);
			
			accidentChk();

			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(110);
			ddong.setVisible(false);
			ddong = null; //똥 전부 내려오면 객체삭제
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public JPanel getPanel() {
		return ddong;
	}
	
	public int get_ddong_X_Location() {
		return ddong_X_location;
	}
	
	public int get_ddong_Y_Location() {
		return ddong_Y_location;
	}

	@Override
	public void run() {
		downDDong(ddong, ddong_X_location);
	}
	
	// 충돌판정 : 면대면 판정
	public void accidentChk() {
		if( (ddong_X_location <= (MainFrame.stickman_X_location+30)) && 
				( (ddong_X_location+30) >= (MainFrame.stickman_X_location)) ) {
			
			if(ddong_Y_location >=480) {
				MainFrame.isArived = false;
			}
			
		}
	}
				
}
