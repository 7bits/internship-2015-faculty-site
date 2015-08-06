package it.sevenbits.FacultySite.web.domain.contentOfPages;

public class ContentDescriptionForm {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String type;
    private String imageLink;

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

    @Override
    public String toString() {
        return "Title: " + getTitle() + "\n"
                + "Page: \n" + getDescription() + "\n"
                + "Date: " + getCreatingDate() + "\n"
                + "Time: " + getCreatingTime() + "\n"
                + "Type: " + getType() + "\n";
    }
}
