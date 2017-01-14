package eu.paliwoda.risk.decision.process.domain;

/**
 * An interface for objects able to make creadit decision.
 */
public interface CreditDecisionMaker {

    /**
     * Obtain a credit decision for given credit proposal and actual exposure
     * 
     * @param creditProposal credit proposal detail
     * @param actualBuyersExposure the sum of the purchases buyer did so far
     */
    CreditDecision makeCreditDecision(CreditProposal creditProposal, int actualBuyersExposure);

}