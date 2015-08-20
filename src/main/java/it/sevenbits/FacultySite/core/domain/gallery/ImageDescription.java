package it.sevenbits.FacultySite.core.domain.gallery;

public class ImageDescription {
    private Long id;
    private Long album;
    private String title;
    private String description;
    private String creating_date;
    private String creating_time;
    private String link;
    private boolean is_head;

    public ImageDescription() {
    }

    public ImageDescription(Long id, String title, String description, String link, boolean is_head) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.is_head = is_head;
    }

    public ImageDescription(Long id, String title, String description, Long album, boolean is_head, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.album = album;
        this.is_head = is_head;
        this.link = link;
    }

    public ImageDescription(Long id, Long album, String title, String description, String creating_date, String creating_time, String link, boolean is_head) {
        this.id = id;
        this.album = album;
        this.title = title;
        this.description = description;
        this.creating_date = creating_date;
        this.creating_time = creating_time;
        this.link = link;
        this.is_head = is_head;
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

    public boolean is_head() {
        return is_head;
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

    public void setIs_head(boolean is_head) {
        this.is_head = is_head;
    }



}
