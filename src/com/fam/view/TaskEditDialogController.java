package com.fam.view;

import javax.xml.transform.Source;

import com.fam.MainApp;
import com.fam.VO.Member;
import com.fam.VO.Task;
import com.fam.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskEditDialogController {

	@FXML private TextField categoryField;
	@FXML private TextField taskField;
	@FXML private ComboBox<String> statusBox;
	@FXML private ComboBox<String> memberBox;
	@FXML private ComboBox<String> substituteBox;
	@FXML private TextField substituteField;
	@FXML private DatePicker startDateField;
	@FXML private DatePicker endDateField;
	@FXML private TextField memoField;
	@FXML private Label likeLabel;

	private ObservableList<String> statusList = FXCollections.observableArrayList("Not started", "In progress", "Completed");
	private ObservableList<String> memberData = FXCollections.observableArrayList();

	private Member member;
	private Stage dialogStage;
	private Task task;
	private boolean okClicked = false;

	private MainApp mainApp;

	public TaskEditDialogController(Member member, ObservableList<String> memberData) {
		this.member = member;
		this.memberData = memberData;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void initialize() {
		statusBox.setItems(statusList);
		memberBox.setItems(memberData);
		substituteBox.setItems(memberData);
	}

	public void setTask(Task task) {
		this.task = task;

		categoryField.setText(task.getCategory());
		taskField.setText(task.getTask());
		statusBox.setPromptText(task.getStatus());
		memberBox.setPromptText(task.getMember());
		substituteBox.setPromptText(task.getMember());
		startDateField.setPromptText(DateUtil.format(task.getStartDate()));
		endDateField.setPromptText(DateUtil.format(task.getEndDate()));
		memoField.setText(task.getMemo());
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleOk() {

		
		if (isInputValid()) {

			task.setCategory(categoryField.getText());
			task.setTask(taskField.getText());
			task.setStatus(statusBox.getValue().toString());
			task.setFamilyId(member.getFamilyId());
			task.setMember(memberBox.getValue().toString());
			task.setSubstitute(substituteField.getText());
			task.setStartDateString(startDateField.getValue().toString());			
			task.setEndDateString(endDateField.getValue().toString());
			task.setMemo(memoField.getText());

			okClicked = true;
			
			dialogStage.close();

			System.out.println(task.toString());
		}
	}

	/**
	 * 다이얼로그 창에 입력한 값이 유효한지 확인하는 메서드
	 * 
	 * @return
	 */

	private boolean isInputValid() {
		String errorMessage = "";

		if (categoryField.getText() == null || categoryField.getText().length() == 0) {
			errorMessage += "No valid category name";
		}
		if (taskField.getText() == null || taskField.getText().length() == 0) {
			errorMessage += "No valid task name";
		}
		if (memberBox.getValue().toString() == null || memberBox.getValue().toString().length() == 0) {
			errorMessage += "No valid member name";
		}

		if (errorMessage.length() == 0) {

			return true;

		} else {
			// 오류 메시지 창
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}

	}

}
