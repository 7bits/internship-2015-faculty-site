package it.sevenbits.FacultySite.web.service;

import it.sevenbits.FacultySite.web.domain.content.ContentForm;
import org.springframework.stereotype.Service;

@Service
public class StateContentCheckService {
    private Boolean checked;
    private Boolean correctly;
    private String message;
    private ContentForm contentForm;

    public StateContentCheckService(){
        checked = false;
        correctly = null;
        message = "Haven't entity to check";
    }

    public StateContentCheckService(ContentForm contentForm) {
        this.contentForm = contentForm;
        check();
    }

    private void check(){
        checked = false;
        correctly = true;
        if (contentForm == null){
            correctly = false;
            message = "Content is null";
        }
        else {
            if (contentForm.getTitle() == null) {
                correctly = false;
                message = "Title of content is null";
            }
            else {
                if (contentForm.getTitle().isEmpty()) {
                    correctly = false;
                    message = "Title of content is empty";
                }
            }
        }
        message = "Checked";
        checked = true;
    }

    public Boolean getChecked() {
        return checked;
    }

    public Boolean getCorrectly() {
        return correctly;
    }

    public String getMessage() {
        return message;
    }

    public ContentForm getContentForm() {
        return contentForm;
    }

    public void setContentForm(ContentForm contentForm) {
        this.contentForm = contentForm;
        check();
    }
}
