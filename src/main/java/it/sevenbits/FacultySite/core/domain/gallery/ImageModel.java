package it.sevenbits.FacultySite.core.domain.gallery;

public class ImageModel {
    private final Long id;
    private final Long album;
    private final String title;
    private final String description;
    private final String creatingDate;
    private final String creatingTime;
    private final String link;
    private final Boolean isHead;

    public ImageModel(Long id, Long album, String title, String description, String creatingDate, String creatingTime, String link, Boolean isHead) {
        this.id = id;
        this.album = album;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.link = link;
        this.isHead = isHead;
    }

    public ImageModel(String title, String description) {
        this.title = title;
        this.description = description;
        this.creatingDate = null;
        this.creatingTime = null;
        this.link = null;
        this.isHead = null;
        this.id = null;
        this.album = null;
    }

    public Long getId() {
        return id;
    }

    public Long getAlbum() {
        return album;
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

    public Boolean getIsHead() {
        return isHead;
    }
}
