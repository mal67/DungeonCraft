#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec3 normals;

out vec4 vColor;
out vec2 texC;
uniform mat4 transform;


void main()
{	
	
    vColor = color;
    texC = texCoords;
    gl_Position = transform * vec4(pos, 1);
    
}