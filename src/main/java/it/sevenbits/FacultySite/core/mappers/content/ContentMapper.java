package it.sevenbits.FacultySite.core.mappers.content;

import it.sevenbits.FacultySite.core.domain.content.Content;
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
    void insertContent(final Content description);

    @Update("UPDATE content_of_pages SET " +
                "title=#{title}, " +
                "description=#{description}, " +
                "mini_content=#{miniContent}, " +
                "image_link=#{imageLink}, " +
                "publish=#{publish} " +
            "WHERE " +
                "id=#{id}")
    void updatePage(final Content description);


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

//
//    @Delete("DELETE FROM content_of_pages WHERE type=#{type};")
//    void removePageByType(final String type);
//
//    @Select("SELECT * FROM content_of_pages WHERE type LIKE #{type};")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "title", property = "title"),
//            @Result(column = "description", property = "description"),
//            @Result(column = "creating_date", property = "creatingDate"),
//            @Result(column = "creating_time", property = "creatingTime"),
//            @Result(column = "type", property = "type"),
//            @Result(column = "image_link", property = "imageLink")
//    })
//    List<Content> getPagesWhichContainType(final @Param("type")String type);
//
//
//    @Select("SELECT * FROM content_of_pages WHERE type LIKE #{type} and publish=#{publish};")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "title", property = "title"),
//            @Result(column = "description", property = "description"),
//            @Result(column = "creating_date", property = "creatingDate"),
//            @Result(column = "creating_time", property = "creatingTime"),
//            @Result(column = "mini_content", property = "miniContent"),
//            @Result(column = "type", property = "type"),
//            @Result(column = "image_link", property = "imageLink")
//    })
//    List<Content> getPagesWhichContainTypeAndPublish(final @Param("type")String type, final @Param("publish")boolean publish);
//
//    @Select("SELECT * FROM content_of_pages where type LIKE #{type} and publish=#{publish} LIMIT #{end} OFFSET #{start} ;")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "title", property = "title"),
//            @Result(column = "description", property = "description"),
//            @Result(column = "creating_date", property = "creatingDate"),
//            @Result(column = "creating_time", property = "creatingTime"),
//            @Result(column = "mini_content", property = "miniContent"),
//            @Result(column = "type", property = "type"),
//            @Result(column = "image_link", property = "imageLink")
//    })
//    List<Content> getPagesWhichContainTypeAndPublishWithBoundaries(final @Param("type")String type, final @Param("publish")boolean publish, @Param("start")Long startPosition, @Param("end")Long lastPosition);
//
//    @Select("SELECT * FROM content_of_pages where type LIKE #{type} LIMIT #{end} OFFSET #{start} ;")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "title", property = "title"),
//            @Result(column = "description", property = "description"),
//            @Result(column = "creating_date", property = "creatingDate"),
//            @Result(column = "creating_time", property = "creatingTime"),
//            @Result(column = "mini_content", property = "miniContent"),
//            @Result(column = "type", property = "type"),
//            @Result(column = "image_link", property = "imageLink")
//    })
//    List<Content> getPagesWhichContainTypeWithBoundaries(final @Param("type")String type, @Param("start")Long startPosition, @Param("end")Long lastPosition);
//
//
//    @Select("select count(*) from content_of_pages;")
//    @Result(column = "count")
//    Long getSumOfAllContent();
//
//    @Select("select count(*) from content_of_pages WHERE type LIKE #{type};")
//    @Result(column = "count")
//    Long getSumOfContentWhichContainType(String type);
//
//    @Select("select count(*) from content_of_pages WHERE type LIKE #{type} and publish=#{publish};")
//    @Result(column = "count")
//    Long getSumOfContentWhichContainTypeAndPublish(@Param("type")String type, @Param("publish")Boolean publish);
//
//    @Select("SELECT * FROM content_of_pages WHERE publish=#{publish};")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "title", property = "title"),
//            @Result(column = "description", property = "description"),
//            @Result(column = "creating_date", property = "creatingDate"),
//            @Result(column = "creating_time", property = "creatingTime"),
//            @Result(column = "mini_content", property = "miniContent"),
//            @Result(column = "type", property = "type"),
//            @Result(column = "image_link", property = "imageLink")
//    })
//    List<Content> getPagesIsPublish(final @Param("publish")boolean publish);

}
