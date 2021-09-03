package cc.igxnon.squarelottery.lottery.roundabout.prizepool;

import cc.igxnon.squarelottery.SquareLottery;
import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.RoundaboutEntity;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
@SuppressWarnings("unused")
// 增删改查...
public class PrizePool {

    public static Config prizePoolConfig;
    public static Config config;
    public static final Map<String, Prize> allPrize = new HashMap<>();
    public static final Map<String, PrizePool> allPrizePool = new HashMap<>();
    public static final Map<String, Prize> waitPrizePlayers = new HashMap<>();

    public static double defaultRedRate = 0.25D;
    public static double defaultBlueRate = 0.25D;
    public static double defaultYellowRate = 0.25D;
    public static double defaultOrangeRate = 0.25D;

    public String name;
    public int prizeSize;
    public Map<String, Prize> prizePool = new HashMap<>();

    public static void init() {
        SquareLottery.getInstance().saveResource("roundabout/config.yml");
        prizePoolConfig = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/prizepool.yml", Config.YAML);
        config = new Config(SquareLottery.getInstance().getDataFolder() + "/roundabout/config.yml", Config.YAML);
        double redRate = config.getDouble("defaultRedRate");
        double blueRate = config.getDouble("defaultBlueRate");
        double yellowRate = config.getDouble("defaultYellowRate");
        double orangeRate = config.getDouble("defaultOrangeRate");
        if(redRate + blueRate + yellowRate + orangeRate == 1.0D) {
            defaultRedRate = redRate;
            defaultBlueRate = blueRate;
            defaultYellowRate = yellowRate;
            defaultOrangeRate = orangeRate;
        }else {
            SquareLottery.getInstance().getLogger().warning(Languages.translate("%lottery_roundabout_prizepool_config_warning%"));
        }
        initPrize();
        initPrizePool();
        initMachines();
        initWaitList();
        SquareLottery.getInstance().getLogger().info(Languages.translate("%lottery_roundabout_prizepool_init%"));
    }

    public static void initPrize() {
        if(prizePoolConfig == null) {
            return;
        }
        prizePoolConfig.getAll().forEach((key, secondMap) -> {
            String info = (String) ((Map<String, Object>) secondMap).get("info");
            Prize.PrizeType prizeType = Prize.PrizeType.of((String) ((Map<String, Object>) secondMap).get("prizeType"));
            String prizeValue = (String) ((Map<String, Object>) secondMap).get("prizeValue");
            int prizeCount = (int) ((Map<String, Object>) secondMap).get("prizeCount");
            int prizeSize = (int) ((Map<String, Object>) secondMap).get("prizeSize");
            boolean prizeArranged = (boolean) ((Map<String, Object>) secondMap).get("prizeArranged");
            List<List<String>> prizeArrangements = (List<List<String>>) ((Map<String, Object>) secondMap).get("prizeArrangements");
            for(List<String> childList : prizeArrangements) {
                if(childList.size() != prizeSize) {
                    SquareLottery.getInstance().getLogger().warning(Languages.translate("%lottery.roundabout_prize_init_failed%").replace("{prizeName}", key));
                    return;
                }
            }
            Prize prize = new Prize();
            prize.setName(key);
            prize.setInfo(info);
            prize.setPrizeType(prizeType);
            prize.setPrizeValue(prizeValue);
            prize.setPrizeCount(prizeCount);
            prize.setPrizeSize(prizeSize);
            prize.setPrizeArranged(prizeArranged);
            prize.setPrizeArrangements(prizeArrangements);
            allPrize.put(key, prize);
        });
    }

    public static void initPrizePool() {
        Map<String, List<String>> prizePools = (Map<String, List<String>>) config.get("prizePools");
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        prizePools.forEach((prizePoolName, prizeNames) -> {
            if(prizeNames.size() == 0) {
                return;
            }
            atomicInteger.set(allPrize.get(prizeNames.get(0)).getPrizeSize());
            PrizePool prizePool = PrizePool.createPrizePool(prizePoolName, atomicInteger.get());
            prizeNames.forEach(prizeName -> {
                prizePool.addPrize(allPrize.get(prizeName));
            });
            atomicInteger.set(-1);
        });
    }

    public static void initMachines() {
        List<String> machines = config.getStringList("machines");
        machines.forEach(infoStr -> {
            String[] splitInfo = infoStr.split("#");
            int x = Integer.parseInt(splitInfo[0]);
            int y = Integer.parseInt(splitInfo[1]);
            int z = Integer.parseInt(splitInfo[2]);
            int yaw = Integer.parseInt(splitInfo[3]);
            Level level = Server.getInstance().getLevelByName(splitInfo[4]);
            Location location = new Location(x, y, z, yaw, 0, level);
            RoundaboutEntity roundaboutEntity = RoundaboutEntity.createLottery(location);
            roundaboutEntity.spawnToAll();
        });
    }

    public static void savePrizePool(PrizePool prizePool) {
        Map<String, List<String>> prizePools = (Map<String, List<String>>) config.get("prizePools");
        List<String> prizeList = Arrays.asList(prizePool.prizePool.keySet().toArray(new String[0]));
        prizePools.put(prizePool.name, prizeList);
        config.set("prizePools", prizePools);
        config.save();
    }

    @Deprecated
    public static void saveMachine(RoundaboutEntity roundaboutEntity) {
        roundaboutEntity.save();
    }

    public static void initWaitList() {
        Map<String, String> waitList = (Map<String, String>) config.get("waitPrizePlayers");
        waitList.forEach((player, prizeName) -> waitPrizePlayers.put(player, allPrize.get(prizeName)));
    }

    public static void save() {
        saveWaitList();
    }

    public static void saveWaitList() {
        Map<String, String> waitList = new HashMap<>();
        waitPrizePlayers.forEach((player, prize) -> waitList.put(player, prize.getName()));
        config.set("waitPrizePlayers", waitList);
        config.save();
    }

    /**
     * Nk 储存会打乱arrange顺序
     */
    @Deprecated
    public static void savePrize(Prize prize) {
        if(!allPrize.containsValue(prize)) {
            allPrize.put(prize.getName(), prize);
        }
        Map<String ,Object> prizeMap = new HashMap<>();
        prizeMap.put("info", prize.getInfo());
        prizeMap.put("prizeType", prize.getPrizeType().name().toLowerCase(Locale.ROOT));
        prizeMap.put("prizeValue", prize.getPrizeValue());
        prizeMap.put("prizeCount", prize.getPrizeCount());
        //prizeMap.put("prizeSize", prize.getPrizeSize());
        //prizeMap.put("prizeArrangements", prize.getPrizeArrangements());
        prizeMap.put("prizeArranged", prize.isPrizeArranged());
        prizePoolConfig.set(prize.getName(), prizeMap);
        prizePoolConfig.save();
    }

    public PrizePool(String name, int prizeSize) {
        this.name = name;
        this.prizeSize = prizeSize;
        allPrizePool.put(name, this);
    }

    public static PrizePool createPrizePool(String name, int prizeSize) {
        return new PrizePool(name, prizeSize);
    }

    public boolean addPrize(Prize prize) {
        if(prize.getPrizeSize() != this.prizeSize) {
            SquareLottery.getInstance().getLogger().warning(Languages.translate("%lottery_roundabout_prizepool_add_failed%"));
            return false;
        }
        prizePool.put(prize.getName(), prize);
        return true;
    }

}
