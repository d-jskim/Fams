package com.fam.client;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.fam.VO.Data;
import com.fam.view.OverviewController;

public class ChatClient implements Runnable {

	String serverIP;
	int port;
	String nickname;
	String userCount;
	String outputMsg;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	private Thread thread;
	private boolean flag;

	private Socket socket;
	private Data d;
	OverviewController overviewController;
	Vector<String> UserName;

	public ChatClient(String serverIP, int port, String nickname, OverviewController controller) {
		this.serverIP = serverIP;
		this.port = port;
		this.nickname = nickname;
		this.overviewController = controller;
	}

	public void start() {
		System.out.println("여기 start()");
		try {
			
			socket = new Socket(serverIP, port);
			System.out.println("소켓 연결" + serverIP + " : " + port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("oos");
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("ois");

			Data d = new Data(nickname, "님이 접속하였습니다 !!!!! \n", Data.FIRST_CONNECTION);
			oos.writeObject(d);
			System.out.println("Server에 접속!!");
		} catch (IOException e) {
			e.printStackTrace();
		}


		thread = new Thread(this);
		thread.start();


	}// end start

	
	
	public void run() {
		
		

		while (!flag) {
			try {
				d = (Data) ois.readObject();
			} catch (IOException e) {
				System.err.println("run method IOException");
				try {
					oos.close();
					ois.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				flag = true;
			} catch (ClassNotFoundException e1) {
				System.err.println("Data class NotFound");
			}
			int state = d.getState();
			String name = d.getName();
			System.out.println("name : " + name);//-----------------------!
			System.out.println(d.toString());//-----------------------!

			switch (state) {
			case Data.FIRST_CONNECTION:
				System.out.println("FIRST_CONNECTION");
				//뒤로 옮기기 
				outputMsg = "[ " + name + " ]" + d.getMessage() + "\n";
				overviewController.appendText(outputMsg);
				
				UserName = d.getUserName();

				break;

			case Data.DISCONNECTION:
				UserName.remove(name);

				userCount = "현재 " + UserName.size() + "명 접속";

				outputMsg = "[ " + name + " ]" + d.getMessage() + "\n";
				overviewController.appendText(outputMsg);
				break;

			case Data.CHAT_MESSAGE:
				outputMsg = "[ " + name + " ]" + d.getMessage() + "\n";
				overviewController.appendText(outputMsg);
				break;

			default:
				System.out.println("error");

			}// switch
		} // while
		try {
			ois.close();
		} catch (IOException e) {
			System.err.println(" ChatClientThread에의 ObjectOutputStream을 Close하는 중에 IOException이 발생하였습니다.");
		} // catch
	}// run


	
	
	// 원래 private이었는데............해결하기..
	public void copyText(String message, int state) {
		try {
			oos.writeObject(new Data(nickname, message, state));
			// input.setText("");
			// input.requestFocus();
		} catch (IOException e2) {
			System.err.println("대화중 IOException이 발생하였습니다 ");
		}
	}

}
