package org.lessons.java.spring.ticket_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {

    // ! ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ! TITLE
    @NotBlank(message = " The title is mandatory and must be present")
    @Size(min = 1, message = "Title must have at least one characters")
    private String title;

    // ! CATEGORY
    @NotNull(message = " It is mandatory to select a category")
    private String category;

    // ! STATUS
    @NotNull(message = "Every ticket must have a valid status")
    private String status;

    // ! OPERATOR
    @NotNull(message = "A ticket must be assigned to an operator")
    private String operator;

    // ! NOTES
    @Lob
    @Size(max = 2000, message = "Notes cannot be longer than 2000 characters")
    private String notes;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
