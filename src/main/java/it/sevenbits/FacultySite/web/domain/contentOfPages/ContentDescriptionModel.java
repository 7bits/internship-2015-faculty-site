package it.sevenbits.FacultySite.web.domain.contentOfPages;


public class ContentDescriptionModel {
    private final Long id;
    private final String title;
    private final String description;
    private final String creatingDate;
    private final String creatingTime;
    private final String type;
    private final String imageLink;

    public ContentDescriptionModel(){
        this.id = (long)0;
        this.title = "";
        this.description = "";
        this.creatingDate = "";
        this.creatingTime = "";
        this.type = "";
        this.imageLink = "";
    }

    public ContentDescriptionModel(String description) {
        this.id = (long)0;
        this.title = "";
        this.description = description;
        this.creatingDate = "";
        this.creatingTime = "";
        this.type = "";
        this.imageLink = "";
    }

    public ContentDescriptionModel(Long id, String title, String description, String creatingDate, String creatingTime, String type, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatingDate = creatingDate;
        this.creatingTime = creatingTime;
        this.type = type;
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

    @Override
    public String toString() {
        return "Title: " + getTitle() + "\n"
                + "Page: \n" + getDescription() + "\n"
                + "Date: " + getCreatingDate() + "\n"
                + "Time: " + getCreatingTime() + "\n"
                + "Type: " + getType() + "\n";
    }
}
