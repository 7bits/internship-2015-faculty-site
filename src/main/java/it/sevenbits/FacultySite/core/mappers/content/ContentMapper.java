package it.sevenbits.FacultySite.core.mappers.content;

import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.content.ContentModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContentMapper {

    @Select("SELECT * FROM content_of_pages;")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "image_link", property = "imageLink"),
            @Result(column = "publish", property = "publish")
    })
    List<Content> getAllContent();

    @Insert("INSERT INTO content_of_pages (" +
                "title, " +
                "description, " +
                "creating_date, " +
                "creating_time, " +
                "mini_content, " +
                "image_link, " +
                "publish) " +
            "VALUES (" +
                "#{title}, " +
                "#{description}, " +
                "'today', " +
                "'now', " +
                "#{miniContent}, " +
                "#{imageLink}, " +
                "#{publish})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void insertContent(final ContentModel description);

    @Update("UPDATE content_of_pages SET " +
                "title=#{title}, " +
                "description=#{description}, " +
                "mini_content=#{miniContent}, " +
                "image_link=#{imageLink}, " +
                "publish=#{publish} " +
            "WHERE " +
                "id=#{id}")
    void updateContent(final ContentModel description);


    @Select("SELECT * FROM content_of_pages " +
            "WHERE " +
                "id=#{id};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "image_link", property = "imageLink"),
            @Result(column = "publish", property = "publish")
    })
    Content getContentById(final @Param("id")Long id);

    @Delete("DELETE FROM content_of_pages " +
            "WHERE " +
                "id=#{id};")
    void removeContentById(final @Param("id")Long id);

    @Select("SELECT * FROM content_of_pages WHERE publish=#{publish};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime"),
            @Result(column = "mini_content", property = "miniContent"),
            @Result(column = "image_link", property = "imageLink")
    })
    List<Content> getPublishedContent(final @Param("publish")boolean publish);


    @Select("SELECT cop.* FROM " +
            "content_tags ct, " +
            "tags t, " +
            "content_of_pages cop " +
            "WHERE " +
            "ct.content=cop.id AND " +
            "ct.tag=t.id AND " +
            "ct.tag=#{tagID};")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "title", property = "title"),
        @Result(column = "description", property = "description"),
        @Result(column = "creating_date", property = "creatingDate"),
        @Result(column = "creating_time", property = "creatingTime"),
        @Result(column = "mini_content", property = "miniContent"),
        @Result(column = "image_link", property = "imageLink")
    })
    List<Content> getContentsByTag(final @Param("tagID")Long idOfTag);


}
