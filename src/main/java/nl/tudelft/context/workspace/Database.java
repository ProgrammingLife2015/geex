package nl.tudelft.context.workspace;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
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

    public List<String[]> getList(final String tableName, final String[] columns, final int limit) {
        List<String[]> out = new ArrayList<>();
        try {
            ISqlJetTable table = db.getTable(tableName);
            db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
            ISqlJetCursor cursor = table.order(table.getPrimaryKeyIndexName());
            cursor.setLimit(limit);
            if (!cursor.eof()) {
                do {
                    String[] row = new String[cursor.getFieldsCount()];
                    for (int i = 0, columnsLength = columns.length; i < columnsLength; i++) {
                        row[i] = cursor.getString(columns[i]);
                    }
                    out.add(row);
                } while (cursor.next());
            }
        } catch (SqlJetException e) {
            e.printStackTrace();
        } finally {
            try {
                db.commit();
            } catch (SqlJetException e) {
                e.printStackTrace();
            }
        }

        return out;
    }

    private void createTables() throws SqlJetException {
        String workspaceTable = "CREATE TABLE IF NOT EXISTS workspace (`location` TEXT NOT NULL PRIMARY KEY , `name` TEXT NOT NULL)";

        db.beginTransaction(SqlJetTransactionMode.WRITE);
        db.createTable(workspaceTable);
        db.commit();
    }
}
