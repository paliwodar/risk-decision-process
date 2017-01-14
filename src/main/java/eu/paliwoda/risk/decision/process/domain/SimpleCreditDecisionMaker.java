package eu.paliwoda.risk.decision.process.domain;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.Optional;

import javax.annotation.Resource;

/**
 * A simple implementation of credit decision algorithm.
 * 
 * It's basically based on the following rules:
 * - If the amount is less than 10, it should always be accepted 
 * - If the amount is higher than 1000, it should always be rejected with the reason "amount"
 * - If the sum of purchases from a particular email is larger than 1000 (including current purchase), 
 *      it should be rejected with reason "debt"
 *
 */
@Resource
public class SimpleCreditDecisionMaker implements CreditDecisionMaker {

    static final int MAXIMUM_CREDIT_AMOUNT = 1000;
    static final int CREDIT_LINE_LIMIT = 1000;
    static final int ALWAYS_ACCEPT_LIMIT = 9;

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.paliwoda.risk.decision.process.domain.CreditCalculator#calculateRisk
     * (eu.paliwoda.risk.decision.process.domain.CreditProposal, int)
     */
    @Override
    public CreditDecision makeCreditDecision(CreditProposal creditProposal, int actualBuyersExposure) {
        final int creditAmount = creditProposal.getAmount();
        checkArgument(creditAmount > 0);

        Optional<CreditDecision> preliminaryDecision = tryToMakeDecisionUsingPreliminaryConditions(creditAmount,
                actualBuyersExposure);

        if (preliminaryDecision.isPresent()) {
            return preliminaryDecision.get();
        } else {
            return makeDecisionBasedOnBuyersExposure(creditAmount, actualBuyersExposure);
        }
    }

    private Optional<CreditDecision> tryToMakeDecisionUsingPreliminaryConditions(int amount, int actualBuyersExposure) {

        if (amount <= ALWAYS_ACCEPT_LIMIT) {
            return Optional.of(EstablishedCreditDecision.ACCEPTED);
        } else if (amount > MAXIMUM_CREDIT_AMOUNT) {
            return Optional.of(EstablishedCreditDecision.MAX_AMOUNT_BREACH);
        } else {
            return Optional.empty();
        }
    }

    private CreditDecision makeDecisionBasedOnBuyersExposure(int creditAmount, int actualBuyersExposure) {
        final CreditDecision decision;

        if (isCreditLimitGoingToBeExceeded(creditAmount, actualBuyersExposure)) {
            decision = EstablishedCreditDecision.DEBT;
        } else {
            decision = EstablishedCreditDecision.ACCEPTED;
        }
        return decision;
    }

    private boolean isCreditLimitGoingToBeExceeded(int creditAmount, int actualBuyersExposure) {
        return actualBuyersExposure + creditAmount > CREDIT_LINE_LIMIT;
    }

}
