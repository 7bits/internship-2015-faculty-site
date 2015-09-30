package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.mappers.content.ContentTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ContentTagsRepository {

    @Autowired
    private ContentTagsMapper contentTagsMapper;

    public void insertPair(Long content, Long tag) throws RepositoryException{
        try{
            contentTagsMapper.insertPair(content, tag);
        }
        catch (Exception e){
            throw new RepositoryException("Can't create pair: " + e.getMessage(), e);
        }
    }

    public Boolean havePair(Long content, Long tag) throws RepositoryException{
        try{
            List<Long> resTagId = contentTagsMapper.getTagIDsByContentAndTagIDs(content, tag);
            if (resTagId.size() > 0){
                return true;
            }
            return false;
        }
        catch (Exception e){
            throw new RepositoryException("Can't give pair: " + e.getMessage(), e);
        }
    }

    public void removePair(Long content, Long tag) throws RepositoryException{
        try{
            contentTagsMapper.removePair(content, tag);
        }
        catch (Exception e){
            throw new RepositoryException("Can't remove pair: " + e.getMessage(), e);
        }
    }

}