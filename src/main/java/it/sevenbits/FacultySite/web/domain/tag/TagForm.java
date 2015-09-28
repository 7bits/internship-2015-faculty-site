package it.sevenbits.FacultySite.web.domain.tag;

public class TagForm {
    private Long id;
    private String title;

    public TagForm(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public TagForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
