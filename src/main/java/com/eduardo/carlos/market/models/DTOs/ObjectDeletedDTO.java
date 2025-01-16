package com.eduardo.carlos.market.models.DTOs;

public class ObjectDeletedDTO {

    private Object deletedObject;
    private String message;

    public ObjectDeletedDTO(Object deletedObject) {
        this.deletedObject = deletedObject;
        this.message = "Deletado com sucesso!";
    }

    public Object getDeletedObject() {
        return deletedObject;
    }

    public void setDeletedObject(Object deletedObject) {
        this.deletedObject = deletedObject;
    }
}
