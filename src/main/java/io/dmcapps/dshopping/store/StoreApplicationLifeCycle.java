package io.dmcapps.dshopping.store;

import io.dmcapps.dshopping.utils.DatabaseInitialization;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
class StoreApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(StoreApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        
        LOGGER.info(":'######::'########::'#######::'########::'########::'######::::::::'###::::'########::'####:");
        LOGGER.info("'##... ##:... ##..::'##.... ##: ##.... ##: ##.....::'##... ##::::::'## ##::: ##.... ##:. ##::");
        LOGGER.info(" ##:::..::::: ##:::: ##:::: ##: ##:::: ##: ##::::::: ##:::..::::::'##:. ##:: ##:::: ##:: ##::");
        LOGGER.info(". ######::::: ##:::: ##:::: ##: ########:: ######:::. ######:::::'##:::. ##: ########::: ##::");
        LOGGER.info(":..... ##:::: ##:::: ##:::: ##: ##.. ##::: ##...:::::..... ##:::: #########: ##.....:::: ##::");
        LOGGER.info("'##::: ##:::: ##:::: ##:::: ##: ##::. ##:: ##:::::::'##::: ##:::: ##.... ##: ##::::::::: ##::");
        LOGGER.info(". ######::::: ##::::. #######:: ##:::. ##: ########:. ######::::: ##:::: ##: ##::::::::'####:");
        LOGGER.info(":......::::::..::::::.......:::..:::::..::........:::......::::::..:::::..::..:::::::::....::");
        LOGGER.info("                                                         Powered by Quarkus");
        
        LOGGER.infof("The application STORES is starting with profile `%s`", ProfileManager.getActiveProfile());
        
        if(ProfileManager.getActiveProfile() == "dev" || ProfileManager.getActiveProfile() == "test"){
            DatabaseInitialization.mongoDBInitialize(ProfileManager.getActiveProfile());
        }
    }             

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application PRODUCT is stopping...");
    }
}