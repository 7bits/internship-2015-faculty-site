package it.sevenbits.FacultySite.core.repository;

import java.util.List;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;

public interface ImageDescriptionRepository {
    void saveImage(final ImageDescription subscription) throws RepositoryException;
    void removeImage(final Long id) throws RepositoryException;
    void changeImage(final ImageDescription container) throws RepositoryException;
    void addAlbum(final AlbumDescription album) throws RepositoryException;
    List<AlbumDescription> getAllAlbums() throws RepositoryException;
    AlbumDescription getAlbumById(Long id) throws RepositoryException;
    ImageDescription getImageById(Long id) throws RepositoryException;
    List<ImageDescription> getAllImages() throws RepositoryException;
    List<ImageFromAlbumDescription> getImagesFromAlbum(long id) throws RepositoryException;
}