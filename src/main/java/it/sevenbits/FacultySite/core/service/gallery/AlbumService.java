package it.sevenbits.FacultySite.core.service.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.Album;
import it.sevenbits.FacultySite.core.domain.gallery.Image;
import it.sevenbits.FacultySite.core.repository.AlbumRepository;
import it.sevenbits.FacultySite.core.service.ServiceException;
import it.sevenbits.FacultySite.core.domain.gallery.ImageModel;
import it.sevenbits.FacultySite.core.service.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository repository;
    @Autowired
    ImageService imgService;
    @Autowired
    ServiceHelper helper;

    public void saveAlbum(final Album album) throws ServiceException {
        try{
            if (album.getTitle() != null && !album.getTitle().isEmpty()) {
                if (album.getId() == null) {
                    repository.addAlbum(album);
                } else {
                    repository.updateAlbum(album);
                }
            }
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean removeAlbum(Long id) throws ServiceException{
        try{
            List<ImageModel> imagesFromDeletingAlbum = imgService.getImagesFromAlbum(id);
            for (ImageModel tmpImg : imagesFromDeletingAlbum)
                imgService.removeImage(tmpImg.getId());
            repository.removeAlbum(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while saving ImageDescriptions: " + e.getMessage(), e);
        }
        return true;
    }

    public Long updateAlbum(Double relationSideWidth,
                            Double relationSideHeight,
                            Long id,
                            String title,
                            String description,
                            List<Long> toDeleteIDs,
                            List<Long> isHeadIDs,
                            List<MultipartFile> files) throws ServiceException {

        Double relationSide = helper.getRelationSide();
        if (relationSideHeight != null && relationSideHeight >= 0
                && relationSideWidth != null && relationSideWidth >= 0) {
            relationSide = relationSideWidth / relationSideHeight;
        }
        Album album = new Album(id, title, description);
        if (album.getId() == null) {
            try {
                saveAlbum(album);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        if (toDeleteIDs != null) {
            for (Long toDeleteId : toDeleteIDs) {
                Image image = imgService.getImageById(toDeleteId);
                if (!image.getAlbum().equals(album.getId()))
                    continue;
                helper.deleteFile(helper.getImgPath() +
                        helper.getImgGalleryFolderPrefix() +
                        helper.getImgBigiPrefix() + image.getLink());
                helper.deleteFile(helper.getImgPath() +
                        helper.getImgGalleryFolderPrefix() +
                        helper.getImgMiniPrefix() + image.getLink());
                imgService.removeImage(toDeleteId);
            }
        }
        try {
            List<ImageModel> images = imgService.getImagesFromAlbum(album.getId());
            for (ImageModel currentImage : images) {
                try {
                    Boolean isHead = false;
                    if (isHeadIDs != null) {
                        for (Long isHeadTmpId : isHeadIDs) {
                            if (isHeadTmpId.equals(currentImage.getId())) {
                                isHead = true;//если присутствует в списке - значит, должен быть заглавной
                                break;
                            }
                        }
                    }
                    Image image = new Image(currentImage.getId(),
                            album.getId(),
                            currentImage.getTitle(),
                            currentImage.getDescription(),
                            currentImage.getCreatingDate(),
                            currentImage.getCreatingTime(),
                            currentImage.getLink(),
                            isHead);
                    imgService.changeImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (album.getId() != null && album.getId() > 0){
            imgService.uploadAndSaveFiles(files, album.getId());
        }
        return album.getId();
    }

    public List<Album> getAllAlbums() throws ServiceException {
        try{
            return repository.getAllAlbums();
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while getting albums: " + e.getMessage(), e);
        }
    }

    public Album getAlbumById(Long id) throws ServiceException {
        try{
            return repository.getAlbumById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while get album by id: " + e.getMessage(), e);
        }
    }

    public List<Album> getAllAlbumsWithUniqueLink() throws ServiceException {
        try{
            List<Album> albums = repository.getAllAlbums();
            List<Album> uniqAlbums = new ArrayList<>();
            Long lastId = null;
            for (Album tmp : albums){
                if (lastId != null)
                    if (lastId.equals(tmp.getId()))
                        continue;
                uniqAlbums.add(tmp);
                lastId = tmp.getId();
            }
            return uniqAlbums;
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while getting uniq albums: " + e.getMessage(), e);
        }

    }

}
