package it.sevenbits.FacultySite.web.service.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.core.repository.ImageDescriptionRepository;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageDescriptionRepository repository;

    static public final Double relationSide = (double)((float)16)/9;
    static public final Double miniImgWidth = 480.0;
    static public final Double miniImgHeight = miniImgWidth/relationSide;


    final static String bigi = "gallery-page-gallery-photo/bigi/";
    final static String mini = "gallery-page-gallery-photo/mini/";
    //final static String path = "/home/internship-2015-faculty-site/src/main/resources/public/img/";//for server
    final static String path = "src/main/resources/public/img/";

    public static BufferedImage resizeImage(BufferedImage src, Double destWidth, Double destHeight, Double relationSide){
        if (destWidth == null || destWidth < 1)
            destWidth = miniImgWidth;
        if (destHeight == null || destHeight < 1)
            destHeight = miniImgHeight;
        if (relationSide == null || relationSide < 0)
            relationSide = ImageService.relationSide;
        src = cutImageToSquare(src, null, null, null, null, relationSide);
        src = scaleToSize(src, destWidth, destHeight, null, null);
        return src;
    }


    public static BufferedImage cutImageToSquare(BufferedImage src, Double startX, Double startY, Double cutW, Double cutH, Double relationSide){
        double w = src.getRaster().getWidth();
        double h = src.getRaster().getHeight();
        if (cutW == null || cutW <= 0) {
            cutW = w;
        }
        if (cutH == null || cutH <= 0){
            cutH = h;
        }
        if (relationSide == null || relationSide < 0)
            relationSide = 1.0;
        if (relationSide > 1) {
            cutH = cutW/relationSide;
            if (cutH > h){
                cutW = cutW*(h/cutH);
                cutH = h;
            }
        }
        else{
            cutW = cutH*relationSide;
            if (cutW > w){
                cutH = cutH*(w/cutW);
                cutW = w;
            }
        }
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
        if (w >= src.getWidth() && h >= src.getHeight())
            return src;
        if (scaleX == null || scaleX < 0)
            scaleX = w / src.getWidth();
        if (scaleY == null || scaleY < 0)
            scaleY = h / src.getHeight();
        scales.scale(scaleX, scaleY);
        AffineTransformOp resScale = new AffineTransformOp(scales, AffineTransformOp.TYPE_BILINEAR);
        res = resScale.filter(src, res);
        try {
            res = cutImageToSquare(res, 0.0, 0.0, w, h, relationSide);
        }
        catch (Exception e){
            return src;
        }
        return res;
    }

    public void saveAlbum(final AlbumDescription album) throws ServiceException{
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

    public void saveImage(final ImageDescriptionForm form) throws ServiceException {
        final ImageDescription ImageDescription = new ImageDescription();
        ImageDescription.setTitle(form.getTitle());
        ImageDescription.setDescription(form.getDescription());
        ImageDescription.setAlbum(form.getAlbum());
        ImageDescription.setLink(form.getLink());
        ImageDescription.setIsHead(form.isHead());
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

    public boolean removeAlbum(Long id) throws ServiceException{
        try{
            List<ImageFromAlbumDescriptionModel> imagesFromDeletingAlbum = getImagesFromAlbum(id);
            for (ImageFromAlbumDescriptionModel tmpImg : imagesFromDeletingAlbum)
                removeImage(tmpImg.getId());
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
                            List<Long> isHeadIDs) throws ServiceException {

        Double relationSide = ImageService.relationSide;
        if (relationSideHeight != null && relationSideHeight >= 0
                && relationSideWidth != null && relationSideWidth >= 0) {

            relationSide = relationSideWidth / relationSideHeight;
        }
        AlbumDescription album = new AlbumDescription(id, title, description);
        if (album.getId() == null) {
            try {
                saveAlbum(album);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        if (toDeleteIDs != null) {
            for (Long toDeleteId : toDeleteIDs) {
                ImageDescription image = getImageById(toDeleteId);
                if (!image.getAlbum().equals(album.getId()))
                    continue;
                deleteFile(path + bigi + image.getLink());
                deleteFile(path + mini + image.getLink());
                removeImage(toDeleteId);
            }
        }
        try {
            List<ImageFromAlbumDescriptionModel> images = getImagesFromAlbum(album.getId());
            for (ImageFromAlbumDescriptionModel currentImage : images) {
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
                    ImageDescription image = new ImageDescription(currentImage.getId(),
                            album.getId(),
                            currentImage.getTitle(),
                            currentImage.getDescription(),
                            currentImage.getCreating_date(),
                            currentImage.getCreating_time(),
                            currentImage.getLink(),
                            isHead);
                    changeImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return album.getId();
    }


    public static Boolean deleteFile(String path) throws ServiceException{
        try {
            File src = new File(path);
            Boolean res = src.delete();
            if (!res)
                throw new ServiceException("Can't remove file: " + path, null);
        }
        catch (ServiceException e){
            throw new ServiceException(e.getMessage(), e);
        }
        return true;
    }


    public void uploadFiles(List<MultipartFile> files, Long albumId){
        if (albumId == null || albumId < 1){
            return;
        }
        for (MultipartFile file : files){
            if (file.getOriginalFilename().isEmpty()) {
                continue;
            }
            try {
                downloadImage(file, albumId, relationSide);
            }
            catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }

    public String downloadImage(MultipartFile file, Long albumId, Double relationSide) throws ServiceException{
        String toOut = "";
        if (file != null && !file.isEmpty()) {
            try {
                String name = ContentOfPagesService.generateName(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];

                byte[] bytes = file.getBytes();
                File src = new File(path+bigi+name);
                File miniFile = new File(path+mini+name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(src));
                stream.write(bytes);
                stream.close();
                ImageDescriptionForm image = new ImageDescriptionForm();
                image.setLink(name);
                image.setAlbum(albumId);
                saveImage(image);
                BufferedImage srcImg = ImageIO.read(src);
                BufferedImage miniImg = ImageService.resizeImage(srcImg, null, null, relationSide);
                if (miniFile.createNewFile()) {
                    try {
                        ImageIO.write(miniImg, type, miniFile);
                    }
                    catch (Exception e){
                        ImageIO.write(srcImg, type, miniFile);
                    }
                }
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return toOut;
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

    public ImageDescription getImageById(Long id) throws ServiceException {
        try{
            return repository.getImageById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while get image by id: " + e.getMessage(), e);
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

    public List<ImageFromAlbumDescriptionModel>getImagesFromAlbum(Long id) throws ServiceException {
        try {
            if (id == null || id < 1)
                return new ArrayList<>();
            List<ImageFromAlbumDescription> descriptions = repository.getImagesFromAlbum(id);
            List<ImageFromAlbumDescriptionModel> models = new ArrayList<>(descriptions.size());
            for (int i = descriptions.size()-1; i >= 0 ; i--) {
                ImageFromAlbumDescription s = descriptions.get(i);
                models.add(new ImageFromAlbumDescriptionModel(s.getId(),
                        s.getAlbum_title(),
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