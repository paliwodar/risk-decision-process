package eu.paliwoda.risk.decision.process.test.helper;

import eu.paliwoda.risk.decision.process.domain.CreditProposal;

/**
 * A helper class used in tests. It contains some constants and provide credit proposal construction method. 
 *
 */
public class CreditDecisionTestHelper {

    public final String SOME_FIRST_NAME = "John";
    public final String SOME_LAST_NAME = "Doe";
    public final String SOME_EMAIL = "john@doe.com";

    public CreditProposal createCreditProposal(int amount) {

        CreditProposal creditProposal = new CreditProposal(SOME_EMAIL, SOME_FIRST_NAME, SOME_LAST_NAME, amount);

        return creditProposal;
    }

}
