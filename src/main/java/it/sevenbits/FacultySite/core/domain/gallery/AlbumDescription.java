package it.sevenbits.FacultySite.core.domain.gallery;


public class AlbumDescription {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String link;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatingTime() {
        return creatingTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatingTime(String creatingTime) {
        this.creatingTime = creatingTime;
    }
}
