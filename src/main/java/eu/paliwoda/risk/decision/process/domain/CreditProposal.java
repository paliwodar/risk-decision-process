package eu.paliwoda.risk.decision.process.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * A class representing the details of credit proposal 
 *
 */
public class CreditProposal {

    /** Email of the buyer */
    private String email;

    /** First Name of the buyer */
    @XmlElement(name = "first_name")
    private String firstName;

    /** Last Name of the buyer */
    @XmlElement(name = "last_name")
    private String lastName;

    /** The total amount of this purchase, in some fictitious currency */
    private int amount;

    @SuppressWarnings("unused")
    private CreditProposal() {
    }
    
    public CreditProposal(String email, String firstName, String lastName, int amount) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    
    public int getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    private void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("unused")
    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("unused")
    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("unused")
    private void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditProposal [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", amount="
                + amount + "]";
    }

}
