package xyz.lightsky.squarelottery.lottery.entities.ache;

import cn.nukkit.utils.Config;
import lombok.Getter;
import lombok.Setter;
import xyz.lightsky.squarelottery.SquareLottery;
import xyz.lightsky.squarelottery.lottery.utils.Languages;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OneArmBanditAche {

    private String identifier;

    private Config config;

    private float scale;

    private String nameTag;

    private float purpleRate;

    private float redRate;

    private float blueRate;

    private float greenRate;

    public void save() {
        config.set("identifier", getIdentifier());
        config.set("scale", getScale());
        config.set("nameTag", getNameTag());
        Map<String , Float> rates = new HashMap<>();
        rates.put("purpleRate", purpleRate);
        rates.put("redRate", redRate);
        rates.put("blueRate", blueRate);
        rates.put("greenRate", greenRate);
        config.set("rates", rates);
        config.save();
    }

    public static OneArmBanditAche load(Config config) {
        OneArmBanditAche ache = new OneArmBanditAche();
        ache.setConfig(config);
        ache.setIdentifier(config.getString("identifier"));
        ache.setScale((float) config.getDouble("scale"));
        ache.setNameTag(config.getString("nameTag"));
        try {
            Map<String, Float> rates = (Map<String, Float>) config.get("rates");
            ache.setPurpleRate(rates.get("purpleRate"));
            ache.setRedRate(rates.get("redRate"));
            ache.setBlueRate(rates.get("blueRate"));
            ache.setGreenRate(rates.get("greenRate"));
        }catch (ClassCastException e) {
            SquareLottery.getInstance().getLogger().warning(config.toString() + Languages.translate("%config_load_failed%"));
        }
        return ache;
    }

}
