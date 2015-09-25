package it.sevenbits.FacultySite.core.repository;


import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.content.ContentModel;
import it.sevenbits.FacultySite.core.mappers.content.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContentRepository{

    @Autowired
    ContentMapper contentMapper;

    public ContentModel getContentById(Long id) throws RepositoryException{
        try{
            Content content = contentMapper.getContentById(id);
            ContentModel resModel = new ContentModel(content);
            return resModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get content by id: " + e.getMessage(), e);
        }
    }

    public List<ContentModel> getAllContent() throws RepositoryException{
        try{
            List<Content> contents = contentMapper.getAllContent();
            List<ContentModel> contentModels = new ArrayList<>();
            for (Content tmpContent : contents){
                ContentModel tmpContentModel = new ContentModel(tmpContent);
                contentModels.add(tmpContentModel);
            }
            return contentModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get all content: " + e.getMessage(), e);
        }
    }

    public void insertContent(ContentModel content) throws RepositoryException{
        try {
            contentMapper.insertContent(content);
        }
        catch (Exception e){
            throw new RepositoryException("Can't insert new content: " + e.getMessage(), e);
        }
    }

    public void updateContent(ContentModel newContent) throws RepositoryException{
        try {
            contentMapper.updateContent(newContent);
        }
        catch (Exception e){
            throw new RepositoryException("Can't update content: " + e.getMessage(), e);
        }
    }

    public void removeContentByID(Long id) throws RepositoryException{
        try{
            contentMapper.removeContentById(id);
        }
        catch (Exception e){
            throw new RepositoryException("Can't remove content: " + e.getMessage(), e);
        }
    }

    public List<ContentModel> getPublishedContent(Boolean isPublish) throws RepositoryException{
        try{
            List<Content> contents = contentMapper.getPublishedContent(isPublish);
            List<ContentModel> contentModels = new ArrayList<>();
            for (Content tmpContent : contents){
                contentModels.add(new ContentModel(tmpContent));
            }
            return contentModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get publish(or unpublished) content" + e.getMessage(), e);
        }
    }

    public List<ContentModel> getContentByTag(Long tagID) throws RepositoryException{
        try{
            List<Content> contents = contentMapper.getContentsByTag(tagID);
            List<ContentModel> contentModels = new ArrayList<>();
            for (Content content: contents){
                ContentModel tmpModel = new ContentModel(content);
                contentModels.add(tmpModel);
            }
            return contentModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get contents byt tag: " + e.getMessage(), e);
        }
    }



}
