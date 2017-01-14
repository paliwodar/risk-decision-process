package eu.paliwoda.risk.decision.process.domain;

/**
 *  An interface representing credit decision. 
 *
 */
public interface CreditDecision {

    /**
     *  Whether or not this purchase should be accepted 
     */
    boolean isAccepted();

    /**
     * The reason for not being accepted, or "ok" if accepted
     */
    String getReason();

}