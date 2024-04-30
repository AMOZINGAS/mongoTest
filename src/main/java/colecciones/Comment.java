/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package colecciones;

import org.bson.types.ObjectId;

/**
 *
 * @author lv1822
 */
public class Comment {
    
    private ObjectId id;
    private String comment;

    public Comment() {
    }

    public Comment(ObjectId id, String text) {
        this.id = id;
        this.comment = text;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
}
