package com.fam.VO;


 //util.Date는 오라클과 연동 불가

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import com.fam.util.LocalDateAdapter;
import com.fam.util.DateUtil;

public class Task {
	
	private IntegerProperty taskNo;
	private StringProperty category;
	private StringProperty task;
	private StringProperty status;
	private IntegerProperty familyId;
	private StringProperty member;
	private StringProperty substitute;
	private ObjectProperty<LocalDate> startDate;
	private ObjectProperty<LocalDate> endDate;
	private StringProperty memo;
	private IntegerProperty likeNo;
	
	/**
	 * [NEW]버튼을 클릭했을 때 기본생성자로 생성한 Task객체에 새로운 값 넣어야 하기 때문에 필요한 기본생성자(args X & default value 포함)
	 */
	public Task() {
		this.category = new SimpleStringProperty(""); 
		this.task = new SimpleStringProperty("");
		this.member = new SimpleStringProperty("");
	
		//default values
		this.taskNo = new SimpleIntegerProperty(0);
		this.status = new SimpleStringProperty("not started");
		this.substitute = new SimpleStringProperty("none");
		this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.memo = new SimpleStringProperty("none");
		this.likeNo = new SimpleIntegerProperty(0);

	}
	/**
	 * 필수 내용(category, task, member)을 입력받고 나서 사용하는 매개변수 생성자
	 * @param category
	 * @param task
	 * @param member
	 */
	public Task(String category, String task, String member) {
		this.category = new SimpleStringProperty(category);
		this.task = new SimpleStringProperty(task);
		this.member = new SimpleStringProperty(member);
	
		//default values
		this.taskNo = new SimpleIntegerProperty(0);
		this.status = new SimpleStringProperty("");
		this.substitute = new SimpleStringProperty("");
		this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.memo = new SimpleStringProperty("");
		this.likeNo = new SimpleIntegerProperty(0);

	}
	
	
	/**
	 * SELECT ALL했을 때 oracle cursor가 받아 온 튜플 하나를 매개변수 생성자를 통해 생성한 후 all(java.util.List)에 add()함.
	 * DB로부터 받은 데이터 타입을 Task(VO)에서 선언한 형으로 변환한 후 생성.
	 * int -> IntegerProperty / String -> StringProperty / String(date[DB]) -> ObjectProperty<LocalDate>
	 */
	//taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	public Task(int taskNo, String category, String task, String status, int familyId, String member, 
			String substitute, String sDate, String eDate, String memo, int likeNo){
		
		this.taskNo = new SimpleIntegerProperty(taskNo);
		this.category = new SimpleStringProperty(category);
		this.task = new SimpleStringProperty(task);
		this.status = new SimpleStringProperty(status);
		this.familyId = new SimpleIntegerProperty(familyId);
		this.member = new SimpleStringProperty(member);
		this.substitute = new SimpleStringProperty(substitute);	
		this.startDate =  new SimpleObjectProperty<LocalDate>(LocalDate.of(intDate(sDate)[0], intDate(sDate)[1], intDate(sDate)[2]));
		this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(intDate(eDate)[0], intDate(eDate)[1], intDate(eDate)[2]));
		this.memo = new SimpleStringProperty(memo);
		this.likeNo = new SimpleIntegerProperty(likeNo);
		this.memo = new SimpleStringProperty("");
		this.likeNo = new SimpleIntegerProperty(0);
	}

	
	
	//New Task할 때 새로운 객체 생성하면서 familyId 넣어야 함.
	public Task(int familyId) {
		this.familyId = new SimpleIntegerProperty(familyId);
		//default 초기화
		this.taskNo = new SimpleIntegerProperty(0);
		this.category = new SimpleStringProperty("");
		this.task = new SimpleStringProperty("");
		this.status = new SimpleStringProperty("");
		this.member = new SimpleStringProperty("");
		this.substitute = new SimpleStringProperty("");
		this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.memo = new SimpleStringProperty("");
		this.likeNo = new SimpleIntegerProperty(0);
	}
	public int getTaskNo() {
		return taskNo.get();
	}

	public void setTaskNo(int taskNo) {
		this.taskNo.set(taskNo);
	}
	
	public IntegerProperty taskNoProperty() {
		return taskNo;
	}

	public String getCategory() {
		return category.get();	
	}

	public void setCategory(String category) {
		this.category.set(category);
	}
	
	public StringProperty categoryProperty() {
		return category;
	}

	public String getTask() {
		return task.get();
	}

	public void setTask(String task) {
		this.task.set(task);
	}
	
	public StringProperty taskProperty() {
		return task;
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public StringProperty statusProperty() {
		return status;
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
	
	public String getMember() {
		return member.get();
	}

	public void setMember(String member) {
		this.member.set(member);
	}
	
	public StringProperty memberProperty() {
		return member;
	}
	
	public String getSubstitute() {
		return substitute.get();
	}

	public void setSubstitute(String substitute) {
		this.substitute.set(substitute);
	}
	
	public StringProperty substituteProperty() {
		return substitute;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate.get();
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate.set(startDate);
	}

	
	public void setStartDateString(String startDate){
		this.startDate.set(DateUtil.parse(startDate));
	}
	
	public String getStartDateString() {
		return DateUtil.format(getStartDate());
	}
	
	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getEndDate() {
		return endDate.get();
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate.set(endDate);
	}
	
	public void setEndDateString(String endDate){
		this.endDate.set(com.fam.util.DateUtil.parse(endDate));
	}
	
	public String getEndDateString() {
		return DateUtil.format(getEndDate());
	}
	
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}

	public String getMemo() {
		return memo.get();
	}

	public void setMemo(String memo) {
		this.memo.set(memo);
	}	
	
	public StringProperty memoProperty() {
		return memo;
	}
	
	public int getLikeNo() {
		return likeNo.get();
	}

	public void setLikeNO(int like) {
		this.likeNo.set(like);
	}
	
	public IntegerProperty likeNoProperty() {
		return likeNo;
	}
	
	/**
	 * ObjectProperty<LocalDate>로 형 변환 시 String으로 받아 온 date(2020-06-20)의 year, month, day 추출
	 * @param date
	 * @return
	 */
	public int[] intDate(String date) {
		//String startDate -> 2020-06-09 15:15:30 (예시)
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt((date.substring(5, 7)).replace("0",""));
		int day = Integer.parseInt(date.substring(8, 10));	
		int[] intDate = {year, month, day};
		
		return intDate;
	}
	@Override
	public String toString() {
		return String.format(
				"Task [taskNo=%s, category=%s, task=%s, status=%s, familyId=%s, member=%s, substitute=%s, startDate=%s, endDate=%s, memo=%s, likeNo=%s]",
				taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo);
	}

	/**
	 * task 객체 확인 용 출력 메서드
	 */

	
	
	
	
}
