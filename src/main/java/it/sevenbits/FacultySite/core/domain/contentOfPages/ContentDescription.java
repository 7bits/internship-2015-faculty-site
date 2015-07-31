package it.sevenbits.FacultySite.core.domain.contentOfPages;


public class ContentDescription {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String type;
    private String imageLink;

    public ContentDescription() {
    }

    public ContentDescription(String title, String description, String creatingDate, String creatingTime, String type) {
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
    }

    public ContentDescription(Long id, String title, String description, String type, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageLink = imageLink;
    }

    public ContentDescription(String title, String description, String type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public ContentDescription(Long id, String title, String description, String creatingDate, String creatingTime, String type, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
        this.imageLink = imageLink;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatingDate(String creatingDate) {
        String date[] = creatingDate.split("-");
        this.creatingDate = date[2]+"."+date[1]+"."+date[0];
    }

    public void setCreatingTime(String creating_time) {
        this.creatingTime = creating_time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Long getId() {
        return id;
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

    public String getType() {
        return type;
    }

    public String getImageLink() {
        return imageLink;
    }
}
