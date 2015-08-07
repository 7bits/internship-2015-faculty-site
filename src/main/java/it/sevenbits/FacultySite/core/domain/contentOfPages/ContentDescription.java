package it.sevenbits.FacultySite.core.domain.contentOfPages;


public class ContentDescription {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String type;
    private String imageLink;
    private String miniContent;
    private Boolean publish;

    public ContentDescription() {
    }

    public ContentDescription(String title, String description, String creatingDate, String creatingTime, String type) {
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
    }

    public ContentDescription(Long id, String title, String description, String creatingDate, String creatingTime, String type, String imageLink, String miniContent, Boolean publish) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
        this.publish = publish;
    }

    public ContentDescription(String title, String description, String miniContent, String imageLink, String type, Boolean publish) {
        this.title = title;
        this.description = description;
        this.publish = publish;
        this.miniContent = miniContent;
        this.imageLink = imageLink;
        this.type = type;
    }

    public ContentDescription(Long id, String title, String description, String creatingDate, String creatingTime, String type, String imageLink, String miniContent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
    }



    public ContentDescription(Long id, String title, String description, String type, String imageLink, String miniContent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
    }

    public ContentDescription(String title, String description, String type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }


    public ContentDescription(String title, String description, String type, String miniContent) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.miniContent = miniContent;
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

    public void setMiniContent(String miniContent) {
        this.miniContent = miniContent;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
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

    public String getMiniContent() {
        return miniContent;
    }

    public Boolean getPublish() {
        return publish;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + "\n"
                + "Page:\n" + getDescription() + "\n"
                + "Mini content:\n" + getMiniContent() + "\n"
                + "Date: " + getCreatingDate() + "\n"
                + "Time: " + getCreatingTime() + "\n"
                + "Type: " + getType() + "\n"
                + "Publish: " + getPublish() + "\n";
    }
}
