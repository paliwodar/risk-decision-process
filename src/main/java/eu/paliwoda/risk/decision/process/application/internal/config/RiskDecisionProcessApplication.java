package eu.paliwoda.risk.decision.process.application.internal.config;

import org.glassfish.jersey.server.ResourceConfig;

import eu.paliwoda.risk.decision.process.application.internal.di.binding.RiskDecisionProcessBinder;

/**
 * An application configuration class. Provides information about packages
 * and applies dependency injection bindings
 *
 */
public class RiskDecisionProcessApplication extends ResourceConfig {

    public RiskDecisionProcessApplication() {

        register(new RiskDecisionProcessBinder());
        packages(true, "eu.paliwoda.risk.decision.process");

    }
}
