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

    public List<String[]> getList(final String tableName, final String[] columns, final int limit) {
        List<String[]> out = new ArrayList<>();
        try {
            db.runWriteTransaction(db1 -> {
                ISqlJetCursor cursor = db1.getTable(tableName).open().reverse();

                while (!cursor.eof()) {
                    String[] row = new String[cursor.getFieldsCount()];
                    for (int i = 0, columnsLength = columns.length; i < columnsLength; i++) {
                        row[i] = cursor.getString(columns[i]);
                    }
                    out.add(row);
                    cursor.next();
                }

                return null;
            });
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

    public void insert(final String tableName, final String... values) throws SqlJetException {
        db.beginTransaction(SqlJetTransactionMode.WRITE);
        ISqlJetTable table = db.getTable(tableName);

        ISqlJetCursor cursor = table.lookup("location_index", (Object[]) values);

        if (cursor.getRowCount() != 0) {
            System.out.println("Already got this one!");
            cursor.delete();
            cursor.close();
        }
        db.commit();

        db.beginTransaction(SqlJetTransactionMode.WRITE);
        table = db.getTable(tableName);

        table.insert(values);

        db.commit();
    }
}
