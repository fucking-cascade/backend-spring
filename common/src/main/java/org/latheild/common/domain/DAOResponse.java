package org.latheild.common.domain;

import java.io.Serializable;

public class DAOResponse implements Serializable {
    Object data;

    String error;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DAOResponse{" +
                "data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
