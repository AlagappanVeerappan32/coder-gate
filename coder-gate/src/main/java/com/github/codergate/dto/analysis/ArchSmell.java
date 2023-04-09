
package com.github.codergate.dto.analysis;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Name",
    "Description",
    "Entity",
    "ResponsibleClasses"
})
public class ArchSmell implements Serializable
{

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Entity")
    private Entity entity;
    @JsonProperty("ResponsibleClasses")
    private String responsibleClasses;
    private final static long serialVersionUID = -7014946052825035518L;

    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("Entity")
    public Entity getEntity() {
        return entity;
    }

    @JsonProperty("Entity")
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @JsonProperty("ResponsibleClasses")
    public String getResponsibleClasses() {
        return responsibleClasses;
    }

    @JsonProperty("ResponsibleClasses")
    public void setResponsibleClasses(String responsibleClasses) {
        this.responsibleClasses = responsibleClasses;
    }

}