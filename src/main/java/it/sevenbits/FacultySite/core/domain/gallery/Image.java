package it.sevenbits.FacultySite.core.domain.gallery;

public class Image {
    private Long id;
    private Long album;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String link;
    private Boolean isHead;

    public Image() {
    }

    public Image(Long id, String title, String description, String link, Boolean isHead) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.isHead = isHead;
    }



    public Image(Long id, String title, String description, Long album, Boolean isHead, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.album = album;
        this.isHead = isHead;
        this.link = link;
    }

    public Image(Long id, Long album, String title, String description, String creatingDate, String creatingTime, String link, Boolean isHead) {
        this.id = id;
        this.album = album;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.link = link;
        this.isHead = isHead;
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

    public Boolean isHead() {
        return isHead;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setAlbum(Long album) {
        this.album = album;
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



}
