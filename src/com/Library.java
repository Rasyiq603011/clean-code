package com;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private List<Book> books;
    private Map<String, Member> members;
    
    public Library() {
        books = new ArrayList<>();
        members = new HashMap<>();
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }
    
    public boolean borrowBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = members.get(memberId);
        
        if (book != null && member != null && book.isAvailable()) {
            book.setAvailable(false);
            member.borrowBook(book);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = members.get(memberId);
        
        if (book != null && member != null && !book.isAvailable()) {
            book.setAvailable(true);
            member.returnBook(book);
            return true;
        }
        return false;
    }
    
    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
    
    public List<Book> getAllBooks() {
        return books;
    }
    
    public Map<String, Member> getAllMembers() {
        return members;
    }
}

