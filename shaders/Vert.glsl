#version 330 core

 layout(location = 0) in vec2 pos;
 layout(location = 1) in vec2 textureCoord;


 uniform mat4 projection;

 out vec2 texCoord;

void main(){

    gl_Position = projection * vec4(pos,0.0f,1.0f);
    texCoord = textureCoord;
}


