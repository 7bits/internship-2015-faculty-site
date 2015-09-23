package it.sevenbits.FacultySite.core.mappers.tags;

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
    List<Integer> getContentIDsByTagID(final @Param("tagID")Long tagId);

    @Select("SELECT tag FROM content_tags " +
            "WHERE " +
                "content=#{contentID}")
    List<Integer> getTagIDsByContentID(final @Param("contentID")Long contentId);

    @Insert("INSERT INTO content_tags (" +
                "content, " +
                "tag) " +
            "VALUES (" +
                "#{content}, " +
                "#{tag})")
    void insertPair(final @Param("content")Long contentID, final @Param("tag")Long tagID);

    @Delete("DELETE FROM content_tags WHERE content=#{content} and tag=#{tag}")
    void removePair(final @Param("content")Long contentID, final @Param("tag")Long tagID);


}
