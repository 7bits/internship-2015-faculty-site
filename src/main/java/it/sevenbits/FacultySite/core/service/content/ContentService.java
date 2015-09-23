package it.sevenbits.FacultySite.core.service.content;

import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.tags.Tag;
import it.sevenbits.FacultySite.core.domain.tags.TagModel;
import it.sevenbits.FacultySite.core.repository.ContentRepository;
import it.sevenbits.FacultySite.core.repository.ContentTagsRepository;
import it.sevenbits.FacultySite.core.repository.RepositoryException;
import it.sevenbits.FacultySite.core.repository.TagsRepository;
import it.sevenbits.FacultySite.core.service.ServiceException;
import it.sevenbits.FacultySite.web.domain.content.ContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
@Service
public class ContentService {
    Logger LOG = Logger.getLogger("ContentServiceLogger");

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private ContentTagsRepository contentTagsRepository;

    private DefaultTransactionDefinition customTX;

    @Value("${services.tx_main}")
    private String TX_NAME;

    public ContentService(){
        customTX = new DefaultTransactionDefinition();
        customTX.setName(TX_NAME);
        customTX.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    public ContentForm insertContent(Content newContent, List<Tag> tags) throws ServiceException{
        TransactionStatus status = null;
        if (newContent == null){
            return null;
        }
        try{
            status = txManager.getTransaction(customTX);
            contentRepository.insertContent(newContent);
            List<Tag> resTags = new ArrayList<>();
            for (Tag tag : tags){
                if (tag == null){
                    continue;
                }
                if (tag.getId() == null && tag.getTitle() != null && !tag.getTitle().isEmpty()){
                    try {
                        TagModel tmpTag = tagsRepository.getTagByTitle(tag.getTitle());
                        tag.setId(tmpTag.getId());
                        createPair(newContent.getId(), tag.getId());
                        resTags.add(tag);
                    }
                    catch (RepositoryException e) {
                        LOG.log(Level.WARNING, "Can't get tag or create pair: " + e.getMessage());
                    }
                }
            }
            ContentForm contentForm = new ContentForm(newContent, resTags);
            txManager.commit(status);
            return contentForm;
        }
        catch (RepositoryException e){
            LOG.log(Level.WARNING, e.getMessage());
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't save content: " + e.getMessage(), e);
        }
    }

    private void createPair(Long content, Long tag) throws ServiceException{
        if (content == null || tag == null){
            throw new ServiceException("Content or(and) tag id was null", null);
        }
        try{
            contentTagsRepository.insertPair(content, tag);
        }
        catch (Exception e){
            throw new ServiceException("Can't insert pair: " + e.getMessage(), e);
        }
    }

}
