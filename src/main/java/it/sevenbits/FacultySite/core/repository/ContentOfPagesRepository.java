package it.sevenbits.FacultySite.core.repository;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;

import java.util.List;


public interface ContentOfPagesRepository {
    public List<ContentDescription> getAllPages() throws RepositoryException;
    public List<ContentDescription> getPagesByType(String type) throws RepositoryException;
    public List<ContentDescription> getPagesWhichContainType(String type) throws RepositoryException;
    public List<ContentDescription> getPagesIsPublish(Boolean publish) throws RepositoryException;
    public ContentDescription getPageById(Long id) throws RepositoryException;

    public void saveContent(ContentDescription description) throws RepositoryException;
    public void updatePage(String newTitle, String newDescription, String newType, String newMiniContent, String newImageLink, Long id) throws RepositoryException;
    public void removePageById(Long id) throws RepositoryException;

    public void removePageByType(String type) throws RepositoryException;
}
