package it.sevenbits.FacultySite.web.domain.gallery;

public class ImageDescriptionForm {
    private int id;
    private int album;
    private String title;
    private String description;
    private String creating_date;
    private String creating_time;
    private String link;
    private boolean is_head;

    public ImageDescriptionForm(Integer album, String title, String description, String creating_date, String creating_time, String link, boolean is_head) {
        if (album != null)
            this.album = album;
        this.title = title;
        this.description = description;
        this.creating_date = creating_date;
        this.creating_time = creating_time;
        this.link = link;
        this.is_head = is_head;
    }

    public ImageDescriptionForm(){
        this.title = null;
        this.description = null;
        this.creating_date = null;
        this.creating_time = null;
        this.link = null;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public void setId(int id) {
        this.id = id;
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


    public int getId() {
        return id;
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
    public String toString(){
        return String.format("Title: %s\nDescription: %s\nLink: %s\n", title, description, link);
    }

}
