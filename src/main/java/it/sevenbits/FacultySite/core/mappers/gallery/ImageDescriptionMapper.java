package it.sevenbits.FacultySite.core.mappers.gallery;

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
            @Result(column = "is_head", property = "is_head")
    })
    List<ImageFromAlbumDescription> getImagesFromAlbum(@Param("album_id") Long album_id);

    @Insert("INSERT INTO image (title, description, link, creating_date, creating_time, album) VALUES (#{title}, #{subscription}, #{link}, #{creating_date}, #{creating_time}, #{album})")
    void saveImage(final ImageDescription subscription);
}