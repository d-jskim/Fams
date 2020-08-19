package com.fam.server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import com.fam.VO.Data;

public class ChatServer {
	Vector<Data> buffer = null;
	ServerSocket serverSocket = null;
	Socket socket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	//파일 쓰기 : OutputStream 객체 리스트
	public static ArrayList<ObjectOutputStream> outputList;
	
	public void service() {
		try {
			outputList = new ArrayList<ObjectOutputStream>();
			System.out.println("접속 준비중");
			serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			System.out.println("서비스 준비 중에 IOException이 발생했습니다.");
		}
		while(true) {
			try {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "가 접속하셨습니다.\n");
								
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				
				// socket에서 얻은 ouputStream 리스트에 저장
				outputList.add(oos); 
			
				//1.클라이언트 관리 2. 메시지 관리  3.모두에게 메시지 리턴
				Thread t = new Thread(new ChatServerThread(buffer, ois, oos));
				t.start();
			} catch (IOException e) {
				System.err.println("service()에서 thread 실행");
			}
		}//while
	}//service
	
	public static void main(String[] args) {
		System.out.println("Start Server Service...");
		ChatServer cs = new ChatServer();
		cs.buffer = new Vector<Data>(5, 1);
		cs.service();
	}
}
