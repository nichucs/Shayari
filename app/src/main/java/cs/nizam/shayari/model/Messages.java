package cs.nizam.shayari.model;

import java.io.Serializable;

/**
 * Created by nizamcs on 17/4/16.
 */
public class Messages implements Serializable{

    private static final long serialVersionUID = 6889281605384303354L;
    int _id;
    String msg;
    int cat;

    public Messages(int _id, String msg, int cat) {
        this._id = _id;
        this.msg = msg;
        this.cat = cat;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }
}
