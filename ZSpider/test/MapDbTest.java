import java.io.File;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class MapDbTest {
	
	@Test
	public void test() {
		File file = new File("d:/mapdb/collector");
		// configure and open database using builder pattern.
		// all options are available with code auto-completion.
		DB db = DBMaker.newFileDB(file)
		               .closeOnJvmShutdown()		               
		               .make();

		// open existing an collection (or create new)
		HTreeMap<String, Boolean> map = db.getHashMap("collectionName");
		
		map.put("url5", true);
		map.put("url2", false);
		
		System.out.println(map.keySet());
		
		// map.keySet() is now [1,2]

		db.commit();  //persist changes into disk

		map.put("url3", false);
		System.out.println(map.keySet());
		// map.keySet() is now [1,2,3]
		db.rollback(); //revert recent changes
		// map.keySet() is now [1,2]
		System.out.println(map.keySet());

		db.close();
	}
	
	@Test
	public void readMap() {
		File file = new File("d:/mapdb/collector");
		DB db = DBMaker.newFileDB(file)
	               .closeOnJvmShutdown()               
	               
	               .make();
		HTreeMap<String, Boolean> map = db.getHashMap("collectionName");	
		
		System.out.println(map);
		System.out.println(map.get("url4"));
	}
	
	
	
}
