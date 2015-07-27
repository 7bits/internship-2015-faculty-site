package it.sevenbits.FacultySite.core.mappers.contentOfPages;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContentOfPagesMapper {

    @Select("SELECT * FROM content_of_pages;")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTate"),
            @Result(column = "type", property = "type")
    })
    List<ContentDescription> getAllPages();

    @Insert("INSERT INTO content_of_pages (title, description, creating_date, creating_time, type) VALUES (#{title}, #{description}, #{creatingDate}, #{creatingTime}, #{type})")
    void saveContentOfPage(final ContentDescription description);

}
