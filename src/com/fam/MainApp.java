package com.fam;

import java.util.ArrayList;
import java.util.List;

import com.fam.BIZ.MemberBIZ;
import com.fam.BIZ.TaskBIZ;
import com.fam.VO.Member;
import com.fam.VO.Task;
import com.fam.view.MainLoginController;
import com.fam.view.OverviewController;
import com.fam.view.RootLayoutController;
import com.fam.view.TaskEditDialogController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {

	private Stage primaryStage;
	private Stage dialogStage;
	private BorderPane rootLayout;
	private Member member; // 현재 member
	private ObservableList<Task> taskData = FXCollections.observableArrayList();
	private ObservableList<Task> myTaskData = FXCollections.observableArrayList();
	private ObservableList<String> memberData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Family To do list App");

		initRootLayout();
		showMainLogin();
	}

	public Stage getPrimarystage() {
		return primaryStage;
	}
	
	public Stage getDialogStaty() {
		return dialogStage;
	}

	public void setMember(Member db_member) {
		this.member = db_member; // 로그인 후 DB에 있는 데이터를 다시 가져옴.SELECT * FROM member WHERE memberId = 로그인id
	}

	public Member getMember() {
		return member;
	}

	private void initRootLayout() {
		try {
			// fxml 파일에서 상위 레이아웃을 가져온다.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// 상위 레이아웃을 포함하는 scene을 보여준다.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// 컨트롤러한테 MainApp 접근 권한을 준다.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showMainLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainLogin.fxml"));
			AnchorPane mainLogin = (AnchorPane) loader.load();

			rootLayout.setCenter(mainLogin);

			MainLoginController loginController = loader.getController();
			loginController.setMainApp(this);

		} catch (Exception e) {
			System.out.println("showMainLogin() errors:");
			e.printStackTrace();
		}

	}

	public void showOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Overview.fxml"));
			AnchorPane overview = (AnchorPane) loader.load();

			rootLayout.setCenter(overview);

			OverviewController overviewController = loader.getController();
			overviewController.setMainApp(this);

		} catch (Exception e) {
			System.out.println("showOverview() errors:");
			e.printStackTrace();
		}
	}

	public boolean showTaskEditDialog(Task task) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TaskEditDialog.fxml"));
			TaskEditDialogController dialogController = new TaskEditDialogController(member, memberData);
			loader.setController(dialogController);
			AnchorPane dialog = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(dialog);
			dialogStage.setScene(scene);

			dialogController.setDialogStage(dialogStage);
			dialogController.setTask(task);

			dialogStage.showAndWait();

			return dialogController.isOkClicked();

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public void setTaskData() {

		List<Task> all = new ArrayList<>();
		all = new TaskBIZ().task_selectAll(member.getFamilyId());

		for (int i = 0; i < all.size(); i++) {
			taskData.add(all.get(i));
		}
	}

	public ObservableList<Task> getTaskData() {
		setTaskData();
		return taskData;
	}
	
	public void setMyTaskData() {
		List<Task> all = new ArrayList<>();
		all = new TaskBIZ().task_selectMyList(member.getFamilyId(), member.getNickname());

		for (int i = 0; i < all.size(); i++) {
			myTaskData.add(all.get(i));
		}
	}
	
	public ObservableList<Task> getMyTaskData() {
		setMyTaskData();		
		return myTaskData;
	}

	public void setMemberData() {

		ArrayList<String> mList = new ArrayList<>();
		mList = new MemberBIZ().member_memberList(member.getFamilyId());

		for (int i = 0; i < mList.size(); i++) {
			memberData.add(mList.get(i));
		}
	}

	public ObservableList<String> getMemberData() {

		setMemberData();
		return memberData;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
