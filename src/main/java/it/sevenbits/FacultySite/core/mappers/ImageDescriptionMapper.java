package it.sevenbits.FacultySite.core.mappers;

import it.sevenbits.FacultySite.core.domain.ImageDescription;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ImageDescriptionMapper {

    @Select("SELECT id, title, subscription, link, album FROM image")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "link", property = "link"),
            @Result(column = "description", property = "description"),
            @Result(column = "title", property = "title"),
            @Result(column = "album", property = "album")
    })
    List<ImageDescription> getAllImages();

    @Insert("INSERT INTO image (title, description, link, creating_date, creating_time, album) VALUES (#{title}, #{subscription}, #{link}, #{creating_date}, #{creating_time}, #{album})")
    void saveImage(final ImageDescription subscription);
}