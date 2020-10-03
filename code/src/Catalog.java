import java.util.Date;
import java.util.List;
import java.util.Map;

public class Catalog implements Search {
    private Map<String, List<Book>> bookTitles;
    private Map<String, List<Book>> bookAuthors;
    private Map<String, List<Book>> bookSubjects;
    private Map<String, List<Book>> bookPublicationDates;

    @Override
    public List<Book> searchByTitle(String title) {
        return bookTitles.get(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookAuthors.get(author);
    }

    @Override
    public List<Book> searchBySubject(String subject) {
        return bookSubjects.get(subject);
    }

    @Override
    public List<Book> searchByPubDate(Date publishDate) {
        return bookPublicationDates.get(publishDate);
    }
}
