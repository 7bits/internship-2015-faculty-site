package it.sevenbits.FacultySite.core.mappers.gallery;


import it.sevenbits.FacultySite.core.domain.gallery.Album;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AlbumMapper {

    @Select("SELECT * FROM album")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    List<Album> getAllAlbums();

    @Select("SELECT id, title, description, creating_date, creating_time FROM album WHERE id=#{albumId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    Album getAlbumById(@Param("albumId")Long id);

    @Insert("INSERT INTO album (title, description, creating_date, creating_time) VALUES (#{title}, #{description}, 'today', 'now')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void addAlbum(final Album album);

    @Update("UPDATE album SET title=#{title}, description=#{description}) WHERE id=#{id}")
    void updateAlbum(final Album album);

    @Delete("DELETE FROM album WHERE id=#{id}")
    void removeAlbum(final Long id);


}
