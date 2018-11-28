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
import javax.swing.JButton;

public class D04PersonalWishList extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//final CustomCellRenderer renderer = new CustomCellRenderer();	//테이블 색상 변경
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"구분", "종류", "품명", "가격", "배송비", "할인", "합계", "구입일", "수령일", "비고", "id"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);

	public D04PersonalWishList() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(10, 9, 88, 23);
		add(btnSearch);
		
		JButton btnAddRow = new JButton("행 추가");
		btnAddRow.setBounds(110, 9, 88, 23);
		add(btnAddRow);
		
		JButton btnSave = new JButton("저장");
		btnSave.setBounds(210, 9, 88, 23);
		add(btnSave);
		
		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(310, 9, 88, 23);
		add(btnDelete);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		/*JTable table = new JTable(model) {
		@Override
		   public TableCellRenderer getCellRenderer(int row, int column) {
			   return renderer;
		   }
		};*/
		JTable table = new JTable(model);
		table.getColumn("구분").setPreferredWidth(80);
		table.getColumn("종류").setPreferredWidth(200);
		table.getColumn("품명").setPreferredWidth(1100);
		table.getColumn("가격").setPreferredWidth(120);
		table.getColumn("배송비").setPreferredWidth(120);
		table.getColumn("할인").setPreferredWidth(120);
		table.getColumn("합계").setPreferredWidth(120);
		table.getColumn("구입일").setPreferredWidth(150);
		table.getColumn("수령일").setPreferredWidth(150);
		table.getColumn("비고").setPreferredWidth(300);
		table.getColumn("id").setWidth(0);						//id 컬럼은 보이면 안되기 때문에 이렇게 설정을 한다.
		table.getColumn("id").setMinWidth(0);			
		table.getColumn("id").setMaxWidth(0);					
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
		for (int i=0; i<=1; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}
		for (int i=7; i<=8; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableCenter);
		}
		//오른쪽 정렬 적용 컬럼
		for (int i=3; i<=6; i++) {
			tableColumn.getColumn(i).setCellRenderer(tableRight);
		}
		//참고:  (2번째 "품명" 컬럼은 왼쪽 정렬이기 때문에 지정하지 않는다)
		
		setVisible(true);

		//테이블 초기화
		model.setNumRows(0);
		select();
		
		//조회 버튼 클릭 이벤트
		btnSearch.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
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
				modelAddRow.addRow(new String[] {"", "", "", "", "", "", "", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
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
				
				//"구분", "종류", "품명", "가격", "배송비", "할인", "구입일", "수령일", "비고", "id", "flag" 순으로 각각의 변수에 저장
				String getCategory = (String) modelSave.getValueAt(rowIndex,  0);
				String getSection = (String) modelSave.getValueAt(rowIndex,  1);
				String getDetails = (String) modelSave.getValueAt(rowIndex,  2);
				String getCostaaa = (String) modelSave.getValueAt(rowIndex,  3);
				String getCostDeliveryAaa = (String) modelSave.getValueAt(rowIndex,  4);
				String getCostDiscountAaa = (String) modelSave.getValueAt(rowIndex, 5);
				String getCost = getCostaaa.replaceAll(",", "");											//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String getCostDelivery = getCostDeliveryAaa.replaceAll(",", "");					//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String getCostDiscount = getCostDiscountAaa.replaceAll(",", "");					//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String getDateBuy = (String) modelSave.getValueAt(rowIndex, 7);
				String getDateReceive = (String) modelSave.getValueAt(rowIndex, 8);
				String getRemarks = (String) modelSave.getValueAt(rowIndex, 9);
				String getId = (String) modelSave.getValueAt(rowIndex, 10);
				
				//구한 값들은 배열에 담아서 보낸다.
				String[] getArray = {getCategory, getSection, getDetails, getCost, getCostDelivery, getCostDiscount, getDateBuy, getDateReceive, getRemarks, getId};
				
				//id의 값이 null이면 "입력" 쿼리를 실행
				if(getId == null || getId.length() ==0) {
					ZD04DaoPersonalWishList.tableInsert(getArray);
				}
				
				//id의 값이 null이 아니라면 "수정" 쿼리를 실행
				if (getId != null && getId.length() !=0) {
					ZD04DaoPersonalWishList.tableUpdate(getArray);
				}
				
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
				int getId = Integer.parseInt((String) modelSave.getValueAt(rowIndex, 10));
				
				//쿼리 실행
				ZD04DaoPersonalWishList.tableDelete(getId);
				
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
			List<Map<String, Object>> list = ZD04DaoPersonalWishList.tableSelect();
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map.get("personalWishCategory"),
	        			map.get("personalWishSection"),
	        			map.get("personalWishDetail"),
	        			String.format("%,d", (Integer.parseInt(map.get("personalWishCost").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("personalWishCostDelivery").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("personalWishCostDiscount").toString()))),
	        			String.format("%,d", (Integer.parseInt(map.get("personalWishCostSum").toString()))),
	        			map.get("personalWishDateBuy"),
	        			map.get("personalWishDateReceive"),
	        			map.get("personalWishRemarks"),
	        			map.get("personalWishId")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}