package nl.tudelft.context.workspace;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 5-6-2015
 */
public class DatabaseTest {

    @Test
    public void testInstance() throws Exception {
        assertEquals(Database.instance(), Database.instance());
    }

    @Test
    public void testGetList() throws Exception {
        Database db = new Database();
        db.db = mock(SqlJetDb.class);

        db.getList("table", new String[] {"column"}, 5);

        verify(db.db).runReadTransaction(any());
    }

    @Test
    public void testRemove() throws Exception {
        Database db = new Database();
        db.db = mock(SqlJetDb.class);

        db.remove("table", "value1");

        verify(db.db).runWriteTransaction(any());
    }

    @Test
    public void testInsert() throws Exception {
        Database db = new Database();
        db.db = mock(SqlJetDb.class);

        ArgumentCaptor<ISqlJetTransaction> dbCaptor = ArgumentCaptor.forClass(ISqlJetTransaction.class);

        db.insert("table", "value1");

        verify(db.db).runWriteTransaction(dbCaptor.capture());

        testInsertTransaction(dbCaptor.getValue());
    }

    private void testInsertTransaction(ISqlJetTransaction transaction) throws Exception {
        SqlJetDb db = mock(SqlJetDb.class);
        ISqlJetTable table = mock(ISqlJetTable.class);

        when(db.getTable(any())).thenReturn(table);
        transaction.run(db);

        verify(db).getTable("table");

        verify(table).insert("value1");
    }

    @Test
    public void testReplace() throws Exception {
        Database db = mock(Database.class);
        doCallRealMethod().when(db).replace(any());

        db.replace("test");

        verify(db).remove("test");
        verify(db).insert("test");
    }
}