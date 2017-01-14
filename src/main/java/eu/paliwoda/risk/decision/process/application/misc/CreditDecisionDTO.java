package eu.paliwoda.risk.decision.process.application.misc;

import eu.paliwoda.risk.decision.process.domain.CreditDecision;
/**
 *  A class representing credit decision result. The DTO was introduced
 *  in order to deal easier with JSON since it was hard to make it working
 *  either with an interface and an enum 
 *
 */
public class CreditDecisionDTO implements CreditDecision {

    private boolean accepted;
    private String reason;

    private CreditDecisionDTO() {
    }

    /* (non-Javadoc)
     * @see eu.paliwoda.risk.decision.process.domain.CreditDecision#isAccepted()
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }

    private void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /* (non-Javadoc)
     * @see eu.paliwoda.risk.decision.process.domain.CreditDecision#getReason()
     */
    @Override
    public String getReason() {
        return reason;
    }

    private void setReason(String reason) {
        this.reason = reason;
    }
    
    public static CreditDecisionDTO from(CreditDecision creditDecision) {
        CreditDecisionDTO creditDecisionDTO = new CreditDecisionDTO();
        creditDecisionDTO.setAccepted(creditDecision.isAccepted());
        creditDecisionDTO.setReason(creditDecision.getReason());
        return creditDecisionDTO;
    }

    @Override
    public String toString() {
        return "CreditDecisionDTO [accepted=" + accepted + ", reason=" + reason + "]";
    }

}
