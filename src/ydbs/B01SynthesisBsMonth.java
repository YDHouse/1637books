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

public class B01SynthesisBsMonth extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"날짜", "사업수입", "개인수입", "수입합계", "사업비용", "사업자산", "개인지출", "지출합계",  "월간합계"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);
	private String queryType = "dateAll";
	private String accountBookYear = null;
	private String accountBookMonth = null;

	public B01SynthesisBsMonth() {
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
		//comboMonth.setSelectedItem(todayArray[1]);
		comboMonth.setBounds(129, 10, 70, 21);
		add(comboMonth);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(210, 9, 88, 23);
		add(btnSearch);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
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
		tableColumn.getColumn(0).setCellRenderer(tableCenter);
		// 오른쪽 정렬 적용 컬럼
		for (int i=1; i<=8; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableRight);
		}
		setVisible(true);
		
		//처음 실행시 오늘 날짜의 연도를 기준으로 DB의 select를 돌린다.
		queryType = "dateYear";
		accountBookYear = comboYear.getSelectedItem().toString();
		accountBookMonth = "월";
		
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
		
	}
	
	/*
	 * ********************************************************************
	 * DB에서 가져온 값들을 테이블에 넣기 (DB Select)
	 * ********************************************************************
	 * 
	 * parameter: queryType, accountBookYear, accountBookMonth
	 * return:
	 * 		list1, map1 = 개인의 수입, 지출
	 * 	 	list2, map2 = 사업의 수입, 지출, 자산
	*/
	private void select() {
		try {
			List<Map<String, Object>> list1 = ZB01DaoBsMonth.selectPersonal(queryType, accountBookYear, accountBookMonth);
			List<Map<String, Object>> list2 = ZB01DaoBsMonth.selectBusiness(queryType, accountBookYear, accountBookMonth);
			if (list1.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				Map<String, Object> map2 = list2.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map1.get("personalDate"),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessIncome").toString()))),
	        			String.format("%,d", (Integer.parseInt(map1.get("personalIncome").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessIncome").toString()) + Integer.parseInt(map1.get("personalIncome").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessAsset").toString()))),
	        			String.format("%,d", (Integer.parseInt(map1.get("personalCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessCost").toString()) + Integer.parseInt(map2.get("businessAsset").toString()) + Integer.parseInt(map1.get("personalCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessIncome").toString()) + Integer.parseInt(map1.get("personalIncome").toString()) - Integer.parseInt(map2.get("businessCost").toString()) - Integer.parseInt(map2.get("businessAsset").toString()) - Integer.parseInt(map1.get("personalCost").toString())))
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}