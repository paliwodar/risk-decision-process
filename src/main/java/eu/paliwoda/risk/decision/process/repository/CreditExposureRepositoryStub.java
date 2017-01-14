package eu.paliwoda.risk.decision.process.repository;

import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;

import eu.paliwoda.risk.decision.process.domain.CreditExposure;

/**
 * A naive implementation of credit exposure repository
 *
 */
@Resource
public class CreditExposureRepositoryStub implements CreditExposureRepository {

    private Map<String, Integer> creditExposures = Maps.newConcurrentMap();

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.paliwoda.risk.decision.process.repository.CreditExposureRepository
     * #getExposure(java.lang.String)
     */
    @Override
    public CreditExposure fetchCreditExposureForEmail(String email) {
        creditExposures.putIfAbsent(email, 0);
        return new CreditExposure(email, creditExposures.get(email));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * eu.paliwoda.risk.decision.process.repository.CreditExposureRepository
     * #increaseExposure(java.lang.String, int)
     */
    @Override
    public void persistExposure(CreditExposure creditExposure) {
        creditExposures.put(creditExposure.getBuyerEmail(), creditExposure.getExposure());
    }

}
