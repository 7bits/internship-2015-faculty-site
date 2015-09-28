package it.sevenbits.FacultySite.core.mappers.content;

import it.sevenbits.FacultySite.core.domain.content.Content;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ContentTagsMapper {

    @Select("SELECT content FROM content_tags " +
            "WHERE " +
                "tag=#{tagID}")
    List<Long> getContentIDsByTagID(final @Param("tagID")Long tagId);

    @Select("SELECT tag FROM content_tags " +
            "WHERE " +
                "content=#{contentID}")
    List<Long> getTagIDsByContentID(final @Param("contentID")Long contentId);

    @Select("SELECT tag FROM content_tags " +
            "WHERE " +
            "content=#{contentID}" +
            " and tag=#{tagID}")
    List<Long> getTagIDsByContentAndTagIDs(final @Param("contentID")Long contentId, final @Param("tagID")Long tagId);

    @Insert("INSERT INTO content_tags (" +
                "content, " +
                "tag) " +
            "VALUES (" +
                "#{content}, " +
                "#{tag})")
    void insertPair(final @Param("content")Long contentID, final @Param("tag")Long tagID);



    @Delete("DELETE FROM content_tags WHERE content=#{content} and tag=#{tag}")
    void removePair(final @Param("content")Long contentID, final @Param("tag")Long tagID);

    @Delete("DELETE FROM content_tags WHERE tag=#{tag}")
    void removePairForTag(final @Param("tag")Long tagID);

    @Delete("DELETE FROM content_tags WHERE content=#{content}")
    void removePairForContent(final @Param("content")Long contentID);



}
