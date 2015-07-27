package it.sevenbits.FacultySite.web.service.contentOfPages;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.repository.ContentOfPagesRepository;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.Helper;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.flywaydb.core.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ContentOfPagesService {
    @Autowired
    private ContentOfPagesRepository repository;

    public List<ContentDescriptionModel> getAllPages() throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getAllPages();
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public void saveContentOfPage(String title, String description, String type) throws ServiceException{
        try{
            Calendar calendar = Calendar.getInstance();
            ContentDescription res = new ContentDescription(title, description, Helper.getDate(), Helper.getTime(), type);
            repository.saveContent(res);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while save content: " + e.getMessage(), e);
        }

    }

}