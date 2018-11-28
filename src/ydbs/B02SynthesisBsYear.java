package ydbs;

import java.util.List;
import java.util.Map;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class B02SynthesisBsYear extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"날짜", "수입", "지출", "차이"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);
	private JTextField textIncomeTotal;		//텍스트 총수입
	private JTextField textLack;			//텍스트 부족분
	private JTextField textCostTotal;		//텍스트 총지출
	private JTextField textStock;			//텍스트 주식
	private JTextField textHouse;			//텍스트 청약
	private JTextField textDeposit;			//텍스트 예금
	private JTextField textKB;				//텍스트 국민
	private JTextField textHana;			//텍스트 하나외환
	private JTextField textCash;			//텍스트 현금
	private JTextField textCostSum;			//텍스트 지출합
	private JTextField textIncomeSum;		//텍스트 수입합
	private JTextField textBalance;			//텍스트 BS 균형 확인
	
	private int incomeTotal = 0;			//총수입 금액이 담길 변수
	private int costTotal = 0;				//총지출 금액이 담길 변수
	private int[] amountTotal = new int[7];	//총수입, 총지출을 제외한 금액이 담길 변수

	public B02SynthesisBsYear() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(10, 9, 88, 23);
		add(btnSearch);
		
		JButton btnUpdate = new JButton("저장");
		btnUpdate.setBounds(110, 9, 88, 23);
		add(btnUpdate);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
		table.setBounds(0, 41, 300, 520);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(300,520);
		add(scrollPane);
		
		//테이블 옆에 생성하는 패널
		JPanel panel = new JPanel();
		panel.setBounds(312, 41, 393, 249);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("총수입");
		lblNewLabel.setBounds(12, 10, 36, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("부족분");
		lblNewLabel_1.setBounds(12, 40, 36, 15);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("총지출");
		lblNewLabel_2.setBounds(208, 14, 57, 15);
		panel.add(lblNewLabel_2);
		
		JLabel label = new JLabel("주식");
		label.setBounds(208, 44, 57, 15);
		panel.add(label);
		
		JLabel lblNewLabel_3 = new JLabel("청약");
		lblNewLabel_3.setBounds(208, 73, 57, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("예금");
		lblNewLabel_4.setBounds(208, 102, 57, 15);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("국민");
		lblNewLabel_5.setBounds(208, 132, 57, 15);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("외환");
		lblNewLabel_6.setBounds(208, 162, 57, 15);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("현금");
		lblNewLabel_7.setBounds(208, 193, 57, 15);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("지출합");
		lblNewLabel_8.setBounds(208, 222, 57, 15);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("수입합");
		lblNewLabel_9.setBounds(12, 222, 36, 15);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("균형");
		lblNewLabel_10.setBounds(12, 162, 57, 15);
		panel.add(lblNewLabel_10);
		
		textIncomeTotal = new JTextField();
		textIncomeTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textIncomeTotal.setBounds(60, 7, 116, 21);
		panel.add(textIncomeTotal);
		textIncomeTotal.setColumns(10);
		
		textLack = new JTextField();
		textLack.setHorizontalAlignment(SwingConstants.RIGHT);
		textLack.setBounds(60, 38, 116, 21);
		panel.add(textLack);
		textLack.setColumns(10);
		
		textCostTotal = new JTextField();
		textCostTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textCostTotal.setBounds(259, 9, 116, 21);
		panel.add(textCostTotal);
		textCostTotal.setColumns(10);
		
		textStock = new JTextField();
		textStock.setHorizontalAlignment(SwingConstants.RIGHT);
		textStock.setBounds(259, 39, 116, 21);
		panel.add(textStock);
		textStock.setColumns(10);
		
		textHouse = new JTextField();
		textHouse.setHorizontalAlignment(SwingConstants.RIGHT);
		textHouse.setBounds(259, 69, 116, 21);
		panel.add(textHouse);
		textHouse.setColumns(10);
		
		textDeposit = new JTextField();
		textDeposit.setHorizontalAlignment(SwingConstants.RIGHT);
		textDeposit.setBounds(259, 99, 116, 21);
		panel.add(textDeposit);
		textDeposit.setColumns(10);
		
		textKB = new JTextField();
		textKB.setHorizontalAlignment(SwingConstants.RIGHT);
		textKB.setBounds(259, 129, 116, 21);
		panel.add(textKB);
		textKB.setColumns(10);
		
		textHana = new JTextField();
		textHana.setHorizontalAlignment(SwingConstants.RIGHT);
		textHana.setBounds(259, 159, 116, 21);
		panel.add(textHana);
		textHana.setColumns(10);
		
		textCash = new JTextField();
		textCash.setHorizontalAlignment(SwingConstants.RIGHT);
		textCash.setBounds(259, 189, 116, 21);
		panel.add(textCash);
		textCash.setColumns(10);
		
		textCostSum = new JTextField();
		textCostSum.setHorizontalAlignment(SwingConstants.RIGHT);
		textCostSum.setBounds(259, 219, 116, 21);
		panel.add(textCostSum);
		textCostSum.setColumns(10);
		
		textIncomeSum = new JTextField();
		textIncomeSum.setHorizontalAlignment(SwingConstants.RIGHT);
		textIncomeSum.setBounds(60, 219, 116, 21);
		panel.add(textIncomeSum);
		textIncomeSum.setColumns(10);
		
		textBalance = new JTextField();
		textBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		textBalance.setBounds(60, 159, 116, 21);
		panel.add(textBalance);
		textBalance.setColumns(10);
		
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
		for (int i=1; i<=3; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableRight);
		}
		setVisible(true);
		
		model.setNumRows(0);		//테이블 초기화
		select();					//조회 DB 실행
		TextBoxSelect();			//텍스트 박스 초기화 및 값 넣기 
		
		//조회 버튼 클릭 이벤트
		btnSearch.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setNumRows(0);//테이블 초기화
				select();			//조회 DB 실행
				TextBoxSelect();	//텍스트 박스 초기화 및 값 넣기 
			}
		});	
		
		//저장 버튼 클릭 이벤트
		btnUpdate.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//텍스트 필들의 값을 가져온다.	
				String getKB111 = textKB.getText();
				String getLack111 = textLack.getText();
				String getDeposit111 = textDeposit.getText();
				String getStock111 = textStock.getText();
				String getHouse111 = textHouse.getText();
				String getHana111 = textHana.getText();
				String getCash111 = textCash.getText();
				
				//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String getKB = getKB111.replaceAll(",", "");
				String getLack = getLack111.replaceAll(",", "");
				String getDeposit = getDeposit111.replaceAll(",", "");
				String getStock = getStock111.replaceAll(",", "");
				String getHouse = getHouse111.replaceAll(",", "");
				String getHana = getHana111.replaceAll(",", "");
				String getCash = getCash111.replaceAll(",", "");				
				
				//구한 값들은 배열에 담아서 보낸다.
				String[] getArray = {getKB, getLack, getDeposit, getStock, getHouse, getHana, getCash};
				
				//DB실행
				ZB02DaoBsYear.updateSummary(getArray);
			}
		});		
		
	}

	private void select() {
		incomeTotal = 0;
		costTotal = 0;
		try {
			List<Map<String, Object>> list1 = ZB02DaoBsYear.selectPersonal();
			List<Map<String, Object>> list2 = ZB02DaoBsYear.selectBusiness();
			List<Map<String, Object>> list3 = ZB02DaoBsYear.selectSummary();
			
			if (list1.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				Map<String, Object> map2 = list2.get(i);
	        	model.addRow(new Object[] {
	        			map1.get("personalDate"),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessIncome").toString()) + Integer.parseInt(map1.get("personalIncome").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessCost").toString()) + Integer.parseInt(map2.get("businessAsset").toString()) + Integer.parseInt(map1.get("personalCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map2.get("businessIncome").toString()) + Integer.parseInt(map1.get("personalIncome").toString()) - Integer.parseInt(map2.get("businessCost").toString()) - Integer.parseInt(map2.get("businessAsset").toString()) - Integer.parseInt(map1.get("personalCost").toString())))
	        	});
	        	//총수입
	        	incomeTotal += Integer.parseInt(map2.get("businessIncome").toString()) + Integer.parseInt(map1.get("personalIncome").toString());
	        	//총지출
	        	costTotal += Integer.parseInt(map2.get("businessCost").toString()) + Integer.parseInt(map2.get("businessAsset").toString()) + Integer.parseInt(map1.get("personalCost").toString());
			}
			//DB등록 순서 - 국민(0), 부족(1), 예금(2), 주식(3), 청약(4), 하나(5), 현금(6)
			for (int i = 0; i < list3.size(); i++) {
				Map<String, Object> map3 = list3.get(i);
				amountTotal[i] = Integer.parseInt(map3.get("bsAmount").toString());
			}						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void TextBoxSelect() {
		//텍스트 박스 초기화
		textIncomeTotal.setText("");
		textLack.setText("");
		textIncomeSum.setText("");
		textCostTotal.setText("");
		textKB.setText("");
		textDeposit.setText("");
		textStock.setText("");
		textHouse.setText("");
		textHana.setText("");
		textCash.setText("");
		textCostSum.setText("");
		textBalance.setText("");
		
		//텍스트 박스에 값 넣기:  DB등록 순서 - 국민(0), 부족(1), 예금(2), 주식(3), 청약(4), 하나(5), 현금(6)
		//차변항목
		textIncomeTotal.setText(String.format("%,d", incomeTotal));		//총수입
		textLack.setText(String.format("%,d", amountTotal[1]));			//부족분
		int sumIncome = incomeTotal + amountTotal[1];					//수입합
		textIncomeSum.setText(String.format("%,d", sumIncome));
		
		//대변항목
		textCostTotal.setText(String.format("%,d", costTotal));			//총지출
		textKB.setText(String.format("%,d", amountTotal[0]));			//국민
		textDeposit.setText(String.format("%,d", amountTotal[2]));		//예금
		textStock.setText(String.format("%,d", amountTotal[3]));		//주식
		textHouse.setText(String.format("%,d", amountTotal[4]));		//청약
		textHana.setText(String.format("%,d", amountTotal[5]));			//하나
		textCash.setText(String.format("%,d", amountTotal[6]));			//현금
		int sumCost = costTotal + amountTotal[0] + amountTotal[2] + amountTotal[3] + amountTotal[4] + amountTotal[5] + amountTotal[6];	//지출합
		textCostSum.setText(String.format("%,d", sumCost));
		
		//BS 균형 확인
		textBalance.setText(String.format("%,d", sumIncome - sumCost));		
	}
}