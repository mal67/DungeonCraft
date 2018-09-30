#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec3 normals;
layout(location = 4) in vec3 instanceOffset;


out vec3 diffColor;
out vec4 vColor;
out vec2 texC;
out vec3 norms;
out vec3 FragPos;
out vec3 lightPos;

uniform mat4 MVP;
uniform mat4 transform;
uniform vec3 lightPosition;
uniform vec3 diffuseColor;
uniform vec3 v3fTranslate;

void main()
{

	lightPos = lightPosition;
	diffColor = diffuseColor;
    vColor = color;
    
    vec4 instancePosition = vec4(instanceOffset.x ,instanceOffset.y,instanceOffset.z, 0);
    gl_Position = MVP * (instancePosition + transform * vec4(pos,1));
    
    texC = texCoords;
    FragPos = vec3(instancePosition + transform * vec4(pos,1));
	norms = normalize(transpose(inverse(mat3(transform))) * normals);
}