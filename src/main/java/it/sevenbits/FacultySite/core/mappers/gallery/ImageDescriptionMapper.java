package it.sevenbits.FacultySite.core.mappers.gallery;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ImageDescriptionMapper {

    @Select("SELECT id, title, description, link, album FROM image")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "link", property = "link"),
            @Result(column = "description", property = "description"),
            @Result(column = "title", property = "title"),
            @Result(column = "album", property = "album")
    })
    List<ImageDescription> getAllImages();

    @Select("SELECT i.id, i.title, i.description, i.link, i.is_head, a.title as album_title FROM image i, album a WHERE i.album=a.id and a.id=#{album_id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "link", property = "link"),
            @Result(column = "description", property = "description"),
            @Result(column = "title", property = "title"),
            @Result(column = "album_title", property = "album_title"),
            @Result(column = "is_head", property = "isHead"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    List<ImageFromAlbumDescription> getImagesFromAlbum(@Param("album_id") Long album_id);

    @Select("SELECT * FROM image WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "link", property = "link"),
            @Result(column = "description", property = "description"),
            @Result(column = "title", property = "title"),
            @Result(column = "album", property = "album"),
            @Result(column = "is_head", property = "isHead"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    ImageDescription getImageById(@Param("id") Long id);

    @Insert("INSERT INTO image (title, description, link, creating_date, creating_time, album, is_head) VALUES (#{title}, #{description}, #{link}, 'today', 'now', #{album}, #{isHead})")
    void saveImage(final ImageDescription description);

    @Delete("DELETE FROM image WHERE id=#{id}")
    void removeImage(final Long id);

    @Update("UPDATE image SET title=#{title}, description=#{description}, album=#{album}, is_head=#{isHead} WHERE id=#{id}")
    void changeImage(@Param("title")String title, @Param("description")String description, @Param("album")Long album, @Param("isHead")boolean isHead, @Param("id")Long id);

    @Select("SELECT * FROM album")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    List<AlbumDescription> getAllAlbums();

    @Select("SELECT id, title, description, creating_date, creating_time FROM album WHERE id=#{albumId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "description", property = "description"),
            @Result(column = "creating_date", property = "creatingDate"),
            @Result(column = "creating_time", property = "creatingTime")
    })
    AlbumDescription getAlbumById(@Param("albumId")Long id);

    @Insert("INSERT INTO album (title, description, creating_date, creating_time) VALUES (#{title}, #{description}, 'today', 'now')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void addAlbum(final AlbumDescription album);

}