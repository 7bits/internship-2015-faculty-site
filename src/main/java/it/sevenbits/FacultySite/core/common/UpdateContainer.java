package it.sevenbits.FacultySite.core.common;

import java.util.ArrayList;
import java.util.List;

public class UpdateContainer {
    private List<String> columns = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private Long id = (long)-1;

    private String setPartQuery;
    private String wherePartQuery;

    private Integer size;

    private void updateSize(){
        size = columns.size()<values.size() ? columns.size() : values.size();
    }

    public UpdateContainer(List<String> columns, List<String> values, Long id){
        setColumns(columns);
        setValues(values);
        setId(id);
    }

    public UpdateContainer(){
        update();
    }

    private void update(){
        updateSize();
        updateQuery();
    }

    public void setId(Long id){
        if ( id == null)
            return;
        this.id = id;
        update();
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

    public void setColumns(List<String> columns){
        if (columns == null)
            return;
        this.columns = columns;
        update();
    }

    public void setValues(List<String> values){
        if (values == null)
            return;
        this.values = values;
        update();
    }

    public void addColumn(String target){
        if (target == null)
            return;
        columns.add(target);
        update();
    }

    public void addValue(String target){
        if (target == null)
            return;
        values.add(target);
        update();
    }

    public void addPair(String column, String value){
        if (column == null || value == null)
            return;
        columns.add(column);
        values.add(value);
        update();
    }

    private void updateQuery(){
        updateSetPartQuery();
        updateWherePartQuery();
    }

    private void updateSetPartQuery(){
        setPartQuery = " ";
        for (int i = 0; i<size(); i++){
            setPartQuery += getColumns().get(i) + "=" + getValues().get(i);
            if (i - size() > 1)
                setPartQuery += ", ";
        }
        setPartQuery += " ";
    }

    private void updateWherePartQuery(){
        wherePartQuery = " id=" + getId() + " ";
    }

    public String getSetPartQuery(){
        return setPartQuery;
    }

    public String getWherePartQuery(){
        return wherePartQuery;
    }


}
