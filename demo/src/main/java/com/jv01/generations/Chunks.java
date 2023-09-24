package com.jv01.generations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.buildings.Inside;
import com.jv01.buildings.Buildings;
import com.jv01.fonctionals.Save;

public class Chunks {
    public Save save = new Save();

    public boolean load = true;

    public String id;
    public long[] chunk;
    public String seed;
    public int biome;
    public String key;
    public int boxSize;
    public int cellSize;
    public String backPic = "";

    public String gameName;

    public int number = 0;

    public boolean isInsideBuilding;
    public boolean isCenterChunk = false;

    public boolean[] completedCell = {false,false,false,false};

    public JPanel backgroundPanel;

    public List<Buildings> triggerableBuilding = new ArrayList<>();
    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();

    public List<Object[]> characters = new ArrayList<>();

    public Document doc;
    public Element element;

    public int[] bCellX = {0,0,0,0};
    public int[] bCellY = {0,0,0,0};
    public int[] bType = {0,0,0,0};

    private Random random = new Random();

    public Chunks(long[] chunk, String seed, String gameName, int buildingType){
        this.chunk = chunk;
        this.seed = seed;
        this.gameName = gameName;

        this.load = false;

        this.key = getKey();

        this.id = chunk[0]+"_"+chunk[1];

        this.doc = save.getDocumentXml(gameName,"chunks");
        this.element = save.getElementById(doc, "chunk", id);

        if(element == null){
            if(!isInsideBuilding){
                createBiome();
                addBuildings(buildingType);
                saveChunk();
            }else{
                addInsideBuildings();
            }
            
        }else{
            
        }   
    }

    public Chunks(long[] chunk, String seed, String gameName, int boxSize, JPanel backgroundPanel, boolean isInsideBuilding, boolean load){
        this.load = load;

        this.chunk = chunk;
        this.seed = seed;
        this.boxSize = boxSize;
        this.cellSize = boxSize/3;
        this.backgroundPanel = backgroundPanel;
        this.isInsideBuilding = isInsideBuilding;
        this.gameName = gameName;

        this.key = getKey();

        this.id = chunk[0]+"_"+chunk[1];

        this.doc = save.getDocumentXml(gameName,"chunks");
        this.element = save.getElementById(doc, "chunk", id);

        if(element == null){
            if(!isInsideBuilding){
                createBiome();
                addBuildings(-1);
                if(this.load)addDecorations();
                saveChunk();
            }else{
                addInsideBuildings();
            }
            
        }else{
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);
            if(!isInsideBuilding){
                this.biome = Integer.parseInt(save.getChildFromMapElements(allElements,"biome"));
                this.backPic = save.getChildFromMapElements(allElements,"backPic");

                if(this.load)changeBiome(biome, backPic);

                this.number = Integer.parseInt(save.getChildFromMapElements(allElements,"number"));

                if(number>0){
                    this.bCellX = save.stringToIntArray(save.getChildFromMapElements(allElements,"buildingsCellsX"));
                    this.bCellY = save.stringToIntArray(save.getChildFromMapElements(allElements,"buildingsCellsY"));
                    this.bType = save.stringToIntArray(save.getChildFromMapElements(allElements,"buildingsTypes"));

                    
                    for(int i=0; i <number; i++){
                        int[] cell = (new int[]{bCellX[i],bCellY[i]});
                        if(this.load)createBuilding(number, cell, bType[i]);
                    }

                    int[] completedCellInt = save.stringToIntArray(save.getChildFromMapElements(allElements,"completedCell"));
                    for(int i=0; i<4; i++){
                        if(completedCellInt[i]==1){
                            completedCell[i] = true;
                        }else{
                            completedCell[i] = false;
                        }
                    }
                }
                if(this.load)addDecorations();
            }else{
                this.bType = save.stringToIntArray(save.getChildFromMapElements(allElements,"buildingsTypes"));
                addInsideBuildings();
            }
        }   
    }

    public void createChunkElement(Document doc){
        Element chunkElement = doc.createElement("chunk");

        chunkElement.setAttribute("id", id);

        Element element = doc.createElement("cell");
        element.appendChild(doc.createTextNode('{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}'));
        chunkElement.appendChild(element);

        element = doc.createElement("biome");
        element.appendChild(doc.createTextNode(String.valueOf(biome)));
        chunkElement.appendChild(element);

        element = doc.createElement("backPic");
        element.appendChild(doc.createTextNode(backPic));
        chunkElement.appendChild(element);

        element = doc.createElement("number");
        element.appendChild(doc.createTextNode(String.valueOf(number)));
        chunkElement.appendChild(element);

        String buildingsCellsX = "{";
        String buildingsCellsY = "{";
        String buildingsTypes = "{";

        for(int i=0; i< number; i++){
            buildingsCellsX+=bCellX[i];
            if(i!=(number-1))buildingsCellsX+=",";
            
            buildingsCellsY+=bCellY[i];
            if(i!=(number-1))buildingsCellsY+=",";

            buildingsTypes+=bType[i];
            if(i!=(number-1))buildingsTypes+=",";
        }

        buildingsCellsX+="}";
        buildingsCellsY+="}";
        buildingsTypes+="}";

        element = doc.createElement("buildingsCellsX");
        element.appendChild(doc.createTextNode(buildingsCellsX));
        chunkElement.appendChild(element);

        element = doc.createElement("buildingsCellsY");
        element.appendChild(doc.createTextNode(buildingsCellsY));
        chunkElement.appendChild(element);

        element = doc.createElement("buildingsTypes");
        element.appendChild(doc.createTextNode(buildingsTypes));
        chunkElement.appendChild(element);

        String completedCellInt = "{";

        for(int i=0; i< 4; i++){
           if(completedCell[i]){
                completedCellInt+="1";
           }else{completedCellInt+="0";}
           if(i!=3)completedCellInt+=",";
        }

        completedCellInt+='}';

        element = doc.createElement("completedCell");
        element.appendChild(doc.createTextNode(completedCellInt));
        chunkElement.appendChild(element);

        doc.getDocumentElement().appendChild(chunkElement);
    }

    public void saveChunk(){
        createChunkElement(doc);
        save.saveXmlFile(doc, gameName, "chunks");
    }



    public void createBiome(){
        Biomes biome = new Biomes(gameName, key);
        backPic = biome.imageUrl;
        this.biome = biome.biomeType;
    }
    public void changeBiome(int type, String imgUrl){
        backPic = imgUrl;
        biome = type;
    }

    public void addBuildings(int buildingType){
        int[] cell = {0,0};
        int[] cell01 = {0,0};
        char key2 = key.charAt(1);
        
        number = 0;

        if(chunk[0] == 0 && chunk[1] == 0){
            isCenterChunk = true;
        }else{
            isCenterChunk = false;
        }

        if(biome >= 0 && biome <= 1){

        }else if(biome >= 2 && biome <= 3){
            if(key2 >= '0' && key2 <= 'b'){
                number = 1;
            }else{
                number = 0;
            }
        }else if(biome == 4){
            if(key2 >= '0' && key2 <= 'b'){
                number = 2;
            }else{
                number = 3;
            }
        }else if(biome == 5){
            if(key2 >= '0' && key2 <= 'b'){
                number = 1;
            }else{
                number = 2;
            }
        }else if(biome == 6){
            if(key2 >= '0' && key2 <= 'b'){
                number = 2;
            }else{
                number = 3;
            }
        }else if(biome == 7){
            if(key2 >= '0' && key2 <= 'b'){
                number = 3;
            }else{
                number = 4;
            }
        }

        if((isCenterChunk || (buildingType > 0)) && number == 0)number = 1;

        char key3 = key.charAt(2);

        // location of the important building

        if (key3 >= '0' && key3 <= '3') {
            cell01[0]=0;
            cell01[1]=0;
            completedCell[0] = true;
        } else if (key3 >= '4' && key3 <= '7') {
            cell01[0]=0;
            cell01[1]=2;
            completedCell[1] = true;
        } else if (key3 >= '8' && key3 <= 'b') {
            cell01[0]=2;
            cell01[1]=0;
            completedCell[2] = true;
        } else {
            cell01[0]=2;
            cell01[1]=2;
            completedCell[3] = true;
        }

        if(buildingType < 0){
            buildingType = 7;
            char key4 = key.charAt(3);
            char key5 = key.charAt(4);

            /*if (key4 >= '0' && key4 <= '1' && biome > 3) {
                if(key5 <= getCharComparedToPercentage(13))buildingType = 8;
            } else */if (key4 >= '2' && key4 <= '3' && biome > 3) {
                if(key5 <= getCharComparedToPercentage(20))buildingType = 1;
            } else if (key4 >= '4' && key4 <= '5'  && biome > 3) {
                if(key5 <= getCharComparedToPercentage(20))buildingType = 2;
            } else if (key4 >= '6' && key4 <= '7'  && biome > 3) {
                if(key5 <= getCharComparedToPercentage(30))buildingType = 3;
            } else if (key4 >= '8' && key4 <= '9'  && biome > 3) {
                if(key5 <= getCharComparedToPercentage(30))buildingType = 4;
            } else if (key4 >= 'a' && key4 <= 'b'  && biome > 3) {
                if(key5 <= getCharComparedToPercentage(30))buildingType = 5;
            } else if (key4 >= 'c' && key4 <= 'd') {
                if(key5 <= getCharComparedToPercentage(50))buildingType = 6;
            }else {
                if(key5 <= getCharComparedToPercentage(100))buildingType = 7;
            }

            if(isCenterChunk)buildingType = 0;
        }

        if(number != 0){
            if(this.load){
                buildingType = createBuilding(number, cell01, buildingType).id;
            }
            bCellX[0] = cell01[0];
            bCellY[0] = cell01[1];
            bType[0] = buildingType;
        }

        if(number != 0)for(int i = 2; i <= number; i++){
            char key01 = key.charAt(i+2);   
            if (key01 >= '0' && key01 <= '3') {
                if(!completedCell[0]){
                    cell[0]=0;
                    cell[1]=0;
                    completedCell[0] = true;
                }else if(!completedCell[1]){
                    cell[0]=0;
                    cell[1]=2;
                    completedCell[1] = true;
                }else if(!completedCell[2]){
                    cell[0]=2;
                    cell[1]=0;
                    completedCell[2] = true;
                }else if(!completedCell[3]){
                    cell[0]=2;
                    cell[1]=2;
                    completedCell[3] = true;
                }
                
            } else if (key01 >= '4' && key01 <= '7') {
                if(!completedCell[1]){
                    cell[0]=0;
                    cell[1]=2;
                    completedCell[1] = true;
                }else if(!completedCell[2]){
                    cell[0]=2;
                    cell[1]=0;
                    completedCell[2] = true;
                }else if(!completedCell[3]){
                    cell[0]=2;
                    cell[1]=2;
                    completedCell[3] = true;
                }else if(!completedCell[0]){
                    cell[0]=0;
                    cell[1]=0;
                    completedCell[0] = true;
                } 
            } else if (key01 >= '8' && key01 <= 'b') {
                if(!completedCell[2]){
                    cell[0]=2;
                    cell[1]=0;
                    completedCell[2] = true;
                }else if(!completedCell[3]){
                    cell[0]=2;
                    cell[1]=2;
                    completedCell[3] = true;
                }else if(!completedCell[0]){
                    cell[0]=0;
                    cell[1]=0;
                    completedCell[0] = true;
                }else if(!completedCell[1]){
                    cell[0]=0;
                    cell[1]=2;
                    completedCell[1] = true;
                } 
            } else {
                if(!completedCell[3]){
                    cell[0]=2;
                    cell[1]=2;
                    completedCell[3] = true;
                }else if(!completedCell[0]){
                    cell[0]=0;
                    cell[1]=0;
                    completedCell[0] = true;
                }else if(!completedCell[1]){
                    cell[0]=0;
                    cell[1]=2;
                    completedCell[1] = true;
                }else if(!completedCell[2]){
                    cell[0]=2;
                    cell[1]=0;
                    completedCell[2] = true;
                } 
            }

            if(this.load)createBuilding(number, cell, 7);
            bCellX[i-1] = cell[0];
            bCellY[i-1] = cell[1];
            bType[i-1] = 7;
        }
    }
    private char getCharComparedToPercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 100.");
        }
    
        char[] charArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int index = (int)(percentage / 6.25)-1;
        return charArray[index];
    }

    private Buildings createBuilding(int number, int[] cell, int buildingType){
        int[] code = {(number)*(cell[0]+1),(number)*(cell[1]+1)};
        String buildingKey = getObjectKey(code);

        Buildings building01 = new Buildings(gameName, buildingType, chunk, cell, buildingKey);
        buildingType = building01.id;

        Objects obj = new Objects(cell[0]*cellSize+(cellSize/2)+building01.offsetX, cell[1]*cellSize+(cellSize/2)+ building01.offsetY, building01.dimension, building01.imageUrl, 1, backgroundPanel);         
        restrictedAreas.add(obj.restrictedAreas);
        if((buildingType!=7)){
            triggerableBuilding.add(building01);
        }  
        return building01;
    }

    public void addDecorations(){
        String decorationKey = "0";
        int[] position = {0,0};
        int[] cell = {0,0};
        char key01 = decorationKey.charAt(0);
        char key02 = decorationKey.charAt(0);

        boolean structure = false;

        int decorationType = -1;

        for(int i=2; i>=0; i--){
            cell[0]=i;
            for(int j=2; j>=0; j--){
                cell[1]=j;
            
                for(int k=2; k>=0; k--){
                    position[0]=k;
                    for(int l=2; l>=0; l--){
                        position[1]=l;

                        if((((i==0&&j==0)&&completedCell[0])||((i==0&&j==2)&&completedCell[1])||((i==2&&j==0)&&completedCell[2])||((i==2&&j==2)&&completedCell[3]))){
                            k=-1;
                            l=-1;
                            break;
                        }

                        int[] code = {(position[0]+1)*(cell[0]+1),(position[1]+1)*(cell[1]+1)};

                        decorationKey = getObjectKey(code);

                        key01 = decorationKey.charAt((position[0])*(3)+(position[1]+1));
                        key02 = decorationKey.charAt((position[0])*(3)+(position[1]+1)+1);

                        if(biome == 0 || biome == 1){
                            if(k==2 && l==2 && key01 == '0' && !structure){
                                position[0]=0;
                                position[1]=0;

                                Decorations deco = new Decorations("lacs", 0, chunk, cell, decorationKey);
                                int size[] = {(int)(deco.dimension[0]+deco.scale),(int)(deco.dimension[1]+deco.scale)};

                                new Objects(cell[0]*cellSize+(position[0]*(cellSize/3))+((size[0])/2)+deco.offsetX, cell[1]*cellSize+(position[1]*(cellSize/3))+((size[1])/2)+deco.offsetY, size, deco.imageUrl, 0, backgroundPanel);         
            
                                k=-1;
                                l=-1;
                                structure = true;
                                break;
                            }

                            if(k==2 && l==2 && key01 == '1' && !structure && ((i==0&&j==0)||(i==2&&j==2)||(i==0&&j==2)||(i==2&&j==0))){
                                position[0]=0;
                                position[1]=0;

                                Buildings building01 = new Buildings(gameName, 6, chunk, cell, decorationKey);

                                Objects obj = new Objects(cell[0]*cellSize+(cellSize/2)+building01.offsetX, cell[1]*cellSize+(cellSize/2)+ building01.offsetY, building01.dimension, building01.imageUrl, 1, backgroundPanel);         
                                restrictedAreas.add(obj.restrictedAreas);
                                //triggerableBuilding.add(building01);

                                k=-1;
                                l=-1;
                                structure = true;
                                break;
                            }

                            if(key01 >= '0' && key01 <= '6'){
                                decorationType = -1;
                            }else if(key01 >= '7' && key01 <= 'e'){
                                decorationType = biome;
                            }else if(key01 == 'f' && key02 >= 'a'){
                                if(biome == 0){
                                    decorationType = 1;
                                }else{
                                    decorationType = 0;
                                }   
                            }
                            if(decorationType >= 0){
                                Decorations deco = new Decorations("arbres", decorationType, chunk, cell, decorationKey);
                                int size[] = {(int)(deco.dimension[0]+deco.scale),(int)(deco.dimension[1]+deco.scale)};

                                new Objects(cell[0]*cellSize+(position[0]*(cellSize/3))+((size[0])/2)+deco.offsetX, cell[1]*cellSize+(position[1]*(cellSize/3))+((size[1])/2)+deco.offsetY, size, deco.imageUrl, 0, backgroundPanel);         
                            }else{
                                int ran = random.nextInt(40);
                                if(ran == 1)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),0);
                                if(ran == 2)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),1);
                            }
                        }else if(biome == 2 || biome == 3 || biome == 4){
                            if(k==2 && l==2 && key01 == '0' && !structure){
                                position[0]=0;
                                position[1]=0;

                                Decorations deco = new Decorations("lacs", 0, chunk, cell, decorationKey);
                                int size[] = {(int)(deco.dimension[0]+deco.scale),(int)(deco.dimension[1]+deco.scale)};

                                new Objects(cell[0]*cellSize+(position[0]*(cellSize/3))+((size[0])/2)+deco.offsetX, cell[1]*cellSize+(position[1]*(cellSize/3))+((size[1])/2)+deco.offsetY, size, deco.imageUrl, 0, backgroundPanel);                    
            
                                k=-1;
                                l=-1;
                                structure = true;
                                break;
                            }
                            
                            if(key01 >= '0' && key01 <= '9'){
                                decorationType = -1;
                            }else if(key01 >= 'a' && key01 <= 'e'){
                                decorationType = 0;
                                if(biome==2){
                                    decorationType = 0;
                                }else if(biome==3){
                                    decorationType = 1;
                                }
                            }else if(key01 == 'f'){
                                decorationType = 1;
                                if(biome==2){
                                    decorationType = 1;
                                }else if(biome==3){
                                    decorationType = 0;
                                }  
                            }
                            if(decorationType >= 0){
                                Decorations deco = new Decorations("arbres", decorationType, chunk, cell, decorationKey);
                                int size[] = {(int)(deco.dimension[0]+deco.scale),(int)(deco.dimension[1]+deco.scale)};

                                new Objects(cell[0]*cellSize+(position[0]*(cellSize/3))+((size[0])/2)+deco.offsetX, cell[1]*cellSize+(position[1]*(cellSize/3))+((size[1])/2)+deco.offsetY, size, deco.imageUrl, 0, backgroundPanel);
                            }else{
                                int ran = random.nextInt(100);
                                if(ran >= 0 && ran <=5)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),0);
                                if(ran == 6 || ran == 7)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),1);
                            }
                        }else if(biome == 5 || biome == 6 || biome == 7){
                            decorationType = -1;
                            if(key01 == '0' && key02 <= '1'){
                                decorationType = 0;
                            }else if(key01 == '1' && key02 <= '5'){
                                decorationType = 1;
                            }

                            if(decorationType >= 0){
                                Decorations deco = new Decorations("arbres", decorationType, chunk, cell, decorationKey);
                                int size[] = {(int)(deco.dimension[0]+deco.scale),(int)(deco.dimension[1]+deco.scale)};

                                new Objects(cell[0]*cellSize+(position[0]*(cellSize/3))+((size[0])/2)+deco.offsetX, cell[1]*cellSize+(position[1]*(cellSize/3))+((size[1])/2)+deco.offsetY, size, deco.imageUrl, 0, backgroundPanel);
                            }else{
                                int ran = random.nextInt(100);
                                if(ran >= 0 && ran <=5)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),0);
                                if(ran == 6)createItem(cell[0]*cellSize+(position[0]*(cellSize/3)),cell[1]*cellSize+(position[1]*(cellSize/3)),1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void createItem(int x, int y, int type){
        Items item = new Items(gameName,type);
        Object[] obj = item.addItem(x,y,backgroundPanel);

        trigerEvents.add(obj);
    }

    public void addInsideBuildings(){
        int type = bType[0];

        Inside inside01 = new Inside(type,boxSize,gameName, backgroundPanel);
        restrictedAreas = inside01.restrictedAreas;
        trigerEvents = inside01.trigerEvents;

        backPic = inside01.imageUrl;
    }


    public String hash(String seed, long value1, long value2) {
        try {
            String input = seed + value1 + value2;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getObjectKey(int[] cell){
        String key01 = hash(seed+key,cell[0],cell[1]);
        return(key01);
    }

    public String getKey(){
        String key = hash(seed,chunk[0],chunk[1]);
        return(key);
    };
}
