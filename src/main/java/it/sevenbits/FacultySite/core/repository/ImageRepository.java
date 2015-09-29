package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.gallery.Image;
import it.sevenbits.FacultySite.core.mappers.gallery.ImageMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepository {
    private static Logger LOG = Logger.getLogger(ImageRepository.class);

    @Autowired
    private ImageMapper mapper;

    public void saveImage(final Image Description) throws RepositoryException {
        try {
            mapper.saveImage(Description);
        } catch (Exception e) {
            throw new RepositoryException("Can't save image: " + e.getMessage(), e);
        }
    }

    public void removeImage(final Long id) throws RepositoryException {
        try {
            mapper.removeImage(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }


    public void changeImage(final Image container) throws  RepositoryException{
        try{
            mapper.changeImage(container.getTitle(), container.getDescription(), container.getAlbum(), container.isHead(), container.getId());
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    public Image getImageById(Long id) throws RepositoryException {
        try{
            return mapper.getImageById(id);
        }
        catch (Exception e){
            throw new RepositoryException("An error occurred while getting album by id: " + e.getMessage(), e);
        }
    }

    public List<Image> getAllImages() throws RepositoryException {
        try {
            return mapper.getAllImages();
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    public List<Image> getImagesFromAlbum(long id) throws RepositoryException {
        try {
            return mapper.getImagesFromAlbum(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

}