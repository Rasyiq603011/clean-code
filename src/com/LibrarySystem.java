package com;

import java.util.Scanner;
import java.util.logging.Logger;

public class LibrarySystem {
    private final Library library;
    private final LibraryReport report;
    private final Scanner scanner;
    private static final Logger logger = Logger.getLogger(LibrarySystem.class.getName());
    private static final String BUKU_ID = "ID Buku: ";
    private static final String ANGGOTA_ID = "ID Anggota: ";
    
    public LibrarySystem() {
        library = new Library();
        report = new LibraryReport(library);
        scanner = new Scanner(System.in);
        
        // Inisialisasi data awal (opsional)
        initializeData();
    }
    
    private void initializeData() {
        // Menambahkan beberapa buku
        library.addBook(new Book("B001", "Java Programming", "John Doe"));
        library.addBook(new Book("B002", "Python Basics", "Jane Smith"));
        library.addBook(new Book("B003", "Data Structures", "Alan Turing"));
        library.addBook(new Book("B004", "Web Development", "Tim Berners-Lee"));
        
        // Mendaftarkan beberapa anggota
        library.registerMember(new Member("M001", "Alice", "alice@example.com"));
        library.registerMember(new Member("M002", "Bob", "bob@example.com"));
    }
    
    public void start() {
        int choice = 0;
        
        while (choice != 8) {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());
                processChoice(choice);
            } catch (NumberFormatException e) {
                logger.warning("Masukkan angka yang valid.");
            }
        }
    }
    
    private void displayMenu() {
        logger.info("\n===== SISTEM PERPUSTAKAAN =====");
        logger.info("1. Tambah Buku");
        logger.info("2. Daftar Anggota");
        logger.info("3. Pinjam Buku");
        logger.info("4. Kembalikan Buku");
        logger.info("5. Cari Buku");
        logger.info("6. Laporan Peminjaman");
        logger.info("7. Laporan Inventaris Buku");
        logger.info("8. Keluar");
        logger.info("Pilih menu: ");
    }
    
    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> addBook(false);
            case 2 -> registerMember();
            case 3 -> borrowBook();
            case 4 -> returnBook();
            case 5 -> findBook();
            case 6 -> report.generateBorrowingReport();
            case 7 -> report.generateBookInventoryReport();
            case 8 -> logger.info("Terima kasih telah menggunakan Sistem Perpustakaan.");
            default -> logger.warning("Pilihan tidak valid.");
        }
    }
    
    private void addBook(boolean silent) {
        if (!silent) logger.info("\n--- TAMBAH BUKU ---");
        
        logger.info(BUKU_ID);
        String id = scanner.nextLine();
        
        if (library.findBookById(id) != null) {
            if (!silent) logger.warning("ID Buku sudah digunakan.");
            return;
        }
        
        logger.info("Judul: ");
        String title = scanner.nextLine();
        
        logger.info("Penulis: ");
        String author = scanner.nextLine();
        
        Book book = new Book(id, title, author);
        library.addBook(book);
        
        if (!silent) logger.info("Buku berhasil ditambahkan.");
    }

    
    private void registerMember() {
        logger.info("\n--- DAFTAR ANGGOTA ---");
        
        logger.info(ANGGOTA_ID);
        String id = scanner.nextLine();
        
        // Cek apakah ID sudah digunakan
        if (library.getAllMembers().containsKey(id)) {
            logger.warning("ID Anggota sudah digunakan.");
            return;
        }
        
        logger.info("Nama: ");
        String name = scanner.nextLine();
        
        logger.info("Email: ");
        String email = scanner.nextLine();
        
        Member member = new Member(id, name, email);
        library.registerMember(member);
        
        logger.info("Anggota berhasil didaftarkan.");
    }
    
    private void borrowBook() {
        logger.info("\n--- PINJAM BUKU ---");
        
        // Tampilkan daftar buku yang tersedia
        displayAvailableBooks();

        logger.info(BUKU_ID);
        String bookId = scanner.nextLine();
        
        logger.info(ANGGOTA_ID);
        String memberId = scanner.nextLine();
        
        boolean borrowed = library.borrowBook(bookId, memberId);
        
        if (borrowed) {
            logger.info("Buku berhasil dipinjam.");
        } else {
            logger.warning("Gagal meminjam buku. Pastikan ID buku dan ID anggota valid, dan buku tersedia.");
        }
    }
    
    private void returnBook() {
        logger.info("\n--- KEMBALIKAN BUKU ---");
        
        logger.info(BUKU_ID);
        String bookId = scanner.nextLine();
        
        logger.info(ANGGOTA_ID);
        String memberId = scanner.nextLine();
        
        boolean returned = library.returnBook(bookId, memberId);
        
        if (returned) {
            logger.info("Buku berhasil dikembalikan.");
        } else {
            logger.warning("Gagal mengembalikan buku. Pastikan ID buku dan ID anggota valid, dan buku sedang dipinjam.");
        }
    }
    
    private void findBook() {
        logger.info("\n--- CARI BUKU ---");
        
        logger.info(BUKU_ID);
        String id = scanner.nextLine();
        
        Book book = library.findBookById(id);
        
        if (book != null) {
            logger.info("Buku ditemukan:");
            logger.info(() -> String.format("ID: %s", book.getId()));
            logger.info(() -> String.format("Judul: %s", book.getTitle()));
            logger.info(() -> String.format("Penulis: %s", book.getAuthor()));
            logger.info(() -> String.format("Status: %s", (book.isAvailable() ? "Tersedia" : "Dipinjam")));
        } else {
            logger.warning("Buku tidak ditemukan.");
        }
    }
    
    private void displayAvailableBooks() {
        logger.info("\nDaftar Buku yang Tersedia:");
        
        boolean hasAvailableBooks = false;
        
        for (Book book : library.getAllBooks()) {
            if (book.isAvailable()) {
                logger.info(() -> String.format("%s - %s oleh %s", 
                    book.getId(), book.getTitle(), book.getAuthor()));
                hasAvailableBooks = true;
            }
        }
        
        if (!hasAvailableBooks) {
            logger.info("Tidak ada buku yang tersedia saat ini.");
        }
    }
}