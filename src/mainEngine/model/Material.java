package mainEngine.model;

import mainEngine.graphics.Color;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.*;


public class Material {

    AIMaterial material;

    Color ambientColor;
    Color diffuseColor;
    Color specularColor;

    public Material(AIMaterial material) {
        this.material = material;

        AIColor4D colorTemp = AIColor4D.create();

        Assimp.aiGetMaterialColor(material,Assimp.AI_MATKEY_COLOR_AMBIENT,Assimp.aiTextureType_NONE,0,colorTemp);

        ambientColor = new Color(colorTemp.r(),colorTemp.g(),colorTemp.b(),colorTemp.a());

        Assimp.aiGetMaterialColor(material,Assimp.AI_MATKEY_COLOR_DIFFUSE,Assimp.aiTextureType_NONE,0,colorTemp);

        diffuseColor = new Color(colorTemp.r(),colorTemp.g(),colorTemp.b(),colorTemp.a());

        Assimp.aiGetMaterialColor(material,Assimp.AI_MATKEY_COLOR_SPECULAR,Assimp.aiTextureType_NONE,0,colorTemp);

        specularColor = new Color(colorTemp.r(),colorTemp.g(),colorTemp.b(),colorTemp.a());



    }
}
