package com;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        // Konfigurasi logger untuk seluruh aplikasi
        configureLogger();
        
        logger.info("Selamat Datang di Sistem Perpustakaan");
        logger.info("-----------------------------------");
        
        // Membuat dan memulai sistem perpustakaan
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.start();
    }
    
    private static void configureLogger() {
        // Menghapus handler default
        Logger rootLogger = Logger.getLogger("");
        for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }
        
        // Menambahkan handler kustom
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        
        // Set level logging dan tambahkan handler
        Logger appLogger = Logger.getLogger("com");
        appLogger.setLevel(Level.INFO);
        appLogger.addHandler(handler);
        
        // Pastikan logger tidak melewatkan log ke handler parent
        appLogger.setUseParentHandlers(false);
    }
}