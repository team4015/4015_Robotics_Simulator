package mainEngine.model;

import mainEngine.graphicBuffers.ElementBufferObject;
import mainEngine.graphicBuffers.VertexArrayObject;
import mainEngine.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.nio.IntBuffer;

import static com.badlogic.gdx.graphics.GL20.GL_TRIANGLES;
import static com.badlogic.gdx.graphics.GL20.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL11C.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Mesh {

    AIMesh modelMesh;

    float [] vertices;

    float [] textureCoords;

    float [] normals;

    int numFaces;

    int [] elements;

    VertexArrayObject vao;
    ElementBufferObject ibo;

    public Mesh(AIMesh mesh){
        this.modelMesh = mesh;
        vao = new VertexArrayObject();
        ibo = new ElementBufferObject();
        initMesh();
    }


    private void initMesh(){
        //LOAD VERTICES
        AIVector3D.Buffer verticesBuffer = modelMesh.mVertices();

        int numberOfVertices = modelMesh.mNumVertices();

        vertices = new float[numberOfVertices*3];

        int verticesProcessed = 0;

        for(int i = 0;i<vertices.length;i+=3){
            AIVector3D vertex = verticesBuffer.get(verticesProcessed);
            vertices[i] = vertex.x();
            vertices[i+1] = vertex.y();
            vertices[i+2] = vertex.z();
            verticesProcessed++;
        }

        vao.setData(0,vertices,false,GL_STATIC_DRAW);

        //LOAD TEXTURE COORDS

        int texturesProcessed = 0;

        int numberTexCoords = modelMesh.mNumUVComponents().get(0);

        textureCoords = new float[numberOfVertices * 2];

        for(int i =0; i <textureCoords.length;i+=2){
            AIVector3D.Buffer texCoords = modelMesh.mTextureCoords(texturesProcessed);
            textureCoords[i] = texCoords.x();
            textureCoords[i+1] = texCoords.y();
            texturesProcessed++;
        }

        vao.setData(2,textureCoords,true,GL_STATIC_DRAW);

        //LOAD NORMAL VECTORS

        int normalsProcessed = 0 ;

        AIVector3D.Buffer normalBuffer = modelMesh.mNormals();

        normals = new float[modelMesh.mNumVertices()*3];

        for(int i= 0 ; i<normals.length;i+=3){
            AIVector3D normal = normalBuffer.get(normalsProcessed);
            normals[i] = normal.x();
            normals[i+1] = normal.y();
            normals[i+2] = normal.z();
        }

        vao.setData(1,normals,false,GL_STATIC_DRAW);

        //PROCESS FACES FOR INDEXED DRAWS

        numFaces = modelMesh.mNumFaces();

        elements = new int[numFaces*3];

        IntBuffer tempIndexBuffer = BufferUtils.createIntBuffer(elements.length);

        AIFace.Buffer faceBuffer = modelMesh.mFaces();

        for(int i = 0; i < numFaces;i++){
            AIFace face = faceBuffer.get(i);
            tempIndexBuffer.put(face.mIndices());
        }

        tempIndexBuffer.get(elements);

        ibo.setData(elements);


    }

    public void draw(){
        vao.bind();
        ibo.bind();
        Logger.getInstance().clearErrors();
        glDrawElements(GL_TRIANGLES,elements.length,GL_UNSIGNED_INT,0L);
        glFinish();
        Logger.getInstance().getErrors();
        VertexArrayObject.unbind();
        ElementBufferObject.unbind();
    }


}
