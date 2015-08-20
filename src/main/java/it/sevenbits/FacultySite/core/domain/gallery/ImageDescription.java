package it.sevenbits.FacultySite.core.domain.gallery;

public class ImageDescription {
    private Long id;
    private Long album;
    private String title;
    private String description;
    private String creating_date;
    private String creating_time;
    private String link;
    private Boolean isHead;

    public ImageDescription() {
    }

    public ImageDescription(Long id, String title, String description, String link, Boolean isHead) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.isHead = isHead;
    }



    public ImageDescription(Long id, String title, String description, Long album, Boolean isHead, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.album = album;
        this.isHead = isHead;
        this.link = link;
    }

    public ImageDescription(Long id, Long album, String title, String description, String creating_date, String creating_time, String link, Boolean isHead) {
        this.id = id;
        this.album = album;
        this.title = title;
        this.description = description;
        this.creating_date = creating_date;
        this.creating_time = creating_time;
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

    public String getCreating_date() {
        return creating_date;
    }

    public String getCreating_time() {
        return creating_time;
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

    public void setCreating_date(String creating_date) {
        this.creating_date = creating_date;
    }

    public void setCreating_time(String creating_time) {
        this.creating_time = creating_time;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setIsHead(Boolean isHead) {
        this.isHead = isHead;
    }



}
