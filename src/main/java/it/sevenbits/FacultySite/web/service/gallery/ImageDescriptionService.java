package it.sevenbits.FacultySite.web.service.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.core.repository.ImageDescriptionRepository;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageDescriptionService {
    @Autowired
    private ImageDescriptionRepository repository;

    static public final double relationSide = 1/1;
    static public final double miniImgWidth = 240;
    static public final double miniImgHeight = miniImgWidth/relationSide;

    public static BufferedImage resizeImage(BufferedImage src, Double destWidth, Double destHeight){
        if (destWidth == null || destWidth < 1)
            destWidth = miniImgWidth;
        if (destHeight == null || destHeight < 1)
            destHeight = miniImgHeight;
        src = cutImageToSquare(src, null, null, null, null);
        src = scaleToSize(src, destWidth, destHeight, null, null);
        return src;
    }

    public static BufferedImage cutImageToSquare(BufferedImage src, Double startX, Double startY, Double cutW, Double cutH){
        double w = src.getWidth();
        double h = src.getHeight();
        if (cutW == null || cutW <= 0) {
            cutW = w;
        }
        if (cutH == null || cutH <= 0){
            cutH = h;
        }
        if (cutW>cutH)
            cutW = cutH;
        else
            cutH = cutW;
        if (startX == null || startX < 0) {
            startX = (w - cutW) / 2;
        }
        if (startY == null || startY < 0){
            startY = (h-cutH)/2;
        }
        src = src.getSubimage(startX.intValue(), startY.intValue(), cutW.intValue(), cutH.intValue());
        return src;
    }

    public static BufferedImage scaleToSize(BufferedImage src, Double w, Double h, Double scaleX, Double scaleY){
        BufferedImage res = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        AffineTransform scales = new AffineTransform();
        if (scaleX != null && scaleY != null && (scaleX > 0 && scaleY > 0)) {
            scales.scale(scaleX, scaleY);
        }
        else {
            scales.scale(w / src.getWidth(), h / src.getHeight());
        }
        AffineTransformOp resScale = new AffineTransformOp(scales, AffineTransformOp.TYPE_BILINEAR);
        res = resScale.filter(src, res);
        res = cutImageToSquare(res, 0.0, 0.0, w, h);
        return res;
    }

    public void saveAlbum(final AlbumDescription album) throws ServiceException{
        try{
            if (album.getId() == null && album.getTitle() != null && album.getDescription() != null && !album.getTitle().isEmpty()){
                repository.addAlbum(album);
            }
            else{

            }
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void saveImage(final ImageDescriptionForm form) throws ServiceException {
        final ImageDescription ImageDescription = new ImageDescription();
        ImageDescription.setTitle(form.getTitle());
        ImageDescription.setDescription(form.getDescription());
        ImageDescription.setAlbum(form.getAlbum());
        ImageDescription.setLink(form.getLink());
        ImageDescription.setIs_head(form.is_head());
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

    public List<AlbumDescription> getAllAlbums() throws ServiceException {
        try{
            return repository.getAllAlbums();
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while getting albums: " + e.getMessage(), e);
        }
    }

    public AlbumDescription getAlbumById(Long id) throws ServiceException {
        try{
            return repository.getAlbumById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while get album by id: " + e.getMessage(), e);
        }
    }

    public List<AlbumDescription> getAllAlbumsWithUniqueLink() throws ServiceException {
        try{
            List<AlbumDescription> albums = repository.getAllAlbums();
            List<AlbumDescription> uniqAlbums = new ArrayList<>();
            Long lastId = null;
            for (AlbumDescription tmp : albums){
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

    public boolean changeImage(ImageDescription container) throws ServiceException{
        try{
            repository.changeImage(container);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while changing image: " + e.getMessage(), e);
        }
        return true;
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
            throw new ServiceException("An error occurred while get image description: " + e.getMessage(), e);
        }
    }

}