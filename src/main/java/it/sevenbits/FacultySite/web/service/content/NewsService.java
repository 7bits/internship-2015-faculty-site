package it.sevenbits.FacultySite.web.service.content;


import it.sevenbits.FacultySite.core.domain.tags.TagModel;
import it.sevenbits.FacultySite.core.service.ServiceException;
import it.sevenbits.FacultySite.core.service.content.ContentService;
import it.sevenbits.FacultySite.core.service.tag.TagService;
import it.sevenbits.FacultySite.web.domain.content.ContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    ContentService contentService;

    @Autowired
    TagService tagService;

    public List<ContentForm> constructNewsPage(Integer pageNum){
        if (pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        try{
            TagModel tagModel = tagService.getTagByTitle("Новости");
            List<ContentForm> res = contentService.getContentByTagOnPage(tagModel, pageNum);
            return res;
        }
        catch (ServiceException e){
            return new ArrayList<>();
        }
    }
}
