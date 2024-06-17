package com.jv01.generations.Panels.Menus;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class SelectionWheelIcons {
    private Save save = new Save();
    private String gameName;
    private Document doc;

    public SelectionWheelIcons(String gameName){
        this.gameName = gameName;
        getDoc();
    }

    private void getDoc(){
        doc = save.getDocumentXml(this.gameName,"functional/selectionWheel/interactions");
    }

    private String getIconUrlById(String id){
        String url = "";
        Element element = save.getElementById(doc, "interaction", String.valueOf(id));

        url = save.getChildFromElement(element, "logoPath");
        url = save.dropSpaceFromString(url);
        return url;
    }

    public ImageIcon getIconById(String id){
        ImageIcon icon = new ImageIcon(getIconUrlById(id));
        return icon;
    }
}
