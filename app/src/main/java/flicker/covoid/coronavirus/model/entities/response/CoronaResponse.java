package flicker.covoid.coronavirus.model.entities.response;

import flicker.covoid.coronavirus.model.dto.Data;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Parcel
public class CoronaResponse extends BaseServerResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
