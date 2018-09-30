#version 330 core

in vec4 vColor;
in vec2 texC;
out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
	fragColor = vec4(0.8,0.8,0.8,1);
}