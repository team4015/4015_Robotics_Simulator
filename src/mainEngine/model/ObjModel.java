package mainEngine.model;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import mainEngine.graphicBuffers.*;
import mainEngine.graphics.Texture;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.Assimp.*;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryStack;

import org.lwjgl.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

import static org.lwjgl.BufferUtils.*;

import mainEngine.logging.*;
import sun.nio.ch.IOUtil;

public class ObjModel {

    String pathToObjFile;

    Texture fieldTexture;

    Vector3f currPos;

    Matrix4f modelMatrix;

    boolean loaded;

    AIScene model;

    ArrayList<Mesh> meshes;
    ArrayList<Material> materials;

    int numMeshes;

    int numMaterials;

    public ObjModel(String pathToObjFile , Vector3f currPos){
        this.pathToObjFile = pathToObjFile;
        glEnable(GL_DEPTH_TEST);
        loadModelAssimp();
        fieldTexture = Texture.loadTexture("fieldDiagram2019.png");
        loaded = true;
        this.currPos = new Vector3f(currPos);
        modelMatrix = new Matrix4f().translate(currPos);
    }

    public ObjModel(String pathToObjFile) {
        this.pathToObjFile = pathToObjFile;
        glEnable(GL_DEPTH_TEST);
        loadModelAssimp();
        fieldTexture = Texture.loadTexture("2019-field.jpg");
        loaded = true;
        this.currPos = new Vector3f(0.0f,0.0f,0.0f);
        modelMatrix = new Matrix4f().translate(currPos);
    }


    public void draw(){

        if(loaded) {
            fieldTexture.bindTexture();
                for(Mesh mesh : meshes){
                    mesh.draw();
                }

            fieldTexture.unbindTexture();

        }
    }

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = IOUtil.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private void loadModelFile(){
        AIFileOpenProcI fileOpenProc = new AIFileOpenProc() {
            public long invoke(long pFileIO, long fileName, long openMode) {
                AIFile aiFile = AIFile.create();
                final ByteBuffer data;

                String fileNameUtf8 = memUTF8(fileName);
                try {

                    data = ioResourceToByteBuffer(fileNameUtf8, 8192);
                } catch (IOException e) {
                    throw new RuntimeException("Could not open file: " + fileNameUtf8);
                }
                AIFileReadProcI fileReadProc = new AIFileReadProc() {
                    public long invoke(long pFile, long pBuffer, long size, long count) {
                        long max = Math.min(data.remaining(), size * count);
                        memCopy(memAddress(data) + data.position(), pBuffer, max);
                        return max;
                    }
                };
                AIFileSeekI fileSeekProc = new AIFileSeek() {
                    public int invoke(long pFile, long offset, int origin) {
                        if (origin == Assimp.aiOrigin_CUR) {
                            data.position(data.position() + (int) offset);
                        } else if (origin == Assimp.aiOrigin_SET) {
                            data.position((int) offset);
                        } else if (origin == Assimp.aiOrigin_END) {
                            data.position(data.limit() + (int) offset);
                        }
                        return 0;
                    }
                };
                AIFileTellProcI fileTellProc = new AIFileTellProc() {
                    public long invoke(long pFile) {
                        return data.limit();
                    }
                };
                aiFile.ReadProc(fileReadProc);
                aiFile.SeekProc(fileSeekProc);
                aiFile.FileSizeProc(fileTellProc);
                return aiFile.address();
            }
        };
        AIFileCloseProcI fileCloseProc = new AIFileCloseProc() {
            public void invoke(long pFileIO, long pFile) {
                /* Nothing to do */
            }
        };


        AIFileIO fileIO = AIFileIO.create();
        fileIO.set(fileOpenProc,fileCloseProc,NULL);


        model = Assimp.aiImportFileEx(pathToObjFile,Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices,fileIO);

        System.out.println(Assimp.aiGetErrorString());

    }

    private void loadModelAssimp(){

        meshes = new ArrayList<>();
        materials = new ArrayList<>();

        loadModelFile();

        System.out.println(Assimp.aiGetErrorString());

        numMeshes = model.mNumMeshes();

        PointerBuffer meshesBuffer = model.mMeshes();

        for(int i =0; i<numMeshes;i++){
            meshes.add(new Mesh(AIMesh.create(meshesBuffer.get(i))));
        }

        numMaterials = model.mNumMaterials();

        PointerBuffer materialsBuffer = model.mMaterials();

        for(int i=0; i<numMaterials;i++){
            materials.add(new Material(AIMaterial.create(materialsBuffer.get(i))));
        }

    }


    public void translateModel(Vector3f translationVector){
        this.currPos.x += translationVector.x;
        this.currPos.y += translationVector.y;
        this.currPos.z += translationVector.z;
        modelMatrix.translate(translationVector);
    }

    public Matrix4f getModelMatrix(){
        return modelMatrix;
    }

    public void rotateModel(double angle, RotationAxis axis){

        float angleRad  = (float)Math.toRadians(angle);

        switch (axis){
            case ROTATION_AXIS_X:
                this.currPos.rotateX(angleRad);
                modelMatrix.rotate(angleRad,new Vector3f(1.0f,0.0f,0.0f));
                break;
            case ROTATION_AXIS_Y:
                this.currPos.rotateY(angleRad);
                modelMatrix.rotate(angleRad,new Vector3f(0.0f,1.0f,0.0f));
                break;
            case ROTATION_AXIS_Z:
                this.currPos.rotateZ(angleRad);
                modelMatrix.rotate(angleRad,new Vector3f(0.0f,0.0f,1.0f));
                break;
        }

    }


}
