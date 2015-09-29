package it.sevenbits.FacultySite.core.service.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.Image;
import it.sevenbits.FacultySite.core.repository.ImageRepository;
import it.sevenbits.FacultySite.core.service.ServiceHelper;
import it.sevenbits.FacultySite.web.domain.gallery.ImageForm;
import it.sevenbits.FacultySite.core.service.ServiceException;
import it.sevenbits.FacultySite.core.domain.gallery.ImageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository repository;
    @Autowired
    private ServiceHelper helper;


    public void saveImage(final ImageModel form) throws ServiceException {
        final Image ImageDescription = new Image();
        ImageDescription.setTitle(form.getTitle());
        ImageDescription.setDescription(form.getDescription());
        ImageDescription.setAlbum(form.getAlbum());
        ImageDescription.setLink(form.getLink());
        ImageDescription.setIsHead(form.getIsHead());
        try {
            repository.saveImage(ImageDescription);
        } catch (Exception e) {
            throw new ServiceException("An error occurred while saving ImageDescription: " + e.getMessage(), e);
        }
    }

    public boolean removeImage(Long id) throws ServiceException{
        try{
            repository.removeImage(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while saving ImageDescriptions: " + e.getMessage(), e);
        }
        return true;
    }

    public Image getImageById(Long id) throws ServiceException {
        try{
            return repository.getImageById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while get image by id: " + e.getMessage(), e);
        }
    }

    public List<ImageModel> getAllImages() throws ServiceException {
        try {
            List<Image> descriptions = repository.getAllImages();
            List<ImageModel> models = new ArrayList<>(descriptions.size());
            for (Image s: descriptions) {
                models.add(new ImageModel(
                        s.getId(),
                        s.getAlbum(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getCreatingDate(),
                        s.getCreatingTime(),
                        s.getLink(),
                        s.isHead()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while retrieving Images: " + e.getMessage(), e);
        }
    }

    public void uploadAndSaveFiles(List<MultipartFile> files, Long albumId){
        if (albumId == null || albumId < 1){
            return;
        }
        for (MultipartFile file : files){
            if (file.getOriginalFilename().isEmpty()) {
                continue;
            }
            try {
                helper.downloadImage(file, albumId);
            }
            catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }



    public boolean changeImage(Image container) throws ServiceException{
        try{
            repository.changeImage(container);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while changing image: " + e.getMessage(), e);
        }
        return true;
    }

    public List<ImageModel> getImagesFromAlbum(Long id) throws ServiceException {
        try {
            if (id == null || id < 1)
                return new ArrayList<>();
            List<Image> descriptions = repository.getImagesFromAlbum(id);
            List<ImageModel> models = new ArrayList<>();
            for (int i = descriptions.size()-1; i >= 0 ; i--) {
                Image s = descriptions.get(i);
                models.add(new ImageModel(s.getId(),
                        s.getAlbum(),
                        s.getTitle(),
                        s.getDescription(),
                        s.getCreatingDate(),
                        s.getCreatingTime(),
                        s.getLink(),
                        s.isHead()
                ));
            }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get image description: " + e.getMessage(), e);
        }
    }

}