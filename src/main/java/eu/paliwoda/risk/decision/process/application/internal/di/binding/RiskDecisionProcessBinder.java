package eu.paliwoda.risk.decision.process.application.internal.di.binding;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import eu.paliwoda.risk.decision.process.domain.CreditDecisionMaker;
import eu.paliwoda.risk.decision.process.domain.SimpleCreditDecisionMaker;
import eu.paliwoda.risk.decision.process.repository.CreditExposureRepository;
import eu.paliwoda.risk.decision.process.repository.CreditExposureRepositoryStub;

/**
 *  A class containing interface-to-implementation bindings for dependency injection 
 *
 */
public class RiskDecisionProcessBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CreditExposureRepositoryStub.class).to(CreditExposureRepository.class);
        bind(SimpleCreditDecisionMaker.class).to(CreditDecisionMaker.class);
    }
}
