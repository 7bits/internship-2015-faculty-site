package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.tags.Tag;
import it.sevenbits.FacultySite.core.domain.tags.TagModel;
import it.sevenbits.FacultySite.core.mappers.tags.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TagsRepository {
    @Autowired
    TagMapper tagsMapper;

    public TagModel getTagById(Long id) throws RepositoryException{
        try{
            Tag tag = tagsMapper.getTagById(id);
            TagModel resModel = convertTagToModel(tag);
            return resModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get tag by id: " + e.getMessage());
        }
    }

    public TagModel getTagByTitle(String title) throws RepositoryException{
        try{
            Tag tag = tagsMapper.getTagByTitle(title);
            TagModel resModel = convertTagToModel(tag);
            return resModel;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get tag by title: " + e.getMessage());
        }
    }

    public List<TagModel> getAllTags() throws RepositoryException{
        try{
            List<Tag> tags = tagsMapper.getAllTags();
            List<TagModel> tagModels = new ArrayList<>();
            for (Tag tmpTag : tags){
                TagModel tmpContentModel = convertTagToModel(tmpTag);
                tagModels.add(tmpContentModel);
            }
            return tagModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't get all tags: " + e.getMessage());
        }
    }

    public void insertTag(Tag tag) throws RepositoryException{
        try {
            tagsMapper.insertTag(tag);
        }
        catch (Exception e){
            throw new RepositoryException("Can't tag new tag: " + e.getMessage());
        }
    }

    public void updateTag(Tag tag) throws RepositoryException{
        try {
            tagsMapper.updateTag(tag);
        }
        catch (Exception e){
            throw new RepositoryException("Can't update tag: " + e.getMessage());
        }
    }

    public void removeTagByID(Long id) throws RepositoryException{
        try{
            tagsMapper.removeTagById(id);
        }
        catch (Exception e){
            throw new RepositoryException("Can't remove tag: " + e.getMessage());
        }
    }

    public List<TagModel> getTagsOfContent(Long idContent) throws RepositoryException{
        try{
            List<Tag> tags = tagsMapper.getTagsOfContent(idContent);
            List<TagModel> resModels = new ArrayList<>();
            for (Tag tag : tags){
                TagModel tmpModel = convertTagToModel(tag);
                resModels.add(tmpModel);
            }
            return resModels;
        }
        catch (Exception e){
            throw new RepositoryException("Can't give contents by tag: " + e.getMessage());
        }
    }

    private TagModel convertTagToModel(Tag tag){
        TagModel resModel = new TagModel(tag.getId(),
                tag.getTitle());
        return resModel;
    }
}
