package com.fam.VO;

import java.io.*;
import java.util.*;

public class Data implements Serializable{
	private String name; // Ŭ���̾�Ʈ �̸�
	private String receiver; // �Ӹ��� ���� Ŭ���̾�Ʈ �̸�
	private String message; // Ŭ���̾�Ʈ�� �޽���
	private int state; // Ŭ���̾�Ʈ�� ���Ӱ� ������¸� �����ϴ� ����
	private transient ObjectOutputStream oos; // �ڷ����� ��ü
	private Vector<String> userName; // �����ڵ��� �̸�(ó�� �����Ҷ� ����)
	//Ŭ���̾�Ʈ�� ���¸� ��Ÿ���� ���
	public static final int FIRST_CONNECTION = 0;
	public static final int DISCONNECTION = -1;
	public static final int CHAT_MESSAGE = 1;
	public static final int CHAT_WHISPER = 2;
	
	public Data() {
		super();
	}
	public Data(String name, String message, int state, ObjectOutputStream oos) {
		super();
		this.name = name;
		this.message = message;
		this.state = state;
		this.oos = oos;
	}
	
	public Data(String name, String message, int state) {
		this.name = name;
		this.message = message;
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public Vector<String> getUserName() {
		return userName;
	}
	public void setUserName(Vector<String> userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return String.format("Data [name=%s, receiver=%s, message=%s, state=%s, userName=%s]", name, receiver, message,
				state, userName);
	}
	
	
}
