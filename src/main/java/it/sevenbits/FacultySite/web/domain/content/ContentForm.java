package it.sevenbits.FacultySite.web.domain.content;

import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.content.ContentModel;
import it.sevenbits.FacultySite.core.domain.tags.Tag;

import java.util.List;

public class ContentForm {
    private Long id;
    private String title;
    private String description;
    private String creatingDate;
    private String creatingTime;
    private String imageLink;
    private String miniContent;
    private Boolean publish;
    private List<Tag> tags;

    public ContentForm(Content content, List<Tag> tags) {
        this.setId(content.getId());
        this.setTitle(content.getTitle());
        this.setDescription(content.getDescription());
        this.setCreatingDate(content.getCreatingDate());
        this.setCreatingTime(content.getCreatingTime());
        this.setImageLink(content.getImageLink());
        this.setMiniContent(content.getMiniContent());
        this.setPublish(content.getPublish());
        this.setTags(tags);
    }

    public ContentForm(ContentModel content, List<Tag> tags){
        this.setId(content.getId());
        this.setTitle(content.getTitle());
        this.setDescription(content.getDescription());
        this.setCreatingDate(content.getCreatingDate());
        this.setCreatingTime(content.getCreatingTime());
        this.setImageLink(content.getImageLink());
        this.setMiniContent(content.getMiniContent());
        this.setPublish(content.getPublish());
        this.setTags(tags);
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        if (tags != null) {
            this.tags = tags;
        }
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
