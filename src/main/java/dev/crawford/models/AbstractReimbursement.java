package dev.crawford.models;

import java.util.Objects;

/**
 * This AbstractReimbursement class defines a minimum functionality for
 * interacting with reimbursements in the ERS application.
 *
 * All reimbursements in this application must at least have:
 * <ul>
 *     <li>ID</li>
 *     <li>Status</li>
 *     <li>Author</li>
 *     <li>Resolver</li>
 *     <li>Amount</li>
 * </ul>
 *
 * Additional fields can be added to the concrete {@link dev.crawford.models.Reimbursement} class.
 *
 * @author Center of Excellence
 */
public class AbstractReimbursement {

    private int id;
    private Status status;
    private User author;
    private User resolver;
    private Double amount;
    private String date;
    private String time;
    private String location;
    private String description;
    private String justification;
    private Double courseType;
    private String grade;

    public AbstractReimbursement() {
        super();
    }

    public AbstractReimbursement(int id, Status status, User author, User resolver, Double amount, String date, String time, String location, String description, String justification, Double courseType, String grade) {
        super();
        this.id = id;
        this.status = status;
        this.author = author;
        this.resolver = resolver;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.justification = justification;
        this.courseType = courseType;
        this.grade = grade;
    }

    public AbstractReimbursement(int id, Status status, User author, User resolver, Double amount, String grade) {
        super();
        this.id = id;
        this.status = status;
        this.author = author;
        this.resolver = resolver;
        this.amount = amount;
        this.grade = grade;
    }

    public AbstractReimbursement(int id, Status status, User resolver, Double amount) {
        super();
        this.id = id;
        this.status = status;
        this.resolver = resolver;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Double getCourseType() {
        return courseType;
    }

    public void setCourseType(Double courseType) {
        this.courseType = courseType;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractReimbursement that = (AbstractReimbursement) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && status == that.status && Objects.equals(author, that.author) && Objects.equals(resolver, that.resolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, author, resolver, amount);
    }

    @Override
    public String toString() {
        return "AbstractReimbursement{" +
                "id=" + id +
                ", status=" + status +
                ", author=" + author +
                ", resolver=" + resolver +
                ", amount=" + amount +
                '}';
    }
}
