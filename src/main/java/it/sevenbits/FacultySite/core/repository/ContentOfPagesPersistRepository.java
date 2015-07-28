package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.mappers.contentOfPages.ContentOfPagesMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public void updatePage(String newTitle, String newDescription, String newType, Long id) throws RepositoryException{
        try{
            mapper.updatePage(newTitle, newDescription, newType, id);
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

}
