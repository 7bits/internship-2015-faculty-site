package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.gallery.Album;
import it.sevenbits.FacultySite.core.mappers.gallery.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumRepository {

    @Autowired
    AlbumMapper mapper;

    public void addAlbum(final Album album) throws RepositoryException{
        try {
            mapper.addAlbum(album);
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Album> getAllAlbums() throws RepositoryException{
        try{
            return mapper.getAllAlbums();
        }
        catch (Exception e){
            throw new RepositoryException("Can't give all albums: " + e.getMessage(), e);
        }
    }

    public void updateAlbum(final Album album) throws RepositoryException{
        try {
            mapper.updateAlbum(album);
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public void removeAlbum(final Long id) throws RepositoryException {
        try {
            mapper.removeAlbum(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }

    public Album getAlbumById(Long id) throws RepositoryException {
        try{
            return mapper.getAlbumById(id);
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while getting album by id: " + e.getMessage(), e);
        }
    }


}
