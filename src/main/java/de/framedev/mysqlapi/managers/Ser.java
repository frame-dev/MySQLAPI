package de.framedev.mysqlapi.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.framedev.mysqlapi.api.MySQL;
import de.framedev.mysqlapi.main.Main;
import org.mindrot.BCrypt;

import java.io.*;

/*
 * =============================================
 * = This Plugin was Created by FrameDev       =
 * = Copyrighted by FrameDev                   =
 * = Please don't Change anything              =
 * =============================================
 * This Class was made at 29.04.2020, 21:07
 */
public class Ser {

    public static void createConnection(MySQL.MySQLConnection obj, String fileName) {
        File file = new File(Main.getInstance().getDataFolder(),fileName+".json");
        obj.setPassword(BCrypt.hashpw(obj.getPassword(), BCrypt.gensalt()));
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(obj));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MySQL.MySQLConnection getMySQLLogs(String fileName) throws NullPointerException {
        File file = new File(Main.getInstance().getDataFolder(),fileName+".json");
        MySQL.MySQLConnection object = null;
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                object = new Gson().fromJson(reader, MySQL.MySQLConnection.class);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

}
