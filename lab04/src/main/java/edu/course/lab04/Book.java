package edu.course.lab04;

import java.util.Objects;

public final class Book {
    private final String isbn;
    private final String title;

    public Book(String isbn, String title) {
        if (isbn == null || title == null) {
            throw new IllegalArgumentException("isbn и title не должны быть null");
        }
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                  
        if (!(o instanceof Book)) return false;      
        Book other = (Book) o;
        return isbn.equals(other.isbn);              
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);                   
    }

    @Override
    public String toString() {
        return "Book{isbn=" + isbn + ", title=" + title + "}";
    }
}