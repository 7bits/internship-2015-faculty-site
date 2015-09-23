package it.sevenbits.FacultySite.core.service.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class ContentService {
    @Autowired
    private PlatformTransactionManager ptManager;

    private DefaultTransactionDefinition customTX;

    @Value("${services.tx_main}")
    private String TX_NAME;

    public ContentService(){
        customTX = new DefaultTransactionDefinition();
        customTX.setName(TX_NAME);
        customTX.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

}
