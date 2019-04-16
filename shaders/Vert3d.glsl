#version 330 core

layout (location = 0 ) in vec3 posA;
layout (location = 1 ) in vec3 normalVector;
layout (location = 2 ) in vec2 textureCoord;



out vec2 textureCoordinates;

out vec3 normal;

out vec3 fragPosition;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;




void main() {
    vec4 newPosition = projection*view*vec4(posA,1.0);

    fragPosition = posA;

    textureCoordinates = textureCoord;
    normal = normalVector;

    gl_Position = newPosition;

}
