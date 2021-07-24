package xyz.lightsky.squarelottery.lottery.entities.manager;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Config;
import xyz.lightsky.squarelottery.SquareLottery;
import xyz.lightsky.squarelottery.lottery.entities.OneArmBandit;
import xyz.lightsky.squarelottery.lottery.entities.ache.OneArmBanditAche;
import xyz.lightsky.squarelottery.lottery.utils.DataPool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OneArmBanditManager {

    /* identifier -> ache */
    public static final Map<String, OneArmBanditAche> aches = new HashMap<>();

    public static void init() {
        File path = new File(SquareLottery.getInstance().getDataFolder() + "/OneArmBandit/");
        if(path.mkdirs()) {
            SquareLottery.getInstance().getLogger().info("first init");
        }
        registerAll();
    }

    public static void registerAll() {
        for(File file : Objects.requireNonNull(new File(SquareLottery.getInstance().getDataFolder() + "/OneArmBandit/").listFiles())) {
            if(file.getName().endsWith(".yml")) {
                register(OneArmBanditAche.load(new Config(file)));
            }
        }
    }

    public static void register(OneArmBanditAche ache) {
        aches.putIfAbsent(ache.getIdentifier(), ache);
    }

    public static CompoundTag getBaseNBT(String identifier) {
        CompoundTag tag = Entity.getDefaultNBT(aches.get(identifier).getLocation());
        tag.putCompound("Skin", new CompoundTag());
        tag.putString("Identifier", identifier);
        return tag;
    }

    public static void createAche(String identifier, Location location, String nameTag, double[] rates, float scale) {
        OneArmBanditAche ache = new OneArmBanditAche();
        ache.setIdentifier(identifier);
        ache.setLocation(location);
        ache.setNameTag(nameTag);
        ache.setPurpleRate(rates[0]);
        ache.setRedRate(rates[1]);
        ache.setBlueRate(rates[2]);
        ache.setGreenRate(rates[3]);
        ache.setScale(scale);
        ache.save();
        register(ache);
    }

    public static void spawn(String identifier) {
        if(aches.containsKey(identifier)) {
            CompoundTag tag = getBaseNBT(identifier);
            OneArmBanditAche ache = aches.get(identifier);
            OneArmBandit bandit = new OneArmBandit(ache.getLocation().getChunk(), tag);
            bandit.setSkin(DataPool.ONE_ARM_BANDIT_SKIN);
            bandit.spawnToAll();
        }
    }



}
