package de.framedev.mysqlapi.main;

import com.google.gson.GsonBuilder;
import de.framedev.mysqlapi.api.MySQL;
import de.framedev.mysqlapi.api.SQLite;
import de.framedev.mysqlapi.managers.Ser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mindrot.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
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

    // Singleton
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save Default Config
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Check for Connection
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

        // Save MySQL Connection
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("MySQL.Use")) {
                    MySQL.MySQLConnection connection = new MySQL.MySQLConnection(MySQL.host, MySQL.user, MySQL.password, MySQL.database, MySQL.port);
                    Ser.createConnection(connection, "connection");
                    getLogger().log(Level.INFO, "MySQL Connection wurden gespeichert!");
                }
            }
        }.runTaskLater(this, 120);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mysqlinfo")) {
            if (sender.hasPermission("mysqlapi.info")) {
                if (args.length == 0) {
                    MySQL.MySQLConnection connection = Ser.getMySQLLogs("connection");
                    sender.sendMessage("§6Host §b: " + connection.getHost());
                    sender.sendMessage("§6User §b: " + connection.getUser());
                    sender.sendMessage("§6Password §b: " + connection.getPassword());
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

    /**
     * This Method returns the Singleton of this Class (instance)
     *
     * @return return the Singleton of this Class
     */
    public static Main getInstance() {
        return instance;
    }


    /**
     * Check if MySQL has been enabled in the Config File
     *
     * @return return MySQL.Use in config.yml
     */
    public boolean isMysql() {
        return getConfig().getBoolean("MySQL.Use");
    }

    /**
     * Check if SQL has been enabled in the Config File
     *
     * @return return SQLite.Use in config.yml
     */
    public boolean isSQL() {
        return getConfig().getBoolean("SQLite.Use");
    }
}
