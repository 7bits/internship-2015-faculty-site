package it.sevenbits.FacultySite.core.domain.content;


public class Content {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String imageLink;
    private String miniContent;
    private Boolean publish;

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
                + "Publish: " + getPublish() + "\n";
    }
}
