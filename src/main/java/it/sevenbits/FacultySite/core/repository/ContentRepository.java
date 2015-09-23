package it.sevenbits.FacultySite.core.repository;


import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.content.ContentModel;
import it.sevenbits.FacultySite.core.mappers.content.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ContentRepository{

    @Autowired
    ContentMapper contentMapper;

    public ContentModel getContentById(Long id) throws RepositoryException{
        try{
            Content content = contentMapper.getContentById(id);
            ContentModel resModel = new ContentModel(content.getId(),
                    content.getTitle(),
                    content.getDescription(),
                    content.getCreatingDate(),
                    content.getCreatingTime(),
                    content.getImageLink(),
                    content.getMiniContent(),
                    content.getPublish());
            return resModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get content by id: " + e.getMessage());
        }
    }

    public List<ContentModel> getAllContent() throws RepositoryException{
        try{
            List<Content> contents = contentMapper.getAllContent();
            List<ContentModel> contentModels = new ArrayList<>();
            for (Content tmpContent : contents){
                ContentModel tmpContentModel = new ContentModel(tmpContent.getId(),
                        tmpContent.getTitle(),
                        tmpContent.getDescription(),
                        tmpContent.getCreatingDate(),
                        tmpContent.getCreatingTime(),
                        tmpContent.getImageLink(),
                        tmpContent.getMiniContent(),
                        tmpContent.getPublish());
                contentModels.add(tmpContentModel);
            }
            return contentModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get all content: " + e.getMessage());
        }
    }

    public ContentModel insertContent(Content content) throws RepositoryException{
        try {
            ContentModel insertingModel = new ContentModel(content.getId(),
                    content.getTitle(),
                    content.getDescription(),
                    content.getCreatingDate(),
                    content.getCreatingTime(),
                    content.getImageLink(),
                    content.getMiniContent(),
                    content.getPublish());
            contentMapper.insertContent(insertingModel);
            return insertingModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't insert new content: " + e.getMessage());
        }
    }

    public ContentModel updateContent(Content newContent) throws RepositoryException{
        try {
            ContentModel updatingModel = new ContentModel(newContent.getId(),
                    newContent.getTitle(),
                    newContent.getDescription(),
                    newContent.getCreatingDate(),
                    newContent.getCreatingTime(),
                    newContent.getImageLink(),
                    newContent.getMiniContent(),
                    newContent.getPublish());
            contentMapper.updateContent(updatingModel);
            return updatingModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't update content: " + e.getMessage());
        }
    }

    public void removeContentByID(Long id) throws RepositoryException{
        try{
            contentMapper.removeContentById(id);
        }
        catch (Exception e){
            throw new RepositoryException("Can't remove content: " + e.getMessage());
        }
    }

    public List<ContentModel> getPublishedContent(Boolean isPublish) throws RepositoryException{
        try{
            List<Content> contents = contentMapper.getPublishedContent(isPublish);
            List<ContentModel> contentModels = new ArrayList<>();
            for (Content tmpContent : contents){
                ContentModel tmpContentModel = new ContentModel(tmpContent.getId(),
                        tmpContent.getTitle(),
                        tmpContent.getDescription(),
                        tmpContent.getCreatingDate(),
                        tmpContent.getCreatingTime(),
                        tmpContent.getImageLink(),
                        tmpContent.getMiniContent(),
                        tmpContent.getPublish());
                contentModels.add(tmpContentModel);
            }
            return contentModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get publish(or unpublished) content" + e.getMessage());
        }
    }

}
