package greenteam.dungeoncraft.Game.Controller;

import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Model.PlayerData;
import greenteam.dungeoncraft.Game.Model.PlayerDataList;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeaderBoardUtility {

	private PlayerDataList plyDList = new PlayerDataList();;
	
	
	public void LeaderBoardUtility() {
	    
	    plyDList = new PlayerDataList();
	    
	}

	public void parsePlayerXMLData() throws ParserConfigurationException, SAXException, IOException {

		File inputFile = new File("./src/main/java/greenteam/dungeoncraft/Game/Model/leaderboard.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		Node leaderBoardNode = doc.getFirstChild();
		NodeList nodeList = leaderBoardNode.getChildNodes();
		for (int dataNum = 0; dataNum < nodeList.getLength(); dataNum++) {
			Node node = nodeList.item(dataNum);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println("Name : "  + element.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("Score : "  + element.getElementsByTagName("score").item(0).getTextContent());
				PlayerData pData = new PlayerData();
				pData.setName(element.getElementsByTagName("name").item(0).getTextContent());
				pData.setScore(Integer.parseInt(element.getElementsByTagName("score").item(0).getTextContent()));
				plyDList.playerData.add(pData);
			}
		}
	}


	public void exportPlayerXMLData() {
		try {

			File file = new File("./src/main/java/greenteam/dungeoncraft/Game/Model/leaderboard.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(PlayerDataList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(plyDList, file);
			jaxbMarshaller.marshal(plyDList, System.out);

		} catch (JAXBException e) {
			System.err.print("could not save player score");
			e.printStackTrace();
		}
		plyDList.playerData.clear();
	}

	public void updatePlayerScore() {
		String playerName = Main.getPlayer().getName();
		int playerScore = Main.getPlayer().getScore();
		for (int i = plyDList.playerData.size() - 1; i >= 0; i--) {
			PlayerData currComparedPlayer = plyDList.playerData.get(i);
			if (playerScore > currComparedPlayer.getScore()) {
				PlayerData player = new PlayerData();
				player.setName(playerName);
				player.setScore(playerScore);

				plyDList.playerData.remove(0);
				plyDList.playerData.add(i, player);
				break;
			}
		}
	}
	
	public String produceRandomNumberedName(){
		int randNum = (int) (Math.random() * (1000 - 0));
		String name = "soldier-" + String.valueOf(randNum);
		return name;
	}


	public PlayerDataList getPlyDList() {
		return plyDList;
	}


	public void setPlyDList(PlayerDataList plyDList) {
		this.plyDList = plyDList;
	}

}
