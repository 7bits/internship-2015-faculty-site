package it.sevenbits.FacultySite.web.service.contentOfPages;

import com.sun.jndi.toolkit.url.Uri;
import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.repository.ContentOfPagesRepository;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import it.sevenbits.FacultySite.web.service.gallery.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ContentOfPagesService {
    @Autowired
    private ContentOfPagesRepository repository;
    //public static final String imgPath = "/home/internship-2015-faculty-site/src/main/resources/public/img/";//for server
    public static final String imgPath = "src/main/resources/public/img/";
    public static final String imgBigiPrefix = "bigi/";
    public static final String imgMiniPrefix = "mini/";

    public List<ContentDescriptionModel> getAllPages() throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getAllPages();
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesByType(String type) throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getPagesByType(type);
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesWhichContainType(String type) throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getPagesWhichContainType(type);
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink(), tmp.getMiniContent(), tmp.getPublish()));

                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesIsPublish(Boolean publish) throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getPagesIsPublish(publish);
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink(), tmp.getMiniContent(), tmp.getPublish()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesWhichContainTypeIsPublish(String type, Boolean publish) throws ServiceException {
        try {
            List<ContentDescription> pages = repository.getPagesWhichContainTypeIsPublish(type, publish);
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink(), tmp.getMiniContent(), tmp.getPublish()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesWhichContainTypeIsPublishWithBoundaries(String type, Boolean publish, Long start, Long count) throws ServiceException {
        try {

            List<ContentDescription> pages = repository.getPagesWhichContainTypeIsPublishWithBoundaries(type, publish, start, count);
            List<ContentDescriptionModel> models = new ArrayList<>();
            for (ContentDescription tmp : pages)
                try {
                    models.add(new ContentDescriptionModel(tmp.getId(), tmp.getTitle(), tmp.getDescription(), tmp.getCreatingDate(), tmp.getCreatingTime(), tmp.getType(), tmp.getImageLink(), tmp.getMiniContent(), tmp.getPublish()));
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            return models;
        } catch (Exception e) {
            throw new ServiceException("An error occurred while get content: " + e.getMessage(), e);
        }
    }

    public Long getSumOfPages(String type, Boolean publish) throws ServiceException{
        try{
            if (type == null)
                type = "";
            type = "%" + type + "%";
            return repository.getSumOfRecords(type, publish);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getPagesWhichContainTypeWithCountWithPublish(String type, Long count, Boolean publish) throws ServiceException{
        try {
            List<ContentDescriptionModel> all = getPagesWhichContainTypeIsPublish(type, publish);
            if (all.size() < count)
                return all;
            List<ContentDescriptionModel> res = new ArrayList<>();
            for (int i = all.size()-1, j = 0; j<count && i >= 0; i--){
                res.add(all.get(i));
            }
            return res;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public ContentDescription updateContent(Long id, String title, String content, String miniContent, String imageLink, String type, Boolean publish) throws ServiceException{
        try {
            if ((id == null || id < 1) || (title == null || content == null || content.isEmpty() ))
                return null;
            ContentDescription page = getPageById(id);
            if (!type.isEmpty() && !content.isEmpty()) {
                page.setDescription(content);
                page.setMiniContent(miniContent);
                page.setTitle(title);
                page.setType(type);
                page.setPublish(publish);
                page.setImageLink(imageLink);
            }
            updatePage(page);
            return page;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static String generateName(String input){
        String name = input;
        String partsOfName[] = name.split("\\.");
        name = "." + partsOfName[partsOfName.length-1];
        name = UUID.randomUUID().toString() + name;
        return name;
    }

    public String uploadFile(MultipartFile file) throws ServiceException{
        if (file != null && !file.isEmpty()) {
            try {
                String name = generateName(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];
                byte[] bytes = file.getBytes();
                File src = new File(imgPath+imgBigiPrefix+name);
                File miniFile = new File(imgPath+imgMiniPrefix+name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(src));
                stream.write(bytes);
                stream.close();
                BufferedImage srcImg = ImageIO.read(src);
                BufferedImage miniImg = ImageService.resizeImage(srcImg, null, null, null);
                if (miniFile.createNewFile()) {
                    ImageIO.write(miniImg, type, miniFile);
                }
                return name;
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return "";
    }

    public Model createEditContent(String createType,
                                   Model model) {
        if (createType != null && !createType.isEmpty()) {
            model.addAttribute("type", createType);
        }
        return model;
    }

    public Model redactContent(Long redactId, Model model){
        if (redactId > 0) {
            try {
                ContentDescription res = getPageById(redactId);
                model.addAttribute("content", res.getDescription());
                model.addAttribute("title", res.getTitle());
                model.addAttribute("type", res.getType());
                model.addAttribute("miniContent", res.getMiniContent());
                model.addAttribute("imageLink", res.getImageLink());
                model.addAttribute("publish", res.getPublish());
                model.addAttribute("id", res.getId());
            } catch (Exception e) {
            }
        }
        return model;
    }

    public ContentDescription editContentAction(String imageLink,
                                    Long deleteId,
                                    String type,
                                    Long id,
                                    String title,
                                    String content,
                                    String miniContent,
                                    Boolean publish){
        ContentDescription res = null;
        if (imageLink != null && imageLink.isEmpty())
            imageLink = "/img/lost-page.png";
        if (deleteId != null && deleteId > 0){
            removeContent(deleteId);
            return null;
        }
        try {
            if (id == null || id < 1) {
                res = createContent(title, content, miniContent, imageLink, type, publish);
                if (res == null){
                    res = new ContentDescription(title, content, miniContent, imageLink, type, publish);
                }
            } else {
                res = updateContent(id, title, content, miniContent, imageLink, type, publish);
                if (res == null)
                    res = getPageById(id);
            }
        }
        catch (ServiceException e){
        }
        return res;
    }

    public Boolean removeContent(Long deleteId){
        if (deleteId != null && deleteId > 0){
            try{
                ContentDescription res = getPageById(deleteId);
                String type = res.getType();
                removePageById(res.getId());
                return true;
            }
            catch (Exception e) {
            }
        }
        return false;
    }



    public List<String> getImgPath(String name){
        List<String> paths = new ArrayList<>();

        File bigiFile = new File(imgPath+imgBigiPrefix+name);
        File miniFile = new File(imgPath+imgMiniPrefix+name);
        if (bigiFile.exists()){
            paths.add(bigiFile.getPath());
        }
        if (miniFile.exists()){
            paths.add(miniFile.getPath());
        }
        return paths;
    }

    public List<String> uploadFiles(List<MultipartFile> files) throws ServiceException{
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String name = uploadFile(file);
                List<String> tmpPaths = getImgPath(name);
                paths.addAll(tmpPaths);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return paths;
    }


    public ContentDescription createContent(String title, String content, String miniContent, String imageLink, String type, Boolean publish) throws ServiceException{
        Long id;
        if (title == null || content == null || content.isEmpty() || type == null || miniContent == null) {
            return null;
        }
        try {
            id = saveContentOfPage(title, content, imageLink, miniContent, type, publish);
            if (id == null || id < 1)
                return null;
            return getPageById(id);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Long saveContentOfPage(String title, String description, String miniContent, Boolean publish, String type) throws ServiceException{
        try{
            ContentDescription res = new ContentDescription(title, description, miniContent, type);
            repository.saveContent(res);
            return res.getId();
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while save content: " + e.getMessage(), e);
        }
    }

    public Long saveContentOfPage(String title, String description, String imageLink, String miniContent, String type, Boolean publish) throws ServiceException{
        try{
            ContentDescription res = new ContentDescription(title, description, miniContent, imageLink, type, publish);
            repository.saveContent(res);
            return res.getId();
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while save content: " + e.getMessage(), e);
        }
    }

    public ContentDescription getPageById(Long id) throws ServiceException{
        try{
            return repository.getPageById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while save content: " + e.getMessage(), e);
        }
    }

    public void updatePage(ContentDescription description) throws ServiceException{
        try{
            repository.updatePage(description.getTitle(), description.getDescription(), description.getType(), description.getMiniContent(), description.getImageLink(), description.getPublish(), description.getId());

        }
        catch (Exception e){
            throw new ServiceException("An error occurred while save content: " + e.getMessage(), e);
        }
    }

    public void removePageById(Long id) throws ServiceException{
        try{
            repository.removePageById(id);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while delete content: " + e.getMessage(), e);
        }
    }

    public void removePageByType(String type) throws ServiceException{
        try{
            repository.removePageByType(type);
        }
        catch (Exception e){
            throw new ServiceException("An error occurred while delete content: " + e.getMessage(), e);
        }
    }

}