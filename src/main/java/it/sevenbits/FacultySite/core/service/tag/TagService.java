package it.sevenbits.FacultySite.core.service.tag;

import it.sevenbits.FacultySite.core.domain.tags.Tag;
import it.sevenbits.FacultySite.core.domain.tags.TagModel;
import it.sevenbits.FacultySite.core.repository.ContentRepository;
import it.sevenbits.FacultySite.core.repository.ContentTagsRepository;
import it.sevenbits.FacultySite.core.repository.RepositoryException;
import it.sevenbits.FacultySite.core.repository.TagsRepository;
import it.sevenbits.FacultySite.core.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class TagService {
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

    public void insertTag(TagModel tagModel) throws ServiceException{
        TransactionStatus status = null;
        try{
            status = txManager.getTransaction(customTX);
            tagsRepository.insertTag(tagModel);
            txManager.commit(status);
        }
        catch (RepositoryException e){
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't insert tag: " + e.getMessage(), e);
        }
    }

    public void updateTag(TagModel tagModel) throws ServiceException{
        TransactionStatus status = null;
        try {
            status = txManager.getTransaction(customTX);
            tagsRepository.updateTag(tagModel);
            txManager.commit(status);
        }
        catch (RepositoryException e){
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't update content: " + e.getMessage(), e);
        }
    }

    public void removeTag(Long id) throws ServiceException{
        TransactionStatus status = null;
        try {
            status = txManager.getTransaction(customTX);
            tagsRepository.removeTagByID(id);
            txManager.commit(status);
        }
        catch (RepositoryException e){
            if (status != null){
                txManager.rollback(status);
            }
            throw new ServiceException("Can't remove content: " + e.getMessage(), e);
        }
    }

    public TagModel getTagByID(Long id) throws ServiceException{
        try{
            TagModel resModel = tagsRepository.getTagById(id);
            return resModel;
        }
        catch (RepositoryException e){
            throw new ServiceException("Can't give tag by id: " + e.getMessage(), e);
        }
    }

    public TagModel getTagByTitle(String title) throws ServiceException{
        try{
            TagModel resModel = tagsRepository.getTagByTitle(title);
            return resModel;
        }
        catch (RepositoryException e){
            throw new ServiceException("Can't give tag by title: " + e.getMessage(), e);
        }
    }
}
