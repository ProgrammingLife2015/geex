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
    /**
     * Database location.
     */
    private static final String DB_FILE = "geex.db";

    /**
     * Reference to the SqlJet database.
     */
    SqlJetDb db;

    /**
     * Create a new Database
     */
    Database() {
    }

    /**
     * Static instance, for the singleton.
     */
    private static volatile Database instance;

    /**
     * Get or Create the database.
     * @return The database for this application.
     * @throws SqlJetException When the creation fails.
     */
    public static Database instance() throws SqlJetException {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    Database database = new Database();
                    database.open();

                    instance = database;
                }
            }
        }
        return instance;
    }

    /**
     * Get the contents of a table from the database.
     * @param tableName The table to select form.
     * @param columns The columns to select.
     * @param limit The limit of the selection.
     * @return A list of rows.
     * @throws SqlJetException When the database fails.
     */
    public List<String[]> getList(final String tableName, final String[] columns, final int limit) throws SqlJetException {
        List<String[]> out = new ArrayList<>();

        db.runReadTransaction(db1 -> {
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

    /**
     * Open the database and create the proper tables if they don't exist.
     * @throws SqlJetException Something went wrong.
     */
    private void open() throws SqlJetException {
        db = SqlJetDb.open(new File(DB_FILE), true);

        db.runTransaction(db1 -> {
            db1.getOptions().setUserVersion(1);
            return true;
        }, SqlJetTransactionMode.WRITE);

        String workspaceTable = "CREATE TABLE workspace (`location` TEXT NOT NULL PRIMARY KEY, `name` TEXT NOT NULL)";
        String workspaceIndex = "CREATE INDEX location_index ON workspace(location, name)";

        if (db.getSchema().getTable("workspace") == null) {
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
            ISqlJetTable table = db.getTable(tableName);

            ISqlJetCursor cursor = table.lookup(table.getPrimaryKeyIndexName(), values[0]);

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
     * @param values Values to replace
     * @throws SqlJetException
     */
    public void replace(final String tableName, final String... values) throws SqlJetException {
        remove(tableName, values);
        insert(tableName, values);
    }
}
