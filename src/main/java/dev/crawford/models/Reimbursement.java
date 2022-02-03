package dev.crawford.models;

/**
 * This concrete Reimbursement class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 *     <li>Description</li>
 *     <li>Creation Date</li>
 *     <li>Resolution Date</li>
 *     <li>Receipt Image</li>
 * </ul>
 *
 */
public class Reimbursement extends AbstractReimbursement {

    public Reimbursement() {
        super();
    }

    /**
     * This includes the minimum parameters needed for the {@link dev.crawford.models.AbstractReimbursement} class.
     * If other fields are needed, please create additional constructors.
     */
    public Reimbursement(int id, Status status, User author, User resolver, Double amount, String date, String time, String location, String description, String justification, Double courseType, String grade) {
        super(id, status, author, resolver, amount, date, time, location, description, justification, courseType, grade);
    }

    public Reimbursement(int id, Status status, User author, User resolver, Double amount, String grade) {
        super(id, status, author, resolver, amount, grade);
    }

    public Reimbursement(int id, Status status, User resolver, Double amount) {
        super(id, status, resolver, amount);
    }
}
