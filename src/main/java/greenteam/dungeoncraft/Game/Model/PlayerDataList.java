package greenteam.dungeoncraft.Game.Model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LeaderBoard")
public class PlayerDataList {
    public List<PlayerData> playerData;
    
    public PlayerDataList() {
	playerData = new ArrayList();
    }
}
