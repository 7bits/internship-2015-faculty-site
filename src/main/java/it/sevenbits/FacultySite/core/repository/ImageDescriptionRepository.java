package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.core.mappers.gallery.ImageDescriptionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageDescriptionRepository {
    private static Logger LOG = Logger.getLogger(ImageDescriptionRepository.class);

    @Autowired
    private ImageDescriptionMapper mapper;


    
    public void addAlbum(final AlbumDescription album) throws RepositoryException{
        try {
            mapper.addAlbum(album);
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }


    public void updateAlbum(final AlbumDescription album) throws RepositoryException{
        try {
            mapper.updateAlbum(album);
        }
        catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public void saveImage(final ImageDescription Description) throws RepositoryException {
        if (Description == null) {
            throw new RepositoryException("Description is null");
        }
        try {
            mapper.saveImage(Description);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }

    public void removeImage(final Long id) throws RepositoryException {
        if (id == null) {
            throw new RepositoryException("Description is null");
        }
        try {
            mapper.removeImage(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }

    public void removeAlbum(final Long id) throws RepositoryException {
        if (id == null) {
            throw new RepositoryException("Description is null");
        }
        try {
            mapper.removeAlbum(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }

    public void changeImage(final ImageDescription container) throws  RepositoryException{
        try{
            mapper.changeImage(container.getTitle(), container.getDescription(), container.getAlbum(), container.isHead(), container.getId());
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    public List<AlbumDescription> getAllAlbums() throws RepositoryException{
        try {
            return mapper.getAllAlbums();
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    public AlbumDescription getAlbumById(Long id) throws RepositoryException {
        try{
            return mapper.getAlbumById(id);
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while getting album by id: " + e.getMessage(), e);
        }
    }

    public ImageDescription getImageById(Long id) throws RepositoryException {
        try{
            return mapper.getImageById(id);
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while getting album by id: " + e.getMessage(), e);
        }
    }

    public List<ImageDescription> getAllImages() throws RepositoryException {
        try {
            return mapper.getAllImages();
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    public List<ImageFromAlbumDescription> getImagesFromAlbum(long id) throws RepositoryException {
        try {
            return mapper.getImagesFromAlbum(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

}