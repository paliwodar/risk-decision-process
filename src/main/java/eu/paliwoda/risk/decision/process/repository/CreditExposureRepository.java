package eu.paliwoda.risk.decision.process.repository;

import eu.paliwoda.risk.decision.process.domain.CreditExposure;

/**
 * An interface for handling the persistence of credit exposure data
 *
 */
public interface CreditExposureRepository {

    /**
     * Fetching credit exposure for the buyer of given email address
     * 
     * @param email buyer's email address
     * @return
     */
    CreditExposure fetchCreditExposureForEmail(String email);

    /**
     * Persisting the buyer's credit exposure data
     * 
     * @param buyerData buyer's credit exposure data 
     */
    void persistExposure(CreditExposure buyerData);

}