package greenteam.dungeoncraft.Engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GameWindow {

	private long window;
	private double mouseXPos = 0;
	private double mouseYPos = 0;
	private boolean mouseWithinWindow;
	private int width;
	private int height;

	
	/* Constructor  */
	public GameWindow() {
	    
	}

	
	/* Initialize the glfw window */
	public void init() {

		width = 1280;
		height = 720;

		// error callback
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // hints
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // hide after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // allow resizing
		glfwWindowHint(GLFW_SAMPLES, 4); // anti aliasing
		// Create window
		window = glfwCreateWindow(width, height, "Dungeon Craft", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Error creating the Window");

		glfwSetCursorEnterCallback(window, (windowHandle, entered) -> {
			mouseWithinWindow = entered;
		});

		// Push new frame to thread stack
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			// Get Window Size;
			glfwGetWindowSize(window, pWidth, pHeight);
			// Get Monitor Resolution (primary display)
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			// Center
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		// hide mouse cursor
		disableMouseCursor();
	
		glfwMakeContextCurrent(window);
		// Vertical Sync (sync with monitor refresh rate)
		glfwSwapInterval(1);
		
		
	}
	
	public void disableMouseCursor() {
	    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	public void enableMouseCursor() {
	    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}

	public void Destroy() {

	}

	public long getWindow() {
		return window;
	}

	public double getMouseXPos() {
		return mouseXPos;
	}

	public void setMouseXPos(double mouseXPos) {
		this.mouseXPos = mouseXPos;
	}

	public double getMouseYPos() {
		return mouseYPos;
	}

	public void setMouseYPos(double mouseYPos) {
		this.mouseYPos = mouseYPos;
	}

	public boolean isMouseWithinWindow() {
		return mouseWithinWindow;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
