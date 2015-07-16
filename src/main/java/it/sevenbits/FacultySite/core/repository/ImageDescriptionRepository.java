package it.sevenbits.FacultySite.core.repository;

import java.util.List;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;

public interface ImageDescriptionRepository {
    void save(final ImageDescription subscription) throws RepositoryException;
    List<ImageDescription> getAllImages() throws RepositoryException;
    List<ImageFromAlbumDescription> getImagesFromAlbum(long id) throws RepositoryException;
}