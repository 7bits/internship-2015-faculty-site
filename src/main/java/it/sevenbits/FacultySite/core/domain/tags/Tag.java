package it.sevenbits.FacultySite.core.domain.tags;

public class Tag {
    private Long id;
    private String title;

    public Tag(){
        this.setId(null);
        this.setTitle(null);
    }

    public Tag(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Tag(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
