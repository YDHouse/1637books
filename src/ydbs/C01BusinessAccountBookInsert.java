package ydbs;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;


public class C01BusinessAccountBookInsert extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String getAction;								//선택된 라디오버튼의 값 수입, 비용, 고정자산 중 하나가 들어간다
	private JComboBox<String> comboSection;	//거래구분


	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					C01BusinessAccountBookInsert frame = new C01BusinessAccountBookInsert();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public C01BusinessAccountBookInsert() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1200, 100, 284, 455);
		setTitle("장부기입");
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//라디오 버튼
		JRadioButton rb1 = new JRadioButton("수입");
		rb1.setBounds(12, 12, 64, 23);
		rb1.addActionListener(this);
		
		JRadioButton rb2 = new JRadioButton("비용");
		rb2.setBounds(80, 12, 55, 23);
		rb2.addActionListener(this);
		
		JRadioButton rb3 = new JRadioButton("고정자산");
		rb3.setBounds(139, 12, 117, 23);
		rb3.addActionListener(this);
		
		//라디오 버튼의 그룹화
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rb1);
		btnGroup.add(rb2);
		btnGroup.add(rb3);
		
		//패널에 등록
		contentPane.setLayout(null);
		contentPane.add(rb1);
		contentPane.add(rb2);
		contentPane.add(rb3);
		
		//라벨
		JLabel lblNewLabel = new JLabel("날짜");
		lblNewLabel.setBounds(22, 55, 54, 15);
		contentPane.add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("거래 구분");
		lblNewLabel_1.setBounds(22, 99, 75, 15);
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel_2 = new JLabel("거래 내용");
		lblNewLabel_2.setBounds(22, 143, 75, 15);
		contentPane.add(lblNewLabel_2);
		JLabel lblNewLabel_3 = new JLabel("거래처 상호");
		lblNewLabel_3.setBounds(22, 187, 93, 15);
		contentPane.add(lblNewLabel_3);
		JLabel lblNewLabel_4 = new JLabel("비고");
		lblNewLabel_4.setBounds(22, 231, 54, 15);
		contentPane.add(lblNewLabel_4);
		JLabel lblNewLabel_5 = new JLabel("금액");
		lblNewLabel_5.setBounds(22, 275, 54, 15);
		contentPane.add(lblNewLabel_5);
		JLabel lblNewLabel_6 = new JLabel("부가세");
		lblNewLabel_6.setBounds(22, 319, 54, 15);
		contentPane.add(lblNewLabel_6);
		
		//스피너 (날짜: 자동으로 오늘 날짜 입력)
		SpinnerDateModel model = new SpinnerDateModel();
		JSpinner spinnerDate = new JSpinner(model);
		//yyyy-MM-dd에서 M은 꼭 대문자로 입력
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerDate, "yyyy-MM-dd");
		spinnerDate.setEditor(dateEditor);
		spinnerDate.setBounds(127, 52, 129, 21);
		contentPane.add(spinnerDate);
		
		//콤보박스 (거래구분)
		comboSection = new JComboBox<String>();
		comboSection.setEditable(true);
		comboSection.setBounds(127, 96, 129, 21);
		contentPane.add(comboSection);
		
		//콤보박스 (거래내용)
		String[] detailsArray = {"기타", "매출(카드)", "매출(현금)", "커피재료", "우유등", "전기요금", "수도요금", "통신요금(인터넷)", "통신요금(휴대폰)"};
		JComboBox<String> comboDetails = new JComboBox<String>(detailsArray);
		comboDetails.setEditable(true);
		comboDetails.setBounds(127, 140, 129, 21);
		contentPane.add(comboDetails);
		
		//콤보박스 (거래처 상호) : DB연동해서 값 가져오기
		JComboBox<String>comboClient = new JComboBox<String>();
		comboClient.setEditable(true);
		comboClient.setBounds(127, 183, 129, 21);
		contentPane.add(comboClient);
		
		//콤보박스 (비고)
		String[] remarksArray = {"세금계산서", "계산서", "카드전표", "현금영수증", "간이영수증", "기타"};
		JComboBox<String>comboRemarks = new JComboBox<String>(remarksArray);
		comboRemarks.setEditable(true);
		comboRemarks.setBounds(127, 228, 129, 21);
		contentPane.add(comboRemarks);
		
		//금액
		NumberFormat numberFormatCost = NumberFormat.getNumberInstance();			//천단위 콤마를 찍기 위해 숫자 포맷으로 변경
		JFormattedTextField formattedCost = new JFormattedTextField(numberFormatCost);
		formattedCost.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedCost.setColumns(10);
		formattedCost.setBounds(127, 273, 129, 21);
		contentPane.add(formattedCost);
		
		//부가세
		NumberFormat numberFormatVAT = NumberFormat.getNumberInstance();				//천단위 콤마를 찍기 위해 숫자 포맷으로 변경
		JFormattedTextField formattedVAT = new JFormattedTextField(numberFormatVAT);
		formattedVAT.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedVAT.setColumns(10);
		formattedVAT.setBounds(127, 317, 129, 21);
		contentPane.add(formattedVAT);
		
		//버튼 (입력)
		JButton btnInsert = new JButton("입력");
		btnInsert.setBounds(32, 369, 97, 23);
		contentPane.add(btnInsert);
		
		//버튼 (종료)
		JButton btnExit = new JButton("종료");
		btnExit.setBounds(146, 369, 97, 23);
		contentPane.add(btnExit);
		
		
		
		/*
		 *
		 * 이벤트 리스너
		 *		
		*/
		
		//금액 입력 후 엔터가 눌러지면 이벤트 발생
		formattedCost.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//입력된 금액을 가지고 와서 부가세를 구한다
					int costValue = Integer.parseInt(formattedCost.getText());
					int costVAT = (int) (costValue *0.1);
					//부가세 필드에 값을 입력한다
					formattedVAT.setValue(costVAT);
				}
			}
		});
		
		//입력 버튼 클릭 이벤트 (DB insert 쿼리 실행)
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
				String getDate = formatDate.format(spinnerDate.getValue());
				String getCategory = getAction;
				String getSection = comboSection.getSelectedItem().toString();
				String getDetails = comboDetails.getSelectedItem().toString();
				String getCustomer = comboClient.getSelectedItem().toString();
				String getRemarks = comboRemarks.getSelectedItem().toString();
				String getCost_aaa = formattedCost.getText();
				String getCostVAT_aaa = formattedVAT.getText();
				String getCost = getCost_aaa.replaceAll(",", "");				//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String getCostVAT = getCostVAT_aaa.replaceAll(",", "");	//DB에 넣을 때는 콤마가 있으면 안되기 때문에 미리 제거한다.
				String[] insertArray = {getDate, getCategory, getSection, getDetails, getCustomer, getRemarks, getCost, getCostVAT};
				ZC01DaoBusinessAccountBook.booksInsert(insertArray);
			}
		});

		//종료 버튼 클릭 이벤트
		btnExit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();	//현재 프레임만 종료, 부모 프레임은 유지
			}
		});

	}

	//라디오버튼 이벤트 리스너
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String[] selectionIncomeArray = {"매출", "기타(수입)"};
		String[] selectionCostArray = {"재료매입", "급료", "재세공과금", "임차료", "지급이자", "접대비", "기부금", "감가상각비", "차량유지비", "지급수수료", "소모품비", "복리후생비", "운반비", "광고선전비", "여비교통비", "기타(비용)"};
		String[] selectionAssetArray = {"건물및구축물", "차량운반구,비품", "기계장치", "기타(고정)"};
		
		//선택된 라디오버튼의 값 가져오기
		getAction = e.getActionCommand();
		
		if (getAction == "수입") {
			comboSection.setModel(new DefaultComboBoxModel<>(selectionIncomeArray));
		} else if (getAction == "비용") {
			comboSection.setModel(new DefaultComboBoxModel<>(selectionCostArray));
		} else if (getAction == "고정자산") {
			comboSection.setModel(new DefaultComboBoxModel<>(selectionAssetArray));
		} else {
			System.out.println("라디오버튼 (거래구분 콤보박스 선택을 위한) 에러");
		}
	}
}