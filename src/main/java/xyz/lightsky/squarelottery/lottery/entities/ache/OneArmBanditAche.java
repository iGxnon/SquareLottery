package xyz.lightsky.squarelottery.lottery.entities.ache;

import cn.nukkit.Server;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.lightsky.squarelottery.SquareLottery;
import xyz.lightsky.squarelottery.lottery.utils.Languages;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class OneArmBanditAche {

    private String identifier;

    private float scale;

    private String nameTag;

    private double purpleRate;

    private double redRate;

    private double blueRate;

    private double greenRate;

    private Location location;

    public void save() {
        Config config = new Config(SquareLottery.getInstance().getDataFolder() + "/OneArmBandit/" + identifier + ".yml", Config.YAML);
        config.set("identifier", getIdentifier());
        config.set("scale", getScale());
        config.set("nameTag", getNameTag());
        Map<String , Double> rates = new HashMap<>();
        rates.put("purpleRate", purpleRate);
        rates.put("redRate", redRate);
        rates.put("blueRate", blueRate);
        rates.put("greenRate", greenRate);
        config.set("rates", rates);
        String pos = location.x + "-" + location.y + "-" + location.z;
        String level = location.level.getName();
        config.set("pos", pos);
        config.set("level", level);
        config.set("yaw", location.yaw);
        config.save();
    }

    public static OneArmBanditAche load(Config config) {
        OneArmBanditAche ache = new OneArmBanditAche();
        ache.setIdentifier(config.getString("identifier"));
        ache.setScale((float) config.getDouble("scale"));
        ache.setNameTag(config.getString("nameTag"));
        String pos = config.getString("pos");
        Location location = new Location();
        try {
            location.setComponents(Double.parseDouble(pos.split("-")[0]), Double.parseDouble(pos.split("-")[1]), Double.parseDouble(pos.split("-")[2]));
            location.setLevel(Server.getInstance().getLevelByName(config.getString("level")));
            location.yaw = config.getDouble("yaw");
            ache.setLocation(location);
            Map<String, Double> rates = (Map<String, Double>) config.get("rates");
            ache.setPurpleRate(rates.get("purpleRate"));
            ache.setRedRate(rates.get("redRate"));
            ache.setBlueRate(rates.get("blueRate"));
            ache.setGreenRate(rates.get("greenRate"));
        }catch (Exception e) {
            SquareLottery.getInstance().getLogger().warning(config.toString() + Languages.translate("%config_load_failed%"));
        }
        return ache;
    }

}
