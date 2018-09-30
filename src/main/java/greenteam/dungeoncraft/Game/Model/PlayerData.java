package greenteam.dungeoncraft.Game.Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerData {

    String name;
    int score;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    @XmlElement
    public void setScore(int score) {
        this.score = score;
    }
} 
