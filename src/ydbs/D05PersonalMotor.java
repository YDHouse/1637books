package ydbs;

import java.util.List;
import java.util.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class D05PersonalMotor extends JPanel{

	private static final long serialVersionUID = 1L;

	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames1[] = {"날짜", "원/리터", "주유용량", "금액", "누적주행", "주행거리", "연비", "id"};
	private String colmNames2[] = {"날짜", "구분", "내용", "장소", "금액", "거리1", "거리2", "id"};
	private String colmNames3[] = {"날짜", "구분", "내용", "장소", "금액", "id"};
	private DefaultTableModel model1 = new DefaultTableModel(colmNames1, 0);
	private DefaultTableModel model2 = new DefaultTableModel(colmNames2, 0);
	private DefaultTableModel model3 = new DefaultTableModel(colmNames3, 0);
	private String queryTypeDate = null;
	private String queryTypeCategory = "first";	//프로그램이 처음 시작될 때에는 연비, 오일, 기타 3가지 테이블 모두 select하기 위해.
	private String aYear = null;
	private String aMonth = null;
	

	public D05PersonalMotor() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		String yearArray[] = {"연", "2020", "2019", "2018", "2017", "2016"};
		JComboBox<String> comboYear = new JComboBox<String>(yearArray);
		comboYear.setBounds(12, 10, 105, 21);
		add(comboYear);
		
		String monthArray[] = {"월", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		JComboBox<String> comboMonth = new JComboBox<String>(monthArray);
		comboMonth.setBounds(129, 10, 70, 21);
		add(comboMonth);
		
		String categoryArray[] = {"연비", "오일", "기타"};
		JComboBox<String> comboCategory = new JComboBox<String>(categoryArray);
		comboCategory.setBounds(210, 10, 70, 21);
		add(comboCategory);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(310, 9, 88, 23);
		add(btnSearch);
		
		JButton btnAddRow = new JButton("행 추가");
		btnAddRow.setBounds(410, 9, 88, 23);
		add(btnAddRow);
		
		JButton btnSave = new JButton("저장");
		btnSave.setBounds(510, 9, 88, 23);
		add(btnSave);
		
		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(610, 9, 88, 23);
		add(btnDelete);

		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		//첫번째 테이블 (연비)
		JTable table1 = new JTable(model1);
		table1.getColumn("id").setWidth(0);			//id 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table1.getColumn("id").setMinWidth(0);			
		table1.getColumn("id").setMaxWidth(0);
		table1.getColumn("날짜").setPreferredWidth(100);
		table1.setBounds(0, 41, 420, 520);
		JScrollPane scrollPane = new JScrollPane(table1);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(420, 520);
		add(scrollPane);
		
		//두번째 테이블 (엔진오일교체)
		JTable table2 = new JTable(model2);
		table2.getColumn("id").setWidth(0);			//id 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table2.getColumn("id").setMinWidth(0);			
		table2.getColumn("id").setMaxWidth(0);
		table2.getColumn("날짜").setPreferredWidth(100);
		table2.getColumn("구분").setPreferredWidth(80);
		table2.getColumn("내용").setPreferredWidth(300);
		table2.getColumn("장소").setPreferredWidth(180);
		table2.getColumn("금액").setPreferredWidth(60);
		table2.getColumn("거리1").setPreferredWidth(60);
		table2.getColumn("거리2").setPreferredWidth(60);
		table2.setBounds(435, 41, 630, 255);
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setLocation(435, 41);
		scrollPane2.setSize(630, 255);
		add(scrollPane2);
		
		//세번째 테이블 (보험, 수리 등)
		JTable table3 = new JTable(model3);
		table3.getColumn("id").setWidth(0);			//id 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table3.getColumn("id").setMinWidth(0);			
		table3.getColumn("id").setMaxWidth(0);
		table3.getColumn("날짜").setPreferredWidth(100);
		table3.getColumn("구분").setPreferredWidth(120);
		table3.getColumn("내용").setPreferredWidth(300);
		table3.getColumn("장소").setPreferredWidth(180);
		table3.getColumn("금액").setPreferredWidth(80);
		table3.setBounds(435, 305, 630, 255);
		JScrollPane scrollPane3 = new JScrollPane(table3);
		scrollPane3.setLocation(435, 305);
		scrollPane3.setSize(630, 255);
		add(scrollPane3);
		
		//가운데 정렬
		DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();
		tableCenter.setHorizontalAlignment(SwingConstants.CENTER);
		//오른쪽 정렬
		DefaultTableCellRenderer tableRight = new DefaultTableCellRenderer();
		tableRight.setHorizontalAlignment(SwingConstants.RIGHT);
		//J테이블의 컬럼명을 가져온다.
		TableColumnModel tableColumn1 = table1.getColumnModel();
		TableColumnModel tableColumn2 = table2.getColumnModel();
		TableColumnModel tableColumn3 = table3.getColumnModel();
		//가운데 정렬 적용 컬럼
		tableColumn1.getColumn(0).setCellRenderer(tableCenter);
		tableColumn2.getColumn(0).setCellRenderer(tableCenter);
		tableColumn3.getColumn(0).setCellRenderer(tableCenter);
		//오른쪽 정렬 적용 컬럼
		for (int i=1; i<=6; i++) {
			tableColumn1.getColumn(i).setCellRenderer(tableRight);
		}
		for (int i=4; i<=6; i++) {
			tableColumn2.getColumn(i).setCellRenderer(tableRight);
		}
		tableColumn3.getColumn(4).setCellRenderer(tableRight);
		
		setVisible(true);
		
		//처음 실행시 오늘 날짜를 기준으로 DB의 select를 돌린다.
		queryTypeDate = "dateAll";
		//aYear = comboYear.getSelectedItem().toString();
		//aMonth = comboMonth.getSelectedItem().toString();

		//테이블 초기화
		model1.setNumRows(0);
		model2.setNumRows(0);
		model3.setNumRows(0);
		select1();
		select2();
		select3();
		
		//조회 버튼 클릭 이벤트
		btnSearch.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//콤보박스에 선택된 연도, 월을 변수에 저장
				aYear = comboYear.getSelectedItem().toString();
				aMonth = comboMonth.getSelectedItem().toString();
				
				//콤보박스를 선택된 값에 따라 쿼리문을 선택하기 위해 값을 지정
				if (aYear == "연" && aMonth == "월") {
					queryTypeDate = "dateAll";
				} else if (aYear != "연" && aMonth == "월"){
					queryTypeDate = "dateYear";
				} else if (aYear != "연" && aMonth != "월"){
					queryTypeDate = "dateMonth";
				} else {
					JOptionPane.showMessageDialog(null, "연도를 선택해 주세요.", "조회 오류", JOptionPane.ERROR_MESSAGE);
				}
				
				//콤보박스에 선택된 카테고리를 변수에 저장
				queryTypeCategory = comboCategory.getSelectedItem().toString();
				
				//카테고리 별 테이블 초기화 후 쿼리 실행
				if (queryTypeCategory == "연비") {
					model1.setNumRows(0);
					select1();
				}
				if (queryTypeCategory == "오일") {
					model2.setNumRows(0);
					select2();
				}
				if (queryTypeCategory == "기타") {
					model3.setNumRows(0);					
					select3();
				}

			}
		});

		//행추가 버튼 클릭 이벤트
		btnAddRow.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//콤보박스에 선택된 카테고리를 변수에 저장
				queryTypeCategory = comboCategory.getSelectedItem().toString();
				
				//카테고리 별 테이블 초기화 후 쿼리 실행
				if (queryTypeCategory == "연비") {
					DefaultTableModel modelAddRow = (DefaultTableModel) table1.getModel();
					modelAddRow.addRow(new String[] {"", "", "", "", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
				} else if(queryTypeCategory == "오일") {
					DefaultTableModel modelAddRow = (DefaultTableModel) table2.getModel();
					modelAddRow.addRow(new String[] {"", "", "", "", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
				} else {	//기타
					DefaultTableModel modelAddRow = (DefaultTableModel) table3.getModel();
					modelAddRow.addRow(new String[] {"", "", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
				}
			}
		});
		
		//저장 버튼 클릭 이벤트
		btnSave.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//콤보박스에 선택된 카테고리를 변수에 저장
				queryTypeCategory = comboCategory.getSelectedItem().toString();
				
				//카테고리 별 테이블 초기화 후 쿼리 실행
				if (queryTypeCategory == "연비") {
					//기본 모델 생성
					DefaultTableModel modelSave1 = (DefaultTableModel) table1.getModel();
					//테이블의 선택된 행번호를 저장
					int rowIndex = table1.getSelectedRow();
					//행을 선택한 상태가 아니면 -1을 가지는데 그럴 경우에는 리턴(종료, 즉 아무런 이벤트도 발생하지 않는다.)
					if (rowIndex<0) return;
					//"날짜", "원/리터", "용량", "누적주행거리", "id" 순으로 각각의 변수에 저장
					String getDate = (String) modelSave1.getValueAt(rowIndex,  0);
					String getGasolineCostaaa = (String) modelSave1.getValueAt(rowIndex,  1);
					String getGasolineCost = getGasolineCostaaa.replaceAll(",", "");					//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
					String getGasolineVolume = (String) modelSave1.getValueAt(rowIndex,  2);
					String getDistance = (String) modelSave1.getValueAt(rowIndex,  4);
					String getId = (String) modelSave1.getValueAt(rowIndex, 7);
					//구한 값들은 배열에 담아서 보낸다.
					String[] getArray = {getDate, getGasolineCost, getGasolineVolume, getDistance, getId};
					//id의 값이 null이면 "입력" 쿼리를 실행
					if(getId == null || getId.length() ==0) {
						ZD05DaoPersonalMotor.tableInsert(queryTypeCategory, getArray);
						//입력일 경우에만 장부에 동일하게 입력하기
						int aCost = Integer.parseInt(getGasolineCost);
						int bVolume = Integer.parseInt(getGasolineVolume);
						int result = aCost * bVolume;
						String getCost = String.valueOf(result).toString();
						personalaccountbookInsert(getDate, getCost);
					}
					//id의 값이 null이 아니라면 "수정" 쿼리를 실행
					if (getId != null && getId.length() !=0) {
						ZD05DaoPersonalMotor.tableUpdate(queryTypeCategory, getArray);
					}			
					//테이블 초기화
					model1.setNumRows(0);
					select1();
				} else if(queryTypeCategory == "오일") {
					//기본 모델 생성
					DefaultTableModel modelSave2 = (DefaultTableModel) table2.getModel();
					//테이블의 선택된 행번호를 저장
					int rowIndex = table2.getSelectedRow();
					//행을 선택한 상태가 아니면 -1을 가지는데 그럴 경우에는 리턴(종료, 즉 아무런 이벤트도 발생하지 않는다.)
					if (rowIndex<0) return;
					//"날짜", "구분", "내용", "장소", "금액", "거리1", "id" 순으로 각각의 변수에 저장
					String getDate = (String) modelSave2.getValueAt(rowIndex,  0);
					String getSection = (String) modelSave2.getValueAt(rowIndex,  1);
					String getDetails = (String) modelSave2.getValueAt(rowIndex,  2);
					String getPlace = (String) modelSave2.getValueAt(rowIndex,  3);
					String getCostaaa = (String) modelSave2.getValueAt(rowIndex,  4);
					String getCost = getCostaaa.replaceAll(",", "");					//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
					String getDistance = (String) modelSave2.getValueAt(rowIndex, 5);
					String getId = (String) modelSave2.getValueAt(rowIndex, 7);
					//구한 값들은 배열에 담아서 보낸다.
					String[] getArray = {getDate, getSection, getDetails, getPlace, getCost, getDistance, getId};
					//id의 값이 null이면 "입력" 쿼리를 실행
					if(getId == null || getId.length() ==0) {
						ZD05DaoPersonalMotor.tableInsert(queryTypeCategory, getArray);
						//입력일 경우에만 장부에 동일하게 입력하기
						personalaccountbookInsert(getDate, getCost);
					}
					//id의 값이 null이 아니라면 "수정" 쿼리를 실행
					if (getId != null && getId.length() !=0) {
						ZD05DaoPersonalMotor.tableUpdate(queryTypeCategory, getArray);
					}
					//테이블 초기화
					model2.setNumRows(0);
					select2();
				} else {	//기타
					//기본 모델 생성
					DefaultTableModel modelSave3 = (DefaultTableModel) table3.getModel();
					//테이블의 선택된 행번호를 저장
					int rowIndex = table3.getSelectedRow();
					//행을 선택한 상태가 아니면 -1을 가지는데 그럴 경우에는 리턴(종료, 즉 아무런 이벤트도 발생하지 않는다.)
					if (rowIndex<0) return;
					//"날짜", "구분", "내용", "장소", "금액", "id" 순으로 각각의 변수에 저장
					String getDate = (String) modelSave3.getValueAt(rowIndex,  0);
					String getSection = (String) modelSave3.getValueAt(rowIndex,  1);
					String getDetails = (String) modelSave3.getValueAt(rowIndex,  2);
					String getPlace = (String) modelSave3.getValueAt(rowIndex,  3);
					String getCostaaa = (String) modelSave3.getValueAt(rowIndex,  4);
					String getCost = getCostaaa.replaceAll(",", "");					//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
					String getId = (String) modelSave3.getValueAt(rowIndex, 5);
					//구한 값들은 배열에 담아서 보낸다.
					String[] getArray = {getDate, getSection, getDetails, getPlace, getCost, getId};
					//id의 값이 null이면 "입력" 쿼리를 실행
					if(getId == null || getId.length() ==0) {
						ZD05DaoPersonalMotor.tableInsert(queryTypeCategory, getArray);
						//입력일 경우에만 장부에 동일하게 입력하기
						personalaccountbookInsert(getDate, getCost);
					}
					//id의 값이 null이 아니라면 "수정" 쿼리를 실행
					if (getId != null && getId.length() !=0) {
						ZD05DaoPersonalMotor.tableUpdate(queryTypeCategory, getArray);
					}
					//테이블 초기화
					model3.setNumRows(0);
					select3();
				}
			}
		});
		
		//삭제 버튼 클릭 이벤트
		btnDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//콤보박스의 값 가져오기
				queryTypeCategory = comboCategory.getSelectedItem().toString();

				//카테고리 별 실행
				if (queryTypeCategory == "연비") {
					DefaultTableModel modelSave = (DefaultTableModel) table1.getModel();
					int rowIndex = table1.getSelectedRow();
					if (rowIndex<0) return;
					int getId = Integer.parseInt((String) modelSave.getValueAt(rowIndex, 7));
					ZD05DaoPersonalMotor.tableDelete(getId);
					model1.setNumRows(0);
					select1();
				}
				if(queryTypeCategory == "오일") {
					DefaultTableModel modelSave = (DefaultTableModel) table2.getModel();
					int rowIndex = table2.getSelectedRow();
					if (rowIndex<0) return;
					int getId = Integer.parseInt((String) modelSave.getValueAt(rowIndex, 7));
					ZD05DaoPersonalMotor.tableDelete(getId);
					model2.setNumRows(0);
					select2();
				}
				if(queryTypeCategory == "기타") {
					DefaultTableModel modelSave = (DefaultTableModel) table3.getModel();
					int rowIndex = table3.getSelectedRow();
					if (rowIndex<0) return;
					int getId = Integer.parseInt((String) modelSave.getValueAt(rowIndex, 5));
					ZD05DaoPersonalMotor.tableDelete(getId);
					model3.setNumRows(0);
					select3();
				}
			}
		});
		
	}
	
	/*
	 * ********************************************************************
	 * DB에서 가져온 값들을 테이블에 넣기 (DB Select)
	 * ********************************************************************
	 * 
	 * parameter: queryTypeDate, aYear, aMonth
	 * return: 쿼리 결과 값 (list map)
	 * 
	*/
	private void select1() {
		try {
			List<Map<String, Object>> list = ZD05DaoPersonalMotor.tableSelect("연비", queryTypeDate, aYear, aMonth);
			int sizeCount = list.size();
			if (sizeCount == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
			/* ******************************************************
			 * 
			 * 누적주행거리를 빼기 연산을 해서 주행거리 구하기
			 * 
			 * ******************************************************
			*/
			//주행거리 계산을 위하여 누적거리를 담을 변수를 선언
			BigDecimal[] firstDistance = new BigDecimal[sizeCount];
			//두번째 누적거리에서 첫번째 누적거리를 뺀 주행거리가 저장할 변수를 선언
			BigDecimal[] sumDistance = new BigDecimal[sizeCount];
			
			//연비 계산을 위하여 주유용량을 담을 변수를 선언
			BigDecimal[] gasolineCapacity = new BigDecimal[sizeCount];
			//두번째 주행거리에서 첫번째 주유 용량을 나눈 값 저장할 변수를 선언
			BigDecimal[] divideGasolineCapacity = new BigDecimal[sizeCount];
			
			//주행거리와 연비의 첫번째 배열에는 무조건 0을 입력한다.
			sumDistance[0] = BigDecimal.valueOf(0);
			divideGasolineCapacity[0] = BigDecimal.valueOf(0);
			
			for (int i = 0; i < sizeCount; i++) {
				//사이즈가 0이 아닐 때 즉 조회된 값이 하나라도 있을 때에만 다음의 작업을 수행한다.
				//값이 없을 때 아래의 작업을 하면 널포인트 익셉션이 발생
				if (sizeCount != 0) {
					//누적 주행거리를 배열에 담는다
					firstDistance[i] = BigDecimal.valueOf(Float.parseFloat(list.get(i).get("personalMotorDistance").toString()));
					//주유 용량을 배열에 담는다.
					gasolineCapacity[i] = BigDecimal.valueOf(Float.parseFloat(list.get(i).get("personalMotorGasolineVolume").toString()));
					//조회된 값이 0보다 클 때 즉, 1개 이상의 누적주행거리가 있을 때 아래 작업을 실행
					//아니면 마찬가지로 널포인트 익셉션 발생
					if (i > 0) {
						//두번째 누적거리에서 첫번째 누적거리를 뺀(subtract) 값을 변수에 저장
						sumDistance[i] = firstDistance[i].subtract(firstDistance[i-1]);
						//두번째 주행거리에서 첫번째 주유용량을 나눈다. (나누기를 할때는 꼭 소수점 처리를 해야 에러가 안난다.)
						divideGasolineCapacity[i] = sumDistance[i].divide(gasolineCapacity[i-1], 1, BigDecimal.ROUND_HALF_UP);
					}
				}
			}
			
			/* ******************************************************
			 * 
			 * 테이블에 값 넣기
			 * 
			 * ******************************************************
			*/
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model1.addRow(new Object[] {
	        			map.get("personalMotorDate"),
	        			String.format("%,d", (Integer.parseInt(map.get("personalMotorGasolineCost").toString()))),
	        			map.get("personalMotorGasolineVolume"),
	        			String.format("%,d", (Integer.parseInt(map.get("personalMotorCostSum").toString()))),
	        			map.get("personalMotorDistance"),
	        			String.format("%.1f", sumDistance[i]),				//주행거리 소수점 첫째자리까지만 표시
	        			divideGasolineCapacity[i],
	        			map.get("personalMotorId")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//엔진오일 select
	private void select2() {
		try {
			List<Map<String, Object>> list = ZD05DaoPersonalMotor.tableSelect("오일", queryTypeDate, aYear, aMonth);
			int sizeCount = list.size();
			if (sizeCount == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model2.addRow(new Object[] {
        			map.get("personalMotorOilDate"),
        			map.get("personalMotorOilSection"),
        			map.get("personalMotorOilDetails"), 
        			map.get("personalMotorOilLocation"),
        			String.format("%,d", (Integer.parseInt(map.get("personalMotorOilCost").toString()))),
        			String.format("%,d", (Integer.parseInt(map.get("personalMotorOilDistance").toString()))),
        			0,
        			map.get("personalMotorOilId")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//기타 select
	private void select3() {
		try {
			List<Map<String, Object>> list = ZD05DaoPersonalMotor.tableSelect("기타", queryTypeDate, aYear, aMonth);
			int sizeCount = list.size();
			if (sizeCount == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model3.addRow(new Object[] {
        			map.get("personalMotorOilDate"),
        			map.get("personalMotorOilSection"),
        			map.get("personalMotorOilDetails"),
        			map.get("personalMotorOilLocation"),
        			String.format("%,d", (Integer.parseInt(map.get("personalMotorOilCost").toString()))),
        			map.get("personalMotorOilId")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//팝업창 (장부에 동일하게 기록할 것인가 확인 창)
	private void personalaccountbookInsert(String aDate, String aValue) {
		String[] getArray = {aDate, "지출", "교통", "스쿠터 주유", aValue};
		
		int result = JOptionPane.showConfirmDialog(null, "장부에 기입하겠습니까?", "장부기입 확인", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.CLOSED_OPTION) {
			//yes or no를 선택하지 않고 그냥 x를 눌러서 끌 경우
		} else if (result == JOptionPane.YES_OPTION) {
			//yes를 선택했을 경우 - 개인장부에 동일한 내용을 자동으로 입력
			ZD05DaoPersonalMotor.personalaccountbookInsert(getArray);
		} else {
			//no를 선택했을 경우 - 아무 행동 없음
		}
		
	}
		
}