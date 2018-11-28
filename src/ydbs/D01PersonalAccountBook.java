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

public class D01PersonalAccountBook extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"날짜", "대분류", "소분류", "내용", "금액", "id", "flag"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);
	private String queryTypeDate = null;
	private String queryTypeCategory = null;
	private String accountBookYear = null;
	private String accountBookMonth = null;
	private String accountBookCategory = null;
	private String accountBookSection = null;
	private JComboBox<String> comboSection = null;
	private String sectionArray1[] = {"소분류", "급여", "상여", "잔업", "이자", "생활", "음식", "교통", "가전", "통신", "오락", "의류", "미용", "의료", "기타", "X"};

	public D01PersonalAccountBook() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		//오늘 날짜 구하기 (초기 실행시 오늘 날자를 기준으로 DB를 select 하기 위함)
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatDate.format(date);
		String[] todayArray = today.split("-");
		
		String yearArray[] = {"연", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000"};
		JComboBox<String> comboYear = new JComboBox<String>(yearArray);
		comboYear.setSelectedItem(todayArray[0]);
		comboYear.setBounds(12, 10, 105, 21);
		add(comboYear);
		
		String monthArray[] = {"월", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		JComboBox<String> comboMonth = new JComboBox<String>(monthArray);
		comboMonth.setSelectedItem(todayArray[1]);
		comboMonth.setBounds(129, 10, 70, 21);
		add(comboMonth);
		
		String categoryArray[] = {"대분류", "수입", "지출"};
		JComboBox<String> comboCategory = new JComboBox<String>(categoryArray);
		comboCategory.setBounds(209, 10, 80, 21);
		add(comboCategory);
		
		comboSection = new JComboBox<String>(sectionArray1);
		comboSection.setBounds(299, 10, 80, 21);
		add(comboSection);
		
		//대분류 콤보박스의 선택된 값에 따라 소분류 콤보박스의 항목을 변경한다.
		comboCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedItem = comboCategory.getSelectedItem().toString();
				comboSection.removeAllItems();
				String sectionArray2[] = {"소분류", "급여", "상여", "잔업", "알바", "이자", "투자", "기타"};
				String sectionArray3[] = {"소분류", "생활", "음식", "교통", "가전", "통신", "오락", "의류", "미용", "의료", "기타", "X"};
				if (selectedItem == "대분류") {
					for (int i=0; i<sectionArray1.length; i++) {
						comboSection.addItem(sectionArray1[i]);
					}
				}
				if (selectedItem == "수입") {
					for (int i=0; i<sectionArray2.length; i++) {
						comboSection.addItem(sectionArray2[i]);
					}
				}
				if (selectedItem == "지출") {
					for (int i=0; i<sectionArray3.length; i++) {
						comboSection.addItem(sectionArray3[i]);
					}
				}		
			}
		});
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(410, 9, 88, 23);
		add(btnSearch);
		
		JButton btnAddRow = new JButton("행 추가");
		btnAddRow.setBounds(510, 9, 88, 23);
		add(btnAddRow);
		
		JButton btnSave = new JButton("저장");
		btnSave.setBounds(610, 9, 88, 23);
		add(btnSave);
		
		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(710, 9, 88, 23);
		add(btnDelete);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
		table.getColumn("날짜").setPreferredWidth(120);
		table.getColumn("대분류").setPreferredWidth(50);
		table.getColumn("소분류").setPreferredWidth(50);
		table.getColumn("내용").setPreferredWidth(1100);
		table.getColumn("금액").setPreferredWidth(120);
		table.getColumn("id").setWidth(0);						//id 컬럼과 flag 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table.getColumn("id").setMinWidth(0);			
		table.getColumn("id").setMaxWidth(0);			
		table.getColumn("flag").setWidth(0);						//id 컬럼과 flag 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table.getColumn("flag").setMinWidth(0);			
		table.getColumn("flag").setMaxWidth(0);			
		table.setBounds(0, 41, 1085, 520);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(1085,520);
		add(scrollPane);
		
		// 가운데 정렬
		DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();
		tableCenter.setHorizontalAlignment(SwingConstants.CENTER);
		// 오른쪽 정렬
		DefaultTableCellRenderer tableRight = new DefaultTableCellRenderer();
		tableRight.setHorizontalAlignment(SwingConstants.RIGHT);
		// J테이블의 컬럼명을 가져온다.
		TableColumnModel tableColumn = table.getColumnModel();
		//가운데 정렬 적용 컬럼
		for (int i=0; i<=2; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}
		//오른쪽 정렬 적용 컬럼
		tableColumn.getColumn(4).setCellRenderer(tableRight);
		//참고:  (3번째 "내용" 컬럼은 왼쪽 정렬이기 때문에 지정하지 않는다), (5, 6번 id 컬럼과 flag 컬럼은 눈에 보이지 않으므로 어떤식으로 정렬하든 별 상관없다.)
		
		setVisible(true);
		
		//처음 실행시 오늘 날짜를 기준으로 DB의 select를 돌린다.
		queryTypeDate = "dateMonth";
		queryTypeCategory ="typeAll";
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
				accountBookCategory = comboCategory.getSelectedItem().toString();
				accountBookSection = comboSection.getSelectedItem().toString();
				
				//콤보박스 연도와 날짜에 선택된 값에 따라 쿼리문을 선택하기 위해 값을 지정
				if (accountBookYear == "연" && accountBookMonth == "월") {
					queryTypeDate = "dateAll";
				} else if (accountBookYear != "연" && accountBookMonth == "월"){
					queryTypeDate = "dateYear";
				} else if (accountBookYear != "연" && accountBookMonth != "월"){
					queryTypeDate = "dateMonth";
				} else {
					JOptionPane.showMessageDialog(null, "연도를 선택해 주세요.", "조회 오류", JOptionPane.ERROR_MESSAGE);
				}

				//콤보박스 카테고리와 섹션에 선택된 값에 따라 쿼리문을 선택하기 위해 값을 지정
				if (accountBookCategory == "대분류" && accountBookSection == "소분류") {
					queryTypeCategory = "typeAll";
				} else if (accountBookCategory != "대분류" && accountBookSection == "소분류"){
					queryTypeCategory = "typeCategory";
				} else if (accountBookCategory != "대분류" && accountBookSection != "소분류"){
					queryTypeCategory = "typeSection";
				} else if (accountBookCategory == "대분류" && accountBookSection != "소분류"){
					queryTypeCategory = "typeSectionOnly";
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
				modelAddRow.addRow(new String[] {today, "지출", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
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
				
				//"날짜", "대분류", "소분류", "내용", "금액", "id", "flag" 순으로 각각의 변수에 저장
				String getDate = (String) modelSave.getValueAt(rowIndex,  0);
				String getCategory = (String) modelSave.getValueAt(rowIndex,  1);
				String getSection = (String) modelSave.getValueAt(rowIndex,  2);
				String getDetails = (String) modelSave.getValueAt(rowIndex,  3);
				String getCost = (String) modelSave.getValueAt(rowIndex,  4);
				String getId = (String) modelSave.getValueAt(rowIndex, 5);
				String getFlag = (String) modelSave.getValueAt(rowIndex, 6);
				
				//idValue의 값이 널이면 "입력" 문자를 입력해서 insert 쿼리문이 동작하게 만든다.
				String idValue = (String) modelSave.getValueAt(rowIndex, 5);
				if(idValue == null || idValue.length() ==0) {
					idValue = "입력";
				} else {
				//idValue의 값이 널이 아니면 "수정" 문자를 입력해서 update 쿼리문이 동작하게 만든다.
					idValue = "수정";					
				}

				//구한 값들은 배열에 담아서 보낸다.
				String[] insertArray = {getDate, getCategory, getSection, getDetails, getCost, getId, getFlag, idValue};
				ZD01DaoPersonalAccountBook.tableInsertUpdate(insertArray);
				
				//테이블 초기화
				model.setNumRows(0);
				select();
			}
		});
		
		//삭제 버튼 클릭 이벤트
		btnDelete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//기본 모델 생성
				DefaultTableModel modelSave = (DefaultTableModel) table.getModel();
				
				//테이블의 선택된 행번호를 저장
				int rowIndex = table.getSelectedRow();
				
				//행을 선택한 상태가 아니면 -1을 가지는데 그럴 경우에는 리턴(종료, 즉 아무런 이벤트도 발생하지 않는다.)
				if (rowIndex<0) return;
				
				//"id"를 기준으로 삭제를 하므로 id 값만 가져온다.
				String getId = (String) modelSave.getValueAt(rowIndex, 5);
				
				//구한 값들은 배열에 담아서 보낸다.
				ZD01DaoPersonalAccountBook.tableDelete(getId);
				
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
			List<Map<String, Object>> list = ZD01DaoPersonalAccountBook.tableSelect(queryTypeDate, queryTypeCategory, accountBookYear, accountBookMonth, accountBookCategory, accountBookSection);
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map.get("personalAccountBookDate"),
	        			map.get("personalAccountBookCategory"),
	        			map.get("personalAccountBookSection"),
	        			map.get("personalAccountBookDetails"),
	        			String.format("%,d", (Integer.parseInt(map.get("personalAccountBookCost").toString()))),
	        			map.get("personalAccountBookId"),
	        			map.get("personalAccountBookDeleteFlag")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}