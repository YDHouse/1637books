package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZD04DaoPersonalWishList {
	
	private ZD04DaoPersonalWishList() {
		
	}
	
	private static Statement st = null;
	private static ResultSet rs = null;

	/*
	 * ****************************************
	 * SELECT
	 * 	****************************************
	*/
	protected static List<Map<String, Object>> tableSelect() throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String query =  "SELECT personalWishCategory, personalWishSection, personalWishDetail, personalWishCost, personalWishCostDelivery, personalWishCostDiscount, (personalWishCost+personalWishCostDelivery-personalWishCostDiscount) AS personalWishCostSum, personalWishDateBuy, personalWishDateReceive, personalWishRemarks, personalWishId, personalWishDeleteFlag FROM personalwish WHERE personalWishDeleteFlag = 'Y' ORDER BY personalWishCategory ASC, personalWishSection ASC, personalWishDetail ASC;";
	       	st = ZA01DaoConnection.getConnection().createStatement();
	       	rs = st.executeQuery(query);
	
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
	 * 	****************************************
	*/
	public static void tableInsert(String[] getArray) {
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			String query = "INSERT INTO personalwish (personalWishCategory, personalWishSection, personalWishDetail, personalWishCost, personalWishCostDelivery, personalWishCostDiscount, personalWishDateBuy, personalWishDateReceive, personalWishRemarks, personalWishDeleteFlag) VALUES ( '"+getArray[0]+"', '"+getArray[1]+"', '"+getArray[2]+"', "+getArray[3]+", "+getArray[4]+", "+getArray[5]+", '"+getArray[6]+"', '"+getArray[7]+"', '"+getArray[8]+"', 'Y' );";
			st.executeUpdate(query);
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
	public static void tableUpdate(String[] getArray) {
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			String query = "UPDATE personalwish SET personalWishCategory = '"+getArray[0]+"', personalWishSection = '"+getArray[1]+"', personalWishDetail = '"+getArray[2]+"', personalWishCost = "+getArray[3]+", personalWishCostDelivery = "+getArray[4]+", personalWishCostDiscount = "+getArray[5]+", personalWishDateBuy = '"+getArray[6]+"', personalWishDateReceive = '"+getArray[7]+"', personalWishRemarks = '"+getArray[8]+"' WHERE personalWishId = '"+getArray[9]+"';";
			st.executeUpdate(query);
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
			String query = "UPDATE personalwish SET personalWishDeleteFlag = 'N' WHERE personalWishId = '"+getId+"'";
	       	st = ZA01DaoConnection.getConnection().createStatement();
			st.executeUpdate(query);
			st.close();			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}