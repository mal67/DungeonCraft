package greenteam.dungeoncraft.Engine.Scene;

import java.util.ArrayList;

import greenteam.dungeoncraft.Util.FileReader;

public class MeshLoader {
	FileReader fr;

	ArrayList<Float> vertList;
	ArrayList<Float> normalList;
	ArrayList<Float> texList;
	ArrayList<Integer> indicesList;

	float[] texArr;
	float[] normalArr;
	float[] vertArr;
	int[] indicesArr;

	public MeshLoader() {

		fr = new FileReader();

		vertList = new ArrayList<Float>();
		normalList = new ArrayList<Float>();
		texList = new ArrayList<Float>();
		indicesList = new ArrayList<Integer>();
	}

	/* load a mesh from a given file location */
	public void loadFromFile(String source) {

		try {
			String myMeshStr = fr.ReadFile(source);
			
			String lines[] = myMeshStr.split("\\r?\\n");

			for (String line : lines) {
				if (line.startsWith("v  ")) {
					String[] verts = line.split(" ");
					vertList.add(Float.parseFloat(verts[2]));
					vertList.add(Float.parseFloat(verts[3]));
					vertList.add(Float.parseFloat(verts[4]));
				} else if (line.startsWith("vn ")) {
					String[] normals = line.split(" ");
					normalList.add(Float.parseFloat(normals[1]));
					normalList.add(Float.parseFloat(normals[2]));
					normalList.add(Float.parseFloat(normals[3]));
				} else if (line.startsWith("vt ")) {
					String[] tex = line.split(" ");
					texList.add(Float.parseFloat(tex[1]));
					texList.add(Float.parseFloat(tex[2]));
				}
			}

			texArr = new float[vertList.size() * 2];
			indicesArr = new int[vertList.size()];
			normalArr = new float[vertList.size() * 3];

			for (String line : lines) {
				if (line.startsWith("f ")) {
					
					String[] verts = line.split(" ");
					String[] sec1 = verts[1].split("/");
					String[] sec2 = verts[2].split("/");
					String[] sec3 = verts[3].split("/");
					
					parseF(sec1, texList, normalList, indicesList, texArr, normalArr);
					parseF(sec2, texList, normalList, indicesList, texArr, normalArr);
					parseF(sec3, texList, normalList, indicesList, texArr, normalArr);
				}
			}

			vertArr = convertListToFloatArr(vertList, vertArr);
			indicesArr = convertListToIntArr(indicesList, indicesArr);
		} catch ( NumberFormatException e ) {
			System.err.println( "Could not load mesh into arrays. note: Multiple objects in an .obj file are not supported" );
			e.printStackTrace();
		}
	}

	/* parse the mesh indices data, (.obj formats have individual indices data for the verts, frags and normals)
	 * openGL uses only indices relating the the vertexs for all 3 (verts/frag/normals) so data has to be parsed so that 
	 * the fragment data and normal data relate to the vertex indices
	 *  */
	public void parseF(String[] sec, ArrayList<Float> texListIn,ArrayList<Float> normalListIn, ArrayList<Integer> indicesListIn, float[] newTexArr,float[] newNormalArr) {
		int vertAddress = Integer.parseInt(sec[0]) - 1;
		int texAddress = Integer.parseInt(sec[1]) - 1;
		int normalAddress = Integer.parseInt(sec[2]) - 1;
		indicesListIn.add(vertAddress);
		//uvCoords
		newTexArr[vertAddress * 2] = (float) texListIn.get(texAddress * 2);
		newTexArr[vertAddress * 2 + 1] = 1 - (float) texListIn.get(texAddress * 2 + 1);
		//normalCoords
		newNormalArr[vertAddress * 3] = (float) normalListIn.get(normalAddress * 3);
		newNormalArr[vertAddress * 3 + 1] = (float) normalListIn.get(normalAddress * 3 + 1);
		newNormalArr[vertAddress * 3 + 2] = (float) normalListIn.get(normalAddress * 3 + 2);
	}

	public float[] convertListToFloatArr(ArrayList<Float> list, float[] arr) {
		arr = new float[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}

		return arr;
	}

	public int[] convertListToIntArr(ArrayList<Integer> list, int[] arr) {
		arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
}
