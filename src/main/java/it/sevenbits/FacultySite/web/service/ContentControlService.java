package it.sevenbits.FacultySite.web.service;

import it.sevenbits.FacultySite.core.domain.content.ContentModel;
import it.sevenbits.FacultySite.core.domain.tags.Tag;
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
public class ContentControlService {
    @Autowired
    ContentService contentService;
    @Autowired
    TagService tagService;
    @Autowired
    StateContentCheckService stateContentCheckService;


    public void saveContent(ContentForm form){
        stateContentCheckService.setContentForm(form);
        if (stateContentCheckService.getCorrectly()){
            ContentModel contentModel = new ContentModel(form.getId(),
                    form.getTitle(),
                    form.getDescription(),
                    form.getCreatingDate(),
                    form.getCreatingTime(),
                    form.getImageLink(),
                    form.getMiniContent(),
                    form.getPublish());
            List<TagModel> tagModelList = new ArrayList<>();
            for (Tag tag : form.getTags()){
                TagModel tmp = new TagModel(tag.getId(), tag.getTitle());
                tagModelList.add(tmp);
            }
            try {
                contentService.insertContent(contentModel, tagModelList);
            }
            catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }
}
