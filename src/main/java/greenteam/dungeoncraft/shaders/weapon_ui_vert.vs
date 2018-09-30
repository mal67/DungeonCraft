#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec3 normals;

out vec4 vColor;
out vec2 texC;
out vec3 norms;
out vec3 FragPos;
out vec3 lightPos;
uniform mat4 transform;
uniform vec3 v3fTranslate;
uniform vec3 lightPosition;

void main()
{	
    texC = texCoords;
    FragPos = vec3(vec4(v3fTranslate,0) + vec4(pos, 1));
	norms = normals;
	lightPos = lightPosition;
    vColor = color;
    gl_Position = transform * (vec4(v3fTranslate,0) + vec4(pos, 1));
}