package org.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

	static {
		String className = PropertityUtil.getValue(ConstantsUtil.ENV_PRO, "jdbc.driver");
		try {
			Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		String url = PropertityUtil.getValue(ConstantsUtil.ENV_PRO, "jdbc.url");
		String user = PropertityUtil.getValue(ConstantsUtil.ENV_PRO, "jdbc.username");
		String password = PropertityUtil.getValue(ConstantsUtil.ENV_PRO, "jdbc.password");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	private static void close(Object o) {
		if (o == null) {
			return;
		}
		if (o instanceof ResultSet) {
			try {
				((ResultSet) o).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (o instanceof PreparedStatement) {
			try {
				((PreparedStatement) o).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (o instanceof Connection) {
			Connection c = (Connection) o;
			try {
				if (!c.isClosed()) {
					c.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs, PreparedStatement pst, Connection conn) {
		close(rs);
		close(pst);
		close(conn);
	}

	public static void getSchemasInfo() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getSchemas();
			while (rs.next()) {
				String tableSchem = rs.getString("TABLE_SCHEM");
				String tableCatlog = rs.getString("TABLE_CATALOG");
				System.out.println(tableSchem + ":" + tableCatlog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
	}

	public static void getDataBaseInfo() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println("数据库已知的用户: " + dbmd.getUserName());
			System.out.println("数据库的系统函数的逗号分隔列表: " + dbmd.getSystemFunctions());
			System.out.println("数据库的时间和日期函数的逗号分隔列表: " + dbmd.getTimeDateFunctions());
			System.out.println("数据库的字符串函数的逗号分隔列表: " + dbmd.getStringFunctions());
			System.out.println("数据库供应商用于 'schema' 的首选术语: " + dbmd.getSchemaTerm());
			System.out.println("数据库URL: " + dbmd.getURL());
			System.out.println("是否允许只读:" + dbmd.isReadOnly());
			System.out.println("数据库的产品名称:" + dbmd.getDatabaseProductName());
			System.out.println("数据库的版本:" + dbmd.getDatabaseProductVersion());
			System.out.println("驱动程序的名称:" + dbmd.getDriverName());
			System.out.println("驱动程序的版本:" + dbmd.getDriverVersion());

			System.out.println("数据库中使用的表类型");
			rs = dbmd.getTableTypes();
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_TYPE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}

	}

	public static List<Map<String, Object>> getTablesList() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = new ArrayList<Map<String, Object>>();
			DatabaseMetaData dbmd = conn.getMetaData();
			String[] types = { "TABLE", "VIEW" };
			rs = dbmd.getTables(null, null, "%", types);
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("TABLE_NAME", rs.getString("TABLE_NAME"));
				String tableName = rs.getString("TABLE_NAME"); // 表名
				map.put("TABLE_TYPE", rs.getString("TABLE_TYPE"));
				String tableType = rs.getString("TABLE_TYPE"); // 表类型
				map.put("REMARKS", rs.getString("REMARKS"));
				String remarks = rs.getString("REMARKS"); // 表备注
				System.out.println(tableName + " - " + tableType + " - " + remarks);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
		return list;
	}

	public static List<Map<String, Object>> getColumnsInfo(String tableName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		List<Map<String, Object>> list = null;
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, null, tableName, null);
			list = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("COLUMN_NAME", rs.getString("COLUMN_NAME")); // 列名
				String columnName = rs.getString("COLUMN_NAME");
				// int dataType = rs.getInt("DATA_TYPE"); // 对应的java.sql.Types
				map.put("TYPE_NAME", rs.getString("TYPE_NAME")); // 列名
				String dataTypeName = rs.getString("TYPE_NAME"); // java.sql.Types类型名称(列类型名称)
				map.put("COLUMN_SIZE", rs.getInt("COLUMN_SIZE"));
				int columnSize = rs.getInt("COLUMN_SIZE"); // 列大小
				map.put("DECIMAL_DIGITS", rs.getInt("DECIMAL_DIGITS"));
				int decimalDigits = rs.getInt("DECIMAL_DIGITS"); // 小数位数
				map.put("NUM_PREC_RADIX", rs.getInt("NUM_PREC_RADIX"));
				int numPrecRadix = rs.getInt("NUM_PREC_RADIX"); // 基数（通常是10或2）
				// 0 该列不允许为空
				// 2 不确定该列是否为空
				// 1 该列允许为空
				// int nullAble = rs.getInt("NULLABLE"); // 是否允许为null
				map.put("REMARKS", rs.getString("REMARKS"));
				String remarks = rs.getString("REMARKS"); // 列描述
				map.put("COLUMN_DEF", rs.getString("COLUMN_DEF"));
				String columnDef = rs.getString("COLUMN_DEF"); // 默认值
				map.put("CHAR_OCTET_LENGTH", rs.getInt("CHAR_OCTET_LENGTH"));
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH"); // 对于
				map.put("ORDINAL_POSITION", rs.getInt("ORDINAL_POSITION"));
				int ordinalPosition = rs.getInt("ORDINAL_POSITION"); // 表中列的索引（从1开始）
				// ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
				// YES -- 该列可以有空值
				// NO -- 该列不能为空
				// 空字符串--- 不知道该列是否可为空
				map.put("IS_NULLABLE", rs.getString("IS_NULLABLE"));
				String isNullAble = rs.getString("IS_NULLABLE");
				System.out.println(columnName + " - " + dataTypeName + " - " + columnSize + " - " + decimalDigits
						+ " - " + numPrecRadix + " - " + remarks + " - " + columnDef + " - " + charOctetLength + " - "
						+ ordinalPosition + " - " + isNullAble);
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
		return list;
	}

	public static List<Map<String, Object>> getColumnsValue(String tableName) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		List<Map<String, Object>> resultList = null;
		getColumnsInfo(tableName);
		try {
			List<Map<String, Object>> list = getColumnsInfo(tableName);
			resultList = new ArrayList<Map<String, Object>>();
			String sql = "select * from " + tableName;
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (Map<String, Object> map1 : list) {
					map.put(map1.get("COLUMN_NAME").toString(), rs.getObject(map1.get("COLUMN_NAME").toString()));
				}
				resultList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pst, conn);
		}
		return resultList;
	}

	public static void main(String[] args) {
		List<Map<String, Object>> list = getColumnsValue("um_user_info");
		for (Map<String, Object> map : list) {
			System.out.println(map.get("id").toString());
		}
	}

}
