package ydbs;

import java.util.List;
import java.util.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class D03PersonalMember extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//id 컬럼은 숨겨져 있기 때문에 보이지 않는다. id는 DB 처리를 할 때 insert, update를 구분하기 위해 사용
	private String colmNames[] = {"구분", "이름", "내용", "비고", "id"};
	private DefaultTableModel model = new DefaultTableModel(colmNames, 0);

	public D03PersonalMember() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JButton btnSearch = new JButton("조회");
		btnSearch.setBounds(210, 9, 88, 23);
		add(btnSearch);
		
		JButton btnAddRow = new JButton("행 추가");
		btnAddRow.setBounds(310, 9, 88, 23);
		add(btnAddRow);
		
		JButton btnSave = new JButton("저장");
		btnSave.setBounds(410, 9, 88, 23);
		add(btnSave);
		
		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(510, 9, 88, 23);
		add(btnDelete);
		
		//테이블 및 스크롤바 생성 (참조: 스크롤바를 함께 생성하지 않으면 테이블 생성이 되지 않는다.)
		JTable table = new JTable(model);
		table.getColumn("id").setWidth(0);				//id 컬럼은 보이면 안되기 때문에 이렇게 설정
		table.getColumn("id").setMinWidth(0);			
		table.getColumn("id").setMaxWidth(0);
		table.getColumn("구분").setPreferredWidth(60);
		table.getColumn("이름").setPreferredWidth(240);
		table.getColumn("내용").setPreferredWidth(240);
		table.getColumn("비고").setPreferredWidth(1000);
		table.setBounds(0, 41, 1085, 520);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 41);
		scrollPane.setSize(1085,520);
		add(scrollPane);
		
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
				modelAddRow.addRow(new String[] {"", "", "", ""});	//테이블의 컬럼수 만큼 공백으로 추가한다.
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
			List<Map<String, Object>> list = ZD03DaoPersonalMember.tableSelect();
			if (list.size() == 0) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
	        	model.addRow(new Object[] {
	        			map.get("personalMemberCategory"),
	        			map.get("personalMemberName"),
	        			map.get("personalMemberDetails"),
	        			map.get("personalMemberRemarks"),
	        			map.get("personalMemberId")
	        	});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}