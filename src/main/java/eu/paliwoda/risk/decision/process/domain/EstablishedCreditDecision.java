package eu.paliwoda.risk.decision.process.domain;

/**
 * An enum representing predefined credit decision results.
 */
public enum EstablishedCreditDecision implements CreditDecision {

    ACCEPTED(true, "ok"), 
    MAX_AMOUNT_BREACH(false, "amount"), 
    DEBT(false, "debt");

    private final boolean accepted;
    private final String reason;

    private EstablishedCreditDecision(boolean accepted, String reason) {
        this.accepted = accepted;
        this.reason = reason;
    }

    /* (non-Javadoc)
     * @see eu.paliwoda.risk.decision.process.domain.CreditDecision#isAccepted()
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }

    /* (non-Javadoc)
     * @see eu.paliwoda.risk.decision.process.domain.CreditDecision#getReason()
     */
    @Override
    public String getReason() {
        return reason;
    }
    
}
