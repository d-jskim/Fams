package com.todo.server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import com.todo.VO.Data;

public class ChatServer {
	Vector<Data> buffer = null;
	ServerSocket serverSocket = null;
	Socket socket = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	//���� ���� : OutputStream ��ü ����Ʈ
	public static ArrayList<ObjectOutputStream> outputList;
	
	public void service() {
		try {
			outputList = new ArrayList<ObjectOutputStream>();
			System.out.println("���� �غ���");
			serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			System.out.println("���� �غ� �߿� IOException�� �߻��߽��ϴ�.");
		}
		while(true) {
			try {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "�� �����ϼ̽��ϴ�.\n");
								
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				
				// socket���� ���� ouputStream ����Ʈ�� ����
				outputList.add(oos); 
			
				//1.Ŭ���̾�Ʈ ���� 2. �޽��� ����  3.��ο��� �޽��� ����
				Thread t = new Thread(new ChatServerThread(buffer, ois, oos));
				t.start();
			} catch (IOException e) {
				System.err.println("service()���� thread ����");
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
