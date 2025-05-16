
package com;
import java.util.logging.Logger;

public class LibraryReport {
    private final Library library;
    private static final Logger logger = Logger.getLogger(LibraryReport.class.getName());

    public LibraryReport(Library library) {
        this.library = library;
    }
    
    public void generateBorrowingReport() {
        logger.info("=== BORROWING REPORT ===");
        library.getAllMembers().values().forEach(member -> {
            logger.info(() -> String.format("Member: %s", member.getName()));
            logger.info(() -> String.format("Borrowed books: %d", member.getBorrowedBooks().size()));
            member.getBorrowedBooks().forEach(book ->
                logger.info(() -> String.format("- %s by %s", book.getTitle(), book.getAuthor()))
            );
        });
    }

    public void generateBookInventoryReport() {
        // Menggunakan array untuk mengatasi masalah "final" dalam lambda
        final int[] bookCounts = new int[2]; // [0]=available, [1]=borrowed
        
        logger.info("=== BOOK INVENTORY REPORT ===");
        for (Book book : library.getAllBooks()) {
            if (book.isAvailable()) {
                bookCounts[0]++; // availableCount
            } else {
                bookCounts[1]++; // borrowedCount
            }

            // Properly escape single quotes for java.util.logging
            logger.info(() -> String.format("- %s by %s [%s]", 
                book.getTitle(), 
                book.getAuthor(),
                book.isAvailable() ? "Available" : "Borrowed")
            );
        }

        int totalBooks = bookCounts[0] + bookCounts[1];
        
        // Gunakan nilai yang sudah final dan tidak berubah lagi
        logger.info(String.format("\nTotal books: %d", totalBooks));
        logger.info(String.format("Available books: %d", bookCounts[0]));
        logger.info(String.format("Borrowed books: %d", bookCounts[1]));
    }
}