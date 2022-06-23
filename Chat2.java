import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener;
class Chat2 extends JFrame
{
	Socket socket;
	BufferedReader br;
	PrintWriter out;

	private JLabel heading=new JLabel("Chat2");
	private JTextArea messageArea=new JTextArea();
	private JTextField messageInput=new JTextField();
	private Font font=new Font("Roboto",Font.PLAIN,20);


	public Chat2()
	{
		try
		{
			
			System.out.println("Chat2 Sending request to  Chat1...");
			socket=new Socket("192.168.43.30",7777);
			System.out.println("Connection Done.");
			br=new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			out=new PrintWriter(socket.getOutputStream());
			CreateGUI();
			handleEvents();
			startReading();
			//startWrite();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void handleEvents()
	{
		messageInput.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("key released " +e.getKeyCode());
				if (e.getKeyCode()==10) {
					// System.out.println("You have pressed enter button");
					String contentToSend=messageInput.getText();
					messageArea.append("me : "+contentToSend+"\n");
					out.println(contentToSend);
					out.flush();
					messageInput.setText("");
					messageInput.requestFocus();
				}
			}
		
		});
	}
	private void CreateGUI(){
		this.setTitle("Chat2 Messanger[END]");
		this.setSize(700,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		heading.setFont(font);
		messageArea.setFont(font);
		messageInput.setFont(font);
		heading.setIcon(new ImageIcon("chat.png"));
		heading.setHorizontalTextPosition(SwingConstants.CENTER);
		heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		messageArea.setEditable(false);
		// messageArea.setCaretPosition(messageArea.getDocument().getLength());
		messageInput.setHorizontalAlignment(SwingConstants.CENTER);

		this.setLayout(new BorderLayout());
		this.add(heading,BorderLayout.NORTH);
		JScrollPane jScrollPane=new JScrollPane(messageArea);
		// messageArea.setCaretPosition(messageArea.getDocument().getLength());
		this.add(jScrollPane,BorderLayout.CENTER);
		this.add(messageInput,BorderLayout.SOUTH);

		this.setVisible(true);

	}
public void startReading()
	{
		Runnable r1=()->{
			System.out.println("Reader started...");
			try{
			while(true)
			{
				String msg=br.readLine();
				if (msg.equals("exit")) {
					System.out.println("Chat1 terminated the chat");
					JOptionPane.showMessageDialog(this,"Chat1 is tarminated");
					messageInput.setEnabled(false);
					socket.close();

					break;
				}
				// System.out.println("Chat1 : " +msg);
				messageArea.append("You : " +msg+"\n");
			}
		}catch(Exception e){
			System.out.println("Connection closed");
		}
		};
		new Thread(r1).start();
	}

	// public void startWrite()
	// {
	// 	Runnable r2=()->{
	// 		System.out.println("Writer started...");
	// 		try{

			
	// 		while(!socket.isClosed())
	// 		{
				
	// 				BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
	// 				String content=br1.readLine();
	// 				out.println(content);
	// 				out.flush();
	// 				if (content.equals("exit")) {
	// 				socket.close();
	// 				break;
	// 			}

	// 		}
	// 	}catch(Exception e)
	// 		{
	// 		System.out.println("Connection is closed");
	// 		}
	// 	};
	// 	new Thread(r2).start();
	// }

	public static void main(String[] args) {
		System.out.println("This is Chat2....");
		new Chat2();
	}
}