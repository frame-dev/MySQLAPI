package de.framedev.mysqlapi.api;

import de.framedev.mysqlapi.main.Main;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.mysqlapi.api
 * / ClassName API
 * / Date: 29.10.21
 * / Project: MySQLAPI
 * / Copyrighted by FrameDev
 */

public class MySQLAPI {

    private static MySQLAPI instance;

    public MySQLAPI() {
        instance = this;
    }

    public static MySQLAPI getInstance() {
        return instance;
    }

    public boolean isMySQL() {
        return Main.getInstance().isMysql();
    }

    public boolean isSQL() {
        return Main.getInstance().isSQL();
    }
}
