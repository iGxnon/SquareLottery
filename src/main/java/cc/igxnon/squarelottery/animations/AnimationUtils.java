package cc.igxnon.squarelottery.animations;

import cc.igxnon.squarelottery.SquareLottery;
import cc.igxnon.squarelottery.animations.procotol.AnimateEntityPacket;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.resourcepacks.ResourcePack;
import cn.nukkit.resourcepacks.ResourcePackManager;
import cn.nukkit.resourcepacks.ZippedResourcePack;
import com.sun.xml.internal.rngom.parse.host.Base;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author iGxnon
 * @date 2021/8/30
 */
@SuppressWarnings("unused")
public class AnimationUtils {

    public static final String ROUNDABOUT_ANIMATION_JSON = "{\"format_version\":\"1.8.0\",\"animations\":{\"animation.roundabout.lottery.red\":{\"loop\":\"hold_on_last_frame\",\"animation_length\":2.66667,\"bones\":{\"cjj\":{\"rotation\":{\"0.0\":[0,0,0],\"0.9167\":[0,0,832.5],\"1.9167\":[0,0,1090]}}}},\"animation.roundabout.lottery.blue\":{\"loop\":\"hold_on_last_frame\",\"animation_length\":2.66667,\"bones\":{\"cjj\":{\"rotation\":{\"0.0\":[0,0,0],\"0.9167\":[0,0,832.5],\"1.9167\":[0,0,1065]}}}},\"animation.roundabout.lottery.yellow\":{\"loop\":\"hold_on_last_frame\",\"animation_length\":2.66667,\"bones\":{\"cjj\":{\"rotation\":{\"0.0\":[0,0,0],\"0.9167\":[0,0,832.5],\"1.9167\":[0,0,1115]}}}},\"animation.roundabout.lottery.orange\":{\"loop\":\"hold_on_last_frame\",\"animation_length\":2.66667,\"bones\":{\"cjj\":{\"rotation\":{\"0.0\":[0,0,0],\"0.9167\":[0,0,832.5],\"1.9167\":[0,0,1220]}}}},\"animation.roundabout.rest\":{\"animation_length\":3}}}";

    public static final String MANIFEST = "{\"format_version\":2,\"header\":{\"description\":\"Animation resource pack\",\"name\":\"SquareLottery Animation Resource Pack\",\"uuid\":\""+UUID.randomUUID()+"\",\"version\":[0,0,1],\"min_engine_version\":[1,17,0]},\"modules\":[{\"description\":\"Animation resource pack\",\"type\":\"resources\",\"uuid\":\""+UUID.randomUUID()+"\",\"version\":[0,0,1]}]}";

    public static final String ICON_BASE64 = "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";

    /**
     * 全服广播动画包
     * @param packet 动画包
     */
    public static void senPacket(AnimateEntityPacket packet) {
        Server.getInstance().getOnlinePlayers().values().forEach(player -> senPacket(packet, player));
    }

    /**
     * 向单个个体发放动画包
     * @param packet 动画包
     * @param target 个体
     */
    public static void senPacket(AnimateEntityPacket packet, Player target) {
        target.dataPacket(packet);
    }

    public static AnimationPacketBuilder builder() {
        return new AnimationUtils.AnimationPacketBuilder();
    }

    public static boolean buildResourcePack() {
        File packFile = new File(SquareLottery.getInstance().getDataFolder().getParentFile().getParentFile(), "/resource_packs/SquareLotteryAnimationPack.zip");
        if(packFile.exists()) return false;
        try {
            if(!packFile.createNewFile()) return false;
            // 生成 .zip
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(packFile));
            ZipEntry manifest = new ZipEntry("manifest.json");
            zipOutputStream.putNextEntry(manifest);
            zipOutputStream.write(MANIFEST.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
            ZipEntry pack_icon = new ZipEntry("pack_icon.png");
            zipOutputStream.putNextEntry(pack_icon);
            zipOutputStream.write(Base64.getDecoder().decode(ICON_BASE64.getBytes(StandardCharsets.UTF_8)));
            zipOutputStream.closeEntry();
            ZipEntry animationJson = new ZipEntry("animations/roundabout.animation.json");
            zipOutputStream.putNextEntry(animationJson);
            zipOutputStream.write(ROUNDABOUT_ANIMATION_JSON.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
            zipOutputStream.close();

            // 向nk内写入新的packs
            ResourcePackManager resourcePackManager = Server.getInstance().getResourcePackManager();
            ResourcePack[] oldResourcePacks = resourcePackManager.getResourceStack();
            for(ResourcePack oldPack : oldResourcePacks) {
                if(oldPack.getPackName().equals("SquareLottery Animation Resource Pack")) {
                    return false;
                }
            }
            ResourcePack[] newResourcePacks = new ResourcePack[oldResourcePacks.length + 1];
            ResourcePack resourcePack = new ZippedResourcePack(packFile);
            System.arraycopy(oldResourcePacks, 0, newResourcePacks, 0, oldResourcePacks.length);
            newResourcePacks[oldResourcePacks.length] = resourcePack;

            Field resourcePacksField = ResourcePackManager.class.getDeclaredField("resourcePacks");
            Field resourcePacksByIdField = ResourcePackManager.class.getDeclaredField("resourcePacksById");
            resourcePacksByIdField.setAccessible(true);
            resourcePacksField.setAccessible(true);

            resourcePacksField.set(resourcePackManager, newResourcePacks);

            Map<UUID, ResourcePack>  resourcePacksById = (Map<UUID, ResourcePack>) resourcePacksByIdField.get(resourcePackManager);
            resourcePacksById.put(resourcePack.getPackId(), resourcePack);
            resourcePacksByIdField.set(resourcePackManager, resourcePacksById);

            SquareLottery.getInstance().getLogger().warning("Injected resource pack into nukkit...");
            return true;
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unused")
    public static class AnimationPacketBuilder {

        private String animation;
        private String nextState;
        private String stopExpression;
        private String controller;
        private float blendOutTime;
        private long entityRuntimeId;

        /**
         * 必要的
         * @param animation 动画identifier 储存在cc.igxnon.squarelottery.animations.Info中
         * @return Builder
         */
        public AnimationPacketBuilder animation(String animation) {
            this.animation = animation;
            return this;
        }

        public AnimationPacketBuilder nextState(String nextState) {
            this.nextState = nextState;
            return this;
        }

        public AnimationPacketBuilder stopExpression(String stopExpression) {
            this.stopExpression = stopExpression;
            return this;
        }

        public AnimationPacketBuilder controller(String controller) {
            this.controller = controller;
            return this;
        }

        /**
         * 消除时间
         * @param blendOutTime [填0为直接停止]
         * @return Builder
         */
        public AnimationPacketBuilder blendOutTime(float blendOutTime) {
            this.blendOutTime = blendOutTime;
            return this;
        }

        /**
         * 必要的
         * @param entityRuntimeId 目标实体runtimeId
         * @return Builder
         */
        public AnimationPacketBuilder entityRuntimeId(long entityRuntimeId) {
            this.entityRuntimeId = entityRuntimeId;
            return this;
        }

        /**
         * @return 获取的动画包对象
         */
        public AnimateEntityPacket build() {
            AnimateEntityPacket packet = new AnimateEntityPacket();
            packet.setAnimation(animation);
            packet.setController(controller);
            packet.setBlendOutTime(blendOutTime);
            packet.setNextState(nextState);
            packet.setStopExpression(stopExpression);
            packet.setEntityRuntimeIds(Collections.singletonList(entityRuntimeId));
            return packet;
        }

        /**
         * 必须在必要参数全补全了再发包
         * @param player 个体
         */
        public void deliverTo(Player player) {
            AnimationUtils.senPacket(build(), player);
        }

        /**
         * 必须在必要参数全补全了再发包
         */
        public void deliverToAll() {
            AnimationUtils.senPacket(build());
        }
    }


}
