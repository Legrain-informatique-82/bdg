package fr.legrain.prestashop.ws.entities;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String string) throws Exception {
        if("".equals(string)) {
            return null;
        }
        if("0".equals(string)) {
            return null;
        }
        if("0.0".equals(string)) {
            return null;
        }
        return string;
    }

    @Override
    public String unmarshal(String string) throws Exception {
        return string;
    }

}
