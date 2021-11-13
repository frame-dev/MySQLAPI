package de.framedev.mysqlapi.main;

import de.framedev.mysqlapi.api.MySQL;
import de.framedev.mysqlapi.api.MySQLAPI;
import de.framedev.mysqlapi.api.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

/*
 * =============================================
 * = This Plugin was Created by FrameDev       =
 * = Copyrighted by FrameDev                   =
 * = Please don't Change anything              =
 * =============================================
 * This Class was made at 22.04.2020, 21:32
 */
public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        new MySQLAPI();
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (isMysql()) {
            // Setup MySQL
            new MySQL();
            if (getConfig().getString("MySQL.Host").equalsIgnoreCase(" ")) {
                Bukkit.getConsoleSender().sendMessage("§cBitte bearbeite die Config.yml!");
                Bukkit.getConsoleSender().sendMessage("§6[§bMySQL§6] §c§lERROR");
                getLogger().log(Level.INFO, "MySQL Enabled!");
            }
        } else if (isSQL()) {
            // Setup SQLite
            new SQLite(getConfig().getString("SQLite.Path"), getConfig().getString("SQLite.FileName"));
            if (getConfig().getString("SQLite.Path").equalsIgnoreCase(" ")) {
                Bukkit.getConsoleSender().sendMessage("§cBitte bearbeite die Config.yml!");
                Bukkit.getConsoleSender().sendMessage("§6[§bSQLite§6] §c§lERROR");
            } else {
                getLogger().log(Level.INFO, "SQLite Enabled!");
            }
        }
        /*new BukkitRunnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("MySQL.Use")) {
                    MySQL.MySQLConnection connection = new MySQL.MySQLConnection(MySQL.host, MySQL.user, MySQL.password, MySQL.database, MySQL.port);
                    Ser.createConnection(connection.toString(), "connection");
                    getLogger().log(Level.INFO, "MySQL Connection wurden gespeichert!");
                }
            }
        }.runTaskLater(this, 120);*/
    }

    /*@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mysqlinfo")) {
            if (sender.hasPermission("mysqlapi.info")) {
                if (args.length == 0) {
                    MySQL.MySQLConnection connection = MySQL.MySQLConnection.getFromString((String) Ser.getMySQLLogs("connection"));
                    try {
                        File file = new File(getDataFolder(), "mysql.json");
                        FileWriter writer = new FileWriter(file);
                        writer.write(new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(connection));
                        writer.flush();
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage("§6Host §b: " + connection.getHost());
                    sender.sendMessage("§6User §b: " + connection.getUser());
                    String password = encryptText(connection.getPassword());
                    sender.sendMessage("§6Password §b: " + password);
                    sender.sendMessage("§6Database §b: " + connection.getDatabase());
                    sender.sendMessage("§6Port §b: " + connection.getPort());
                    if (MySQL.con != null) {
                        sender.sendMessage("§6Connected §b: " + true);
                    } else {
                        sender.sendMessage("§6Connected §b: " + false);
                    }
                } else {
                    sender.sendMessage("§cBitte benutze §6/mysqlinfo§4§l");
                }
            }
            return true;
        }
        return super.onCommand(sender, command, label, args);

    }*/

    public static Main getInstance() {
        return instance;
    }

    public String encryptText(String text) {
        char[] chars = text.toCharArray();
        Arrays.fill(chars, '*');
        return String.valueOf(chars);
    }

    public boolean isMysql() {
        return getConfig().getBoolean("MySQL.Use");
    }

    public boolean isSQL() {
        return getConfig().getBoolean("SQLite.Use");
    }
}
