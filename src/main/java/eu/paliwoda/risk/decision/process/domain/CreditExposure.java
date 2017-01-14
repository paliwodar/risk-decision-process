package eu.paliwoda.risk.decision.process.domain;

/**
 * A class representing credit exposure which means the sum of purchases done 
 * by the buyer so far. 
 * 
 * The email is something like buyer's id.
 *
 */
public class CreditExposure {

    private String buyerEmail;

    private int exposure;

    public CreditExposure() {
    }
    
    public CreditExposure(String buyerEmail, int exposure) {
        this.buyerEmail = buyerEmail;
        this.exposure = exposure;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public void increaseExposure(int amount) {
        exposure += amount;
    }

}
