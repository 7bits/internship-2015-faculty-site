package it.sevenbits.FacultySite.core.domain.gallery;


public class Album {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String link;
    private Long length;

    public Album() {}

    public Album(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Album(Long id, String title, String description, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Album(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Album(Long id, String title, String description, String creatingDate, String creatingTime, String link, Long length) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.link = link;
        this.length = length;
    }

    public Album(Long id, String title, String description, String creatingDate, String creatingTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
    }

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

    public Long getLength() {
        return length;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatingDate(String creatingDate) {
        String date[] = creatingDate.split("-");
        this.creatingDate = date[2]+"."+date[1]+"."+date[0];
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

    public void setLength(Long length) {
        this.length = length;
    }


}
