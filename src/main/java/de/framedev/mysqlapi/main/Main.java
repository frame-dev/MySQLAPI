package de.framedev.mysqlapi.main;

import de.framedev.mysqlapi.api.MySQL;
import de.framedev.mysqlapi.managers.Ser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
        getConfig().options().copyDefaults(true);
        saveConfig();
        new MySQL();
        if(getConfig().getString("MySQL.Host").equalsIgnoreCase(" ")) {
            Bukkit.getConsoleSender().sendMessage("§cBitte bearbeite die Config.yml!");
            Bukkit.getConsoleSender().sendMessage("§6[§bMySQL§6] §c§lERROR");
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                MySQL.MySQLConnection connection = new MySQL.MySQLConnection(MySQL.host, MySQL.user, MySQL.password, MySQL.database, MySQL.port);
                Ser.createConnection(connection.toString(),"connection");
                getLogger().log(Level.INFO,"MySQL Connection wurden gespeichert!");
            }
        }.runTaskLater(this, 120);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mysqlinfo")) {
            if(sender.hasPermission("mysqlapi.info")) {
                if (args.length == 0) {
                    MySQL.MySQLConnection connection = MySQL.MySQLConnection.getFromString((String) Ser.getMySQLLogs("connection"));
                    sender.sendMessage("§6Host §b: " + connection.getHost());
                    sender.sendMessage("§6User §b: " + connection.getUser());
                    String password;
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

    }

    public static Main getInstance() {
        return instance;
    }
}
