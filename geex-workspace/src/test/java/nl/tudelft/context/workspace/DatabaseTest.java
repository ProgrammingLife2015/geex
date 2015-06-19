package nl.tudelft.context.workspace;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Gerben Oolbekkink
 * @version 2.0
 * @since 5-6-2015
 */
public class DatabaseTest {

    @After
    public void breakDown() throws Exception {
        Database.close();
        Files.delete(new File("geex.db").toPath());
    }

    @Test
    public void testInstance() throws Exception {
        assertEquals(Database.instance(), Database.instance());
    }

    @Test
    public void testGetListDb() throws Exception {
        Database db = Database.instance();

        List<String[]> data = new ArrayList<>();

        data.add(new String[]{"MyLocation", "MyName"});
        data.add(new String[]{"MyOtherLocation", "MyUniqueName"});
        data.add(new String[]{"MyFinalLocation", "MyCoolName"});

        for (String[] strings : data) {
            db.insert("workspace", strings);
        }

        List<String[]> result = db.getList("workspace", new String[]{"location", "name"}, 2);

        assertEquals(2, result.size());
        // Note the reverse in db.getList
        assertArrayEquals(data.get(2), result.get(0));
        assertArrayEquals(data.get(1), result.get(1));
    }

    @Test
    public void testRemoveDb() throws Exception {
        Database db = Database.instance();

        List<String[]> data = new ArrayList<>();

        data.add(new String[]{"MyLocation", "MyName"});
        data.add(new String[]{"MyOtherLocation", "MyUniqueName"});
        data.add(new String[]{"MyFinalLocation", "MyCoolName"});

        for (String[] strings : data) {
            db.insert("workspace", strings);
        }

        List<String[]> result = db.getList("workspace", new String[]{"location", "name"}, 2);

        assertEquals(2, result.size());

        db.remove("workspace", data.get(2));
        db.remove("workspace", data.get(1));

        result = db.getList("workspace", new String[]{"location", "name"}, 2);

        assertEquals(1, result.size());

        assertArrayEquals(data.get(0), result.get(0));
    }
}
