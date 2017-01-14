package eu.paliwoda.risk.decision.process.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eu.paliwoda.risk.decision.process.application.service.CreditDecisionProcessService;
import eu.paliwoda.risk.decision.process.domain.CreditDecision;
import eu.paliwoda.risk.decision.process.domain.CreditDecisionMaker;
import eu.paliwoda.risk.decision.process.domain.CreditExposure;
import eu.paliwoda.risk.decision.process.domain.CreditProposal;
import eu.paliwoda.risk.decision.process.domain.EstablishedCreditDecision;
import eu.paliwoda.risk.decision.process.repository.CreditExposureRepository;
import eu.paliwoda.risk.decision.process.test.helper.CreditDecisionTestHelper;

@RunWith(MockitoJUnitRunner.class)
public class CreditDecisionProcessServiceTest {

    @Mock
    private CreditExposureRepository creditExposureRepositoryMock;

    @Mock
    private CreditDecisionMaker creditDecisionMakerMock;

    @InjectMocks
    private CreditDecisionProcessService underTest;

    private CreditDecisionTestHelper helper = new CreditDecisionTestHelper();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreditProposalIsNull() {

        underTest.handleCreditDecisionProcess(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreditAmountIsNegative() {

        CreditProposal creditProposal = helper.createCreditProposal(-10);

        underTest.handleCreditDecisionProcess(creditProposal);
    }

    @Test
    public void shouldAcceptCreditProposalWhenDecisionMakerDecidedSo() {
        final int someAmount = 42;
        final int actualBuyersExposure = 7;
        final CreditProposal creditProposal = helper.createCreditProposal(someAmount);
        makeCreditAccepted(creditProposal);
        makeExposureBeingFetchedFromRepositoryWithGivenAmount(actualBuyersExposure);

        CreditDecision decision = underTest.handleCreditDecisionProcess(creditProposal);

        assertThat(decision.isAccepted(), is(true));
    }

    @Test
    public void shouldUpdateBuyersExposureWhenCreditAccepted() {
        final int someAmount = 42;
        final int actualBuyersExposure = 7;
        final CreditProposal creditProposal = helper.createCreditProposal(someAmount);
        makeCreditAccepted(creditProposal);
        makeExposureBeingFetchedFromRepositoryWithGivenAmount(actualBuyersExposure);

        underTest.handleCreditDecisionProcess(creditProposal);

        verifyThatExposureWasPersistedWithIncreasedValue(someAmount, actualBuyersExposure);
    }
    
    @Test
    public void shouldRefuseCreditProposalWhenDecisionMakerDecidedSo() {
        final int someAmount = 42;
        final int actualBuyersExposure = 7;
        final CreditProposal creditProposal = helper.createCreditProposal(someAmount);
        makeCreditRefused(creditProposal);
        makeExposureBeingFetchedFromRepositoryWithGivenAmount(actualBuyersExposure);

        CreditDecision decision = underTest.handleCreditDecisionProcess(creditProposal);

        assertThat(decision.isAccepted(), is(false));
    }

    private void makeCreditAccepted(final CreditProposal creditProposal) {
        when(creditDecisionMakerMock.makeCreditDecision(Mockito.eq(creditProposal), Mockito.anyInt())).thenReturn(
                EstablishedCreditDecision.ACCEPTED);
    }
    
    private void makeCreditRefused(final CreditProposal creditProposal) {
        when(creditDecisionMakerMock.makeCreditDecision(Mockito.eq(creditProposal), Mockito.anyInt())).thenReturn(
                EstablishedCreditDecision.DEBT);
    }

    private void makeExposureBeingFetchedFromRepositoryWithGivenAmount(final int actualBuyersExposure) {
        when(creditExposureRepositoryMock.fetchCreditExposureForEmail(helper.SOME_EMAIL)).thenReturn(
                new CreditExposure(helper.SOME_EMAIL, actualBuyersExposure));
    }

    private void verifyThatExposureWasPersistedWithIncreasedValue(final int someAmount, final int actualBuyersExposure) {
        ArgumentCaptor<CreditExposure> captor = ArgumentCaptor.forClass(CreditExposure.class);
        verify(creditExposureRepositoryMock).persistExposure(captor.capture());
        assertThat(captor.getValue().getBuyerEmail(), is(helper.SOME_EMAIL));
        assertThat(captor.getValue().getExposure(), is(someAmount + actualBuyersExposure));
    }

}
