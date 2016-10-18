package mysqlTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class mysqlTest {
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
			Map<String, Integer> map1 = new TreeMap<String, Integer>(new mapComparator(map));
			for (Map.Entry<String, Integer> m : map.entrySet()) {
				map1.put(m.getKey(), m.getValue());
			}
			// for(String key:map.keySet()){
			// System.out.println(key+":"+map.get(key));
			// }

			// for(Integer v:map.values()){
			// System.out.println(v);
			// }
			for (Map.Entry<String, Integer> m : map.entrySet()) {
				System.out.println(m.getKey() + ":" + m.getValue());
			}
			System.out.println("============");
			for (Map.Entry<String, Integer> m : map1.entrySet()) {
				System.out.println(m.getKey() + ":" + m.getValue());
			}
		} catch (Exception e) {
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}

	}
}

class mapComparator implements Comparator<String> {
	Map<String, Integer> map;

	public mapComparator(Map<String, Integer> map) {
		this.map = map;
	}

	public int compare(String o1, String o2) {
		return map.get(o2) - map.get(o1);
	}

}
