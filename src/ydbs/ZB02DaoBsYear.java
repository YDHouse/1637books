package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZB02DaoBsYear {
	
	
	private ZB02DaoBsYear() {
		
	}
	
	//개인 수입, 지출 조회 select
	protected static List<Map<String, Object>> selectPersonal() throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String dateAll = "SELECT DATE_FORMAT(personalAccountBookDate, '%Y') AS personalDate, SUM(IF(personalAccountBookCategory='수입', personalAccountBookCost, '0')) AS 'personalIncome',	SUM(IF(personalAccountBookCategory='지출', personalAccountBookCost, '0')) AS 'personalCost' FROM personalaccountbook WHERE personalAccountBookDeleteFlag ='Y' GROUP BY personalDate ORDER BY personalDate;";
    		
	       	
	       	Statement st = ZA01DaoConnection.getConnection().createStatement();
	       	ResultSet rs = st.executeQuery(dateAll);
	
	        // ResultSet 의 MetaData를 가져온다.
	        ResultSetMetaData metaData = rs.getMetaData();
	        // ResultSet 의 Column의 갯수를 가져온다.
	        int sizeOfColumn = metaData.getColumnCount();
	        
	        Map<String, Object> map;
	        String column;
	        
	        while (rs.next())
	        {
	            // 내부에서 map을 초기화
	            map = new HashMap<String, Object>();
	            // Column의 갯수만큼 회전
	            for (int i=0; i<sizeOfColumn; i++)
	            {
	                column = metaData.getColumnName(i + 1);
	                // map에 값을 입력 map.put(columnName, columnName으로 getString)
	                map.put(column, rs.getString(column));
	            }
	            list.add(map);
	        }
	        rs.close();
	        st.close();
	        //DBConnection.getConnection().close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		return list;
	}
	
	//사업 수입,지출,자산 조회 select
	protected static List<Map<String, Object>> selectBusiness() throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String dateAll = "SELECT DATE_FORMAT(businessAccountBookDate, '%Y') AS businessDate, SUM(IF(businessAccountBookCategory='수입', businessAccountBookIncome, '0')) AS 'businessIncome', SUM(IF(businessAccountBookCategory='비용', businessAccountBookCost, '0')) AS 'businessCost', SUM(IF(businessAccountBookCategory='고정자산', businessAccountBookAsset, '0')) AS 'businessAsset' FROM businessaccountbook WHERE businessAccountBookDeleteFlag = 'Y' GROUP BY businessDate ORDER BY businessDate;";
    		
	       	Statement st = ZA01DaoConnection.getConnection().createStatement();
	       	ResultSet rs = st.executeQuery(dateAll);
	
	        // ResultSet 의 MetaData를 가져온다.
	        ResultSetMetaData metaData = rs.getMetaData();
	        // ResultSet 의 Column의 갯수를 가져온다.
	        int sizeOfColumn = metaData.getColumnCount();
	        
	        Map<String, Object> map;
	        String column;
	        
	        while (rs.next())
	        {
	            // 내부에서 map을 초기화
	            map = new HashMap<String, Object>();
	            // Column의 갯수만큼 회전
	            for (int i=0; i<sizeOfColumn; i++)
	            {
	                column = metaData.getColumnName(i + 1);
	                // map에 값을 입력 map.put(columnName, columnName으로 getString)
	                map.put(column, rs.getString(column));
	            }
	            list.add(map);
	        }
	        rs.close();
	        st.close();
	        //DBConnection.getConnection().close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		return list;
	}
	
	//BS 조회 select
	protected static List<Map<String, Object>> selectSummary() throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String dateAll = "SELECT bsAmount FROM synthesisbsyear;";
    		
	       	Statement st = ZA01DaoConnection.getConnection().createStatement();
	       	ResultSet rs = st.executeQuery(dateAll);
	
	        // ResultSet 의 MetaData를 가져온다.
	        ResultSetMetaData metaData = rs.getMetaData();
	        // ResultSet 의 Column의 갯수를 가져온다.
	        int sizeOfColumn = metaData.getColumnCount();
	        
	        Map<String, Object> map;
	        String column;
	        
	        while (rs.next())
	        {
	            // 내부에서 map을 초기화
	            map = new HashMap<String, Object>();
	            // Column의 갯수만큼 회전
	            for (int i=0; i<sizeOfColumn; i++)
	            {
	                column = metaData.getColumnName(i + 1);
	                // map에 값을 입력 map.put(columnName, columnName으로 getString)
	                map.put(column, rs.getString(column));
	            }
	            list.add(map);
	        }
	        rs.close();
	        st.close();
	        //DBConnection.getConnection().close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		return list;
	}
	
	//DB 수정 (personalDaily)
	public static void updateSummary(String[] getArray) {
		String[] bsCategory = {"국민", "부족", "예금", "주식", "청약", "하나", "현금"};
		String[] runQuery = new String[7];
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			for (int i=0; i<=6; i++) {
				runQuery[i] = "UPDATE synthesisbsyear SET bsAmount = '"+getArray[i]+"' WHERE bsCategory = '"+bsCategory[i]+"';";
				st.executeUpdate(runQuery[i]);
			}			
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}