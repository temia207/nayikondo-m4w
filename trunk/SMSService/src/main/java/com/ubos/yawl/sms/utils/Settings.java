package com.ubos.yawl.sms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the storage of setting user the Java 
 * in built Porperties
 * @author yak
 */
public class Settings {

        private Properties prop;
        private File userFile = new File("number.properties");
        private static Settings settings = null;

        public Settings() {
                prop = new Properties();
                System.out.println("Using Properties File: " + userFile.getAbsolutePath());
                if (!userFile.exists()) {
                        try {
                                //if file does not exist
                                //User is the first:
                                System.out.println("Hullo you are the first User");
                                userFile.createNewFile();
                        } catch (Exception ex) {
                                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
        }

        private void loadProperties(boolean reload) throws FileNotFoundException, IOException {
                if (!reload || !prop.isEmpty())
                        return;
                InputStream inputStream = new FileInputStream(userFile);
                prop.load(inputStream);
                inputStream.close();
        }

        private synchronized void store(String property, String value) {
                try {
                        Object obj = prop.setProperty(property, value);
                        persistProperties(property + "=" + obj);
                } catch (IOException ex) {
                        throw new RuntimeException("Error while persisting properties", ex);
                }
        }

        private void persistProperties(String comment) throws FileNotFoundException, IOException {
                OutputStream outputStream = null;
                try {
                        outputStream = new FileOutputStream(userFile);
                        prop.store(outputStream, comment);
                } finally {
                        outputStream.close();
                }
        }

        private synchronized String read(String property) {
                try {
                        loadProperties(true);
                        String setting = prop.getProperty(property);
                        return setting;
                } catch (Exception ex) {
                        Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                }
        }

        private static Settings instance() {
                if (settings == null) {
                        try {
                                settings = new Settings();
                                settings.loadProperties(true);
                                return settings;
                        } catch (IOException ex) {
                                throw new RuntimeException("Error While Loading User Properties File", ex);
                        }
                }
                return settings;
        }

        public synchronized static void storeSetting(String property, String value) {
                Settings st = Settings.instance();
                st.store(property, value);

        }

        public synchronized static String readSetting(String property) {
                Settings st = Settings.instance();
                String setting = st.read(property);
                return setting;
        }

        public static Properties properties() {
                try {
                        Settings instance = Settings.instance();
                        instance.loadProperties(true);
                        instance.prop.list(System.out);
                        return instance.prop;
                } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                }
        }

        public static void delete(String userName) {
                try {
                        Object remove = Settings.instance().prop.remove(userName);
                        Settings.instance().persistProperties("Deleted :" + userName + "=" + remove);
                } catch (IOException ex) {
                        throw new RuntimeException("Error While Deleting: " + userName, ex);
                }
        }
}
