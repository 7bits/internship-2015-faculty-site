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
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink"),
            @Result(column = "publish", property = "publish")
    })
    List<ContentDescription> getAllPages();

    @Insert("INSERT INTO content_of_pages (title, description, creating_date, creating_time, mini_content, type, image_link, publish) VALUES (#{title}, #{description}, 'today', 'now', #{miniContent}, #{type}, #{imageLink}, #{publish})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void saveContentOfPage(final ContentDescription description);

    @Update("UPDATE content_of_pages SET title=#{title}, description=#{description}, type=#{type}, mini_content=#{miniContent}, image_link=#{imageLink}, publish=#{publish} WHERE id=#{id}")
    void updatePage(@Param("title")String title, @Param("description")String description, @Param("type")String type, @Param("miniContent")String miniContent , @Param("imageLink")String imageLink, @Param("publish")Boolean publish, @Param("id")Long id);


    @Select("SELECT * FROM content_of_pages WHERE type=#{type};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTate"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink"),
            @Result(column = "publish", property = "publish")
    })
    List<ContentDescription> getPagesByType(@Param("type")String type);

    @Select("SELECT * FROM content_of_pages WHERE id=#{id};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink"),
            @Result(column = "publish", property = "publish")
    })
    ContentDescription getPageById(@Param("id")Long id);

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


    @Select("SELECT * FROM content_of_pages WHERE type LIKE #{type} and publish=#{publish};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<ContentDescription> getPagesWhichContainTypeAndPublish(final @Param("type")String type, final @Param("publish")boolean publish);

    @Select("SELECT * FROM content_of_pages WHERE publish=#{publish};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "type", property = "type"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<ContentDescription> getPagesIsPublish(final @Param("publish")boolean publish);

}
