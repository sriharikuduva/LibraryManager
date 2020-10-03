import enumerations.BookFormat;
import enumerations.BookStatus;

import java.time.LocalDate;
import java.util.Date;

public class BookItem extends Book {
    private String barcode;
    private boolean isReferenceOnly;
    private LocalDate borrowed, dueDate, dateOfPurchase, publicationDate;
    private double price;
    private BookFormat format;
    private BookStatus status;
    private Rack placedAt;

    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }
    public void setReferenceOnly(boolean referenceOnly) {
        isReferenceOnly = referenceOnly;
    }

    public LocalDate getBorrowed() {
        return borrowed;
    }
    public void setBorrowed(LocalDate borrowed) {
        this.borrowed = borrowed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }
    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public BookFormat getFormat() {
        return format;
    }
    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public BookStatus getStatus() {
        return status;
    }
    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Rack getPlacedAt() {
        return placedAt;
    }
    public void setPlacedAt(Rack placedAt) {
        this.placedAt = placedAt;
    }

    public boolean checkout(String memberId) {
        if(this.isReferenceOnly()) {
            System.out.println("This book is Reference only and can't be issued");
            return false;
        }
        if(!BookLending.lendBook(this.getBarcode(), memberId)){
            return false;
        }
        this.setStatus(BookStatus.LOANED);
        return true;
    }
}
