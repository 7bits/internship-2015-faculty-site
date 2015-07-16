package it.sevenbits.FacultySite.web.domain;

public class ImageDescriptionModel {
    private final Long id;
    private final String title;
    private final String description;
    private final String link;

    public ImageDescriptionModel(Long id, String title, String description, String link) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return String.format("Subscription[id=%d, title=%s, description=%s, link=%s]",
                id, title, description, link);
    }
}
