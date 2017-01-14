package eu.paliwoda.risk.decision.process.domain;

import static eu.paliwoda.risk.decision.process.domain.SimpleCreditDecisionMaker.ALWAYS_ACCEPT_LIMIT;
import static eu.paliwoda.risk.decision.process.domain.SimpleCreditDecisionMaker.CREDIT_LINE_LIMIT;
import static eu.paliwoda.risk.decision.process.domain.SimpleCreditDecisionMaker.MAXIMUM_CREDIT_AMOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import eu.paliwoda.risk.decision.process.test.helper.CreditDecisionTestHelper;

public class SimpleCreditDecisionMakerTest {

    private static final int QUARTER_OF_CREDIT_LINE_LIMIT = CREDIT_LINE_LIMIT / 4;
    private static final int HALF_OF_CREDIT_LINE_LIMIT = CREDIT_LINE_LIMIT / 2;

    private CreditDecisionMaker underTest = new SimpleCreditDecisionMaker();

    private CreditDecisionTestHelper helper = new CreditDecisionTestHelper();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionOnNegativeCreditAmount() {

        CreditProposal creditProposal = helper.createCreditProposal(-42);
        int actualBuyersExposure = 0;

        underTest.makeCreditDecision(creditProposal, actualBuyersExposure);
    }

    @Test
    public void shouldNotAllowForProposalHigherThatMaximumCreditAmount() {

        CreditProposal creditProposal = helper.createCreditProposal(MAXIMUM_CREDIT_AMOUNT + 1);
        int actualBuyersExposure = 0;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(false));
    }

    @Test
    public void shouldAllowForProposalsUpToMaximumCreditAmount() {

        CreditProposal creditProposal = helper.createCreditProposal(MAXIMUM_CREDIT_AMOUNT);
        int actualBuyersExposure = 0;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(true));
    }

    @Test
    public void shouldAllowForProposalsUpToDefiniedThresholdEvenIfTheCreditLineWouldRunOut() {

        CreditProposal creditProposal = helper.createCreditProposal(ALWAYS_ACCEPT_LIMIT);
        int actualBuyersExposure = CREDIT_LINE_LIMIT - 1;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(true));
    }
    
    @Test
    public void shouldNotAllowForProposalsHigherThanTheDefiniedAcceptanceThresholdIfTheCreditLinesGoingToRunOut() {

        CreditProposal creditProposal = helper.createCreditProposal(ALWAYS_ACCEPT_LIMIT + 1);
        int actualBuyersExposure = CREDIT_LINE_LIMIT - 1;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(false));
    }

    @Test
    public void shouldAcceptCreditWhenTheSumOfPurchasesAreBelowCreditLineLimit() {

        CreditProposal creditProposal = helper.createCreditProposal(QUARTER_OF_CREDIT_LINE_LIMIT);
        int actualBuyersExposure = HALF_OF_CREDIT_LINE_LIMIT;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(true));
    }

    @Test
    public void shouldNotAcceptCreditWhenTheSumOfPurchasesWouldBeGreaterThanCreditLineLimit() {

        CreditProposal creditProposal = helper.createCreditProposal(HALF_OF_CREDIT_LINE_LIMIT + 10);
        int actualBuyersExposure = HALF_OF_CREDIT_LINE_LIMIT;

        CreditDecision creditDecision = underTest.makeCreditDecision(creditProposal, actualBuyersExposure);

        assertThat(creditDecision.isAccepted(), is(false));
    }

}
