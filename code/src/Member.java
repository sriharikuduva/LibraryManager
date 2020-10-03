import enumerations.AccountStatus;
import enumerations.BookStatus;
import enumerations.ReservationStatus;
import metadata.Constants;
import metadata.Person;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Member extends Account {
    private Date joinDate;
    private int totalBooksCheckedOut;


    public Member(String id, String password, AccountStatus status, Person person, Date joinDate) {
        super(id, password, status, person);
        this.joinDate = joinDate;
        this.totalBooksCheckedOut = 0;
    }

    public Date getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public int getTotalBooksCheckedOut() {
        return totalBooksCheckedOut;
    }
    public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
        this.totalBooksCheckedOut = totalBooksCheckedOut;
    }


    public boolean checkoutBookItem(BookItem bookItem) {
        if (this.getTotalBooksCheckedOut() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            System.out.println("The user has already checked-out maximum number of books");
            return false;
        }
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
            // book item has a pending reservation from another user
            System.out.println("This book is reserved by another member");
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from the give member, update it
            bookReservation.setStatus(ReservationStatus.COMPLETED);
        }

        if (!bookItem.checkout(this.getId())) {
            return false;
        }

        this.totalBooksCheckedOut++;
        return true;
    }

    private void checkForFine(String bookItemBarcode) {
        BookLending bookLending = BookLending.fetchLendingDetails(bookItemBarcode);
        Date dueDate = bookLending.getDueDate();
        Date today = new Date();
        // check if the book has been returned within the due date
        if (today.compareTo(dueDate) > 0) {
            long diff = today.getTime() - dueDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            Fine.collectFine(super.getId(), diffDays);
        }
    }

    public void returnBookItem(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        if (bookReservation != null) {
            // book item has a pending reservation
            bookItem.setStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
        }
        bookItem.updateBookItemStatus(BookStatus.AVAILABLE);
    }

    public boolean renewBookItem(BookItem bookItem) {
        this.checkForFine(bookItem.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(bookItem.getBarcode());
        // check if this book item has a pending reservation from another member
        if (bookReservation != null && bookReservation.getMemberId() != super.getId()) {
            System.out.println("This book is reserved by another member");
            totalBooksCheckedOut--;
            bookItem.setStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from this member
            bookReservation.setStatus(ReservationStatus.COMPLETED);
        }
        BookLending.lendBook(bookItem.getBarcode(), super.getId());
        bookItem.setDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
        return true;
    }
}
