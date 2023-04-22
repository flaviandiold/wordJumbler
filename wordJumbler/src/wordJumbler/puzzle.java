package wordJumbler;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;

public class puzzle {

	private JFrame frame;
	
	public ArrayList<ArrayList<String>> levels = new ArrayList<ArrayList<String>>();
	
	public ArrayList<String> level1 = new ArrayList<String>();
	public ArrayList<String> level2 = new ArrayList<String>();
	public ArrayList<String> level3 = new ArrayList<String>();
	public ArrayList<String> level4 = new ArrayList<String>();
	public ArrayList<String> level5 = new ArrayList<String>();
	public ArrayList<String> level6 = new ArrayList<String>();
	public ArrayList<String> level7 = new ArrayList<String>();
	public ArrayList<String> level8 = new ArrayList<String>();

	int count = 0;
	int curLevel = 0;
	int curScore = 0;
	int skipCount = 3;
	int sec = 120;
	Timer t;
	String original = "";
	JButton enterButton;
	JTextField userInput;
	JLabel currentLabel;
	JButton skipButton;
	JButton startButton;
	JLabel scoreLabel;
	private JLabel timeLabel;
	private JButton resetButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					puzzle window = new puzzle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public puzzle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		

		currentLabel = new JLabel("");
		currentLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		userInput = new JTextField();
		userInput.setEnabled(false);
		userInput.setColumns(10);
		
		enterButton = new JButton("Enter");
		enterButton.setEnabled(false);	
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userInput.getText().contentEquals(original)) {
					updateScore();
					playGame();
					userInput.setText("");
					counter();
					userInput.requestFocusInWindow();
			}
		}
		});
		skipButton = new JButton("Skip "+skipCount);
		skipButton.setEnabled(false);
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(skipCount!=0) {
				playGame();
				userInput.setText("");
				counter();
				userInput.requestFocusInWindow();
				skipCount--;
				skipButton.setText("Skip "+(skipCount));
				frame.getRootPane().setDefaultButton(enterButton);
				if(skipCount==0) {
					skipButton.setEnabled(false);
				}
				}
				}
				
		});
		
		scoreLabel = new JLabel("Score: "+ curScore);
		
		timeLabel = new JLabel("Time Left:"+ sec);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userInput.setEnabled(true);
				enterButton.setEnabled(true);
				startButton.setEnabled(false);
				timer();
				start();
			}
		});
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(timeLabel, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
									.addComponent(scoreLabel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(resetButton)
									.addGap(268)
									.addComponent(skipButton))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(189)
							.addComponent(currentLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(165)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(userInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(10)
											.addComponent(startButton))
										.addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(scoreLabel)
						.addComponent(timeLabel))
					.addGap(59)
					.addComponent(currentLabel)
					.addGap(18)
					.addComponent(userInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(enterButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(startButton)
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(skipButton)
						.addComponent(resetButton))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		levels.add(level1);
		levels.add(level2);
		levels.add(level3);
		levels.add(level4);
		levels.add(level5);
		levels.add(level6);
		levels.add(level7);
		levels.add(level8);
		
		for(int i = 0; i < 8; i++) {
			readText("level" + (i+1) + ".txt", levels.get(i));
		}
}
	
	public void start() {
		
		playGame();
		
	}
	
	public void readText(String path, ArrayList<String> currentArray) {
		try {
			BufferedReader read = new BufferedReader(new FileReader(path));
			
			String word = read.readLine();
			while(word != null) {
				currentArray.add(word);
				word = read.readLine();
			}
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void counter() {
		count++;
		if(count%9 == 0) {
			count = 0;
			curLevel++;
		}
	}
	
	public String selectRandomWord() {
		ArrayList<String> input = levels.get(curLevel);
		Random r = new Random(); 
		int random = r.nextInt(input.size());
		
		String output = input.get(random);
		input.remove(random);
		return output;
	}
	
	public String jumble(String original) {
		int len = original.length();
		String jumble = original;
		int jumbleCount = 10;
		while(jumbleCount > 0) {
			jumbleCount--;
			int index1 = ThreadLocalRandom.current().nextInt(0,len);
			int index2 = ThreadLocalRandom.current().nextInt(0,len);
			jumble = swap(original,index1,index2);
		}
		return jumble;
	}

	public String swap(String word, int ind1, int ind2) {
		char[] charWord = word.toCharArray();
		char temp = charWord[ind1];
		charWord[ind1] = charWord[ind2];
		charWord[ind2] = temp;
		
		String org = new String(charWord);
		
		return org;
		
	}
	
	public void playGame() {
		original = selectRandomWord();
		String jumbled = jumble(original);
		skipButton.setEnabled(true);
		frame.getRootPane().setDefaultButton(enterButton);
		userInput.requestFocusInWindow();



		while(jumbled.equals(original)) {
			jumbled = jumble(original);
		}
		currentLabel.setText(jumbled);
	}
	public void updateScore() {
		curScore++;
		scoreLabel.setText("Score: "+ curScore);
	}
	public void timer () {
		t = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sec == 0) {
					JOptionPane.showMessageDialog(null, "Game Over!" + "\n Your Final Score: "+ curScore);
					reset();
				}
				sec--;
				timeLabel.setText("Time: "+ sec);
			}
		});
			t.start();
	}
	
	public void reset() {
		for(int i = 0; i < levels.size(); i++) {
			levels.get(i);
		}
		for(int i = 0; i < 8; i++) {
			readText("level" + (i+1) + ".txt", levels.get(i));
		}
	    count = 0;
		curLevel = 0;
		curScore = 0;
		original = "";
		skipCount = 3;
		t.stop();
		sec = 120;
		skipButton.setText("Skip " + skipCount);
		timeLabel.setText("Time Left:"+ sec);
		scoreLabel.setText("Score:"+curScore);
		
		startButton.setEnabled(true);
		userInput.setEnabled(false);
		enterButton.setEnabled(false);
		skipButton.setEnabled(false);
		
		frame.getRootPane().setDefaultButton(startButton);
		userInput.requestFocusInWindow();
		
	}
}
