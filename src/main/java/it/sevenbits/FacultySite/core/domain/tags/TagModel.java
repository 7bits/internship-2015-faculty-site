package it.sevenbits.FacultySite.core.domain.tags;

public class TagModel {
    private final Long id;
    private final String title;

    public TagModel(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public TagModel(String title) {
        this.title = title;
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
