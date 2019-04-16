package mainEngine;

import com.badlogic.gdx.physics.bullet.Bullet;
import mainEngine.callbacks.mouse.Mouse;

import mainEngine.contexts.UIRender;
import mainEngine.datastore.DataStoreGlobal;
import mainEngine.graphics.camera.Camera;
import mainEngine.physics.PhysicsWorld;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


import mainEngine.graphics.projections.*;

import mainEngine.callbacks.*;




public class MainApplication {

    // The window handle
    private long window;


    //Float buffers for joystick inout
    FloatBuffer floatBuffer ;

    //Int buffers for window size
    IntBuffer pWidth;
    IntBuffer pHeight;

    public int[] getWindowDimensions(){
        return new int[]{pWidth.get(0),pHeight.get(0)};
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);



        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {

        PhysicsWorld.getInstance().initializePhysics();



        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        glfwWindowHint(GLFW_OPENGL_PROFILE,GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(vidmode.width(), vidmode.height(), "FRC GRID 2019", NULL, NULL);
        if ( window == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        DataStoreGlobal.getInstance().setProperty("windowID",window);

        PerspectiveProjection.setWindowID(window);
        PerspectiveProjection.createNewProjection(70.0f,0.1f,100.0f);
        OrthographicProjection.setWindowID(window);
        OrthographicProjection.createProjection();


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            pWidth = stack.mallocInt(1); // int*
            pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetFramebufferSize(window,pWidth,pHeight);


            // Center the window
//            glfwSetWindowPos(
//                    window,
//                    (vidmode.width() - pWidth.get(0)) / 2,
//                    (vidmode.height() - pHeight.get(0)) / 2
//            );
        } // the stack frame is popped automatically




        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        String joystickName = glfwGetJoystickName(GLFW_JOYSTICK_1);

        System.out.println(joystickName);




        GL.createCapabilities();



        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        MapRenderer gp = new MapRenderer();


        KeyboardHandler.getInstance().initKeyboard(window);
        Camera.getInstance().setWindowID(window);
        Mouse.getInstance().initWindow(window);


       // imgui.styleColorsDark();

        boolean contextsInit = false;
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {


            floatBuffer = glfwGetJoystickAxes(GLFW_JOYSTICK_1);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


            glfwGetFramebufferSize(window,pWidth,pHeight);
            glViewport(0,0,pWidth.get(0),pHeight.get(0));

          //  System.out.printf("height: %d width : %d\n",pHeight.get(0),pWidth.get(0));


            DataStoreGlobal.getInstance().setProperty("windowWidth",pWidth.get(0));
            DataStoreGlobal.getInstance().setProperty("windowHeight",pHeight.get(0));

            glfwGetWindowSize(window,pWidth,pHeight);

            DataStoreGlobal.getInstance().setProperty("windowWidthActual",pWidth.get(0));
            DataStoreGlobal.getInstance().setProperty("windowHeightActual",pHeight.get(0));

            CallbackHandler.timeForUpdateCB();
            CallbackHandler.timeForDrawCB();

            if(!contextsInit){
                UIRender.getInstance().initializeContexts();
                contextsInit = true;
            }

            UIRender.getInstance().timeFor2DCallbacks();


            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();



            CallbackHandler.timeForAfterdrawCallbacks();
        }
    }



}