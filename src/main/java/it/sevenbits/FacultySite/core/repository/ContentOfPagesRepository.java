package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;

import java.util.List;

/**
 * Created by aleksey on 27.07.15.
 */
public interface ContentOfPagesRepository {
    public List<ContentDescription> getAllPages() throws RepositoryException;
    public List<ContentDescription> getPagesByType(String type) throws RepositoryException;
    public void saveContent(ContentDescription description) throws RepositoryException;
    public void updatePage(String newTitle, String newDescription, String newType, Long id) throws RepositoryException;
    public void removePageById(Long id) throws RepositoryException;
    public void removePageByType(String type) throws RepositoryException;
}
