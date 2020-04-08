package flicker.covoid.coronavirus.model.dto;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Parcel
public class Hospital {
    private String name_ta;

    private String updated_at;

    private String name;

    private String name_si;

    private String created_at;

    private String id;

    private String deleted_at;

    public String getName_ta() {
        return name_ta;
    }

    public void setName_ta(String name_ta) {
        this.name_ta = name_ta;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_si() {
        return name_si;
    }

    public void setName_si(String name_si) {
        this.name_si = name_si;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
