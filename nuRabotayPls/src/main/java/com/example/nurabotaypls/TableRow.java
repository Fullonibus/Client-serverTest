package com.example.nurabotaypls;

public class TableRow{
    public Integer id_column;

    public Integer getId_column() {
        return id_column;
    }

    public String getName_column() {
        return name_column;
    }

    public String getDescription_column() {
        return description_column;
    }

    public String name_column;
    public String description_column;

    public TableRow() {
    }

    public TableRow(Integer id_column, String name_column, String description_column) {
        this.id_column = id_column;
        this.name_column = name_column;
        this.description_column = description_column;
    }
}
