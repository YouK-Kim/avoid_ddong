package graphic;

import ddong_game.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MainFrame implements Runnable, KeyListener{
	
	JFrame mainFrame;
	BufferedImage main_img, start_img;
	JPanel startBtn, Wpanel, stickMan, ddong;
	JLayeredPane tpanel;
	MakeDDong mkddong;
	MakeStickMan mkstickMan;
	Thread killme, killStickMan, killddong;
	Random generator;
	
	private int ddong_time;
	protected static int stickman_X_location;
	private int acceleration = 4;
	protected static boolean isArived = true;
	
	public MainFrame() {
		try {
			main_img = ImageIO.read(new File(".//img//mainimg.png"));
			start_img = ImageIO.read(new File(".//img//gameStart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		generator = new Random();
		
		makeMainPanel();
		
	}
	
	//////////////////////////////////////////////////////////////
	// 메인화면 생성메서드
	/////////////////////////////////////////////////////////////
	public void makeMainPanel() {
		mainFrame = new JFrame();
		
		mainFrame.setTitle("똥 피하기");
		mainFrame.setLayout(null);
		mainFrame.setSize(350, 550);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainFrame.addKeyListener(this);
		
		makeWpanel();
		makeStartButton();
		makeTpanel();
		
		mainFrame.add(startBtn);
		mainFrame.add(tpanel);
		
		mainFrame.add(Wpanel);
		
		mainFrame.setVisible(true);
	}
	
	///////////////////////////////////////////////////////////
	// 게임용 흰패널
	///////////////////////////////////////////////////////////
	public void makeWpanel() {
		Wpanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(main_img, 0, 0, 350, 550, null);
			}
			
		};
		Wpanel.setSize(350,550);
		
	}
	
	///////////////////////////////////////////////////////////
	// 게임용 흰패널
	///////////////////////////////////////////////////////////
	public void makeTpanel() {
		tpanel = new JLayeredPane();
		
		tpanel.setOpaque(false);
		tpanel.setLayout(null);
		tpanel.setSize(350,550);
		
	}
	
	///////////////////////////////////////////////////////////
	// 시작버튼 생성
	///////////////////////////////////////////////////////////
	public void makeStartButton() {
		startBtn = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				g.drawImage(start_img, 0, 0, 150, 50, null);
			}
		};
		
		startBtn.setBounds(95, 250, 150, 50);
		startBtn.setBackground(Color.black);
		startBtn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				startBtn.setVisible(false);
				start();
			}
		});
	}
	
	public void start() {
		stickman_X_location = 160;		
		
		mkstickMan = new MakeStickMan();
		stickMan = mkstickMan.makeStickMan();
		
		tpanel.add(stickMan);
		
		(killme = new Thread(this)).start();
		//(killStickMan = new Thread(mkstickMan)).start();
		
	}
	
	public void stop() {
		if(killme != null) {
			killme.interrupt();
		}
		killme = null;
		stickMan.setVisible(false);
		tpanel.remove(stickMan);
		stickMan = null;
		startBtn.setVisible(true);
	}
	
	@Override
	public void run() {
		isArived = true;
		while(isArived) {
			ddong_time = generator.nextInt(1000);
			
			(killddong = new Thread(mkddong = new MakeDDong())).start();
			ddong = mkddong.getPanel();
			tpanel.add(ddong);
			
			try {
				Thread.sleep(ddong_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		stop();
	}
	
	////////////////////////////////////////////////////////////
	// 스틱맨 움직임 처리
	///////////////////////////////////////////////////////////
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 39) { //->
			if(acceleration < 8) { //가속도 조절
				acceleration++;
			}
			if(stickman_X_location <= 316 ) {
				stickman_X_location += acceleration;
			}
			stickMan.setBounds(stickman_X_location, 480, 30, 30);
		} else if(e.getKeyCode() == 37) { //<-
			if(acceleration < 5) {
				acceleration++;
			}
			if(stickman_X_location >= 2 ) {
				stickman_X_location -= acceleration;
			}
			stickMan.setBounds(stickman_X_location, 480, 30, 30);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 39 || e.getKeyCode() == 37) {// 키 떼었을때 가속도 0으로 조정
			acceleration = 0;
		}
	}

	
	
}
