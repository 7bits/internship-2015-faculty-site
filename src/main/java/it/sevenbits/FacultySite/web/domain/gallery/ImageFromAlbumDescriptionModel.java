package it.sevenbits.FacultySite.web.domain.gallery;

public class ImageFromAlbumDescriptionModel {



    private Long id;
    private String album_title;
    private String title;
    private String description;
    private String creating_date;
    private String creating_time;
    private String link;
    private boolean is_head;

    public ImageFromAlbumDescriptionModel(Long id, String title, String description, String link, String album_title) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.album_title = album_title;
    }

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

    @Override
    public String toString() {
        return String.format("Description[id=%d, title=%s, description=%s, link=%s, album=%s]",
                id, title, description, link, album_title);
    }
}
