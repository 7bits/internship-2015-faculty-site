package it.sevenbits.FacultySite.core.service;

import it.sevenbits.FacultySite.core.service.content.ContentService;
import it.sevenbits.FacultySite.web.domain.gallery.ImageForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class ServiceHelper {

    @Value("${services.imgUpload.targetMiniWidth}")
    private Double imgMiniImgWidth;
    @Value("${services.imgUpload.targetMiniHeight}")
    private Double imgMiniImgHeight;
    private Double relationSide = null;

    @Value("${services.imgConfig.imgGalleryFolderPrefix}")
    private String imgGalleryFolderPrefix;
    @Value("${services.imgConfig.imgFolderPath}")
    private String imgPath;
    @Value("${services.imgConfig.imgBigiPrefix}")
    private String imgBigiPrefix;
    @Value("${services.imgConfig.imgMiniPrefix}")
    private String imgMiniPrefix;

    public Double getRelationSide(){
        return imgMiniImgWidth/imgMiniImgHeight;
    }

    public BufferedImage resizeImage(BufferedImage src, Double destWidth, Double destHeight, Double relationSide){
        if (destWidth == null || destWidth < 1)
            destWidth = imgMiniImgWidth;
        if (destHeight == null || destHeight < 1)
            destHeight = imgMiniImgHeight;
        if (relationSide == null || relationSide < 0)
            relationSide = getRelationSide();
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



    public BufferedImage scaleToSize(BufferedImage src, Double w, Double h, Double scaleX, Double scaleY){
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
            res = cutImageToSquare(res, 0.0, 0.0, w, h, getRelationSide());
        }
        catch (Exception e){
            return src;
        }
        return res;
    }


    public String downloadImage(MultipartFile file, Long albumId) throws ServiceException{
        String link = "";
        if (file != null && !file.isEmpty()) {
            try {
                String name = ContentService.generateNameForFile(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];
                byte[] bytes = file.getBytes();
                File src = new File(imgPath + imgGalleryFolderPrefix+imgBigiPrefix+name);
                File imgMiniPrefixFile = new File(imgPath + imgGalleryFolderPrefix+imgMiniPrefix+name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(src));
                stream.write(bytes);
                stream.close();
                link = name;
                BufferedImage srcImg = ImageIO.read(src);
                BufferedImage imgMiniPrefixImg = resizeImage(srcImg, null, null, relationSide);
                if (imgMiniPrefixFile.createNewFile()) {
                    try {
                        ImageIO.write(imgMiniPrefixImg, type, imgMiniPrefixFile);
                    }
                    catch (Exception e){
                        ImageIO.write(srcImg, type, imgMiniPrefixFile);
                    }
                }
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return link;
    }


    public Boolean deleteFile(String path){
        File src = new File(path);
        Boolean res = src.delete();
        if (!res)
            return false;
        return true;
    }


    public Double getImgMiniImgWidth() {
        return imgMiniImgWidth;
    }

    public Double getImgMiniImgHeight() {
        return imgMiniImgHeight;
    }

    public String getImgGalleryFolderPrefix() {
        return imgGalleryFolderPrefix;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getImgBigiPrefix() {
        return imgBigiPrefix;
    }

    public String getImgMiniPrefix() {
        return imgMiniPrefix;
    }
}
