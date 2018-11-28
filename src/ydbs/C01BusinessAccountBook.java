package ydbs;

import java.util.List;
import java.util.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class C01BusinessAccountBook extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private static String colmNames[] = {"날짜", "구분", "거래구분", "거래내용", "거래처", "수입", "수입VAT", "비용", "비용VAT", "고정자산비용", "고정자산VAT", "비고"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);
	private String queryType = "dateAll";
	private String accountBookYear = null;
	private String accountBookMonth = null;

	public C01BusinessAccountBook() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		String yearArray[] = {"연", "2020", "2019", "2018"};
		JComboBox<String> comboYear = new JComboBox<String>(yearArray);
		comboYear.setBounds(12, 10, 105, 21);
		add(comboYear);
		
		String monthArray[] = {"월", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		JComboBox<String> comboMonth = new JComboBox<String>(monthArray);
		comboMonth.setBounds(129, 10, 70, 21);
		add(comboMonth);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(211, 9, 88, 23);
		add(btnSearch);
		
		JButton btnInsert = new JButton("장부기입");
		btnInsert.setBounds(310, 9, 88, 23);
		add(btnInsert);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
		table.setBounds(0, 41, 1085, 520);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(1085,520);
		add(scrollPane);
		
		//가운데 정렬
		DefaultTableCellRenderer tableCenter = new DefaultTableCellRenderer();
		tableCenter.setHorizontalAlignment(SwingConstants.CENTER);
		//오른쪽 정렬
		DefaultTableCellRenderer tableRight = new DefaultTableCellRenderer();
		tableRight.setHorizontalAlignment(SwingConstants.RIGHT);
		// J테이블의 컬럼명을 가져온다.
		TableColumnModel tableColumn = table.getColumnModel();
		//가운데 정렬 적용 컬럼
		for (int i=0; i<=4; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}
		tableColumn.getColumn(11).setCellRenderer(tableCenter);
		//오른쪽 정렬 적용 컬럼
		for (int i=5; i<=10; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableRight);
		}
		
		setVisible(true);
		
		select();
		
		//조회 버튼 클릭 이벤트
		btnSearch.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//테이블 초기화
				model.setNumRows(0);
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
				
				select();
			}
		});
		
		//장부기입 버튼 클릭 이벤트
		btnInsert.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				C01BusinessAccountBookInsert.main();
			}
		});
		
	}
	
	//J테이블에 DB 값 가져오기
	private void select() {
		try {
			List<Map<String, Object>> list = ZC01DaoBusinessAccountBook.getSelect(queryType, accountBookYear, accountBookMonth);
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map.get("businessAccountBookDate"),
	        			map.get("businessAccountBookCategory"),
	        			map.get("businessAccountBookSection"),
	        			map.get("businessAccountBookDetails"),
	        			map.get("businessAccountBookClient"),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookIncome").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookIncomeVat").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookCostVat").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookAsset").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("businessAccountBookAssetVat").toString()))),
	        			map.get("businessAccountBookRemarks")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}