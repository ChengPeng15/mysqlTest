package mysqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class mysqlTest2 {
	public static void main(String arg[]) {
		try {
			Connection con = null; // 定义一个MYSQL链接对象
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // MYSQL驱动
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Demo", "root", "root"); // 链接本地MYSQL

			Statement stmt; // 创建声明
			stmt = con.createStatement();

			String str = "Select name,(@rowNum:=@rowNum+1) as rowNo,score From mysqlSort,"
					+ "(Select (@rowNum :=0) ) b Order by mysqlSort.score Desc";
			ResultSet res = stmt.executeQuery(str);
			Map<String, Integer> map = new TreeMap<String, Integer>();
			while (res.next()) {
				map.put(res.getString(1), res.getInt(2));

			}
			Map<String, Integer> map1 = new TreeMap<String, Integer>();
			for (Map.Entry<String, Integer> m : map.entrySet()) {
				map1.put(m.getKey(), m.getValue());
			}
			List<Map.Entry<String, Integer>>  list = new ArrayList<Map.Entry<String, Integer>>(map1.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					// TODO Auto-generated method stub
					return o2.getValue()-o1.getValue();
				}
			});
			for (Map.Entry<String, Integer> m : map.entrySet()) {
				System.out.println(m.getKey() + ":" + m.getValue());
			}
			System.out.println("============");
			for (Map.Entry<String, Integer> m : list) {
				System.out.println(m.getKey() + ":" + m.getValue());
			}
		} catch (Exception e) {
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}

	}
}


