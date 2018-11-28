package ydbs;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class A01YdbsMain extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cards = new CardLayout();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A01YdbsMain frame = new A01YdbsMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public A01YdbsMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 1100, 620);
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(cards);
		
		setTitle("YD'favorite");
		
		//글꼴 일괄 적용
		setUIFont (new javax.swing.plaf.FontUIResource("Gulim", Font.PLAIN, 12));
		
		//메뉴바 (파일)
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("파일");
		menuBar.add(mnFile);
		JMenuItem mntmLogin = new JMenuItem("로그인");
		mnFile.add(mntmLogin);
		JMenuItem mntmExit = new JMenuItem("종료");
		mnFile.add(mntmExit);
		//메뉴바 (공통)
		JMenu mnSynthesis = new JMenu("공통");
		menuBar.add(mnSynthesis);
		JMenuItem mntmSynthesisBSmonth = new JMenuItem("BS월간");
		mnSynthesis.add(mntmSynthesisBSmonth);
		JMenuItem mntmSynthesisBSyear = new JMenuItem("BS연간");
		mnSynthesis.add(mntmSynthesisBSyear);		
		//메뉴바 (사업)
		JMenu mnBusiness = new JMenu("사업");
		menuBar.add(mnBusiness);
		JMenuItem mntmBusinessAccountBook = new JMenuItem("장부기입");
		mnBusiness.add(mntmBusinessAccountBook);
		JMenuItem mntmBusinessanalysis = new JMenuItem("장부분석");
		mnBusiness.add(mntmBusinessanalysis);
		JMenuItem mntmBusinessDepreciation = new JMenuItem("감가상각");
		mnBusiness.add(mntmBusinessDepreciation);
		JMenuItem mntmBusinessClient = new JMenuItem("거래처");
		mnBusiness.add(mntmBusinessClient);
		JMenuItem mntmBusinessDocumentForm1 = new JMenuItem("양식1");
		mnBusiness.add(mntmBusinessDocumentForm1);
		JMenuItem mntmBusinessDocumentForm2 = new JMenuItem("양식2");
		mnBusiness.add(mntmBusinessDocumentForm2);
		//메뉴바 (개인)
		JMenu mnPersonal = new JMenu("개인");
		menuBar.add(mnPersonal);
		JMenuItem mntmPersonalAccountBook = new JMenuItem("장부기입");
		mnPersonal.add(mntmPersonalAccountBook);
		JMenuItem mntmPersonalDaily = new JMenuItem("데일리");
		mnPersonal.add(mntmPersonalDaily);
		JMenuItem mntmPersonalMember = new JMenuItem("회원");
		mnPersonal.add(mntmPersonalMember);
		JMenuItem mntmPersonalWishList = new JMenuItem("위시리스트");
		mnPersonal.add(mntmPersonalWishList);
		JMenuItem mntmPersonalMotor = new JMenuItem("스쿠터");
		mnPersonal.add(mntmPersonalMotor);
		JMenuItem mntmPersonalLabor = new JMenuItem("근무");
		mnPersonal.add(mntmPersonalLabor);
		JMenuItem mntmPersonalHouse = new JMenuItem("주거");
		mnPersonal.add(mntmPersonalHouse);
		//메뉴바 (커피)
		JMenu mnCoffee = new JMenu("커피");
		menuBar.add(mnCoffee);
		JMenuItem mntmCoffeeMaking = new JMenuItem("제조방법");
		mnCoffee.add(mntmCoffeeMaking);
		JMenuItem mntmCoffeePOSmenu = new JMenuItem("포스메뉴");
		mnCoffee.add(mntmCoffeePOSmenu);
		JMenuItem mntmCoffeeInfrastructure = new JMenuItem("설비구축");
		mnCoffee.add(mntmCoffeeInfrastructure);
		JMenuItem mntmCoffeeMeterials = new JMenuItem("자재");
		mnCoffee.add(mntmCoffeeMeterials);
		//메뉴바 (지식)
		JMenu mnKnowledge = new JMenu("지식");
		menuBar.add(mnKnowledge);
		JMenuItem mntmKnowledgeTaxAccounting = new JMenuItem("세무회계");
		mnKnowledge.add(mntmKnowledgeTaxAccounting);
		JMenuItem mntmKnowledgeETC = new JMenuItem("기타");
		mnKnowledge.add(mntmKnowledgeETC);
		
		//처음 실행 시 카드 레이아웃 특성 상 임의의 레이아웃을 하나 띄워나야지 메뉴에서 항목을 선택했을 때 한 번에 해당 카드 레이아웃이 보인다. (아니면 첫번째 선택하는 것은 보이지 않고 두번째 부터 보이게 된다.)
		getContentPane().add("BusinessAccountBookClient", new C04BusinessAccountBookClient());	//거래처
		//A02YdbsMainLogin.main();
		
		
		/* 
		 * *****************************************************
		 * 
		 * 메뉴바 클릭 이벤트
		 * 
		 * ***************************************************** 		
		*/
		//파일-로그인
		mntmLogin.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				A02YdbsMainLogin.main();
			}
		});
		//파일-종료
		mntmExit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ZA01DaoConnection.close();
				System.exit(0);
			}
		});
		//공통 - BS월간
		mntmSynthesisBSmonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("SynthesisBsMonth", new B01SynthesisBsMonth());							//BS월간
				cards.show(getContentPane(), "SynthesisBsMonth");
			}
		});
		//공통 - BS연간
		mntmSynthesisBSyear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("SynthesisBsYear", new B02SynthesisBsYear());								//BS연간
				cards.show(getContentPane(), "SynthesisBsYear");
			}
		});	
		//사업-장부기입
		mntmBusinessAccountBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("BusinessAccountBook", new C01BusinessAccountBook());				//장부기입 (사업)
				cards.show(getContentPane(), "BusinessAccountBook");
			}
		});	
		//사업-거래처
		mntmBusinessClient.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("BusinessAccountBookClient", new C04BusinessAccountBookClient());	//거래처
				cards.show(getContentPane(), "BusinessAccountBookClient");
			}
		});
		//개인-장부기입
		mntmPersonalAccountBook.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("PersonalAccountBook", new D01PersonalAccountBook());				//장부기입 (개인)
				cards.show(getContentPane(), "PersonalAccountBook");
			}
		});
		//개인-데일리
		mntmPersonalDaily.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("PersonalDaily", new D02PersonalDaily());										//데일리
				cards.show(getContentPane(), "PersonalDaily");
			}
		});
		//개인-회원
		mntmPersonalMember.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("PersonalMember", new D03PersonalMember());								//회원
				cards.show(getContentPane(), "PersonalMember");
			}
		});
		//개인-위시리스트
		mntmPersonalWishList.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("PersonalWishList", new D04PersonalWishList());								//위시리스트
				cards.show(getContentPane(), "PersonalWishList");
			}
		});
		//개인-스쿠터
		mntmPersonalMotor.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().add("PersonalMotor", new D05PersonalMotor());									//스쿠터
				cards.show(getContentPane(), "PersonalMotor");
			}
		});
		
	}
	
	//폰트 일괄 적용
	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
	    java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof javax.swing.plaf.FontUIResource)
	            UIManager.put(key, f);
	    }
	}
}