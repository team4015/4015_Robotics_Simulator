#version 330 core


out vec4 fragColor;

in vec2 textureCoordinates;
in vec3 normal;
in vec3 fragPosition;

uniform vec3 color;
uniform float randomSeed;

#define MAX_NUMBER_OF_LIGHTS 20

struct pointLight{
      vec3 posLight;
      vec3 lightColor;

      float constant;
      float quadratic;
      float linear;

};

uniform int lightsAvail;

uniform pointLight pointLights[MAX_NUMBER_OF_LIGHTS];

uniform sampler2D textureIn;


vec4 calcPointLight(pointLight pointLighti,vec3 normal, vec3 fragPosition);

void main(){
    vec3 lightPosition = vec3(3.0f,10.0f,0.0f);
    vec4 lightColor = vec4(1.0f, 1.0f, 1.0f,1.0f);

    vec3 norm = vec3(normalize(normal));
    vec3 lightDirectionVector = vec3(normalize(lightPosition - fragPosition));

    float lightIntensity = max(dot(normal,lightDirectionVector),0.0);

    for(int i=0; i<lightsAvail;i++){
    fragColor+= calcPointLight(pointLights[i],norm,fragPosition) * texture(textureIn,textureCoordinates);
    }

}

vec4 calcPointLight(pointLight pointLighti,vec3 normal, vec3 fragPosition){
    vec3 lightDirectionVector = vec3(normalize(pointLighti.posLight - fragPosition));

    float distance = length(pointLighti.posLight,fragPosition);

    float attenuation = 1.0f/ (pointLighti.constant + pointLighti.linear * distance + pointLighti.quadratic*(distance*distance));

    float lightIntensity = max(dot(normal,lightDirectionVector),0.0) * attenuation;

    return vec4(lightIntensity * pointLighti.lightColor,1.0);
}

