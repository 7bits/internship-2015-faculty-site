package it.sevenbits.FacultySite.core.mappers.tags;


import it.sevenbits.FacultySite.core.domain.tags.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TagMapper {

    @Select("SELECT * FROM tags")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "title", property = "title")
    })
    List<Tag> getAllTags();

    @Select("SELECT * FROM tags WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title")
    })
    Tag getTagById(@Param("id")Long id);

    @Update("UPDATE tags SET title=#{title} WHERE id=#{id}")
    void updateTag(@Param("id")Long id, @Param("title")String title);

    @Delete("DELETE FROM tags WHERE id=#{id} ")
    void deleteTagById(@Param("id")Long id);

    @Insert("INSERT INTO tags (title) VALUES(#{title}")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void insertTag(Tag tag);

    @Select("SELECT t.* FROM " +
            "content_tags ct, " +
            "tags t, " +
            "content_of_pages cop " +
            "WHERE " +
            "ct.content=cop.id AND " +
            "ct.tag=t.id AND " +
            "ct.content=#{contentID};")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title")
    })
    List<Tag> getTagsOfContent(final @Param("contentID")Long idOfContent);

}
