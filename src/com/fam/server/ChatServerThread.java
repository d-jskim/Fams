package com.fam.server;

import java.io.*;
import java.util.*;
import com.fam.VO.Data;

public class ChatServerThread implements Runnable {
	Vector<Data> buffer = null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	Data d = null; // �̸�, msg, �ӼӸ��� �����ϴ� ��ü
	boolean exit = false;
	String name;
	public ChatServerThread(Vector<Data> v, ObjectInputStream ois, ObjectOutputStream oos) {
		this.buffer = v;
		this.ois = ois;
		this.oos = oos;
	}
	@Override
	public void run() {
		while (!exit) {
			try {
				d = (Data) ois.readObject();
				int state = d.getState();
				switch (state) {
				case Data.DISCONNECTION:
					name = d.getName();
					for (int i = 0; i < buffer.size(); i++) {
						if (((Data) buffer.elementAt(i)).getName().equals(name)) {
							buffer.removeElementAt(i);
							break;
						}
					}
					broadCasting();
					try {
						ois.close();
						oos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case Data.CHAT_MESSAGE:
					broadCasting();
					break;
				case Data.FIRST_CONNECTION:
					// �̸��� �޾Ƽ� ���Ϳ� �߰��ϰ� ���� ������ �̸��� ��ϻ��ڸ� �����Ѵ�.
					Vector<String> userName = new Vector<String>(5, 1);
					d.setOos(oos);
					buffer.addElement(d);
					for (int i = 0; i < buffer.size(); i++) {
						userName.addElement(((Data) buffer.elementAt(i)).getName());
					}
					d.setUserName(userName);
					broadCasting();
					break;	
				default:
				}
			} catch (Exception e) {
				System.err.println("IOException�� �߻��Ͽ����ϴ�.");
				exit = true;
			}
		}
	}
	public void broadCasting() throws IOException {
		for (int i = 0; i < buffer.size(); i++) {
			((Data) buffer.elementAt(i)).getOos().writeObject(d);
		}
	}
	
	public Vector<Data> getBuffer() {
		return buffer;
	}
	public void setBuffer(Vector<Data> buffer) {
		this.buffer = buffer;
	}
	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
