package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZC01DaoBusinessAccountBook {
	
	private ZC01DaoBusinessAccountBook() {
		
	}
	
	private static Statement st = null;
	private static ResultSet rs = null;

	//j테이블 조회 select
	public static List<Map<String, Object>> getSelect(String queryType, String comboYear, String comboMonth) throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String dateAll = "";
    		if (queryType == "dateAll") {
    			dateAll = "select businessAccountBookId, businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks  FROM businessaccountbook WHERE businessAccountBookDeleteFlag = 'Y' ORDER BY businessAccountBookId asc;";
    		}
    		if (queryType == "dateYear") {
    			dateAll = "select businessAccountBookId, businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks  FROM businessaccountbook WHERE businessAccountBookDate LIKE '"+comboYear+"%' AND businessAccountBookDeleteFlag = 'Y' ORDER BY businessAccountBookId asc;";
    		}
    		if (queryType == "dateMonth") {
    			dateAll = "select businessAccountBookId, businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks  FROM businessaccountbook WHERE businessAccountBookDate LIKE '"+comboYear+"-"+comboMonth+"%' AND businessAccountBookDeleteFlag = 'Y' ORDER BY businessAccountBookId asc;";
    		}
	       	
	       	st = ZA01DaoConnection.getConnection().createStatement();
	       	rs = st.executeQuery(dateAll);
	
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
	
	//입력 insert
	public static void booksInsert(String[] insertArray) {
		
		try {
			int getCost = Integer.parseInt(insertArray[6]);
			int getCostVAT = Integer.parseInt(insertArray[7]);
			
			String insertQuery = null;
			
			if (insertArray[1] == "수입") {
				insertQuery = "INSERT INTO businessaccountbook (businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks, businessAccountBookDeleteFlag ) VALUES ( '"+insertArray[0]+"', '"+insertArray[1]+"', '"+insertArray[2]+"', '"+insertArray[3]+"', '"+insertArray[4]+"', '"+getCost+"', '"+getCostVAT+"', 0, 0, 0, 0, '"+insertArray[5]+"', 'Y' );";
			} else if (insertArray[1] == "비용") {
				insertQuery = "INSERT INTO businessaccountbook (businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks, businessAccountBookDeleteFlag ) VALUES ( '"+insertArray[0]+"', '"+insertArray[1]+"', '"+insertArray[2]+"', '"+insertArray[3]+"', '"+insertArray[4]+"', 0, 0, '"+getCost+"', '"+getCostVAT+"', 0, 0, '"+insertArray[5]+"', 'Y' );";
			} else if (insertArray[1] == "고정자산") {
				insertQuery = "INSERT INTO businessaccountbook (businessAccountBookDate, businessAccountBookCategory, businessAccountBookSection, businessAccountBookDetails, businessAccountBookClient, businessAccountBookIncome, businessAccountBookIncomeVat, businessAccountBookCost, businessAccountBookCostVat, businessAccountBookAsset, businessAccountBookAssetVat, businessAccountBookRemarks, businessAccountBookDeleteFlag ) VALUES ( '"+insertArray[0]+"', '"+insertArray[1]+"', '"+insertArray[2]+"', '"+insertArray[3]+"', '"+insertArray[4]+"', 0, 0, 0, 0, '"+getCost+"', '"+getCostVAT+"', '"+insertArray[5]+"', 'Y' );";
			} else {
				System.out.println("입력 에러");
			}
	       	st = ZA01DaoConnection.getConnection().createStatement();
			st.executeUpdate(insertQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

}