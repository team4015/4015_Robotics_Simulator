#version 330 core

out vec4 color;

in vec2 texCoord;

uniform sampler2D inTexture;

void main(){
    color = texture(inTexture,vec2(texCoord));
}