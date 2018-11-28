package ydbs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class D02PersonalDaily extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"날짜", "장소", "운동", "날씨", "기록", "출근", "퇴근", "근무",  "id1", "date2", "date3"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);
	private String queryType = "dateAll";
	private String accountBookYear = null;
	private String accountBookMonth = null;

	public D02PersonalDaily() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		//오늘 날짜 구하기 (초기 실행시 오늘 날자를 기준으로 DB를 select 하기 위함)
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatDate.format(date);
		String[] todayArray = today.split("-");
		
		String yearArray[] = {"연", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013"};
		JComboBox<String> comboYear = new JComboBox<String>(yearArray);
		comboYear.setSelectedItem(todayArray[0]);
		comboYear.setBounds(12, 10, 105, 21);
		add(comboYear);
		
		String monthArray[] = {"월", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		JComboBox<String> comboMonth = new JComboBox<String>(monthArray);
		comboMonth.setSelectedItem(todayArray[1]);
		comboMonth.setBounds(129, 10, 70, 21);
		add(comboMonth);
		
		String placeArray[] = {"주문진", "사천"};
		JComboBox<String> comboPlace = new JComboBox<String>(placeArray);
		comboPlace.setSelectedItem(placeArray[0]);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(210, 9, 88, 23);
		add(btnSearch);
		
		JButton btnAddRow = new JButton("행 추가");
		btnAddRow.setBounds(310, 9, 88, 23);
		add(btnAddRow);
		
		JButton btnSave = new JButton("저장");
		btnSave.setBounds(410, 9, 88, 23);
		add(btnSave);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
		table.getColumn("날짜").setPreferredWidth(120);
		table.getColumn("장소").setPreferredWidth(60);
		table.getColumn("운동").setPreferredWidth(50);
		table.getColumn("날씨").setPreferredWidth(200);
		table.getColumn("기록").setPreferredWidth(900);
		table.getColumn("출근").setPreferredWidth(100);
		table.getColumn("퇴근").setPreferredWidth(100);
		table.getColumn("근무").setPreferredWidth(100);	//퇴근시간에서 출근시간을 뺀 값으로 실제 DB에 존재하지는 않는다.
		table.getColumn("id1").setWidth(0);				//id 컬럼은 보이면 안되기 때문에 이렇게 설정 (id1=personalDaily 테이블의 id 컬럼)
		table.getColumn("id1").setMinWidth(0);			
		table.getColumn("id1").setMaxWidth(0);			
		table.getColumn("date2").setWidth(0);			//id 컬럼은 보이면 안되기 때문에 이렇게 설정 (date2=personalDailyRecords 테이블의 날짜 컬럼)
		table.getColumn("date2").setMinWidth(0);			
		table.getColumn("date2").setMaxWidth(0);
		table.getColumn("date3").setWidth(0);			//id 컬럼은 보이면 안되기 때문에 이렇게 설정 (date3=personalDailyWork 테이블의 날짜 컬럼)
		table.getColumn("date3").setMinWidth(0);
		table.getColumn("date3").setMaxWidth(0);
		table.setBounds(0, 41, 1085, 520);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(1085,520);
		add(scrollPane);
		
		// 가운데 정렬
		DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();
		tableCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 오른쪽 정렬
		//DefaultTableCellRenderer tableRight = new DefaultTableCellRenderer();
		//tableRight.setHorizontalAlignment(SwingConstants.RIGHT);
		// J테이블의 컬럼명을 가져온다.
		TableColumnModel tableColumn = table.getColumnModel();
		//가운데 정렬 적용 컬럼
		for (int i=0; i<=2; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}
		//참조: 3, 4번째 항목은 왼쪽 정렬이기 때문에 정렬에서 제외 (여기는 오른쪽 정렬이 없다.)
		for (int i=5; i<=7; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}

		setVisible(true);
		
		//처음 실행시 오늘 날짜를 기준으로 DB의 select를 돌린다.
		queryType = "dateMonth";
		accountBookYear = comboYear.getSelectedItem().toString();
		accountBookMonth = comboMonth.getSelectedItem().toString();
		
		//테이블 초기화
		model.setNumRows(0);
		select();
		
		//조회 버튼 클릭 이벤트
		btnSearch.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//콤보박스에 선택된 연도, 월을 변수에 저장
				accountBookYear = comboYear.getSelectedItem().toString();
				accountBookMonth = comboMonth.getSelectedItem().toString();
				
				//콤보박스를 선택된 값에 따라 쿼리문을 선택하기 위해 값을 지정
				if (accountBookYear == "연" && accountBookMonth == "월") {
					queryType = "dateAll";
				} else if (accountBookYear != "연" && accountBookMonth == "월"){
					queryType = "dateYear";
				} else if (accountBookYear != "연" && accountBookMonth != "월"){
					queryType = "dateMonth";
				} else {
					JOptionPane.showMessageDialog(null, "연도를 선택해 주세요.", "조회 오류", JOptionPane.ERROR_MESSAGE);
				}
				
				//테이블 초기화
				model.setNumRows(0);
				select();
			}
		});

		//행추가 버튼 클릭 이벤트
		btnAddRow.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel modelAddRow = (DefaultTableModel) table.getModel();
				modelAddRow.addRow(new String[] {today, "주문진", "", "", "", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
			}
		});
		
		//저장 버튼 클릭 이벤트
		btnSave.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//기본 모델 생성
				DefaultTableModel modelSave = (DefaultTableModel) table.getModel();
				
				//테이블의 선택된 행번호를 저장
				int rowIndex = table.getSelectedRow();
				
				//행을 선택한 상태가 아니면 -1을 가지는데 그럴 경우에는 리턴(종료, 즉 아무런 이벤트도 발생하지 않는다.)
				if (rowIndex<0) return;
				
				/*
				 * 괄호별 서로 다른 테이블
				 * ("날짜", "장소", "운동", "날씨"), ("기록"), ("출근", "퇴근", ^근무^), ("id1", "date2", "date3") 
				*/
				//personalDaily 테이블에서 가져오는 값
				String getDate = (String) modelSave.getValueAt(rowIndex,  0);
				String getPlace = (String) modelSave.getValueAt(rowIndex,  1);
				String getPlay = (String) modelSave.getValueAt(rowIndex,  2);
				String getWeather = (String) modelSave.getValueAt(rowIndex,  3);
				//4번 기록 = personalDailyRecords 테이블
				String getRecords = (String) modelSave.getValueAt(rowIndex,  4);
				//5, 6번 출근, 퇴근 = personalDailyWork 테이블
				String getOnTime = (String) modelSave.getValueAt(rowIndex,  5);
				String getOffTime = (String) modelSave.getValueAt(rowIndex,  6);
				//7번 근무는 5, 6번 컬럼의 합이다
				//8, 9, 10번은 각 테이블의 id
				String getId1 = (String) modelSave.getValueAt(rowIndex, 8);
				//11, 12번은 각 테이블의 date
				String getDate2 = (String) modelSave.getValueAt(rowIndex, 9);
				String getDate3 = (String) modelSave.getValueAt(rowIndex, 10);
				
				//구한 값들은 배열에 담아서 보낸다.
				String[] getArray = {getDate, getPlace, getPlay, getWeather, getRecords, getOnTime, getOffTime, getId1, getDate2, getDate3};
				
				//id의 값이 null이면 "입력" 쿼리를 실행
				if(getId1 == null || getId1.length() ==0) {
					//personalDaily 테이블은 무조건 입력 쿼리 실행
					ZD02DaoPersonalDaily.insertDaily(getArray);
					//personalDailyRecords 테이블은 personalDailyRecordsBook 컬럼에 값이 들어가 있는 경우에만 쿼리 실행
					if(getRecords != null && getRecords.length() !=0) {
						ZD02DaoPersonalDaily.insertRecords(getArray);
					}
					//personalDailyWork 테이블은 personalDailyWorkOnTime 컬럼에 값이 들어가 있는 경우에만 쿼리 실행
					if(getOnTime != null && getOnTime.length() !=0) {
						ZD02DaoPersonalDaily.insertWork(getArray);
					}
				}

				/*
				 * daily 테이블이 수정 쿼리를 실행하더라도 records, work는 입력 쿼리를 실행하는 경우가 있는데
				 * daily 테이블의 날짜를 가져와서 records, work 테이블의 날짜와 비교한 후,
				 * 동일한 날짜를 가진 date 값이 있으면 수정을 실행,
				 * daily 테이블에는 있으나 records, work 테이블에는 해당 날짜가 없을 경우에는 
				 * records, work 테이블은 수정이 아니라 입력을 실행한다. 
				*/
				
				//id의 값이 null이 아니라면 "수정" 쿼리를 실행
				if (getId1 != null && getId1.length() !=0) {
					//personalDaily 테이블은 무조건 수정 쿼리 실행
					ZD02DaoPersonalDaily.updateDaily(getArray);
					//동일한 날짜가 있을 경우 "수정"을 실행하고,
					if(getDate.equals(getDate2)) {
						ZD02DaoPersonalDaily.updateRecords(getArray);
					}
					if(getDate.equals(getDate3)) {
						ZD02DaoPersonalDaily.updateWork(getArray);
					}
					//동일한 날짜는 없지만 "기록"컬럼에 값이 들어가 있는 경우에는 "입력"을 실행
					if(!getDate.equals(getDate2) && getRecords != null && getRecords.length() !=0) {
						ZD02DaoPersonalDaily.insertRecords(getArray);
					}
					//동일한 날짜는 없지만 "출근"컬럼에 값이 들어가 있는 경우에는 "입력"을 실행
					if(!getDate.equals(getDate3) && getOnTime != null && getOnTime.length() !=0) {
						ZD02DaoPersonalDaily.insertWork(getArray);
					}
				}

				//테이블 초기화
				model.setNumRows(0);
				select();
			}
		});
		
	}
	
	/*
	 * ********************************************************************
	 * DB에서 가져온 값들을 테이블에 넣기 (DB Select)
	 * ********************************************************************
	 * 
	 * parameter: queryType, accountBookYear, accountBookMonth
	 * return: 쿼리 결과 값 (list map)
	 * 
	*/
	private void select() {
		try {
			List<Map<String, Object>> list = ZD02DaoPersonalDaily.tableSelect(queryType, accountBookYear, accountBookMonth);
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map.get("personalDailyDate"),
	        			map.get("personalDailyPlace"),
	        			map.get("personalDailyPlay"),
	        			map.get("personalDailyWeather"),
	        			map.get("personalDailyRecordsBook"),
	        			map.get("onTimes"),		//출근시간 (date_format을 사용해서 값을 변경) 
	        			map.get("offTimes"),		//퇴근시간 (date_format을 사용해서 값을 변경)
	        			map.get("workTimes"),	//퇴근시간에서 출근시간을 뺀 값
	        			map.get("personalDailyId"),
	        			map.get("personalDailyRecordsDate"),
	        			map.get("personalDailyWorkDate")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}