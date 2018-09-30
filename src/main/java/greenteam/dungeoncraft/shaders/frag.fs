#version 330 core

in vec3 norms; 
in vec2 texC;
in vec4 vColor;
in vec3 diffColor;
in vec3 FragPos;
//in vec3 lightPos;

out vec4 fragColor;

uniform sampler2D sampler;
uniform vec3 lightPosition;
uniform float lightIntensity;
uniform int uvScale;

void main()
{
	vec3 normalz = vec3(0,0,1);
	vec3 ambient = 0.01f * vec3(0.15,0.14,0.13);
    float distance = length(lightPosition - FragPos)*length(lightPosition - FragPos)/2;
	
	
	vec3 norm = normalize(norms);
	vec3 lightDir = normalize(lightPosition-FragPos);
	float diff = max(dot(norm,lightDir),0.0)*0.1;
	vec3 diffuse = diff * vec3(0.1 + lightIntensity/100,0.1,0.1) * 100/distance;
	fragColor = texture2D(sampler,texC.xy*uvScale) * clamp(vec4(diffuse*diffColor*lightIntensity, 1.0),0,1.5) 
                 * vec4(1,1,1,1);
	
}