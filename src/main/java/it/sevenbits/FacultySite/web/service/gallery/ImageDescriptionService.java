package it.sevenbits.FacultySite.web.service.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.core.repository.ImageDescriptionRepository;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageDescriptionService {
    @Autowired
    private ImageDescriptionRepository repository;

    public void save(final ImageDescriptionForm form) throws ServiceException {
        final ImageDescription ImageDescription = new ImageDescription();
        ImageDescription.setId(form.getId());
        ImageDescription.setTitle(form.getTitle());
        ImageDescription.setDescription(form.getDescription());
        ImageDescription.setAlbum(form.getAlbum());
        ImageDescription.setLink(form.getLink());
        ImageDescription.setIs_head(form.is_head());
        try {
            repository.save(ImageDescription);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving ImageDescription: " + e.getMessage(), e);
        }
    }

    public List<ImageDescriptionModel>getAllImages() throws ServiceException {
        try {
            List<ImageDescription> descriptions = repository.getAllImages();
            List<ImageDescriptionModel> models = new ArrayList<>(descriptions.size());
            for (ImageDescription s: descriptions) {
                models.add(new ImageDescriptionModel(
                        (long)s.getId(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getLink()
                ));
            }

            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving ImageDescriptions: " + e.getMessage(), e);
        }
    }

    public List<ImageFromAlbumDescriptionModel>getImagesFromAlbum(long id) throws ServiceException {
        try {
            List<ImageFromAlbumDescription> descriptions = repository.getImagesFromAlbum(id);
            List<ImageFromAlbumDescriptionModel> models = new ArrayList<>(descriptions.size());
            for (ImageFromAlbumDescription s: descriptions) {
                models.add(new ImageFromAlbumDescriptionModel(
                        (long)s.getId(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getLink(),
                        s.getAlbum_title()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving ImageDescriptions: " + e.getMessage(), e);
        }
    }

}