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
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<ContentDescription> getAllPages();

    @Insert("INSERT INTO content_of_pages (title, description, creating_date, creating_time, type, image_link) VALUES (#{title}, #{description}, 'today', 'now', #{type}, #{imageLink})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void saveContentOfPage(final ContentDescription description);

    @Update("UPDATE content_of_pages SET title=#{title}, description=#{description}, type=#{type}, image_link=#{imageLink} WHERE id=#{id}")
    void updatePage(@Param("title")String title, @Param("description")String description, @Param("type")String type, @Param("imageLink")String imageLink, @Param("id")Long id);

    @Select("SELECT * FROM content_of_pages WHERE type=#{type};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTate"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<ContentDescription> getPagesByType(@Param("type")String type);

    @Select("SELECT * FROM content_of_pages WHERE id=#{id};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    ContentDescription getPagesById(@Param("id")Long id);

    @Delete("DELETE FROM content_of_pages WHERE id=#{id};")
    void removePageById(final Long id);

    @Delete("DELETE FROM content_of_pages WHERE type=#{type};")
    void removePageByType(final String type);

    @Select("SELECT * FROM content_of_pages WHERE type LIKE #{type};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<ContentDescription> getPagesWhichContainType(final @Param("type")String type);

}
