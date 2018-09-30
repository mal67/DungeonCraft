#version 330 core

in vec4 vColor;
in vec2 texC;
out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
	fragColor = texture2D(sampler,texC.xy);
}