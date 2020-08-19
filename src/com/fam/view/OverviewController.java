package com.fam.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.fam.MainApp;
import com.fam.BIZ.TaskBIZ;
import com.fam.VO.Member;
import com.fam.VO.Task;
import com.fam.util.DateUtil;
import com.fam.VO.Data;
import com.fam.client.ChatClient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class OverviewController {

	private MainApp mainApp;
	private Stage dialogStage;

	private Member member; // ���� member ���� from DB

	/* ���̺�� */
	private ObservableList<Task> taskData = FXCollections.observableArrayList();
	private ObservableList<Task> myTaskData = FXCollections.observableArrayList();

	@FXML private TableView<Task> taskTable;
	@FXML private TableColumn<Task, String> categoryColumn;
	@FXML private TableColumn<Task, String> taskColumn;
	@FXML private TableColumn<Task, String> statusColumn;
	@FXML private TableColumn<Task, String> memberColumn;
	@FXML private TableColumn<Task, String> substituteColumn;
	@FXML private TableColumn<Task, LocalDate> startDateColumn;
	@FXML private TableColumn<Task, LocalDate> endDateColumn;
	@FXML private TableColumn<Task, Integer> likeNoColumn;
	
	@FXML private Label categoryLabel;
	@FXML private Label taskLabel;
	@FXML private Label statusLabel;
	@FXML private Label memberLabel;
	@FXML private Label substituteLabel;
	@FXML private Label startDateLabel;
	@FXML private Label endDateLabel;
	@FXML private Label memoLabel;
	@FXML private Label likeNoLabel;
	
	@FXML private Button myListBtn;
	@FXML private Button famListBtn;
	
	private boolean okClicked = false;
	
	
	//�� ��Ʈ�� x��
	@FXML private BarChart<String, Integer> barChart1;
	@FXML private BarChart<String, Integer> barChart2;
	@FXML private BarChart<String, Integer> barChart3;
	@FXML private StackedBarChart<String, Integer> barChart4;
	@FXML private LineChart<String, Integer> barChart5;
	@FXML private CategoryAxis xAxis;
	@FXML private CategoryAxis xAxis2;
	@FXML private CategoryAxis xAxis3;
	@FXML private CategoryAxis xAxis4;
	@FXML private CategoryAxis xAxis5;
	@FXML private NumberAxis yAxis5;
		
		//�� ��Ʈ�� �� ���� �������� �̸�
		private ObservableList<String> memberNames = FXCollections.observableArrayList();
		private ObservableList<String> newMemberNames = FXCollections.observableArrayList();
		//���� �̸�(not started, in progress, completed) 
		private ObservableList<String> statusNames = FXCollections.observableArrayList();
		//���κ� ������ ����Ʈ
		private ObservableList<String> taskNames = FXCollections.observableArrayList();
		//��ü ���� ī�װ� ����Ʈ
		private ObservableList<String> allTaskList = FXCollections.observableArrayList();
		private ObservableList<String> newTaskList = FXCollections.observableArrayList();
		//��¥ ����Ʈ
		private ObservableList<String> dateSeries = FXCollections.observableArrayList();
		@FXML private ChoiceBox<String> nameBox;
		@FXML private ChoiceBox<String> nameBox1;
		//������ ���� �� ��� ��Ÿ���� ��
		@FXML private Label statNameLabel;
		//������ ���� ��(���� �� ��� �����ֱ� ���ؼ�)
		@FXML private ChoiceBox<String> nameBox2;
	
	
	//EditTaskDialog�� member combo box
	private ObservableList<String> memberData = FXCollections.observableArrayList();
	
	/* ä�� */
	private String serverIP = "127.0.0.1";
    private int port = 5555;
    private ChatClient client;
    
	@FXML private TextArea inputText;
	@FXML private TextArea outputText;
	@FXML private Label userCountLabel;


	private ObservableList<String> userNameList = FXCollections.observableArrayList();


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		taskTable.setItems(mainApp.getTaskData());
		memberData = mainApp.getMemberData(); //ä���� memberList & taskDialog�� ��� �޺��ڽ�
		this.member = mainApp.getMember();
		
		client = new ChatClient(serverIP, port, member.getNickname(), this);
		client.start();

	}	

	// dialog
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void initialize() {
		// ���̺� �ʱ�ȭ
		categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
		taskColumn.setCellValueFactory(cellData -> cellData.getValue().taskProperty());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
		memberColumn.setCellValueFactory(cellData -> cellData.getValue().memberProperty());
		substituteColumn.setCellValueFactory(cellData -> cellData.getValue().substituteProperty());
		startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		likeNoColumn.setCellValueFactory(cellData -> cellData.getValue().likeNoProperty().asObject());

		showTaskDetails(null);

		taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTaskDetails(newValue));
	}

	@FXML // FamToDoList Btn �̺�Ʈ
	private void resetTaskTable() {
		taskTable.getItems().clear();
		taskTable.setItems(mainApp.getTaskData());
	}
	@FXML//MyToDoList Btn �̺�Ʈ
	private void showMyTaskTable() {

		taskTable.getItems().clear();
		taskTable.setItems(mainApp.getMyTaskData());
	}

	private void showTaskDetails(Task task) {
		if (task != null) {
			// task ��ü�� label�� ������ ä���.
			categoryLabel.setText(task.getCategory());
			taskLabel.setText(task.getTask());
			statusLabel.setText(task.getStatus());
			memberLabel.setText(task.getMember());
			substituteLabel.setText(task.getSubstitute());
			startDateLabel.setText(DateUtil.format(task.getStartDate()));
			endDateLabel.setText(DateUtil.format(task.getEndDate()));
			memoLabel.setText(task.getMemo());
			likeNoLabel.setText(Integer.toString(task.getLikeNo()));

		} else {
			// task�� null�̸� ��� �ؽ�Ʈ�� �����.
			categoryLabel.setText("");
			taskLabel.setText("");
			statusLabel.setText("");
			memberLabel.setText("");
			substituteLabel.setText("");
			startDateLabel.setText("");
			endDateLabel.setText("");
			memoLabel.setText("");
			likeNoLabel.setText("");
		}
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * [NEW]��ư �̺�Ʈ -> ���ο� Task ��ü DB�� �ֱ�
	 */
	@FXML
	private void handleNewTask() {
		
		int res = 0;

		Task tempTask = new Task(member.getFamilyId());
		boolean okClicked = mainApp.showTaskEditDialog(tempTask);
	
		if (okClicked) {
			res = new TaskBIZ().task_insertAll(tempTask);
			resetTaskTable();			
		}
	}
	
	/**
	 * [EDIT]��ư �̺�Ʈ -> ������Ʈ�� Task ��ü DB�� �ֱ�
	 */
	@FXML
	private void handleEditTask() {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();


		if (selectedTask != null) {
			boolean okClicked = mainApp.showTaskEditDialog(selectedTask);
			if (okClicked) {
				int res = new TaskBIZ().task_update(selectedTask);
				showTaskDetails(selectedTask);
				resetTaskTable();
			}
		} else {
			// �ƹ� �͵� �������� �ʾ��� ���
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimarystage());
			alert.setTitle("No selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * [DELETE]��ư �̺�Ʈ -> ���̺�信�� ���õ� Task ��ü DB���� �����ϱ�
	 */
	@FXML
	private void handleDeleteTask() {
		// ���̺��(����Ʈ)���� ������ ��ü�� �ε��� ��ȣ
		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			Task delTask = new Task();
			delTask = taskTable.getItems().get(selectedIndex);
			int delTaskNo = delTask.taskNoProperty().getValue().intValue();
			new TaskBIZ().task_delete(delTaskNo);
			resetTaskTable();

		} else {
			// ���� �޽����� �����ش�.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimarystage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Task Selected");
			alert.setContentText("Please select a task in the table");

			alert.showAndWait();
		}
	}
	
	// -------ä��---------------------------------------------

		// [SEND] ��ư ������ �� �۵�
		// TODO:inputText���� ENTER ������ ���� send()�� ����..........
		@FXML
		private void send() {
			String message = inputText.getText();
			client.copyText(message, Data.CHAT_MESSAGE);
			inputText.setText("");
			inputText.requestFocus();
		}
		
		@FXML
		public void inputTextKeyEvent(KeyEvent event) {
			if (event.getCode().equals(KeyCode.ENTER)) 
				send();
		}

		public void appendText(String clientMsg) {
			System.out.println("msg: " + clientMsg);
			outputText.appendText(clientMsg);
		}
		
		//�м� ���� �޼ҵ��-----------------------------------------------------------------------
		//��ü �м� ��Ʈ �ε��ϱ�
		public void showChart() {
			setStatusData();
			setStackedBarChart();
			setBarChart3();
			setBarChart5();
			
		}
			//��ü ������ ����Ʈ
				public void setCountData() {
					xAxis.getCategories().clear();
					barChart1.getData().clear();
					Map<String, Integer> countList = new TaskBIZ().count_task(member.getFamilyId());
					List<String> countKey = countList.keySet().stream().collect(Collectors.toList());
					memberNames.addAll(countKey);
			        xAxis.setCategories(memberNames);
					
					XYChart.Series<String, Integer> series = new XYChart.Series<>();
			        Set<Entry<String, Integer>> entries = countList.entrySet();
			        for (Map.Entry<String, Integer> entry : entries) {
			        	series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
			        }
			        barChart1.getData().add(series);
			    }
				
				
				public void setStatusData() {
					xAxis2.getCategories().clear();
					barChart2.getData().clear();
					Map<String, Integer> statusList = new TaskBIZ().count_status();
			        List<String> statusKey = statusList.keySet().stream().collect(Collectors.toList());
			        statusNames.addAll(statusKey);
			        xAxis2.setCategories(statusNames);
			        
					XYChart.Series<String, Integer> countSeries = new XYChart.Series<>();
			        Set<Entry<String, Integer>> countEntries = statusList.entrySet();
			        for (Map.Entry<String, Integer> countEntry : countEntries) {
			        	countSeries.getData().add(new XYChart.Data<>(countEntry.getKey(), countEntry.getValue()));
			        }
			        barChart2.getData().add(countSeries);
			    }
				
				//����Ʈ3 �����
				public void setBarChart3() {
					nameBox.getItems().clear();
					Map<String, Integer> countList = new TaskBIZ().count_task(member.getFamilyId());
					List<String> countKey = countList.keySet().stream().collect(Collectors.toList());
					memberNames.addAll(countKey);
					nameBox.setItems(memberNames);
					//private ObservableList<Series<String, Integer>> taskData = FXCollections.observableArrayList();
					ChangeListener<String> changeListener = new ChangeListener<String>() { 
						
						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
							 if (newValue != null) {
								xAxis3.getCategories().clear();
								barChart3.getData().clear(); //��Ʈ �ʱ�ȭ
								Map<String, Integer> taskCountList = new TaskBIZ().individual_task(member.getFamilyId(), newValue);
								List<String> TaskKey = taskCountList.keySet().stream().collect(Collectors.toList()); //���� ����Ʈ
								taskNames.addAll(TaskKey);
								xAxis3.setCategories(taskNames);
								
								XYChart.Series<String, Integer> taskSeries = new XYChart.Series<>();
							    Set<Entry<String, Integer>> taskEntries = taskCountList.entrySet();
							    for (Map.Entry<String, Integer> taskEntry : taskEntries) {
							      	taskSeries.getData().add(new XYChart.Data<>(taskEntry.getKey(), taskEntry.getValue()));
							    }
							    barChart3.getData().addAll(taskSeries);
				                }
						}
			        } ;
			        nameBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
			    }
				
				//����Ʈ4
				public void setStackedBarChart() {
					barChart4.getData().clear();
					 
					xAxis4.setCategories(memberNames);
					List<String> taskList = null;
					taskList = new TaskBIZ().select_all_task(member.getFamilyId());
					allTaskList.clear();
			        allTaskList.addAll(taskList);
					for(String res: allTaskList) {
						XYChart.Series<String, Integer> stackedSeries = new XYChart.Series<>(); 
						stackedSeries.setName(res);
						Map<String, Integer> stackedTaskCountList = new TaskBIZ().individual_task_reverse(res);
						Set<Entry<String, Integer>> stackedTaskcountEntries = stackedTaskCountList.entrySet();
				        for (Map.Entry<String, Integer> stackedTaskcountEntry : stackedTaskcountEntries) {
				        	stackedSeries.getData().add(new XYChart.Data<>(stackedTaskcountEntry.getKey(), stackedTaskcountEntry.getValue()));
				        }
						barChart4.getData().add(stackedSeries);
					};

				}
				
				
				//�ð迭��Ʈ
				public void setBarChart5() {
					nameBox1.getItems().clear();
					Map<String, Integer> countList2 = new TaskBIZ().count_task(member.getFamilyId());
					List<String> countKey2 = countList2.keySet().stream().collect(Collectors.toList());
					newMemberNames.addAll(countKey2);
					nameBox1.setItems(newMemberNames);
					ChangeListener<String> changeListener1 = new ChangeListener<String>() { 
						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
							 if (newValue != null) {
								xAxis5.getCategories().clear();
								barChart5.getData().clear(); //��Ʈ �ʱ�ȭ
								
								Map<String, Integer> timeSeriesList = new TaskBIZ().time_series_task(newValue);
								List<String> TimeKey = timeSeriesList.keySet().stream().collect(Collectors.toList()); //���� ����Ʈ
								dateSeries.addAll(TimeKey);
								xAxis5.setCategories(dateSeries);
								
								XYChart.Series<String, Integer> timeSeries = new XYChart.Series<>();
								Set<Entry<String, Integer>> timeSeriesEntries = timeSeriesList.entrySet();
								 for (Entry<String, Integer> timeSeriesEntry : timeSeriesEntries) {				
									 timeSeries.getData().add(new XYChart.Data<>(timeSeriesEntry.getKey(), timeSeriesEntry.getValue()));
								 }
							    barChart5.getData().add(timeSeries);
							 }
						}
			        } ;
			        nameBox1.getSelectionModel().selectedItemProperty().addListener(changeListener1);
						 
		         }//end setBarChart5
				
			  
//				//StatNameLabel
//				public void setStatNameLabel() {
//					nameBox2.getItems().clear();
//					List<String> taskList2 = null;
//					taskList2 = new TaskBIZ().select_all_task(member.getFamilyId());
//					
//					newTaskList.addAll(taskList2);
//					nameBox2.setItems(newTaskList);
//					
//				}
					
				
				

}
