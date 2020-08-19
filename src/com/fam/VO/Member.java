package com.fam.VO;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fam.util.LocalDateAdapter;

public class Member {

	private final IntegerProperty memberNo;
	private final StringProperty memberId;
	private final StringProperty password;
	private final IntegerProperty familyId;
	private final StringProperty nickname;
	private final ObjectProperty<LocalDate> indate;

	/**
	 * �����͸� �ʱ�ȭ�ϴ� ������
	 */
	/*
	 * public Member(String memberId) { this.memberId = new
	 * SimpleStringProperty(memberId);
	 * 
	 * // �׽�Ʈ�� ���� �ʱ�ȭ�ϴ� ���� ������ this.memberNo = new SimpleIntegerProperty(0);
	 * this.password = new SimpleStringProperty(); this.familyId = new
	 * SimpleStringProperty(); this.nickname = new SimpleStringProperty();
	 * this.indate = new SimpleObjectProperty<LocalDate>(LocalDate.now()); }
	 */

	public Member(String nickname) {
		this.nickname = new SimpleStringProperty(nickname);

		// �׽�Ʈ�� ���� �ʱ�ȭ�ϴ� ���� ������
		this.memberId = new SimpleStringProperty();
		this.memberNo = new SimpleIntegerProperty(0);
		this.password = new SimpleStringProperty();
		this.familyId = new SimpleIntegerProperty(0);
		this.indate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	}

	// login�� �� �ʿ��� member ��ü
	public Member(String memberId, String password, int familyId) {
		this.memberId = new SimpleStringProperty(memberId);
		this.password = new SimpleStringProperty(password);
		this.familyId = new SimpleIntegerProperty(familyId);

		this.memberNo = new SimpleIntegerProperty(0);
		this.nickname = new SimpleStringProperty();
		this.indate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
	}

	// login ���� ��� ����(memberId, familyId, nickname) �޾� ���� member ��ü
	public Member(int memberNo, String memberId, int familyId, String nickname) {
		this.memberNo = new SimpleIntegerProperty(memberNo);
		this.memberId = new SimpleStringProperty(memberId);
		this.familyId = new SimpleIntegerProperty(familyId);
		this.nickname = new SimpleStringProperty(nickname);
		this.indate = new SimpleObjectProperty<LocalDate>(LocalDate.now());		
		this.password = new SimpleStringProperty();
	}


	public int getMemberNo() {
		return memberNo.get();
	}

	public void setMemberNo(int member_no) {
		this.memberNo.set(member_no);
	}

	public IntegerProperty memberNoProperty() {
		return memberNo;
	}

	public String getMemberId() {
		return memberId.get();
	}

	public void setMemberId(String memberId) {
		this.memberId.set(memberId);
	}

	public StringProperty memberIdProperty() {
		return memberId;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public int getFamilyId() {
		return familyId.get();
	}

	public void setFamilyId(int familyId) {
		this.familyId.set(familyId);
	}

	public IntegerProperty familyIdProperty() {
		return familyId;
	}

	public String getNickname() {
		return nickname.get();
	}

	public void setNickname(String nickname) {
		this.nickname.set(nickname);
	}

	public StringProperty nicknameProperty() {
		return nickname;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getIndate() {
		return indate.get();
	}

	public void setIndate(LocalDate indate) {
		this.indate.set(indate);
	}

	public ObjectProperty<LocalDate> indateProperty() {
		return indate;
	}

	@Override
	public String toString() {
		return String.format("Member [memberNo=%s, memberId=%s, password=%s, familyId=%s, nickname=%s, indate=%s]",
				memberNo, memberId, password, familyId, nickname, indate);
	}
	
	
}