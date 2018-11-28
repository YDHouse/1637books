package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZD01DaoPersonalAccountBook {
	
	private ZD01DaoPersonalAccountBook() {
		
	}
	
	private static Statement st = null;
	private static ResultSet rs = null;

	//j테이블 조회 select
	protected static List<Map<String, Object>> tableSelect(String queryTypeDate, String queryTypeCategory, String accountBookYear, String accountBookMonth, String accountBookCategory, String accountBookSection) throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String queryRun = "";
    		
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "typeAll") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "typeCategory") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "typeSection") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "typeSectionOnly") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "typeAll") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"%' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "typeCategory") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"%' AND personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "typeSection") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"%' AND personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "typeSectionOnly") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"%' AND personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "typeAll") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"-"+accountBookMonth+"%' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "typeCategory") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"-"+accountBookMonth+"%' AND personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "typeSection") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"-"+accountBookMonth+"%' AND personalAccountBookCategory = '"+accountBookCategory+"' AND personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "typeSectionOnly") {
    			queryRun = "select personalAccountBookId, personalAccountBookDeleteFlag, personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost  FROM personalaccountbook WHERE personalAccountBookDate LIKE '"+accountBookYear+"-"+accountBookMonth+"%' AND personalAccountBookSection = '"+accountBookSection+"' AND personalAccountBookDeleteFlag = 'Y' ORDER BY personalAccountBookDate asc;";
    		}
	       	
	       	st = ZA01DaoConnection.getConnection().createStatement();
	       	rs = st.executeQuery(queryRun);
	
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
	
	//테이블 입력 & 수정
	public static void tableInsertUpdate(String[] insertArray) {
		try {
			int getCost = Integer.parseInt(insertArray[4].replaceAll(",", ""));
			
			String insertQuery = null;
			
			if (insertArray[7] == "입력") {
				insertQuery = "INSERT INTO personalaccountbook (personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost, personalAccountBookDeleteFlag) VALUES ( '"+insertArray[0]+"', '"+insertArray[1]+"', '"+insertArray[2]+"', '"+insertArray[3]+"', '"+getCost+"', 'Y' );";
			}else if (insertArray[7] == "수정") {
				insertQuery = "UPDATE personalaccountbook SET personalAccountBookDate = '"+insertArray[0]+"', personalAccountBookCategory = '"+insertArray[1]+"', personalAccountBookSection = '"+insertArray[2]+"', personalAccountBookDetails = '"+insertArray[3]+"', personalAccountBookCost = '"+getCost+"', personalAccountBookDeleteFlag = '"+insertArray[6]+"' WHERE personalAccountBookId = '"+insertArray[5]+"'";
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

	//테이블에서 선택된 1행 데이터 삭제
	public static void tableDelete(String getId) {
		try {
			int personalAccountBookId = Integer.parseInt(getId);
			
			String deleteQuery = "UPDATE personalaccountbook SET personalAccountBookDeleteFlag = 'N' WHERE personalAccountBookId = '"+personalAccountBookId+"'";
					
	       	st = ZA01DaoConnection.getConnection().createStatement();
			st.executeUpdate(deleteQuery);
			st.close();			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}