import enumerations.AccountStatus;
import metadata.Person;

public abstract class Account {
    private String id, password;
    private AccountStatus status;
    private Person person;

    public Account(String id, String password, AccountStatus status, Person person) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.person = person;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
}
