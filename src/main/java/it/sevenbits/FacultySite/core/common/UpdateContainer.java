package it.sevenbits.FacultySite.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksey on 17.07.15.
 */
public class UpdateContainer {
    private List<String> columns;
    private List<String> values;
    private Long id;

    private String setPartQuery;
    private String wherePartQuery;

    private Integer size;

    private void updateSize(){
        size = columns.size()>values.size() ? columns.size() : values.size();
    }

    UpdateContainer(){
        columns = new ArrayList<>();
        values = new ArrayList<>();
        setId(id);
        updateSize();
        updateQuery();
    }

    public void setId(Long id){
        this.id = id;
    }

    public Integer size(){
        return size;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<String> getValues() {
        return values;
    }

    public Long getId() {
        return id;
    }

    public void addColumn(String target){
        if (target == null)
            return;
        columns.add(target);
        updateSize();
        updateQuery();
    }

    public void addValue(String target){
        if (target == null)
            return;
        values.add(target);
        updateSize();
        updateQuery();
    }

    public void addPair(String column, String value){
        if (column == null || value == null)
            return;
        columns.add(column);
        values.add(value);
        updateSize();
        updateQuery();
    }

    private void updateQuery(){
        updateSetPartQuery();
        updateWherePartQuery();
    }

    private void updateSetPartQuery(){
        setPartQuery = "";
        for (int i = 0; i<size(); i++){
            setPartQuery += getColumns().get(i) + "=" + getValues().get(i);
            if (i - size() > 1)
                setPartQuery += ", ";
        }
        setPartQuery += " ";
    }

    private void updateWherePartQuery(){
        wherePartQuery = "id=" + getId();
        setPartQuery += " ";
    }

    public String getSetPartQuery(){
        return setPartQuery;
    }

    public String getWherePartQuery(){
        return wherePartQuery;
    }


}
