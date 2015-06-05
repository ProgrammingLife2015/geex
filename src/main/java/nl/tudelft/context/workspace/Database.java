package nl.tudelft.context.workspace;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public class Database {
    private static final String DB_FILE = "geex.db";
    private SqlJetDb db;

    public Database() {
        try {
            db = SqlJetDb.open(new File(DB_FILE), true);

            db.runTransaction(db1 -> {
                db1.getOptions().setUserVersion(1);
                return true;
            }, SqlJetTransactionMode.WRITE);

            createTables();

        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    private static Database instance;

    public static Database instance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public List<String[]> getList(final String tableName, final String[] columns, final int limit) throws SqlJetException {
        List<String[]> out = new ArrayList<>();

        db.runWriteTransaction(db1 -> {
            ISqlJetCursor cursor = db1.getTable(tableName).open().reverse();

            while (!cursor.eof() && out.size() < limit) {
                String[] row = new String[cursor.getFieldsCount()];
                for (int i = 0, columnsLength = columns.length; i < columnsLength; i++) {
                    row[i] = cursor.getString(columns[i]);
                }
                out.add(row);
                cursor.next();
            }

            cursor.close();

            return null;
        });

        return out;
    }

    private void open() throws SqlJetException {
        db = SqlJetDb.open(new File(DB_FILE), true);

        db.runTransaction(db1 -> {
            db1.getOptions().setUserVersion(1);
            return true;
        }, SqlJetTransactionMode.WRITE);

        String workspaceTable = "CREATE TABLE workspace (`location` TEXT NOT NULL, `name` TEXT NOT NULL)";
        String workspaceIndex = "CREATE INDEX location_index ON workspace(location,name)";

        if (db.getTable("workspace") == null) {
            db.runWriteTransaction(arg0 -> {
                arg0.createTable(workspaceTable);
                arg0.createIndex(workspaceIndex);

                return null;
            });
        }
    }

    /**
     * Remove the specified values from te database. Does not fail
     * when the record is not found.
     * @param tableName The table to remove data from
     * @param values The data to remove
     * @throws SqlJetException When there is a database error.
     */
    public void remove(final String tableName, final String... values) throws SqlJetException {
        db.runWriteTransaction(db1 -> {
            ISqlJetCursor cursor = db.getTable(tableName).lookup(db.getTable(tableName).getPrimaryKeyIndexName(), (Object[]) values);

            if (cursor.getRowCount() != 0) {
                cursor.delete();
            }

            cursor.close();

            return null;
        });
    }

    /**
     * Insert a new row into a table.
     * @param tableName Table to insert data into
     * @param values Values to insert into the table
     * @throws SqlJetException
     */
    public void insert(final String tableName, final String... values) throws SqlJetException {
        db.runWriteTransaction(db1 -> db1.getTable(tableName).insert(values));
    }

    /**
     * Replace data in a table, does not fail when the original doesn't exist.
     * @param tableName Table to replace data in.
     * @param index
     * @param values
     * @throws SqlJetException
     */
    public void replace(final String tableName, final String... values) throws SqlJetException {
        remove(tableName, values);
        insert(tableName, values);
    }
}
