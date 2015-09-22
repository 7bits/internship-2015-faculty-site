package it.sevenbits.FacultySite.web.domain.content;


public class ContentModel {
    private final Long id;
    private final String title;
    private final String description;
    private final String creatingDate;
    private final String creatingTime;
    private final String imageLink;
    private final String miniContent;
    private final Boolean publish;

    public ContentModel(){
        this.id = (long)0;
        this.title = "";
        this.description = "";
        this.creatingDate = "";
        this.creatingTime = "";
        this.imageLink = "";
        this.miniContent = "";
        this.publish = false;
    }

    public ContentModel(String description) {
        this.id = (long)0;
        this.title = "";
        this.description = description;
        this.creatingDate = "";
        this.creatingTime = "";
        this.imageLink = "";
        this.miniContent = "";
        this.publish = false;
    }

    public ContentModel(String description, String miniContent) {
        this.id = (long)0;
        this.title = "";
        this.description = description;
        this.miniContent = miniContent;
        this.creatingDate = "";
        this.creatingTime = "";
        this.imageLink = "";
        this.publish = false;
    }

    public ContentModel(Long id, String title, String description, String creatingDate, String creatingTime, String imageLink, String miniContent) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
        this.publish = false;
    }

    public ContentModel(Long id, String title, String description, String creatingDate, String creatingTime, String imageLink, String miniContent, Boolean publish) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
        this.publish = publish;
    }

    public ContentModel(Long id, String title, String description, String imageLink, String miniContent, Boolean publish) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.miniContent = miniContent;
        this.publish = publish;
        this.creatingDate = "";
        this.creatingTime = "";
    }

    public ContentModel(Long id, String title, String description, String creatingDate, String creatingTime, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.imageLink = imageLink;
        this.miniContent = "";
        this.publish = false;
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
                + "Time: " + getCreatingTime() + "\n";
    }
}
