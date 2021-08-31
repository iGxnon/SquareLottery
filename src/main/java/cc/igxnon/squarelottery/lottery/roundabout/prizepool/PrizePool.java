package cc.igxnon.squarelottery.lottery.roundabout.prizepool;

import cc.igxnon.squarelottery.SquareLottery;
import cc.igxnon.squarelottery.languages.Languages;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
@SuppressWarnings("unused")
// 增删改查...
public class PrizePool {

    public static Config config;
    public static Config setting;
    public static final Map<String, Prize> allPrize = new HashMap<>();
    public static final Map<String, PrizePool> allPrizePool = new HashMap<>();
    public static final Map<String, Prize> waitPrizePlayers = new HashMap<>();

    private static double defaultRedRate = 0.25D;
    private static double defaultBlueRate = 0.25D;
    private static double defaultYellowRate = 0.25D;
    private static double defaultOrangeRate = 0.25D;


    public String name;
    public Map<String, Prize> prizePool = new HashMap<>();

    public static void init() {
        SquareLottery.getInstance().saveResource("roundabout/config.yml");
        config = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/prizepool.yml", Config.YAML);
        setting = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML);
        double redRate = setting.getDouble("defaultRedRate");
        double blueRate = setting.getDouble("defaultBlueRate");
        double yellowRate = setting.getDouble("defaultYellowRate");
        double orangeRate = setting.getDouble("defaultOrangeRate");
        if(redRate + blueRate + yellowRate + orangeRate == 1.0D) {
            defaultRedRate = redRate;
            defaultBlueRate = blueRate;
            defaultYellowRate = yellowRate;
            defaultOrangeRate = orangeRate;
        }else {
            SquareLottery.getInstance().getLogger().warning(Languages.translate("%lottery_roundabout_prizepool_config_warning%"));
        }
        initPrizePool();
        initWaitList();
        SquareLottery.getInstance().getLogger().info(Languages.translate("%lottery_roundabout_prizepool_init%"));
    }

    public static void initPrizePool() {
        if(config == null) {
            return;
        }
        config.getAll().forEach((key, secondMap) -> {
            String info = (String) ((Map<String, Object>) secondMap).get("info");
            boolean showRate = (boolean) ((Map<String, Object>) secondMap).get("showRate");
            double redRate = defaultRedRate;
            double blueRate = defaultBlueRate;
            double yellowRate = defaultYellowRate;
            double orangeRate = defaultOrangeRate;
            if(((Map<String, Object>) secondMap).containsKey("redRate")
                && ((Map<String, Object>) secondMap).containsKey("blueRate")
                && ((Map<String, Object>) secondMap).containsKey("yellowRate")
                && ((Map<String, Object>) secondMap).containsKey("orangeRate")) {
                redRate = (double) ((Map<String, Object>) secondMap).get("redRate");
                blueRate = (double) ((Map<String, Object>) secondMap).get("blueRate");
                yellowRate = (double) ((Map<String, Object>) secondMap).get("yellowRate");
                orangeRate = (double) ((Map<String, Object>) secondMap).get("orangeRate");
                if(redRate + blueRate + yellowRate + orangeRate != 1.0D) {
                    SquareLottery.getInstance().getLogger().warning(Languages.translate("%lottery_roundabout_prizepoll_config_warning%"));
                    redRate = defaultRedRate;
                    blueRate = defaultBlueRate;
                    yellowRate = defaultYellowRate;
                    orangeRate = defaultOrangeRate;
                }
            }
            Prize.PrizeType prizeType = Prize.PrizeType.of((String) ((Map<String, Object>) secondMap).get("prizeType"));
            String prizeValue = (String) ((Map<String, Object>) secondMap).get("prizeValue");
            int prizeCount = (int) ((Map<String, Object>) secondMap).get("prizeCount");
            int prizeSize = (int) ((Map<String, Object>) secondMap).get("prizeSize");
            boolean prizeArranged = (boolean) ((Map<String, Object>) secondMap).get("prizeArranged");
            List<List<String>> prizeArrangements = (List<List<String>>) ((Map<String, Object>) secondMap).get("prizeArrangements");

            Prize prize = new Prize();
            prize.setName(key);
            prize.setInfo(info);
            prize.setShowRate(showRate);
            prize.setRedRate(redRate);
            prize.setBlueRate(blueRate);
            prize.setYellowRate(yellowRate);
            prize.setOrangeRate(orangeRate);
            prize.setPrizeType(prizeType);
            prize.setPrizeValue(prizeValue);
            prize.setPrizeCount(prizeCount);
            prize.setPrizeSize(prizeSize);
            prize.setPrizeArranged(prizeArranged);
            prize.setPrizeArrangements(prizeArrangements);
            allPrize.put(key, prize);
        });
    }

    public static void initWaitList() {
        Map<String, String> waitList = (Map<String, String>) setting.get("waitPrizePlayers");
        waitList.forEach((player, prizeName) -> waitPrizePlayers.put(player, allPrize.get(prizeName)));
    }

    public static void saveWaitList() {
        Map<String, String> waitList = new HashMap<>();
        waitPrizePlayers.forEach((player, prize) -> waitList.put(player, prize.getName()));
        setting.set("waitPrizePlayers", waitList);
        setting.save();
    }

    public static void savePrize(Prize prize) {
        if(!allPrize.containsValue(prize)) {
            allPrize.put(prize.getName(), prize);
        }
        Map<String ,Object> prizeMap = new HashMap<>();
        prizeMap.put("info", prize.getInfo());
        prizeMap.put("showRate", prize.isShowRate());
        prizeMap.put("redRate", prize.getRedRate());
        prizeMap.put("blueRate", prize.getBlueRate());
        prizeMap.put("yellowRate", prize.getYellowRate());
        prizeMap.put("orangeRate", prize.getOrangeRate());
        prizeMap.put("prizeType", prize.getPrizeType().name().toLowerCase(Locale.ROOT));
        prizeMap.put("prizeValue", prize.getPrizeValue());
        prizeMap.put("prizeCount", prize.getPrizeCount());
        prizeMap.put("prizeSize", prize.getPrizeSize());
        prizeMap.put("prizeArranged", prize.isPrizeArranged());
        prizeMap.put("prizeArrangements", prize.getPrizeArrangements());
        config.set(prize.getName(), prizeMap);
        config.save();
    }


    public PrizePool(String name) {
        this.name = name;
        allPrizePool.put(name, this);
    }

    public static PrizePool createPrizePool(String name) {
        return new PrizePool(name);
    }

    public PrizePool addPrize(Prize prize) {
        prizePool.put(prize.getName(), prize);
        return this;
    }

}
