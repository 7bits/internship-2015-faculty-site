package it.sevenbits.FacultySite.web.service.contentOfPages;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    ContentOfPagesService contentOfPagesService;

    public static Long calculateStartFromTheEnd(Integer sumOfNews, Long current, Integer countOnPage) throws ServiceException{
        try {
            Long start = (long) sumOfNews - (current) * countOnPage;
            //Рассчитываем с конца - получается, если start = 2, sum = 10, count = 15,
            // то взяты будут записи с 14 до 4 (10 записей, начиная со второй с конца)
            if (start < 1) {
                start = (long) 0;
            }
            return start;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static Long calculateEndFromTheEnd(Long start, Integer sumOfNews, Integer countOnPage) throws ServiceException{
        try {
            Long end = countOnPage + start;
            if (end > sumOfNews)
                end = (long) sumOfNews;
            return end;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static List<String> generatePagination(Long current, Integer sum){
        if (current == null || current < 1 || sum == null || sum < 1){
            current = (long)1;
            sum = 1;
        }

        List<String> pagination = new ArrayList<>();
        pagination.add("<");
        pagination.add("1");
        if (current-1 > 2){
            pagination.add("...");
        }
        for (long i = current-2; i<current+3 && i <= sum; i++)
            if (i > 1) {
                pagination.add("" + i);
            }
        if (sum-current > 2){
            pagination.add("...");
        }
        if (!pagination.get(pagination.size()-1).equals(""+sum))
            pagination.add(""+sum);
        pagination.add(">");
        return pagination;
    }

    public Model constructData(Long current,
                               String page,
                               String type,
                               Boolean publish,
                               Integer countOnPage,
                               Model model) throws ServiceException{
        try {

            if (type == null) {
                type = "News:";
            }
            if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
                model.addAttribute("root", true);
                model.addAttribute("canCreate", true);
                model.addAttribute("createType", type);
            }
            if (page == null)
                page = "1";
            Long sumOfNews = (long) 0;
            sumOfNews = contentOfPagesService.getSumOfPages(type, publish);
            current = calculateCurrentPage(page, current, sumOfNews);

            List<String> pagination = new ArrayList<>();
            Integer sumOfPages = (int) Math.ceil(sumOfNews / ((float) countOnPage));
            if (sumOfPages < 1)
                sumOfPages = 1;
            pagination = generatePagination(current, sumOfPages);
            Long start = calculateStartFromTheEnd(sumOfNews.intValue(), current, countOnPage);
            Long end = calculateEndFromTheEnd(start, sumOfNews.intValue(), countOnPage);
            List<ContentDescriptionModel> content = getContentByType(type, publish, start, end);
            model.addAttribute("content", content);
            model.addAttribute("pagination", pagination);
            model.addAttribute("current", current + "");
            model.addAttribute("sumOfPages", sumOfPages + "");
            return model;
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public ContentDescription getContentById(Long id) throws ServiceException{
        try{
            return contentOfPagesService.getPageById(id);
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<ContentDescriptionModel> getContentByType(String type,
                                                                 Boolean publish,
                                                                 Long start,
                                                                 Long count) throws ServiceException{
        try{
            if (type == null){
                type = "";
            }
            type = "%" + type + "%";
            Long sum = contentOfPagesService.getSumOfPages(type, publish);
            if (start>sum){
                return new ArrayList<>();
            }
            if (sum-start < count) {
                count = sum-start;
            }

            //Переворачиваем последовательность
            List<ContentDescriptionModel> tmp = contentOfPagesService.getPagesWhichContainTypeIsPublishWithBoundaries(type, publish, start, count);
            List<ContentDescriptionModel> result = new ArrayList<>();
            for (int i = tmp.size()-1; i>=0; i--){
                result.add(tmp.get(i));
            }
            return result;
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    public Long calculateCurrentPage(String checked, Long current, Long sumOfNews)throws ServiceException{
        if (current == null)
            current = (long)1;
        if (checked.equals("<")){
            if (current > 1){
                current--;
            }
        }
        else {
            if (checked.equals(">")) {
                if (current < sumOfNews) {
                    current++;
                }
            } else{
                try {
                    current = Long.parseLong(checked);
                }
                catch (Exception e){
                    throw new ServiceException(e.getMessage(), e);
                }
            }
        }
        return current;
    }


}
