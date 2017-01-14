package eu.paliwoda.risk.decision.process.application.service;

import static com.google.common.base.Preconditions.checkArgument;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Strings;

import eu.paliwoda.risk.decision.process.application.misc.CreditDecisionDTO;
import eu.paliwoda.risk.decision.process.domain.CreditDecision;
import eu.paliwoda.risk.decision.process.domain.CreditDecisionMaker;
import eu.paliwoda.risk.decision.process.domain.CreditExposure;
import eu.paliwoda.risk.decision.process.domain.CreditProposal;
import eu.paliwoda.risk.decision.process.repository.CreditExposureRepository;

/**
 *  A class representing our decision service. 
 *  
 *  Marked as Singleton in order to make it working while purchases are not persisted yet. 
 *
 */
@Singleton
@Path("/decision")
public class CreditDecisionProcessService {

    @Inject
    private CreditExposureRepository creditExposureRepository;

    @Inject
    private CreditDecisionMaker creditDecisionMaker;

    /**
     * Handling credit decision process
     * 
     * Fetching and persisting the exposure should take place inside a transaction.
     * I was thinking about using OptimisticLocking there. I assume that it is not
     * common situation that the buyer performs two purchases at the same time.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreditDecisionDTO handleCreditDecisionProcess(CreditProposal creditProposal) {

        performArgumentChecks(creditProposal);
        CreditExposure creditExposure = creditExposureRepository.fetchCreditExposureForEmail(creditProposal.getEmail());

        CreditDecision creditDecision = creditDecisionMaker.makeCreditDecision(creditProposal, creditExposure.getExposure());

        if (creditDecision.isAccepted()) {
            creditExposure.increaseExposure(creditProposal.getAmount());
            creditExposureRepository.persistExposure(creditExposure);
        }

        return CreditDecisionDTO.from(creditDecision);

    }

    private void performArgumentChecks(CreditProposal creditProposal) {
        checkArgument(creditProposal != null);
        checkArgument(!Strings.isNullOrEmpty(creditProposal.getEmail()));
        checkArgument(!Strings.isNullOrEmpty(creditProposal.getFirstName()));
        checkArgument(!Strings.isNullOrEmpty(creditProposal.getLastName()));
        checkArgument(creditProposal.getAmount() > 0);
    }

}
