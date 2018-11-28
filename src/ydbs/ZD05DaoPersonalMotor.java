package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZD05DaoPersonalMotor {
	
	private ZD05DaoPersonalMotor() {
		
	}
	
	private static Statement st = null;
	private static ResultSet rs = null;

	/*
	 * ****************************************
	 * SELECT
	 * 	****************************************
	*/
	protected static List<Map<String, Object>> tableSelect(String queryTypeCategory, String queryTypeDate, String aYear, String aMonth) throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String queryRun = null;
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "연비") {    			
    			queryRun = "SELECT personalMotorDate, personalMotorGasolineCost, personalMotorGasolineVolume, ROUND(personalMotorGasolineCost * personalMotorGasolineVolume)AS personalMotorCostSum, personalMotorDistance, personalMotorId FROM personalmotor WHERE personalMotorDeleteFlag = 'Y' ORDER BY personalMotorId ASC;";
    		}
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "오일") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilDistance, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilCategory = '오일' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilId ASC;";
    		}
    		if (queryTypeDate == "dateAll" && queryTypeCategory == "기타") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilCategory = '기타' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilDate ASC, personalMotorOilSection ASC;";
    		}
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "연비") {    			
    			queryRun = "SELECT personalMotorDate, personalMotorGasolineCost, personalMotorGasolineVolume, ROUND(personalMotorGasolineCost * personalMotorGasolineVolume)AS personalMotorCostSum, personalMotorDistance, personalMotorId FROM personalmotor WHERE personalMotorDate LIKE '"+aYear+"%' AND personalMotorDeleteFlag = 'Y' ORDER BY personalMotorId ASC;";
    		}
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "오일") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilDistance, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilDate LIKE '"+aYear+"%' AND personalMotorOilCategory = '오일' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilId ASC;";
    		}
    		if (queryTypeDate == "dateYear" && queryTypeCategory == "기타") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilDate LIKE '"+aYear+"%' AND personalMotorOilCategory = '기타' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilDate ASC, personalMotorOilSection ASC;";
    		}
    		//-------------------------000-----------------------------
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "연비") {    			
    			queryRun = "SELECT personalMotorDate, personalMotorGasolineCost, personalMotorGasolineVolume, ROUND(personalMotorGasolineCost * personalMotorGasolineVolume)AS personalMotorCostSum, personalMotorDistance, personalMotorId FROM personalmotor WHERE personalMotorDate LIKE '"+aYear+"-"+aMonth+"%' AND personalMotorDeleteFlag = 'Y' ORDER BY personalMotorId ASC;";
    		}
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "오일") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilDistance, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilDate LIKE '"+aYear+"-"+aMonth+"%' AND personalMotorOilCategory = '오일' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilId ASC;";
    		}
    		if (queryTypeDate == "dateMonth" && queryTypeCategory == "기타") {
    			queryRun = "SELECT personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilId FROM personalmotoroil WHERE personalMotorOilDate LIKE '"+aYear+"-"+aMonth+"%' AND personalMotorOilCategory = '기타' AND personalMotorOilDeleteFlag = 'Y' ORDER BY personalMotorOilDate ASC, personalMotorOilSection ASC;";
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
	
	/*
	 * ****************************************
	 * INSERT
	 * ****************************************
	*/
	public static void tableInsert(String queryTypeCategory, String[] getArray) {
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			
			String queryRun = null;
			
    		if (queryTypeCategory == "연비") {    			
    			queryRun = "INSERT INTO personalmotor (personalMotorDate, personalMotorGasolineCost, personalMotorGasolineVolume, personalMotorDistance, personalMotorDeleteFlag) VALUES ('"+getArray[0]+"', "+getArray[1]+", "+getArray[2]+", "+getArray[3]+", 'Y');";
    		}
    		if (queryTypeCategory == "오일") {
    			queryRun = "INSERT INTO personalmotoroil (personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilDistance, personalMotorOilDeleteFlag) VALUES ('"+getArray[0]+"', '오일', '"+getArray[1]+"', '"+getArray[2]+"', '"+getArray[3]+"', "+getArray[4]+", "+getArray[5]+", 'Y');";
    		}
    		if (queryTypeCategory == "기타") {
    			queryRun = "INSERT INTO personalmotoroil (personalMotorOilDate, personalMotorOilCategory, personalMotorOilSection, personalMotorOilDetails, personalMotorOilLocation, personalMotorOilCost, personalMotorOilDeleteFlag) VALUES ('"+getArray[0]+"', '기타', '"+getArray[1]+"', '"+getArray[2]+"', '"+getArray[3]+"', "+getArray[4]+", 'Y');";
    		}
			st.executeUpdate(queryRun);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ****************************************
	 * UPDATE
	 * 	****************************************
	*/
	public static void tableUpdate(String queryTypeCategory, String[] getArray) {
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			
			String queryRun = null;
			
    		if (queryTypeCategory == "연비") {    			
    			queryRun = "UPDATE personalmotor SET personalMotorDate = '"+getArray[0]+"', personalMotorGasolineCost = "+getArray[1]+", personalMotorGasolineVolume = "+getArray[2]+", personalMotorDistance = "+getArray[3]+" WHERE personalMotorId = '"+getArray[4]+"';";
    		}
    		if (queryTypeCategory == "오일") {
    			queryRun = "";
    		}
    		if (queryTypeCategory == "기타") {
    			queryRun = "";
    		}
			st.executeUpdate(queryRun);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ****************************************
	 * DELETE
	 * 실제로 삭제하지는 않고 update를 통해서
	 * deleteFlag를 Y에서 N으로 변경만 한다.
	 * 	****************************************
	*/
	public static void tableDelete(int getId) {
		try {
			String query = "UPDATE personalmotor SET personalMotorDeleteFlag = 'N' WHERE personalMotorId = "+getId+";";
	       	st = ZA01DaoConnection.getConnection().createStatement();
			st.executeUpdate(query);
			st.close();			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ****************************************
	 * 장부에 동일하게 기입
	 * 	****************************************
	*/
	public static void personalaccountbookInsert(String[] getArray) {
		try {
			String query = "INSERT INTO personalaccountbook (personalAccountBookDate, personalAccountBookCategory, personalAccountBookSection, personalAccountBookDetails, personalAccountBookCost, personalAccountBookDeleteFlag) VALUES ( '"+getArray[0]+"', '"+getArray[1]+"', '"+getArray[2]+"', '"+getArray[3]+"', '"+getArray[4]+"', 'Y' );";
	       	st = ZA01DaoConnection.getConnection().createStatement();
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}