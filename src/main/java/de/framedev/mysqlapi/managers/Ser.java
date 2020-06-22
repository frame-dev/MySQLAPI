package de.framedev.mysqlapi.managers;

import de.framedev.mysqlapi.main.Main;

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

    public static void createConnection(Object obj,String fileName) {
        File file = new File(Main.getInstance().getDataFolder(),fileName+".my");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getMySQLLogs(String fileName) throws NullPointerException {
        File file = new File(Main.getInstance().getDataFolder(),fileName+".my");
        Object object = null;
        if (file.exists()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                object = objectInputStream.readObject();
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

}
