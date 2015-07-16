package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.ImageSubscription;
import it.sevenbits.FacultySite.core.mappers.ImageSubscriptionMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier(value = "subscriptionPersistRepository")
public class ImageDescriptionPersistRepository implements ImageDescriptionRepository {
    private static Logger LOG = Logger.getLogger(ImageDescriptionPersistRepository.class);

    @Autowired
    private ImageSubscriptionMapper mapper;


    @Override
    public void save(final ImageSubscription subscription) throws RepositoryException {
        if (subscription == null) {
            throw new RepositoryException("Subscription is null");
        }
        try {
            mapper.saveImage(subscription);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImageSubscription> getAllImages() throws RepositoryException {
        try {
            return mapper.getAllImages();
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving subscriptions: " + e.getMessage(), e);
        }
    }
}