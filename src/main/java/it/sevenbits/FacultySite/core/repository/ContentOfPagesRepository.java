package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;

import java.util.List;

/**
 * Created by aleksey on 27.07.15.
 */
public interface ContentOfPagesRepository {
    public List<ContentDescription> getAllPages() throws RepositoryException;
    public void saveContent(ContentDescription description) throws RepositoryException;
}
