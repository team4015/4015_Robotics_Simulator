package mainEngine.physics;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

public class PhysicsWorld{


    private static PhysicsWorld ourInstance = new PhysicsWorld();

    private btDynamicsWorld physicsWorld;
    private btDispatcher dispatcher;
    private btBroadphaseInterface broadphaseInterface;
    private btCollisionConfiguration collisionConfig;

    public static PhysicsWorld getInstance(){
        if(ourInstance == null){
            ourInstance = new PhysicsWorld();
        }
        return ourInstance;
    }

    private PhysicsWorld(){
        System.out.println("Initializing Physics!");
    }


    public void initializePhysics(){
        Bullet.init(true);
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadphaseInterface = new btDbvtBroadphase();
        physicsWorld = new btDiscreteDynamicsWorld(dispatcher,broadphaseInterface,new btSequentialImpulseConstraintSolver(),collisionConfig);
        physicsWorld.setGravity(new Vector3(0.0f,-9.81f,0.0f));
    }

    public void addCollisionObject(btCollisionObject object,int filterID){
        physicsWorld.addCollisionObject(object,filterID);
    }


    public static btRigidBody createBodyFromModelDynamic(float [] vertices,int [] indices, float mass,btMotionState motionState){



        if(mass == 0 ){
            throw new IllegalArgumentException("Mass cannot be 0 for dynamic bodies!");
        }

        btCollisionShape modelShape = new btConvexHullShape();

        for(int index : indices){
            index = index -1;
            float x = vertices[index];
            float y = vertices[index+1];
            float z = vertices[index+2];
            ((btConvexHullShape) modelShape).addPoint(new Vector3(x,y,z));
        }

        Vector3 inertia = new Vector3();

        modelShape.calculateLocalInertia(mass, inertia);

        return new btRigidBody(mass,motionState,modelShape,inertia);
    }

    public static btRigidBody createBodyStaticObject(float [] vertices,int [] indices,btMotionState motionState){


        btTriangleMesh triangleMesh = new btTriangleMesh();

        for(int i = 0; i<indices.length ; i+=3){
           int index1 = indices[i]-1;
           int index2 = indices[i+1]-1;
           int index3 = indices[i+2] -1 ;
            Vector3 vertex1 = new Vector3(vertices[index1],vertices[index1+1],vertices[index1+2]);
            Vector3 vertex2 = new Vector3(vertices[index2],vertices[index2+1],vertices[index2+2]);
            Vector3 vertex3 = new Vector3(vertices[index3],vertices[index3+1],vertices[index3+2]);

            triangleMesh.addTriangle(vertex1,vertex2,vertex3);
        }



        btCollisionShape modelShape = new btBvhTriangleMeshShape(triangleMesh,true);





        return new btRigidBody(0.0f,motionState,modelShape,new Vector3(0.0f,0.0f,0.0f));

    }

    public void addBodyToWorld(btRigidBody body){
        physicsWorld.addRigidBody(body);
    }

    public void stepWorld(double time){
        physicsWorld.stepSimulation((float)time);
    }



}
