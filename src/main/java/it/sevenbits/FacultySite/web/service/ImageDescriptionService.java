package it.sevenbits.FacultySite.web.service;

import it.sevenbits.FacultySite.core.domain.ImageDescription;
import it.sevenbits.FacultySite.core.repository.ImageDescriptionRepository;
import it.sevenbits.FacultySite.web.domain.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.ImageDescriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
            List<ImageDescription> ImageDescriptions = repository.getAllImages();
            List<ImageDescriptionModel> models = new ArrayList<>(ImageDescriptions.size());
            for (ImageDescription s: ImageDescriptions) {
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
}