package it.sevenbits.FacultySite.core.domain.gallery;

public class ImageFromAlbumDescription {
    private Long id;
    private String album_title;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String link;
    private Boolean isHead;


    public void setId(Long id) {
        this.id = id;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public void setCreatingTime(String creatingTime) {
        this.creatingTime = creatingTime;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setIsHead(Boolean isHead) {
        this.isHead = isHead;
    }

    public Long getId() {
        return id;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public String getCreatingTime() {
        return creatingTime;
    }

    public String getLink() {
        return link;
    }

    public Boolean isHead() {
        return isHead;
    }
}
