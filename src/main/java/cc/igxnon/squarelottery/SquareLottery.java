package cc.igxnon.squarelottery;

import cc.igxnon.squarelottery.commands.CommandBuilder;
import cc.igxnon.squarelottery.languages.Languages;
import cc.igxnon.squarelottery.lottery.roundabout.prizepool.PrizePool;
import cn.nukkit.plugin.PluginBase;

/**
 * @author iGxnon
 * @date 2021/8/29
 */
public class SquareLottery extends PluginBase {

    private static SquareLottery instance;

    public static SquareLottery getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Languages.init();
        PrizePool.init();
        CommandBuilder.init();
    }


//    public static void main(String[] args) throws URISyntaxException, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        File file = new File(System.getProperty("user.dir") + "/skin2.png");
//        BufferedImage bufferedImage = ImageIO.read(file);
//        Class<Skin> skinClass = Skin.class;
//        Method method = skinClass.getDeclaredMethod("parseBufferedImage", BufferedImage.class);
//        method.setAccessible(true);
//        Skin skin = skinClass.newInstance();
//        SerializedImage image = (SerializedImage) method.invoke(skin, bufferedImage);
//        System.out.println(new String(Base64.getEncoder().encode(image.data)));
//    }

    @Override
    public void onDisable() {
        PrizePool.save();
    }

}
