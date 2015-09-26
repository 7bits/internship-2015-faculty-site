package it.sevenbits.FacultySite.core.service.content;

import it.sevenbits.FacultySite.core.domain.content.ContentModel;
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

    public ContentForm insertContent(ContentModel newContent, List<TagModel> tags) throws ServiceException{
        TransactionStatus status = null;
        if (newContent == null){
            return null;
        }
        try{
            status = txManager.getTransaction(customTX);
            contentRepository.insertContent(newContent);
            List<TagModel> resTags = new ArrayList<>();
            for (TagModel tag : tags){
                if (tag == null){
                    continue;
                }
                TagModel tmpTag = tag;
                try {
                    if (tag.getId() == null && tag.getTitle() != null && !tag.getTitle().isEmpty()){
                        tmpTag = tagsRepository.getTagByTitle(tag.getTitle());
                    }
                    createPair(newContent.getId(), tmpTag.getId());
                    resTags.add(tmpTag);
                }
                catch (RepositoryException e) {
                    LOG.log(Level.WARNING, "Can't get tag or create pair: " + e.getMessage());
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

    public void updateContent(ContentModel contentModel, TagModel tagModel) throws ServiceException{
        TransactionStatus status = null;
        try{
            status = txManager.getTransaction(customTX);
            contentRepository.updateContent(contentModel);
            txManager.commit(status);
        }
        catch (RepositoryException e){
            if(status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't update content: " + e.getMessage(), e);
        }
    }

    public void removeContent(Long id) throws ServiceException{
        TransactionStatus status = null;
        try{
            status = txManager.getTransaction(customTX);
            contentRepository.removeContentByID(id);
            txManager.commit(status);
        }catch (RepositoryException e){
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't remove content: " + e.getMessage(), e);
        }
    }

    public ContentForm getContentById(Long id) throws ServiceException{
        if (id == null || id < 1){
            return null;
        }
        try{
            List<TagModel> tags = tagsRepository.getTagsOfContent(id);
            ContentModel contentModel = contentRepository.getContentById(id);
            ContentForm resContent = new ContentForm(contentModel, tags);
            return resContent;
        }
        catch (RepositoryException e){
            throw new ServiceException("Can't give content: " + e.getMessage(), e);
        }
    }


    private void createPair(Long content, Long tag) throws ServiceException{
        if (content == null || tag == null){
            throw new ServiceException("Content or/and tag id was null", null);
        }
        TransactionStatus status = null;
        try{
            status = txManager.getTransaction(customTX);
            contentTagsRepository.insertPair(content, tag);
            txManager.commit(status);
        }
        catch (Exception e){
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't insert pair: " + e.getMessage(), e);
        }
    }

}
