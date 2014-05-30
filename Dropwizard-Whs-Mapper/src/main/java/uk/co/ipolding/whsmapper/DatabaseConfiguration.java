package uk.co.ipolding.whsmapper;


import org.h2.jdbcx.JdbcConnectionPool;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;

/**
 * Created by ian.polding on 16/05/2014.
 */
public class DatabaseConfiguration {

    public static DBI getH2DatabaseInstance() {

        DataSource ds = JdbcConnectionPool.create("jdbc:h2:tcp://localhost/~/test",
                "ian",
                "password");

        return new DBI(ds);
    }

    public static DBI getH2MemoryDatabaseInstance() {

        DataSource ds = JdbcConnectionPool.create("jdbc:h2:mem:test",
                "ian",
                "password");

        return new DBI(ds);
    }

    public static DBI getSQLiteDatabaseInstance() throws Exception {

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:sites.db",
                    "ian",
                    "password");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        c.close();

        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.createConnection("jdbc:sqlite:test:db");
        return new DBI(new SQLiteConnectionPoolDataSource(sqLiteConfig));
    }

 }