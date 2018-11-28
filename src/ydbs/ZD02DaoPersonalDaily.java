package ydbs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZD02DaoPersonalDaily {
	
	
	private ZD02DaoPersonalDaily() {
		
	}
	
	/*
	 * ****************************************
	 * SELECT
	 * 	****************************************
	*/
	
	//j테이블 조회 select
	protected static List<Map<String, Object>> tableSelect(String queryType, String comboYear, String comboMonth) throws Exception{
		//DB 연결
    	ZA01DaoConnection.getConnection();	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	try {
    		String dateAll = "";
    		if (queryType == "dateAll") {    			
    			dateAll = "SELECT a.personalDailyId, a.personalDailyDate, b.personalDailyRecordsDate, c.personalDailyWorkDate, a.personalDailyPlace, a.personalDailyPlay, a.personalDailyWeather, b.personalDailyRecordsBook, DATE_FORMAT(c.personalDailyWorkOnTime, '%H:%i')AS onTimes, DATE_FORMAT(c.personalDailyWorkOffTime, '%H:%i')AS offTimes, DATE_FORMAT(TIMEDIFF(c.personalDailyWorkOffTime, c.personalDailyWorkOnTime), '%H시간%i분')AS workTimes FROM personaldaily a LEFT JOIN personaldailyrecords b ON a.personalDailyDate = b.personalDailyRecordsDate LEFT JOIN personaldailywork c ON a.personalDailyDate = c.personalDailyWorkDate WHERE personalDailyDeleteFlag = 'Y' ORDER BY personalDailyDate ASC;";
    		}
    		if (queryType == "dateYear") {
    			dateAll = "SELECT a.personalDailyId, a.personalDailyDate, b.personalDailyRecordsDate, c.personalDailyWorkDate, a.personalDailyPlace, a.personalDailyPlay, a.personalDailyWeather, b.personalDailyRecordsBook, DATE_FORMAT(c.personalDailyWorkOnTime, '%H:%i')AS onTimes, DATE_FORMAT(c.personalDailyWorkOffTime, '%H:%i')AS offTimes, DATE_FORMAT(TIMEDIFF(c.personalDailyWorkOffTime, c.personalDailyWorkOnTime), '%H시간%i분')AS workTimes FROM personaldaily a LEFT JOIN personaldailyrecords b ON a.personalDailyDate = b.personalDailyRecordsDate LEFT JOIN personaldailywork c ON a.personalDailyDate = c.personalDailyWorkDate WHERE personalDailyDate LIKE '"+comboYear+"%' AND personalDailyDeleteFlag = 'Y' ORDER BY personalDailyDate ASC;";
    		}
    		if (queryType == "dateMonth") {
    			dateAll = "SELECT a.personalDailyId, a.personalDailyDate, b.personalDailyRecordsDate, c.personalDailyWorkDate, a.personalDailyPlace, a.personalDailyPlay, a.personalDailyWeather, b.personalDailyRecordsBook, DATE_FORMAT(c.personalDailyWorkOnTime, '%H:%i')AS onTimes, DATE_FORMAT(c.personalDailyWorkOffTime, '%H:%i')AS offTimes, DATE_FORMAT(TIMEDIFF(c.personalDailyWorkOffTime, c.personalDailyWorkOnTime), '%H시간%i분')AS workTimes FROM personaldaily a LEFT JOIN personaldailyrecords b ON a.personalDailyDate = b.personalDailyRecordsDate LEFT JOIN personaldailywork c ON a.personalDailyDate = c.personalDailyWorkDate WHERE personalDailyDate LIKE '"+comboYear+"-"+comboMonth+"%' AND personalDailyDeleteFlag = 'Y' ORDER BY personalDailyDate ASC;";
    		}
	       	
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
	
	/*
	 * ****************************************
	 * INSERT
	 * 	****************************************
	*/
	
	//DB 입력 (personalDaily)
	public static void insertDaily(String[] getArray) {
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			//운동 컬럼(personalDailyPlay)은 Integer 타입에 null값을 넣어야 하기 때문에
			if (getArray[2] == "" || getArray[2].length() == 0) {
				//운동 컬럼(personalDailyPlay)에 값이 입력되지 않았을 경우 자동으로 null이 입력되기 때문에 insert에서 제외를 시킨다. (포함 시킬 경우 널포인트 예외가 발생한다.)
				runQuery = "INSERT INTO personaldaily (personalDailyDate, personalDailyPlace, personalDailyWeather, personalDailyDeleteFlag) VALUES ( '"+getArray[0]+"', '"+getArray[1]+"', '"+getArray[3]+"', 'Y' );";
			} else {
				//운동 컬럼(personalDailyPlay)에 값이 입력되었을 경우에는 운동 컬럼을 포함해서 insert 한다.
				runQuery = "INSERT INTO personaldaily (personalDailyDate, personalDailyPlace, personalDailyPlay, personalDailyWeather, personalDailyDeleteFlag) VALUES ( '"+getArray[0]+"', '"+getArray[1]+"', "+getArray[2]+", '"+getArray[3]+"', 'Y' );";	
			}
			st.executeUpdate(runQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//DB 입력 (personalDailyRecords)
	public static void insertRecords(String[] getArray) {
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			runQuery = "INSERT INTO personaldailyrecords (personalDailyRecordsDate, personalDailyRecordsBook) VALUES ( '"+getArray[0]+"', '"+getArray[4]+"' );";
			st.executeUpdate(runQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//DB 입력 (personalDailyWork)
	public static void insertWork(String[] getArray) {
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			runQuery = "INSERT INTO personaldailywork (personalDailyWorkDate, personalDailyWorkOnTime, personalDailyWorkOffTime) VALUES ( '"+getArray[0]+"', '"+getArray[5]+"', '"+getArray[6]+"' );";
			st.executeUpdate(runQuery);
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
	
	//DB 수정 (personalDaily)
	public static void updateDaily(String[] getArray) {		
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			//운동 컬럼(personalDailyPlay)에 처음부터 값이 없었다면 null값이 들어가 있으므로 insert에서 운동컬럼을 제외한다.
			if (getArray[2] == null) {
				runQuery = "UPDATE personaldaily SET personalDailyDate = '"+getArray[0]+"', personalDailyPlace = '"+getArray[1]+"', personalDailyWeather = '"+getArray[3]+"' WHERE personalDailyId = '"+getArray[7]+"'";
			}
			if(getArray[2] != null) {
				//운동 컬럼(personalDailyPlay)에 값이 입력되었을 경우에는 운동 컬럼을 포함해서 insert 한다.
				runQuery = "UPDATE personaldaily SET personalDailyDate = '"+getArray[0]+"', personalDailyPlace = '"+getArray[1]+"', personalDailyPlay = "+getArray[2]+", personalDailyWeather = '"+getArray[3]+"' WHERE personalDailyId = '"+getArray[7]+"'";	
			}
			st.executeUpdate(runQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//DB 수정 (personalDailyRecords)
	public static void updateRecords(String[] getArray) {
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			runQuery = "UPDATE personaldailyrecords SET personalDailyRecordsDate = '"+getArray[8]+"', personalDailyRecordsBook = '"+getArray[4]+"' WHERE personalDailyRecordsDate = '"+getArray[8]+"'";
			st.executeUpdate(runQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//DB 수정 (personalDailyWork)
	public static void updateWork(String[] getArray) {
		String runQuery = null;
		try {
			Statement st = ZA01DaoConnection.getConnection().createStatement();
			runQuery = "UPDATE personaldailywork SET personalDailyWorkDate = '"+getArray[9]+"', personalDailyWorkOnTime = '"+getArray[5]+"', personalDailyWorkOffTime = '"+getArray[6]+"' WHERE personalDailyWorkDate = '"+getArray[9]+"'";
			st.executeUpdate(runQuery);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}