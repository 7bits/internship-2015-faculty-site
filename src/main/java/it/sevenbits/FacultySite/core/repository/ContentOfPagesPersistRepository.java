package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.mappers.contentOfPages.ContentOfPagesMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContentOfPagesPersistRepository implements ContentOfPagesRepository{
    private static Logger LOG = Logger.getLogger(ImageDescriptionPersistRepository.class);

    @Autowired
    private ContentOfPagesMapper mapper;

    public List<ContentDescription> getAllPages() throws RepositoryException{
        try {
            return mapper.getAllPages();
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting all pages: "+e.getMessage());
        }
    }

    public void saveContent(ContentDescription description) throws RepositoryException{
        try{
            mapper.saveContentOfPage(description);
        }
        catch (Exception e){
            throw new RepositoryException("Error with adding content: "+e.getMessage());
        }
    }


    public void updatePage(String newTitle, String newDescription, String newType, String newMiniContent, String newImageLink, Boolean isPublish, Long id) throws RepositoryException{
        try{
            mapper.updatePage(newTitle, newDescription, newType, newMiniContent, newImageLink, isPublish, id);
        }
        catch (Exception e){
            throw new RepositoryException("Error with updating page: " + e.getMessage());
        }
    }

    public List<ContentDescription> getPagesByType(String type) throws RepositoryException{
        try {
            return mapper.getPagesByType(type);
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting all pages: "+e.getMessage());
        }
    }

    public void removePageById(Long id) throws RepositoryException{
        try{
            mapper.removePageById(id);
        }
        catch (Exception e){
            throw new RepositoryException("Error with deleting page: " + e.getMessage());
        }
    }

    public void removePageByType(String type) throws RepositoryException{
        try{
            mapper.removePageByType(type);
        }
        catch (Exception e){
            throw new RepositoryException("Error with deleting page: " + e.getMessage());
        }
    }

    public ContentDescription getPageById(Long id) throws RepositoryException{
        try{
            return mapper.getPageById(id);
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting page by id: " + e.getMessage());
        }
    }

    public List<ContentDescription> getPagesIsPublish(Boolean publish) throws RepositoryException{
        try{
            if (publish != null)
                return mapper.getPagesIsPublish(publish);
            return new ArrayList<>();
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting page by publish: " + e.getMessage());
        }
    }

    public List<ContentDescription> getPagesWhichContainTypeIsPublish(String type, Boolean publish) throws RepositoryException{
        try{
            if (publish != null && type != null)
                return mapper.getPagesWhichContainTypeAndPublish(type, publish);
            return new ArrayList<>();
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting page by publish: " + e.getMessage());
        }
    }

    public List<ContentDescription> getPagesWhichContainTypeIsPublishWithBoundaries(String type, Boolean publish, Long start, Long count) throws RepositoryException{
        try{
            if (publish != null && type != null && start != null && count != null && start>=0 && count>=0)
                return mapper.getPagesWhichContainTypeAndPublishWithBoundaries(type, publish, start, start + count);
            return new ArrayList<>();
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting page by publish: " + e.getMessage());
        }
    }

    public Long getSumOfRecords(String type, Boolean publish) throws RepositoryException{
        try{
            if ((type == null || type.isEmpty()) && publish == null){
                return mapper.getSumOfAllContent();
            }
            if (publish == null) {
                return mapper.getSumOfContentWhichContainType(type);
            }
            return mapper.getSumOfContentWhichContainTypeAndPublish(type, publish);
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting count of pages: "+e.getMessage());
        }
    }

    public List<ContentDescription> getPagesWhichContainType(String type) throws RepositoryException{
        try {
            return mapper.getPagesWhichContainType(type);
        }
        catch (Exception e){
            throw new RepositoryException("Error with getting all pages: "+e.getMessage());
        }
    }


}
