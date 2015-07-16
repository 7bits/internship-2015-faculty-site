package it.sevenbits.FacultySite.core.repository;

import java.util.List;
import it.sevenbits.FacultySite.core.domain.ImageDescription;

public interface ImageDescriptionRepository {
    void save(final ImageDescription subscription) throws RepositoryException;
    List<ImageDescription> getAllImages() throws RepositoryException;
}