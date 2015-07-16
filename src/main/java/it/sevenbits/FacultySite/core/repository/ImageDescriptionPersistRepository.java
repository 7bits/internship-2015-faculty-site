package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.core.mappers.gallery.ImageDescriptionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageDescriptionPersistRepository implements ImageDescriptionRepository {
    private static Logger LOG = Logger.getLogger(ImageDescriptionPersistRepository.class);

    @Autowired
    private ImageDescriptionMapper mapper;


    @Override
    public void save(final ImageDescription Description) throws RepositoryException {
        if (Description == null) {
            throw new RepositoryException("Description is null");
        }
        try {
            mapper.saveImage(Description);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving Description: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImageDescription> getAllImages() throws RepositoryException {
        try {
            return mapper.getAllImages();
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImageFromAlbumDescription> getImagesFromAlbum(long id) throws RepositoryException {
        try {
            return mapper.getImagesFromAlbum(id);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving Descriptions: " + e.getMessage(), e);
        }
    }

}