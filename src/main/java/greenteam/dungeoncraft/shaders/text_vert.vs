#version 330 core

layout(location = 0) in vec3 pos;
layout(location = 1) in vec4 color;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec3 normals;

out vec4 vColor;
out vec2 texC;
uniform vec3 v3fTranslate;
uniform float textScale;


void main()
{	
	
    vColor = color;
    texC = texCoords;
	vec3 positions;
	positions.x = pos.x * textScale;
	positions.y = pos.y * textScale;
	positions.z = pos.z * textScale;
    gl_Position =  vec4(v3fTranslate + positions, 1);
    
}